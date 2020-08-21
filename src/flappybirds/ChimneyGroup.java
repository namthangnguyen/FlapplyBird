package flappybirds;

import game2d.QueueList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChimneyGroup {
    private QueueList<Chimney> chimneys;
    private BufferedImage chimneyImgUp;
    private BufferedImage chimneyImgDown;

    public static final int SIZE = 6;
    private int topChimneyY = -300;
    private int botChimneyY = 260;
    private int dist2Chim = 250;

    public ChimneyGroup() {
        try {
            chimneyImgDown = ImageIO.read(new File("Assets/chimney_.png"));
            chimneyImgUp = ImageIO.read(new File("Assets/chimney.png"));
        } catch (IOException ex) {};

        // thực ra trong constructer phải là create nhưng code giống hệt reset nên ko muốn viết lại
        reset();
    }

    public int getRandomY() {
        Random r = new Random();
        int a = r.nextInt(7);
        return a*40;
    }

    public void reset() {
        chimneys = new QueueList<Chimney>();
        Chimney cn;
        for (int i = 0; i < SIZE/2; i++) {
            int deltaY = getRandomY();
            cn = new Chimney(500 + i*dist2Chim, botChimneyY + deltaY, 74, 400);
            cn.setBehindBird(false);
            chimneys.push(cn);

            cn = new Chimney(500 + i*dist2Chim, topChimneyY + deltaY, 74, 400);
            cn.setBehindBird(false);
            chimneys.push(cn);
        }
    }

    public void update() {
        for (int i = 0; i < SIZE; i++) {
            chimneys.get(i).update();
        }
        if (chimneys.get(0).getPosX() < -74) {
            int deltaY = getRandomY();

            Chimney cn;
            cn = chimneys.pop();
            cn.setPosX(chimneys.get(chimneys.getSize() - 1).getPosX() + dist2Chim);
            cn.setBehindBird(false);
            cn.setPosY(botChimneyY + deltaY);
            chimneys.push(cn);

            cn = chimneys.pop();
            cn.setPosX(chimneys.get(chimneys.getSize() - 1).getPosX());
            cn.setPosY(topChimneyY + deltaY);
            cn.setBehindBird(false);
            chimneys.push(cn);
        }
    }

    public void paint(Graphics2D g2) {
        for (int i = 0; i < SIZE; i++) {
            if (i%2 == 0)
                g2.drawImage(chimneyImgUp, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
            else
                g2.drawImage(chimneyImgDown, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
        }
    }

    public Chimney getChimney(int i) {
        return chimneys.get(i);
    }
}
