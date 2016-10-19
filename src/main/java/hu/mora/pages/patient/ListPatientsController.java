package hu.mora.pages.patient;

import hu.mora.context.ApplicationUserContext;
import hu.mora.model.views.ListPatient;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class ListPatientsController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(ListPatientsController.class);

    @FXML
    private TextField searchField;

    @FXML
    private TableView<ListPatient> patientTable;

    @Autowired
    private ApplicationUserContext userContext;

    @Autowired
    private SceneManager sceneManager;

    private String lastSearch;

    private FilteredList<ListPatient> filteredPatients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<ListPatient> items = FXCollections.observableArrayList();
        IntStream.range(0, 20).forEach(i -> {
            items.addAll(
                    new ListPatient(1, "csabi", LocalDate.now(), "1234-45987", LocalDateTime.now()),
                    new ListPatient(2, "dr juharos ágota", LocalDate.now(), "1234-45987", LocalDateTime.now()),
                    new ListPatient(3, "móczy a kutya", LocalDate.now(), "1234-45987", LocalDateTime.now()));
        });

        TableColumn<ListPatient, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(ListPatient.NAME_COLUMN));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<ListPatient, String> birthDateColumn = new TableColumn<>("Születési idő");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>(ListPatient.BIRTH_DATE_COLUMN));

        TableColumn<ListPatient, String> phoneColumn = new TableColumn<>("Telefonszám");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>(ListPatient.PHONE_COLUMN));

        TableColumn<ListPatient, String> lastModifiedColumn = new TableColumn<>("Utolsó módosítés");
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>(ListPatient.LAST_MOD_COLUMN));

        TableColumn<ListPatient, String> actionsColumn = new TableColumn<>("Műveletek");
        actionsColumn.setCellFactory(param -> new ActionButtons());

        patientTable.setEditable(true);
        patientTable.getColumns().addAll(nameColumn, birthDateColumn, phoneColumn, lastModifiedColumn, actionsColumn);

        filteredPatients = new FilteredList<>(items);
        patientTable.setItems(filteredPatients);
    }

    public void newPatient(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.PATIENT_DATA);
    }

    private class ActionButtons extends TableCell<ListPatient, String> {

        private final Button editUser;
        private final Button goTherapy;
        private final HBox graphics;

        public ActionButtons() {
            editUser = new Button("Szerkeszt");
            goTherapy = new Button("Terápia");


            goTherapy.setOnAction(action -> {
                ListPatient patient = currentPatient();
                LOG.debug("Therapy action for {}", patient);
                sceneManager.showScene(AppScene.PATIENT_THERAPY);

            });
            editUser.setOnAction(action -> {
                ListPatient patient = currentPatient();
                LOG.debug("Edit action for {}", patient);
                sceneManager.showScene(AppScene.PATIENT_DATA);
            });

            graphics = new HBox(goTherapy, editUser);
            graphics.setSpacing(5.0);
            graphics.setAlignment(Pos.CENTER);

        }

        private ListPatient currentPatient() {
            ListPatient patient = (ListPatient) getTableRow().getItem();
            userContext.setPatientId(patient.getId());
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
            filteredPatients.setPredicate(lp -> lp.getSmallCapitalName().contains(lastSearch));
        }
    }
}
