package robot.classes;

import frontend.impl.items.Finish;
import frontend.impl.items.Robot;
import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.util.List;

public class FieldMatrix {

	private int[][] fieldMatrix;
	private int fieldMatrixWidth, fieldMatrixHeight;
	private int gridSize;
	
	public FieldMatrix(Size fieldSize, Size gridSize) {
		this(fieldSize, gridSize, new Field(fieldSize, gridSize));
	}
	
	public FieldMatrix(Size fieldSize, Size gridSize, Field f) {
		this.fieldMatrixWidth = fieldSize.getWidth()/gridSize.getWidth();
		this.fieldMatrixHeight = fieldSize.getHeight()/gridSize.getWidth();
		this.fieldMatrix = new int[this.fieldMatrixWidth][this.fieldMatrixHeight];
		this.gridSize = gridSize.getWidth();
		
		List<Item> items = f.getItems();
		
		for(int i = 0; i < this.fieldMatrixWidth; i++){
			for(int j = 0; j < this.fieldMatrixHeight; j++) {
				for(Item item:items){
					if(fieldMatrix[i][j] == 0)
					{
						if(item.contains(new Position(i*this.gridSize + this.gridSize/3, j*this.gridSize + this.gridSize/3)) ||
								item.contains(new Position(i*this.gridSize + this.gridSize/3, j*this.gridSize + (this.gridSize/3)*2)) ||
								item.contains(new Position(i*this.gridSize + (this.gridSize/3)*2, j*this.gridSize + this.gridSize/3)) ||
								item.contains(new Position(i*this.gridSize + (this.gridSize/3)*2, j*this.gridSize + (this.gridSize/3)*2))
								) 
						{
							if(item instanceof Finish) fieldMatrix[i][j] = 2;
							else if(item instanceof Robot) fieldMatrix[i][j] = 0;
							else fieldMatrix[i][j] = 1;
						}
						else fieldMatrix[i][j] = 0;
					}
				}
			}
		}
	}
	
	
	public int contains(Position p) {
		return fieldMatrix[p.getOriginX()][p.getOriginY()];
	}
	
	public void set(Position p, int value) {
		this.fieldMatrix[p.getOriginX()][p.getOriginY()] = value;
	}
	
}
