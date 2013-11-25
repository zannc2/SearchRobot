package robot.classes;

import frontend.classes.SearchRobotEditor;
import frontend.classes.view.Field;
import helper.Direction;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import robot.classes.strategies.Strategy_G;

public class RobotController implements Runnable {
	private final int MOVE_SPEED;
	private final Position startPosition;
	//size of steps
	private final int EPSILON = 5;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;
	private final int FREE = 3;

	private Field field;
	private Size fieldSize;
	private FieldMatrix fieldCopy;
	private FieldMatrix foundMatrix;
	private Size robotSize;
	private Thread thread = null;
	private boolean foundFinish;
	private Position finish;
	private boolean unreachable, isFinished;
	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	private SearchRobotEditor editor;


	public RobotController(SearchRobotEditor editor, Field f, int robotSpeed) {
		this.field = f;
		this.fieldSize = f.getFieldSize();
		this.robotSize = f.getRobotSize();
		this.startPosition = f.getRobotPosition();
		this.fieldCopy = new FieldMatrix(this.fieldSize, this.robotSize, f);
		this.foundMatrix = new FieldMatrix(this.fieldSize, this.robotSize);
		this.MOVE_SPEED = robotSpeed;
		this.editor = editor;
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
		this.foundMatrix.set(new Position(this.field.getRobotPosition().getOriginX()/10, this.field.getRobotPosition().getOriginY()/10), this.FREE);
//		field.addItem(new Pixel(new Position(this.field.getRobotPosition().getOriginX(), this.field.getRobotPosition().getOriginY()), Color.green));
		// The position of the robot eye(s)
		Position eyePosition;
		// direction of the robot
		Direction d = field.getRobotDirection();
		// max and min angle of the robots view
		double minDegree, maxDegree;
		Position robotPosition = this.field.getRobotPosition();
		if(d == Direction.NORTH)
		{
			eyePosition = new Position(robotPosition.getOriginX() + robotSize.getWidth()/2,
					robotPosition.getOriginY()-1);
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
			eyePosition = new Position(robotPosition.getOriginX()-1,
					robotPosition.getOriginY() + robotSize.getHeight()/2);
			minDegree = 90;
			maxDegree = 270;

		}

		// degrees Between 0 and 180
		for(double i = minDegree; i <= maxDegree; i=i+0.35) {
			if(thread == null) return;
			boolean whileB = true;
			int factor = 1;
			double x = Math.cos(Math.toRadians(i)) * this.EPSILON;
			double y = Math.sin(Math.toRadians(i)) * this.EPSILON;

			while(whileB){
				if(thread == null) return;


				Position p = new Position((int)(eyePosition.getOriginX() + (factor * x)), (int)(eyePosition.getOriginY() + (factor * y)));
				if(p.getOriginX() >= this.fieldSize.getWidth() || 
						p.getOriginY() >= this.fieldSize.getHeight() || 
						p.getOriginX() < 0 || 
						p.getOriginY() < 0) {
					break;
				}
				else{
					Position pixelP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);

					//check if not allready checked
					int foundMatrixFound = this.foundMatrix.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
					if(foundMatrixFound == this.ITEM || foundMatrixFound == this.FINISH) whileB = false;
					if(foundMatrixFound == 0){					
						int found = this.fieldCopy.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
						if(found == this.ITEM) { // 1 = Item
							field.addItem(new Pixel(pixelP, Color.RED));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position((pixelP.getOriginX())/10, (pixelP.getOriginY())/10), this.ITEM);
						}
						else if(found == this.FINISH){ // 2 = Finish
							field.addItem(new Pixel(pixelP, Color.yellow));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), this.FINISH);
							foundFinish = true;
							finish = new Position(pixelP.getOriginX(), pixelP.getOriginY());
						}
						else { // If the position is free
//							field.addItem(new Pixel(pixelP, Color.green));

							//fill foundMatrix
							this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), this.FREE);
						}
					}
				}
				factor++;
			}
		}
	}

	@Override
	public void run() {
//		long lastRound = System.currentTimeMillis();

		//first scan


		while(thread != null)
		{
			this.field.setRobotDirection(Direction.NORTH);
			scanField();
			this.field.setRobotDirection(Direction.EAST);
			scanField();
			this.field.setRobotDirection(Direction.SOUTH);
			scanField();
			this.field.setRobotDirection(Direction.WEST);
			scanField();

			//Strategy zannc2
			//			Strategy_Z strategy_z = new Strategy_Z(this, this.foundMatrix, this.fieldSize, this.field);
			//			List<Position> movePath = strategy_z.computePath();


			//Strategy gfells4
			Strategy_G strategy_g = new Strategy_G(this, this.foundMatrix, this.fieldSize, this.field);
			List<Position> movePath;
			
			if(!foundFinish)
			{
				movePath = strategy_g.computePath();
			}
			else
			{
				movePath = strategy_g.computePathToFinish();
			}
			
			//move to new Position with given movePath
			move(movePath);

//			{// calculation of time for scanning, just for testing
//				long thisRound = System.currentTimeMillis();
//				float timeSinceLastRound = ((float)(thisRound-lastRound)/1000f);
//				lastRound = thisRound;
//			}

			if(isUnreachable()) 
			{
				JOptionPane.showMessageDialog(null, "Das Ziel kann leider nicht angesteuert werden!");
				editor.stopSearch();
				System.out.println("Can not reach the Finish!");
			}
			else if (isFinished() && thread != null)
			{
				JOptionPane.showMessageDialog(null, "Der Roboter hat das Ziel erreicht!");
				editor.stopSearch();
				System.out.println("Finish!!!");
			}
		}
		this.field.setRobotPosition(startPosition);
	}

	private void move(List<Position> pl) {
		Iterator<Position> i = pl.listIterator();
		Position lastP = null;
		while(i.hasNext() && thread != null)
		{
			if(lastP == null) lastP = i.next();
			
			Position p = i.next();
			// move east
			if(p.getOriginX() > lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.EAST);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10+j+1, lastP.getOriginY()*10));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			// move west
			else if(p.getOriginX() < lastP.getOriginX())
			{
				this.field.setRobotDirection(Direction.WEST);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10-1-j, lastP.getOriginY()*10));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			//move south
			else if(p.getOriginY() > lastP.getOriginY())
			{
				this.field.setRobotDirection(Direction.SOUTH);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10, lastP.getOriginY()*10+j+1));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			else // move north
			{
				this.field.setRobotDirection(Direction.NORTH);
				for(int j = 0; j < 10; j++)
				{
					{
						this.field.setRobotPosition(new Position(lastP.getOriginX()*10, lastP.getOriginY()*10-1-j));
						try { Thread.sleep(MOVE_SPEED); } 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			lastP = p;
		}
	}
	
	public boolean isUnreachable() {
		return unreachable;
	}


	public void setUnreachable(boolean unreachable) {
		this.unreachable = unreachable;
	}
}
