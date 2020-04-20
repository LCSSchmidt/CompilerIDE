package notfalsecompiler.ide;

import notfalsecompiler.filehandler.DirectoryHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import notfalsecompiler.ide.LexicalError;
import notfalsecompiler.ide.Lexico;
import notfalsecompiler.ide.SemanticError;
import notfalsecompiler.ide.Semantico;
import notfalsecompiler.ide.Sintatico;
import notfalsecompiler.ide.SyntaticError;

public class IdeController implements Initializable {

    @FXML
    TextArea txtBoxCode;
    
    @FXML
    TextArea txtConsoleLog;
    @FXML
    private Button btnCompile;
    @FXML
    private Button saveCode;
    @FXML
    private Button loadFile;

    @FXML
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

    @FXML
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

    @FXML
    public void compile() {
        System.out.println(this.txtBoxCode.getText());
        
        Lexico lexico = new Lexico(this.txtBoxCode.getText());
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();
        
        try {
            //lexico.setInput(code);
            sintatico.parse(lexico, semantico);
            System.out.println("Deu bom");
        } catch (LexicalError | SyntaticError | SemanticError ex) {
            this.txtConsoleLog.setText(ex.getMessage());
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
