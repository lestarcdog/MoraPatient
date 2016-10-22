package hu.mora.pages.patient;

import com.google.common.base.Strings;
import hu.mora.context.ApplicationUserContext;
import hu.mora.dao.ApplicationDao;
import hu.mora.model.PatientData;
import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import hu.mora.util.DateFormats;
import hu.mora.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalQueries;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Controller
public class PatientDataController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(PatientDataController.class);

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[\\d-\\s.\\/]+$");
    private static final String ERROR_CSS = "error";

    @Autowired
    private ApplicationUserContext userContext;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ApplicationDao dao;

    @FXML
    private Label invalidFormText;

    @FXML
    private TextField name;

    @FXML
    private ToggleGroup gender;

    /**
     * Radio buttons display group only
     */
    @FXML
    private HBox genderGroup;

    @FXML
    private DatePicker birthDate;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> city;

    @FXML
    private TextField street;

    private boolean isEditMode = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return object.format(DateFormats.BIRTHDAY);
                } else {
                    return null;
                }
            }

            @Override
            public LocalDate fromString(String string) {
                return Strings.isNullOrEmpty(string) ? null : DateFormats.BIRTHDAY.parse(string, TemporalQueries.localDate());
            }
        });

        city.setItems(FXCollections.observableArrayList("aa", "aaaa", "bbb", "ccc", "csabi", "móczy", "AAAA", "bbb"));

        FXUtils.autoCompleteComboBoxPlus(city, (typedText, objectToCompare) -> objectToCompare.toLowerCase().startsWith(typedText));

        loadUserFromAppContext();
    }

    private void loadUserFromAppContext() {
        PatientData patient = userContext.getCurrentPatient();
        if (patient != null) {
            isEditMode = true;
            name.setText(patient.getName());
            for (Toggle tg : gender.getToggles()) {
                RadioButton btn = (RadioButton) tg;
                if (btn.getText().equalsIgnoreCase(GenderRadioButtonText.MALE)) {
                    btn.setSelected(patient.getMale());
                } else {
                    btn.setSelected(!patient.getMale());
                }
            }
            birthDate.setValue(patient.getBirthDate());
            phone.setText(patient.getPhone());
            email.setText(patient.getEmail());
            city.setValue(patient.getCity());
            street.setText(patient.getStreet());

            userContext.setCurrentPatient(null);

        }
    }

    private boolean validate() {
        boolean valid = true;

        if (Strings.isNullOrEmpty(name.getText())) {
            valid = false;
            addError(name);
        } else {
            removeError(name);
        }

        if (gender.getSelectedToggle() == null) {
            valid = false;
            addError(genderGroup);
        } else {
            removeError(genderGroup);
        }

        if (birthDate.getValue() == null) {
            valid = false;
            addError(birthDate);
        } else {
            removeError(birthDate);
        }

        if (Strings.isNullOrEmpty(phone.getText()) || !PHONE_PATTERN.matcher(phone.getText()).matches()) {
            valid = false;
            addError(phone);
        } else {
            removeError(phone);
        }


        invalidFormText.setVisible(!valid);
        return valid;
    }

    private void addError(Node node) {
        node.getStyleClass().add(ERROR_CSS);
    }

    private void removeError(Node node) {
        node.getStyleClass().remove(ERROR_CSS);
    }


    public void save(ActionEvent actionEvent) {
        if (validate()) {
            PatientData patient = createPatientFromInput();
            LOG.info("Saving patient data {}", patient);
            dao.saveNewPatient(patient);
        }
    }

    private PatientData createPatientFromInput() {
        PatientData patient = new PatientData();
        patient.setName(name.getText());
        patient.setBirthDate(birthDate.getValue());

        boolean isMale = ((RadioButton) gender.getSelectedToggle()).getText().equals(GenderRadioButtonText.MALE);
        patient.setMale(isMale);

        patient.setPhone(phone.getText());

        String email = this.email.getText();
        if (Strings.isNullOrEmpty(email)) {
            patient.setEmail(email);
        }

        String city = this.city.getValue();
        if (!Strings.isNullOrEmpty(city)) {
            patient.setCity(city);
        }

        String streetTxt = street.getText();
        if (!Strings.isNullOrEmpty(streetTxt)) {
            patient.setStreet(streetTxt);
        }

        return patient;
    }

    public void cancel(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.LIST_PATIENT);
    }
}
