package hu.mora.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
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
    private Pane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("App content init");
    }


    public void showMainContent(Parent content) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }


}
