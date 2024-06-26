package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;

public class Ghost implements drawAble {
    private final GhostColor color;
    private int xWert;
    private int yWert;
    private int pathIndex = 0;

    private Direction lastDirection = Direction.STOP;

    public final InputStream blueGhost = getClass().getClassLoader().getResourceAsStream("blauerGeist.png");
    public final Image blueGhostImage = new Image(blueGhost);
    public final InputStream redGhost = getClass().getClassLoader().getResourceAsStream("roterGeist.png");
    public final Image redGhostImage = new Image(redGhost);
    public final InputStream greenGhost = getClass().getClassLoader().getResourceAsStream("greenGeist.png");
    public final Image greenGhostImage = new Image(greenGhost);

    public Ghost(int xWert, int yWert, GhostColor color) {
        this.xWert = xWert;
        this.yWert = yWert;
        this.color = color;
    }


    @Override
    public void draw(GraphicsContext gc) {
        switch (color){
            case RED -> gc.drawImage(redGhostImage, xWert, yWert, 20, 20);
            case BLUE -> gc.drawImage(blueGhostImage, xWert, yWert, 20, 20);
            case GREEN -> gc.drawImage(greenGhostImage, xWert, yWert, 20, 20);
        }
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

    public void setGhost(int newX, int newY){
        xWert = newX;
        yWert = newY;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void incrementPathIndex() {
        pathIndex++;
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }
}


