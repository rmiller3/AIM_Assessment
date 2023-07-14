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
     * floor to stop at
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
     * Returns the floor of the next destination of the elevator.
     * @return Returns the floor of the next destination of the elevator.
     */
    int getNextDestination();
}
