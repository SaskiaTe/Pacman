package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ScorePoint implements GameElement {
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
            gc.fillOval(xWert, yWert, ScorepointRADIUS * 2, ScorepointRADIUS * 2);
        }
    }

    public void deactivate(){
        active = false;
    }

}
