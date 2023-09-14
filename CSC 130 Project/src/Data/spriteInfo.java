/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class spriteInfo {
	private Vector2D v2d;
	private String tag;
	public spriteInfo(Vector2D v2d, String tag){
		this.tag = tag;
		this.v2d=v2d;
	}
	
	public String getTag(){
		return tag;
	}
	
	public Vector2D getCoords(){
		return v2d;
	}
	
	public void setTag(String newTag){
		tag = newTag;
	}
	
	public void setCoords(Vector2D newV2D){
		v2d = newV2D;
	}
	
	public void setCoords(int x, int y){
		v2d.setX(x);
		v2d.setY(y);
	}
	
	public String toString(){
		return "["+v2d.getX()+", "+v2d.getY()+", " + tag+"]";
	}
}
