import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

public class DTextModel extends DShapeModel{
	private String text;
	private String fontName;
	private Font font;
	private boolean textChanged;
	private boolean fontChanged;
	public DTextModel(){
		super();
		text = "Hello";
		fontName = "Dialog";
		textChanged = false;
		fontChanged = false;
	}
	/*
	 * Additional methods specific for the text model
	 */
	public String getText(){
		return text;
	}
	public String getFontName(){
		return fontName;
	}
	//not really used and might not be necessary if we can find a font with just the name
	public Font getFont(){
		return font;
	}
	public void setText(String newText){
		text = newText;
		super.update();
	}
	//same might not be necessary since i believe this needs to be done in the draw or computeFont method 
	public void setFont(Font newFont){
		font = newFont;
		fontName = newFont.getFontName();
		super.update();
	}
	public void setFontName(String newFont){
		fontName = newFont;
	}


	@Override
	public boolean getBounds(int checkX, int checkY){
		
		if(((checkX>=super.getX())&&(checkX<=(super.getX()+super.getWidth())) && ((checkY>=(super.getY()-super.getHeight()))&&(checkY<=((super.getY()+super.getHeight())-super.getHeight()))))){ 
			return true;
		}
		else return false;
	}
	
	public boolean getTextChanged(){
		return textChanged;
	}
	
	public void setTextChanged(boolean isTextChanged){
		textChanged = isTextChanged;
		if(textChanged) update();
	}
	
	public boolean getFontChanged(){
		return fontChanged;
	}
	
	public void setFontChanged(boolean isFontChanged){
		fontChanged = isFontChanged;
		if(fontChanged)update();
	}

}
