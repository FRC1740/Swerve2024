// This loads the .sb code into java shuffleboard code.
// This makes generic types way easier to create especially for hacking something together.
// This is just a test
package frc.Board;
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

// with properties should always import map
public class ShuffleboardLoader {
  static final String sourceDirectory = "src/main/java/frc/Board/SourceSB/";
  static final String outputDirectory = "src/main/java/frc/Board/OutputSB/";
  public static void main(String[] args) {
    try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory), FileVisitOption.FOLLOW_LINKS)) { // check every file in dir
      paths
        // .filter(Files::isRegularFile) // filter 
        .filter(path -> path.toString().endsWith(".sb"))
        .forEach(path -> {
          createFile(path.getFileName().toString().replaceAll("\\.sb",""));
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  private static void createFile(String name) {
    try {
      File myObj = new File(outputDirectory + name + ".java");
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

  private static void writeLineToFile(String file, String str) {
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
}
