package Main;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Data.Frame;
import Data.RECT;
import Data.Sprite;
import Input.Mouse;
import logic.Control;

public class Move{
	private static int currX;
	private static int currY;
	private static Control ctrl;
	private static BufferedImage subWalkRight1;
	private static BufferedImage subWalkRight2;
	private static BufferedImage subWalkRight3;
	private static BufferedImage subWalkRight4;
	private static BufferedImage subWalkLeft1;
	private static BufferedImage subWalkLeft2;
	private static BufferedImage subWalkLeft3;
	private static BufferedImage subWalkLeft4;
	private static BufferedImage subWalkUp1;
	private static BufferedImage subWalkUp2;
	private static BufferedImage subWalkUp3;
	private static BufferedImage subWalkUp4;
	private static BufferedImage subWalkDown1;
	private static BufferedImage subWalkDown2;
	private static BufferedImage subWalkDown3;
	private static BufferedImage subWalkDown4;

	

	
	public Move(int currX, int currY, Control ctrl) {
		this.currX = currX;
		this.currY = currY;
		this.ctrl = ctrl;
		BufferedImage character = ctrl.getSpriteFromBackBuffer("naruto").getSprite();
		
		subWalkRight1 = character.getSubimage(1, 391, 128, 128);
		subWalkRight2 = character.getSubimage(131, 391, 128, 128);
		subWalkRight3 = character.getSubimage(261, 391, 128, 128);
		subWalkRight4 = character.getSubimage(391, 391, 128, 128);
		subWalkLeft1 = character.getSubimage(1, 261, 128, 128);
		subWalkLeft2 = character.getSubimage(131, 261, 128, 128);
		subWalkLeft3 = character.getSubimage(261, 261, 128, 128);
		subWalkLeft4 = character.getSubimage(391, 261, 128, 128);
		subWalkUp1 = character.getSubimage(1, 1, 128, 128);
		subWalkUp2 = character.getSubimage(131, 1, 128, 128);
		subWalkUp3 = character.getSubimage(261, 1, 128, 128);
		subWalkUp4 = character.getSubimage(391, 1, 128, 128);
		subWalkDown1 = character.getSubimage(1, 131, 128, 128);
		subWalkDown2 = character.getSubimage(131, 131, 128, 128);
		subWalkDown3 = character.getSubimage(261, 131, 128, 128);
		subWalkDown4 = character.getSubimage(391, 131, 128, 128);
	}

	
	public void moveRight(int currX, int currY) {
		currX -= 100;
		Sprite walkRight1 = new Sprite(Main.prevX, Main.prevY, subWalkRight1, "walkRight1");
		Sprite walkRight2 = new Sprite(Main.prevX, Main.prevY, subWalkRight2, "walkRight2");
		Sprite walkRight3 = new Sprite(Main.prevX, Main.prevY, subWalkRight3, "walkRight3");
		Sprite walkRight4 = new Sprite(Main.prevX, Main.prevY, subWalkRight4, "walkRight4");
		
		int frameCounter = 0;
		for(int i = Main.prevX; i < currX; i+=32) {
				if(frameCounter == 0) {
					Main.anim.addFrame(new Frame(i, Main.prevY, walkRight1));
				}else if(frameCounter == 1) {
					Main.anim.addFrame(new Frame(i, Main.prevY, walkRight2));
				}else if(frameCounter == 2) {
					Main.anim.addFrame(new Frame(i, Main.prevY, walkRight3));
				}else if(frameCounter == 3) {
					Main.anim.addFrame(new Frame(i, Main.prevY, walkRight4));
				}
				frameCounter++;
				if(frameCounter > 3) frameCounter = 0;
		}
		frameCounter = 0;
		if(Main.prevY<currY) {
			moveDown(currX, currY);
		}else if(Main.prevY>currY) {
			moveUp(currX,currY);
		}
	}
	public void moveLeft(int currX, int currY) {
		
		Sprite frame1 = new Sprite(currX, currY, subWalkLeft1, "walkRight1");
		Sprite frame2 = new Sprite(currX, currY, subWalkLeft2, "walkRight2");
		Sprite frame3 = new Sprite(currX, currY, subWalkLeft3, "walkRight3");
		Sprite frame4 = new Sprite(currX, currY, subWalkLeft4, "walkRight4");
		
		int frameCounter = 0;
		for(int i = Main.prevX; i > currX; i-=32) {
				if(frameCounter == 0) {
					Main.anim.addFrame(new Frame(i, Main.prevY, frame1));
				}else if(frameCounter == 1) {
					Main.anim.addFrame(new Frame(i, Main.prevY, frame2));
				}else if(frameCounter == 2) {
					Main.anim.addFrame(new Frame(i, Main.prevY, frame3));
				}else if(frameCounter == 3) {
					Main.anim.addFrame(new Frame(i, Main.prevY, frame4));
				}
				frameCounter++;
				if(frameCounter > 3) frameCounter = 0;
		}
		if(Main.prevY<currY) {
			moveDown(currX, currY);
		}else if(Main.prevY>currY) {
			moveUp(currX,currY);
		}

	}
	public void moveUp(int currX, int currY) {
		Sprite frame1 = new Sprite(currX, currY, subWalkUp1, "walkRight1");
		Sprite frame2 = new Sprite(currX, currY, subWalkUp2, "walkRight2");
		Sprite frame3 = new Sprite(currX, currY, subWalkUp3, "walkRight3");
		Sprite frame4 = new Sprite(currX, currY, subWalkUp4, "walkRight4");
		
		int frameCounter = 0;
		for(int i = Main.prevY; i > currY; i-=32) {
				if(frameCounter == 0) {
					Main.anim.addFrame(new Frame(currX, i, frame1));
				}else if(frameCounter == 1) {
					Main.anim.addFrame(new Frame(currX, i, frame2));
				}else if(frameCounter == 2) {
					Main.anim.addFrame(new Frame(currX, i, frame3));
				}else if(frameCounter == 3) {
					Main.anim.addFrame(new Frame(currX, i, frame4));
				}
				frameCounter++;
				if(frameCounter > 3) frameCounter = 0;
		}
//		if(Main.prevX<currX) {
//			moveRight(currX, currY);
//		}else if(Main.prevX>currX) {
//			moveLeft(currX,currY);
//		}

	}
	public void moveDown(int currX, int currY) {
		Sprite frame1 = new Sprite(currX, currY, subWalkDown1, "walkRight1");
		Sprite frame2 = new Sprite(currX, currY, subWalkDown2, "walkRight2");
		Sprite frame3 = new Sprite(currX, currY, subWalkDown3, "walkRight3");
		Sprite frame4 = new Sprite(currX, currY, subWalkDown4, "walkRight4");
		
		int frameCounter = 0;
		for(int i = Main.prevY; i < currY; i+=32) {
				if(frameCounter == 0) {
					Main.anim.addFrame(new Frame(currX, i, frame1));
				}else if(frameCounter == 1) {
					Main.anim.addFrame(new Frame(currX, i, frame2));
				}else if(frameCounter == 2) {
					Main.anim.addFrame(new Frame(currX, i, frame3));
				}else if(frameCounter == 3) {
					Main.anim.addFrame(new Frame(currX, i, frame4));
				}
				frameCounter++;
				if(frameCounter > 3) frameCounter = 0;
		}
	}

}
