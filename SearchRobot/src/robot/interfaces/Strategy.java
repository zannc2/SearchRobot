package robot.interfaces;

import helper.Position;

import java.util.List;

public interface Strategy {
	
	/**
	 * Computes the next path for the robot to find the finish
	 * 
	 * @return a list of positions, the first entry in this list is the vertex, the second entry the second vertex.... and the last
	 * 		   entry the end of this path
	 */
	public List<Position> computePath();
	
	/**
	 * If the finish position is known, this method can compute path to the finish
	 * 
	 * @return a list of positions, the first entry in this list is the first vertex, the second entry the second vertex.... and the last
	 * 		   entry the finish position
	 */
	public List<Position> computePathToFinish();

}
