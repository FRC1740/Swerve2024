import os
import ShuffleboardParserHelpers.ShuffleboardTemplate as ShuffleboardTemplate
import ShuffleboardParserHelpers.ShuffleboardVariables as ShuffleboardVariables

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
  try:
    with open(input_file, "r") as input_file, open(output_file, "w") as output_file:
      # reads variables from .sb files and returns an array with data
      # [0] is name [1] is type [2] is value [3] is size [4] is positon
      # size and position are tuples, they are arrays
      variableData = ShuffleboardVariables.getVariableData(input_file) 
      fileName = output_file.name.split("/")[-1].split(".")[0]
      # add default imports
      ShuffleboardTemplate.addDefaultImports(output_file)
      addCustomImports(input_file, output_file)
      ShuffleboardTemplate.addClass(output_file, fileName)
      ShuffleboardVariables.addVariables(output_file, fileName, variableData)
      ShuffleboardVariables.addVariablesDeclarations(output_file, variableData, fileName)
      output_file.write("}\n")
  except IOError:
    print("An error occurred while parsing files.")

def addCustomImports(input_file, output_file):
  #TODO: add shorthand imports so HornContants expands to frc.robot.HornConstants
  readingImports = False
  for line in input_file:
    if line.__contains__("}"):
      readingImports = False

    if readingImports:
      line = line.strip()
      line = line.replace("\"", "") # remove quotes
      line = line.replace("import", "") # remove import
      line = line.replace(";", "") # remove semicolon if it exists
      line = line.strip()
      output_file.write("import " + line + ";\n")

    if line.__contains__("import"):
      readingImports = True
  # reset file pointer to the beginning of the file
  input_file.seek(0)

    

if __name__ == "__main__":
  main()
