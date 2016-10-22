package hu.mora.pages;

import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {


    @Autowired
    SceneManager sceneManager;

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void gotoPatientList(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.LIST_PATIENT);
    }

    public void gotoNewPatient(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.PATIENT_DATA);
    }

    public void newTherapist(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.NEW_THERAPIST);
    }
}
