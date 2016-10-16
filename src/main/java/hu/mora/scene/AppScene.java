package hu.mora.scene;

public enum AppScene {
    LOGIN("login", "Bejelentkezés"),
    PATIENT_DATA("patient_data", "Beteg adatai"),
    LIST_PATIENT("list_patient", "Betegek"),
    PATIENT_THERAPY("patient_therapy", "Beteg terápiái");

    private static final String PATH_PREFIX = "/fxml/";
    private static final String PATH_POSTFIX = ".fxml";

    private final String fxmlPath;
    private final String sceneTitle;

    AppScene(String fxmlPath, String sceneTitle) {
        this.fxmlPath = fxmlPath;
        this.sceneTitle = sceneTitle;
    }

    public String getSceneTitle() {
        return sceneTitle;
    }

    public String getFxmlPath() {
        return PATH_PREFIX + fxmlPath + PATH_POSTFIX;
    }
}