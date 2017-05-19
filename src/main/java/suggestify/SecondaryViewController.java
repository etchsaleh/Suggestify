/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suggestify;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author HeshamSaleh
 */
public class SecondaryViewController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addBtn;
    @FXML
    private VBox suggestifyLogo;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Label headlineLbl;
    @FXML
    private HBox hboxLogo;
    @FXML
    private ImageView spotifyLogo;
    @FXML
    private Button playlistBtn;
    @FXML
    private Label artistLbl1;
    @FXML
    private Label artistLbl2;
    @FXML
    private Label artistLbl3;
    @FXML
    private Label artistLbl4;
    @FXML
    private Label artistLbl5;
    @FXML
    private Button okBtn;
    @FXML
    private VBox artistList;
    @FXML
    private VBox okBtnVbox;
    @FXML
    private Label errorLbl;
    @FXML
    private Button backBtn;
    @FXML
    private VBox searchVbox;
    @FXML
    private VBox artistHboxVbox;
    @FXML
    private HBox artistHbox1;
    @FXML
    private Button xBtn1;
    @FXML
    private HBox artistHbox2;
    @FXML
    private Button xBtn2;
    @FXML
    private HBox artistHbox3;
    @FXML
    private Button xBtn3;
    @FXML
    private HBox artistHbox4;
    @FXML
    private Button xBtn4;
    @FXML
    private HBox artistHbox5;
    @FXML
    private Button xBtn5;
    
    //Animation
    ScaleTransition scaleSB = new ScaleTransition();
    ScaleTransition scaleAddBtn = new ScaleTransition();
    TranslateTransition transSB = new TranslateTransition();
    TranslateTransition transAddBtn = new TranslateTransition();
    TranslateTransition transSearchVbox = new TranslateTransition();
    TranslateTransition transLogo = new TranslateTransition();
    FadeTransition fadeSB = new FadeTransition();
    FadeTransition fadeSearchVbox = new FadeTransition();
    FadeTransition fadeAddBtn = new FadeTransition();
    FadeTransition okBtnFade = new FadeTransition();
    FadeTransition backBtnFade = new FadeTransition();
    FadeTransition artistListFade = new FadeTransition();
    TranslateTransition okBtnTrans = new TranslateTransition();
    TranslateTransition backBtnTrans = new TranslateTransition();
    TranslateTransition artistListTrans = new TranslateTransition();
    FadeTransition errLbl = new FadeTransition();
            
    ParallelTransition showList = new ParallelTransition(okBtnFade,artistListFade,okBtnTrans,artistListTrans,backBtnFade,backBtnTrans,transSearchVbox,fadeSearchVbox);
    
    ParallelTransition pt = new ParallelTransition(scaleSB,scaleAddBtn,transSB,transAddBtn,transLogo,fadeSB,fadeAddBtn);
    
    public static ArrayList<String> suggestedArtists = null;
    private ArrayList<String> addedArtists = new ArrayList<String>();
    private List<String> artistNames  = HomeViewController.rec.getArtists();
    private ArrayList<Node> components = new ArrayList<Node>();
    int count = 4;
    int hboxCount = 0;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initAssets(); //Animation
        TextFields.bindAutoCompletion(searchBar, artistNames);
    }   

    @FXML
    private void addBtnPressed(MouseEvent event) {
        String artistName = searchBar.getText();
        searchBar.setText("");

        if(artistNames.contains(artistName) && !addedArtists.contains(artistName) && count != -1) {
            
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(-1);
            
            Button b = new Button("X");
            b.setAlignment(Pos.CENTER);
            b.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #000000;");
            b.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
            b.setMinWidth(20);
            b.setMinHeight(20);
            
            Label l = new Label(artistName);
            l.setFont(Font.font("Proxima Nova Rg", 12));
            l.setAlignment(Pos.CENTER);
            l.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #000000;");
            l.setMinWidth(90);
            l.setMinHeight(27);
            
            b.setOnMouseEntered(e-> {
                b.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF");
                l.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF");
                hb.setScaleX(1.02); hb.setScaleY(1.02);
            });
            
            b.setOnMouseExited(e-> {
                b.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #000000; -fx-border-color: #000000");
                l.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #000000; -fx-border-color: #000000");
                hb.setScaleX(1); hb.setScaleY(1);
            });

            b.setOnAction(e->{
                flowPane.getChildren().remove(hb); // remove by Object reference
                components.remove(hb);
                if(addedArtists.contains(l.getText()))
                    addedArtists.remove(l.getText());
                count++;
                hboxCount--;
            });
            
            hb.getChildren().addAll(b,l);
            components.add(hb);

            flowPane.setAlignment(Pos.CENTER);
            flowPane.getChildren().add(components.get(hboxCount));
            addedArtists.add(artistName);
            count--;
            hboxCount++;   
        }
    }

    @FXML
    private void logoHoverExit(MouseEvent event) {
        hboxLogo.setScaleX(1);
        hboxLogo.setScaleY(1);
        playlistBtn.setTextFill(Color.WHITE);
    }

    @FXML
    private void logoHover(MouseEvent event) {
        hboxLogo.setScaleX(1.1);
        hboxLogo.setScaleY(1.1);
        playlistBtn.setTextFill(Color.rgb(106,227,104));
    }

    private void artistLblHoverExit(MouseEvent event) {
        Label lb = ((Label) event.getSource());
        lb.setScaleX(1);
        lb.setScaleY(1);
        if(lb == artistLbl1)
            xBtn1.setOpacity(0);
        else if(lb == artistLbl2)
            xBtn2.setOpacity(0);
            else if(lb == artistLbl3)
            xBtn3.setOpacity(0);
        else if(lb == artistLbl4)
            xBtn4.setOpacity(0);
        else if(lb == artistLbl5)
            xBtn5.setOpacity(0);
    }

    private void artistLblHover(MouseEvent event) {
        Label lb = ((Label) event.getSource());
        lb.setScaleX(1.05);
        lb.setScaleY(1.05);
        if(lb == artistLbl1)
            xBtn1.setOpacity(1);
        else if(lb == artistLbl2)
            xBtn2.setOpacity(1);
        else if(lb == artistLbl3)
            xBtn3.setOpacity(1);
        else if(lb == artistLbl4)
            xBtn4.setOpacity(1);
        else if(lb == artistLbl5)
            xBtn5.setOpacity(1);
    }
    
    @FXML
    private void createPlaylistPressed(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(SecondaryViewController.this.getClass().getResource("/fxml/PlaylistView.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    //Suggest Artists Button.
    
    @FXML
    private void okPressed(MouseEvent event) {
        if(!addedArtists.isEmpty()) {
            suggestedArtists = new ArrayList<>();
            int firstArtists = Math.max(5-addedArtists.size()+1, 1);
            for(int i = 0 ; i < addedArtists.size(); i++)
            {
                suggestedArtists.addAll(HomeViewController.rec.getSimilarTo(addedArtists.get(i), i==0? firstArtists : 1));
            }
            
            artistLbl1.setText(suggestedArtists.get(0));
            artistLbl2.setText(suggestedArtists.get(1));
            artistLbl3.setText(suggestedArtists.get(2));
            artistLbl4.setText(suggestedArtists.get(3));
            artistLbl5.setText(suggestedArtists.get(4));
            
            okBtnVbox.toBack();
            artistList.toFront();
            okBtnAnimation();
            
        } else {
            errorLblAnimation();
        }
    }
    
    private void okBtnAnimation() {
        okBtnFade.setNode(okBtnVbox);
        artistListFade.setNode(artistList);
        okBtnTrans.setNode(okBtnVbox);
        artistListTrans.setNode(artistList);
        backBtnFade.setNode(backBtn);
        backBtnTrans.setNode(backBtn);
        fadeSearchVbox.setNode(searchVbox);
        transSearchVbox.setNode(searchVbox);
        
        fadeSearchVbox.setFromValue(1); fadeSearchVbox.setToValue(0);
        fadeSearchVbox.setDuration(Duration.seconds(0.5));
        
        okBtnFade.setFromValue(1); okBtnFade.setToValue(0);
        okBtnFade.setDuration(Duration.seconds(0.5));
        
        backBtnFade.setFromValue(0); backBtnFade.setToValue(1);
        backBtnFade.setDuration(Duration.seconds(0.5));
        
        artistListFade.setFromValue(0); artistListFade.setToValue(1);
        artistListFade.setDuration(Duration.seconds(0.5));
        
        transSearchVbox.setToY(-100);
        backBtnTrans.setFromY(-100); backBtnTrans.setToY(0);
        
        okBtnTrans.setToX(500);
        artistListTrans.setFromX(-500); artistListTrans.setToX(0);
        
        showList.play();
        
    }
    
    //Initial scene animation.
    private void initAssets() {
        //Set Node
        scaleSB.setNode(searchBar);
        scaleAddBtn.setNode(addBtn);
        transSB.setNode(searchBar);
        transAddBtn.setNode(addBtn);
        transLogo.setNode(suggestifyLogo);
        fadeSB.setNode(searchBar);
        fadeAddBtn.setNode(addBtn);
        
        scaleSB.setFromX(0.5); scaleSB.setFromY(0.5);
        scaleSB.setToX(1); scaleSB.setToY(1);
        
        scaleAddBtn.setFromX(0.5); scaleAddBtn.setFromY(0.5);
        scaleAddBtn.setToX(1); scaleAddBtn.setToY(1);
        
        transSB.setFromY(-200); transSB.setToY(0);
        transLogo.setFromX(-200); transLogo.setToX(0);
        transAddBtn.setFromX(200); transAddBtn.setToX(0);
        transSB.setDuration(Duration.seconds(0.4));
        transLogo.setDuration(Duration.seconds(0.4));
        transAddBtn.setDuration(Duration.seconds(0.4));
        
        fadeSB.setFromValue(0); fadeSB.setToValue(1);
        fadeSB.setDuration(Duration.seconds(0.4));
        
        fadeAddBtn.setFromValue(0); fadeAddBtn.setToValue(1);
        fadeAddBtn.setDuration(Duration.seconds(0.4));
        
        pt.play();
    }
    
    private void errorLblAnimation() {
        errLbl.setNode(errorLbl);
        errLbl.setDuration(Duration.seconds(0.1));
        errLbl.setToValue(1);
        errLbl.play();
    }

    @FXML
    private void hideErrorLbl(KeyEvent event) {
        errorLbl.setOpacity(0);
    }

    @FXML
    private void backBtnPressed(MouseEvent event) {
        okBtnVbox.toFront();
        artistList.toBack();
        backAnimation();
    }
    
    private void backAnimation() {
        okBtnFade.setNode(okBtnVbox);
        artistListFade.setNode(artistList);
        okBtnTrans.setNode(okBtnVbox);
        artistListTrans.setNode(artistList);
        backBtnFade.setNode(backBtn);
        backBtnTrans.setNode(backBtn);
        fadeSearchVbox.setNode(searchVbox);
        transSearchVbox.setNode(searchVbox);
        
        fadeSearchVbox.setFromValue(0); fadeSearchVbox.setToValue(1);
        fadeSearchVbox.setDuration(Duration.seconds(0.5));
        
        okBtnFade.setFromValue(0); okBtnFade.setToValue(1);
        okBtnFade.setDuration(Duration.seconds(0.5));
        
        backBtnFade.setFromValue(1); backBtnFade.setToValue(0);
        backBtnFade.setDuration(Duration.seconds(0.3));
        
        artistListFade.setFromValue(1); artistListFade.setToValue(0);
        artistListFade.setDuration(Duration.seconds(0.5));
        
        transSearchVbox.setToY(0);
        backBtnTrans.setFromY(0); backBtnTrans.setToY(-100);
        
        okBtnTrans.setToX(0);
        artistListTrans.setFromX(0); artistListTrans.setToX(-500);
        
        showList.play();
    }

    @FXML
    private void artistHboxHoverExit(MouseEvent event) {
        HBox hb = ((HBox) event.getSource());
        
        if(hb == artistHbox1) {
            artistLbl1.setScaleX(1);
            artistLbl1.setScaleY(1);
            xBtn1.setOpacity(0);
        } else if(hb == artistHbox2) {
            artistLbl2.setScaleX(1);
            artistLbl2.setScaleY(1);
            xBtn2.setOpacity(0);
        } else if(hb == artistHbox3) {
            artistLbl3.setScaleX(1);
            artistLbl3.setScaleY(1);
            xBtn3.setOpacity(0);
        } else if(hb == artistHbox4) {
            artistLbl4.setScaleX(1);
            artistLbl4.setScaleY(1);
            xBtn4.setOpacity(0);
        } else if(hb == artistHbox5) {
            artistLbl5.setScaleX(1);
            artistLbl5.setScaleY(1);
            xBtn5.setOpacity(0);
        }   
    }

    @FXML
    private void artistHboxHover(MouseEvent event) {
        HBox hb = ((HBox) event.getSource());
        
        if(hb == artistHbox1) {
            artistLbl1.setScaleX(1.05);
            artistLbl1.setScaleY(1.05);
            xBtn1.setOpacity(1);
        } else if(hb == artistHbox2) {
            artistLbl2.setScaleX(1.05);
            artistLbl2.setScaleY(1.05);
            xBtn2.setOpacity(1);
        } else if(hb == artistHbox3) {
            artistLbl3.setScaleX(1.05);
            artistLbl3.setScaleY(1.05);
            xBtn3.setOpacity(1);
        } else if(hb == artistHbox4) {
            artistLbl4.setScaleX(1.05);
            artistLbl4.setScaleY(1.05);
            xBtn4.setOpacity(1);
        } else if(hb == artistHbox5) {
            artistLbl5.setScaleX(1.05);
            artistLbl5.setScaleY(1.05);
            xBtn5.setOpacity(1);
        }      
    } 

    @FXML
    private void removeArtist(MouseEvent event) {
        Button b = ((Button) event.getSource());
        
        if(b == xBtn1) {
            suggestedArtists.remove(artistLbl1.getText());
            artistHboxVbox.getChildren().remove(artistHbox1);
        } else if(b == xBtn2) {
            suggestedArtists.remove(artistLbl2.getText());
            artistHboxVbox.getChildren().remove(artistHbox2);
        } else if(b == xBtn3) {
            suggestedArtists.remove(artistLbl3.getText());
            artistHboxVbox.getChildren().remove(artistHbox3);
        } else if(b == xBtn4) {
            suggestedArtists.remove(artistLbl4.getText());
            artistHboxVbox.getChildren().remove(artistHbox4);
        } else if(b == xBtn5) {
            suggestedArtists.remove(artistLbl5.getText());
            artistHboxVbox.getChildren().remove(artistHbox5);
        }
    }

    @FXML
    private void xBtnHoverExit(MouseEvent event) {
        Button b = ((Button) event.getSource());
        b.setTextFill(Color.BLACK);
        b.setStyle("-fx-background-color: TRANSPARENT; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-width: 1; -fx-border-color:  #000000");
    }

    @FXML
    private void xBtnHover(MouseEvent event) {
        Button b = ((Button) event.getSource());
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-background-color: TRANSPARENT; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-width: 1; -fx-border-color:  #FFFFFF");
        
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
