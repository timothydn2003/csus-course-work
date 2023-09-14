package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	public static Color c = new Color(242,163,11);
	public static stopWatchX timer = new stopWatchX(150);
	
	public static ArrayList<BoundingBox> bounding = new ArrayList<BoundingBox>();//data structure to hold bounding boxes
	public static ArrayList<spriteInfo> sprites = new ArrayList<spriteInfo>();//data structure to hold sprites
	
	public static Vector2D currentVec = new Vector2D(650,380);//current coordinates of sprite
	public static Vector2D prevVec = new Vector2D(0,0);//this is for when the character reaches the bounds, will use these coordinates 
	
	public static int frame = 1;//changing frames
	public static spriteInfo character = new spriteInfo(currentVec, "walkfront" + frame);
	public static spriteInfo prevCharacter = new spriteInfo(prevVec, character.getTag());
	public static spriteInfo background = new spriteInfo(new Vector2D(0,0), "background");
	public static spriteInfo ball = new spriteInfo(new Vector2D(860,200), "ball");
	public static spriteInfo sign = new spriteInfo(new Vector2D(1110,360), "sign");
	
	public static String word = "";
	
	public static HashMap<String,String> lines = new HashMap<>();
	
	public static BoundingBox player;
	
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	public static void start(){
		//adding all sprites
		sprites.add(background);
		sprites.add(ball);
		sprites.add(sign);
		sprites.add(character);
		//bounding boxes for all objects besides sprite
		bounding.add(new BoundingBox(-128,166,-128,900));//left side
		bounding.add(new BoundingBox(1280,1440,-128,900));//right side
		bounding.add(new BoundingBox(-128,1440,730,900));//bottom boundary
		bounding.add(new BoundingBox(-128,1440,-128,100));//top boundary
		bounding.add(new BoundingBox(444,990,580,900));//pond
		bounding.add(new BoundingBox(0,380,0,340));//house
		bounding.add(new BoundingBox(1160,1440,420,412));//sign
		bounding.add(new BoundingBox(890,930,0,190));//ball
	}
	
	public static void update(Control ctrl) {
		//display sprites
		for(int i = 0; i < sprites.size(); i++) {
			ctrl.addSpriteToFrontBuffer(sprites.get(i).getCoords().getX(), sprites.get(i).getCoords().getY(), sprites.get(i).getTag());
		}
		//display text
		ctrl.drawString(character.getCoords().getX(), character.getCoords().getY()+155, word, c);

		//bounding box for player
		player = new BoundingBox(currentVec);

		//check if bounding player has a collision with any of the bounding boxes
		for(int i = 0; i < bounding.size(); i++) {
			if(collision(player,bounding.get(i))) {
				bounce();
			}
		}
	}
	
	// Additional Static methods below...(if needed)
	
	//check for collision
	public static boolean collision(BoundingBox box1, BoundingBox box2) {
		if(box1.getX1()>box2.getX2()||box1.getX2()<box2.getX1()||box1.getY1()>box2.getY2()||box1.getY2()<box2.getY1()) {
			return false;
		}
		return true;
	}
	
	//bounce player backwards if there is a collision
	public static void bounce() {
		currentVec.setX(prevCharacter.getCoords().getX());
		currentVec.setY(prevCharacter.getCoords().getY());
	}

}
