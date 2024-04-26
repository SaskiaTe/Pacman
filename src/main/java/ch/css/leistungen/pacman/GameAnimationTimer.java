package ch.css.leistungen.pacman;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameAnimationTimer extends AnimationTimer {
    private final PacMan pacMan;
    private final GraphicsContext gc;

    public GameAnimationTimer(PacMan pacMan, GraphicsContext gc) {
        this.pacMan = pacMan;
        this.gc = gc;
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, 350, 350);
        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,350,350);
        pacMan.draw(gc);
    }
}
