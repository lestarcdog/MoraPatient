package hu.mora.pages.patient;

import hu.mora.model.ListPatient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ListPatientsController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(ListPatientsController.class);

    @FXML
    private TextField searchField;

    private String lastSearch;

    @FXML
    private TableView<ListPatient> patientTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<ListPatient> items = FXCollections.observableArrayList(
                new ListPatient(1, "csabi", LocalDate.now(), "1234-45987", LocalDateTime.now()),
                new ListPatient(2, "dr juharos ágota", LocalDate.now(), "1234-45987", LocalDateTime.now()),
                new ListPatient(3, "móczy a kutya", LocalDate.now(), "1234-45987", LocalDateTime.now()));

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
        patientTable.setItems(items);
    }

    private class ActionButtons extends TableCell<ListPatient, String> {

        private final Button editUser;
        private final Button goTherapy;
        private final HBox graphics;

        public ActionButtons() {
            editUser = new Button("Szerkeszt");
            goTherapy = new Button("Terápia");


            goTherapy.setOnAction(action -> LOG.info("go to terapia {}", currentPatient()));
            editUser.setOnAction(action -> LOG.info("szerkeszt {}", currentPatient()));

            graphics = new HBox(goTherapy, editUser);
            graphics.setSpacing(5.0);
            graphics.setAlignment(Pos.CENTER);

        }

        private ListPatient currentPatient() {
            return (ListPatient) getTableRow().getItem();
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
        if (lastSearch == null || !lastSearch.equals(currentSearch)) {
            LOG.info("{}", searchField.getText());
            lastSearch = currentSearch;
        }
    }
}
