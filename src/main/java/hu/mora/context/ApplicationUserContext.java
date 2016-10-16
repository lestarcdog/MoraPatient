package hu.mora.context;

import hu.mora.model.LoginTherapist;

public class ApplicationUserContext {

    private LoginTherapist currentTherapist;

    public LoginTherapist getCurrentTherapist() {
        return currentTherapist;
    }

    public void setCurrentTherapist(LoginTherapist currentTherapist) {
        this.currentTherapist = currentTherapist;
    }
}
