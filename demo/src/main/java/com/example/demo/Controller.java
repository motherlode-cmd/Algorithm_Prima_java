package com.example.demo;

import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller extends Observable {

    private Reader reader;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane scene;

    @FXML
    private Button loadBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button mvBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button delBtn;

    @FXML
    private Button solveBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button startBtn;

    @FXML
    private AnchorPane field;

    @FXML
    private TextArea logging;

    private GraphView graphView = new GraphView();


    @FXML
    public void dialog(String resource) throws IOException {
        reader = new AppReader(logging);
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource(resource));
        Scene dialog = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = new Stage();
        stage.setScene(dialog);
        //reader.setAction(str);
        ((Dialog)fxmlLoader.getController()).setReader(reader);
        ((Dialog)fxmlLoader.getController()).setObserver(obs);
        stage.showAndWait();
    }

    @FXML
    void addMouse(MouseEvent event) throws IOException {
        dialog("dialog.fxml");
        if( !reader.getV1().isEmpty() && !reader.getV2().isEmpty() && reader.getW() != 0 ) {
            graphView.counter_add(reader.getV1(), reader.getV2(), reader.getW(), field);
            addBtn.setText("Готово");
            notify(Level.CLUE, "Add " + graphView.getCounter_to_add() + "vertexes by click");
        }
    }

    @FXML
    void addCircle(double x, double y) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(mvBtn.getText().equals("Готово") && circle.getFill().equals(Color.ROSYBROWN)) {
                    circle.setCenterY(e.getSceneY() - field.getLayoutY());
                    circle.setCenterX(e.getSceneX() - field.getLayoutX());
                    graphView.updateEdges(circle);
                    graphView.updateText(circle);
                    circle.setFill(Color.ROSYBROWN);
                }
            }
        };

        EventHandler<MouseEvent> eventHandlerClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(circle.getFill().equals(Color.ROSYBROWN)) {
                    System.out.println("clicked");
                    circle.setFill(Color.DARKSLATEBLUE);
                    circle.setOnMouseMoved(null);
                } else if(mvBtn.getText().equals("Готово")){
                    circle.setOnMouseMoved(eventHandler);
                    circle.setFill(Color.ROSYBROWN);
                }
            }
        };
        circle.setOnMouseClicked(eventHandlerClick);
        //circle.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandlerClick);
        circle.setRadius(15);
        field.getChildren().add(circle);
        graphView.addCircle(circle, field);
    }

    @FXML
    void clickScene(MouseEvent event) {
        if(addBtn.getText().equals("Готово") && graphView.getCounter_to_add() > 0) {
            double x = event.getSceneX() - field.getLayoutX();
            double y = event.getSceneY() - field.getLayoutY();
            //if()
            String name = reader.getV1();
            addCircle(x, y);
        } else {
            addBtn.setText("Добавить");
        }
    }

    @FXML
    void load(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile != null) {
            reader = new FileReader(selectedFile);
            graphView = new GraphView();
            graphView.setObserver(obs);
            field.getChildren().clear();
            logging.clear();
            notify(Level.INPUT, "Load file");
            addEdge();
        }
    }
    void addEdge() {
        GenerateCoords gen = new GenerateCoords(field.getHeight()/2, field.getWidth()/2, field.getHeight()/2.5, reader.getN());
        int count = 0;
        for(int i = 0; i < reader.getN(); i++) {
            reader.read();
            if (!reader.getV1().isEmpty() && !reader.getV2().isEmpty() && reader.getW() != 0) {
                notify(Level.INPUT, "add " + reader.getV1() + " " + reader.getV2() + reader.getW());
                graphView.counter_add(reader.getV1(), reader.getV2(), reader.getW(), field);
                notify(Level.INPUT, "Will be added " + graphView.getCounter_to_add() + " vertexes");
                while ( graphView.getCounter_to_add() > 0) {
                    addCircle(gen.getX(count), gen.getY(count));
                    count++;
                }
            }
        }
    }

    @FXML
    void moveVertex(MouseEvent event) {
        notify(Level.CLUE, "Select vertex for move, click for end moving");
        mvBtn.setText(mvBtn.getText().equals("Готово") ? "Двигать" : "Готово");
    }

    @FXML
    void nextStep(MouseEvent event) {

    }

    @FXML
    void remove(MouseEvent event) throws IOException {
        dialog("remover.fxml");
        String v1 = reader.getV1(), v2 = reader.getV2();
        graphView.removeEdge(v1, v2, field);
    }

    @FXML
    void result(MouseEvent event) {
        Prim prim = new Prim(graphView.initGraph().getGraph());
        prim.run();
        String primRes = prim.mstToString();
        notify(Level.ALGORITHM,"\n" + primRes);
        graphView.primResult(primRes);
    }

    @FXML
    void save(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(graphView.toString());
            fw.close();
        } catch (Exception e) {

        }
    }

    @FXML
    void start(MouseEvent event) {
        startBtn.setText("Готово");
    }

    @FXML
    void initialize() {
        assert scene != null : "fx:id=\"scene\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert loadBtn != null : "fx:id=\"loadBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert delBtn != null : "fx:id=\"delBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert solveBtn != null : "fx:id=\"solveBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert nextBtn != null : "fx:id=\"nextBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert startBtn != null : "fx:id=\"startBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert field != null : "fx:id=\"field\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert logging != null : "fx:id=\"logging\" was not injected: check your FXML file 'hello-view.fxml'.";
        if(logging != null) reader = new AppReader(logging);
        obs = new ObserverTextArea();
        ((ObserverTextArea)obs).setTextArea(logging);
        notify(Level.INPUT, "Sucsess");
        graphView.setObserver(obs);

    }
}
