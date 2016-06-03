import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

	public class DText extends DShape{
	private DTextModel model;
	boolean first=true;
	public DText(){
		super();
	}
	
	public void openedFromFile(){
		first = false;
	}
	public void draw(Graphics g){
		g.setClip(0,0,400,400);
		Font f = computeFont(g);
		g.setFont(f);
		ArrayList<Point> points = getKnobs();
		if(model.isSelected()){
			for(Point point : points){
				g.setColor(Color.BLACK);
				g.fillRect(point.x-4, point.y, 9, 9);
			}
		
		}
		
		Point point = points.get(2);
		Shape clip = g.getClip();
		g.setColor(getColor());
		g.drawString(model.getText(),point.x,point.y);
		g.setClip(clip.getBounds().createUnion(new Rectangle(0,0,400,600)));
	}
	//this still does not work and IDK what to do with it exactly
	public Font computeFont(Graphics g){
		double size = 1.0;
		boolean fits = true;
		Rectangle2D rect = getBoundsRectangle(g);
		
		Font font = new Font(model.getFontName(),Font.PLAIN,(int) size);
		double previous = 0;
		while(fits){
			if(size < rect.getHeight()){
				previous = size;
				size = (size*1.1)+1;
			}
			else{
				fits = false;
			}
		}
		font = new Font(model.getFontName(), Font.PLAIN, (int) (previous));
		Shape clip = g.getClip();
		Rectangle2D cliprec = new Rectangle(model.getX()-4, model.getY(), (int)rect.getWidth()+9, (int)rect.getHeight()+15);
		g.setClip(clip.getBounds().createIntersection(cliprec));
		return font;
	}
	
	public Rectangle2D getBoundsRectangle(Graphics g){
		if(first){
			Font firstFont = g.getFont();
			FontMetrics firstFM = g.getFontMetrics(firstFont);
			Rectangle2D rect = firstFM.getStringBounds(model.getText(), g);
			model.setWidth((int)rect.getWidth());
			model.setHeight((int)rect.getHeight());
			first = false;
			return rect;
		}
		else{
			Rectangle2D rect = new Rectangle(model.getWidth(),model.getHeight());
			return rect;
		}
	}
	
	public void setText(String newText){
		model.setText(newText);
	}
	
	public void setFont(Font newFont){
		model.setFont(newFont);
	}
	
	public void attachModel(DTextModel textModel){
		model = textModel;
	}
	
	public boolean checkBounds(int x, int y){
		ArrayList<Point> points = model.getKnobs();
		Point tl = points.get(0);
		Point br = points.get(3);
		
		if((x>=tl.getX() && x<= br.getX()) && (y >= tl.getY() && y <= br.getY())){
			return true;
		}
		return false;
	}
	
	public boolean checkKnobBounds(int x, int y){
		ArrayList<Point> points = getKnobs();
		int currentPoint=0;
		for(Point point : points){
			currentPoint++;
			if(((point.x-4<=x)&&(x<=(point.x+5))) && ((point.y-4<=y)&&(y<=(point.y+5)))){
				model.select();
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
	
	public void moved(){
		model.update();
	}
	
	// table methods
	
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
	
	public Point getPoint(){
		return model.getPoint();
	}
	
	public void setMoveFront(boolean isMoveFront){
		model.setMoveFront(isMoveFront);
	
	}
	
	public boolean getMoveFront(){
		return model.getMoveFront();
	}
	
	protected DShapeModel getModel(){
		return model;
	}
	
	public void attachModel(DShapeModel theModel){
		model = (DTextModel)theModel;
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
	
	public boolean getTextChanged(){
		return model.getTextChanged();
	}
	
	public void setTextChanged(boolean isTextChanged){
		model.setTextChanged(isTextChanged);;
	}
	
	public boolean getFontChanged(){
		return model.getFontChanged();
	}
	
	public void setFontChanged(boolean isFontChanged){
		model.setFontChanged(isFontChanged);
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
