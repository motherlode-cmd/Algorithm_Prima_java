package com.example.demo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class Prim {

    //Список вершин для хранения всего графа
    //Внутри каждой вершины у нас есть Map<Vertex, Edge>
    private List<Vertex> graph;

    public Prim(List<Vertex> graph){
        this.graph = graph;
    }

    public void run() {
        //Устанавливаем первому элементу графа List<Vertex> статус посещенного
        //Первым элементом может быть любая из вершин в зависимости от порядка их добавления в список в приоритетную очередь
        if (graph.size() > 0){
            graph.get(0).setVisited(true);
        }
        while (isViewedVertex()){
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = graph.get(0);
            for (Vertex vertex : graph){ //Цикл продолжается до тех пор, пока не будут посещены все вершины
                if (vertex.isVisited()){
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