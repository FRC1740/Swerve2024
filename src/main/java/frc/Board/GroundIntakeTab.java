package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class GroundIntakeTab {

  ShuffleboardTab m_sbt_GroundIntakeTab;

  GenericEntry m_nte_GroundIntakeDefaultEnabled;

  private static GroundIntakeTab instance = null;

  private GroundIntakeTab() {
    initShuffleboardTab();
  }

  public static GroundIntakeTab getInstance() {
    if(instance == null) {
      instance = new GroundIntakeTab();
    }
    return instance;
  }
  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_GroundIntakeTab = Shuffleboard.getTab("GroundIntake");

    m_nte_GroundIntakeDefaultEnabled = m_sbt_GroundIntakeTab.add("GroundIntakeDefaultEnabled", false)
    .withSize(1, 1).getEntry();
  }

  public void setGroundIntakeDefaultEnabled(boolean GroundIntakeDefaultEnabled) {
    m_nte_GroundIntakeDefaultEnabled.setBoolean(GroundIntakeDefaultEnabled);
  }

  public boolean getGroundIntakeDefaultEnabled() {
    return m_nte_GroundIntakeDefaultEnabled.getBoolean(false);
  }
}
