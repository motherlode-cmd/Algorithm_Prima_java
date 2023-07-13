package com.example.demo;

import java.util.Vector;

public class AlgorithmView extends Observable {
    private String result;
    private Prim prim;
    private int step = 0;

    private Vector <String> vertexes = new Vector<String>();

    private Vector <String> coss = new Vector<String>();

    public AlgorithmView(Prim prim) {
        this.prim = prim;
        is_started = false;
        //prim.run();
        //result = prim.mstToString();
        //notify(Level.ALGORITHM, "SetGraph for algorithm steps ");
    }

    private boolean is_started = false;

    public boolean isStarted() {
        return is_started;
    }

    public String getResult() {
        return result;
    }

    public void setStartVertex(String vertex) {

    }

    public String nextStepVertex() {
        //if(!prim.getIncludedVertexStep(step).isEmpty())
        //    notify(Level.ALGORITHM, "Включенные в МОД на данном шаге вершины\n" + prim.getIncludedVertexStep(step));
        return prim.getIncludedVertexStep(step);
    }

    public String nextStepIncluded(){
        if(!prim.getIncludedEdgesStep(step).isEmpty())
            notify(Level.ALGORITHM, "Включенные на шаге " + step +  " в МОД рёбра :\n" + prim.getIncludedEdgesStep(step));
        return prim.getIncludedEdgesStep(step);
    }

    public String nextStepCandidate(){
        if(!prim.getCandidateStep(step).isEmpty())
            notify(Level.ALGORITHM, "Рёбра-кандидаты на шаге " + step + "\n смежные включенным в МОД вершинам:\n" + prim.getCandidateStep(step));
        return prim.getCandidateStep(step);
    }

    public String nextGrayEdges() {
        if(!prim.getGrayStep(step).isEmpty())
            notify(Level.ALGORITHM, "Рёбра, смежные включённым в МОД вершинам\n" + "на шаге " + step + ", но образующие цикл :\n" + prim.getGrayStep(step));
        return prim.getGrayStep(step);
    }
    public void next() {
        step = (step + 1) % getSize();
        notify(Level.ALGORITHM, "Шаг номер " + step);
    }
    private int getSize() {
        return prim.getSize();
    }
    public boolean isResult() {
        return (nextStepVertex().split(" ").length == prim.getSize() - 1);
    }
    public String getGraph() {
        return prim.getIncludedVertexStep(prim.getSize() - 1);
    }
    public void start(String start) {
        step = 0;
        prim.run(start);
        result = prim.mstToString();
        notify(Level.ALGORITHM, "Шаг номер 0");
        is_started = true;
        vertexes = prim.getVertexName();
        for(int i = 0; i < vertexes.size(); i++) {
            coss.add("inf");
        }
    }
    public void info() {
        if(prim.getIncludedVertexStep(step) != null && !prim.getIncludedVertexStep(step).equals(""))
            notify(Level.ALGORITHM, "Добавлена вершина " + prim.getIncludedVertexStep(step).split(" ")[ prim.getIncludedVertexStep(step).split(" ").length - 1]);
        if(prim.getIncludedEdgesStep(step) != null && !prim.getIncludedEdgesStep(step).equals(""))
            notify(Level.ALGORITHM, "Добавлено ребро " + prim.getIncludedEdgesStep(step).split("\n")[prim.getIncludedEdgesStep(step).split("\n").length - 1]);

        coss = prim.includedCost();
        String [] vert = prim.getIncludedVertexStep(step).split(" ");
        Vector <String> vecInc = new Vector<String>();

        for(int i = 0; i < vert.length; i++) {
            vecInc.add(vert[i]);
        }

        StringBuilder ans = new StringBuilder();

        for(int i = 0; i < vertexes.size(); i++) {
            if(vecInc.contains(vertexes.elementAt(i))) {
                for(String pair : coss) {
                    String [] p = pair.split(" ");
                    //System.out.println(p[0] + ";" + p[1]);
                    if(p.length > 1 && p[1].equals(vertexes.elementAt(i)))
                        ans.append(vertexes.elementAt(i)).append( " ").append(p[0]).append("\n");
                }
            } else {
                ans.append(vertexes.elementAt(i)).append( " ").append("inf").append("\n");
            }
        }
        if(!ans.isEmpty())
            notify(Level.ALGORITHM,"Множество вершин\n" + ans);
    }

}
