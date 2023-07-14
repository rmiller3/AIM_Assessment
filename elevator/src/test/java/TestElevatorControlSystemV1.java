import org.junit.Assert;
import org.junit.Test;

public class TestElevatorControlSystemV1 {
    ElevatorControlSystemV1 ecs;

    public TestElevatorControlSystemV1() {
        ecs = new ElevatorControlSystemV1(5, 50);
    }



    @Test
    public void GetNextDestination_TestWhenNoUsersAreOnBoard_GoingUp() {
        ecs.callElevator(4, true);
        ecs.callElevator(7, true);
        ecs.getElevators().get(0).setGoingUp(true);
        ecs.getElevators().get(1).setGoingUp(false);
        ecs.getElevators().get(2).setGoingUp(true);
        ecs.getElevators().get(3).setGoingUp(false);

        // 4 is the next floor going up for elevators 0 qnd 2.  1 and 3 are going down
        // so they dn't have a destination.
        Assert.assertEquals(4, ecs.getElevators().get(0).getNextDestination());
        Assert.assertEquals(-1, ecs.getElevators().get(1).getNextDestination());
        Assert.assertEquals(4, ecs.getElevators().get(2).getNextDestination());
        Assert.assertEquals(-1, ecs.getElevators().get(3).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenNoLobbyButtonsArePressed_GoingUpAndDown() {
        ecs.addDestination(0, 4);
        ecs.addDestination(0, 7);
        ecs.addDestination(0, 9);
        ecs.addDestination(1, 9);
        ecs.getElevators().get(0).setGoingUp(true);
        ecs.getElevators().get(0).setCurrentFloor(5);
        ecs.getElevators().get(1).setGoingUp(false);
        ecs.getElevators().get(1).setCurrentFloor(5);

        // 7 is the next floor above 5 going up
        Assert.assertEquals(7, ecs.getElevators().get(0).getNextDestination());

        // elevator 1 is going down, so having floor 9 pressed doesn't change it.
        Assert.assertEquals(-1, ecs.getElevators().get(1).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenBothLobbyButtonsArePressedAndPassengersPressedInternalButtons() {
        ecs.addDestination(0, 4);
        ecs.addDestination(0, 9);
        ecs.addDestination(1, 6);  // wrong elevator
        ecs.getElevators().get(0).setGoingUp(true);
        ecs.getElevators().get(0).setCurrentFloor(5);
        ecs.callElevator(6, false); // ignored since the elevator is going up
        ecs.callElevator(8, true);  // This is the next floor.

        // 7 is the next floor above 5 going up
        Assert.assertEquals(8, ecs.getElevators().get(0).getNextDestination());

        ecs.addDestination(0, 7);  // THis is lower and would be the next one

        // elevator 1 is going down, so having floor 9 pressed doesn't change it.
        Assert.assertEquals(7, ecs.getElevators().get(0).getNextDestination());
    }
}
