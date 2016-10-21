package hu.mora.context;

import hu.mora.model.PatientData;
import hu.mora.model.views.LoginTherapist;

import java.util.Objects;

public class ApplicationUserContext {

    private LoginTherapist currentTherapist;
    private PatientData currentPatient;

    public LoginTherapist getCurrentTherapist() {
        return currentTherapist;
    }

    public void setCurrentTherapist(LoginTherapist currentTherapist) {
        this.currentTherapist = Objects.requireNonNull(currentTherapist);
    }

    public PatientData getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(PatientData currentPatient) {
        this.currentPatient = Objects.requireNonNull(currentPatient);
    }
}
