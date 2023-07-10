package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List <Vertex> graph;

    public List <Vertex> getGraph(){
        return graph;
    }
    public void createGraph() {
        graph = new ArrayList<Vertex>();
    }

    public void addVertex(String name) {
        graph.add(new Vertex(name));
    }
    public void addEdge(String v1, String v2, double weight) {
        Vertex vertex1 = new Vertex(v1), vertex2 = new Vertex(v2);
        for(int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getLabel().equals(v1))
                vertex1 = graph.get(i);
            if(graph.get(i).getLabel().equals(v2))
                vertex2 = graph.get(i);
        }
        if(v1 != null && v2 != null) {
            Edge e = new Edge((int)weight);
            vertex1.addEdge(vertex2, e);
            vertex2.addEdge(vertex1, e);
        }
    }

}
