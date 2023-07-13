package com.example.demo;

import java.util.*;

import javafx.util.Pair;

public class Prim {

    //Список вершин для хранения всего графа
    //Внутри каждой вершины у нас есть Map<Vertex, Edge>
    private List<Vertex> graph;

    public Prim(List<Vertex> graph){
        this.graph = graph;
    }

    public String getIncludedVertexStep(int step) {
        if(includedVertexStep.isEmpty())
            return null;
        return includedVertexStep.elementAt( step % includedVertexStep.size() );
    }

    private Vector <String> includedVertexStep = new Vector<String >();

    public String getIncludedEdgesStep(int step) {
        if(includedEdgesStep.isEmpty())
            return null;
        return includedEdgesStep.elementAt(step % includedEdgesStep.size());
    }

    private Vector <String > includedEdgesStep = new Vector<String >();

    public String getCandidateStep(int step) {
        if(candidateEdges.isEmpty())
            return null;
        return candidateEdges.elementAt(step % candidateEdges.size());
    }

    private Vector <String > candidateEdges = new Vector<String >();

    public String getGrayStep(int step) {
        if(grayEdges.isEmpty())
            return null;
        return grayEdges.elementAt(step % grayEdges.size());
    }

    private Vector <String > grayEdges = new Vector<String >();

    private void addVertexStep() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < graph.size(); i++)
            if(graph.get(i).isVisited()) {
                sb.append(graph.get(i).getLabel()).append(" ");
            }
        includedVertexStep.add(sb.toString());
    }
    private boolean isVertexIncluded(Vertex vertex) {
        if(vertex.getLabel().equals(startVertex))
            return true;
        for(Map.Entry <Vertex, Edge> neigbohour : vertex.getEdges().entrySet()) {
            if(neigbohour.getValue().isIncluded()) {
                return true;
            }
        }
        return false;
    }


    private void addEdgesCandidate() {
        StringBuilder sb = new StringBuilder();
        Vector <String> vertexes = new Vector<String >();
        for(Vertex vertex: graph) {
            if(isVertexIncluded(vertex)) {
                for(Map.Entry <Vertex, Edge> neigbohour : vertex.getEdges().entrySet()) {
                    if(!neigbohour.getValue().isIncluded() && !isVertexIncluded(neigbohour.getKey()) ) {
                        sb.append(vertex.getLabel()).append(" ").append(neigbohour.getKey().getLabel()).append("\n");
                    }
                }
            }
            vertexes.add(vertex.getLabel());
        }
        candidateEdges.add(sb.toString());
    }

    private void grayEdgesStep() {
        StringBuilder sb = new StringBuilder();
        Vector <String> vertexes = new Vector<String>();
        for(Vertex vertex : graph) {
            if(isVertexIncluded(vertex)) {
                for(Map.Entry <Vertex, Edge> neigb : vertex.getEdges().entrySet()) {
                    if(isVertexIncluded(neigb.getKey()) && !neigb.getValue().isIncluded() && !vertexes.contains(neigb.getKey().getLabel()) )
                        sb.append(vertex.getLabel()).append(" ").append(neigb.getKey().getLabel()).append("\n");
                }
            }
            vertexes.add(vertex.getLabel());
        }
        grayEdges.add(sb.toString());
    }

    private void addIncluded() {
        StringBuilder sb = new StringBuilder();
        Vector <String > included = new Vector<String>();
        for( Vertex vertex : graph) {
            if(vertex.isVisited()) {
                for(Map.Entry <Vertex, Edge> neigbohour : vertex.getEdges().entrySet()) {
                    if(neigbohour.getValue().isIncluded() && !included.contains(neigbohour.getKey().getLabel())) {
                        sb.append(vertex.getLabel()).append(" ").append(neigbohour.getKey().getLabel()).append("\n");
                    }
                }
                included.add(vertex.getLabel());
            }
        }

        includedEdgesStep.add(sb.toString());
    }


    String startVertex = "";
    public void run(String start) {
        //Устанавливаем первому элементу графа List<Vertex> статус посещенного
        //Первым элементом может быть любая из вершин в зависимости от порядка их добавления в список в приоритетную очередь
        int startPos = 0;
        for(int i = 0; i < graph.size(); i++)
            if(graph.get(i).getLabel().equals(start))
                startPos = i;
        if (graph.size() > 0){
            graph.get(startPos).setVisited(true);
        }
        startVertex = graph.get(startPos).getLabel();
        includedVertexStep.add(graph.get(startPos).getLabel());
        candidateEdges.add("");
        grayEdges.add("");
        includedEdgesStep.add("");
        while (isViewedVertex()){
            addVertexStep();
            addIncluded();
            addEdgesCandidate();
            grayEdgesStep();
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = graph.get(startPos);
            for (Vertex vertex : graph) { //Цикл продолжается до тех пор, пока не будут посещены все вершины
                if (vertex.isVisited()) {
                    Pair<Vertex, Edge> candidate = vertex.nextMinimum();
                    if (candidate.getValue().getWeight() < nextMinimum.getWeight()){//Поиск минимального кандидата
                        nextMinimum = candidate.getValue();
                        nextVertex = candidate.getKey();
                    }
                }
            }
            nextMinimum.setIncluded(true);
            nextVertex.setVisited(true);
        }
        addVertexStep();
        addIncluded();
        addEdgesCandidate();
        grayEdgesStep();
    }

    public int getSize() {
        return includedVertexStep.size();
    }
    public String getNeigbohour(String s) {
        for(int i = 0; i < graph.size(); i++)
            if(graph.get(i).getLabel().equals(s))
                return graph.get(i).originalToStr();
        return null;
    }

    public boolean isResult(String str) {
        return str.split(" ").length == graph.size();
    }

    //Вершину уже просмотрели
    //Возвращает true, если какая-либо вершина еще не посещена
    private boolean isViewedVertex(){
        for (Vertex vertex : graph){
            if (!vertex.isVisited()){
                return true;
            }
        }
        return false;
    }

    //Печатать начального графа
    public String originalGraphToStr(){
        StringBuilder str = new StringBuilder();
        for (Vertex vertex : graph){
            str.append(vertex.originalToStr());
        }
        return str.toString();
    }

    //Обновление статуса печати
    public void resetPrintStatus(){
        for (Vertex vertex : graph){
            Iterator<Map.Entry<Vertex,Edge>> it = vertex.getEdges().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Vertex,Edge> pair = it.next();
                pair.getValue().setPrinted(false);
            }
        }
    }

    //Пачать после применения алгоритма Прима
    public String mstToString(){
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : graph){
            sb.append(vertex.includedToStr());
        }
        return sb.toString();
    }

}