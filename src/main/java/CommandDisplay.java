/* This file scans every file in the src/java path and writes the public callable functions to a file in build/generated
called AccessibleCommands.jtxt. 
.jtxt is a custom extention for java txt files as defined in .vscode/settings.json. It just makes them have silly colors
The build gradle calls this java file every build. It costs ~35 ms every build.
This could be optimized by keeping the file open, writing the avalible commands to a string and then one write but it's not worth it.
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
// This is a build-time file, this should NEVER be called any other time
public class CommandDisplay {
  public static void main(String[] args) {
    // Check if the correct number of command-line arguments is provided
    if (args.length != 1) {
      System.out.println("Usage: CommandDisplay <sourceDirectory>");
      System.exit(1);
    }

    // Get the source directory from command-line arguments
    String sourceDirectory = args[0];

    // List functions 
    createFile();
    
    try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory), FileVisitOption.FOLLOW_LINKS)) { // check every file in dir
      paths
        .filter(Files::isRegularFile) // filter 
        .filter(path -> path.toString().endsWith(".java"))
        .filter(path -> !path.toString().contains("CommandDisplay")) // don't print this file
        .filter(path -> !path.toString().contains("Constants")) // don't constants have to accessible functions
        .forEach(path -> {
          writeLineToFile(path.toString());
          printFile(path.toFile());
          writeLineToFile(""); // new line (because \n is inserted every time)
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  /** Creates or erases contents of AccessibleCommands.jtxt */
  private static void createFile() {
    try {
      File myObj = new File("build/generated/AccessibleCommands.jtxt");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        new FileOutputStream(myObj).close(); // delete contents
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  /** Writes one string to AccessibleCommands.jtxt
   * @param str 
   * The string that is written to the file 
  */
  private static void writeLineToFile(String str) {
    // str = str.replaceAll("\\s",""); // remove whitespace
    str = str.replaceAll("\\{",""); // remove {
    try {
      FileWriter myWriter = new FileWriter("build/generated/AccessibleCommands.jtxt", true);
      myWriter.write(str + '\n');
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  /** Prints the contents of the file to AccessibleCommands.jtxt with formatting to trim and make it consistent
   * @param fileToPrint 
   * The file that is read to print 
  */
  private static void printFile(File fileToPrint) {
    String fileName = fileToPrint.getName();
    fileName = fileName.replaceAll("\\.java",""); // remove .java
    fileName = fileName.toLowerCase();
    try (BufferedReader br = new BufferedReader(new FileReader(fileToPrint))) {
      String line;
      List<String> commentedFunctions  = new ArrayList<>();

      while ((line = br.readLine()) != null) {
        String editedLine = line.toLowerCase();
        if(editedLine.contains("public") && editedLine.contains("{") && editedLine.contains("(") && !editedLine.contains("}") 
        && !editedLine.contains(fileName) && !editedLine.contains("periodic")){
          // if it is a comment
          if(editedLine.trim().charAt(0) == '/' && editedLine.trim().charAt(1) == '/'){
            commentedFunctions.add(line);
          }else{
            // normal case
            line = line.replaceAll("\\{","");
            line = line.trim();
            writeLineToFile("  " + line + ";");
          }
        }
      }
      // write the saved commented functions for the end
      for (String function : commentedFunctions) {
        function = function.replaceAll("\\{","");
        function = function.trim();
        writeLineToFile(function);
      }
    }catch(Exception e) {

    }
  }
}
