package HIM;

import Utils.AleaColorChooser;
import Utils.Client;
import Utils.Solution;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;


public class Graph extends Stage {

    private Solution solution;
    private Group root;
    private Scene scene;
    private Pane mainPanel;
    private ArrayList<Shape> pointList;
    private ArrayList<Client> clientList;
    private ArrayList<Shape> lines;


    private final int graphicWidth = 650 ;
    private final int graphicHeigh = 850 ;

    private final int facteurWidth = 6;
    private final int facteurHeigh = 8;

    public Graph(Solution solution, String title){
        this.solution = solution;
        this.root = new Group();
        this.scene = new Scene(root,graphicWidth, graphicHeigh);
        this.setWidth(graphicWidth);
        this.setHeight(graphicHeigh);
        this.setTitle(title);
        this.setResizable(false);

        pointList = new ArrayList<>();
        clientList = new ArrayList<>();
        lines = new ArrayList<>();

        AleaColorChooser colorChooser = new AleaColorChooser();
        for (List<Client> tournee :solution.getTournees()) {
            ArrayList<Shape> tmpPointList = new ArrayList<>();
            Client lastClient = null;
            Paint paint = colorChooser.getNextColor();
            for (Client client: tournee) {
                //if (!clientList.contains(client)) {
                    clientList.add(client);
                    Rectangle rectangle = null;
                    if (client.getId()==0){
                        rectangle = new Rectangle(client.getX()*facteurWidth, client.getY()*facteurHeigh, 7, 7);
                        rectangle.setStroke(Color.RED);
                    }else{
                        rectangle = new Rectangle(client.getX()*facteurWidth, client.getY()*facteurHeigh, 3, 3);
                    }
                    if (tmpPointList.size() == 0) {
                        tmpPointList.add(rectangle);
                        lastClient = client;
                    } else {
                        tmpPointList.add(rectangle);
                        Line line = new Line(lastClient.getX()*facteurWidth, lastClient.getY()*facteurHeigh, client.getX()*facteurWidth, client.getY()*facteurHeigh);
                        line.setStroke(paint);
                        lines.add(line);
                        lastClient = client;
                    }
                /*
                }else {
                    lastClient = client;
                }
                */
            }
            pointList.addAll(tmpPointList);
        }

        for (Shape point : pointList) {
            root.getChildren().add(point);
        }
        for (Shape line : lines){
            root.getChildren().add(line);
        }

        this.setScene(scene);
        this.show();
    }

}
