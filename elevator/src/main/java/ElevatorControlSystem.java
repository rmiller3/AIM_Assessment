import java.util.List;
import java.util.Queue;

public interface ElevatorControlSystem {

    /**
     * CallElevator: This requests an elevator to a given floor with the desire to go up or down
     *
     * @param floor Floor that made the call (the floor the elevator should go to)
     * @param up    The user desires the elevator to go up when it reaches this floor if true.  Else, it should go down.
     */
    void callElevator(int floor, boolean up);

    /**
     * Adds the given floor to the given elevator's destinations.
     *
     * @param elevator The index of the elevator to add the destination to
     * @param floor    The floor the user wants to go to (the destination floor number).
     */
    void addDestination(int elevator, int floor);

    /**
     * An elevator car requests all floors that it’s current passengers are servicing (e.g. to light up the buttons
     * that show which floors the car needs to stop at)
     *
     * @param elevator The index of the elevator to query
     * @return A list of floor numbers the elevator needs to stop at.
     */
    List<Integer> requestDestinations(int elevator);

    /**
     * Returns the next floor number the given elevator will stop at or -1 if there aren't any more destinations.
     *
     * @param elevator The index of the elevator to query
     * @return The floor number the elevator will stop at.  Returns -1 if the elevator has no active destinations and
     * there are no users in the queue from CallElevator requests
     */
    int getNextDestination(int elevator);

    /**
     * Return the max floor with an active request to go down.
     *
     * @return the max floor or -1 if no active requests
     */
    int getMaxFloorNeedingToGoDown();

    /**
     * Return the min floor with an active request to go up.
     *
     * @return the min floor or -1 if no active requests
     */
    int getMinFloorNeedingToGoUp();

    /**
     * Gets the active floor requests in the order the request was made (FIFO)
     * @return the ordered list
     */
    Queue<FloorButtonStateKey> getActiveFloorRequests();
}