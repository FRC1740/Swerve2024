# ShuffleboardParserHelpers/ShuffleboardTemplate.py
# This file is the basic template for the shuffleboard java files.
# These are just a lot so I made a new file for them.
def addDefaultImports(output_file):
  output_file.write("// Copyright (c) FIRST and other WPILib contributors.\n" +
  "// Open Source Software; you can modify and/or share it under the terms of\n" +
  "// the WPILib BSD license file in the root directory of this project.\n" + 
  "package frc.Board.OutputSB;\n\n" + 
  "import java.util.Map;\n" +
  "import edu.wpi.first.networktables.GenericEntry;\n" +
  "import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;\n" +
  "import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;\n" +
  "import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;\n\n")
  return 0

def addClass(output_file, filename):
  output_file.write("\n" + 
  "public class " + filename + " {\n\n" +
  "  private static " + filename + " instance = null;\n\n" +
  "  private " + filename + "() {\n" +
  "    initShuffleboardTab();\n" +
  "  }\n" +
  "\n" +
  "  public static " + filename + " getInstance() {\n" +
  "    if(instance == null) {\n" +
  "      instance = new " + filename + "();\n" +
  "    }\n" +
  "    return instance;\n" +
  "  }\n" +
  "\n" +
  "  private void initShuffleboardTab() {\n" +
  "    // Create and get reference to SB tab\n" +
  "    m_sbt_" + filename + " = Shuffleboard.getTab(\"" + filename + "\");\n")
  return 0