package Scripting;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.*;

import Data.Click;
import Data.Frame;
import Data.RECT;
import Data.Sprite;
import Input.Mouse;
import Main.Main;
import logic.Control;
import sound.Sound;

public class Interpreter{
	
	private Command c;	
	public Interpreter(Command c) {
		this.c = c;
	}
	//Running script commands in start method
	public void runScriptCommand(Control ctrl) {
		if(c.isCommand("playSound") && c.getNumParms() == 1) {
			playSceneMusic(c, ctrl);
		}else if(c.isCommand("cursor") && c.getNumParms() == 5) {
			ctrl.hideDefaultCursor();
		}else if(c.isCommand("graphical_hover")) {
			createGraphicalHover(c, ctrl);
		}
	}
	//Running script commands in update method
	public void runCommand(Control ctrl, int x, int y) {
		if(c.isCommand("bg") && c.getNumParms() == 1) {
			displayBG(c,ctrl);
		//Create a RECT that will act as button to change between backgrounds
		}else if(c.isCommand("RECT_BG") && c.getNumParms() == 7) {
			createRECTBG(c,ctrl);
		//Show sprite from sprite sheet
		}else if(c.isCommand("show_sprite") && c.getNumParms()== 9){
			createSprite(c, ctrl);
		//Show sprite from single file
		}else if(c.isCommand("show_sprite") && c.getNumParms() == 4) {
			showSprite(c, ctrl);
		//Write text to the screen
		}else if(c.isCommand("text") && c.getNumParms() == 3) {
			ctrl.drawString(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2), Color.white);
		//Create rect for an object on screen
		}else if(c.isCommand("RECT") && c.getNumParms() == 7) {
			createRECT(c, ctrl);
		//Custom Mouse Cursor Command
		}else if(c.isCommand("cursor") && c.getNumParms() == 5) {
			customCursor(c,ctrl, x, y);
		}else if(c.isCommand("graphical_hover")) {
			useGraphicalHover(c, ctrl, x, y);
		}
		
		
	}
	//Creates RECT and adds to arraylist
	public void createRECT(Command c, Control ctrl) {
		if(c.getParmByIndex(6).equals("bg1")) {
			if(Main.currBG == 0) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);
			}
		}else if(c.getParmByIndex(6).equals("bg2")) {
			if(Main.currBG == 1) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);
			}
		}else if(c.getParmByIndex(6).equals("bg3")) {
			if(Main.currBG == 2) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);
			}
		}else if(c.getParmByIndex(6).equals("all")){
			Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
			activateRect(ctrl);
		}
	}
	//Activates RECT and allows for hover
	public void activateRect(Control ctrl) {
		Point p = Mouse.getMouseCoords();
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		for(int i = 0; i < Main.rects.size(); i++) {
			if(Main.rects.get(i).isCollision(x, y)) {
				Main.clr = Main.rects.get(i).getHoverLabel();
				clickRect(ctrl);
			}
			else
				Main.clr = "";
			
			ctrl.drawString(x, (y-2), Main.clr, Color.black);
			ctrl.drawString(x-Main.dropShadow, (y-Main.dropShadow)-2, Main.clr, Color.YELLOW);
		}

	}
	//Runs functions when RECT is clicked
	public void clickRect(Control ctrl) {
		Sound interact = new Sound("Sound/interact_sound.wav");
		
		for(int i = 0; i < Main.rects.size(); i++) {
			if(Control.getMouseInput() != null) {
				if(Main.rects.get(i).isClicked(Control.getMouseInput(), Click.RIGHT_BUTTON)) {
					if(Main.rects.get(i).getTag().equals("sign1")) {
						Main.showExit = true;

						Main.hideInfo();
						interact.playWAV();
						Main.signHud1 = true;
						Main.sign1.resetAnimation();
					}else if(Main.rects.get(i).getTag().equals("sign2")) {
						Main.showExit = true;

						Main.hideInfo();
						interact.playWAV();
						Main.signHud2 = true;
						Main.sign2.resetAnimation();

					}else if(Main.rects.get(i).getTag().equals("chest")) {
						Main.showExit = true;
						Main.hideInfo();
						Main.openChest = true;
					}else if(Main.rects.get(i).getTag().equals("close")) {
						Main.hideInfo();
						Main.showExit = false;
						
					}
					else if(Main.rects.get(i).getTag().equals("savetag")) {
						Main.showExit = true;
						Main.hideInfo();
						Main.saveData();
						
					}else if(Main.rects.get(i).getTag().equals("ball")) {
						Main.ballPicked = true;
						addToInventory(Main.rects.get(i), ctrl);
					}
					else if(Main.rects.get(i).getTag().equals("inventory")) {
						Main.showExit = true;
						Main.hideInfo();
						Main.showInventory = true;
					}else if(Main.rects.get(i).getTag().equals("npc")) {
						Main.showExit = true;
						Main.hideInfo();
						Main.welcomeMessage = true;
					}else if(Main.rects.get(i).getTag().equals("mute")) {
						Main.song.pauseWAV();
					}else if(Main.rects.get(i).getTag().equals("playMusic")) {
						Main.song.setLoop();
					}else if(Main.rects.get(i).getTag().equals("shield")) {
						Main.shieldPicked = true;
						addToInventory(Main.rects.get(i), ctrl);
					}else if(Main.rects.get(i).getTag().equals("exit")){
						System.exit(0);
					}else if(Main.rects.get(i).getTag().equals("load")) {
						Main.loadData();
					}
				}
			}
		}
	}
	//Displays the BG
	public void displayBG(Command c, Control ctrl) {
		if(c.getParmByIndex(0).equals("bg0") && Main.currBG == 0) {
			ctrl.addSpriteToFrontBuffer(0, 0, c.getParmByIndex(0));
		}else if(c.getParmByIndex(0).equals("bg1") && Main.currBG == 1) {
			ctrl.addSpriteToFrontBuffer(0, 0, c.getParmByIndex(0));
		}else if(c.getParmByIndex(0).equals("bg2") && Main.currBG == 2) {
			ctrl.addSpriteToFrontBuffer(0, 0, c.getParmByIndex(0));
		}
	}
	//Change BG based on RECT click
	public void changeBG(Control ctrl) {		
			for(int i = 0; i < Main.rects.size(); i++) {
				if(Control.getMouseInput() != null) {
					if(Main.rects.get(i).isClicked(Control.getMouseInput(), Click.RIGHT_BUTTON)) {
						if(Main.rects.get(i).getTag().equals("pathway1")){
							Main.currBG = 1;
							Main.prevX = 128;
							Main.prevY = 490;
							Main.loadAnimation(ctrl);
							Main.showExit = false;
							Main.hideInfo();
						}else if(Main.rects.get(i).getTag().equals("pathway0")) {
							Main.currBG = 0;
							Main.prevX = 1175;
							Main.prevY = 486;
							Main.loadAnimation(ctrl);
							Main.showExit = false;
							Main.hideInfo();
						}else if(Main.rects.get(i).getTag().equals("pathway2")){
							Main.currBG = 2;
							Main.prevX = 150;
							Main.prevY = 150;
							Main.loadAnimation(ctrl);
							Main.showExit = false;
							Main.hideInfo();
						}else if(Main.rects.get(i).getTag().equals("pathway3")) {
							Main.currBG = 1;
							Main.prevX = 1150;
							Main.prevY = 150;
							Main.loadAnimation(ctrl);
							Main.showExit = false;
							Main.hideInfo();
						}
					}
				}
			}
			Main.rects.clear();
		}
	

	//Creates RECT for the pathways
	public void createRECTBG(Command c, Control ctrl) {
		if(c.getParmByIndex(6).equals("bg1")) {
			if(Main.currBG == 0) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);
				changeBG(ctrl);
			}
		}else if(c.getParmByIndex(6).equals("bg2")) {
			if(Main.currBG == 1) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);

				changeBG(ctrl);
			}
		}else if(c.getParmByIndex(6).equals("bg3")) {
			if(Main.currBG == 2) {
				Main.rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), c.getParmByIndex(5)));
				activateRect(ctrl);
				changeBG(ctrl);
			}
		}
	}
	//Add sprite to scene using sprite sheet
	public void createSprite(Command c, Control ctrl) {
		BufferedImage i = ctrl.getSpriteFromBackBuffer(c.getParmByIndex(7)).getSprite();
		BufferedImage subImage = i.getSubimage(Integer.parseInt(c.getParmByIndex(0)),Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)));
		Sprite s = new Sprite(Integer.parseInt(c.getParmByIndex(4)),Integer.parseInt(c.getParmByIndex(5)),subImage, "");
		
		if(c.getParmByIndex(6).equals("bg1") && Main.currBG == 0) {
			ctrl.addSpriteToFrontBuffer(s);
		}else if(c.getParmByIndex(6).equals("bg2") && Main.currBG == 1) {
			if(c.getParmByIndex(8).equals("shield")) {
				if(Main.shieldPicked == false) {
					ctrl.addSpriteToFrontBuffer(s);
				}
			}else {
				ctrl.addSpriteToFrontBuffer(s);

			}
		}else if(c.getParmByIndex(6).equals("bg3") && Main.currBG == 2) {
			if(c.getParmByIndex(8).equals("ball")) {
				if(Main.ballPicked == false) {
					ctrl.addSpriteToFrontBuffer(s);
				}
			}else {
				ctrl.addSpriteToFrontBuffer(s);
			}
		}else if(c.getParmByIndex(6).equals("all")){
			if(c.getParmByIndex(8).equals("close")) {
				if(Main.showExit == true) {
					ctrl.addSpriteToFrontBuffer(s);
				}
			}else {
				ctrl.addSpriteToFrontBuffer(s);
			}
		}
	}
	//Add sprite to scene using sprite tag
	public void showSprite(Command c, Control ctrl) {
		if(c.getParmByIndex(3).equals("bg1") && Main.currBG == 0) {
			ctrl.addSpriteToFrontBuffer(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2));
		}else if(c.getParmByIndex(3).equals("bg2") && Main.currBG == 1){
			ctrl.addSpriteToFrontBuffer(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2));
		}else if(c.getParmByIndex(3).equals("bg3")&& Main.currBG == 2) {
			ctrl.addSpriteToFrontBuffer(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2));
		}else if(c.getParmByIndex(3).equals("all")){
			if(c.getParmByIndex(2).equals("close")){
				if(Main.showExit) {
					ctrl.addSpriteToFrontBuffer(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2));
				}else {
					return;
				}
			}
			ctrl.addSpriteToFrontBuffer(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), c.getParmByIndex(2));
		}
	}
	
	//Play scene music
	public void playSceneMusic(Command c, Control ctrl) {
		Main.song = new Sound("Sound/"+c.getParmByIndex(0));
		Main.song.setLoop();

	}
	//Makes custome cursor
	public void customCursor(Command c, Control ctrl, int x, int y) {
		BufferedImage i = ctrl.getSpriteFromBackBuffer(c.getParmByIndex(4)).getSprite();
		BufferedImage subImage = i.getSubimage(Integer.parseInt(c.getParmByIndex(0)),Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)));
		Sprite s = new Sprite(0,0,subImage, "");
		ctrl.addSpriteToOverlayBuffer(x, y, s);
		
	}
	//Adds graphical hover to arraylist
	public void createGraphicalHover(Command c, Control ctrl) {
		BufferedImage i = ctrl.getSpriteFromBackBuffer(c.getParmByIndex(11)).getSprite();
		BufferedImage subImage = i.getSubimage(Integer.parseInt(c.getParmByIndex(7)), Integer.parseInt(c.getParmByIndex(8)), Integer.parseInt(c.getParmByIndex(9)), Integer.parseInt(c.getParmByIndex(10)));
		Sprite s = new Sprite(0, 0, subImage, "saveH");
		RECT r = new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)), Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)), c.getParmByIndex(4), new Frame(Integer.parseInt(c.getParmByIndex(5)), Integer.parseInt(c.getParmByIndex(6)), s));
		Main.gHovers.add(r);
		
	}
	//Activates the graphical hovers
	public void useGraphicalHover(Command c, Control ctrl, int x, int y) {
		for(int i = 0; i < Main.gHovers.size(); i++) {
			if(Main.gHovers.get(i).isCollision(x, y)) {
				ctrl.addSpriteToHudBuffer(Main.gHovers.get(i).getGraphicalHover().getX(), Main.gHovers.get(i).getGraphicalHover().getY(), Main.gHovers.get(i).getGraphicalHover().getSprite());
			}
		}
	}
	//Adds objects to inventory
	public void addToInventory(RECT r, Control ctrl) {
		if(!Main.inventory.contains(r.getHoverLabel()))
			Main.inventory.add(r.getHoverLabel());
	}

}

