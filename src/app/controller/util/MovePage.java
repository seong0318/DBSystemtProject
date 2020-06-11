package app.controller.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MovePage {
    public void movePageBtnAction(Button btn, String pagePath) {
        Stage stage = (Stage) btn.getScene().getWindow();

        try {
            Parent second = FXMLLoader.load(getClass().getResource(pagePath));
            Scene sc = new Scene(second);
            stage.setScene(sc);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
