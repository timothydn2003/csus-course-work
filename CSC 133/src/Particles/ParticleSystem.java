package Particles;

import java.awt.image.BufferedImage;
import java.util.*;

import Data.Frame;
import Data.Sprite;

public class ParticleSystem{
	private Particle[] particles;
	private int x,y;
	private int xrange, yrange;
	private int maxlife;
	private String spriteTags[];
	private BufferedImage spriteTag[];
	
	public ParticleSystem(int numParticles, int x, int y, int xrange, int yrange, int minlife, int maxlife, 
			int xmove, int ymove, int mindelay, int maxdelay, String[] spriteTags) {
		this.xrange = xrange;
		this.yrange = yrange;
		this.x = x;
		this.y = y;
		this.maxlife = maxlife;
		particles = new Particle[numParticles];
		this.spriteTags = spriteTags;
		initParticles(xmove, ymove, mindelay, maxdelay, minlife);
	}
	//Using Sprite
	public ParticleSystem(int numParticles, int x, int y, int xrange, int yrange, int minlife, int maxlife, 
			int xmove, int ymove, int mindelay, int maxdelay, BufferedImage[] spriteTags) {
		this.xrange = xrange;
		this.yrange = yrange;
		this.x = x;
		this.y = y;
		this.maxlife = maxlife;
		particles = new Particle[numParticles];
		this.spriteTag = spriteTags;
		initParticlesSprite(xmove, ymove, mindelay, maxdelay, minlife);
	}
	
	public void initParticles(int xmove, int ymove, int mindelay,int maxdelay, int _minlife) {
		for(int i = 0; i < particles.length; i++) {
			int n = spriteTags.length;
			int index = Particle.getRandomInt(0, n-1);
			particles[i] = new Particle(x, (x+xrange), y, (y+yrange), spriteTags[index], _minlife, maxlife, xmove, ymove, mindelay, maxdelay);
		}
		boolean isDone = false;
		while(isDone == false) {
			isDone = true;
			for(int i = 0; i < particles.length; i++) {
				particles[i].simulateAge();
				if(particles[i].hasBeenRest() == false)
					isDone = false;
			}
		}
	}
	//Using Sprite
	public void initParticlesSprite(int xmove, int ymove, int mindelay,int maxdelay, int _minlife) {
		for(int i = 0; i < particles.length; i++) {
			int n = spriteTag.length;
			int index = Particle.getRandomInt(0, n-1);
			particles[i] = new Particle(x, (x+xrange), y, (y+yrange), spriteTag[index], _minlife, maxlife, xmove, ymove, mindelay, maxdelay);
		}
		boolean isDone = false;
		while(isDone == false) {
			isDone = true;
			for(int i = 0; i < particles.length; i++) {
				particles[i].simulateAge();
				if(particles[i].hasBeenRest() == false)
					isDone = false;
			}
		}
	}
	
	public Particle[] getParticleArray() {
		return particles;
	}
	
	public Iterator<Frame> getSpriteParticles(){
		List<Frame> parts = new ArrayList<>();
		for(int i = 0; i < particles.length; i++) {
			Frame tmp = particles[i].getCurrentSpriteFrame();
			parts.add(tmp);
		}
		return parts.iterator();
	}
	public Iterator<Frame> getParticles(){
		List<Frame> parts = new ArrayList<>();
		for(int i = 0; i < particles.length; i++) {
			Frame tmp = particles[i].getCurrentFrame();
			parts.add(tmp);
		}
		return parts.iterator();
	}
}