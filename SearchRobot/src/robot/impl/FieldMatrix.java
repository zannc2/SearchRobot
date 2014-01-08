package robot.impl;

import frontend.impl.items.Finish;
import frontend.impl.items.Robot;
import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.util.List;

/**
 * A Matrix implementation which represents the field with his items as a matrix.
 * @author zannc2 & gfells4
 *
 */
public class FieldMatrix {

	private int[][] fieldMatrix;
	private int fieldMatrixWidth, fieldMatrixHeight;
	private int gridSize;
	
	/**
	 * Constructor which creates a new matrix.
	 * Field is unknown, so there are no items to fill in the matrix. 
	 * @param fieldSize the size of the field
	 * @param gridSize the size of each grid
	 */
	public FieldMatrix(Size fieldSize, Size gridSize) {
		this(fieldSize, gridSize, new Field(fieldSize, gridSize));
	}
	
	/**
	 * Constructor which creates a new matrix and fills it with the items in the given
	 * field.
	 * @param fieldSize the size of the field
	 * @param gridSize the size of each grid
	 * @param f the given field
	 */
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
	
	/**
	 * returns the value on the asked position
	 * @param p asked position 
	 * @return returns the value on the position, 0:unknown/robot, 1:item, 2:finish
	 */
	public int contains(Position p) {
		return fieldMatrix[p.getOriginX()][p.getOriginY()];
	}
	
	/**
	 * sets a value to a given position
	 * @param p given position
	 * @param value new value
	 */
	public void set(Position p, int value) {
		this.fieldMatrix[p.getOriginX()][p.getOriginY()] = value;
	}
	
}
