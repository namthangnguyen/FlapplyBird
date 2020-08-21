package flappybirds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ground {
    BufferedImage groundImg;
    int x1, y1, x2, y2;

    public Ground(){
        try {
            groundImg = ImageIO.read(new File("Assets/ground.png"));
        } catch (IOException e) {};
        x1 = 0;
        y1 = 600;
        x2 = x1 + 830;
        y2 = 600;
    }

    public void update() {
        if (x1 < 0) x2 = x1 + 830;
        if (x2 <0) x1 = x2 + 830;
        x1 -= 3;
        x2 -= 3;
    }

    public void paint(Graphics2D g2) {
        g2.drawImage(groundImg, x1, y1, null);
        g2.drawImage(groundImg, x2, y2, null);
    }

    public int getY1() {
        return y1;
    }
}
