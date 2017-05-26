package FileIO;

import java.io.File;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class FileManager {
    public static File[][] getImportFileList(){
        File files[][] = new File[3][];
        files[0] = new File("Import Folder/DBS").listFiles();
        files[1] = new File("Import Folder/DBS Credit").listFiles();
        files[2] = new File("Import Folder/OCBC").listFiles();
        return files;
    }

    public static void moveFileToArchive(File f,String month,String year,String Bank) {
        f.renameTo(new File("Statement Archives/"+month+" "+year+" "+Bank+".pdf"));
    }

    public static void moveFileToArchive(File f,String date,String Bank) {
        f.renameTo(new File("Statement Archives/"+date+" "+Bank+".pdf"));
    }
}
