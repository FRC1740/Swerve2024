#include <i2c_device.h>
#include <i2c_driver.h>
#include <i2c_driver_wire.h>
#include <i2c_register_slave.h>
#include <FastLED.h>

#define LED_PIN 7
#define NUM_LEDS 16
#define NUM_STRIPS 1

CRGB leds[NUM_LEDS];

int currentLED = 3;

int apple = 10;
int length = 1;
bool snakePos[NUM_LEDS]; 

void setup() {
  randomSeed(analogRead(2));
  FastLED.addLeds<NUM_STRIPS, WS2812, LED_PIN>(leds, NUM_LEDS);

  Wire.begin(8);                // join i2c bus with address #8
  Wire.onReceive((void (*)(int))receiveData); // register incoming data
  Wire.onRequest(sendData); //register a request for data
  Serial.begin(9600);           // start serial for output debugging

  sortInit();
}

void loop() {
  // pathing();
  // distance();
  // snake();
  gambling();
  // strobe();
  // sort();
  // FastLED.show();
  // newPattern();
  delay(100);
}

void clearLEDS(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB(0, 0, 0); //G R B
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
    leds[i] = CRGB(((float)i / NUM_LEDS) * 0, ((float)i / NUM_LEDS) * 16, ((float)i / NUM_LEDS) * 255); //G R B
  }
  bool shuffledTemp[16];
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



// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveData()
{
  String str;
  while (Wire.available()) {
    char c = Wire.read();
    str += c;
  }
  if(str == "Data"){
    pathing();
    // Serial.println(str);
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