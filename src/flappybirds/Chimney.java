package flappybirds;

import game2d.Objects;

import java.awt.*;

public class Chimney extends Objects {
    private Rectangle rect;
    private boolean behindBird;

    public Chimney(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }

    public void update() {
        setPosX(getPosX() - 3);
        this.rect.setLocation((int)getPosX(), (int)getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isBehindBird() {
        return behindBird;
    }

    public void setBehindBird(boolean behindBird) {
        this.behindBird = behindBird;
    }
}
