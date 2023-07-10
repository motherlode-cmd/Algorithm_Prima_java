package com.example.demo;

import javafx.scene.control.TextArea;

import java.io.IOException;

public class ObserverTextArea implements Observer{
    private TextArea textArea = new TextArea();

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
    @Override
    public void update(Level level, String message) {
        if(textArea != null)
            textArea.setText(textArea.getText() + "\n[" + level.toString() + "]" + message);
    }
}
