package robot.classes;

import frontend.classes.items.Finish;
import frontend.classes.items.Robot;
import frontend.classes.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.util.List;

public class FieldMatrix {

	private int[][] field;
	
	public FieldMatrix(Size s) {
		this.field = new int[s.getWidth()][s.getHeight()];
	}
	
	public FieldMatrix(Size s, Field f) {
		int width = s.getWidth()/10;
		int height = s.getHeight()/10;
		this.field = new int[width][height];
		
		List<Item> items = f.getItems();
		
		//TODO fill Matrix
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++) {
				for(Item item:items){
					if(field[i][j] == 0)
					{
						if(item.contains(new Position(i*10 + 5, j*10 + 5))) {
							if(item instanceof Finish) {
								field[i][j] = 2;
							}
							else if(item instanceof Robot) field[i][j] = 0;
							else field[i][j] = 1;
						}
						else field[i][j] = 0;
					}
				}
//				field[i][j] = f.contains(p, epsilon)
			}
		}
		for(int j = 0; j<height; j++) {
			System.out.println("{");
			for(int i = 0; i<width; i++){
				System.out.print("[" + field[i][j] + "]");
			}
			System.out.print(" }");
			
		}
	}
	
	
	public int contains(Position p) {
		return field[p.getOriginX()][p.getOriginY()];
	}
	
}
