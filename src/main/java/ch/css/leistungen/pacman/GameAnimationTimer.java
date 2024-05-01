package ch.css.leistungen.pacman;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameAnimationTimer extends AnimationTimer {
    private final GraphicsContext gc;
    private final List<GameElement> gameElementListe;
    public GameAnimationTimer(List<GameElement> gameElementListe, GraphicsContext gc) {
        this.gameElementListe = gameElementListe;
        this.gc = gc;
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, 350, 350);
        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,350,350);
        for (GameElement gameElement: gameElementListe) {
            gameElement.draw(gc);
        }
    }
}
