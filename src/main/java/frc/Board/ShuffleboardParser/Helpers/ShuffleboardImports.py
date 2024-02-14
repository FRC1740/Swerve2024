# this is a simple function that scans for whether some basic imports are used in the file
# This does not check for user defined imports
def getImports(input_file, output_file):
  usedImports = []
  for line in input_file:
    line = line.strip() 
    # Skip comments, non-variable lines, and imports
    if line.startswith("--") or "=" not in line or "imports" in line:
      continue
    # remove comments and anything after them
    commentPos = line.find("--")
    if commentPos != -1:
      line = line[:commentPos]

    if line.__contains__("widget"):
      usedImports.append("import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;")
    if line.__contains__("properties"):
      usedImports.append("import java.util.Map;")

  usedImports = list(set(usedImports)) # remove duplicates
      
  input_file.seek(0) # reset file pointer to the beginning of the file

  customImports = getCustomImports(input_file, output_file)
  for imports in customImports:
    usedImports.append(imports)

  return usedImports

def getCustomImports(input_file, output_file):
  #TODO: add shorthand imports so HornContants expands to frc.robot.HornConstants
  imports = []
  readingImports = False
  for line in input_file:
    if line.__contains__("}"):
      readingImports = False

    if readingImports:
      line = line.strip()
      line = line.replace("\"", "") # remove quotes
      line = line.replace("import", "") # remove import
      line = line.replace(";", "") # remove semicolon if it exists
      line = line.replace(",", "") # remove comma if it exists
      line = line.strip()
      imports.append("import " + line + ";\n")

    if line.__contains__("import"):
      readingImports = True
  # reset file pointer to the beginning of the file
  input_file.seek(0)

  return imports