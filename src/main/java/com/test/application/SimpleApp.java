package com.test.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Main window");
        Button openLoginWindowButton = new Button("Open another Login Dialog");
        openLoginWindowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new LoginWindow().show();
            }
        });
        openLoginWindowButton.setPadding(new Insets(80));
        stage.setScene(new Scene(openLoginWindowButton));
        stage.show();
    }

    class LoginWindow extends Stage {

        private LabeledTextField nameField;
        private LabeledTextField passwordField;
        private Button loginButton;

        public LoginWindow() {
            setTitle("Login");
            setScene(createScene());
            registerListeners();
        }

        private Scene createScene() {
            nameField = new LabeledTextField("Name:", false);
            passwordField = new LabeledTextField("Password:", true);
            loginButton = new Button("Submit");
            HBox bottomBox = new HBox(loginButton);
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
            VBox rootBox = new VBox(20, nameField, passwordField, bottomBox);
            rootBox.setPadding(new Insets(10));
            return new Scene(rootBox);
        }

        private void registerListeners() {
            loginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Login attempt of " + nameField.getText());
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            });
            // ...
        }
    }

    class LabeledTextField extends HBox {

        private TextField textField;
        private Label label;

        public LabeledTextField(String text, boolean hideInput) {
            label = new Label(text);
            textField = hideInput ? new PasswordField() : new TextField();
            setAlignment(Pos.CENTER_RIGHT);
            setSpacing(10);
            getChildren().addAll(label, textField);
        }

        public String getText() {
            return textField.getText();
        }
    }
}