package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.Board.DriverTab;
import frc.Board.SensorTab;
import frc.robot.commands.basic.NoteRumble;
// import frc.robot.RobotShared;
import frc.robot.constants.SensorConstants;

public class BreakbeamSensorSubsystem extends SubsystemBase{
  /* example usage:
    if(m_SensorSubsystem,getSensorValue(1) == true){
      //do something
    }  
  */
  private DigitalInput[] m_breakBeamSensors;
  private SensorTab m_sensorTab;
  private DriverTab m_driverTab;
  private boolean hasNote = false;
  // private RobotShared m_robotShared = RobotShared.getInstance();
  // private CommandXboxController m_driverController = m_robotShared.getDriverController();
  // private double timeOfLastNote = System.currentTimeMillis() + SensorConstants.kTimeToRumbleController + 1; // When enabled, it will not be true

  public BreakbeamSensorSubsystem() {
    m_breakBeamSensors = new DigitalInput[SensorConstants.kDIOPorts]; // ten ports total
    m_sensorTab = SensorTab.getInstance();
    m_driverTab = DriverTab.getInstance();
    getSensorValue(1);
  }

  @Override
  public void periodic() {
    boolean previousNoteState = hasNote;

    hasNote = false;
    // Loop over every port and update them if they exist
    for(int index = 0; index < SensorConstants.kDIOPorts; index++) {
      if(m_sensorTab.sensorStatePortExists(index)){
        m_sensorTab.setSensorStatePort(m_breakBeamSensors[index].get(), index);
        if(m_breakBeamSensors[index].get() == false){
          hasNote = true;
        }
      }
    }

    if(previousNoteState != hasNote){
      new NoteRumble().schedule();
    }

    // if(timeOfLastNote > System.currentTimeMillis() - SensorConstants.kTimeToRumbleController){
    //   // the HID is the controller, commandXbox can't trust us with rumble apparently and it wont extend so I have to get HID
    //   m_driverController.getHID().setRumble(RumbleType.kLeftRumble, SensorConstants.kRumbleStrength);
    //   m_driverController.getHID().setRumble(RumbleType.kRightRumble, SensorConstants.kRumbleStrength);
    // }else{
    //   m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0);
    //   m_driverController.getHID().setRumble(RumbleType.kRightRumble, 0);
    // }

    m_driverTab.setHasNote(hasNote);
  }
  /**
   * Returns true if the sensors can see eachother, meaning there is nothing there, 
   * otherwise it returns false, and there is something there,
   * if it does not exist, it is created. 
   * In the event the sensors are not hooked up correctly, it will return false when they should see eachother
   * @param port The port to check 
  */
  public boolean getSensorValue(int port){
    // This checks a value in an array on the sensor tab, it does not affect performance in a meaningful way
    if(!m_sensorTab.sensorStatePortExists(port)){
      createSensor(port);
    }
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Returns true if the sensors can see eachother, meaning there is nothing there, 
   * otherwise it returns false, and there is something there,
   * Returns the value at a port, if it does not exist, it is *<b>NOT</b>* created
   * This method is not recommened, use getSensorValue instead, because if it does not exist it will cause a crash and a memory leak
   * @param port The port to check 
  */
  @Deprecated
  public boolean getSensorValueUnsafe(int port){
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Creates a new Senor with DigitalInput(port); and posts it to the shuffleboard
   * This is not a safe method, improper usage can crash the robot so it is private
   * You need to make sure port does not already exist via sensorStatePortExists
   * @param port The port to make 
  */
  private void createSensor(int port){
    m_breakBeamSensors[port] = new DigitalInput(port);
    m_sensorTab.addSensorStatePort(port);
  }
}
