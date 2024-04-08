package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedSubsystem extends SubsystemBase{
	// communicate with leds via I2C
	public static I2C arduino;

	public Timer timer;
	
	public LedSubsystem() {
		arduino = new I2C(I2C.Port.kOnboard, 8);
		timer = new Timer();
		timer.start();
	}

	@Override
	public void periodic() {
		// System.out.println("led");
		if (timer.get() > .1){
		// System.out.println("timer");
		// SendLedCommand();

		}
	}
    
	public void SendLedCommandGotNote() {
		byte[] sendData = "Note".getBytes(); // should read from shuffleboard
		byte[] receiveData = new byte[5];
		arduino.transaction(sendData, sendData.length, receiveData, 5);
		// arduino.writeBulk(sendData, sendData.length);
		System.out.println("Recieved " + (receiveData[0] & 0xff));
		timer.reset();
	}
	public void SendLedCommandPathing() {
		byte[] sendData = "Data".getBytes(); // should read from shuffleboard
		byte[] receiveData = new byte[5];
		arduino.transaction(sendData, sendData.length, receiveData, 5);
		// arduino.writeBulk(sendData, sendData.length);
		// System.out.println("Recieved " + (receiveData[0] & 0xff));
		timer.reset();
	}
}
