package hu.mora.scene;

import hu.mora.pages.AppContentManager;
import hu.mora.springloader.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

public class SceneManager {

    private static final Logger LOG = LoggerFactory.getLogger(SceneManager.class);

    private Stage mainStage;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @Autowired
    private AppContentManager appContentManager;

    public synchronized void setMainStage(Stage main) {
        if (mainStage == null) {
            mainStage = requireNonNull(main, "main");

            //set up main scene
            Parent mainSceneNode = fxmlLoader.load(SceneManager.class.getResourceAsStream("/fxml/main.fxml"));
            main.setScene(new Scene(mainSceneNode));

            main.getIcons().add(new Image("graphics/mora_icon.jpg"));
            main.show();

            showScene(AppScene.LOGIN);

        } else {
            LOG.warn("Main stage is already set");
        }
    }


    public void showScene(AppScene appScene) {
        requireNonNull(appScene);
        requireNonNull(mainStage, "main stage is not set");

        LOG.debug("Loading scene {}", appScene.getSceneTitle());
        InputStream sceneFxml = SceneManager.class.getResourceAsStream(appScene.getFxmlPath());
        Parent parent = fxmlLoader.load(sceneFxml);

        mainStage.setTitle(appScene.getSceneTitle());
        appContentManager.showMainContent(parent);

        if (!mainStage.isShowing()) {
            mainStage.show();
        }

    }

}
