import java.beans.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ClientServer implements ModelListener{
	 private java.util.List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	 private ClientHandler clientHandler;
	 private ServerAccepter serverAccepter;
	 private Canvas c;
	 public ClientServer(Canvas c)  {
		  this.c = c;
	  }
	
	 
	public void doServer() {
        String result = JOptionPane.showInputDialog("Run server on port", "39587");
        if (result!=null) {
            
            serverAccepter = new ServerAccepter(Integer.parseInt(result.trim()));
            serverAccepter.start();
        }
        
    }
    
	 
	
    
    public String doClient() {
        String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:39587");
        
        if(result==null){
        	return result;
        }
        else if (result!=null) {
            String[] parts = result.split(":");
            
            clientHandler = new ClientHandler(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            clientHandler.start();
            
        }
        return result;
    }
    
	
	
    
    public synchronized void sendRemote(Message message) {
        
        // Convert the message object into an xml string.
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(message);
        encoder.close();
        String xmlString = memStream.toString();
        // Now write that xml string to all the clients.
        Iterator<ObjectOutputStream> it = outputs.iterator();
        while (it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
                out.writeObject(xmlString);
                out.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                it.remove();
            }
        }
    }
    
   
    
    public synchronized void addOutput(ObjectOutputStream out) {
        outputs.add(out);
    }
	
	
	public static class Message {
        public DShapeModel model;
		public String text;
        public Message() {
            text = null;
            model = null;
        }

        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        
        public DShapeModel getModel(){
        	return model;
        }

        public void setModel(DShapeModel model) {
            this.model = model;
        }
        
        public String toString() {
            return "message: " + text;
        }  
    }
	
	
	 private class ClientHandler extends Thread {
         private String name;
         private int port;
         ClientHandler(String name, int port) {
             this.name = name;
             this.port = port;
         }
     // Connect to the server, loop getting messages
         public void run() {
             try { 
                 Socket toServer = new Socket(name, port); // make connection to the server name/port
                 ObjectInputStream in = new ObjectInputStream(toServer.getInputStream()); // get input stream to read from server and wrap in object input stream
                 
                 while (true) {
                     String xmlString = (String) in.readObject();
                     XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
                     Message message = (Message) decoder.readObject();
                     invokeToGUI(message);  
                 }
             }
             catch (Exception ex) { // IOException and ClassNotFoundException
                
             }
        }
    } 
	
	 
	 
	 public void invokeToGUI(Message message) {
	        final Message temp = message;
	        SwingUtilities.invokeLater( new Runnable() {
	            public void run() {
	       
	            if("ADD".equals(temp.getText())){
	       			c.addShape(temp.getModel(), true);  //CHANGE BACK TO TRUE
	       			
	       		 }
	       		 else if("Deleted".equals(temp.getText())){
	       			 c.removeShape();
	       		 }
	       		 
	       		 else if("MoveBack".equals(temp.getText())){
	       			 
	       			 c.moveToBack();
	       		 }
	       		 else if("MoveFront".equals(temp.getText())){
	       			
	       			 c.moveToFront();
	       		 }
	       		 else if("Resize".equals(temp.getText())){
	       			 //dont know what to do yet
	       			c.getSelected().setWidth(temp.getModel().getWidth());
	       			c.getSelected().setHeight(temp.getModel().getHeight());
	       			c.getSelected().setX(temp.getModel().getX());
	       			c.getSelected().setY(temp.getModel().getY());
	       			
	       			if(temp.getModel() instanceof DLineModel){
	       				//c.getSelected().setX(temp.getModel().getX());
	       				//c.getSelected().setY(temp.getModel().getY());
	       				((DLineModel)c.getSelected().getModel()).setX2(((DLineModel)temp.getModel()).getX2());
	       				((DLineModel)c.getSelected().getModel()).setY2(((DLineModel)temp.getModel()).getY2());
	       			}
	       		 }
	       		 
	       		 else if("Moved".equals(temp.getText())){
	       			 //dont know what to do yet
	       			c.getSelected().setX(temp.getModel().getX());
	       			c.getSelected().setY(temp.getModel().getY());
	       			if(temp.getModel() instanceof DLineModel){
	       				((DLineModel)c.getSelected().getModel()).setX2(((DLineModel)temp.getModel()).getX2());
	       				((DLineModel)c.getSelected().getModel()).setY2(((DLineModel)temp.getModel()).getY2());
	       			}
	       		 }
	       		 
	       		 else if("Color".equals(temp.getText())){
	       			
	       			c.getSelected().setColor(temp.getModel().getColor());
	       		 }
	       		 
	       		 else if("FontChanged".equals(temp.getText())){
	       			
	       			c.changeFont((((DTextModel)temp.getModel()).getFont())); 
	       		}
	       			 
	       		 else if("TextChanged".equals(temp.getText())){
	       			 c.changeString((((DTextModel)temp.getModel()).getText())); 
	       		}
	       		
	       		 if("Selected".equals(temp.getText())){
	       			
	       			if(c.getSelected() == null){
	       				for(int i = 0; i<c.list.size(); i++){
	       					if(c.list.get(i).getID() ==temp.getModel().getID() ){
	       						
	       						c.setSelected(c.list.get(i));
	       					}
	       				}
	       			 }
	       			else if(c.getSelected().getID() != temp.getModel().getID()){
	       				for(int i = 0; i<c.list.size(); i++){
	       					if(c.list.get(i).getModel().getID() ==temp.getModel().getID() ){
	       						c.setSelected(c.list.get(i));	
	       					}
	       				}
	       			 }
	       			
	    		 }
	            
	            }
	           
	        });
	        
	         
	    }
	 
	 class ServerAccepter extends Thread {
	        private int port;
	        ServerAccepter(int port) {
	            this.port = port;
	        }
	        public void run() {
	            try {
	                ServerSocket serverSocket = new ServerSocket(port);
	                while (true) {
	                    Socket toClient = null;
	                    toClient = serverSocket.accept();
	                    
	                    addOutput(new ObjectOutputStream(toClient.getOutputStream()));
	                    
	                    DShapeModel[] models = c.getModels();
	                    for(int i =0; i<models.length; i++){
	                    	Message m = new Message();
	                    	m.setText("ADD");
	                    	m.setModel(models[i]);
	                    	invokeToGUI(m);
	                    }
	                    
	                }
	            } catch (IOException ex) {
	                 
	            }
	        }
	    }

	
	
	 
	
	public void modelChanged(DShapeModel model) {
		Message message = new Message();
		message.setModel(model);
		
		if(model.getAdd()){
			 message.setText("ADD");
			 model.setAdd(false);
		 }
		 else if(model.getDeleted()){
			 message.setText("Deleted");
		 }
		 
		 else if(model.getMoveB()){
			 message.setText("MoveBack");
			 model.setMoveB(false);
		 }
		 else if(model.getMoveF()){
			 message.setText("MoveFront");
			 model.setMoveF(false);
		 }
		 else if(model.getResize()){
			 message.setText("Resize");
			 model.setResize(false);
		 }
		 
		 else if(model.getMoved()){
			 message.setText("Moved");
			 model.setMoved(false);
		 }
		 
		 else if(model.getColored()){
			 message.setText("Color");
			 model.setColored(false);
		 }
		
		 else if(model instanceof DTextModel){
			 if(((DTextModel)model).getFontChanged()){
				message.setText("FontChanged");
				((DTextModel)model).setFontChanged(false);
			 }
			 
			 else if(((DTextModel)model).getTextChanged()){
					message.setText("TextChanged");
					((DTextModel)model).setTextChanged(false);
				 }
		 }
		 
		 else{
			 message.setText("Selected");
		 }
		 
		 sendRemote(message);
	}
	
	
	
}