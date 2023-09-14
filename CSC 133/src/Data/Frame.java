package Data;

import java.awt.image.BufferedImage;

public class Frame{
	private int x;
	private int y;
	private String spriteTag;
	private Sprite s;
	private BufferedImage image;
	
	public Frame(int x, int y, String spriteTag) {
		this.x = x;
		this.y = y;
		this.spriteTag = spriteTag;
	}
	public Frame(int x, int y, Sprite s) {
		this.x = x;
		this.y = y;
		this.s = s;
	}
	public Frame(int x, int y, BufferedImage image) {
		this.image = image;
		this.x = x;
		this.y = y;
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getSpriteTag(){
		return spriteTag;
	}
	public Sprite getSprite() {
		return s;
	}
	public BufferedImage getBufferedImage() {
		return image;
	}
}