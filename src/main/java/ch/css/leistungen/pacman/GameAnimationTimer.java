package ch.css.leistungen.pacman;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameAnimationTimer extends AnimationTimer {
    private final GraphicsContext gc;
    private final List<drawAble> drawAbleListe;
    public GameAnimationTimer(List<drawAble> drawAbleListe, GraphicsContext gc) {
        this.drawAbleListe = drawAbleListe;
        this.gc = gc;
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, Game.GAME_SIZE, Game.GAME_SIZE);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,Game.GAME_SIZE,Game.GAME_SIZE);
        for (drawAble drawAble : drawAbleListe) {
            drawAble.draw(gc);
        }
    }
}
