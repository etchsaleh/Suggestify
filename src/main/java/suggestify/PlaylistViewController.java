/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suggestify;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import static suggestify.HomeViewController.spotify;

/**
 * FXML Controller class
 *
 * @author HeshamSaleh
 */
public class PlaylistViewController implements Initializable {

    @FXML
    private ImageView spotifyLogo;
    @FXML
    private Label playlistLbl;
    @FXML
    private VBox titleVbox;
    @FXML
    private TextField titleTextField;
    @FXML
    private Button doneBtn;
    @FXML
    private VBox playlistCreatedVbox;
    @FXML
    private Button viewPlaylistBtn;
    @FXML
    private VBox playlistDetailsVbox;
    @FXML
    private Button newPlaylistBtn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox numOfTracksVbox;
    @FXML
    private Button numDecBtn;
    @FXML
    private Label trackNumLbl;
    @FXML
    private Button numIncBtn;
    
    private String playlistID;
    public static int numOfTracksPerArtist = 3;
    
    FadeTransition playlistVboxFade = new FadeTransition();
    TranslateTransition playlistVboxTrans = new TranslateTransition();
    
    FadeTransition numOfTracksVboxFade = new FadeTransition();
    TranslateTransition numOfTracksVboxTrans = new TranslateTransition();
    
    FadeTransition titleFade = new FadeTransition();
    FadeTransition doneBtnFade = new FadeTransition();
    FadeTransition spotifyFade = new FadeTransition();
    FadeTransition playlistFade = new FadeTransition();
    
    TranslateTransition titleTrans = new TranslateTransition();
    TranslateTransition doneBtnTrans = new TranslateTransition();
    TranslateTransition spotifyTrans = new TranslateTransition();
    TranslateTransition playlistTrans = new TranslateTransition();
    
    ParallelTransition pt = new ParallelTransition(titleFade,titleTrans,doneBtnFade,doneBtnTrans,spotifyFade,spotifyTrans,playlistFade,playlistTrans,numOfTracksVboxFade,numOfTracksVboxTrans);
    ParallelTransition ptPlaylistCreated = new ParallelTransition(titleFade,titleTrans,doneBtnFade,doneBtnTrans,spotifyFade,spotifyTrans,playlistFade,playlistTrans,playlistVboxFade,playlistVboxTrans,numOfTracksVboxFade,numOfTracksVboxTrans);


    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initAnimation();
    }    
    
    @FXML
    private void donePressed(MouseEvent event) {
        
        if(!titleTextField.getText().isEmpty()) {
            //Remove username field.
            playlistID = spotify.createAndAddToPlaylist(SecondaryViewController.suggestedArtists,titleTextField.getText());
            doneBtn.setDisable(true);
            
            viewPlaylistBtn.setText(titleTextField.getText());
            playlistCreatedAnimation();
        }
    }
    
    private void initAnimation() {
        
        titleTrans.setNode(titleVbox);
        spotifyTrans.setNode(spotifyLogo);
        playlistTrans.setNode(playlistLbl);
        doneBtnTrans.setNode(doneBtn);
        numOfTracksVboxTrans.setNode(numOfTracksVbox);
        
        titleFade.setNode(titleVbox);
        spotifyFade.setNode(spotifyLogo);
        playlistFade.setNode(playlistLbl);
        doneBtnFade.setNode(doneBtn);
        numOfTracksVboxFade.setNode(numOfTracksVbox);
        
        titleFade.setDuration(Duration.seconds(0.5));
        doneBtnFade.setDuration(Duration.seconds(0.5));
        spotifyFade.setDuration(Duration.seconds(0.5));
        playlistFade.setDuration(Duration.seconds(0.5));
        numOfTracksVboxFade.setDuration(Duration.seconds(0.5));
        
        titleFade.setFromValue(0); titleFade.setToValue(1);
        doneBtnFade.setFromValue(0); doneBtnFade.setToValue(1);
        spotifyFade.setFromValue(0); spotifyFade.setToValue(1);
        playlistFade.setFromValue(0); playlistFade.setToValue(1);
        numOfTracksVboxFade.setFromValue(0); numOfTracksVboxFade.setToValue(1);
        
        titleTrans.setFromX(500); titleTrans.setToX(0);
        numOfTracksVboxTrans.setFromY(300); numOfTracksVboxTrans.setToY(0);
        doneBtnTrans.setFromY(300); doneBtnTrans.setToY(0);
        spotifyTrans.setFromY(-200); spotifyTrans.setToY(0);
        playlistTrans.setFromY(-200); playlistTrans.setToY(0);
        
        pt.play();
    }
    
    private void playlistCreatedAnimation() {
        
        playlistVboxFade.setNode(playlistCreatedVbox);
        playlistVboxTrans.setNode(playlistCreatedVbox);
        
        playlistVboxFade.setDuration(Duration.seconds(0.5));
        playlistVboxFade.setFromValue(0); playlistVboxFade.setToValue(1);
        
        playlistVboxTrans.setFromY(-500); playlistVboxTrans.setToY(0);
        
        titleTrans.setNode(titleVbox);
        spotifyTrans.setNode(spotifyLogo);
        playlistTrans.setNode(playlistLbl);
        doneBtnTrans.setNode(doneBtn);
        numOfTracksVboxTrans.setNode(numOfTracksVbox);
        
        titleFade.setNode(titleVbox);
        spotifyFade.setNode(spotifyLogo);
        playlistFade.setNode(playlistLbl);
        doneBtnFade.setNode(doneBtn);
        numOfTracksVboxFade.setNode(numOfTracksVbox);
        
        titleFade.setDuration(Duration.seconds(0.5));
        doneBtnFade.setDuration(Duration.seconds(0.5));
        spotifyFade.setDuration(Duration.seconds(0.5));
        playlistFade.setDuration(Duration.seconds(0.5));
        numOfTracksVboxFade.setDuration(Duration.seconds(0.5));
        
        titleFade.setFromValue(1); titleFade.setToValue(0);
        doneBtnFade.setFromValue(1); doneBtnFade.setToValue(0);
        spotifyFade.setFromValue(1); spotifyFade.setToValue(0);
        playlistFade.setFromValue(1); playlistFade.setToValue(0);
        numOfTracksVboxFade.setFromValue(1); numOfTracksVboxFade.setToValue(0);
        
        titleTrans.setFromX(0); titleTrans.setToX(500);
        numOfTracksVboxTrans.setFromY(0); numOfTracksVboxTrans.setToY(300);
        doneBtnTrans.setFromY(0); doneBtnTrans.setToY(300);
        spotifyTrans.setFromY(0); spotifyTrans.setToY(-200);
        playlistTrans.setFromY(0); playlistTrans.setToY(-200);
        
        ptPlaylistCreated.play();
        
        playlistDetailsVbox.toBack();
    }

    @FXML
    private void viewPlaylistOnSpotify(MouseEvent event) {
        //Open playlist link on external browser.
        System.out.println("Link open");
        String playlistLink = playlistID;
        
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(playlistLink));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + playlistLink);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    @FXML
    private void viewPlaylistHoverExit(MouseEvent event) {
        viewPlaylistBtn.setScaleX(1);
        viewPlaylistBtn.setScaleY(1);
    }

    @FXML
    private void viewPlaylistHover(MouseEvent event) {
        viewPlaylistBtn.setScaleX(1.1);
        viewPlaylistBtn.setScaleY(1.1);
    }

    @FXML
    private void newPlaylist(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(PlaylistViewController.this.getClass().getResource("/fxml/SecondaryView.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void numDecPressed(MouseEvent event) {
        if(numOfTracksPerArtist > 1) {
            numOfTracksPerArtist--;
            trackNumLbl.setText(String.valueOf(numOfTracksPerArtist));
        }
    }

    @FXML
    private void numIncPressed(MouseEvent event) {
        if(numOfTracksPerArtist < 5) {
            numOfTracksPerArtist++;
            trackNumLbl.setText(String.valueOf(numOfTracksPerArtist));
        }
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
}
