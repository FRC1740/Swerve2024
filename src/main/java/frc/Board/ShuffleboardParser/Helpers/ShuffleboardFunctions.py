def addGettersAndSetters(output_file, variableData):
  
  for variable in variableData:
    variable_name = variable[0]
    variable_value = variable[2]
    variable_type = variable[1]
    variable_isComplexWidget = variable[7]
    variable_declaration = variable[8]

    if variable_isComplexWidget or variable_declaration: # complex widgets don't have getters and setters
      continue
    capitalizedType = variable_type.capitalize()
    capitalizedName = variable_name[0].capitalize() + variable_name[1:]
    
    output_file.write("\n  public void set" + capitalizedName + "(" + variable_type + " value) {\n")
    output_file.write("    m_nte_" + variable_name + ".set" + capitalizedType + "(value);\n")
    output_file.write("  }\n")
    output_file.write("  public " + variable_type + " get" + capitalizedName + "() {\n")
    output_file.write("    return m_nte_" + variable_name + ".get" + capitalizedType + "(" + variable_value + ");\n")
    output_file.write("  }\n")

  return 0

def addCustomFunctions(input_file, output_file):
  custom_functions = getCustomFunctions(input_file)
  for line in custom_functions:
    output_file.write(line)
  return 0

def getCustomFunctions(input_file):
  reading_function = False
  customFunctionLines = []
  for line in input_file:
    # skip comments and empty lines
    if line.startswith("--") or "imports" in line:
      continue
    # remove comments and anything after them

    commentPos = line.find("--")
    if commentPos != -1:
      line = line[:commentPos]

    if line.strip().startswith(")"): # can do this because it will always be on a new line
      reading_function = False

    if reading_function:
      customFunctionLines.append(line)

    if line.__contains__("addCommand"):
      reading_function = True

  input_file.seek(0) # reset file pointer to the beginning of the file
  return customFunctionLines

