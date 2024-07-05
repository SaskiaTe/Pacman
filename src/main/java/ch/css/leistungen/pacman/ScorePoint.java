package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ScorePoint implements drawAble {
    public static final int ScorepointRADIUS = 5;
    private int xWert;
    private int yWert;
    private boolean active  = true;

    public ScorePoint(int xWert, int yWert){
        this.xWert = xWert;
        this.yWert = yWert;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (active) {
            gc.setFill(Color.DARKGOLDENROD);
            gc.fillOval(xWert + 5, yWert + 5, ScorepointRADIUS * 2, ScorepointRADIUS * 2);
        }
    }

    public int getXWert(){
        int xwert = xWert;
        return xwert;
    }
    public int getYWert(){
        int ywert = yWert;
        return ywert;
    }
    public void deactivate(){
        active = false;
    }

    public boolean isActive() {
        return active;
    }
    public void reset(){
        active = true;
    }
}

