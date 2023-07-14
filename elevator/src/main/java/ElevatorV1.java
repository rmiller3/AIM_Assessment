import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nullable;
import java.util.*;

public class ElevatorV1 implements Elevator {

    private boolean goingUp = false;
    private int currentFloor = 0;

    HashSet<Integer> destinationFloors = new HashSet<>();

    ElevatorControlSystem elevatorControlSystem;

    public ElevatorV1(ElevatorControlSystem elevatorControlSystem) {
        this.elevatorControlSystem = elevatorControlSystem;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Integer> getDestinationFloors() {
        return destinationFloors.stream().toList();
    }

    public boolean addOrRemoveDestinationFloors(int floor, boolean add) {
        if (add) {
            if (destinationFloors.contains(floor)) {
                return false;
            } else {
                destinationFloors.add(floor);
                return true;
            }
        } else {
            if (destinationFloors.contains(floor)) {
                destinationFloors.remove(floor);
                return true;
            } else {
                return false;
            }
        }

    }

    @VisibleForTesting // In a fully developed system, this could be a private method
    // where the elevator figures out when it's time to switch directions (i.e. it was going up
    // and hit all the destinations on the way up, and there's nothing in the floor queue above it.
    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    @VisibleForTesting // In a fully developed system, this could be a private method
    // where the elevator figures out when it's time to switch directions (i.e. it was going up
    // and hit all the destinations on the way up, and there's nothing in the floor queue above it.
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public boolean goingUp() {
        return goingUp;
    }

    /**
     * If the elevator is going up, returns the minimum floor that is greater than the current floor
     * among both:
     * 1.  The elevator's active destinationList
     * 2.  The system's queue of floor call buttons that have called an elevator (in the up direction)
     *
     * If the elevator is going down, returns the maximum floor that is greater than the current floor
     * among the same set.
     * @return The next floor
     */
    public int getNextDestination() {

        int nextFloorInDestinationList = this.getNextDestinationFloor();
        int nextFloorInFloorQueue = this.getNextFloorInFloorQueue();

        if (goingUp) {
            if (nextFloorInDestinationList == -1) {
                return nextFloorInFloorQueue;
            }
            if (nextFloorInFloorQueue == -1) {
                return nextFloorInDestinationList;
            }

            return Math.min(nextFloorInDestinationList, nextFloorInFloorQueue);
        }

        return Math.max(nextFloorInDestinationList, nextFloorInFloorQueue);
    }

    /**
     *  Searches the destination list for the next floor above the current floor if the elevator is going up,
     *  or the next floor below the current floor if the elevator is going down.  Returns -1 if no matches
     *
     * @return  The relevant floor or -1 if there is not destination that meets the criteria
     */
    private int getNextDestinationFloor() {

        int output = goingUp ? Integer.MAX_VALUE : -1;

        for (Integer floor : destinationFloors) {
            if (goingUp) {
                if (floor > currentFloor && floor < output) {
                    output = floor;
                }
            } else { // going down
                if (floor < currentFloor && floor > output) {
                    output = floor;
                }
            }
        }

        if (output == Integer.MAX_VALUE) {
            return -1;
        }
        return output;
    }

    /**
     * Searches the floor queue to find the next floor in the elevator's direction relative
     * to the current floor.
     *
     * Note that this searches the system queue of floors where
     * a call button was pressed, NOT a given elevator's destination list, where passengers boarded the
     * elevator and pressed a button.
     *
     * This could therefore be a method in ElevatorControlSystem, but alternative implementations may not
     * want to support this method so it was placed in a utility class to reduce coupling.
     *
     * @return the next floor from the queue, -1 if there isn't one.
     */
    private Integer getNextFloorInFloorQueue() {

        Integer output = goingUp ? Integer.MAX_VALUE : -1;

        // Ignore users who want to go down if this elevator is going up and vice versa
        // Also ignore users below the current floor if this elevator is going up and vice versa.
        for (FloorButtonStateKey key : elevatorControlSystem.getActiveFloorRequests()) {
            if (goingUp) {
                if (key.getUpDirection() == true
                        && key.getFloor() > currentFloor
                        && key.getFloor() < output) {
                    output = key.getFloor();
                }
            } else { // going down
                output = Integer.MAX_VALUE;
                if (key.getUpDirection() == false
                        && key.getFloor() < currentFloor
                        && key.getFloor() > output) {
                    output = key.getFloor();
                }
            }
        }
        if (output == -1 || output == Integer.MAX_VALUE) {
            return -1;
        }
        return output;
    }
}