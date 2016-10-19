package hu.mora.context;

import hu.mora.model.views.LoginTherapist;

import java.util.Objects;

public class ApplicationUserContext {

    private LoginTherapist currentTherapist;
    private Integer patientId;

    public LoginTherapist getCurrentTherapist() {
        return currentTherapist;
    }

    public void setCurrentTherapist(LoginTherapist currentTherapist) {
        this.currentTherapist = Objects.requireNonNull(currentTherapist);
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = Objects.requireNonNull(patientId);
    }
}
