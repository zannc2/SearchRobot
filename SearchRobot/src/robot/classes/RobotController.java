package robot.classes;

import helper.Direction;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Color;

import frontend.classes.items.Robot;
import frontend.classes.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.View;

public class RobotController {
	private View view;
	private Field field;
	
	private Size fieldSize;
	private Position robotPosition;
	private Vector direction;
	private Position eyePosition;
	private FieldMatrix fieldCopy;
	private FieldMatrix foundMatrix;
	private Robot robot;
	
	public RobotController(View v) {
		this.view = v;
		this.field = this.view.getField();
	}
	
	public void startRobotSearch(Size fieldSize, Position p, Size roboterSize, Vector direction, FieldMatrix fieldMatrix) {
		this.fieldSize = fieldSize;
		this.robotPosition = p;
		this.direction = direction;
		
		this.fieldCopy = fieldMatrix;
		this.foundMatrix = new FieldMatrix(fieldSize);
		
		this.eyePosition = new Position(this.robotPosition.getOriginX() + roboterSize.getWidth(),
				this.robotPosition.getOriginY() + roboterSize.getHeight()/2);
		
		scanField();
	}
	
	private void scanField() {
		System.out.println("scan Field");
//		field.addItem(eye);
		
		//Feld grösse definieren (schritte)
		int epsilon = 10;
		Position lastP = new Position(this.eyePosition.getOriginX(), this.eyePosition.getOriginY());
		System.out.println("eyePos: " + lastP);
		
		// degrees Between 0 and 180
		for(double i = -90; i <= 90; i=i+0.3) {
			boolean whileB = true;
			int factor = 1;
			double x = Math.cos(Math.toRadians(i)) * epsilon;
			double y = Math.sin(Math.toRadians(i)) * epsilon;
			
			while(whileB){
				
				
				Position p = new Position((int)(this.eyePosition.getOriginX() + (factor * x)), (int)(this.eyePosition.getOriginY() + (factor * y)));
				if(p.getOriginX() >= this.fieldSize.getWidth() || 
					p.getOriginY() >= this.fieldSize.getHeight() || 
					p.getOriginX() < 0 || 
					p.getOriginY() < 0) {
						lastP = this.eyePosition;
						break;
				}
				else{
					Position pixelP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
					if(pixelP.getOriginX() == 790)
					{
						int r = 1;
					}
					int found = this.fieldCopy.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
					if(found == 1) { // 1 = Item
						System.out.println("found: " + lastP);
//						Item foundI = new Pixel(pixelP, Color.green);
//						field.addItem(foundI);
						lastP = this.eyePosition;
						whileB = false;
						
						//fill foundMatrix
						this.foundMatrix.set(new Position((pixelP.getOriginX())/10, (pixelP.getOriginY())/10), 1);
					}
					else if(found == 2){ // 2 = Finish
						System.out.println("finish found!!!");
//						Item foundI = new Pixel(pixelP, Color.yellow);
//						field.addItem(foundI);
						whileB = false;
						
						//fill foundMatrix
						this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), 2);
					}
					else { // If the position is free
//						Item notfoundI = new Pixel(pixelP, Color.red);
//						field.addItem(notfoundI);
						
						//fill foundMatrix
						System.out.println("pixelP: " + pixelP);
						this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), 3);
					}
				}
				factor++;
			}
			
		}
		System.out.println("done");
		foundMatrix.printArray();
		
		for(int j = 0; j<80; j++) {
			for(int i = 0; i<50; i++){
				Position p = new Position(j, i);
				if(foundMatrix.contains(p) == 1){
					Item item = new Pixel(new Position(j*10, i*10), Color.red);
					field.addItem(item);
				}
				else if(foundMatrix.contains(p) == 2)
				{
					Item item = new Pixel(new Position(j*10, i*10), Color.gray);
					field.addItem(item);
				}
				else if(foundMatrix.contains(p) == 3)
				{
					Item item = new Pixel(new Position(j*10, i*10), Color.green);
					field.addItem(item);
				}
			}
			System.out.println();
		}
	}

}
