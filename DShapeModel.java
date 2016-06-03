import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DShapeModel implements Serializable {
	private int x;
	private int y; 
	private int width;
	private int height;
	private Color color;
	private Point p;
	private int id;
	ArrayList<ModelListener> list;
	
	private boolean isSelected;
	private boolean deleted;
	private boolean moveBack;
	private boolean moveFront;
	private boolean moveF;
	private boolean moveB;
	private boolean add;
	private boolean resize;
	private boolean moved;
	private boolean colored;
	
	
	public DShapeModel(){
		x = 0;
		y= 0;
		width = 0;
		height = 0;
		color = Color.GRAY;
		p = new Point();
		list = new ArrayList<ModelListener>();
		isSelected = false;
		deleted = false;
		moveBack = false;
		moveFront = false;
		add = false;
		resize = false;
		moved = false;
		colored = false;
		moveF = false;
		moveB = false;
		id=0;
	}
	public void select(){
		isSelected = true;
		update();
	}
	public void deSelect(){
		isSelected = false;
		update();
	}
	public boolean isSelected(){
		return isSelected;
	}
	public void setPoint(Point point){
		p = point;
	}
	public Point getPoint(){
		return p;
	}
	
	public void setColor(Color newColor){
		color = newColor;
		update();
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setX(int newX){
		x = newX;
		update();
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int newY){
		y = newY;
		update();
	}
	
	public int getY(){
		return y;
	}
	
	public void setWidth(int newWidth){
		width = newWidth;
		update();
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setHeight(int newHeight){
		height = newHeight;
		update();
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean getBounds(int checkX, int checkY){
		if(((checkX>=x)&&(checkX<=(x+width))) && ((checkY>=y)&&(checkY<=(y+height)))) return true;
		else return false;
	}

	public ArrayList<Point> getKnobs(){
		ArrayList<Point> points = new ArrayList<Point>();
		//get top left
		Point tl = new Point();
		tl.x = getX();
		tl.y = getY();
		points.add(tl);
		//get top right
		Point tr = new Point();
		tr.x = getX()+getWidth();
		tr.y = getY();
		points.add(tr);
		//get bottom left
		Point bl = new Point();
		bl.x = getX();
		bl.y = getY()+getHeight();
		points.add(bl);
		//get bottom right
		Point br = new Point();
		br.x = getX()+getWidth();
		br.y = getY()+getHeight();
		points.add(br);
		return points;
	}
	
	public void addListeners(ModelListener listener){
		list.add(listener);
	}
	
	public void removeListeners(ModelListener listener){
		list.remove(listener);
	}
	
	public void update(){
		for(int i = 0; i< list.size(); i++){
			list.get(i).modelChanged(this);
		}
	}
	
	public void setDeleted(boolean isDeleted){
		deleted = isDeleted;
		update();
	}
	
	public boolean getDeleted(){
		return deleted;
	}
	
	public void setMoveBack(boolean isMoveBack){
		moveBack = isMoveBack;
		if(moveBack)update();
	}
	
	public boolean getMoveBack(){
		return moveBack;
	}
	
	
	public void setMoveFront(boolean isMoveFront){
		moveFront = isMoveFront;
		if(moveFront)update();
	}
	
	public boolean getMoveFront(){
		return moveFront;
	}
	
	public void setAdd(boolean isAdd){
		add = isAdd;
		if(add)update();
	}
	
	public boolean getAdd(){
		return add;
	}
	
	public void setMoved(boolean isMove){
		moved = isMove;
		if(moved)update();
	}
	
	public boolean getMoved(){
		return moved;
	}
	public void setResize(boolean isResize){
		resize = isResize;
		if(resize) update();
	}
	
	public boolean getResize(){
		return resize;
	}
	
	public void setColored(boolean isColored){
		colored = isColored;
		if(colored) update();
	}
	
	public boolean getColored(){
		return colored;
	}	
	
	
	public void setMoveF(boolean isMoveF){
		moveF = isMoveF;
		if(moveF) update();
	}
	
	public boolean getMoveF(){
		return moveF;
	}
	
	public void setMoveB(boolean isMoveB){
		moveB = isMoveB;
		if(moveB) update();
	}
	
	public boolean getMoveB(){
		return moveB;
	}
	
	public int getID(){
		return id;
	}
	public void setID(int newID){
		id = newID;
	}
	
	
	
	
	
}
