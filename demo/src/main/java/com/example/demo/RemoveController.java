package com.example.demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RemoveController extends Observable implements Dialog{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane scene;

    @FXML
    private TextField vertex1;

    @FXML
    private TextField vertex2;

    @FXML
    private Button doneBtn;

    @FXML
    private Button cancetBtn;
    protected Reader reader;
    @Override
    public void setReader(Reader log) {
        reader = log;
    }

    @FXML
    void cancel(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void done(MouseEvent event) {
        reader.setV1(vertex1.getText());
        reader.setV2(vertex2.getText());
        reader.setW(0);
        if(vertex1.getText().isEmpty() || vertex2.getText().isEmpty()) {
            notify(Level.ERROR, "Empty input");
            notify(Level.CLUE, "right all info");
            return;
        }
        if(reader.getV1().equals(reader.getV2())){
            notify(Level.ERROR, "Bad input");
            notify(Level.CLUE, "make vertexes difference");
            return;
        }
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        assert scene != null : "fx:id=\"scene\" was not injected: check your FXML file 'remover.fxml'.";
        assert vertex1 != null : "fx:id=\"vertex1\" was not injected: check your FXML file 'remover.fxml'.";
        assert vertex2 != null : "fx:id=\"vertex2\" was not injected: check your FXML file 'remover.fxml'.";
        assert doneBtn != null : "fx:id=\"doneBtn\" was not injected: check your FXML file 'remover.fxml'.";
        assert cancetBtn != null : "fx:id=\"cancetBtn\" was not injected: check your FXML file 'remover.fxml'.";

    }
}
