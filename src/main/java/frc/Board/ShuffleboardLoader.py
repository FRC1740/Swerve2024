import os

source_directory = "src/main/java/frc/Board/SourceSB/"
output_directory = "src/main/java/frc/Board/OutputSB/"

def main():
  for root, dirs, files in os.walk(source_directory):
    for file in files:
      if file.endswith(".sb"):
        input_filepath = os.path.join(root, file)
        outputted_filename = os.path.splitext(file)[0]
        output_filepath = os.path.join(output_directory, outputted_filename + ".java")
        create_file(output_filepath)
        parse_file(input_filepath, output_filepath)

def create_file(file_path):
  try:
    with open(file_path, "w") as file:
      print("File created:", file_path)
  except IOError:
    print("An error occurred while creating the file:", file_path)

def parse_file(input_file, output_file):
  try:
    with open(input_file, "r") as input_file, open(output_file, "w") as output_file:
      for line in input_file:
        # This is where the file gets converted to a .java file
        output_file.write(line)
  except IOError:
    print("An error occurred while parsing files.")

if __name__ == "__main__":
  main()
