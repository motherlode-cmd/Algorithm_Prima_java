package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class VertexView {
    @FXML
    private Circle vertex = new Circle();

    private int degree = 0;

    @FXML
    private Label label = new Label();
    public void incDeg(){
        degree++;
    }

    public void decDeg(){
        degree--;
    }
    public String toString() {
        if(vertex.getAccessibleText() == null)
            return "";
        return vertex.getAccessibleText();
    }
    public int getDegree() {
        return degree;
    }

    @FXML
    public Label getLabel(){
        return label;
    }
    public void setLabel(Label txt) {
        label = txt;
    }

    public void setVertex(Circle vertex) {
        this.vertex = vertex;
    }

    public Circle getVertex() {
        return vertex;
    }

}
