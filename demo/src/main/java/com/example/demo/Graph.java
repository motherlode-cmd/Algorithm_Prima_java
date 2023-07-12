package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

    private void dfs(Vector <String > included, Vertex curr) {
        if(!included.contains(curr.getLabel())) {
            included.add(curr.getLabel());
            for (Map.Entry<Vertex, Edge> next : curr.getEdges().entrySet()) {
                dfs(included, next.getKey());
            }
        }
    }
    public boolean countComponent() {
        if(graph.isEmpty())
            return false;
        Vector <String> included = new Vector<String>();
        Vertex curr = graph.get(0);
        dfs(included, curr);
        return included.size() == graph.size();
    }

}
