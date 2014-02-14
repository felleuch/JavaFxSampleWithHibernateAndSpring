package com.test.application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by faiez.elleuch on 02/01/14.
 */
public class MyMenuController implements Initializable, ControlledScreen {

    public ScreensController getMyController() {
        return myController;
    }

    public void setMyController(ScreensController myController) {
        this.myController = myController;
    }

    ScreensController myController;



    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private MenuBar menuBar;




    @FXML
    private void handleMenuPerson(final ActionEvent event){
        myController.loadScreen(MainApplication.personScreenID, MainApplication.personScreenFile);
        myController.setScreen(MainApplication.personScreenID);
    }

    @FXML
    private void handleMenuAnimals(final ActionEvent event){
        myController.setScreen(MainApplication.screen2ID);
    }

    @FXML
    private void handleMenuVeterinaire(final ActionEvent event){
        myController.setScreen(MainApplication.screen3ID);
    }

    @FXML
    private void handleExit(final ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuBar.setFocusTraversable(true);
    }
}
