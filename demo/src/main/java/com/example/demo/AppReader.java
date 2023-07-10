package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AppReader extends Reader {
    @FXML
    private TextArea logField = new TextArea();

    public AppReader(TextArea logField) {
        this.logField = logField;
    }

    @Override
    public void read(){

    }
}
