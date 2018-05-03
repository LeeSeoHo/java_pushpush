package stage;

import java.awt.Image;

public class ElementalBase {
	public final int SPACE = 16;

	public int x;
	public int y;
	private Image image;

	public ElementalBase(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Image getImage() {
		return this.image;

	}

	public void setImage(Image img) {
		image = img;
	}

	public int getPointX() {
		return this.x;
	}

	public int getPointY() {
		return this.y;
	}

	public void setPointX(int x) {
		this.x = x;
	}

	public void setPointY(int y) {
		this.y = y;
	}

	public boolean isLeftCollision(ElementalBase actor) {
		if (((this.getPointX() - SPACE) == actor.getPointX()) && (this.getPointY() == actor.getPointY())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRightCollision(ElementalBase actor) {
		if (((this.getPointX() + SPACE) == actor.getPointX()) && (this.getPointY() == actor.getPointY())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTopCollision(ElementalBase actor) {
		if (((this.getPointY() - SPACE) == actor.getPointY()) && (this.getPointX() == actor.getPointX())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBottomCollision(ElementalBase actor) {
		if (((this.getPointY() + SPACE) == actor.getPointY()) && (this.getPointX() == actor.getPointX())) {
			return true;
		} else {
			return false;
		}
	}
}
