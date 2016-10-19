package hu.mora.pages.patient;

import com.google.common.base.Strings;
import hu.mora.model.PatientData;
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

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalQueries;
import java.util.ResourceBundle;

public class PatientDataController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(PatientDataController.class);

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
    private ComboBox<String> city;

    @FXML
    private TextField street;

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

        FXUtils.autoCompleteComboBoxPlus(city, (typedText, objectToCompare) -> objectToCompare.toLowerCase().startsWith(typedText
        ));
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

        if (Strings.isNullOrEmpty(phone.getText())) {
            valid = false;
            addError(phone);
        } else {
            removeError(phone);
        }


        invalidFormText.setVisible(!valid);
        return valid;
    }

    private void addError(Node node) {
        node.getStyleClass().add("error");
    }

    private void removeError(Node node) {
        node.getStyleClass().remove("error");
    }


    public void save(ActionEvent actionEvent) {
        if (validate()) {
            PatientData patient = createPatientFromInput();
            LOG.info("Saving patient data {}", patient);
        }
    }

    private PatientData createPatientFromInput() {
        PatientData patient = new PatientData();
        patient.setName(name.getText());
        patient.setBirthDate(birthDate.getValue());

        boolean isMale = ((RadioButton) gender.getSelectedToggle()).getText().equals(GenderRadioButtonText.MALE);
        patient.setMale(isMale);

        return patient;
    }

}
