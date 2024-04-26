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

public class Game extends javafx.application.Application {
    private Scene scene;
    private Timeline timeline;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        final Canvas canvas = new Canvas(350,350);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,350,350);

        final PacMan pacMan = new PacMan(200, 200);

        final GameAnimationTimer gameAnimationTimer = new GameAnimationTimer(pacMan, gc);
        gameAnimationTimer.start();


        scene = new Scene(root, 350, 350);
        root.getChildren().addAll(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man");
        primaryStage.show();


        initKeyBindings(pacMan);


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
