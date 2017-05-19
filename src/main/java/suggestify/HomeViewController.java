/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suggestify;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HeshamSaleh
 */
public class HomeViewController implements Initializable {
    
    public static Spotify spotify = new Spotify(true, "etchsaleh");
    public static IndexedRecommender rec = new IndexedRecommender("similar-artists.tsv");
    
   
    @FXML
    private Label discoverLbl;
    @FXML
    private VBox SuggestifyLogo;
    @FXML
    private Button loginBtn;
    @FXML
    private AnchorPane rootPane;
    
    public static String[] artists = {"Craig David", "Coldplay", "Eminem", "D12", "Shakira", "Radiohead", "Linkin Park", "Maroon 5", "Celine Dion", "50 Cent", "Tupac", "Snoop Dogg", "Metallica"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void btnHoverExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1); btn.setScaleY(1);
        btn.setStyle("-fx-background-color: #1ed761; -fx-background-radius: 50; -fx-border-radius: 50; -fx-border-width: 1; -fx-border-color:  #15af4d");
    }

    @FXML
    private void btnHover(MouseEvent event) {
        
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.1); btn.setScaleY(1.1);
        btn.setStyle("-fx-background-color: #11f967; -fx-background-radius: 50; -fx-border-radius: 50; -fx-border-width: 1; -fx-border-color:  #15af4d");
    }
    
    public static Stage loginStage;
    
    @FXML
    private void loginPressed(MouseEvent event) throws IOException {
        AnchorPane webPane = FXMLLoader.load(HomeViewController.this.getClass().getResource("/fxml/LoginWindow.fxml"));
        loginStage = new Stage();
        loginStage.setTitle("Spotify® - Login");
        Scene scene = new Scene(webPane);
        loginStage.setScene(scene);
        loginStage.showAndWait();
        
        if(LoginWindowController.loginSuccessful) {
            AnchorPane pane = FXMLLoader.load(HomeViewController.this.getClass().getResource("/fxml/SecondaryView.fxml"));
            rootPane.getChildren().setAll(pane); 
        }
    }
}
