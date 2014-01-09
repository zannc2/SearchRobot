package robot.impl.strategies;

import frontend.impl.view.Field;
import helper.Position;
import helper.Size;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import robot.impl.FieldMatrix;
import robot.impl.RobotController;
import robot.interfaces.Strategy;

/**
 * Our default strategy for the robot to find a way to the finish.
 * It searches always the shortest path to the next unknown position until the finish is reached.
 * 
 * @author zannc2 & gfells4
 *
 */

public class DefaultStrategy implements Strategy{

	// current field
	private Field field;
	// field size
	private Size fieldSize;
	// field values
	private final int UNKNOWN = 0;
	private final int ITEM = 1;
	private final int FINISH = 2;
	private RobotController robotController;
	// the martix which saves the memory of the robot
	private FieldMatrix foundMatrix;
	// matrix to mark the already visited fields while the search
	private int [][] visited;
	// saves the value of the shortest way found to an unknown position
	private int shortestWay;
	// saves a list with the positions of the path to the next unknown field or the finish
	private List<Position> returnList;
	// true if there is an unknown field reachable for the robot
	private boolean unknownFieldExist;
	// true if the search found a way to the finish
	private boolean finishAcessible;

	
	/**
	 * Constructor of this Class
	 * 
	 * @param robotController The robot controller which uses this strategy
	 * @param foundMatrix	the matrix of the field, filled with the memory of the robot
	 * @param fieldSize the size of the current field
	 * @param field	the current field
	 */
	public DefaultStrategy(RobotController robotController, FieldMatrix foundMatrix, Size fieldSize, Field field) {
		this.foundMatrix = foundMatrix;
		this.fieldSize = fieldSize;
		this.field = field;
		this.robotController = robotController;
	}

	/**
	 * This strategy computes the shortest path to an unknown position in the given foundMatrix.
	 * The returned List contains all Positions step by step to the unknown field. Each position
	 * in will be a neighbor position in north, east, west or south of the next position 
	 * in the list
	 * 
	 * @return The path to the next unknown field
	 */
	@Override
	public List<Position> computePath() {
		// get the robot position
		Position robot = new Position(this.field.getRobotPosition().getOriginX()/10, 
				this.field.getRobotPosition().getOriginY()/10);
		// create array to mark the visited positions
		visited = new int[fieldSize.getWidth()/10][fieldSize.getHeight()/10];

		// set the shortest way to max possible
		shortestWay = fieldSize.getHeight()*fieldSize.getWidth();

		// create the list to return
		returnList = new LinkedList<Position>();
		
		// set to false before searching
		unknownFieldExist = false;

		// compute the path, start with the robot position
		computePath(new Position(robot.getOriginX(), robot.getOriginY()), 0);
		
		// if the method found an unknown field
		if(unknownFieldExist)
		{
			Collections.reverse(returnList);
		}
		else // if all reachable fields are discovered -> the finish is unreachable
		{
			this.robotController.setUnreachable(true);
			returnList.clear();
		}
		
		return returnList;
	}

	/**
	 * This is a recursive method to find the shortest way from the robot position to its next unknown position
	 * 
	 * @param p the position of the next position (in "pixels/10")
	 * @param depth the depth of position p in the tree
	 */
	private void computePath(Position p, int depth) {
		// if position p was already visited in a less deep recursion OR there is already a shorter way to an unknown field 
		if ((visited[p.getOriginX()][p.getOriginY()] != 0 && 
				visited[p.getOriginX()][p.getOriginY()] <= depth) || depth > shortestWay)
		{
			return;
		}

		// if the position is visited the first time or the last time was in a deeper recursion
		else
		{
			// set visited in this depth
			visited[p.getOriginX()][p.getOriginY()] = depth;
			// get the value of this position
			int fieldValue = foundMatrix.contains(p);

			// if the value of position p is UNKNOWN & the its the less deepest unknown position till now
			if(fieldValue == UNKNOWN && depth < shortestWay)
			{
				// set unknown as true, its important to find out if there is still an unknown field or not
				unknownFieldExist = true;
				// set the shortest way and create a new list from here
				shortestWay = depth;
				returnList.clear();
				return;// returnList;
			}
			// if the value of this position is unknown or its an item -> return without changes
			else if(fieldValue == UNKNOWN || fieldValue == ITEM)
			{
				return;
			}
			else // we go one step deeper
			{
				depth += 1;
				// north
				if(p.getOriginY() > 0)
					computePath(new Position(p.getOriginX(), p.getOriginY()-1), depth);
				// west
				if(p.getOriginX() > 0)
					computePath(new Position(p.getOriginX()-1, p.getOriginY()), depth);
				// south
				if(p.getOriginY() < (fieldSize.getHeight()/10)-1)
					computePath(new Position(p.getOriginX(), p.getOriginY()+1), depth);
				// east
				if(p.getOriginX() < (fieldSize.getWidth()/10)-1)
					computePath(new Position(p.getOriginX()+1, p.getOriginY()), depth);

				if(depth+returnList.size() > shortestWay)
				{
					return;				
				}
				else
				{
					returnList.add(p);
					return;
				}
			}
		}
	}

	/**
	 * If the finish position is known, it computes the shortest path to the finish in the given foundMatrix.
	 * The returned List contains all Positions step by step to the finish. Each position
	 * in will be a neighbor position in north, east, west or south of the next position 
	 * in the list
	 * 
	 * If there is no way to the finish the method will call the normal computePath() method
	 * 
	 * @return The path to the finish or if not accessible and unknown positions exists -> to the next unknown field
	 */
	@Override
	public List<Position> computePathToFinish() {
		// get the robot position
		Position robot = new Position(this.field.getRobotPosition().getOriginX()/10, 
				this.field.getRobotPosition().getOriginY()/10);
		// create array to mark the visited positions
		visited = new int[fieldSize.getWidth()/10][fieldSize.getHeight()/10];

		// set the shortest way to max possible
		shortestWay = fieldSize.getHeight()*fieldSize.getWidth();

		// create the list to return
		returnList = new LinkedList<Position>();

		// compute the path, start with the robot position
		computePathToFinish(new Position(robot.getOriginX(), robot.getOriginY()), 0);
		
		// if the method found a way to the finish
		if(finishAcessible)
		{
			Collections.reverse(returnList);
			this.robotController.setFinished(true);
		}
		else // if the finish is not accessible
		{
			returnList.clear();
			computePath();
		}
		return returnList;
	}
	
	/**
	 * This is a recursive method to find the shortest way from the robot position to the finish
	 * 
	 * @param p the position of the next position (in "pixels/10")
	 * @param depth the depth of position p in the tree
	 */
	private void computePathToFinish(Position p, int depth) {
		// if position p was already visited in a less deep recursion OR there is already a shorter way to the finish
		if ((visited[p.getOriginX()][p.getOriginY()] != 0 && 
				visited[p.getOriginX()][p.getOriginY()] <= depth) || depth > shortestWay)
		{
			return;
		}

		// if the position is visited the first time or the last time was in a deeper recursion
		else
		{
			// set visited in this depth
			visited[p.getOriginX()][p.getOriginY()] = depth;
			// get the value of this position
			int fieldValue = foundMatrix.contains(p);

			// if the value of position p is UNKNOWN & the its the less deepest unknown position till now
			if(fieldValue == FINISH && depth < shortestWay)
			{
				// set true, if a way to the finish exists
				finishAcessible = true;
				// set the shortest way and create a new list from here
				shortestWay = depth;
				returnList.clear();
				returnList.add(p);
				return;// returnList;
			}
			// if the value of this position is unknown or its an item -> return without changes
			else if(fieldValue == UNKNOWN || fieldValue == FINISH || fieldValue == ITEM)
			{
				return;
			}
			else // we go one step deeper
			{
				depth += 1;
				// north
				if(p.getOriginY() > 0)
					computePathToFinish(new Position(p.getOriginX(), p.getOriginY()-1), depth);
				// west
				if(p.getOriginX() > 0)
					computePathToFinish(new Position(p.getOriginX()-1, p.getOriginY()), depth);
				// south
				if(p.getOriginY() < (fieldSize.getHeight()/10)-1)
					computePathToFinish(new Position(p.getOriginX(), p.getOriginY()+1), depth);
				// east
				if(p.getOriginX() < (fieldSize.getWidth()/10)-1)
					computePathToFinish(new Position(p.getOriginX()+1, p.getOriginY()), depth);
				
				if(depth+returnList.size() > shortestWay+1)
				{
					return;				
				}
				else
				{
					returnList.add(p);
					return;
				}
			}
		}
	}
	
}
