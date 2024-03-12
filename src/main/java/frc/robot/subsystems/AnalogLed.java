package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogOutput;

public class AnalogLed {
  private AnalogOutput ledR;
  private AnalogOutput ledG;
  private AnalogOutput ledB;

  public AnalogLed(int port, int port2, int port3) {
    ledR = new AnalogOutput(port);
    ledG = new AnalogOutput(port2);
    ledB = new AnalogOutput(port3);
  }

  public void set(double r, double g, double b) {
    ledR.setVoltage(r);
    ledG.setVoltage(g);
    ledB.setVoltage(b);
  }
}
