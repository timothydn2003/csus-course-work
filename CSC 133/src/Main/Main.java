package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import Data.AText;
import Data.Animation;
import Data.Click;
import Data.Frame;
import Data.RECT;
import Data.Sprite;
import FileIO.EZFileRead;
import FileIO.EZFileWrite;
import Input.Mouse;
import Particles.ParticleSystem;
import Particles.Snow;
import Scripting.Command;
import Scripting.Interpreter;
import logic.Control;
import sound.Sound;
import timer.stopWatchX;

public class Main{
	
	public static String clr = "";
	public static String s2 = "";
	
	public static Animation anim;
	public static Animation npcAnim;

	public static final int dropShadow = 2;
	private static ArrayList<Command> commands;
	public static ArrayList<RECT> rects;
	public static ArrayList<String> inventory;
	public static ArrayList<RECT> gHovers = new ArrayList<>();
	public static ArrayList<String> bg;

	public static Snow snow;
	
	public static int currBG = 0;
	public static int currX = 150;
	public static int currY = 160;
	public static int prevX = 150;
	public static int prevY = 150;
	
	public static boolean signHud1 = false;
	public static boolean signHud2 = false;
	public static boolean welcomeMessage = false;
	public static boolean openChest = false;
	
	public static boolean showInventory = false;
	public static boolean savedGame = false;
	public static boolean showExit = false;
	public static boolean chestOpened = false;
	public static boolean ballPicked = false;
	public static boolean shieldPicked = false;
	public static Sprite hud;
	
	public static Sound song;

	public static AText sign1 = new AText("Entrance to Frost Wind Peaks", 20);
	public static AText sign2 = new AText("Entrance to the Shadow Wood Forest", 20);
	public static AText chest = new AText("Pick up the key to open the chest", 20);
	public static AText chest2 = new AText("This chest has been here for 100 years...", 20);
	public static AText chest3 = new AText("Chest is empty", 20);
	public static AText welcome = new AText("Welcome to Pixelmon Adventure", 20);
	
	public static stopWatchX stopWatch = new stopWatchX(8000);
	public static boolean showWelcomeMessage = false;
	//RECT for character that updates on every move
	public static RECT characterRECT;
	
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(Control ctrl){				
		snow = new Snow(-50, -50, 1490, 90, 100, 500, 70, ctrl);

		//Getting the commands from the file
		EZFileRead ezr = new EZFileRead("Script/script.txt");
		commands = new ArrayList<>();
		for(int i = 0; i < ezr.getNumLines(); i++) {
			String raw = ezr.getLine(i);
			raw = raw.trim();
			if(!raw.equals("")) {
				boolean b = raw.charAt(0) == '#';
				if(!b)
					commands.add(new Command(raw));
			}
		}
		rects = new ArrayList<RECT>();
		bg = new ArrayList<String>();
		for(Command c: commands) {
			Interpreter comm = new Interpreter(c);
			comm.runScriptCommand(ctrl);
		}
		//Sprite sheet for HUD
		BufferedImage popUp = ctrl.getSpriteFromBackBuffer("items").getSprite();
		BufferedImage popUpBG = popUp.getSubimage(1, 1409, 512, 256);
		hud = new Sprite(500, 500, popUpBG, "popUp");

		//Sprite sheet for NPC animations
		BufferedImage npc = ctrl.getSpriteFromBackBuffer("npc").getSprite();
		BufferedImage npc1 = npc.getSubimage(1, 1, 128, 128);
		BufferedImage npc2 = npc.getSubimage(1, 131, 128, 128);
		BufferedImage npc3 = npc.getSubimage(1, 261, 128, 128);
		//Sprite sheet for character starting point
		loadAnimation(ctrl);
		
		npcAnim = new Animation(70,true);
		for(int i = 1; i < 4; i++) {
			if(i == 1) {
				npcAnim.addFrame(new Frame(650, 150, npc1));
			}else if(i == 2){
				npcAnim.addFrame(new Frame(650, 150, npc2));
			}else {
				npcAnim.addFrame(new Frame(650, 150, npc3));
			}
			
		}	

		inventory = new ArrayList<String>();
//		loadData();

//		//Initializing RECTs Arraylist
		
		
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		
		String timeLeft = stopWatch.getRemaining()/1000 + "";
		if(showWelcomeMessage == false) {
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, "Welcome", Color.black);
			ctrl.drawHudString(540, 595, "Left click to move, right click to interact", Color.black);
			ctrl.drawHudString(950, 675, timeLeft, Color.black);
		}
		if(stopWatch.isTimeUp()) {
			showWelcomeMessage = true;
		}
		
		Point g = Mouse.getMouseCoords();
		int x = (int) g.getX();
		int y = (int) g.getY();
		//Feeds commands into interpreter class then executes them through there
		for(Command c: commands) {
			Interpreter com = new Interpreter(c);
			com.runCommand(ctrl, x, y);
		}
		//Character Movement
		Move move = new Move(currX, currY, ctrl);
		Click click = Control.getMouseInput();
		if(click != null && click.getButton() == 1) {
			
			currX = click.getX();
			currY = click.getY();
			int dx = currX - prevX;
			int dy = currY - prevY;
			
			
			if(Math.abs(dx)>Math.abs(dy)) {
				if(dx>0) {
					move.moveRight(currX, currY);
				}else {
					move.moveLeft(currX, currY);
				}
			}else {
				if(dy>0) {
					move.moveDown(currX, currY);
				}else {
					move.moveUp(currX, currY);
				}
			}
			prevX = currX;
			prevY = currY;
			
		}
		//PARTICLE SYSTEM
		if(currBG == 1) {
			ParticleSystem pm2 = snow.getParticleSystem();
			Iterator<Frame> it2 = pm2.getSpriteParticles();
			while(it2.hasNext()) {
				Frame par2 = it2.next();
				Sprite s = new Sprite(par2.getX(), par2.getY(), par2.getBufferedImage());
				ctrl.addSpriteToFrontBuffer(s);
			}
		}
		//NPC Animation
		if(currBG == 0) {
			Frame currFrame = npcAnim.getCurrentFrame();
			if(currFrame != null) {
				ctrl.addSpriteToFrontBuffer(currFrame.getX(), currFrame.getY(), currFrame.getBufferedImage());
			}
		}
		
		//Walking animation 
		Frame curFrame = anim.getCurrentFrame();
		if(curFrame != null) {
			ctrl.addSpriteToFrontBuffer(curFrame.getX(), curFrame.getY(), curFrame.getSprite());
		}		
				
		String t = "";
		if(!inventory.isEmpty()) {
			for(int i = 0; i < inventory.size(); i++) {
				t += inventory.get(i) + ", ";
			}
			t = t.substring(0, t.length() - 2);
		}
		

		if(showInventory) {
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, "Inventory: ", Color.black);
			ctrl.drawHudString(540, 600, t, Color.black);
		}		

		if(signHud1) {
			String s = sign1.getCurrentStr();
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, s, Color.black);
		}
		
		if(signHud2) {
			String s = sign2.getCurrentStr();
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, s, Color.black);
		}
		
		if(openChest) {
			String s = chest2.getCurrentStr();
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, s, Color.black);
		}
		if(savedGame) {
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, "Game has been saved successfully!", Color.black);
		}	
		if(welcomeMessage) {
			String s = welcome.getCurrentStr();
			ctrl.addSpriteToHudBuffer(500, 500, hud);
			ctrl.drawHudString(540, 575, s, Color.black);
		}		
	}
	
	// Additional Static methods below...(if needed)
	public static void saveData() {
		savedGame = true;
		EZFileWrite ezw = new EZFileWrite("save.txt");
		if(!inventory.isEmpty()) {
			
			ezw.writeLine(currBG + "");
			ezw.writeLine(ballPicked+ "");
			ezw.writeLine(shieldPicked+"");
			for(int i = 0; i < inventory.size(); i++) {
				String s = inventory.get(i);
				ezw.writeLine(s);
			}
			
			ezw.saveFile();
		}
	}
	
	public static void loadData() {
		inventory.clear();
		EZFileRead ezr = new EZFileRead("save.txt");
		if(ezr.getNumLines() == 0) return;
		currBG = Integer.parseInt(ezr.getLine(0));
		ballPicked = Boolean.valueOf(ezr.getLine(1));
		shieldPicked = Boolean.valueOf(ezr.getLine(2));
		if(!ezr.getLine(3).equals("")) {
			for(int i = 3; i < ezr.getNumLines(); i++) {	
				String s = ezr.getLine(i);
				inventory.add(s);
			}
		}
	}
	
	public static void hideInfo() {
		signHud1 = false;
		signHud2 = false;
		openChest = false;
		showInventory = false;
		savedGame = false;
		welcomeMessage = false;
	}
	public static void loadAnimation(Control ctrl) {
		BufferedImage start = ctrl.getSpriteFromBackBuffer("naruto").getSprite();
		BufferedImage startBufferedImage = start.getSubimage(1, 131, 128, 128);
		Sprite startSprite = new Sprite(prevX, prevY, startBufferedImage);
		anim = new Animation(120,false);
		anim.addFrame(new Frame(prevX, prevY, startSprite));
	}

}
