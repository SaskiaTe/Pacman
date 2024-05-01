package ch.css.leistungen.pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;

import static ch.css.leistungen.pacman.PacMan.RADIUS;

public class Game extends javafx.application.Application {
    private Scene scene;
    private final int gamefieldX = 0;
    private final int gamefieldY = 0;
    private final int gamefieldWidth = 350;
    private final int gamefieldHeight = 350;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        final Canvas canvas = new Canvas(350,350);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(0,0,350,350);

        final PacMan pacMan = new PacMan(200, 200);
        final ScorePoint scorepoint = new ScorePoint(250, 250);
        final ScorePoint scorepoint2 = new ScorePoint(210, 210);

        scorepoint.deactivate();

        List<GameElement> gameElementListe = Arrays.asList(pacMan,scorepoint, scorepoint2);

        final GameAnimationTimer gameAnimationTimer = new GameAnimationTimer(gameElementListe, gc);
        gameAnimationTimer.start();

        scene = new Scene(root, 350, 350);
        root.getChildren().addAll(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man");
        primaryStage.show();

        initKeyBindings(pacMan);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handlePacman(pacMan);

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void handlePacman(PacMan pacMan) {
        double xWert = pacMan.xWert();
        int yWert = pacMan.yWert();

        double leftBound = gamefieldX + RADIUS;
        double rightBound = gamefieldX + gamefieldWidth - 3 * RADIUS;
        double topBound = gamefieldY + RADIUS;
        double bottomBound = gamefieldY + gamefieldHeight - 3 * RADIUS;

        switch (pacMan.getLastDirection()) {
            case STOP -> {
            }
            case RIGHT -> {
                if (xWert <= rightBound) {
                    pacMan.moveOneStep();
                }
            }
            case LEFT -> {
                if (xWert >= leftBound) {
                    pacMan.moveOneStep();
                }
            }
            case UP -> {
                if (yWert >= topBound) {
                    pacMan.moveOneStep();
                }
            }
            case DOWN -> {
                if (yWert <= bottomBound) {
                    pacMan.moveOneStep();
                }
            }
        }
    }

    private void initKeyBindings(PacMan pacman) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT -> pacman.whichDirection(Direction.LEFT);
                    case RIGHT -> pacman.whichDirection(Direction.RIGHT);
                    case UP -> pacman.whichDirection(Direction.UP);
                    case DOWN -> pacman.whichDirection(Direction.DOWN);
                }
            }
        });
    }

    public void run() {
        launch();
    }
}
