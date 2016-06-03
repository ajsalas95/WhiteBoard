import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Whiteboard extends JFrame implements ModelListener{
	private Canvas c;	//the current canvas
	private TableData td;	//stores the data for the table
	private JTextField fontText;
	private JComboBox<Font> fonts;
	private JButton[] buttons;
	//private 
	public static void main(String[] args){
		Whiteboard board = new Whiteboard("Whiteboard"); //starts the program
	}
	
	public Whiteboard(String title){
		super(title);
		buttons = new JButton[13];
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		c = new Canvas(this);
		add(c, BorderLayout.CENTER);
		JPanel b1 = addPanels();
		add(b1, BorderLayout.WEST);
		pack();
		setVisible(true);
		
	}
	
	//adds the panes for the buttons and the table to the frame
	public JPanel addPanels(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		mainPanel.setPreferredSize(new Dimension(600, 400));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0, 1));
		topPanel.setPreferredSize(new Dimension(600,200));
		addOptions(topPanel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 1));
		
		td = new TableData();
		JTable table = new JTable(td);
		
		JScrollPane viewTable = new JScrollPane(table);
		
		bottomPanel.add(viewTable);
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		
		return mainPanel;
	}
	
	//adds the buttons, text box, and font selector to the frame
	public void addOptions(JPanel panel){
		//file buttons
		Box fileButtons = new  Box(BoxLayout.X_AXIS);
		JLabel file = new JLabel("FILE: ");
		JButton save = new JButton("Save");
		buttons[0] = save;
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				c.save();
			}
		});
		JButton png = new JButton("Save as PNG");
		buttons[1] = png;
		png.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				c.savePNG();
			}
		});
		JButton open = new JButton("Open");
		buttons[2] = open;
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				c.open();	
			}
		});
		fileButtons.add(file);
		fileButtons.add(save);
		fileButtons.add(open);
		fileButtons.add(png);
		/*
		JLabel server = new JLabel("SERVER: ");
		final JButton serverStart = new JButton("Server Start");
		serverStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//c.startServer();
				serverStart.setText("Server Started");
				serverStart.setEnabled(false);
			}
		});
		final JButton clientStart = new JButton("Client Start");
		serverStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//c.startClient()	;
				clientStart.setText("Client Started");
				clientStart.setEnabled(false);
			}
		});
		
		
		fileButtons.add(server);
		fileButtons.add(serverStart);
		fileButtons.add(clientStart);
		*/
		panel.add(fileButtons);
		
		
		//Server Buttons
		final Box serverButtons = new  Box(BoxLayout.X_AXIS);
		JLabel server = new JLabel("SERVER: ");
		final JButton serverStart = new JButton("Server Start");
		buttons[3] = serverStart;
		serverStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				c.startServer();
				JLabel accepted = new JLabel("Server Mode");
				serverButtons.add(accepted);
				pack();
			}
		});
		final JButton clientStart = new JButton("Client Start");
		buttons[4] = clientStart;
		clientStart.addActionListener(new ActionListener(){
			@Override
			
			public void actionPerformed(ActionEvent e) {
				
				
				if(c.startClient() == null){
					
				}
				else{
					JLabel accepted = new JLabel("Client Mode");
					serverButtons.add(accepted);
					disableButtons();
					fontText.setEnabled(false);
					fonts.setEnabled(false);
					
					
				}
				pack();
				
			}
		});
		serverButtons.add(server);
		serverButtons.add(serverStart);
		serverButtons.add(clientStart);
		panel.add(serverButtons);
		
		//Shape Buttons
		Box shapeButtons = new Box(BoxLayout.X_AXIS);
		JLabel add = new JLabel("ADD: ");
		JButton rect = new JButton("Rectangle");
		buttons[5] = rect;
		rect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DShapeModel model = new DRectModel();
				c.addShape(model, false);
			}
		});
		JButton oval = new JButton("Oval");
		buttons[6] = oval;
		oval.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DShapeModel model = new DOvalModel();
				c.addShape(model, false);
				
			}
		});
		JButton line = new JButton("Line");
		buttons[7] = line;
		line.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DShapeModel model = new DLineModel();
				c.addShape(model, false);
				
			}
		});
		JButton text = new JButton("Text");
		buttons[8] = text;
		text.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DTextModel model = new DTextModel();
				c.addShape(model, false);
			}
		});
		shapeButtons.add(add);
		shapeButtons.add(rect);
		shapeButtons.add(oval);
		shapeButtons.add(line);
		shapeButtons.add(text);
		panel.add(shapeButtons);
		
		//Set Color button
		Box color = new Box(BoxLayout.X_AXIS);
		JButton setColor = new JButton("Set Color");
		buttons[9] = setColor;
		setColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				c.changeColor();
				
			}
		});
		color.add(setColor);
		panel.add(color);
		
		//add Text Field
		Box fontGroup = new Box(BoxLayout.X_AXIS);
		fonts = new JComboBox<Font>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
		fonts.setEnabled(false);
		fonts.setRenderer(new DefaultListCellRenderer() {
			   public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			      if (value != null) {
			         Font font = (Font) value;
			         value = font.getName();
			      }
			      return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			   }
		 });
		
		fonts.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = (Font) fonts.getSelectedItem();
				c.changeFont(font);
			}
			
		});
		
		//final JTextField fontText = new JTextField();
		fontText = new JTextField();
		fontText.setEnabled(false);
		
			fontText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String text = fontText.getText();
				if(!text.isEmpty()){
					c.changeString(text);
				}	
			}
			});
			
			
			
		
		fontGroup.add(fontText);
		fontGroup.add(fonts);
		panel.add(fontGroup);
		
		//move shape buttons
		Box shapeMovement = new Box(BoxLayout.X_AXIS);
		JButton mtf = new JButton("Move to Front");
		buttons[10] = mtf;
		mtf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				c.moveToFront();
			}
		});
		JButton mtb = new JButton("Move to Back");
		buttons[11] = mtb;
		mtb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				c.moveToBack();
			}
		});
		JButton rs = new JButton("Remove Shape");
		buttons[12] = rs;
		rs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				c.removeShape();
			}
		});
		shapeMovement.add(mtf);
		shapeMovement.add(mtb);
		shapeMovement.add(rs);
		panel.add(shapeMovement);
	}
	
	//allows the canvas to access the table data
	public TableData getTable(){
		return td;
	}
	

	//repaints the whiteboard after every model change
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		/*
		if((c.getSelected() instanceof DText) && (c.getSelected()!=null)){
			fontText.setEnabled(true);
		}
		else fontText.setEnabled(false);
		*/
		repaint();
	}
	
	public void setFontEnabled(boolean b){
		fontText.setEnabled(b);
		fonts.setEnabled(b);
	}
	
	public void disableButtons(){
		for(int i = 0; i< buttons.length; i++){
			buttons[i].setEnabled(false);
		}
	}
	
	public void enableButtons(){
		for(int i = 0; i< buttons.length; i++){
			buttons[i].setEnabled(true);
		}
	}
	
	
	

}
