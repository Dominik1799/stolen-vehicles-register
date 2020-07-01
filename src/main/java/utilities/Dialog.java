package utilities;

import javafx.scene.control.Alert;

public class Dialog {

    private static Dialog instance = null;


    private Dialog(){}


    public static Dialog getInstance() {
        if (instance == null)
            instance = new Dialog();

        return instance;
    }

    public void infoDialog(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void warningDialog(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void errorDialog(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
