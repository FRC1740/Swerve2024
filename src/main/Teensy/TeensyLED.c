#include <i2c_device.h>
#include <i2c_driver.h>
#include <i2c_driver_wire.h>
#include <i2c_register_slave.h>
#include <FastLED.h>
#include <OctoWS2811.h>

const int numPins = 1;
int state = 0;

#define LED_PIN 2
byte pinList[numPins] = {LED_PIN}; // Apparently any T4.x pins can be used in parallel
//byte pinList[numPins] = {19,18,14,15,17,16,22,23}; // stock 4.0 parallel pins
const int ledsPerPin = 26;
#define NUM_LEDS (numPins*ledsPerPin)

CRGB leds[NUM_LEDS];

#define RED    0xFF0000
#define GREEN  0x00FF00
#define BLUE   0x0000FF
#define YELLOW 0xFFFF00
#define PINK   0xFF1088
#define ORANGE 0xE05800
#define WHITE  0xFFFFFF

const int ledsSize = sizeof(leds)/sizeof(leds[0]);
byte hue;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// These buffers need to be large enough for all the pixels. The total number of pixels is "ledsPerPin * numPins".
// Each pixel needs 3 bytes, so multiply by 3.  An "int" is 4 bytes, so divide by 4.  The array is created using "int"
// so the compiler will align it to 32 bit memory.
DMAMEM int displayMemory[ledsPerPin * numPins * 3 / 4];
int drawingMemory[ledsPerPin * numPins * 3 / 4];
OctoWS2811 octo(ledsPerPin, displayMemory, drawingMemory, WS2811_RGB | WS2811_800kHz, numPins, pinList);

template <EOrder RGB_ORDER = RGB, uint8_t CHIP = WS2811_800kHz>
class CTeensy4Controller : public CPixelLEDController<RGB_ORDER, 8, 0xFF>{
  OctoWS2811 *pocto;
  public:
    CTeensy4Controller(OctoWS2811 *_pocto):pocto(_pocto){};
    virtual void init() {}
    virtual void showPixels(PixelController<RGB_ORDER, 8, 0xFF>&pixels){
      uint32_t i = 0;
      while (pixels.has(1)){
        uint8_t r = pixels.loadAndScale0();
        uint8_t g = pixels.loadAndScale1();
        uint8_t b = pixels.loadAndScale2();
        pocto->setPixel(i++, r, g, b);
        pixels.stepDithering();
        pixels.advanceData();
      }
      pocto->show();
    }
};
CTeensy4Controller<GRB, WS2811_800kHz> *pcontroller;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void setup(){
  randomSeed(analogRead(2));
  Serial.begin(115200);
  octo.begin();
  pcontroller = new CTeensy4Controller<GRB, WS2811_800kHz>(&octo);
  FastLED.addLeds(pcontroller, leds, numPins * ledsPerPin);

  Wire.begin(8);                // join i2c bus with address #8
  Wire.onReceive((void (*)(int))receiveData); // register incoming data
  Wire.onRequest(sendData); //register a request for data
  Serial.begin(9600);           // start serial for output debugging

  sortInit();
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB(((float)i / NUM_LEDS) * 0, ((float)i / NUM_LEDS) * NUM_LEDS, ((float)i / NUM_LEDS) * 255); //G R B
  }
  pathing();
}
#define NUM_STRIPS 1

int currentLED = 3;

int apple = 10;
int length = 1;
bool snakePos[NUM_LEDS]; 

void loop() {
//    for(int i = 0; i < NUM_LEDS; i++){
//     leds[i] = BLUE; //G
// }
//     FastLED.show();
   //pathing();
   if(state == 0){
    distance();
   }
 //  snake();
  // gambling();
  // strobe();
  // sort();
  // FastLED.show();
  // newPattern();
  // for(int i = 0; i < NUM_LEDS; i++){
  //   leds[i] = CRGB(((float)i / NUM_LEDS) * 0, ((float)i / NUM_LEDS) * 16, ((float)i / NUM_LEDS) * 255); //G R B
  // }
  delay(100);
}

void clearLEDS(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB(0, 0, 0); //G R B
  }
}

void solidLEDS(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB(20, 20, 255); //G R B
  }
}

void strobe(){
  currentLED++;
  if(currentLED % 2){
    for(int i = 0; i < NUM_LEDS; i++){
      leds[i] = CRGB(255, 255, 255); //G R B
    }
  }else{
    clearLEDS();
  }
}

int smoothWrap(int a, int b){
  int distance = abs(a - b);
  distance = min(distance, NUM_LEDS - distance); // Handling wrap-around
  return distance;
}

void sortInit(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB(((float)i / NUM_LEDS) * 0, ((float)i / NUM_LEDS) * NUM_LEDS, ((float)i / NUM_LEDS) * 255); //G R B
  }
  bool shuffledTemp[NUM_LEDS];
  for(int i = 0; i < NUM_LEDS; i++){
    shuffledTemp[i] = false;
  }
  int iter = 0;
  for(int i = 0; i < NUM_LEDS; i++){
    resetRand:
    if(iter++ > 1000){
      break;
    }
    int rand = (int)random(0, NUM_LEDS);
    if(shuffledTemp[rand] == true){
      goto resetRand;
    }else{
      CRGB temp = leds[i];
      leds[i] = leds[rand];
      leds[rand] = temp;
      shuffledTemp[i] = true;
      shuffledTemp[rand] = true;
    }
  }
}
void sort(){
  CRGB temp;
  for(int i = 0; i < NUM_LEDS - 1; i++){
    if(leds[i].b > leds[i + 1].b){
      temp = leds[i];
      leds[i] = leds[i + 1];
      leds[i + 1] = temp;
      break;
    }else if(i == NUM_LEDS - 2){ // succeeded in sort
      sortInit();
      delay(100);
    }
  }
  FastLED.show();
}

void distance(){
  clearLEDS();
  currentLED++;
  for(int i = 0; i < NUM_LEDS; i++){
    int distance = smoothWrap(i, currentLED);
    int brightness;
    if(distance != 0){
      brightness = 255 - (((float)distance / 4) * 255);
    }else{
      brightness = 255;
    }
    if(brightness < 0){
        brightness = 0;
    }
    leds[i] = CRGB(0, 0, brightness); 
  }
  FastLED.show();
  if(currentLED >= NUM_LEDS){ // this stutters for some reason
    currentLED = 0;
  }
}

int calculateBrightness(int x){
  return (255 - abs(x * (float)255 / NUM_LEDS));
}

void snake(){
  clearLEDS();
  if(currentLED == apple){
    apple = random(1, 10);
    length++;
  }
  currentLED--;
  if(currentLED < 0){
    currentLED = NUM_LEDS;
  }
  // apple gets overriten by snake
  leds[apple] = CRGB(0, 255, 0); //G R B
  for(int i = currentLED; i < currentLED + length; i++){
    if(i > NUM_LEDS){
      break;
    }
    int distance = abs(i - currentLED);
    int brightness =  255 - (((float)distance / length) * 255);
    if(brightness < 0){
        brightness = 0;
    }
    if(brightness > 255){
        brightness = 255;
    }
    if(i < NUM_LEDS){
      leds[i] = CRGB(brightness, 0, 0); //G R B
    }
  }
  FastLED.show();
}


void gambling(){
  if((int)random(0, 1000) == 7){
    for(int i = 0; i < NUM_LEDS; i++){
      leds[i] = CRGB(255, 0, 0);
    }
  }else{
    clearLEDS();
  }
  FastLED.show();
}

void pathing(){
  currentLED += 1;
  if(currentLED >= NUM_LEDS or currentLED < 0){
    currentLED = 0;
  }
  // Fade in
  for(int i = 0; i < NUM_LEDS; i++){
    int distanceFromLED = smoothWrap(i, currentLED);
    int brightness = calculateBrightness(distanceFromLED);
    brightness -= 50; // makes colors cooler, more blue
    if(brightness < 0){
      brightness = 0;
    }
    leds[i] = CRGB((255 - brightness), 0, brightness); // G R B
  }
  FastLED.show();
}

void gotNote(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = GREEN;
    //leds[i] = CRGB(70, 255, 0); // G R B
  }
  FastLED.show();
}



// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveData()
{
  String str;
  while (Wire.available()) {
    char c = Wire.read();
    str += c;
  }
    Serial.println(str);
  if(str == "Data"){
    pathing();
    state = 1;
  }
  if(str == "Solid"){
    solidLEDS();
    state = 1;
  }
  if(str == "Note"){
    gotNote();
    state = 1;
  }
}

// function that executes whenever data is requested from master
// this function is registered as an event, see setup()
void sendData() // idk legacy stuff
{
  uint8_t buff[1];
  buff[0]= 255;
  Wire.write(buff[0]);
}