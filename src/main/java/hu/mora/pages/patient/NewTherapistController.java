package hu.mora.pages.patient;

import com.google.common.base.Strings;
import hu.mora.dao.ApplicationDao;
import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NewTherapistController {

    @Autowired
    SceneManager sceneManager;

    @Autowired
    ApplicationDao dao;

    @FXML
    private TextField name;

    @FXML
    private Label errorMessage;

    public void save(ActionEvent actionEvent) {
        String therapistName = name.getText();
        if (!Strings.isNullOrEmpty(therapistName)) {
            dao.saveNewTherapist(therapistName);
            sceneManager.showScene(AppScene.LIST_PATIENT);
            sceneManager.showSuccess(String.format("A terapeuta %s sikeresen létrehozva.", therapistName));
        } else {
            errorMessage.setVisible(true);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.LIST_PATIENT);
    }
}
