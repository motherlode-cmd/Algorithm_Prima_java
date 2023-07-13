package com.example.demo;

public class AlgorithmView extends Observable {
    private String result;
    private Prim prim;
    private int step = 0;
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
    }

}
