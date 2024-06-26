package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle implements drawAble {

    private int yWert;
    private int xWert;
    private int width = 20;
    private int height = 20;
    public Obstacle(int xWert, int yWert) {
        this.xWert = xWert;
        this.yWert = yWert;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(xWert, yWert, width, height);

    }


    public int xWert() {
        return xWert;
    }
    public int yWert() {
        return yWert;
    }
}

