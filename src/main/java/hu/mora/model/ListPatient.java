package hu.mora.model;

import hu.mora.util.DateFormats;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListPatient {

    public static final String NAME_COLUMN = "name";
    public static final String BIRTH_DATE_COLUMN = "birthDate";
    public static final String PHONE_COLUMN = "phone";
    public static final String LAST_MOD_COLUMN = "lastModified";

    private final Integer id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty birthDate;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty lastModified;

    public ListPatient(Integer id, String name, LocalDate birthDate, String phone, LocalDateTime lastModified) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.birthDate = new SimpleStringProperty(birthDate.format(DateFormats.DAY));
        this.phone = new SimpleStringProperty(phone);
        this.lastModified = new SimpleStringProperty(lastModified.format(DateFormats.DATETIME));
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }


    public String getBirthDate() {
        return birthDate.get();
    }

    public SimpleStringProperty birthDateProperty() {
        return birthDate;
    }


    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }


    public String getLastModified() {
        return lastModified.get();
    }

    public SimpleStringProperty lastModifiedProperty() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "ListPatient{" +
                "id=" + id +
                ", name=" + name +
                ", birthDate=" + birthDate +
                ", phone=" + phone +
                ", lastModified=" + lastModified +
                '}';
    }
}
