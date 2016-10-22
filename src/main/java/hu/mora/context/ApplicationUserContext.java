package hu.mora.context;

import hu.mora.model.PatientData;
import hu.mora.model.views.LoginTherapist;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApplicationUserContext {

    private LoginTherapist currentTherapist;
    private PatientData currentPatient;

    public LoginTherapist getCurrentTherapist() {
        return currentTherapist;
    }

    public void setCurrentTherapist(LoginTherapist currentTherapist) {
        this.currentTherapist = Objects.requireNonNull(currentTherapist);
    }

    public PatientData readCurrentPatientOnce() {
        PatientData patient = this.currentPatient;
        currentPatient = null;
        return patient;
    }

    public void setCurrentPatient(PatientData currentPatient) {
        this.currentPatient = Objects.requireNonNull(currentPatient);
    }
}
