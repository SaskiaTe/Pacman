package ch.css.leistungen.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;

public class MagicFruit implements drawAble{
    private final Food food;
    private int xWert;
    private int yWert;
    private int height;
    private int width;
    private boolean active  = true;

    public final InputStream CherryInputStream = getClass().getClassLoader().getResourceAsStream("Kirschen.png");
    public final Image cherryImage = new Image(CherryInputStream);

    public final InputStream StrawberryInputStream = getClass().getClassLoader().getResourceAsStream("Erdbeere.png");
    public final Image StrawberryImage = new Image(StrawberryInputStream);
    public MagicFruit(int xWert, int yWert, int height, int width, Food food) {
        this.xWert = xWert;
        this.yWert = yWert;
        this.height = height;
        this.width = width;
        this.food = food;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (active) {
            switch (food) {
                case CHERRY -> gc.drawImage(cherryImage, xWert, yWert, width, height);
                case STRAWBERRY -> gc.drawImage(StrawberryImage, xWert, yWert, width, height);
            }
        }
    }

    public int xWert() {
        return xWert;
    }

    public int yWert() {
        return yWert;
    }

    public void setMagicFruit(int newX, int newY){
        xWert = newX;
        yWert = newY;
    }
    public void deactivate(){
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}


