package notfalsecompiler;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompilerIDE extends Application {
    public static Stage primarySageSt;
    
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        Scene scene;
        try {
            System.out.println(System.getProperty("user.dir"));
            loader.setLocation(new URL("file:///" + System.getProperty("user.dir") + "/src/notfalsecompiler/ide/ide_nova.fxml"));
    //        loader.setLocation(getClass().getResource("/fxml/ClienteCadastro.fxml"));
            root = loader.load();
            scene = new Scene(root);
            primaryStage.setTitle("Compiler");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            primarySageSt = primaryStage;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
