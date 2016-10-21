package hu.mora.dao;

import com.google.common.collect.ImmutableMap;
import hu.mora.dao.mapper.PatientDataMapper;
import hu.mora.dao.mapper.TherapistMapper;
import hu.mora.model.PatientData;
import hu.mora.model.views.LoginTherapist;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class ApplicationDao {

    private static final String ALL_PATIENTS = "select * from patients";
    private static final String INSERT_NEW_THERAPIST = "INSERT INTO therapists(\"name\") VALUES (:name)";

    private static final String LIST_THERAPISTS = "select * from therapists";

    private static final PatientDataMapper PATIENT_MAPPER = new PatientDataMapper();
    private static final TherapistMapper THERAPIST_MAPPER = new TherapistMapper();

    private final NamedParameterJdbcTemplate jdbc;

    public ApplicationDao(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public List<PatientData> allPatients() {
        return jdbc.query(ALL_PATIENTS, PATIENT_MAPPER);
    }

    public List<LoginTherapist> listTherapists() {
        return jdbc.query(LIST_THERAPISTS, THERAPIST_MAPPER);
    }

    public void saveNewTherapist(String newTherapistName) {
        jdbc.update(INSERT_NEW_THERAPIST, ImmutableMap.of("name", newTherapistName));
    }
}
