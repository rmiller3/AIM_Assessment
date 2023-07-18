import java.util.List;

public interface Elevator {
    /**
     * Returns the current floor the elevator is on.  If it's between floors, return the nearest floor.
     * @return  Floor number
     */
    int getCurrentFloor();

    /**
     * Returns all floors the elevator will stop at.
     * @return  Returns an ordered list of destination floors by priority.  The first item will be the next
     * floor to stop at (nearest floor assuming the elevator continues in the same direction).
     */
    List<Integer> getDestinationFloors();

    /**
     * Adds or removes a floor to the list of pending destinations
     * @param floor the floor to add or remove
     * @param add True if we're going to add, false if we're going to remove
     * @return  True if the state changed, false if the state was already as desired
     */
    boolean addOrRemoveDestinationFloors(int floor, boolean add);

    /**
     * Returns if the elevator is going up or down
     * @return True if up, False if down.
     */
    boolean goingUp();

    /**
     * Returns the floor of the next destination of the elevator.  If the elevator is going up, this would be the lowest
     * of the next floor higher than the current floor in the destination queue or the floor request button list for
     * floors going up.  That is to say, the elevator would continue going in the same direction and either stop
     * to pick up a user going in the same direction or drop off a user from it's destination queue.
     * @return Returns the floor of the next destination of the elevator.
     */
    int getNextDestination();
}
