package notfalsecompiler.filehandler;

import static notfalsecompiler.CompilerIDE.primarySageSt;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class DirectoryHandler {

    public String getSaveDir() throws Exception {
        FileChooser fileCh = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        File file = null;

        fileCh.getExtensionFilters().add(extFilter);
        try {
            file = fileCh.showSaveDialog(primarySageSt);
            return file.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public String getLoadDir() throws Exception {
        FileChooser fileCh = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        File file = null;

        fileCh.getExtensionFilters().add(extFilter);
        try {
            file = fileCh.showOpenDialog(primarySageSt);
            return file.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    public String getOsProtocol() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();

        if (isWindows(os)) {
            return "file:///";
        } else if (isUnix(os)) {
            return "file://";
        } else {
            throw new Exception("Type of os not found");
        }
    }

    public static boolean isWindows(String os) {
        return os.contains("win");
    }

    public static boolean isMac(String os) {
        return os.contains("mac");

    }

    public static boolean isUnix(String os) {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }
}
