import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public class ElevatorControlServiceEndpoints {

    // TODO:  Update this to use dependency injection.  See documentation
    // for notes on this class, which isn't quite working due to issues with Jersey.
    ElevatorControlSystem elevatorControlSystem;

    public ElevatorControlServiceEndpoints(ElevatorControlSystem elevatorControlSystem) {
        this.elevatorControlSystem = elevatorControlSystem;
    }

    @Path ("callElevator")
    @POST
    void callElevator(int floor, boolean up) {
        this.elevatorControlSystem.callElevator(floor, up);
    }


    /**
     * Adds the given floor to the given elevator's destinations.
     *
     * @param elevator The index of the elevator to add the destination to
     * @param floor    The floor the user wants to go to (the destination floor number).
     */
    @Path("addDestination")
    @POST
    void addDestination(int elevator, int floor) {
        this.elevatorControlSystem.addDestination(elevator, floor);
    }

    /**
     * An elevator car requests all floors that it’s current passengers are servicing (e.g. to light up the buttons
     * that show which floors the car needs to stop at)
     *
     * @param elevator The index of the elevator to query
     * @return A list of floor numbers the elevator needs to stop at.
     */
    @Path ("requestDestionations")
    @GET
    // Todo:  Make this return XML
    List<Integer> requestDestinations(int elevator) {
        return elevatorControlSystem.requestDestinations(elevator);
    }

    /**
     * Returns the next floor number the given elevator will stop at or -1 if there aren't any more destinations.
     *
     * @param elevator The index of the elevator to query
     * @return The floor number the elevator will stop at.  Returns -1 if the elevator has no active destinations and
     * there are no users in the queue from CallElevator requests
     */
    @Path ("getNextDestination")
    @GET
    int getNextDestination(int elevator) {
        return getNextDestination(elevator);
    }
}
