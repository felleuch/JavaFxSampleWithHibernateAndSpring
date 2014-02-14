package com.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.test.application.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by faiez.elleuch on 31/12/13.
 */
public class NewPersonController implements Initializable , ControlledScreen {

    ScreensController myController;

    AbstractApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
    PersonRepository repository = context.getBean(PersonRepository.class);



    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
        myMenuController.setMyController(screenParent);
    }


    @FXML private MyMenu myMenu;
    @FXML private MyMenuController myMenuController;


    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;

    @FXML
    private DatePicker birthDate;



    @FXML
    private void save(ActionEvent event){
        System.out.println("add button pressed.....");

        repository.save(new Person(firstNameField.getText(), lastNameField.getText(),emailField.getText()));

        myController.loadScreen(MainApplication.personScreenID, MainApplication.personScreenFile);
        myController.setScreen(MainApplication.personScreenID);
        //persons.add(new Person(firstNameField.getText(),lastNameField.getText(),emailField.getText(),birthDate.getValue()));
       // tableView.getItems().setAll(persons.subList(currentPageIndex * itemsPerPage, ((currentPageIndex * itemsPerPage + itemsPerPage <= persons.size()) ? currentPageIndex * itemsPerPage + itemsPerPage : persons.size())));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // myMenuController.setMyController(myController);


    }
}
