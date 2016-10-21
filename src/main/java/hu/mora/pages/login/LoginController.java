package hu.mora.pages.login;

import hu.mora.context.ApplicationUserContext;
import hu.mora.dao.ApplicationDao;
import hu.mora.model.views.LoginTherapist;
import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

public class LoginController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private ComboBox<LoginTherapist> loginTherapist;

    @FXML
    private Button loginButton;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ApplicationDao dao;

    @Autowired
    private ApplicationUserContext userContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginTherapist.setConverter(new StringConverter<LoginTherapist>() {
            @Override
            public String toString(LoginTherapist object) {
                return object.getName();
            }

            @Override
            public LoginTherapist fromString(String string) {
                throw new UnsupportedOperationException("not me");
            }
        });

        ObservableList<LoginTherapist> therapists = FXCollections.observableArrayList(dao.listTherapists());

        loginTherapist.setItems(therapists);
        loginTherapist.setPromptText("Kérem válasszon");

        loginTherapist.setOnAction(event -> {
            if (loginTherapist.getSelectionModel().isEmpty()) {
                loginButton.setDisable(true);
            } else {
                loginButton.setDisable(false);
            }
        });

    }

    public void login(ActionEvent actionEvent) {
        LoginTherapist therapist = requireNonNull(loginTherapist.getSelectionModel().getSelectedItem(), "therapist");
        LOG.info("Log in as {}", therapist);

        userContext.setCurrentTherapist(therapist);
        sceneManager.showScene(AppScene.LIST_PATIENT);
    }
}
