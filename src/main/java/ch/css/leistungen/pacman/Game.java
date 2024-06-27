package ch.css.leistungen.pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game extends javafx.application.Application {
    private static final int SPEED = 300;
    private final PacMan pacMan = new PacMan(0, 0);
    private final HashMap<Ghost, List<int[]>> pathByGhostMap = new HashMap<>();
    private Scene scene;
    public static final int GAME_SIZE = 340;
    public static final int GAMEFIELD_SIZE = 20;
    private int score;

    private int life = 3;

    private final List<ScorePoint> scorePoints = new ArrayList<>();

    private final List<MagicFruit> magicFruits = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws
            Exception {
        Group root = new Group();

        final Canvas canvas = new Canvas(GAME_SIZE, GAME_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Text title = new Text("PacMan");
        Text scoreText = new Text(String.valueOf(score));
        Text lifeText = new Text(String.valueOf(life));

        ImageView imgView = new ImageView();
        Rectangle2D viewportRect = new Rectangle2D(170, 80, 120, 300);
        imgView.setViewport(viewportRect);


        VBox menu = new VBox(60);
        menu.getChildren().add(title);
        title.fontProperty().set(Font.font(40));
        menu.getChildren().add(scoreText);
        scoreText.fontProperty().set(Font.font(30));
        menu.getChildren().add(lifeText);
        lifeText.fontProperty().set(Font.font(20));
        menu.alignmentProperty().set(Pos.CENTER);
        menu.getChildren().add(imgView);

        HBox gameSide = new HBox();
        gameSide.getChildren().addAll(canvas);
        gameSide.getChildren().add(menu);

        final Ghost redGhost = new Ghost(320, 0, GhostColor.RED);
        final Ghost blueGhost = new Ghost(140, 180, GhostColor.BLUE);
        final Ghost greenGhost = new Ghost(280, 300, GhostColor.GREEN);
        final MagicFruit Cherry = new MagicFruit(320, 60, 20, 20, Food.CHERRY);
        final MagicFruit Strawberry = new MagicFruit(220, 320, 20, 20, Food.STRAWBERRY);
        List<String[]> mapList = createMapList(map);
        createMap(mapList);

        magicFruits.add(Cherry);
        magicFruits.add(Strawberry);

        final List<drawAble> drawAbleListe = new ArrayList<>(scorePoints);
        drawAbleListe.add(redGhost);
        drawAbleListe.add(blueGhost);
        drawAbleListe.add(greenGhost);
        drawAbleListe.add(pacMan);
        drawAbleListe.addAll(obstacles);
        drawAbleListe.addAll(magicFruits);

        pathByGhostMap.put(redGhost, GhostPaths.RED_GHOST_PATH);
        pathByGhostMap.put(blueGhost, GhostPaths.BLUE_GHOST_PATH);
        pathByGhostMap.put(greenGhost, GhostPaths.GREEN_GHOST_PATH);

        final GameAnimationTimer gameAnimationTimer = new GameAnimationTimer(drawAbleListe, gc);
        gameAnimationTimer.start();


        scene = new Scene(root, 600, GAME_SIZE);
        root.getChildren().add(gameSide);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man");
        primaryStage.show();

        initKeyBindings(pacMan);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(SPEED), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handlePacman(pacMan);
                for (Map.Entry<Ghost, List<int[]>> entry : pathByGhostMap.entrySet()) {
                    handleGhost(entry.getKey(), entry.getValue());
                }

                lifeText.setText(String.valueOf(life));
                if (score == 1410) {
                    Platform.exit();
                } else {
                    for (ScorePoint scorePoint : scorePoints) {
                        if (isPacmanOnScorepoint(pacMan, scorePoint)) {
                            if (scorePoint.isActive()) {
                                scorePoint.deactivate();
                                score = score + 10;
                                System.out.println(score);
                                scoreText.setText(String.valueOf(score));
                            }
                        }
                    }
                    for (MagicFruit magicFruit : magicFruits) {
                        if (isPacmanOnFruit(pacMan, magicFruit)) {
                            if (magicFruit.isActive()) {
                                magicFruit.deactivate();
                                score = score + 30;
                                System.out.println(score);
                                scoreText.setText(String.valueOf(score));
                            }
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    String map = """
            P,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,
            S,O,S,O,O,O,O,S,O,O,O,S,O,O,O,O,S,
            S,O,S,O,O,O,O,S,O,O,O,S,O,O,O,O,S,
            S,O,S,O,O,O,O,S,S,S,S,S,O,O,O,O,S,
            S,O,S,O,O,O,O,S,O,S,O,S,S,S,S,S,S,
            S,O,S,O,S,S,S,S,O,S,O,O,O,S,O,O,O,
            S,O,S,O,S,O,O,O,O,S,O,O,O,S,O,O,O,
            S,O,S,O,S,O,O,O,O,S,O,O,O,S,S,S,S,
            S,S,S,O,S,S,S,S,O,S,O,O,O,O,O,O,S,
            O,O,S,O,O,O,O,S,S,S,S,S,S,S,S,S,S,
            O,O,S,S,S,S,S,S,O,O,O,O,O,O,O,O,S,
            S,S,S,S,S,O,O,O,O,O,S,S,S,S,S,S,S,
            S,O,O,O,S,O,O,O,O,O,S,O,O,O,S,O,O,
            S,O,O,O,S,O,O,O,O,O,S,O,O,O,S,O,O,
            S,O,O,O,S,O,O,O,O,O,S,O,O,O,S,O,O,
            S,S,S,S,S,S,S,S,O,O,S,S,S,S,S,O,O,
            S,O,O,O,O,O,O,S,S,S,S,S,O,O,O,O,O,
            """;

    private static List<String[]> createMapList(String map) {
        List<String[]> mapList = new ArrayList<>();
        String[] rows = map.split("\n");
        for (String row : rows) {
            mapList.add(row.split("\\s*,\\s*"));
        }
        return mapList;
    }

    private void createMap(List<String[]> mapList) {
        int y = 0;
        int x = 0;
        for (String[] row : mapList) {
            for (String field : row) {
                if (field.equals("S")) {
                    scorePoints.add(new ScorePoint(x, y));
                } else if (field.equals("O")) {
                    obstacles.add(new Obstacle(x, y));
                }
                x = x + 20;
            }
            y = y + 20;
            x = 0;
        }
    }


    private void handlePacman(PacMan pacMan) {
        double xWert = pacMan.xWert();
        int yWert = pacMan.yWert();

        double leftBound = GAMEFIELD_SIZE / 2;
        double rightBound = GAME_SIZE - 2 * GAMEFIELD_SIZE;
        double topBound = GAMEFIELD_SIZE / 2;
        double bottomBound = GAME_SIZE - 2 * GAMEFIELD_SIZE;

        switch (pacMan.getLastDirection()) {
            case STOP -> {
            }
            case RIGHT -> {
                if (xWert <= rightBound) {
                    if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.RIGHT)) {
                        pacMan.whichDirection(Direction.STOP);
                    } else
                        pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
            case LEFT -> {
                if (xWert >= leftBound) {
                    if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.LEFT)) {
                        pacMan.whichDirection(Direction.STOP);
                    } else {
                        pacMan.moveOneStep(GAMEFIELD_SIZE);
                    }
                }
            }
            case UP -> {
                if (yWert >= topBound) {
                    if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.UP)) {
                        pacMan.whichDirection(Direction.STOP);
                    } else
                        pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
            case DOWN -> {
                if (yWert <= bottomBound) {
                    if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.DOWN)) {
                        pacMan.whichDirection(Direction.STOP);
                    } else
                        pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }

            default -> {
                if (xWert <= rightBound) {
                    if (isPacmanInfrontObstacle(pacMan, obstacles, pacMan.getLastDirection())) {
                        pacMan.whichDirection(Direction.STOP);
                    } else
                        pacMan.moveOneStep(GAMEFIELD_SIZE);
                }
            }
        }
    }

    private void handlePacmanLifeAndReset() {
        if (life > 1) {
            life = life - 1;
        } else {
            Platform.exit();
        }
        pacMan.whichDirection(Direction.STOP);
        pacMan.setPacman(0, 0);
        pathByGhostMap.keySet().forEach(Ghost::reset);
    }

    public boolean isPacmanOnScorepoint(PacMan pacMan, ScorePoint scorePoint) {
        final int scorePointX = scorePoint.getXWert();
        final int scorePointY = scorePoint.getYWert();
        final int pacManX = pacMan.xWert();
        final int pacManY = pacMan.yWert();
        if (scorePointX == pacManX && scorePointY == pacManY) {
            return true;
        }
        return false;
    }

    public boolean isPacmanOnFruit(PacMan pacMan, MagicFruit magicFruit) {
        final int magicFruitX = magicFruit.xWert();
        final int magicFruitY = magicFruit.yWert();
        final int pacManX = pacMan.xWert();
        final int pacManY = pacMan.yWert();
        if (magicFruitX == pacManX && magicFruitY == pacManY) {
            return true;
        }
        return false;
    }

    public boolean isPacmanInfrontObstacle(PacMan pacman, List<Obstacle> obstacles, Direction direction) {
        boolean bool = false;
        for (Obstacle obstacle : obstacles) {
            final int obstacleX = obstacle.xWert();
            final int obstacleY = obstacle.yWert();
            final int pacmanX = pacman.xWert();
            final int pacmanY = pacman.yWert();
            switch (direction) {
                case RIGHT -> {
                    if (obstacleX == pacmanX + 20 && obstacleY == pacmanY) {
                        bool = true;
                    }
                }
                case LEFT -> {
                    if (obstacleX == pacmanX - 20 && obstacleY == pacmanY) {
                        bool = true;
                    }
                }
                case UP -> {
                    if (obstacleX == pacmanX && obstacleY == pacmanY - 20) {
                        bool = true;
                    }
                }
                case DOWN -> {
                    if (obstacleX == pacmanX && obstacleY == pacmanY + 20) {
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }

    private void initKeyBindings(PacMan pacman) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT -> {
                        pacman.whichDirection(Direction.LEFT);
                    }
                    case RIGHT -> {
                        pacman.whichDirection(Direction.RIGHT);
                    }

                    case UP -> {
                        pacman.whichDirection(Direction.UP);
                    }
                    case DOWN -> {
                        pacman.whichDirection(Direction.DOWN);
                    }
                }
            }
        });
    }

    private void handleGhost(Ghost ghost, List<int[]> ghostPath) {

        if (ghost.getPathIndex() >= ghostPath.size()) {
            ghost.setPathIndex(0);
        }
        int[] target = ghostPath.get(ghost.getPathIndex());
        int targetX = target[0];
        int targetY = target[1];

        double xWert = ghost.xWert();
        double yWert = ghost.yWert();

        Direction newDirection = ghost.getLastDirection();

        if (Math.abs(xWert - targetX) < GAMEFIELD_SIZE && Math.abs(yWert - targetY) < GAMEFIELD_SIZE) {
            ghost.incrementPathIndex();
            target = ghostPath.get(ghost.getPathIndex());
            targetX = target[0];
            targetY = target[1];
        }

        if (xWert < targetX) {
            newDirection = Direction.RIGHT;
        } else if (xWert > targetX) {
            newDirection = Direction.LEFT;
        } else if (yWert < targetY) {
            newDirection = Direction.DOWN;
        } else if (yWert > targetY) {
            newDirection = Direction.UP;
        }

        if (isGhostInfrontObstacle(ghost, obstacles, newDirection)) {
            ghost.whichDirection(Direction.STOP);
        } else {
            ghost.whichDirection(newDirection);
        }
        if(arePacmanAndGhostOnSameField(ghost)){
            handlePacmanLifeAndReset();
        }

        ghost.moveOneStep(GAMEFIELD_SIZE);
        if(arePacmanAndGhostOnSameField(ghost)){
            handlePacmanLifeAndReset();
        }
    }



    public boolean isGhostInfrontObstacle(Ghost ghost, List<Obstacle> obstacles, Direction direction) {
        boolean bool = false;
        for (Obstacle obstacle : obstacles) {
            final int obstacleX = obstacle.xWert();
            final int obstacleY = obstacle.yWert();
            final int ghostX = ghost.xWert();
            final int ghostY = ghost.yWert();
            switch (direction) {
                case RIGHT -> {
                    if (obstacleX == ghostX + 20 && obstacleY == ghostY) {
                        bool = true;
                    }
                }
                case LEFT -> {
                    if (obstacleX == ghostX - 20 && obstacleY == ghostY) {
                        bool = true;
                    }
                }
                case UP -> {
                    if (obstacleX == ghostX && obstacleY == ghostY - 20) {
                        bool = true;
                    }
                }
                case DOWN -> {
                    if (obstacleX == ghostX && obstacleY == ghostY + 20) {
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }

    public boolean arePacmanAndGhostOnSameField(Ghost ghost) {
        return ghost.xWert() == pacMan.xWert() && ghost.yWert() == pacMan.yWert();
    }

    public void run() {
        launch();
    }
}
