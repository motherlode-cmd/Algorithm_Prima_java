package com.example.demo;

public class AlgorithmView extends Observable{
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
        return prim.getIncludedVertexStep(step);
    }

    public String nextStepIncluded(){
        return prim.getIncludedEdgesStep(step);
    }

    public String nextStepCandidate(){
        return prim.getCandidateStep(step);
    }

    public String nextGrayEdges() {
        return prim.getGrayStep(step);
    }
    public void next() {
        notify(Level.ALGORITHM, "Step number " + step);
        step = (step + 1) % getSize();
    }
    private int getSize() {
        return prim.getSize();
    }
    public boolean isResult() {
        return result.split("\n").length == nextStepIncluded().split("\n").length;
    }
    public String getGraph() {
        return prim.getIncludedVertexStep(prim.getSize() - 1);
    }
    public void start(String start) {
        step = 0;
        prim.run(start);
        result = prim.mstToString();
        notify(Level.ALGORITHM, "SetGraph for algorithm steps ");
        is_started = true;
    }
}
