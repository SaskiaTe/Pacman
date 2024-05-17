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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Game extends javafx.application.Application {
    private Scene scene;
    private final int gameX = 0;
    private final int gameY = 0;
    private static final int GAME_SIZE = 340;
    public static final int GAMEFIELD_SIZE = 20;

    private int score = 0;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        final Canvas canvas = new Canvas(GAME_SIZE,GAME_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(0,0, GAME_SIZE,GAME_SIZE);

        final PacMan pacMan = new PacMan(0, 0);
        final List<ScorePoint> scorePoints = createScorepoints();

        final List<GameElement> gameElementListe = new ArrayList<>(scorePoints);
        gameElementListe.add(pacMan);

        final GameAnimationTimer gameAnimationTimer = new GameAnimationTimer(gameElementListe, gc);
        gameAnimationTimer.start();

        scene = new Scene(root, GAME_SIZE, GAME_SIZE);
        root.getChildren().addAll(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man");
        primaryStage.show();

        initKeyBindings(pacMan);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handlePacman(pacMan);
                for (ScorePoint scorePoint : scorePoints) {
                    if (isPacmanOnScorepoint(pacMan, scorePoint)) {
                        scorePoint.deactivate();
                        if (!scorePoint.isPointGiven()) {
                            score = score + 10;
                            System.out.println(score);
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private List<ScorePoint> createScorepoints() {
        final List<ScorePoint> scorepoints = new ArrayList<>();

        for (int i = 0; i <= GAME_SIZE; i = i+20){
            for (int j = 0; j <= GAME_SIZE; j = j+20){
                final ScorePoint scorepoint = new ScorePoint(i, j);
                scorepoints.add(scorepoint);
            }
        }
        return scorepoints;
    }

    private void handlePacman(PacMan pacMan) {
        double xWert = pacMan.xWert();
        int yWert = pacMan.yWert();

        double leftBound = GAMEFIELD_SIZE/2;
        double rightBound = GAME_SIZE - 2 * GAMEFIELD_SIZE;
        double topBound = GAMEFIELD_SIZE/2;
        double bottomBound = GAME_SIZE - 2 * GAMEFIELD_SIZE;

        switch (pacMan.getLastDirection()) {
            case STOP -> {
            }
            case RIGHT -> {
                if (xWert <= rightBound) {
                    pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
            case LEFT -> {
                if (xWert >= leftBound) {
                    pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
            case UP -> {
                if (yWert >= topBound) {
                    pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
            case DOWN -> {
                if (yWert <= bottomBound) {
                    pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
        }
    }

    public boolean isPacmanOnScorepoint(PacMan pacman, ScorePoint scorePoint){
        final int scorePointX = scorePoint.getXWert();
        final int scorePointY = scorePoint.getYWert();
        final int pacmanX = pacman.xWert();
        final int pacmanY = pacman.yWert();
        if (scorePointX == pacmanX && scorePointY == pacmanY ){
            return true;
        }
        return false;
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
