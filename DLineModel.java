
import java.awt.Point;
import java.util.ArrayList;

public class DLineModel extends DShapeModel{
	private int x2;
	private int y2;
	public DLineModel(){
		super();
		x2 = 0;
		y2 = 0;
	}
	
	public void setX2(int newX2){
		x2 = newX2;
	}
	
	public void setY2(int newY2){
		y2 = newY2;
	}
	
	
	public int getX2(){
		return x2;
	}
	
	public int getY2(){
		return y2;
	}
	
	
	public boolean getBounds(int checkX, int checkY){
		int x1 = getX();
		int y1 = getY();
		if(x2>getX() && y2>getY()){
			if((checkX>=x1 && checkX<=x2) && (checkY>=y1 && checkY<=y2)){
				return true;
			}
			return false;
		}
		
		if(getX()>x2 && y2>getY()){
			if((checkX>=x2 && checkX<=x1) && (checkY>=y1 && checkY<=y2)){
				return true;
			}
			return false;
		}
		
		if(getX()>x2 && getY()>y2){
			if((checkX>=x2 && checkX<=x1) && (checkY>=y2 && checkY<=y1)){
				return true;
			}
			return false;
		}
		
		if(x2>getX() && getY()>y2){
			if((checkX>=x1 && checkX<=x2) && (checkY>=y2 &&checkY<=y1)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public ArrayList<Point> getKnobs(){
		ArrayList<Point> points = new ArrayList<Point>();
		//get first point
		Point p1 = new Point();
		p1.x = getX();
		p1.y = getY();
		points.add(p1);
		//get second point
		Point p2 = new Point();
		p2.x = getX2();
		p2.y = getY2();
		points.add(p2);
		return points;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
