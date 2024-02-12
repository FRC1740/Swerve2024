import os
import re

source_directory = "src/main/java/frc/Board/SourceSB/"
output_directory = "src/main/java/frc/Board/OutputSB/"

def main():
  # check all .sb files in the source directory
  for root, dirs, files in os.walk(source_directory):
    for file in files:
      if file.endswith(".sb"):
        input_filepath = os.path.join(root, file)
        outputted_filename = os.path.splitext(file)[0]
        output_filepath = os.path.join(output_directory, outputted_filename + ".java")
        create_file(output_filepath) # create java files
        parse_file(input_filepath, output_filepath) # parse .sb files and write to .java files

def create_file(file_path):
  try:
    with open(file_path, "w") as file:
      print("File created:", file_path)
  except IOError:
    print("An error occurred while creating the file:", file_path)

def parse_file(input_file, output_file):
  addingImports = False
  readingEntryParams = False
  skipLine = False
  fileStart = False
  previousName = ""
  variables = []
  variables.append("ShuffleboardTab m_sbt_DriverTab;")
  try:
    with open(input_file, "r") as input_file, open(output_file, "w") as output_file:
      # add default imports
      addDefaultImports(output_file)
      
      for line in input_file:
        # This is where the file gets converted to a .java file
        if line.startswith("--"):
          skipLine = True
        elif line.__contains__("imports"):
          addingImports = True
        elif not line.__contains__("imports"): 
          if line.__contains__("="): #Because you set it a value
            readingEntryParams = True

        # output_file.write(line + "\n") # debugf

        if skipLine:
          skipLine = False
        elif addingImports:
          if addImports(output_file, line) == 0:
            addingImports = False
            fileStart = True # done with imports and ready to start writing the class

        elif fileStart:
          addClass(output_file, output_file.name)
          fileStart = False

        elif readingEntryParams: 
          if line.__contains__("."):
            name = line.split(".")[0]
          else:
            name = line.split(" ")[0]
          # output_file.write("\nname :" + name + "\n") # debugf
          # output_file.write("\nprev :" + previousName + "\n") # debugf
          
          if name != previousName and previousName != "":
            endOfEntry(output_file)
            readingEntryParams = False
            previousName = ""
            name = ""
          elif addEntry(output_file, line) == 0:
            readingEntryParams = False

          previousName = name
        
      # done
      endOfFile(output_file)
      addVariables(output_file, variables)

  except IOError:
    print("An error occurred while parsing files.")

def addImports(output_file, line):
  if line.__contains__("{"): # quit because it's first line
    return 1 
  if line.__contains__("}"):
    return 0
  
  line = line.replace("\"", "")
  line = line.replace(",", "")
  line = line.replace(";", "")
  line = line.replace("\n", "")
  output_file.write("import" + line + ";\n")
  return 1

def addEntry(output_file, line):
  output_file
  #     m_sbt_DriverTab.add(m_Field)
  #    .withSize(4, 2).withPosition(0, 0);
  if line.__contains__(".size"): # look in {0, 0}
    match = re.search(r'\{(\d*\.?\d+), (\d*\.?\d+)\}', line) # find decimal numbers in the line
    size_value_1 = match.group(1)
    size_value_2 = match.group(2)
    output_file.write(".withSize(" + size_value_1 + ", "+ size_value_2 + ")")
    return 1
  
  if line.__contains__(".pos"): # look in {0, 0}
    match = re.search(r'\{(\d*\.?\d+), (\d*\.?\d+)\}', line) # find decimal numbers in the line
    size_value_1 = match.group(1)
    size_value_2 = match.group(2)
    output_file.write(".withPosition(" + size_value_1 + ", "+ size_value_2 + ")")
    return 1
  
  if line.__contains__(".type"):
      name = line.split(" ")[0]
      # remove the .type
      name = name.replace(".type", "")

      output_file.write("\n    m_nte_" + name + " = m_sbt_DriverTab.add(m_" + name + ")\n      ")
      return 1
  
  if line.__contains__(".addCommand"):
    #TODO: implement addCommand
    return 1
  
  # default, setting the type of the entry
  # HasNote = true
  # m_nte_HasNote = m_sbt_DriverTab.add("HasNote", false)
  name = line.split(" ")[0]
  value = line.split(" ")[2] 
  output_file.write("\n    m_nte_" + name + " = m_sbt_DriverTab.add(\"" + name + "\", " + value + ")\n      ")
  return 1

def addVariables(output_file, variables):
  output_file.write("\n\n")
  
  for variable in variables:
    output_file.write(variable + "\n")
  
  output_file.write("\n}")

def endOfEntry(output_file):
  output_file.write(".getEntry();\n")

def addClass(output_file, filename):
  name = filename.split("/")[-1].split(".")[0]
  output_file.write("\n")
  output_file.write("public class " + name + " {\n\n")
  output_file.write("  private static " + name + " instance = null;\n\n")
  output_file.write("  private " + name + "() {\n")
  output_file.write("    initShuffleboardTab();\n")
  output_file.write("  }\n\n")

  output_file.write("  public static " + name + " getInstance() {\n")
  output_file.write("    if(instance == null) {\n")
  output_file.write("      instance = new " + name + "();\n")
  output_file.write("    }\n")
  output_file.write("    return instance;\n")
  output_file.write("  }\n")
  output_file.write("  private void initShuffleboardTab() {\n")
  output_file.write("    // Create and get reference to SB tab\n")
  output_file.write("    m_sbt_" + name + " = Shuffleboard.getTab(\"" + name + "\");\n")

def endOfFile(output_file):
  output_file.write("  }")

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

if __name__ == "__main__":
  main()
