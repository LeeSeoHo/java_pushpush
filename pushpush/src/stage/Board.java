package stage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.Ui;

public class Board extends JPanel {

	private final int OFFSET = 30;
	private final int SPACE = 16;
	private final int LEFT_COLLISION = 1;
	private final int RIGHT_COLLISION = 2;
	private final int TOP_COLLISION = 3;
	private final int BOTTOM_COLLISION = 4;

	private ArrayList walls = new ArrayList();
	private ArrayList balls = new ArrayList();
	private ArrayList goals = new ArrayList();
	private int leveling = 0;
	private String[] Level = new String[4];
	private Player user;
	private Ui ui;
	private int w = 0;
	private int h = 0;
	private final int maxLevel = 2;
	private boolean completed = false;
	ImageIcon icon;
	
	private String level;

	public Board(Ui ui) {
		this.ui = ui;
		icon = new ImageIcon("./Back2.png");
		initLevel();
		initMap();
		addKeyListener(new TAdapter());
		setFocusable(true);

	}

	public void insertMap() {
		level = Level[leveling];
	}
	
	public void initLevel() {
		Level[0] =    "\n"
					+ "       #####\n" 
					+ "       ##.##\n" 
					+ "       ##$##\n" 
					+ "   ###### ######\n" 
					+ "   ##.$  @  $.##\n"
					+ "   ###### ######\n" 
					+ "       ##$##\n" 
					+ "       ##.##\n" 
					+ "       #####\n";

		Level[1] = 	  "     #######\n" 
					+ "     #      ##\n" 
					+ "     #  @   .#\n" 
					+ "     #      .#\n" 
					+ "     # $$   #\n" 
					+ "     #       #\n"
					+ "     ########\n";
		
		Level[2] =    "     ####\n"
					+ "     #  ####\n"
					+ "   ###  . # #\n"
					+ "   #@   $   #\n"
					+ "   #  ## #####\n"
					+ "   #    $   #\n"
					+ "   ###  .   #\n"
					+ "     #   ###\n"
					+ "     #####";
	}

	public int getBoardWidth() {
		return this.w;
	}

	public int getBoardHeight() {
		return this.h;
	}

	public final void initMap() {

		int x = OFFSET;
		int y = OFFSET;
		
		insertMap();
				
		Wall wall;
		Ball b;
		Goal a;

		for (int i = 0; i < level.length(); i++) {

			char item = level.charAt(i);

			if (item == '\n') {
				y += SPACE;
				if (this.w < x) {
					this.w = x;
				}

				x = OFFSET;
			} else if (item == '#') {
				wall = new Wall(x, y);
				walls.add(wall);
				x += SPACE;
			} else if (item == '$') {
				b = new Ball(x, y);
				balls.add(b);
				x += SPACE;
			} else if (item == '.') {
				a = new Goal(x, y);
				goals.add(a);
				x += SPACE;
			} else if (item == '@') {
				user = new Player(x, y);
				x += SPACE;
			} else if (item == ' ') {
				x += SPACE;
			}

			h = y;
		}
	}

	public void buildMap(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, null);

		ArrayList world = new ArrayList();
		world.addAll(walls);
		world.addAll(goals);
		world.addAll(balls);
		world.add(user);

		for (int i = 0; i < world.size(); i++) {

			ElementalBase element = (ElementalBase) world.get(i);

			if ((element instanceof Player) || (element instanceof Ball)) {
				g.drawImage(element.getImage(), element.getPointX() + 2, element.getPointY() + 2, this);
			} else {
				g.drawImage(element.getImage(), element.getPointX(), element.getPointY(), this);
			}

			if (completed) {
				g.setColor(new Color(0, 0, 0));
				g.drawString("Completed", 10, 90);
			}

		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		buildMap(g);
	}

	class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (completed) {
				if (leveling == maxLevel)
					return;
			}
			
			int key = e.getKeyCode();
			ui.setKey(key);
			ui.changePushImg();
			ui.repaint();

			if (key == KeyEvent.VK_LEFT) {

				if (checkWallCollision(user, LEFT_COLLISION)) {
					return;
				}

				if (checkBallCollision(LEFT_COLLISION)) {
					return;
				}

				user.move(-SPACE, 0);
				ui.increaseNum();
			} else if (key == KeyEvent.VK_RIGHT) {
				if (checkWallCollision(user, RIGHT_COLLISION)) {
					return;
				}

				if (checkBallCollision(RIGHT_COLLISION)) {
					return;
				}

				user.move(SPACE, 0);
				ui.increaseNum();
			} else if (key == KeyEvent.VK_UP) {
				if (checkWallCollision(user, TOP_COLLISION)) {
					return;
				}

				if (checkBallCollision(TOP_COLLISION)) {
					return;
				}

				user.move(0, -SPACE);
				ui.increaseNum();
			} else if (key == KeyEvent.VK_DOWN) {
				if (checkWallCollision(user, BOTTOM_COLLISION)) {
					return;
				}

				if (checkBallCollision(BOTTOM_COLLISION)) {
					return;
				}

				user.move(0, SPACE);
				ui.increaseNum();
			} else if (key == KeyEvent.VK_R) {
				restartLevel();
				ui.retryNum();
				return;
			} else if (key == KeyEvent.VK_N) {
				if (completed == true) {
					if(leveling != maxLevel)
						try {
							nextLevel();
						} catch (IOException e1) {	}
				}
				return;
			}

		}

		public void keyReleased(KeyEvent e) {
			int keycode = e.getKeyCode();
			switch (keycode) {
			case KeyEvent.VK_UP:
				ui.setKey(keycode);
				ui.changeImg();
				break;
			case KeyEvent.VK_LEFT:
				ui.setKey(keycode);
				ui.changeImg();
				break;
			case KeyEvent.VK_DOWN:
				ui.setKey(keycode);
				ui.changeImg();
				break;
			case KeyEvent.VK_RIGHT:
				ui.setKey(keycode);
				ui.changeImg();
				break;
			}
			repaint();
		}
	}

	private boolean checkWallCollision(ElementalBase element, int type) {

		if (type == LEFT_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = (Wall) walls.get(i);
				if (element.isLeftCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = (Wall) walls.get(i);
				if (element.isRightCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = (Wall) walls.get(i);
				if (element.isTopCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = (Wall) walls.get(i);
				if (element.isBottomCollision(wall)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private boolean checkBallCollision(int type) {

		if (type == LEFT_COLLISION) {

			for (int i = 0; i < balls.size(); i++) {

				Ball ball = (Ball) balls.get(i);
				if (user.isLeftCollision(ball)) {

					for (int j = 0; j < balls.size(); j++) {
						Ball element = (Ball) balls.get(j);
						if (!ball.equals(element)) {
							if (ball.isLeftCollision(element)) {
								return true;
							}
						}
						if (checkWallCollision(ball, LEFT_COLLISION)) {
							return true;
						}
					}
					ball.move(-SPACE, 0);
					isCompleted();
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < balls.size(); i++) {

				Ball ball = (Ball) balls.get(i);
				if (user.isRightCollision(ball)) {
					for (int j = 0; j < balls.size(); j++) {

						Ball element = (Ball) balls.get(j);
						if (!ball.equals(element)) {
							if (ball.isRightCollision(element)) {
								return true;
							}
						}
						if (checkWallCollision(ball, RIGHT_COLLISION)) {
							return true;
						}
					}
					ball.move(SPACE, 0);
					isCompleted();
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < balls.size(); i++) {

				Ball ball = (Ball) balls.get(i);
				if (user.isTopCollision(ball)) {
					for (int j = 0; j < balls.size(); j++) {

						Ball element = (Ball) balls.get(j);
						if (!ball.equals(element)) {
							if (ball.isTopCollision(element)) {
								return true;
							}
						}
						if (checkWallCollision(ball, TOP_COLLISION)) {
							return true;
						}
					}
					ball.move(0, -SPACE);
					isCompleted();
				}
			}

			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < balls.size(); i++) {

				Ball ball = (Ball) balls.get(i);
				if (user.isBottomCollision(ball)) {
					for (int j = 0; j < balls.size(); j++) {

						Ball element = (Ball) balls.get(j);
						if (!ball.equals(element)) {
							if (ball.isBottomCollision(element)) {
								return true;
							}
						}
						if (checkWallCollision(ball, BOTTOM_COLLISION)) {
							return true;
						}
					}
					ball.move(0, SPACE);
					isCompleted();
				}
			}
		}

		return false;
	}

	public void isCompleted() {

		int num = balls.size();
		int compl = 0;

		for (int i = 0; i < num; i++) {
			Ball ball = (Ball) balls.get(i);
			for (int j = 0; j < num; j++) {
				Goal goal = (Goal) goals.get(j);
				if (ball.getPointX() == goal.getPointX() && ball.getPointY() == goal.getPointY()) {
					compl += 1;
				}
			}
		}

		if (compl == num) {
			completed = true;
			try{
			ui.fileOut();
			}catch(IOException e){		}
			
			repaint();
		}
	}

	public void restartLevel() {

		goals.clear();
		balls.clear();
		walls.clear();
		initMap();
		if (completed) {
			completed = false;
		}
	}

	public void nextLevel() throws IOException{
		goals.clear();
		balls.clear();
		walls.clear();
		
		try{
			leveling++;
			ui.setLevel(leveling);
			ui.newNum();
			ui.fileIn();
		}catch(IOException e){		}
		
		completed = false;
		
		initMap();
	}
}
