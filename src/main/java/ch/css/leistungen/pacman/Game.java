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
import java.util.List;


public class Game extends javafx.application.Application {
    private static final int SPEED = 150;
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
        final Ghost blueGhost = new Ghost(140, 180,  GhostColor.BLUE);
        final Ghost greenGhost = new Ghost(280, 300,  GhostColor.GREEN);
        final PacMan pacMan = new PacMan(0, 0);
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
                handlePacman(pacMan, blueGhost, redGhost, greenGhost);
                lifeText.setText(String.valueOf(life));
                handleBlueGhost(blueGhost);
                handlePinkGhost(redGhost);
                handleGreenGhost(greenGhost);
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


    private void handlePacman(PacMan pacMan, Ghost blueGhost, Ghost pinkGhost, Ghost greenGhost) {
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
                    if (isPacmanInGhost(pacMan, blueGhost) || isPacmanInGhost(pacMan, pinkGhost) || isPacmanInGhost(pacMan, greenGhost)) {
                        if (life > 1) {
                            life = life - 1;
                        } else {
                            Platform.exit();
                        }
                        pacMan.whichDirection(Direction.STOP);
                        pacMan.setPacman(0, 0);
                        blueGhost.setGhost(140, 180);
                        blueGhost.setPathIndex(0);
                        pinkGhost.setGhost(320, 0);
                        pinkGhost.setPathIndex(0);
                        greenGhost.setGhost(280, 300);
                        greenGhost.setPathIndex(0);
                    } else {
                        if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.RIGHT)) {
                            pacMan.whichDirection(Direction.STOP);
                        } else
                            pacMan.moveOneStep(GAMEFIELD_SIZE);
                    }
                }
            }
            case LEFT -> {
                if (xWert >= leftBound) {
                    if (isPacmanInGhost(pacMan, blueGhost) || isPacmanInGhost(pacMan, pinkGhost) || isPacmanInGhost(pacMan, greenGhost)) {
                        if (life > 1) {
                            life = life - 1;
                        } else {
                            Platform.exit();
                        }
                        pacMan.whichDirection(Direction.STOP);
                        pacMan.setPacman(0, 0);
                        blueGhost.setGhost(140, 180);
                        blueGhost.setPathIndex(0);
                        pinkGhost.setGhost(320, 0);
                        pinkGhost.setPathIndex(0);
                        greenGhost.setGhost(280, 300);
                        greenGhost.setPathIndex(0);
                    } else {
                        if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.LEFT)) {
                            pacMan.whichDirection(Direction.STOP);
                        } else {
                            pacMan.moveOneStep(GAMEFIELD_SIZE);
                        }
                    }


                }
            }
            case UP -> {
                if (yWert >= topBound) {
                    if (isPacmanInGhost(pacMan, blueGhost) || isPacmanInGhost(pacMan, pinkGhost) || isPacmanInGhost(pacMan, greenGhost)) {
                        if (life > 1) {
                            life = life - 1;
                        } else {
                            Platform.exit();
                        }
                        pacMan.whichDirection(Direction.STOP);
                        pacMan.setPacman(0, 0);
                        blueGhost.setGhost(140, 180);
                        blueGhost.setPathIndex(0);
                        pinkGhost.setGhost(320, 0);
                        pinkGhost.setPathIndex(0);
                        greenGhost.setGhost(280, 300);
                        greenGhost.setPathIndex(0);

                    } else {
                        if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.UP)) {
                            pacMan.whichDirection(Direction.STOP);
                        } else
                            pacMan.moveOneStep(GAMEFIELD_SIZE);
                    }
                }
            }
            case DOWN -> {
                if (yWert <= bottomBound) {
                    if (isPacmanInGhost(pacMan, blueGhost) || isPacmanInGhost(pacMan, pinkGhost) || isPacmanInGhost(pacMan, greenGhost)) {
                        if (life > 1) {
                            life = life - 1;
                        } else {
                            Platform.exit();
                        }
                        pacMan.whichDirection(Direction.STOP);
                        pacMan.setPacman(0, 0);
                        blueGhost.setGhost(140, 180);
                        blueGhost.setPathIndex(0);
                        pinkGhost.setGhost(320, 0);
                        pinkGhost.setPathIndex(0);
                        greenGhost.setGhost(280, 300);
                        greenGhost.setPathIndex(0);
                    } else {
                        if (isPacmanInfrontObstacle(pacMan, obstacles, Direction.DOWN)) {
                            pacMan.whichDirection(Direction.STOP);
                        } else
                            pacMan.moveOneStep(GAMEFIELD_SIZE);
                    }
                }
            }

            default-> {
                if (xWert <= rightBound) {
                    if (isPacmanInGhost(pacMan, blueGhost) || isPacmanInGhost(pacMan, pinkGhost) || isPacmanInGhost(pacMan, greenGhost)) {
                        if (life > 1) {
                            life = life - 1;
                        } else {
                            Platform.exit();
                        }
                        pacMan.whichDirection(Direction.STOP);
                        pacMan.setPacman(0, 0);
                        blueGhost.setGhost(140, 180);
                        blueGhost.setPathIndex(0);
                        pinkGhost.setGhost(320, 0);
                        pinkGhost.setPathIndex(0);
                        greenGhost.setGhost(280, 300);
                        greenGhost.setPathIndex(0);
                    } else {
                        if (isPacmanInfrontObstacle(pacMan, obstacles, pacMan.getLastDirection())) {
                            pacMan.whichDirection(Direction.STOP);
                        } else
                            pacMan.moveOneStep(GAMEFIELD_SIZE);
                    }
                }
            }
        }

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


    private final List<int[]> blueGhostPath = List.of(
            new int[]{140, 180},
            new int[]{140, 160},
            new int[]{120, 160},
            new int[]{100, 160},
            new int[]{80, 160},
            new int[]{80, 140},
            new int[]{80, 120},
            new int[]{80, 100},
            new int[]{100, 100},
            new int[]{120, 100},
            new int[]{140, 100},
            new int[]{140, 80},
            new int[]{140, 60},
            new int[]{160, 60},
            new int[]{180, 60},
            new int[]{180, 80},
            new int[]{180, 100},
            new int[]{180, 120},
            new int[]{180, 140},
            new int[]{180, 160},
            new int[]{180, 180},
            new int[]{200, 180},
            new int[]{220, 180},
            new int[]{240, 180},
            new int[]{260, 180},
            new int[]{280, 180},
            new int[]{300, 180},
            new int[]{320, 180},
            new int[]{320, 160},
            new int[]{320, 140},
            new int[]{300, 140},
            new int[]{280, 140},
            new int[]{260, 140},
            new int[]{260, 120},
            new int[]{260, 100},
            new int[]{260, 80},
            new int[]{240, 80},
            new int[]{220, 80},
            new int[]{220, 60},
            new int[]{200, 60},
            new int[]{180, 60},
            new int[]{180, 80},
            new int[]{180, 100},
            new int[]{180, 120},
            new int[]{180, 140},
            new int[]{180, 160},
            new int[]{180, 180},
            new int[]{160, 180},
            new int[]{140, 180},
            new int[]{140, 200},
            new int[]{120, 200},
            new int[]{100, 200},
            new int[]{80, 200},
            new int[]{60, 200},
            new int[]{60, 220},
            new int[]{40, 220},
            new int[]{20, 220},
            new int[]{0, 220},
            new int[]{0,240},
            new int[]{0, 260},
            new int[]{0, 280},
            new int[]{0, 300},
            new int[]{20, 300},
            new int[]{40, 300},
            new int[]{60, 300},
            new int[]{80, 300},
            new int[]{80, 280},
            new int[]{80, 260},
            new int[]{80, 240},
            new int[]{80, 220},
            new int[]{80,200},
            new int[]{100, 200},
            new int[]{120, 200},
            new int[]{140, 200}
    );

    private void handleBlueGhost(Ghost blueGhost) {
        if (blueGhost.getPathIndex() >= blueGhostPath.size()) {
            blueGhost.setPathIndex(0);
        }
        int[] target = blueGhostPath.get(blueGhost.getPathIndex());
        int targetX = target[0];
        int targetY = target[1];

        double xWert = blueGhost.xWert();
        double yWert = blueGhost.yWert();

        Direction newDirection = blueGhost.getLastDirection();

        if (Math.abs(xWert - targetX) < GAMEFIELD_SIZE && Math.abs(yWert - targetY) < GAMEFIELD_SIZE) {
            blueGhost.incrementPathIndex();
            target = blueGhostPath.get(blueGhost.getPathIndex());
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

        if (isGhostInfrontObstacle(blueGhost, obstacles, newDirection)) {
            blueGhost.whichDirection(Direction.STOP);
        } else {
            blueGhost.whichDirection(newDirection);
        }

        blueGhost.moveOneStep(GAMEFIELD_SIZE);
    }


    private final List<int[]> pinkGhostPath = List.of(
            new int[]{320, 0},
            new int[]{320, 20},
            new int[]{320, 40},
            new int[]{320, 60},
            new int[]{320, 80},
            new int[]{300, 80},
            new int[]{280, 80},
            new int[]{260, 80},
            new int[]{240, 80},
            new int[]{220, 80},
            new int[]{220, 60},
            new int[]{220, 40},
            new int[]{220, 20},
            new int[]{220, 0},
            new int[]{220, 20},
            new int[]{220, 40},
            new int[]{220, 60},
            new int[]{200, 60},
            new int[]{180, 60},
            new int[]{160, 60},
            new int[]{140, 60},
            new int[]{140, 40},
            new int[]{140, 20},
            new int[]{140, 0},
            new int[]{120, 0},
            new int[]{100, 0},
            new int[]{80, 0},
            new int[]{60, 0},
            new int[]{40, 0},
            new int[]{40, 20},
            new int[]{40, 40},
            new int[]{40, 60},
            new int[]{40, 80},
            new int[]{40, 100},
            new int[]{40, 120},
            new int[]{40, 140},
            new int[]{40, 160},
            new int[]{20, 160},
            new int[]{0, 140},
            new int[]{0, 120},
            new int[]{0, 100},
            new int[]{0, 80},
            new int[]{0, 60},
            new int[]{0, 40},
            new int[]{0, 20},
            new int[]{0, 0},
            new int[]{20, 0},
            new int[]{40, 0},
            new int[]{60, 0},
            new int[]{80, 0},
            new int[]{100, 0},
            new int[]{120, 0},
            new int[]{140, 0},
            new int[]{160, 0},
            new int[]{180, 0},
            new int[]{200, 0},
            new int[]{220, 0},
            new int[]{240, 0},
            new int[]{260, 0},
            new int[]{280, 0},
            new int[]{300, 0},
            new int[]{320, 0}
    );
    private void handlePinkGhost(Ghost pinkGhost) {
        if (pinkGhost.getPathIndex() >= pinkGhostPath.size()) {
            pinkGhost.setPathIndex(0);
        }
        int[] target = pinkGhostPath.get(pinkGhost.getPathIndex());
        int targetX = target[0];
        int targetY = target[1];

        double xWert = pinkGhost.xWert();
        double yWert = pinkGhost.yWert();

        Direction newDirection = pinkGhost.getLastDirection();

        if (Math.abs(xWert - targetX) < GAMEFIELD_SIZE && Math.abs(yWert - targetY) < GAMEFIELD_SIZE) {
            pinkGhost.incrementPathIndex();
            target = pinkGhostPath.get(pinkGhost.getPathIndex());
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

        if (isGhostInfrontObstacle(pinkGhost, obstacles, newDirection)) {
            pinkGhost.whichDirection(Direction.STOP);
        } else {
            pinkGhost.whichDirection(newDirection);
        }

        pinkGhost.moveOneStep(GAMEFIELD_SIZE);
    }



    private final List<int[]> greenGhostPath = List.of(
            new int[]{280, 300},
            new int[]{280, 280},
            new int[]{280, 260},
            new int[]{280, 240},
            new int[]{280, 220},
            new int[]{300, 220},
            new int[]{320, 220},
            new int[]{320, 200},
            new int[]{320, 180},
            new int[]{300, 180},
            new int[]{280, 180},
            new int[]{260, 180},
            new int[]{240, 180},
            new int[]{220, 180},
            new int[]{200, 180},
            new int[]{180, 180},
            new int[]{160, 180},
            new int[]{140, 180},
            new int[]{140, 200},
            new int[]{120, 200},
            new int[]{100, 200},
            new int[]{80, 200},
            new int[]{80, 220},
            new int[]{60, 220},
            new int[]{40, 220},
            new int[]{20, 220},
            new int[]{0, 220},
            new int[]{0, 240},
            new int[]{0, 260},
            new int[]{0, 280},
            new int[]{0, 300},
            new int[]{0, 320},
            new int[]{0, 300},
            new int[]{20, 300},
            new int[]{40, 300},
            new int[]{60, 300},
            new int[]{80, 300},
            new int[]{100, 300},
            new int[]{120, 300},
            new int[]{140, 300},
            new int[]{140, 320},
            new int[]{160, 320},
            new int[]{180, 320},
            new int[]{200, 320},
            new int[]{220, 320},
            new int[]{220, 300},
            new int[]{240, 300},
            new int[]{260, 300},
            new int[]{280, 300}
    );
    private void handleGreenGhost(Ghost greenGhost) {
        if (greenGhost.getPathIndex() >= greenGhostPath.size()) {
            greenGhost.setPathIndex(0);
        }
        int[] target = greenGhostPath.get(greenGhost.getPathIndex());
        int targetX = target[0];
        int targetY = target[1];

        double xWert = greenGhost.xWert();
        double yWert = greenGhost.yWert();

        Direction newDirection = greenGhost.getLastDirection();

        if (Math.abs(xWert - targetX) < GAMEFIELD_SIZE && Math.abs(yWert - targetY) < GAMEFIELD_SIZE) {
            greenGhost.incrementPathIndex();
            target = greenGhostPath.get(greenGhost.getPathIndex());
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

        if (isGhostInfrontObstacle(greenGhost, obstacles, newDirection)) {
            greenGhost.whichDirection(Direction.STOP);
        } else {
            greenGhost.whichDirection(newDirection);
        }

        greenGhost.moveOneStep(GAMEFIELD_SIZE);
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

    public boolean isPacmanInGhost(PacMan pacman, Ghost ghost){
        final int ghostX = ghost.xWert();
        final int ghostY = ghost.yWert();
        final int pacmanX = pacman.xWert();
        final int pacmanY = pacman.yWert();
        int futurePacmanY = pacmanY;
        int futurePacmanX = pacmanX;
        int futureGhostY = ghostY;
        int futureGhostX = ghostX;

        switch (pacman.getLastDirection()){
            case UP -> futurePacmanY = pacmanY - 20;
            case DOWN -> futurePacmanY = pacmanY + 20;
            case LEFT -> futurePacmanX = pacmanX -  20;
            case RIGHT -> futurePacmanX = pacmanX +  20;
            case STOP -> {
                futurePacmanX = pacmanX;
                futurePacmanY = pacmanY;
            }
        }

        switch (ghost.getLastDirection()){
            case UP -> futureGhostY = ghostY - 20;
            case DOWN -> futureGhostY = ghostY + 20;
            case LEFT -> futureGhostX = ghostX -  20;
            case RIGHT -> futureGhostX = ghostX +  20;
            case STOP -> {
                futureGhostX = ghostX;
                futureGhostY = ghostY;
            }
        }

        if (ghostX == pacmanX && ghostY == pacmanY ){
            return true;
        }else if (futurePacmanX == ghostX && futureGhostX == pacmanX && pacmanY == ghostY) {
            return true;
        }else if (futurePacmanY == ghostY && futureGhostY == pacmanY && pacmanX == ghostX) {
            return true;
        }else if (pacman.getLastDirection() == Direction.STOP && ((futureGhostY == pacmanY && pacmanX == futureGhostX) || (ghostY == pacmanY && pacmanX == ghostX) )) {
            return true;
        }
        return false;
    }

    public void run() {
        launch();
    }
}
