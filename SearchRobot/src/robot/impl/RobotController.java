package robot.impl;

import frontend.impl.SearchRobotEditor;
import frontend.impl.view.Field;
import helper.Direction;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import robot.impl.strategies.DefaultStrategy;
import robot.interfaces.Strategy;

/**
 * This is the controller class for the robot search. It implements the
 * runnable interface and search started by a thread. The run
 * method repeats the 3 steps "scanning of the field", "calculate the next path" and "move
 * over the next path" until the robot reaches the finish, the user cancels the
 * search or the finish is fully encircled by items and nor reachable.
 * 
 * @author zannc2 & gfels4
 *
 */
public class RobotController implements Runnable {
	private final int MOVE_SPEED;
	private final Position startPosition;
	//size of steps
	private final int EPSILON = 5;
	// field values
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
	private Strategy strategy;
	private SearchRobotEditor editor;
	
	/**
	 * It will be true if the last calculation of the path goes to the finish 
	 * 
	 * @return true if the robot is finished
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Setter for the isFinished variable, which shows if the path to the finish is found
	 * 
	 * @param isFinished true if the way to the finish is found
	 */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * Constructor sets the editor, the field and the robot speed and creates a matrix filled
	 * with the items on the field and a second empty matrix which is the robots brain. The 
	 * second matrix is filled while the search, because the robot knows nothing about the field
	 * and the obstacles on it at the beginning.
	 * For more informations about the matrix see {@link FieldMatrix}
	 * 
	 * 
	 * @param editor The actual editor
	 * @param f	The field
	 * @param robotSpeed The speed of the robot
	 */
	public RobotController(SearchRobotEditor editor, Field f, int robotSpeed) {
		this.field = f;
		this.fieldSize = f.getFieldSize();
		this.robotSize = f.getRobotSize();
		this.startPosition = f.getRobotPosition();
		this.fieldCopy = new FieldMatrix(this.fieldSize, this.robotSize, f);
		this.foundMatrix = new FieldMatrix(this.fieldSize, this.robotSize);
		this.MOVE_SPEED = robotSpeed;
		this.editor = editor;
		
		setDetaultStrategie();
	}

	/**
	 * Starts the run method
	 */
	public synchronized void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Stops the run method
	 */
	public synchronized void stop(){
		if (thread != null)
			thread = null;	
	}

	/**
	 * Scanning of +/- 90° from the robot position in the direction that he looks
	 * 
	 */
	private void scanField() {
		this.foundMatrix.set(new Position(this.field.getRobotPosition().getOriginX()/10, this.field.getRobotPosition().getOriginY()/10), this.FREE);
		if(editor.isShowGrid())
			field.addItem(new Pixel(new Position(this.field.getRobotPosition().getOriginX(), this.field.getRobotPosition().getOriginY()), Color.green));
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
		for(double i = minDegree; i <= maxDegree; i=i+1) {
			// if the thread is stopped, cancel the scan
			if(thread == null) return;
			boolean whileB = true;
			int factor = 1;
			
			// calculate sinus & cosinus
			double x = Math.cos(Math.toRadians(i)) * this.EPSILON;
			double y = Math.sin(Math.toRadians(i)) * this.EPSILON;

			while(whileB){
				// if the thread is stopped, cancel the scan
				if(thread == null) return;
				
				// Calculate the next position to check
				Position p = new Position((int)(eyePosition.getOriginX() + (factor * x)), (int)(eyePosition.getOriginY() + (factor * y)));
				
				// if position is outside of the field
				if(p.getOriginX() >= this.fieldSize.getWidth() || 
						p.getOriginY() >= this.fieldSize.getHeight() || 
						p.getOriginX() < 0 || 
						p.getOriginY() < 0) {
					break;
				}
				else
				{ // position is inside the field
					
					Position pixelP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);

					//check if not already checked
					int foundMatrixFound = this.foundMatrix.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
					if(foundMatrixFound == this.ITEM || foundMatrixFound == this.FINISH) whileB = false;
					if(foundMatrixFound == 0){					
						int found = this.fieldCopy.contains(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10));
						if(found == this.ITEM) { // 1 = Item
							if(editor.isShowGrid()) field.addItem(new Pixel(pixelP, Color.RED));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position((pixelP.getOriginX())/10, (pixelP.getOriginY())/10), this.ITEM);
						}
						else if(found == this.FINISH){ // 2 = Finish
							if(editor.isShowGrid()) field.addItem(new Pixel(pixelP, Color.yellow));
							whileB = false;
							//fill foundMatrix
							this.foundMatrix.set(new Position(pixelP.getOriginX()/10, pixelP.getOriginY()/10), this.FINISH);
							foundFinish = true;
							finish = new Position(pixelP.getOriginX(), pixelP.getOriginY());
						}
						else { // If the position is free
							if(editor.isShowGrid()) field.addItem(new Pixel(pixelP, Color.green));
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
		long startTime = System.currentTimeMillis();

		// start search Algorithmus
		while(thread != null)
		{
			//first scan - Look in all directions
			this.field.setRobotDirection(Direction.NORTH);
			scanField();
			this.field.setRobotDirection(Direction.EAST);
			scanField();
			this.field.setRobotDirection(Direction.SOUTH);
			scanField();
			this.field.setRobotDirection(Direction.WEST);
			scanField();
			
			
			List<Position> movePath;
			// if the finish position is unknown
			if(!foundFinish)
			{
				movePath = this.strategy.computePath();
			}
			else // if the finish position is known
			{
				movePath = this.strategy.computePathToFinish();
			}
			
			//move to new Position with given movePath
			move(movePath);

			// if the finish is not reachable, show a message and cancel the search
			if(isUnreachable()) 
			{
				JOptionPane.showMessageDialog(null, "Das Ziel kann leider nicht angesteuert werden!");
				editor.stopSearch();
			}
			// if the robot is in the finish show a success message and cancel the search
			else if (isFinished() && thread != null)
			{
				long endTime = System.currentTimeMillis();
				float time = ((float)(endTime-startTime)/1000f);
				JOptionPane.showMessageDialog(null, "Der Roboter hat das Ziel nach " + time + " Sekunden erreicht!");
				editor.stopSearch();
			}
		}
		
		// set the robot back to the start position
		this.field.setRobotPosition(startPosition);
		
	}
	
	/**
	 * define the Default Strategie
	 */
	private void setDetaultStrategie(){
		this.strategy = new DefaultStrategy(this, this.foundMatrix, this.fieldSize, this.field);
	}

	/**
	 * This is the method which moves the robot over the path calculated by the strategy
	 * 
	 * @param pl list with positions, calculated by the strategy
	 */
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

	/**
	 * Returns the status of the finish
	 * 
	 * @return true if the finish is unreachable
	 */
	public boolean isUnreachable() {
		return unreachable;
	}

	/**
	 * Set the status of the finish
	 * 
	 * @param unreachable set true if the finish is nor reachable
	 */
	public void setUnreachable(boolean unreachable) {
		this.unreachable = unreachable;
	}

	/**
	 * Getter of the found matrix
	 * 
	 * @return the foundmatrix
	 */
	public FieldMatrix getFoundMatrix() {
		return this.foundMatrix;
	}
}
