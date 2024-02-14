def addGettersAndSetters(output_file, variableData):
  
  for variable in variableData:
    variable_name = variable[0]
    variable_value = variable[2]
    variable_type = variable[1]
    capitalizedType = variable_type.capitalize()
    output_file.write("\n  public void set" + variable_name + "(" + variable_type + " value) {\n")
    output_file.write("    m_nte_" + variable_name + ".set" + capitalizedType + "(value);\n")
    output_file.write("  }\n")
    output_file.write("  public " + variable_type + " get" + variable_name + "() {\n")
    output_file.write("    return m_nte_" + variable_name + ".get" + capitalizedType + "(" + variable_value + ");\n")
    output_file.write("  }\n")

  return 0