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
  noGetEntry = False
  previousName = ""
  variables = []
  try:
    with open(input_file, "r") as input_file, open(output_file, "w") as output_file:
      # add default imports
      addDefaultImports(output_file)
      
      for line in input_file:
        line = line.replace("\n", "")
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
          addClass(output_file, output_file.name, variables)
          fileStart = False

        elif readingEntryParams: 
          # check that .'s first occurance does not come after a = sign
          if line.__contains__(".") and line.index(".") < line.index("="):
            name = line.split(".")[0]
          else:
            name = line.split(" ")[0]
          # output_file.write("\nname :" + name + "\n") # debugf
          # output_file.write("\nprev :" + previousName + "\n") # debugf
          if line.__contains__(".type"):
            noGetEntry = True
          if name != previousName and previousName != "":
            endOfEntry(output_file, noGetEntry)
            noGetEntry = False
            readingEntryParams = False
            previousName = ""
            name = ""
          elif addEntry(output_file, line, variables) == 0:
            readingEntryParams = False
            noGetEntry = False

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

def addEntry(output_file, line, variables):
  fileName = output_file.name.split("/")[-1].split(".")[0]
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
  
  if line.__contains__(".type"): # special types don't have generic entries
      name = line.split(" ")[0]
      # remove the .type
      name = name.replace(".type", "")

      output_file.write("\n    m_sbt_" + fileName + ".add(m_" + name + ")\n      ")

      # variables.append("GenericEntry m_nte_" + name + ";")
      # Field2d m_Field = new Field2d();
      type = line.split("= ")[1]
      variables.append(type + " m_" + name + " = new " + type + "();")
      return 1
  
  if line.__contains__(".addCommand"):
    #TODO: implement addCommand
    return 1
  
  if line.__contains__(".widget"):
    #TODO: implement addCommand
    return 1
  
  if line.__contains__(".properties"): # Broken
    # .withProperties(Map.of("min", 0, "max", 1))
    name = line.split(" ")[0]
    name = name.replace(".properties", "")
    output = ".withProperties(Map.of(" + line.split("{")[1] + "))"
    output = output.replace("}", "") # remove the last }
    # wrap min and max in quotes
    parts = output.split("min")
    output = parts[0] + '"' + "min" + '"' + parts[1]
    parts = output.split("max")
    output = parts[0] + '"' + "max" + '"' + parts[1]
    
    output_file.write(output)
    return 1
  
  name = line.split(" ")[0]
  value = line.split(" ")[2] 
  output_file.write("\n    m_nte_" + name + " = m_sbt_" + fileName + ".add(\"" + name + "\", " + value + ")\n      ")

  variables.append("GenericEntry m_nte_" + name + ";")
  return 1

def addVariables(output_file, variables):
  output_file.write("\n\n")
  
  for variable in variables:
    output_file.write("  " + variable + "\n")
  
  output_file.write("\n}")


def endOfEntry(output_file, noGetEntry):
  # .type variables don't have a .getEntry() method
  # if not output_file.name.__contains__("Field2d"):
  if not noGetEntry:
    output_file.write(".getEntry();\n")
  else:
    output_file.write(";\n")




def addClass(output_file, filename, variables):
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
  
  variables.append("ShuffleboardTab m_sbt_" + name + ";")

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
