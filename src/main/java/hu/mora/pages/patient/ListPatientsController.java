package hu.mora.pages.patient;

import hu.mora.context.ApplicationUserContext;
import hu.mora.model.PatientData;
import hu.mora.model.PatientData.DB_COLUMN;
import hu.mora.scene.AppScene;
import hu.mora.scene.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

public class ListPatientsController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(ListPatientsController.class);

    @FXML
    private TextField searchField;

    @FXML
    private TableView<PatientData> patientTable;

    @Autowired
    private ApplicationUserContext userContext;

    @Autowired
    private SceneManager sceneManager;

    private String lastSearch;

    private FilteredList<PatientData> filteredPatients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<PatientData> items = FXCollections.observableArrayList();

        TableColumn<PatientData, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(DB_COLUMN.name.name()));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<PatientData, String> birthDateColumn = new TableColumn<>("Születési idő");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>(DB_COLUMN.birthDate.name()));

        TableColumn<PatientData, String> phoneColumn = new TableColumn<>("Telefonszám");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>(DB_COLUMN.phone.name()));

        TableColumn<PatientData, String> lastModifiedColumn = new TableColumn<>("Utolsó módosítés");
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>(DB_COLUMN.lastModified.name()));

        TableColumn<PatientData, String> actionsColumn = new TableColumn<>("Műveletek");
        actionsColumn.setCellFactory(param -> new ActionButtons());

        patientTable.setEditable(true);
        patientTable.getColumns().addAll(nameColumn, birthDateColumn, phoneColumn, lastModifiedColumn, actionsColumn);

        filteredPatients = new FilteredList<>(items);
        patientTable.setItems(filteredPatients);
    }

    public void newPatient(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.PATIENT_DATA);
    }

    private class ActionButtons extends TableCell<PatientData, String> {

        private final Button editUser;
        private final Button goTherapy;
        private final HBox graphics;

        public ActionButtons() {
            editUser = new Button("Szerkeszt");
            goTherapy = new Button("Terápia");


            goTherapy.setOnAction(action -> {
                PatientData patient = currentPatient();
                LOG.debug("Therapy action for {}", patient);
                sceneManager.showScene(AppScene.PATIENT_THERAPY);

            });
            editUser.setOnAction(action -> {
                PatientData patient = currentPatient();
                LOG.debug("Edit action for {}", patient);
                sceneManager.showScene(AppScene.PATIENT_DATA);
            });

            graphics = new HBox(goTherapy, editUser);
            graphics.setSpacing(5.0);
            graphics.setAlignment(Pos.CENTER);

        }

        private PatientData currentPatient() {
            PatientData patient = (PatientData) getTableRow().getItem();
            userContext.setCurrentPatient(patient);
            return patient;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                this.setGraphic(graphics);
            } else {
                this.setGraphic(null);
            }
        }
    }

    public void search() {
        String currentSearch = searchField.getText();
        if (lastSearch == null || !lastSearch.equalsIgnoreCase(currentSearch)) {
            lastSearch = currentSearch.toLowerCase();
            filteredPatients.setPredicate(patient -> patient.getName().toLowerCase().contains(lastSearch));
        }
    }
}
