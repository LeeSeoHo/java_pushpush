package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import stage.Board;

public class Ui extends JPanel {

	BufferedImage upImg = null;
	BufferedImage upImg1 = null;
	BufferedImage upImg2 = null;
	BufferedImage leftImg = null;
	BufferedImage leftImg1 = null;
	BufferedImage leftImg2 = null;
	BufferedImage downImg = null;
	BufferedImage downImg1 = null;
	BufferedImage downImg2 = null;
	BufferedImage rightImg = null;
	BufferedImage rightImg1 = null;
	BufferedImage rightImg2 = null;
	ImageIcon uiBg = null;
	
	public int recentMove = 0, recentRetry = 0;
	private int move = 0;
	private int retry = 0;
	private int img_x = 90, img_y = 140;
	private int img_x1 = 60, img_y1 = 170;
	private int img_x2 = 90, img_y2 = 170;
	private int img_x3 = 120, img_y3 = 170;
	private int key;
	private int Level = 0;
	private String[]  fileName= {"Level1.txt", "Level2.txt", "Level3.txt"};

	
	public Ui() throws IOException {
		uiBg = new ImageIcon("./Ui2.png");
		
		fileIn();
	
		try {
			upImg1 = ImageIO.read(new File("2.png"));
			upImg2 = ImageIO.read(new File("1.png"));
			upImg = upImg1;
			leftImg1 = ImageIO.read(new File("3.png"));
			leftImg2 = ImageIO.read(new File("4.png"));
			leftImg = leftImg1;
			downImg1 = ImageIO.read(new File("5.png"));
			downImg2 = ImageIO.read(new File("6.png"));
			downImg = downImg1;
			rightImg1 = ImageIO.read(new File("7.png"));
			rightImg2 = ImageIO.read(new File("8.png"));
			rightImg = rightImg1;
		} catch (IOException e) {
		}
				
	}
	
	public void setKey(int key){
		this.key = key;
	}
	
	public int getKey(){
		return this.key;
	}
	
	public int getBgWidth(){
		return this.uiBg.getIconWidth();
	}
	
	public int getBgHeight(){
		return this.uiBg.getIconHeight();
	}
	
	public int getImageScale(BufferedImage img){
		return img.getWidth();
	}
	
	public void setLevel(int Level){
		this.Level = Level;
	}
	
	public void fileOut() throws IOException{
		
		DataOutputStream out = null;
		//System.out.println("»£√‚");		
		
		try{
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName[Level])));
			out.writeInt(move);
			out.writeInt(retry);
			out.flush();
		}finally{
			if(out!= null)
				out.close();			
		}
		
	}
	
	public void fileIn() throws IOException{
		
		DataInputStream in = null;
	//	System.out.println("»£√‚2");
		
		try{
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName[Level])));
			recentMove = in.readInt();
			recentRetry = in.readInt();		
		}catch(IOException e){
			fileOut();
		}
		finally{
			if(in != null)
				in.close();
		}
		
	}
	
	public void changePushImg(){

		switch(key){
		case KeyEvent.VK_UP:
			upImg = upImg2;
			break;
		case KeyEvent.VK_LEFT:
			leftImg = leftImg2;
			break;
		case KeyEvent.VK_DOWN:
			downImg = downImg2;
			break;
		case KeyEvent.VK_RIGHT:
			rightImg = rightImg2;
			
			break;
		}
		
	}
	
	public void changeImg(){

		switch(key){
		case KeyEvent.VK_UP:
			upImg = upImg1;
		//	move++;
			break;
		case KeyEvent.VK_LEFT:
			leftImg = leftImg1;
		//	move++;
			break;
		case KeyEvent.VK_DOWN:
			downImg = downImg1;
		//	move++;
			break;
		case KeyEvent.VK_RIGHT:
			rightImg = rightImg1;
		//	move++;
			break;
		}
		repaint();
		
	}
	

	public int increaseNum() {
		return ++move;
	}

	public int retryNum() {
		move=0;
		return ++retry;
	}
	
	public  void newNum() {
		move=0;
		retry=0;	
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
			g.drawImage(uiBg.getImage(), 0, 0, null);
			g.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 10));
			g.drawString("Recent Move : " + recentMove, 60, 55);
			g.drawString("Recent Retry : " + recentRetry, 60, 70);		
			g.drawString("Move : " + move, 60, 85);
			g.drawString("Retry : " + retry, 60, 100);
			g.drawString("*******************" , 145, 110);
			g.drawString("*                     *" , 145, 120);
			g.drawString("*   r : retry         *" , 145, 130);
			g.drawString("*   n : next level  *", 145, 140);
			g.drawString("*                     *" , 145, 150);
			g.drawString("*******************", 145, 160);
			g.drawImage(upImg, img_x, img_y, null);
			g.drawImage(leftImg, img_x1, img_y1, null);
			g.drawImage(downImg, img_x2, img_y2, null);
			g.drawImage(rightImg, img_x3, img_y3, null);
		
	}
	
	

}
