package hu.mora;

import hu.mora.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MoraPatientMain extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MoraPatientMain.class);
    private static final ApplicationContext CTX = new ClassPathXmlApplicationContext("classpath:/context.xml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = CTX.getBean(SceneManager.class);
        sceneManager.setMainStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
