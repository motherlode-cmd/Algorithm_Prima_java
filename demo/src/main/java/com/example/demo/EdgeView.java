package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class EdgeView {
    private VertexView from = new VertexView();
    private VertexView to = new VertexView();
    private double weight = 0;


    @FXML
    private Label label = new Label();

    @FXML
    private Line edge = new Line();

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }

    public VertexView getTo() {
        return to;
    }

    public void setTo(VertexView to) {
        this.to = to;
    }

    public void setEdge(Line edge) {
        this.edge = edge;
    }

    public String toString() {
        if(from == null || to == null || from.getVertex() ==null || to.getVertex() == null) {
            return "";
        }
        return from.toString() + " " + to.toString() + " " + weight + "\n";
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setFrom(VertexView from) {
        this.from = from;
    }

    public Line getEdge() {
        return edge;
    }

    public double getWeight() {
        return weight;
    }

    public VertexView getFrom() {
        return from;
    }
}
