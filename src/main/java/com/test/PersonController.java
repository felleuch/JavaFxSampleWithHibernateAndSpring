package com.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import com.test.application.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * FXML Controller class
 *
 * @author Angie
 */
public class PersonController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML private MyMenu myMenu;
    @FXML private MyMenuController myMenuController;

    AbstractApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
    PersonRepository repository = context.getBean(PersonRepository.class);


    @FXML
    private TableView<Person> tableView;


    @FXML
    private Button addButton;

    @FXML
    private Pagination pagination;


    private List<Person> lstPerson = getAllPersons();
    private ObservableList<Person> persons = FXCollections.observableArrayList(lstPerson);

    /*@FXML
    protected void addPerson(ActionEvent event) {
        persons.add(new Person(firstNameField.getText(),lastNameField.getText(),emailField.getText(),birthDate.getValue()));
        tableView.getItems().setAll(persons.subList(currentPageIndex * itemsPerPage, ((currentPageIndex * itemsPerPage + itemsPerPage <= persons.size()) ? currentPageIndex * itemsPerPage + itemsPerPage : persons.size())));
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }*/
    public List<Person> getAllPersons(){

        return (List<Person>)repository.findAll();

    }



    int pageCount = 5;
    int itemsPerPage = 10;
    int currentPageIndex = 0;

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    private int getPageCount(int totalCount, int itemsPerPage) {
        float floatCount = (float)totalCount / itemsPerPage;
        int intCount = totalCount / itemsPerPage;
        return ((floatCount > intCount) ? ++intCount : intCount);
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstPerson = getAllPersons();
        TableColumn<Person, Boolean> actionCol = new TableColumn<Person, Boolean>("");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, Boolean>, ObservableValue<Boolean>>() {
            @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Person, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        actionCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
            @Override public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> personBooleanTableColumn) {
                return new DeletePersonCell(tableView);
            }
        });
        tableView.getColumns().add(actionCol);

        if(persons.size() <= itemsPerPage ){
            tableView.getItems().setAll(persons);
        }else{
            tableView.getItems().setAll(persons.subList(0, itemsPerPage));
        }



        pageCount = getPageCount(persons.size(), itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentPageIndex = newValue.intValue();
                tableView.getItems().setAll(persons.subList(currentPageIndex * itemsPerPage, ((currentPageIndex * itemsPerPage + itemsPerPage <= persons.size()) ? currentPageIndex * itemsPerPage + itemsPerPage : persons.size())));
            }
        });
    }

    
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
        myMenuController.setMyController(screenParent);
    }

    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(MainApplication.screen2ID);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(MainApplication.screen3ID);
    }


    /*private void nouveauPersonOld(){
        Stage stage = new Stage();
        FXMLLoader f = new FXMLLoader (getClass().getResource("nouveauPerson.fxml"));

        Parent fxmlRoot = null;
        try {
            fxmlRoot = (Parent)f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Ajout person");
        stage.setScene(new Scene(fxmlRoot));
        stage.show();

    }*/

    @FXML
    private void nouveauPerson(){
        myController.setScreen(MainApplication.createPersonID);
    }



    class DeletePersonCell extends TableCell<Person, Boolean> {
        // a button for adding a new person.
        final Button addButton = new Button("Delete");
        // pads and centers the add button in the cell.
        final StackPane paddedButton = new StackPane();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();


        DeletePersonCell(final TableView table) {
            paddedButton.setPadding(new Insets(3));
            paddedButton.getChildren().add(addButton);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    table.getSelectionModel().select(getTableRow().getIndex());
                    int iindex = currentPageIndex * itemsPerPage + getTableRow().getIndex();
                    Person pp = (Person) persons.get(iindex);
                    repository.delete(pp);
                    myController.loadScreen(MainApplication.personScreenID, MainApplication.personScreenFile);
                    myController.setScreen(MainApplication.personScreenID);

                }
            });
        }

        /** places an add button in the row only if the row is not empty. */
        @Override protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            }
        }
    }
}
