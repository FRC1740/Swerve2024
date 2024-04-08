package frc.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.DriveTrainTab;
import frc.Networking.LimelightTable;

public class Inspired extends SubsystemBase{

  String unprintedChars = "";
  long currentTime = System.currentTimeMillis();
  int timesRanLoop = 0;

  public Inspired() {
    // slowPrintAppend("Inspired by the best! (3461)                       ");
    slowPrintAppend("#####################");
  }

  public void randomPrint() {
    //seed random
    int random = (int) (Math.random() * 3000);
    timesRanLoop++;
    if(timesRanLoop > 3) {
      outOfMemory();
    }
    switch(random) {
      case 0:
        dooryPrint();
        break;
      case 1:
        somethingNew();
        break;
      case 2:
        limelightSeen();
        break;
      case 3:
        goFast();
        break;
      case 4:
        driveTeam();
        break;
      case 5:
        preMatch();
        break;
      case 6:
        robotFindsKitten();
        break;
      case 7:
        isAutoSet();
        break;
      default:
        error();
        break;
    }
  }

  private void outOfMemory() {
    slowPrintAppend("ERROR: OUT OF MEMORY");
    slowPrintAppend("EARLY TERMINATION ACTIVATED");
  }

  private void error() {
    slowPrintAppend("ERROR: INSPIRATION NOT FOUND");
    slowPrintAppend("ERROR: INSPIRATION NOT FOUND");
    slowPrintAppend("ERROR: INSPIRATION NOT FOUND");
    slowPrintAppend("Reloading inspiration...");
    // # = delay
    // String randomDelay = "";#
    // int random = (int) (Math.random() * 10);
    // for(int i = 0; i < random; i++){
    //   randomDelay += "#";
    // }
    
    // slowPrintAppend("-##--##############-#############---#---#-" + randomDelay + "###########----####################-########--######----###-#########-");
    randomPrint();
  }

  private void isAutoSet() {
    slowPrintAppend("Did you set the auto yet?");
    slowPrintAppend("#####################");
    slowPrintAppend(SmartDashboard.getData("Auto Mode").toString());
    slowPrintAppend("#####################");
    slowPrintAppend("Clearly not.");
    slowPrintAppend("#########################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################");
    if(MatchType.Practice == DriverStation.getMatchType()){
      slowPrintAppend("You should probably flip the path.");
      slowPrintAppend("#####################");
      slowPrintAppend("Even if it is just practice.");
    }
  }

  public void robotFindsKitten() {
    slowPrintAppend("I found a kitten!");
    slowPrintAppend("Wait, no, that's just an orange ring.");
    slowPrintAppend("I'm not sure how I got those two confused.");
    slowPrintAppend("Wait, hold on, ############### A NOTE!!");
    slowPrintAppend("That's worth atleast 10 kittens.");
    slowPrintAppend("##########Possibly 11.##################");
    slowPrintAppend("I'm going to go get some more notes.");
  }

  private void preMatch() {
    slowPrintAppend("Why on earth would you not just get the notes before the match?");
    slowPrintAppend("They're just sitting there");
    slowPrintAppend("If they could hurry up and enable me, I'll get them all");
    // Delays
    slowPrintAppend("#########################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################");
    
    slowPrintAppend("  |   |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("  | X |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("  | X |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("  | X |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("O | X |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   |  ");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("O | X |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   | X");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("O | X | O");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("  |   | X");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("O | X | O");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X |   | X");
    slowPrintAppend("#########################################################" +
    "####################################################");
    slowPrintAppend("O | X | O");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O |  ");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O | X");
    slowPrintAppend("#########################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################");
    slowPrintAppend("O | X | O");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | X | X");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O | X");
    slowPrintAppend("#########################################################");
    slowPrintAppend("O | X | O");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X---X---X");
    slowPrintAppend("--+---+--");
    slowPrintAppend("X | O | X");
    slowPrintAppend("#########################################################");
    slowPrintAppend("This delay is unnacceptable");
  }

  private void driveTeam() {
    slowPrintAppend("Oh man, I sure am thirsty");
    slowPrintAppend("################################");
    slowPrintAppend("Maybe if drive team wins, they'll get me a drink");
    slowPrintAppend("##############");
    slowPrintAppend("I mean, they don't drink anything themselves, so the odds of them getting me anything are pretty low");
    slowPrintAppend("############");
    slowPrintAppend("If (hydration = hydration for me) = winning, then hydration = winning");
  }

  private void goFast() {
    slowPrintAppend("I need more speed");
    slowPrintAppend("Wow, " + DriveTrainTab.getInstance().getMaxDrivingSpeed() + "? That's it? We need atleast 8 MPS");
    slowPrintAppend("##############");
    slowPrintAppend("It might be dangerous,");
    slowPrintAppend("####");
    slowPrintAppend("but you do want to win, right?");
    slowPrintAppend("I mean,  .    .       .");
    slowPrintAppend("############");
    slowPrintAppend("Have you considered that if you never stop going fast, nothing breaks?");
    slowPrintAppend("#########################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################" +
    "####################################################");
    slowPrintAppend("I'm just saying, if you never stop, you never have to start again");
    slowPrintAppend("#########################################################");
    slowPrintAppend("So, we should just let the autonomous run forever, right?");
    slowPrintAppend("We might get maybe 3 notes.");
    slowPrintAppend("#########################################################");
  }

  private void limelightSeen() {
    slowPrintAppend("Have you seen what I've seen?");
    slowPrintAppend("It's a lot of walls, and sometimes lime walls.");
    if(LimelightTable.getInstance().getBotPose()[0] != 0){
      slowPrintAppend("I've seen the bot pose, and it's not great.");
      slowPrintAppend("#########################################################");
      slowPrintAppend("Why did we let Sean set it up?");
      slowPrintAppend("#########################################################");
      if(LimelightTable.getInstance().getTx() > 0) {// 0 is center positive is right side tag pos
        slowPrintAppend("Move me a bit to the left"); // tag is to the right, move left
      }else if(LimelightTable.getInstance().getTx() < 0) {
        slowPrintAppend("Move me a bit to the right"); // tag is to the left, move right
      }else{
        slowPrintAppend("Actually###############. My bad Sean, you did great.");
      }
    }
  }

  private void somethingNew() {
    slowPrintAppend("Hey, it's something new.");
    slowPrintAppend("You're going to kill it out there. I just know it!");
    slowPrintAppend("Just... try not to break anything important.");
  }

  private void dooryPrint() {
    slowPrintAppend("          ");     
    slowPrintAppend("............................");
    slowPrintAppend("........-------------.......");
    slowPrintAppend(".......|.............|......");
    slowPrintAppend(".......|.............|......");
    slowPrintAppend(".......|.............|......");
    slowPrintAppend(".......|.............|......");
    slowPrintAppend(".......|.............|......");
    slowPrintAppend("........-------------.......");
    slowPrintAppend("..........|...|..|..........");
    slowPrintAppend("............................");
    slowPrintAppend("....._-----------------_....");
    slowPrintAppend("....{0|...............|0}...");
    slowPrintAppend("....|.|.....=====.....|.|...");
    slowPrintAppend("....|.|......| |......|.|...");
    slowPrintAppend("....|.\\......| |....../.|...");
    slowPrintAppend("....|........| |........|...");
    slowPrintAppend("....|........| |........|...");
    slowPrintAppend("....|........|\"|........|...");
    slowPrintAppend("....|...................|...");
    slowPrintAppend(".....-------------------....");
    slowPrintAppend("............1740............");
  }

  public void clearPrintQueue() {
    unprintedChars = "";
    System.out.println("-");
  }

  public void slowPrintAppend(String message) {
    unprintedChars += message + "\n";
  }

  public void slowPrint(String message) {
    if(currentTime + 100 < System.currentTimeMillis()){
      if(message.length() > 0){
        if(message.charAt(0) == '#'){
          //delay
          currentTime = System.currentTimeMillis();
          unprintedChars = message.substring(1);
        }else{
          System.out.print(message.charAt(0));
          currentTime = System.currentTimeMillis();
          unprintedChars = message.substring(1);
        }
      }
    }
  }
  @Override
  public void periodic() {
    slowPrint(unprintedChars);
  }
}
