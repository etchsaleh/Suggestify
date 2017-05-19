/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suggestify;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import static suggestify.HomeViewController.loginStage;
import static suggestify.HomeViewController.spotify;

/**
 * FXML Controller class
 *
 * @author HeshamSaleh
 */
public class LoginWindowController implements Initializable {

    @FXML
    private WebView loginWebView;
    
    public static boolean loginSuccessful = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final WebEngine webEngine = loginWebView.getEngine();
        webEngine.load(spotify.authorizer.getPermissionURL());
        webEngine.getLoadWorker().stateProperty().addListener(
        new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    if(spotify.authorizer.isDeny(webEngine.getLocation())) { 
                        System.out.println("Current Location " + webEngine.getLocation());
                        System.out.println("User refused access");
                        loginSuccessful = false;
                        loginStage.close();

                    } else if (spotify.authorizer.isRedirectAddress(webEngine.getLocation())) {
                        System.out.println("Current Location " + webEngine.getLocation());
                        spotify.authorizer.obtainCredentials(webEngine.getLocation());
                        loginSuccessful = true;
                        loginStage.hide();
                        //loginStage.close();
                    }
                }
            }
        });
    }    
    
}
