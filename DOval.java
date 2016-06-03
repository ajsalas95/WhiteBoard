import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class DOval extends DShape{
	public DOval(){
		super();
	}
	
	public void draw(Graphics g){
		super.draw(g);
		g.fillOval(getModel().getX(), getModel().getY(), getModel().getWidth(), getModel().getHeight());
		if(getModel().isSelected()){
			ArrayList<Point> points = getKnobs();
			for(Point point : points){
				g.setColor(Color.BLACK);
				g.fillRect(point.x-4, point.y-4, 9, 9);
			}
		}
	}
}
