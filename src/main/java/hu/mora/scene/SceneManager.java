package hu.mora.scene;

import hu.mora.springloader.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

public class SceneManager {

    private static final Logger LOG = LoggerFactory.getLogger(SceneManager.class);

    private static Stage MAIN;

    public static synchronized void setMainStage(Stage main) {
        if (MAIN == null) {
            MAIN = requireNonNull(main, "main");
        } else {
            LOG.warn("Main stage is already set");
        }
    }


    public void showScene(AppScene appScene) {
        showScene(appScene, null);
    }

    public void showScene(AppScene appScene, Object sceneData) {
        requireNonNull(appScene);
        requireNonNull(MAIN, "main stage is not set");

        LOG.debug("Loading scene {}", appScene.getSceneTitle());
        InputStream sceneFxml = SceneManager.class.getResourceAsStream(appScene.getFxmlPath());
        Parent parent = SpringFxmlLoader.load(sceneFxml);

        Scene newScene = new Scene(parent);
        if (sceneData != null) {
            newScene.setUserData(sceneData);
        }
        MAIN.setScene(newScene);
        MAIN.setTitle(appScene.getSceneTitle());

        if (!MAIN.isShowing()) {
            MAIN.show();
        }

    }

}
