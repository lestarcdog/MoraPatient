package hu.mora.scene;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import hu.mora.dao.ApplicationDao;
import hu.mora.pages.AppContentManager;
import hu.mora.scene.springloader.SpringFxmlLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class SceneManager {

    private static final Logger LOG = LoggerFactory.getLogger(SceneManager.class);

    private Stage mainStage;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @Autowired
    private ApplicationDao dao;

    @Autowired
    private AppContentManager appContentManager;

    public synchronized void setupStage(Stage main) {
        if (mainStage == null) {
            mainStage = requireNonNull(main, "main");

            //set up main scene
            Parent mainSceneNode = fxmlLoader.load(SceneManager.class.getResourceAsStream("/fxml/main.fxml"));
            main.setScene(new Scene(mainSceneNode));
            main.getIcons().add(new Image("graphics/mora_icon.jpg"));

            List<String> errorMessages = preFlightCheck();
            if (errorMessages.isEmpty()) {
                showScene(AppScene.LOGIN);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        Joiner.on("\n").join(errorMessages),
                        new ButtonType("Kilépés", ButtonBar.ButtonData.CANCEL_CLOSE));
                alert.setHeaderText("Végzetes hiba történt!");
                alert.showAndWait();
                Platform.exit();
            }
            main.show();

        } else {
            LOG.warn("Main stage is already set");
        }
    }

    private List<String> preFlightCheck() {
        List<String> exceptionMessages = Lists.newArrayList();
        if (!dao.pingDatabase()) {
            exceptionMessages.add("Az adatbázis nem elérhető.");
        }

        return exceptionMessages;
    }


    public void showScene(AppScene appScene) {
        requireNonNull(appScene);
        requireNonNull(mainStage, "main stage is not set");

        LOG.debug("Loading scene {}", appScene.getSceneTitle());
        InputStream sceneFxml = SceneManager.class.getResourceAsStream(appScene.getFxmlPath());
        Parent parent = fxmlLoader.load(sceneFxml);

        appContentManager.menuVisibility(!appScene.equals(AppScene.LOGIN));

        mainStage.setTitle(appScene.getSceneTitle());
        appContentManager.showMainContent(parent);

        if (!mainStage.isShowing()) {
            mainStage.show();
        }

    }

    public void showError(String errorMessage) {
        LOG.error(errorMessage);
    }

    public void showSuccess(String successMessage) {
        LOG.info(successMessage);
    }
}
