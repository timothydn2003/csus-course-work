/* This will handle the "Hot Key" system. */

package Main;

import Data.BoundingBox;
import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	private static int i = 1;
	private static int j = 1;
	private static String pokeball = "Collected a new pokeball";
	private static String sign = "A great adventure awaits you";
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		case 'w':
			Main.word = "";
			
			Main.prevVec.setX(Main.currentVec.getX());
			Main.prevVec.setY(Main.currentVec.getY());
			
			Main.currentVec.adjustY(-16);
			Main.character.setTag("walkback" + i);
			
			i++;
			if(i>4) {
				i = 1;
			}
			break;
		case 'a':
			Main.word = "";
			
			Main.prevVec.setY(Main.currentVec.getY());
			Main.prevVec.setX(Main.currentVec.getX());
			
			Main.currentVec.adjustX(-16);
			Main.character.setTag("walkleft" + j);
			
			
			j++;
			if(j>4) {
				j = 1;
			}
			break;
		case 's':
			Main.word = "";
			
			Main.prevVec.setX(Main.currentVec.getX());
			Main.prevVec.setY(Main.currentVec.getY());
			
			Main.currentVec.adjustY(16);
			Main.character.setTag("walkfront" + i);
			
			i++;
			if(i>4) {
				i = 1;
			}
			break;
		case 'd':
			Main.word = "";
			
			Main.prevVec.setX(Main.currentVec.getX());
			Main.prevVec.setY(Main.currentVec.getY());
			
			Main.currentVec.adjustX(16);
			Main.character.setTag("walkright" + j);
			
			j++;
			if(j>4) {
				j = 1;
			}
			break;
		//For this case, we have multiple coordinates so the character doesn't have to exactly centered to be able to interact with the object
		case '$':
			//check if the character is near the object and facing it
			if(Main.character.getTag().equals("walkback1") || Main.character.getTag().equals("walkback2")|| Main.character.getTag().equals("walkback3") || Main.character.getTag().equals("walkback4")){
				if(Main.character.getCoords().getX() == 842 && Main.player.getY1() == 204) {
					Main.word = pokeball;
				}else if(Main.character.getCoords().getX() == 858 && Main.player.getY1() == 204){
					Main.word = pokeball;
				}else if(Main.character.getCoords().getX() == 826 && Main.player.getY1() == 204){
					Main.word = pokeball;
				}else if(Main.player.getY1() == 428 && Main.character.getCoords().getX() == 1114) {
					Main.word = sign;
				}else if(Main.player.getY1() == 428 && Main.character.getCoords().getX() == 1130) {
					Main.word = sign;
				}else if(Main.player.getY1() == 428 && Main.character.getCoords().getX() == 1098) {
					Main.word = sign;
				}
			}else if(Main.character.getTag().contains("walkleft")){
				if(Main.player.getX1() == 938 && Main.character.getCoords().getY() == 140) {
					Main.word = pokeball;
				}else if(Main.player.getX1() == 938 && Main.character.getCoords().getY() == 156) {
					Main.word = pokeball;
				}
			}else if(Main.character.getTag().equals("walkright1") || Main.character.getTag().equals("walkright2")|| Main.character.getTag().equals("walkright3") || Main.character.getTag().equals("walkright4")) {
				if(Main.player.getX2() == 874 && Main.character.getCoords().getY() == 140) {
					Main.word = pokeball;
				}else if(Main.player.getX2() == 874 && Main.character.getCoords().getY() == 156) {
					Main.word = pokeball;
				}else if(Main.player.getX2() == 1146 && Main.character.getCoords().getY() == 380) {
					Main.word = sign;
				}else if(Main.player.getX2() == 1146 && Main.character.getCoords().getY() == 348) {
					Main.word = sign;
				}else if(Main.player.getX2() == 1146 && Main.character.getCoords().getY() == 364) {
					Main.word = sign;
				}
			}else if(Main.character.getTag().equals("walkfront1") || Main.character.getTag().equals("walkfront2")|| Main.character.getTag().equals("walkfront3") || Main.character.getTag().equals("walkfront4")) {
				if(Main.player.getY2() == 412 && Main.character.getCoords().getX() == 1114) {
					Main.word = sign;
				}else if(Main.player.getY2() == 412 && Main.character.getCoords().getX() == 1098) {
					Main.word = sign;
				}else if(Main.player.getY2() == 412 && Main.character.getCoords().getX() == 1130) {
					Main.word = sign;
				}
			}
			
			break;
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}
}