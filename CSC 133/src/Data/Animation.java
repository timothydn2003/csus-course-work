package Data;

import java.util.ArrayList;

import timer.stopWatchX;

public class Animation{
	
	private ArrayList<Frame> frames;
	private stopWatchX timer;
	private int cursor;
	private boolean isLooping;
	private boolean isFinished;
	
	public Animation(int delay, boolean isLooping) {
		frames = new ArrayList<>();
		timer = new stopWatchX(delay);
		this.isLooping = isLooping;
		cursor = 0;
		isFinished = false;
	}
	
	public void addFrame(Frame newFrame) {
		frames.add(newFrame);
	}
	
	public Frame getCurrentFrame() {
		if(frames.isEmpty()) return null;
		if(timer.isTimeUp()) {
			timer.resetWatch();
			cursor++;
			if(cursor > (frames.size() - 1)) {
				if(isLooping) cursor = 0;
				else {
					cursor--;
					isFinished = true;
				}
			}
		}
		return frames.get(cursor);
	}
	public void restartAnim() {
		cursor = 0;
		isFinished = false;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
}