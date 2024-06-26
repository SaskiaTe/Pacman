package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;

public class PacMan implements drawAble {
    private int xWert;
    private int yWert;

    private boolean active  = true;

    public final InputStream pacmanRight = getClass().getClassLoader().getResourceAsStream("PacmanOffen Rechts.png");
    public final Image PacmanImageRight = new Image(pacmanRight);
    public final InputStream pacmanLeft = getClass().getClassLoader().getResourceAsStream("PacmanOffen Links.png");
    public final Image PacmanImageLeft = new Image(pacmanLeft);
    public final InputStream pacmanUp = getClass().getClassLoader().getResourceAsStream("PacmanOffen Oben.png");
    public final Image PacmanImageUp = new Image(pacmanUp);
    public final InputStream pacmanDown = getClass().getClassLoader().getResourceAsStream("PacmanOffen Unten.png");
    public final Image PacmanImageDown = new Image(pacmanDown);
    public final InputStream pacmanStop = getClass().getClassLoader().getResourceAsStream("PacmanGeschlossen.png");
    public final Image PacmanImageStop = new Image(pacmanStop);
    private Direction lastDirection = Direction.STOP;

    public PacMan(int xWert, int yWert) {
        this.xWert = xWert;
        this.yWert = yWert;
    }


    @Override
    public void draw(GraphicsContext gc) {
        switch (lastDirection){
            case RIGHT -> gc.drawImage(PacmanImageRight, xWert, yWert,  Game.GAMEFIELD_SIZE,  Game.GAMEFIELD_SIZE);
            case LEFT -> gc.drawImage(PacmanImageLeft, xWert, yWert,  Game.GAMEFIELD_SIZE,  Game.GAMEFIELD_SIZE);
            case UP -> gc.drawImage(PacmanImageUp, xWert, yWert,  Game.GAMEFIELD_SIZE,  Game.GAMEFIELD_SIZE);
            case DOWN -> gc.drawImage(PacmanImageDown, xWert, yWert,  Game.GAMEFIELD_SIZE,  Game.GAMEFIELD_SIZE);
            case STOP -> gc.drawImage(PacmanImageStop, xWert, yWert,  Game.GAMEFIELD_SIZE,  Game.GAMEFIELD_SIZE);
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
    public void deactivate(){
        active = false;
    }
    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setPacman(int newX, int newY){
        xWert = newX;
        yWert = newY;
    }
}
