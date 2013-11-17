package robot.classes;

import frontend.classes.items.Finish;
import frontend.classes.items.Robot;
import frontend.classes.view.Field;
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
						if(item.contains(new Position(i*this.gridSize + this.gridSize/2, j*this.gridSize + this.gridSize/2))) {
							if(item instanceof Finish) fieldMatrix[i][j] = 2;
							else if(item instanceof Robot) fieldMatrix[i][j] = 0;
							else fieldMatrix[i][j] = 1;
						}
						else fieldMatrix[i][j] = 0;
					}
				}
			}
		}
//		printArray();
	}
	
	
	public int contains(Position p) {
		return fieldMatrix[p.getOriginX()][p.getOriginY()];
	}
	
	public void set(Position p, int value) {
		this.fieldMatrix[p.getOriginX()][p.getOriginY()] = value;
	}
	
	public void printArray()
	{
		for(int j = 0; j < fieldMatrixHeight; j++) {
			for(int i = 0; i < fieldMatrixWidth; i++){
				System.out.print("[" + fieldMatrix[i][j] + "]");
			}
			System.out.println();
		}
	}
	
}
