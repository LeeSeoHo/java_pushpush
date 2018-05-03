package stage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Goal extends ElementalBase {

	private Image image;

	public Goal(int x, int y) {
		super(x, y);
		try {
			image = ImageIO.read(new File("Goal2.png"));
		} catch (IOException e) {
			System.out.println("warning");
			System.exit(0);
		}

		this.setImage(image);

	}
}
