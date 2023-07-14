import java.util.*;
import java.util.concurrent.BlockingQueue;

public class ElevatorControlSystemV1 implements ElevatorControlSystem {

    private FloorButtonState floorButtonState;

    // Elevator V1 is used for testing; in a fully developed system we can use dependency injection
    private ArrayList<ElevatorV1> elevators = new ArrayList();

    Queue<FloorButtonStateKey> activeFloorRequests = new LinkedList<FloorButtonStateKey>();

    public ElevatorControlSystemV1(int numElevators, int numFloors) {
        for (int i=0; i<numElevators; i++) {
            elevators.add(new ElevatorV1(this));
        }

        floorButtonState = new FloorButtonStateV1(numFloors);
    }


    public void callElevator(int floor, boolean upDirection) {
        activeFloorRequests.offer(new FloorButtonStateKey(floor, upDirection));
        floorButtonState.setOrClearActiveFloorRequest(floor, upDirection, true);
    }

    public void addDestination(int elevator, int floor) {
        elevators.get(elevator).addOrRemoveDestinationFloors(floor, true);
    }

    public List<Integer> requestDestinations(int elevator) {
        return elevators.get(elevator).getDestinationFloors();
    }

    public int getNextDestination(int elevator) {
        return elevators.get(elevator).getNextDestination();
    }

    public Queue<FloorButtonStateKey> getActiveFloorRequests() {
        return activeFloorRequests;
    }

    public List<ElevatorV1> getElevators() {
        return elevators;
    }
}
