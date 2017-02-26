package FileIO;
import java.io.*;
public class FileReaderWriter {

   public static void writeFile(String fileName, String n) throws IOException{
      File file = new File(fileName);
      
      // creates the file
      file.createNewFile();
      
      // creates a FileWriter Object
      FileWriter writer = new FileWriter(file); 

      writer.write(n);
      writer.flush();
      writer.close();
   }

    public static String readFileString(String fileName) throws IOException,FileNotFoundException{
        String output = "";
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                output = output + line + "\n";
            }
        }
        return output;
    }
}
