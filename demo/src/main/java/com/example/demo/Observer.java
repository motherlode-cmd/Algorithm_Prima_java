package com.example.demo;

import javafx.scene.control.TextArea;

public interface Observer {
    public void update(Level level, String message);
}
