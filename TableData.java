import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableData extends AbstractTableModel implements ModelListener{
	String[] columnNames;
	ArrayList<DShapeModel> data;
	public TableData(){
		columnNames = new String[4];
		columnNames[0] = "X";
		columnNames[1] = "Y";
		columnNames[2] = "Width";
		columnNames[3] = "Height";
		data = new ArrayList<DShapeModel>();
		
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 0){
			return data.get(rowIndex).getX();
		}
		
		if(columnIndex == 1){
			return data.get(rowIndex).getY();
		}
		
		if(columnIndex == 2){
			return data.get(rowIndex).getWidth();
		}
		
		else{
			return data.get(rowIndex).getHeight();
		}
		
	}

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
		if(data.size()==0){
			data.add(model);
		}
		
		else{
			int count = 0;
			for(int i = 0; i<data.size(); i++){
				
				if(data.get(i).equals(model)){
					if(model.getDeleted()){
						data.remove(i);
					}
					
					
					else if(model.getMoveFront()){
						data.remove(i);
						data.add(0, model);
						 model.setMoveFront(false);
						
					}
						
					else if(model.getMoveBack()){
						data.remove(i);
						data.add(model);
						 model.setMoveBack(false);
					}
					
					count++;
				}
			}
			
			if(count == 0){
				data.add(0, model);
			}
		}
		this.fireTableDataChanged();
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}

}
