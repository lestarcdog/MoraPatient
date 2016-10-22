package hu.mora.dao;

import com.google.common.collect.ImmutableMap;
import hu.mora.dao.mapper.PatientDataMapper;
import hu.mora.dao.mapper.TherapistMapper;
import hu.mora.model.PatientData;
import hu.mora.model.views.LoginTherapist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApplicationDao {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDao.class);

    private static final String ALL_PATIENTS = "select * from patients";
    private static final String INSERT_NEW_THERAPIST = "INSERT INTO therapists(\"name\") VALUES (:name)";

    private static final String INSERT_PATIENT = "INSERT INTO patients(\"name\",\"ismale\",\"birthdate\",\"phone\",\"email\",\"city\",\"street\",\"lastmodified\")" +
            " VALUES (:name,:male,:birthDate,:phone,:email,:city,:street,:lastModified)";

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

    public void saveNewPatient(PatientData patient) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", patient.getName());
        params.put("male", patient.getMale());
        //Date birthDate = new Date(patient.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        params.put("birthDate", Date.valueOf(patient.getBirthDate()));
        params.put("phone", patient.getPhone());
        params.put("email", patient.getEmail());
        params.put("city", patient.getCity());
        params.put("street", patient.getStreet());
        params.put("lastModified", Calendar.getInstance().getTime());


        jdbc.update(INSERT_PATIENT, params);
    }

    public boolean pingDatabase() {
        try {
            jdbc.queryForObject("SELECT 1", EmptySqlParameterSource.INSTANCE, Integer.class);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public void saveNewTherapist(String newTherapistName) {
        jdbc.update(INSERT_NEW_THERAPIST, ImmutableMap.of("name", newTherapistName));
    }
}
