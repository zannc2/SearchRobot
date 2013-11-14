package robot.classes;

import helper.Direction;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.util.List;

import frontend.classes.items.Robot;
import frontend.classes.view.Field;
import frontend.classes.view.ViewImpl;

public class RobotController implements Runnable {
	private ViewImpl view;
	private Field field;

	private Size fieldSize;
	private Vector direction;
	private FieldMatrix fieldCopy;
	private FieldMatrix foundMatrix;
	private Robot robot;
	private Size robotSize;
	private boolean isNotFinished = true;
	private Thread thread = null;

	public RobotController(Field f) {
		this.field = f;
		this.fieldSize = f.getFieldSize();
		this.fieldCopy = new FieldMatrix(this.fieldSize, f);
		this.robotSize = f.getRobotSize();
		this.foundMatrix = new FieldMatrix(fieldSize);
	}


	public synchronized void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	public synchronized void stop(){
		if (thread != null)
			thread = null;
	}

	private void scanField() {
		// The position of the robot eye(s)
		Position eyePosition;
		// size of the steps
		int epsilon = 10;
		// direction of the robot
		Direction d = field.getRobotDirection();
		// max and min angle of the robots view
		double minDegree, maxDegree;
		Position robotPosition = this.field.getRobotPosition();
		if(d == Direction.NORTH)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth()/2,
					robotPosition.getOriginY());
			minDegree = -180;
			maxDegree = 0;
		}
		else if(d == Direction.EAST)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth(),
					robotPosition.getOriginY() + robotSize.getHeight()/2);	
			minDegree = -90;
			maxDegree = 90;
		}
		else if(d == Direction.SOUTH)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth()/2,
					robotPosition.getOriginY() + robotSize.getHeight());	
			minDegree = 0;
			maxDegree = 180;
		}
		else
		{
			eyePosition = new Position(robotPosition.getOriginX(),
					robotPosition.getOriginY() + robotSize.getHeight()/2);
			minDegree = 90;
			maxDegree = 270;

		}

		// degrees Between 0 and 180
		for(double i = minDegree; i <= maxDegree; i=i+0.35) {
			boolean whileB = true;
			int factor = 1;
			double x = Math.cos(Math.toRadians(i)) * epsilon;
			double y = Math.sin(Math.toRadians(i)) * epsilon;

			while(whileB){


				Position p = new Position((int)(eyePosition.getOriginX() + (factor * x)), (int)(eyePosition.getOriginY() + (factor * y)));
				if(p.getOriginX() >= this.fieldSize.getWidth() || 
						p.getOriginY() >= this.fieldSize.getHeight() || 
						p.getOriginX() < 0 || 
						p.getOriginY() < 0) {
					break;
				}
				else{
					Position pixelP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
					int found = this.fieldCopy.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
					if(found == 1) { // 1 = Item
						//						field.addItem(new Pixel(pixelP, Color.red));
						whileB = false;
						//fill foundMatrix
						this.foundMatrix.set(new Position((pixelP.getOriginX())/10, (pixelP.getOriginY())/10), 1);
					}
					else if(found == 2){ // 2 = Finish
						//						field.addItem(new Pixel(pixelP, Color.yellow));
						whileB = false;
						//fill foundMatrix
						this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), 2);
					}
					else { // If the position is free
						//						field.addItem(new Pixel(pixelP, Color.green));

						//fill foundMatrix
						this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), 3);
					}
				}
				//				try {
				//					Thread.sleep(1);
				//				} catch (InterruptedException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}
				factor++;
			}

		}
		System.out.println("DONE");
		//foundMatrix.printArray();
	}

	@Override
	public void run() {
		long lastRound = System.currentTimeMillis();
		while(thread != null)
		{
			scanField();
			{// calculation of time for scanning, just for testing
				long thisRound = System.currentTimeMillis();
				float timeSinceLastRound = ((float)(thisRound-lastRound)/1000f);
				lastRound = thisRound;
				System.out.println(timeSinceLastRound);
			}
			// TODO computePath(); -> nächster weg berechnen und in form einer liste alle
			//						  punkte wo der roboter durch muss
			
			// TODO move -> mit einer liste, welche alle positionen enthält von der 
			//				aktuellen position bis zur endposition

		}
	}

	private void move(List<Position> pl) {
		// TODO Will be implemented
		// bsp
		for(Position p: pl)
		{
			this.field.setRobotPosition(p);
		}
	}

	private List<Position> computePath() {
		// TODO Will be implemented
		return null;
	}
}
