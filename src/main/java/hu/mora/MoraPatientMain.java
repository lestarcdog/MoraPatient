package hu.mora;

import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import hu.mora.springloader.SpringFxmlLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class MoraPatientMain extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MoraPatientMain.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setMainStage(primaryStage);
        LOG.info("Init application with login screen");

        //do it manually from non spring bean
        InputStream loginScreenFxml = MoraPatientMain.class.getResourceAsStream(AppScene.PATIENT_DATA.getFxmlPath());
        Parent parent = SpringFxmlLoader.load(loginScreenFxml);

        primaryStage.setTitle(AppScene.LOGIN.getSceneTitle());
        primaryStage.setScene(new Scene(parent));

        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
