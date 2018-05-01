package HIM;

import Utils.Client;
import Utils.Solution;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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


    private final int graphicWidth = 600 ;
    private final int graphicHeigh = 850 ;

    private final int facteurWidth = 6;
    private final int facteurHeigh = 8;

    public Graph(Solution solution){
        this.solution = solution;
        this.root = new Group();
        this.scene = new Scene(root,graphicWidth, graphicHeigh);
        this.setWidth(graphicWidth);
        this.setHeight(graphicHeigh);
        this.setTitle("Graph");

        pointList = new ArrayList<>();
        clientList = new ArrayList<>();
        lines = new ArrayList<>();

        for (List<Client> tournee :solution.getTournees()) {
            ArrayList<Shape> tmpPointList = new ArrayList<>();
            for (Client client: tournee) {
                if (!clientList.contains(client)) {
                    clientList.add(client);
                    if (tmpPointList.size() == 0) {
                        tmpPointList.add(new Rectangle(client.getX()*facteurWidth, client.getY()*facteurHeigh, 2, 2));
                    } else {
                        tmpPointList.add(new Rectangle(client.getX()*facteurWidth, client.getY()*facteurHeigh, 2, 2));
                        Shape p1 = tmpPointList.get(tmpPointList.size() - 2);
                        Shape p2 = tmpPointList.get(tmpPointList.size()-1);
                        lines.add(new Line(p1.getLayoutX()*facteurWidth, p1.getLayoutY()*facteurHeigh, p2.getLayoutX()*facteurWidth, p2.getLayoutY()*facteurHeigh));
                    }
                }
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
