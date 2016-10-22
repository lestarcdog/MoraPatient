package hu.mora.pages;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;

@Controller
public class UserNotificationController {

    @FXML
    private Pane errorMessageBox;

    @FXML
    private Pane successMessageBox;

    @FXML
    private Label errorMessageText;

    @FXML
    Label successMessageText;

    public void showSuccessMessage(String message) {
        successMessageBox.setVisible(true);
        successMessageText.setText(message);
    }

    public void showErrorMessage(String message) {
        errorMessageBox.setVisible(true);
        errorMessageText.setText(message);
    }


    public void closeErrorBox(ActionEvent actionEvent) {
        errorMessageBox.setVisible(false);
    }
}
