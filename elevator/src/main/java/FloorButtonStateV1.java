import java.util.HashMap;

public class FloorButtonStateV1 implements FloorButtonState {

    HashMap<FloorButtonStateKey, Boolean> activeRequests = new HashMap<>();

    public FloorButtonStateV1(int numFloors) {
        for (int i=0; i<numFloors; i++) {
            activeRequests.put(new FloorButtonStateKey(i+1, true), false);
            activeRequests.put(new FloorButtonStateKey(i+1, false), false);
        }
    }

    public boolean getActiveFloorRequest(int floor, boolean up) {
        return activeRequests.get(new FloorButtonStateKey(floor, up));
    }

    public void setOrClearActiveFloorRequest(int floor, boolean up, boolean on) {
        activeRequests.put(new FloorButtonStateKey(floor, up), on);
    }
}
