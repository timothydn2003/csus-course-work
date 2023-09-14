package Data;

public class BoundingBox{
	private int x1;//left
	private int x2;//right
	private int y1;//top
	private int y2;//bottom
	private Vector2D sprite;
	
	//constructor
	//bounding box for sprite, always changing so will use vector
	public BoundingBox(Vector2D sprite){
		this.sprite = sprite;
		this.x1 = sprite.getX();
		this.x2 = sprite.getX() + 128;
		this.y1 = sprite.getY();
		this.y2 = sprite.getY() + 128;
	}
	//bounding box for edges and objects that wont move
	public BoundingBox(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	//getters
	public Vector2D getSprite() {
		return sprite;
	}
	public int getX1() {
		return x1;
	}
	public int getX2() {
		return x2;
	}
	public int getY1() {
		return y1;
	}
	public int getY2() {
		return y2;
	}
	
	//setters
	public void setSprite(Vector2D newSprite) {
		sprite = newSprite;
	}
	public void setX1(int newX1) {
		x1 = newX1;
	}
	public void setX2(int newX2) {
		x2 = newX2;
	}
	public void setY1(int newY1) {
		y1 = newY1;
	}
	public void setY2(int newY2) {
		y2 = newY2;
	}
}