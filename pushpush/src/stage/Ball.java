package stage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball extends ElementalBase {

	private Image image;

	public Ball(int x, int y) {
		super(x, y);

		try {
			image = ImageIO.read(new File("Ball2.png"));
		} catch (IOException e) {
			System.out.println("warning");
			System.exit(0);
		}

		this.setImage(image);

	}

	public void move(int x, int y) {
		int mx = this.getPointX() + x;
		int my = this.getPointY() + y;
		this.setPointX(mx);
		this.setPointY(my);
	}
}
