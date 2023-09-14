package Particles;

import java.awt.image.BufferedImage;

import Data.Sprite;
import logic.Control;

public class Snow{
	private ParticleSystem parts;
	private String[] spriteTags;
	private BufferedImage[] spriteTag;
	public Snow(int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numparticles, Control ctrl) {
		BufferedImage s = ctrl.getSpriteFromBackBuffer("snowSprite").getSprite();
		BufferedImage s1 = s.getSubimage(1, 1, 32, 32);
		BufferedImage s2 = s.getSubimage(1, 35, 32, 32);
		BufferedImage s3 = s.getSubimage(1, 69, 32, 32);
		BufferedImage s4 = s.getSubimage(1, 103, 32, 32);
		BufferedImage s5 = s.getSubimage(1, 137, 32, 32);

		
		spriteTag = new BufferedImage[5];
		spriteTag[0] = s1;
		spriteTag[1] = s2;
		spriteTag[2] = s3;
		spriteTag[3] = s4;
		spriteTag[4] = s5;
		
		int xspeed = 0;
		int yspeed = 2; 
		parts = new ParticleSystem(numparticles, xpos, ypos, xrange, yrange, minlife, maxlife, xspeed, yspeed, 2, 10, spriteTag);
	}
	
	public void updateParticleSprites() {
		Particle[] pa = parts.getParticleArray();
		for(int i =0; i < pa.length;i++) {
			int stages = spriteTags.length;
			int life = pa[i].getLifeCycle();
			int range = life/stages;
			int age = pa[i].getAge();
			
			for(int  j = 0; j < stages; j++) {
				if(age >= (range*j) && age < (range * (j+1))) {
					pa[i].changeSprite(spriteTags[j]);
					break;
				}
			}
		}
	}
	
	public ParticleSystem getParticleSystem() {
//		updateParticleSprites();
		return parts;
	}
}