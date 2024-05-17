package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacMan implements GameElement {
    private int xWert;
    private int yWert;

    private Direction lastDirection = Direction.STOP;

    public PacMan(int xWert, int yWert) {
        this.xWert = xWert;
        this.yWert = yWert;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(xWert, yWert, Game.GAMEFIELD_SIZE , Game.GAMEFIELD_SIZE);
    }

    public void whichDirection(Direction direction){
        lastDirection = direction;
    }

    public void moveOneStep(int stepSize) {
        switch (lastDirection) {
            case LEFT -> xWert -= stepSize;
            case RIGHT -> xWert += stepSize;
            case UP -> yWert -= stepSize;
            case DOWN -> yWert += stepSize;
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
