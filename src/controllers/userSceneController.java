package controllers;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import entities.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userSceneController implements Initializable {
    @FXML
    protected AnchorPane navList,background;
    @FXML
    protected Text fullname;
    @FXML
    protected JFXTextArea userInfo;
    @FXML
    protected JFXHamburger hamburgerOpen;
    protected User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void showName() {
        fullname.setText(user.getFirstName() + " " +  user.getLastName());
    }
    public void showInfo() {
        userInfo.setText(String.format("ID: %s\nUser: %s %s\nSex: %s\nRank:%s\nTeam: %s\nBirthday:%s",
                String.valueOf(user.getId()),user.getFirstName(), user.getLastName(), user.getSex(), user.getRank(), user.getTeam(), user.getBirthdate())
        );
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
    }


    protected void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), navList);
        hamburgerOpen.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if(navList.getTranslateX()!=0){
                openNav.play();
            }else{
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        background.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            if(navList.getTranslateX() == 0){
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
    }

    public void onTeamClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/teamScene.fxml"));
        Parent root = (Parent) loader.load();
        teamSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onCriminalsClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/criminalsScene.fxml"));
        Parent root = (Parent) loader.load();
        criminalsSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onLogOutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent view2 = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onHomeClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/userScene.fxml"));
        Parent root = (Parent) loader.load();
        userSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        ctrl.showInfo();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onVehicleClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/vehiclesScene.fxml"));
        Parent root = (Parent) loader.load();
        vehicleSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onCasesClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/casesScene.fxml"));
        Parent root = (Parent) loader.load();
        casesSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


    public void onActionSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/settingsScene.fxml"));
        Parent root = (Parent) loader.load();
        settingsSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        ctrl.getOptions();
        ctrl.setPrompt();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

    }


}
