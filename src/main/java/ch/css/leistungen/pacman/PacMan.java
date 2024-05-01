package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacMan implements GameElement {
    private int xWert;
    private int yWert;

    public static final double RADIUS = 12.5;
    private Direction lastDirection = Direction.STOP;

    public PacMan(int xWert, int yWert) {
        this.xWert = xWert;
        this.yWert = yWert;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(xWert, yWert, RADIUS*2 , RADIUS*2);
    }

    public void whichDirection(Direction direction){
        lastDirection = direction;
    }

    public void moveOneStep() {
        switch (lastDirection) {
            case LEFT -> xWert -= 10;
            case RIGHT -> xWert += 10;
            case UP -> yWert -= 10;
            case DOWN -> yWert += 10;
        }
    }

    public int xWert() {
        return xWert;
    }

    public int yWert() {
        return yWert;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }
}
