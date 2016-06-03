import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class Canvas extends JPanel{
	private ClientServer client;
	private ClientServer server; 
	private boolean attachClient;
	private boolean attachServer;
	private int count;
	 ArrayList<DShape> list; //the list of shapes
	private DShape selected;	//the selected Shape
	private Whiteboard b;		//the current whiteboard
	private Listener i;
	//drags and resizes the shapes
	class Listener extends MouseAdapter{
		private boolean drag;
		private boolean resize;
		private int xOffset;
		private int yOffset;
		private int x2Offset;
		private int y2Offset;
		private Point anchorPoint;
		private boolean down;
		@Override
		public void mouseClicked(MouseEvent e) {
			for(int i = 0;i < list.size();i++){
				list.get(i).deSelect();
			}
			boolean first = true;
			int x = e.getX();
			int y = e.getY();
			for(int i=list.size()-1; i>=0; i--){
				if(first){
					if(list.get(i).checkBounds(x, y)){
						selected = list.get(i);
						if(selected instanceof DText){
							b.setFontEnabled(true);
						}
						else{b.setFontEnabled(false);}
						selected.select();
						first = false;
					}
					else{
						selected = null;
						b.setFontEnabled(false);
					}
				}
			}	
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			if(selected!=null && selected.checkKnobBounds(x, y)){
				anchorPoint = selected.getPoint();
				if(anchorPoint.y>y && anchorPoint.x<x){
					down = true;
				}
				resize = true;
			}
			else if(selected!=null && selected.checkBounds(x, y)){
				drag = true;
				xOffset = x - selected.getX();
				yOffset = y - selected.getY();
				
				if(selected instanceof DLine){
					x2Offset = x - ((DLine)selected).getX2();
					y2Offset = y - ((DLine)selected).getY2();
				}
				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			drag = false;
			resize = false;
			down = false;
		}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(drag){
				selected.setMoved(true);
				int x = e.getX();
				int y = e.getY();
				int newX = x-xOffset;
				int newY = y-yOffset;
				selected.setX(newX);
				selected.setY(newY);
				
				if(selected instanceof DLine){
					int newX2 = x-x2Offset;
					int newY2 = y-y2Offset;
					((DLine)selected).setX2(newX2);
					((DLine)selected).setY2(newY2);
				}	
			}
			
			else if(resize){
				selected.setResize(true);
				int x1 = anchorPoint.x;
				int y1 = anchorPoint.y;
				int x2 = e.getX();
				int y2 = e.getY(); 
				
				if(x2<x1 && y2<y1){//top left-----Works
					if(selected instanceof DLine){
						if(down){
							selected.setWidth(x1 - x2);
							selected.setHeight(y1 - y2);
							((DLine)selected).setX2(x2);
							((DLine)selected).setY2(y2);
							
						}
						
						else{
							selected.setWidth(x1 - x2);
							selected.setHeight(y1 - y2);
							selected.setX(x2);
							selected.setY(y2);
						}
					}
					else{
						selected.setWidth(x1-x2);
						selected.setX(x2);
						selected.setHeight(y1-y2);
						selected.setY(y2);
					}
				}
				
				else if(x2>x1 && y2<y1){//top right
					if(selected instanceof DLine){
						if(down){
							selected.setWidth(x2 - x1);
							selected.setHeight(y1 - y2);
							((DLine)selected).setX2(x2);
							((DLine)selected).setY2(y2);
							
						}
						
						else{
							selected.setWidth(x2 - x1);
							selected.setHeight(y1 - y2);
							selected.setX(x2);
							selected.setY(y2);
							
						}
					}
					else{
						selected.setWidth(x2-x1);
						selected.setX(x1);
						selected.setHeight(y1-y2);
						selected.setY(y2);
					}
					
				}
				
				else if(x2<x1 && y2>y1){//bottom left
					if(selected instanceof DLine){
						if(down){
							selected.setWidth(x1 - x2);
							selected.setHeight(y2 - y1);
							((DLine)selected).setX2(x2);
							((DLine)selected).setY2(y2);
							
						}
						
						else{
							selected.setWidth(x1 - x2);
							selected.setHeight(y2 - y1);
							selected.setX(x2);
							selected.setY(y2);
							
						}
						
					}
					else{
						selected.setWidth(x1-x2);
						selected.setX(x2);
						selected.setHeight(y2-y1);
						selected.setY(y1);
					}
				}
				
				else{//bottom right-----Works
					
					if(selected instanceof DLine){
						if(down){
							selected.setWidth(x2-x1);
							selected.setHeight(y2-y1);
							((DLine)selected).setX2(x2);
							((DLine)selected).setY2(y2);
							
						}
						
						else{
							selected.setWidth(x2-x1);
							selected.setHeight(y2-y1);
							selected.setX(x2);
							selected.setY(y2);
							
						}	
						
					}
					
					else{
						selected.setWidth(x2-x1);
						selected.setX(x1);
						selected.setHeight(y2-y1);
						selected.setY(y1);	
					}
				}
			}
		}

		public void mouseMoved(MouseEvent e) {}
		
	}
	
	
	public Canvas(Whiteboard board){
		super();
		count=0;
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);	
		list = new ArrayList<DShape>();
		i = new Listener();
		addMouseListener(i);
		addMouseMotionListener(i);
		b = board;
		client = new ClientServer(this);
		server = new ClientServer(this);
		attachClient = false;
		attachServer = false;
	}
	
	//adds the shape to the canvas, depending on from file or from button click
	public void addShape(DShapeModel model, boolean fromFile){
		int initX = 10;
		int initY = 10;
		int initHeight = 20;
		int initWidth = 20;
		for(int i=0; i<list.size(); i++){
			list.get(i).deSelect();
		}
		if(model instanceof DRectModel){
			model.addListeners(b);
			if(!fromFile){
				model.setX(initX);
				model.setY(initY);
				model.setHeight(initHeight);
				model.setWidth(initWidth);
			}
			b.setFontEnabled(false);
			model.addListeners(b.getTable());
			DRect newRect = new DRect();
			newRect.attachModel(model);
			selected = newRect;
			newRect.select();
			model.addListeners(newRect);
			
			if(model.getID() ==0){
			count++;
			model.setID(count);
			}
			if(attachClient) model.addListeners(client);
			if(attachServer) model.addListeners(server);
			list.add(newRect);
			model.setAdd(true);
		}
		
		else if(model instanceof DOvalModel){
			model.addListeners(b);
			if(!fromFile){
				model.setX(initX);
				model.setY(initY);
				model.setHeight(initHeight);
				model.setWidth(initWidth);
			}
			b.setFontEnabled(false);
			model.addListeners(b.getTable());
			DOval newOval = new DOval();
			newOval.attachModel(model);
			selected = newOval;
			newOval.select();
			if(model.getID() ==0){
				count++;
				model.setID(count);
				}
			model.addListeners(newOval);
			if(attachClient) model.addListeners(client);
			if(attachServer) model.addListeners(server);
			list.add(newOval);
			model.setAdd(true);
		}
		
		else if(model instanceof DTextModel){
			model.addListeners(b);
			if(!fromFile){
				if(model.getID() ==0){
					count++;
					model.setID(count);
					}
				b.setFontEnabled(true);
				model.setX(initX);
				model.setY(initY);
				model.setHeight(initHeight);
				model.setWidth(initWidth);
				model.addListeners(b.getTable());
				DText newText = new DText();
				newText.attachModel(model);
				selected = newText;
				newText.select();
				model.addListeners(newText);
				if(attachClient) model.addListeners(client);
				if(attachServer) model.addListeners(server);
				list.add(newText);
				model.setAdd(true);
			}
			else{
				if(model.getID() ==0){
					count++;
					model.setID(count);
					}
				model.addListeners(b.getTable());
				b.setFontEnabled(true);
				DText newText = new DText();
				newText.attachModel(model);
				selected = newText;
				
				model.setID(count);
				newText.openedFromFile();
				model.addListeners(newText);
				if(attachClient) model.addListeners(client);
				if(attachServer) model.addListeners(server);
				list.add(newText);
				model.setAdd(true);
			}
			
		}
		
		else if(model instanceof DLineModel){
			model.addListeners(b);
			if(!fromFile){
				model.setX(initX);
				model.setY(initY);
				model.setHeight(initHeight);
				model.setWidth(initWidth);
				((DLineModel)model).setX2(initWidth+initX);
				((DLineModel)model).setY2(initHeight + initY);
			}
			b.setFontEnabled(false);
			model.addListeners(b.getTable());
			DLine newLine = new DLine();
			newLine.attachModel(model);
			selected = newLine;
			newLine.select();
			if(model.getID() ==0){
				count++;
				model.setID(count);
				}
			model.addListeners(newLine);
			if(attachClient) model.addListeners(client);
			if(attachServer) model.addListeners(server);
			list.add(newLine);
			model.setAdd(true);
		}
	}
	
	//changes the string
	public void changeString(String text){
		for(int i =0; i < list.size(); i++){
			DShape s = list.get(i);
			if(s instanceof DText && s.isSelected()){
				((DText)s).setText(text);
				((DText)s).setTextChanged(true);
			}
		}
		
	}
	
	//changes the font
	public void changeFont(Font newFont){
		for(int i =0;i<list.size();i++){
			DShape s = list.get(i);
			if(s instanceof DText && s.isSelected()){
				((DText)s).setFont(newFont);
				((DText)s).setFontChanged(true);
			}
		}
	}
	
	//moves the shape to the top most shape
	public void moveToFront(){
		for(int i =0;i <list.size();i++){
			DShape s = list.get(i);
			if(s.isSelected()){
				list.remove(i);
				list.add(s);
				s.setMoveFront(true);
				s.setMoveF(true);
			}
		}
		
	}
	
	//moves the shape to the back of all other shapes
	public void moveToBack(){
		for(int i =0; i < list.size();i++){
			DShape s = list.get(i);
			if(s.isSelected()){
				list.remove(i);
				list.add(0, s);
				s.setMoveBack(true);
				s.setMoveB(true);
			}
		}
	}
	
	//removes the shape from the list
	public void removeShape(){
		for(int i =0; i< list.size();i++){
			if(list.get(i).isSelected()){
				list.get(i).deSelect();
				list.get(i).setDeleted(true);
				list.remove(i);
				selected=null;
			}
		}
	}
	
	//changes the color of the shape
	public Color changeColor(){
		if(selected!=null){
			Color color = JColorChooser.showDialog(null, "Pick", Color.GRAY);
			selected.setColor(color);
			selected.setColored(true);
			return color;
		}
		return null;
	}
	
	//saves the shape to a file
	public void save(){
		String result = JOptionPane.showInputDialog("File Name", null);
		if (result != null) {
            File f = new File(result);
            doSave(f);
		}
	}
	
	//helper method for saving the file, actually saves the file
	public void doSave(File file){
		try{
			 XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(
			            new FileOutputStream(file)));
			 DShapeModel[] shapeArray = getModels();
			 xmlOut.writeObject(shapeArray);
			 xmlOut.close(); 
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(b, "Save Failed");
		}
	}
	
	//helper method for saving the file, gets all of the models for saving
	public DShapeModel[] getModels(){
		DShapeModel[] models = new DShapeModel[list.size()];
		for(int i = 0; i<models.length; i++){	
			models[i] = list.get(i).getModel();
		}
		
		return models;
	}
	
	//opens the file saves as an xml file
	public void open(){
		String result = JOptionPane.showInputDialog("File Name", null);
		if (result != null) {
            File f = new File(result);
            doOpen(f);
        }
	}
	
	//helper method for open, actually opens the file
	public void doOpen(File file){
		DShapeModel[] modelArray = null;
		try{
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(
		            new FileInputStream(file))); 
			
			modelArray = (DShapeModel[]) xmlIn.readObject();
			xmlIn.close();
			clear();
			for(int i = 0; i<modelArray.length; i++){
				addShape(modelArray[i], true);
			}
			
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(b, "File Not Found");
			//e.printStackTrace();
		}
	}
	
	//clears the canvas and table in preparation for the loaded file
	public void clear(){
		selected = null;
		for(int i = 0; i<list.size(); i++){
			list.get(i).setDeleted(true);
		}
		list.clear();
		repaint();
		
	}
	
	//saves the canvas as a png file
	public void savePNG(){
		String result = JOptionPane.showInputDialog("File Name", null);
        if (result != null) {
            File f = new File(result);
            saveImage(f);
        }
	}
	
	//helper method for savePNG, actually saves the image to a PNG
	public void saveImage(File file){
		if(selected!=null) selected.deSelect();
		BufferedImage image = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics g = image.getGraphics();
		paintAll(g);
		 g.dispose();
		 try {
	            javax.imageio.ImageIO.write(image, "PNG", file);
	        }
	        catch (IOException ex) {
	            ex.printStackTrace();
	        }
		 if(selected!=null)selected.select();
	}

	//start the server
	public void startServer(){
		attachServer = true;
		server.doServer();
	}
	
	//starts the client
	public String startClient(){
		
		attachClient = true;
		String s = client.doClient();
		if(s==null){
			return s;
		}
		this.removeMouseListener(i);
		clear();
		return s;
		
		
	}
	
	//paints the canvas
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i =0; i<list.size(); i++){
			list.get(i).draw(g);
		}
	}
	
	
	
	public void setSelected(DShape shape){
		
		
		if(selected == null){
			selected=shape;
			
	
			selected.select();
			
		}
		else{
		selected.deSelect();
		selected=shape;
		selected.select();
		}
	}
	public DShape getSelected(){
		return selected;
	}
	
	
	
		
		

}
