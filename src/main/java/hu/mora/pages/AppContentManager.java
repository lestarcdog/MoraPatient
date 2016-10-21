package hu.mora.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

public class AppContentManager implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(AppContentManager.class);

    @Autowired
    private MenuController menuController;

    @FXML
    private HBox menuContainer;

    @FXML
    private Pane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void menuVisibility(boolean visible) {
        menuContainer.setVisible(visible);
    }


    public void showMainContent(Parent content) {
        contentArea.getChildren().clear();
        HBox.setHgrow(content, Priority.ALWAYS);
        contentArea.getChildren().add(content);
    }


}
