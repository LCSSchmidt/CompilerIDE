package notfalsecompiler.ide;

import notfalsecompiler.filehandler.DirectoryHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import notfalsecompiler.compiler.LexicalError;
import notfalsecompiler.compiler.Lexico;
import notfalsecompiler.compiler.SemanticError;
import notfalsecompiler.compiler.Semantico;
import notfalsecompiler.compiler.Sintatico;
import notfalsecompiler.compiler.SyntaticError;

public class IdeController implements Initializable {

    @FXML
    private TextArea txtBoxCode;
    @FXML
    private TextArea errorConsole;
    @FXML
    private TextArea warningConsole;
    @FXML
    private Button btnCompile;
    @FXML
    private Button saveCode;
    @FXML
    private Button loadFile;
    @FXML
    private TableView symbolTable;
    @FXML
    private Tab errorConsoleLabel;
    @FXML
    private Tab warningConsoleLabel;

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
                if (!fileLoad) {
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

    private void clearTableColumns() {
        this.symbolTable.getItems().clear();
    }

    private void getWarningMessages(List<String> warnings) {
        String next;
        String warningText = "";

        for (Iterator<String> iterator = warnings.iterator(); iterator.hasNext();) {
            next = iterator.next();
            warningText += next + "\n";
        }

        this.warningConsoleLabel.setText("Warnings (" + warnings.size() + ")");
        this.warningConsole.setText(warningText);
    }

    @FXML
    public void compile() {
        this.errorConsole.setText("");

        Lexico lexico = new Lexico(this.txtBoxCode.getText());
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();

        try {
            this.clearTableColumns();
            sintatico.parse(lexico, semantico);
            this.symbolTable.getItems().addAll(semantico.symbols);
        } catch (LexicalError | SyntaticError | SemanticError ex) {
            this.errorConsole.setText(ex.getMessage());
        } catch (Exception e) {
            this.errorConsole.setText(e.getMessage());
        }

        getWarningMessages(semantico.warnings);
    }

    public void createTableColumns() {
        TableColumn idColumn = new TableColumn("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn tipoColumn = new TableColumn("tipo");
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn iniColumn = new TableColumn("ini");
        iniColumn.setCellValueFactory(new PropertyValueFactory<>("ini"));

        TableColumn usadaColumn = new TableColumn("usada");
        usadaColumn.setCellValueFactory(new PropertyValueFactory<>("usada"));

        TableColumn escopoColumn = new TableColumn("escopo");
        escopoColumn.setCellValueFactory(new PropertyValueFactory<>("escopo"));

        TableColumn paramColumn = new TableColumn("param");
        paramColumn.setCellValueFactory(new PropertyValueFactory<>("param"));

        TableColumn posColumn = new TableColumn("pos");
        posColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));

        TableColumn vetColumn = new TableColumn("vet");
        vetColumn.setCellValueFactory(new PropertyValueFactory<>("vet"));

        TableColumn matrizColumn = new TableColumn("matriz");
        matrizColumn.setCellValueFactory(new PropertyValueFactory<>("matriz"));

        TableColumn refColumn = new TableColumn("ref");
        refColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));

        TableColumn funcColumn = new TableColumn("func");
        funcColumn.setCellValueFactory(new PropertyValueFactory<>("func"));

        this.symbolTable.getColumns().addAll(idColumn, tipoColumn, iniColumn, usadaColumn, escopoColumn, paramColumn, posColumn, vetColumn, matrizColumn, refColumn, funcColumn);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.errorConsole.setEditable(false);
        this.warningConsole.setEditable(false);
        createTableColumns();

    }

}
