package flappybirds;

import game2d.AFrameOnImage;
import game2d.Animation;
import game2d.Objects;
import game2d.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bird extends Objects {
    private float s = 0; // quãng đường rơi của chim trong 1 vòng lặp game
    private boolean isFlying = false;
    private Rectangle rect;
    private boolean isLive = true;

    private SoundPlayer fapSound, fallSound, pointSound;
    private BufferedImage birdsImg;
    private Animation birdAnimation;

    public Bird(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);

        fapSound = new SoundPlayer(new File("Assets/fap.wav"));
        fallSound = new SoundPlayer(new File("Assets/fall.wav"));
        pointSound = new SoundPlayer(new File("Assets/getpoint.wav"));

        try {
            birdsImg = ImageIO.read(new File("Assets/bird_sprite_50.png"));
        } catch (IOException ex) {};

        birdAnimation = new Animation(80);

        AFrameOnImage frame;
        frame = new AFrameOnImage(0, 0, 50, 50);
        birdAnimation.AddFrame(frame);
        frame = new AFrameOnImage(50, 0, 50, 50);
        birdAnimation.AddFrame(frame);
        frame = new AFrameOnImage(100, 0, 50, 50);
        birdAnimation.AddFrame(frame);
        frame = new AFrameOnImage(50, 0, 50, 50);
        birdAnimation.AddFrame(frame);
    }

    public void update(long deltaTime) {
        s += Math.sqrt(FlappyBirds.g);
        this.setPosY(this.getPosY() + s);
        this.rect.setLocation((int)getPosX(), (int)getPosY());

        if (s < 0) isFlying = true;
        else isFlying = false;
    }

    public void fly() {
        s = -8;
        fapSound.play();
    }

    public void reset() {
        setPos(175, 300);
        setS(0);
        setLive(true);
    }

    public void paint(Graphics2D g2) {
        if (isLive) {
            if (isFlying)
                birdAnimation.PaintAnims((int)getPosX(), (int)getPosY(), birdsImg, g2, 0, -0.6f);
            else
                birdAnimation.PaintAnims((int)getPosX(), (int)getPosY(), birdsImg, g2, 0, 0);
        } else birdAnimation.PaintAnims((int)getPosX(), (int)getPosY(), birdsImg, g2, 0, 1);
    }

    public Animation getBirdAnimation() {
        return birdAnimation;
    }

    public void setS(int s) {
        this.s = s;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean getLive() {
        return isLive;
    }

    public SoundPlayer getPointSound() {
        return pointSound;
    }

    public SoundPlayer getFallSound() {
        return fallSound;
    }
}
