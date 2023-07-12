package com.example.demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.InputMethodEvent;

public class StartController extends Observable implements Dialog{
    protected Reader reader;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textField;

    @FXML
    private Button doneBtn;

    @FXML
    private Button cancelBtn;


    @FXML
    void cancel(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void done(MouseEvent event) {
        reader.setV1(textField.getText());
        if(reader.getV1().isEmpty()){
            notify(Level.ERROR, "Bad input");
            return;
        }
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'start.fxml'.";
        assert doneBtn != null : "fx:id=\"doneBtn\" was not injected: check your FXML file 'start.fxml'.";
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'start.fxml'.";
    }

    @Override
    public void setReader(Reader log) {
        reader = log;

    }
}
