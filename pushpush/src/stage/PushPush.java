package stage;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;

import ui.Ui;

public class PushPush extends JFrame {
	private final int OFFSET = 30;

	public PushPush() throws IOException {
		InitUI();
	}

	public void InitUI() throws IOException {
		
		Ui ui = new Ui();
		Board board = new Board(ui);
		setLayout(new GridLayout(1, 2));
		
		add(board);
		add(ui);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(board.getBoardWidth() + ui.getBgWidth()+ OFFSET, ui.getBgHeight()+  OFFSET);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Push&Push");
	}

	public static void main(String[] args) throws IOException {
		PushPush pushpush;
		pushpush = new PushPush();
		pushpush.setVisible(true);
	}
}
