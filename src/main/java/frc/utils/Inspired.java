package frc.utils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Inspired extends SubsystemBase{

  String unprintedChars = "";
  long currentTime = System.currentTimeMillis();

  public Inspired() {
    slowPrintAppend("Inspired by the best! (3461)");
  }

  public void randomPrint() {
    int random = (int) (Math.random() * 1);
    switch(random) {
      case 0:
        dooryPrint();
        break;
      case 1:
        // youGotThis();
        break;
      case 2:
        // keepGoing();
        break;
      default:
        break;
    }
  }

  private void somethingNew() {
    slowPrintAppend("Hey, it's something new.");
    slowPrintAppend("You're going to kill it out there. I just know it!");
    slowPrintAppend("Just... try not to break anything important.");
  }

  private void dooryPrint() {
    // slowPrintAppend("............................");
    // slowPrintAppend(".....-------------------....");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend("....|...................|...");
    // slowPrintAppend(".....-------------------....");
    // slowPrintAppend("............................");
    slowPrintAppend("          ");     
    slowPrintAppend("............................");
    slowPrintAppend("....._-----------------_....");
    slowPrintAppend("....{0|...............|0}...");
    slowPrintAppend("....|.|.....=====.....|.|...");
    slowPrintAppend("....|.|......| |......|.|...");
    slowPrintAppend("....|.\\......| |....../.|...");
    slowPrintAppend("....|........| |........|...");
    slowPrintAppend("....|........| |........|...");
    slowPrintAppend("....|........| |........|...");
    slowPrintAppend("....|...................|...");
    slowPrintAppend(".....-------------------....");
    slowPrintAppend("............1740.............");
  }

  public void slowPrintAppend(String message) {
    unprintedChars += message + "\n";
  }

  public void slowPrint(String message) {
    if(currentTime + 0 < System.currentTimeMillis()){
      if(message.length() > 0){
        System.out.print(message.charAt(0));
        currentTime = System.currentTimeMillis();
        unprintedChars = message.substring(1);
      }
    }
  }
  @Override
  public void periodic() {
    slowPrint(unprintedChars);
  }
}
