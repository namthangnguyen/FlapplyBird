package flappybirds;

import game2d.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlappyBirds extends GameScreen {

    private BufferedImage skyImg;

    public static float g = 0.2f; // gia tốc trọng trường
    private int point = 0;

    private Bird bird;
    private Ground ground;
    private ChimneyGroup chimneys;

    private final int BEGIN_SCREEN = 1;
    private final int GAMEPLAY_SCREEN = 2;
    private final int GAMEOVER_SCREEN = 3;
    private int currentScreen = BEGIN_SCREEN;

    public FlappyBirds() {
        super(450, 700);

        try {
            skyImg = ImageIO.read(new File("Assets/skyline1a.png"));
        } catch (IOException ex) {};

        bird = new Bird(175, 250, 50, 50);
        ground = new Ground();
        chimneys = new ChimneyGroup();

        BeginGame();
    }

    public static void main(String[] args) {
        new FlappyBirds();
    }

    public void resetGame() {
        bird.reset();
        chimneys.reset();
        point = 0;
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (currentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (currentScreen == GAMEPLAY_SCREEN) {
            bird.getBirdAnimation().Update_Me(deltaTime);
            bird.update(deltaTime);
            ground.update();
            chimneys.update();
            for (int i = 0; i < chimneys.SIZE; i++) {
                Chimney chimney_i = chimneys.getChimney(i);
                if (bird.getRect().intersects(chimney_i.getRect())) {
                    if(bird.getLive()) bird.getFallSound().play();
                    bird.setLive(false);
                }
                if (i%2 == 0 && bird.getPosX() > chimney_i.getPosX() && !chimney_i.isBehindBird()) {
                    point++;
                    bird.getPointSound().play();
                    chimney_i.setBehindBird(true);
                }
            }
            if (bird.getPosY() + bird.getH() > ground.y1) {
                bird.setLive(false);
                currentScreen = GAMEOVER_SCREEN;
            }
        } else {}
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {
        g2.drawImage(skyImg, 0, -30, null);
        chimneys.paint(g2);
        ground.paint(g2);
        bird.paint(g2);
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(30.0f));
        if (currentScreen == BEGIN_SCREEN) {
            g2.drawString("Press SPACE to play game", 60, 300);
        } else if (currentScreen == GAMEPLAY_SCREEN) {
        } else {
            g2.drawString("Press SPACE to replay", 80, 300);
            g2.setFont(g2.getFont().deriveFont(72.0f));
            g2.drawString("" + point, 200, 220);
        }
        g2.setFont(g2.getFont().deriveFont(46.0f));
        g2.drawString("" + point, 15, 50);
    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            if (currentScreen == BEGIN_SCREEN) {
                currentScreen = GAMEPLAY_SCREEN;
            } else if (currentScreen == GAMEPLAY_SCREEN) {
                if (bird.isLive()) bird.fly();
            } else {
                currentScreen = BEGIN_SCREEN;
            }
        }
    }
}
