package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Vector;

public class GraphView extends Observable {
    private String [] next = new String [2];

    private EdgeView nextEdge = new EdgeView();
    public int getCounter_to_add() {
        return counter_to_add;
    }

    private int counter_to_add = 0;

    private Vector <VertexView> vertexes = new Vector<VertexView>();

    private Vector <EdgeView> edges = new Vector<EdgeView>();

    private void addLine(AnchorPane field) {
        Line line = new Line();
        line.setStartX(nextEdge.getFrom().getVertex().getCenterX());
        line.setStartY(nextEdge.getFrom().getVertex().getCenterY());
        line.setEndX(nextEdge.getTo().getVertex().getCenterX());
        line.setEndY(nextEdge.getTo().getVertex().getCenterY());
        field.getChildren().add(line);
        nextEdge.setEdge(line);
        edges.add(nextEdge);
        nextEdge.getTo().incDeg();
        nextEdge.getFrom().incDeg();
        addEdgeLabel(nextEdge, field);
        defaultColor();
        notify(Level.INPUT, "Add new edge " + nextEdge.getFrom().getVertex().getAccessibleText() + " " + nextEdge.getTo().getVertex().getAccessibleText() + " weight = " + nextEdge.getWeight());
        nextEdge = new EdgeView();
    }

    private void addEdgeLabel(EdgeView edge, AnchorPane field) {
        Label label = new Label();
        label.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
        label.setText(edge.getWeight() + " ");
        label.setLayoutX((edge.getEdge().getEndX() + edge.getEdge().getStartX()) / 2 );
        label.setLayoutY((edge.getEdge().getEndY() + edge.getEdge().getStartY()) / 2 );
        edge.setLabel(label);
        field.getChildren().add(label);
    }

    private void addLabel(Circle circle, VertexView newVertex, AnchorPane field) {
        Label label = new Label();
        label.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
        label.setText(newVertex.getVertex().getAccessibleText());
        label.setLayoutX(circle.getCenterX());
        label.setLayoutY(circle.getCenterY());
        newVertex.setLabel(label);
        field.getChildren().add(label);
    }
    public void addCircle(Circle circle, AnchorPane field) {
        VertexView newVertex = new VertexView();
        newVertex.setVertex(circle);
        if(!next[0].isEmpty()) {
            newVertex.getVertex().setAccessibleText(next[0]);
            nextEdge.setTo(newVertex);
            next[0] = "";
            counter_to_add--;
        } else if(!next[1].isEmpty()) {
            newVertex.getVertex().setAccessibleText(next[1]);
            nextEdge.setFrom(newVertex);
            next[1] = "";
            counter_to_add--;
        } else {
            return;
        }
        vertexes.add(newVertex);
        addLabel(circle, newVertex, field);
        circle.setFill(Color.DARKSLATEBLUE);
        notify(Level.INPUT,"Vertex " + circle.getAccessibleText() + " added");
        if(counter_to_add == 0 && nextEdge != null) {
            addLine(field);
        }
    }

    public void updateEdges(Circle circle) {
        String name = circle.getAccessibleText();
        for(int i = 0; i < edges.size(); i++) {
            if(edges.elementAt(i).getFrom().getVertex().getAccessibleText().equals(name)) {
                edges.elementAt(i).getEdge().setStartX(circle.getCenterX());
                edges.elementAt(i).getEdge().setStartY(circle.getCenterY());
                updateEdgeLabel(edges.elementAt(i));
            } else if(edges.elementAt(i).getTo().getVertex().getAccessibleText().equals(name)) {
                edges.elementAt(i).getEdge().setEndX(circle.getCenterX());
                edges.elementAt(i).getEdge().setEndY(circle.getCenterY());
                updateEdgeLabel(edges.elementAt(i));
            }
        }
    }

    public void updateEdgeLabel(EdgeView edge) {
        edge.getLabel().setLayoutX((edge.getEdge().getEndX() + edge.getEdge().getStartX()) / 2 );
        edge.getLabel().setLayoutY((edge.getEdge().getEndY() + edge.getEdge().getStartY()) / 2 );
    }
    public void updateText(Circle circle) {
        for(int i = 0; i < vertexes.size(); i++) {
            if(circle.getAccessibleText().equals(vertexes.elementAt(i).getVertex().getAccessibleText())) {
                double x = (circle.getCenterX() - circle.getRadius());
                double y = (circle.getCenterY() - circle.getRadius());
                vertexes.elementAt(i).getLabel().relocate(x, y);
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < edges.size(); i++) {
            sb.append(edges.elementAt(i).toString());
        }
        return sb.toString();
    }

    public void counter_add(String from, String to, double w, AnchorPane field) {
        if(from.equals(to)) {
            counter_to_add = 0;
            return;
        }
        counter_to_add = 2;
        next[0] = to;
        next[1] = from;
        nextEdge = new EdgeView();
        nextEdge.setWeight(w);
        for(int i = 0; i < vertexes.size(); i++) {
            if(vertexes.elementAt(i).getVertex().getAccessibleText().equals(from)) {
                counter_to_add--;
                next[1] = "";
                nextEdge.setFrom(vertexes.elementAt(i));
            }
            if(vertexes.elementAt(i).getVertex().getAccessibleText().equals(to)){
                counter_to_add--;
                next[0] = "";
                nextEdge.setTo(vertexes.elementAt(i));
            }
        }
        if(counter_to_add == 0) {
            for(int i = 0; i < edges.size(); i++) {
                EdgeView edge = edges.elementAt(i);
                String v1 = edge.getFrom().getVertex().getAccessibleText(), v2 = edge.getTo().getVertex().getAccessibleText();
                if(v1.equals(from) && v2.equals(to) || v1.equals(to) && v2.equals(from)) {
                    edge.setWeight(w);
                    edge.getLabel().setText(w + " ");
                    defaultColor();
                    nextEdge = new EdgeView();
                    return;
                }
            }
            addLine(field);
        }
    }

    public void removeEdge(String from, String to, AnchorPane field) {
        for(int i = 0; i < edges.size(); i++) {
            EdgeView edge = edges.elementAt(i);
            String v1 = edge.getFrom().getVertex().getAccessibleText(), v2 = edge.getTo().getVertex().getAccessibleText();
            if(v1.equals(from) && v2.equals(to) || v1.equals(to) && v2.equals(from)) {
                notify(Level.INPUT, "Delete edge " + from + " " + to);
                edges.remove(edge);
                edge.getFrom().decDeg();
                edge.getTo().decDeg();
                removeVertex(edge.getFrom(), field);
                removeVertex(edge.getTo(), field);
                field.getChildren().remove(edge.getEdge());
                field.getChildren().remove(edge.getLabel());
            }
        }
        defaultColor();
        notify(Level.ERROR, "Remove no edge");
    }
    public void colorResult(String primRes, Color color) {
        try {
            String[] ost = primRes.split("\n");
            for (int i = 0; i < ost.length; i++) {
                String[] edge = ost[i].split(" ");
                if (edge.length > 1)
                    colorEdge(edge[0], edge[1], color);
            }
        } catch (NullPointerException e) {
            defaultColor();
        }
    }

    public void colorVertexes(String vrt, Color color) {
        try {
            String[] ost = vrt.split(" ");
            for (int i = 0; i < vertexes.size(); i++) {
                for(int j = 0; j < ost.length; j++)
                    if(vertexes.elementAt(i).getLabel().getText().equals(ost[j]))
                        vertexes.elementAt(i).getVertex().setFill(color);
            }
        } catch (NullPointerException e) {
            defaultColor();
        }
    }

    private void colorEdge(String v1, String v2, Color color) {
        for(int i = 0; i < edges.size(); i++) {
            if(edges.elementAt(i).getFrom().toString().equals(v1) && edges.elementAt(i).getTo().toString().equals(v2) ||
                    edges.elementAt(i).getFrom().toString().equals(v2) && edges.elementAt(i).getTo().toString().equals(v1) ) {
                edges.get(i).getEdge().setStroke(color);
                edges.get(i).getLabel().setTextFill(Color.DARKSLATEBLUE);
            }
        }
    }
    public void defaultColor() {
        for(int i = 0; i < edges.size(); i++) {
            if(edges.elementAt(i).getEdge() != null) {
                edges.get(i).getEdge().setStroke(Color.BLACK);
                edges.get(i).getLabel().setTextFill(Color.BLACK);
            }
        }
        for (int i = 0; i < vertexes.size(); i++) {
            vertexes.elementAt(i).getVertex().setFill(Color.DARKSLATEBLUE);
        }
    }
    private void removeVertex(VertexView vertex, AnchorPane field) {
        if(vertex.getDegree() == 0) {
            field.getChildren().remove(vertex.getVertex());
            field.getChildren().remove(vertex.getLabel());
            vertexes.remove(vertex);
        }
    }

    public void removeVertex(String vertexName, AnchorPane field) {
        for(int i = 0; i < edges.size(); i++) {
            EdgeView edge = edges.elementAt(i);
            String v1 = edge.getFrom().getVertex().getAccessibleText(), v2 = edge.getTo().getVertex().getAccessibleText();
            if(v1.equals(vertexName) || v2.equals(vertexName)) {
                notify(Level.INPUT, "Delete edge " + edges.elementAt(i).getFrom() + " " + edges.elementAt(i).getTo());
                edges.remove(edge);
                edge.getFrom().decDeg();
                edge.getTo().decDeg();
                removeVertex(edge.getFrom(), field);
                removeVertex(edge.getTo(), field);
                field.getChildren().remove(edge.getEdge());
                field.getChildren().remove(edge.getLabel());
                defaultColor();
            }
        }
    }

    public Graph initGraph() {
        Graph g = new Graph();
        g.createGraph();
        for(int i = 0; i < vertexes.size(); i++) {
            g.addVertex(vertexes.elementAt(i).toString());
        }
        for(int i = 0; i < edges.size(); i++) {
            String [] inp = edges.elementAt(i).toString().split(" ");
            g.addEdge(inp[0], inp[1], Double.parseDouble(inp[2]));
        }
        return g;
    }
}
