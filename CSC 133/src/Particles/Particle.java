package Particles;

import java.awt.image.BufferedImage;

import Data.Frame;
import Data.Sprite;
import timer.stopWatchX;

public class Particle{
	private int x, y;
	private String particleSpriteTag;
	private int lifecycle;
	private int age;
	private int xMove, yMove;
	private stopWatchX timer;
	private int rootX, rootY;
	private boolean isReset;
	private BufferedImage particleSpriteTags;
	
	
	public Particle(int minX, int maxX, int minY, int maxY, String particleSpriteTag, int minLife, int maxLife, 
			int xMove, int yMove, int mindelay, int maxdelay) {
		this.particleSpriteTag = particleSpriteTag;
		this.x = getRandomInt(minX, maxX);
		this.y = getRandomInt(minY,maxY);
		lifecycle = getRandomInt(minLife,maxLife);
		this.xMove = xMove;
		this.yMove = yMove;
		int delay = getRandomInt(mindelay, maxdelay);
		timer = new stopWatchX(delay);
		rootX = x;
		rootY = y;
		
	}
	public Particle(int minX, int maxX, int minY, int maxY, BufferedImage particleSpriteTag, int minLife, int maxLife, 
			int xMove, int yMove, int mindelay, int maxdelay) {
		this.particleSpriteTags = particleSpriteTag;
		this.x = getRandomInt(minX, maxX);
		this.y = getRandomInt(minY,maxY);
		lifecycle = getRandomInt(minLife,maxLife);
		this.xMove = xMove;
		this.yMove = yMove;
		int delay = getRandomInt(mindelay, maxdelay);
		timer = new stopWatchX(delay);
		rootX = x;
		rootY = y;
		
	}

	public boolean hasBeenRest() {
		return isReset;
	}
	public void changeX(int newX) {
		x = newX;
	}
	public int getX() {
		return x;
	}
	public int getLifeCycle() {
		return lifecycle;
	}
	public int getAge() {
		return age;
	}
	public void changeSprite(String newSpriteTag) {
		particleSpriteTag = newSpriteTag;
	}
	public void changeSprite(BufferedImage newSpriteTag) {
		particleSpriteTags = newSpriteTag;
	}
	
	public boolean isParticleDead() {
		if(age >= lifecycle) return true;
		
		if(y>900 || x > 1440) return true;
		
		return false;
	}
	
	public void simulateAge() {
		age++;
		x += xMove;
		y += yMove;
		
		if(isParticleDead()) {
			x = rootX;
			y = rootY;
			age = 0;
			isReset = true;
		}
	}
	
	public Frame getCurrentFrame() {
		if(timer.isTimeUp()) {
			age++;
			x+=xMove;
			y+=yMove;
			
			if(isParticleDead()) {
				x = rootX;
				y = rootY;
				age = 0;
				isReset = true;
			}
			timer.resetWatch();
		}
		return new Frame(x,y,particleSpriteTag);
	}
	public Frame getCurrentSpriteFrame() {
		if(timer.isTimeUp()) {
			age++;
			x+=xMove;
			y+=yMove;
			
			if(isParticleDead()) {
				x = rootX;
				y = rootY;
				age = 0;
				isReset = true;
			}
			timer.resetWatch();
		}
		return new Frame(x,y,particleSpriteTags);
	}
	public static int getRandomInt(int first, int last) {
		int diff = last - first;
		double num = Math.random() * diff;
		int intNum = (int)num;
		
		return first + intNum;
	}
	
	public static int rollDie(int dieSides) {
		double result = Math.random() * dieSides;
		int res = (int) result;
		res++;
		return res;
	}
}