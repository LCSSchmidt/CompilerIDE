package notfalsecompiler.ide;

import notfalsecompiler.filehandler.DirectoryHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import notfalsecompiler.compiler.LexicalError;
import notfalsecompiler.compiler.Lexico;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Semantico;
import notfalsecompiler.compiler.Sintatico;
import notfalsecompiler.compiler.SyntaticError;

public class IdeController implements Initializable {

    @FXML
    TextArea txtBoxCode;
    
    @FXML
    TextArea txtConsoleLog;

    public void saveFile() {
        DirectoryHandler directoryHan = new DirectoryHandler();
        String dirToSave = "";
        File file = null;
        FileWriter fr = null;
        boolean fileSaved = false;
        try {
            dirToSave = directoryHan.getSaveDir();
            if (dirToSave != null) {
                file = new File(dirToSave);
                fr = new FileWriter(file);

                fr.write(this.txtBoxCode.getText());
                fileSaved = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fileSaved = false;
        } finally {
            Alert alert = null;
            try {
                if (fr != null) {
                    fr.close();
                }
                if (fileSaved) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("File saved successfully.");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Error when saving file.");
                }
                alert.setTitle("File Handler");
                alert.setHeaderText(null);
                alert.showAndWait();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
//                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadFile() {
        DirectoryHandler directoryHan = new DirectoryHandler();
        File file = null;
        BufferedReader br = null;
        String dirToLoad = "";
        boolean fileLoad = false;
        String fileContent = "";

        try {
            dirToLoad = directoryHan.getLoadDir();
            if (dirToLoad != null) {
                file = new File(dirToLoad);
                br = new BufferedReader(new FileReader(file));
                
                String st;
                while ((st = br.readLine()) != null) {
                    fileContent = fileContent.concat(st);
                }

                fileLoad = true;
                this.txtBoxCode.setText(fileContent);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Alert alert = null;
            try {

                if (br != null) {
                    br.close();
                }
                if(!fileLoad) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Error reading file.");
                }
                
                alert.setTitle("File Reader");
                alert.showAndWait();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void compile() {
        Lexico lexico = new Lexico();
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();
        
        try {
            lexico.setInput(this.txtBoxCode.getText());
            sintatico.parse(lexico, semantico);
        } catch (LexicalError e) {
            System.out.println("Error: " + e.getMessage());
            txtConsoleLog.setText(e.getMessage());
        } catch (SyntaticError e) {
            System.out.println("Error: " + e.getMessage());
            txtConsoleLog.setText(e.getMessage());
        } catch (SemanticError e) {
            System.out.println("Error: " + e.getMessage());
            txtConsoleLog.setText(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            txtConsoleLog.setText(e.getMessage());
        }
    }
    
    private void disableItem() {
        this.txtConsoleLog.setDisable(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.disableItem();
    }

}
