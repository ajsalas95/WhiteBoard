import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
public class DShape implements ModelListener{
	private DShapeModel model;
	
	
	public void draw(Graphics g){
		g.setColor(model.getColor());
	}
	
	public void attachModel(DShapeModel theModel){
		model = theModel;
	}
	
	public boolean checkBounds(int x, int y){
		if(model.getBounds(x, y)){
			model.select();
			return true;
			
		}
		else {
			model.deSelect();
			return false;
	}
	}
	public boolean checkKnobBounds(int x, int y){
		ArrayList<Point> points = getKnobs();
		int currentPoint=0;
		for(Point point : points){
			currentPoint++;
			if(((point.x-4<=x)&&(x<=(point.x+5))) && ((point.y-4<=y)&&(y<=(point.y+5)))){
				if(currentPoint == 1){
					saveOpposingPoint(point.x + model.getWidth(),point.y+model.getHeight());
				}
				else if(currentPoint == 2){
					saveOpposingPoint(point.x - model.getWidth(),point.y + model.getHeight());
				}
				else if(currentPoint == 3){
					saveOpposingPoint(point.x + model.getWidth(),point.y - model.getHeight());
				}
				else{
					saveOpposingPoint(point.x - model.getWidth(),point.y - model.getHeight());
				}
				return true;		
			}
		}
		return false;
	}
	protected DShapeModel getModel(){
		return model;
	}
	
	public void saveOpposingPoint(int x, int y){
		Point p = new Point(x,y);
		model.setPoint(p);
	}
	
	public ArrayList<Point> getKnobs(){
		return model.getKnobs();
	}
	
	public void setColor(Color newColor){
		model.setColor(newColor);
	}
	
	public Color getColor(){
		return model.getColor();
	}
	
	public void setX(int newX){
		model.setX(newX);
	}
	
	public int getX(){
		return model.getX();
	}
	
	public void setY(int newY){
		model.setY(newY);
	}
	
	public int getY(){
		return model.getY();
	}
	
	public void setWidth(int newWidth){
		model.setWidth(newWidth);
	}
	
	public int getWidth(){
		return model.getWidth();
	}
	
	public void setHeight(int newHeight){
		model.setHeight(newHeight);
	}
	
	public int getHeight(){
		return model.getHeight();
	}
	
	public void select(){
		model.select();
	}
	public void deSelect(){
		model.deSelect();
	}
	public boolean isSelected(){
		return model.isSelected();
	}
	

	@Override
	public void modelChanged(DShapeModel model) {
		
	}
	
	public Point getPoint(){
		return model.getPoint();
	}
	
	public boolean getDeleted(){
		return model.getDeleted();
	}
	
	public void setDeleted(boolean isDeleted){
		model.setDeleted(isDeleted);
	}
	
	public void setMoveBack(boolean isMoveBack){
		model.setMoveBack(isMoveBack);
	
	}
	
	public boolean getMoveBack(){
		return model.getMoveBack();
	}
	
	
	public void setMoveFront(boolean isMoveFront){
		model.setMoveFront(isMoveFront);
	
	}
	
	public boolean getMoveFront(){
		return model.getMoveFront();
	}
	
	

	public void setAdd(boolean isAdd){
		model.setAdd(isAdd);
	}
	
	public boolean getAdd(){
		return model.getAdd();
	}
	
	public void setMoved(boolean isMove){
		model.setMoved(isMove);
	}
	
	public boolean getMoved(){
		return model.getMoved();
	}
	public void setResize(boolean isResize){
		model.setResize(isResize);;
	}
	
	public boolean getResize(){
		return model.getResize();
	}
	
	public void setColored(boolean isColored){
		model.setColored(isColored);
	}
	
	public boolean getColored(){
		return model.getColored();
	}	
	
	public void setMoveF(boolean isMoveF){
		model.setMoveF(isMoveF);
	}
	
	public boolean getMoveF(){
		return model.getMoveF();
	}
	
	public void setMoveB(boolean isMoveB){
		model.setMoveB(isMoveB);
	}
	
	public boolean getMoveB(){
		return model.getMoveB();
	}	
	
	public int getID(){
		return model.getID();
	}
	
	public void setID(int newID){
		model.setID(newID);;
	}
	
	
}
