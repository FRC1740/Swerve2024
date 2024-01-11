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

public class CommandDisplay {
  public static void main(String[] args) {
    // Check if the correct number of command-line arguments is provided
    if (args.length != 1) {
      System.out.println("Usage: CommandDisplay <sourceDirectory>");
      System.exit(1);
    }

    // Get the source directory from command-line arguments
    String sourceDirectory = args[0];

    // Add your logic to list functions or perform other tasks
    createFile();
    try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory), FileVisitOption.FOLLOW_LINKS)) {
      paths
        .filter(Files::isRegularFile)
        .filter(path -> path.toString().endsWith(".java"))
        .filter(path -> !path.toString().contains("CommandDisplay"))
        .forEach(path -> {
          // System.out.println(path);
          writeLineToFile(path.toString());
          printFile(path.toFile());
          writeLineToFile(""); // new line
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

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
          if(editedLine.trim().charAt(0) == '/' && editedLine.trim().charAt(1) == '/'){
            commentedFunctions.add(line);
          }else{
            line = line.replaceAll("\\{","");
            line = line.trim();
            writeLineToFile("  " + line + ";");
          }
        }
      }
      for (String function : commentedFunctions) {
        function = function.replaceAll("\\{","");
        function = function.trim();
        writeLineToFile(function);
      }
    }catch(Exception e) {

    }
  }
}
