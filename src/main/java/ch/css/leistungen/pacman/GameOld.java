package ch.css.leistungen.pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOld extends javafx.application.Application {
    private Rectangle gamefield;
    private Circle pacMan;
    private Circle scorepoint;
    private Text score;
    private int actualScore;
    private double pacManRadius;
    private double deltaX;
    private double deltaY;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle background = new Rectangle(100, 50, 650, 500);
        gamefield = new Rectangle(250, 140, 350, 350);
        pacMan = new Circle(260, 200, 10);
        pacManRadius = pacMan.getRadius();
        actualScore = 2;
        score = new Text(150, 100, "Score:   " + actualScore);
        scorepoint = new Circle(260, 150, 3);



        // setting the color and stroke for the Rectangles
        background.setFill(Color.BLACK);
        background.setStroke(Color.BLACK);
        gamefield.setStroke(Color.BLUE);
        pacMan.setFill(Color.YELLOW);
        pacMan.setStroke(Color.YELLOW);
        score.setStroke(Color.PALEGOLDENROD);
        scorepoint.setFill(Color.PALEGOLDENROD);
        scorepoint.setStroke(Color.PALEGOLDENROD);

        Group root = new Group();
        root.getChildren().addAll(background, gamefield, pacMan, score, scorepoint);
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man");
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT:
                        deltaX = -10;
                        deltaY = 0;
                        break;
                    case RIGHT:
                        deltaX = 10;
                        deltaY = 0;
                        break;
                    case UP:
                        deltaX = 0;
                        deltaY = -10;
                        break;
                    case DOWN:
                        deltaX = 0;
                        deltaY = 10;
                        break;
                }
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double newX = pacMan.getCenterX() + deltaX;
                double newY = pacMan.getCenterY() + deltaY;

                // Check if new position is within the gamefield
                double leftBound = gamefield.getX() + pacManRadius;
                double rightBound = gamefield.getX() + gamefield.getWidth() - pacManRadius;
                double topBound = gamefield.getY() + pacManRadius;
                double bottomBound = gamefield.getY() + gamefield.getHeight() - pacManRadius;

                if (newX >= leftBound && newX <= rightBound &&
                        newY >= topBound && newY <= bottomBound) {
                    pacMan.setCenterX(newX);
                    pacMan.setCenterY(newY);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void run() {
        launch();
    }
}
