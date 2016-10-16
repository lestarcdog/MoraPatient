package hu.mora.springloader;

import com.google.common.base.Throwables;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class SpringFxmlLoader {

    private static final ApplicationContext CTX = new ClassPathXmlApplicationContext("classpath:/context.xml");


    public static Parent load(InputStream inputStream) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(CTX::getBean);
            return loader.load(inputStream);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
