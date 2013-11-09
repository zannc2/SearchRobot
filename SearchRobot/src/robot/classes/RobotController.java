package robot.classes;

import java.awt.Color;

import helper.Position;
import helper.Size;
import helper.Vector;
import frontend.classes.items.Line;
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
	
	public RobotController(View view) {
		this.view = view;
		this.field = view.getField();
	}
	
	public void startRobotSearch(Size fieldSize, Position p, Size roboterSize, Vector direction) {
		this.fieldSize = fieldSize;
		this.robotPosition = p;
		this.direction = direction;
		
		this.eyePosition = new Position(this.robotPosition.getOriginX() + roboterSize.getWidth(),
				this.robotPosition.getOriginY() + roboterSize.getHeight()/2);
		
		scanField();
	}
	
	private void scanField() {
		System.out.println("scan Field");
		Item eye = new Pixel(this.eyePosition, Color.yellow);
		field.addItem(eye);
		
		//Feld grösse definieren (schritte)
		int epsilon = 10;
		Position lastP = this.eyePosition;
		// degrees Between 0 and 180
		for(double i = -90; i <= 90; i=i+0.1) {
			System.out.println("angle: " + i);
			boolean whileB = true;
			while(whileB){
				int x = (int) (Math.cos(Math.toRadians(i)) *epsilon);
				int y = (int) (Math.sin(Math.toRadians(i)) * epsilon); 
				
				Position nextP = new Position(lastP.getOriginX() + x, lastP.getOriginY() + y);
//				System.out.println("nextP: " + nextP);
				if(nextP.getOriginX() > this.fieldSize.getWidth() || 
					nextP.getOriginY() > this.fieldSize.getHeight() || 
					nextP.getOriginX() < 0 || 
					nextP.getOriginY() < 0) {
						lastP = this.eyePosition;
						break;
				}
				boolean found = field.contains(nextP, epsilon);
				lastP = nextP;
				if(found) {
					System.out.println("found: " + lastP);
					Item foundI = new Pixel(lastP, Color.green);
					field.addItem(foundI);
					lastP = this.eyePosition;
					whileB = false;
				}
				Item notfoundI = new Pixel(lastP, Color.red);
				field.addItem(notfoundI);
			}
		}
		System.out.println("done");
		
	}

}
