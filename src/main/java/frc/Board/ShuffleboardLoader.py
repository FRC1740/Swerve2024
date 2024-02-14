import os
import ShuffleboardParserHelpers.ShuffleboardTemplate as ShuffleboardTemplate
import ShuffleboardParserHelpers.ShuffleboardVariables as ShuffleboardVariables
import ShuffleboardParserHelpers.ShuffleboardImports as ShuffleboardImports
import ShuffleboardParserHelpers.ShuffleboardFunctions as ShuffleboardFunctions

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
      # [5] is widget [6] is wiget properties (array of 4)
      # size and position are tuples, they are arrays
      variableData = ShuffleboardVariables.getVariableData(input_file) 
      fileName = output_file.name.split("/")[-1].split(".")[0]

      imports = ShuffleboardImports.getImports(input_file, output_file) # get used imports and user defined imports
      ShuffleboardTemplate.addImports(output_file, imports) # adds imports as well as default

      ShuffleboardTemplate.addMainClass(output_file, fileName)
      ShuffleboardVariables.addVariablesDeclarations(output_file, variableData, fileName)

      ShuffleboardTemplate.addInit(output_file, fileName)
      ShuffleboardVariables.addVariables(output_file, fileName, variableData)

      # getters and setters time :sunglasses:
      ShuffleboardFunctions.addGettersAndSetters(output_file, variableData)

      output_file.write("}\n")
  except IOError:
    print("An error occurred while parsing files.")

    

if __name__ == "__main__":
  main()
