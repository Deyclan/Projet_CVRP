package Utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class AleaColorChooser {

    private ArrayList<Paint> colors = new ArrayList<>();
    private int lastChoosed = -1;

    public AleaColorChooser(){
        colors.add(Color.RED);
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.VIOLET);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.ORANGE);
        colors.add(Color.CYAN);
        colors.add(Color.BROWN);
        colors.add(Color.PINK);
    }

    public Paint getNextColor(){
        if (lastChoosed == -1){
            lastChoosed = 0;
        }
        else {
            lastChoosed = (lastChoosed + 1) % colors.size();
        }
        return colors.get(lastChoosed);
    }

}
