import org.junit.Assert;
import org.junit.Test;

public class TestElevatorControlSystemV1 {
    ElevatorControlSystemV1 ecs;

    final int MAX_FLOOR_OF_BUILDING = 50;

    final int IDX_OF_ELEVATOR_ON_1F_GOING_UP = 0;
    final int IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP = 1;
    final int IDX_OF_ELEVATOR_ON_1F_GOING_DOWN = 2;
    final int IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN = 3;

    public TestElevatorControlSystemV1() {
        ecs = new ElevatorControlSystemV1(4, 50);
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setCurrentFloor(1);
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setGoingUp(true);

        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_DOWN).setCurrentFloor(1);
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_DOWN).setGoingUp(false);

        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP).setCurrentFloor(MAX_FLOOR_OF_BUILDING);
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP).setGoingUp(true);

        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).setCurrentFloor(MAX_FLOOR_OF_BUILDING);
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP).setGoingUp(false);

    }

    @Test
    public void GetNextDestination_TestWhenNoUsersAreOnBoard_RequestsGoingUp() {
        ecs.callElevator(4, true);
        ecs.callElevator(7, true);

        // Since all elevators have no users on board, the ones going up will go to the top and pick
        // up the top floor making a request to go down.  Since there isn't one, they'll take the
        // bottom floor going up.  The ones going down will find the bottom floor needing a ride up
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_DOWN).getNextDestination());
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP).getNextDestination());
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenNoUsersAreOnBoard_RequestsGoingDown() {
        ecs.callElevator(4, false);
        ecs.callElevator(7, false);

        // Since all elevators have no users on board, the ones going down will go to the bottom and pick
        // up the bottom floor making a request to go up.  Since there isn't one, they'll take the
        // top floor going down.  The ones going up will find the bottom floor needing a ride up
        Assert.assertEquals(7, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());
        Assert.assertEquals(7, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_DOWN).getNextDestination());
        Assert.assertEquals(7, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_UP).getNextDestination());
        Assert.assertEquals(7, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenNoUsersAreOnBoard_RequestsGoingUpAndDown() {
        ecs.callElevator(4, false);
        ecs.callElevator(7, false);
        ecs.callElevator(2, true);
        ecs.callElevator(8, true);

        // Elevator going up will get the next user in the floor queue from its current position going up
        ecs.getElevators().get(0).setGoingUp(true);
        ecs.getElevators().get(0).setCurrentFloor(1);
        Assert.assertEquals(2, ecs.getElevators().get(0).getNextDestination());
        ecs.getElevators().get(0).setGoingUp(true);
        ecs.getElevators().get(0).setCurrentFloor(3);
        Assert.assertEquals(8, ecs.getElevators().get(0).getNextDestination());

        // Elevator going down will get the next user in the floor queue from its current position going down
        ecs.getElevators().get(0).setGoingUp(false);
        ecs.getElevators().get(0).setCurrentFloor(MAX_FLOOR_OF_BUILDING);
        Assert.assertEquals(7, ecs.getElevators().get(0).getNextDestination());
        ecs.getElevators().get(0).setGoingUp(false);
        ecs.getElevators().get(0).setCurrentFloor(6);
        Assert.assertEquals(4, ecs.getElevators().get(0).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenUsersAreOnBoard_NoLobbyButtonsPressed_ElevatorPicksUpNextUserInSameDirection() {
        ecs.addDestination(0, 4);
        ecs.addDestination(0, 7);
        ecs.addDestination(0, 9);
        ecs.getElevators().get(0).setCurrentFloor(1);
        ecs.getElevators().get(0).setGoingUp(true);

        // first floor going up
        Assert.assertEquals(4, ecs.getElevators().get(0).getNextDestination());

        // If the up elevator started higher, stop at the next destination going up
        ecs.getElevators().get(0).setCurrentFloor(5);
        Assert.assertEquals(7, ecs.getElevators().get(0).getNextDestination());

        // Top floor elevator going down drops off top user
        ecs.getElevators().get(0).setGoingUp(false);
        ecs.getElevators().get(0).setCurrentFloor(MAX_FLOOR_OF_BUILDING);
        Assert.assertEquals(9, ecs.getElevators().get(0).getNextDestination());

        ecs.getElevators().get(0).setCurrentFloor(8);
        Assert.assertEquals(7, ecs.getElevators().get(0).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenNoUsersOnBoardAndNoLobbyButtonsPressed_ReturnsMinusOne() {
        ecs.addDestination(0, 4);

        // Elevator 0 has a user, but elevator 1 doesn't, so return -1
        Assert.assertEquals(-1, ecs.getElevators().get(1).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenBothLobbyButtonsArePressedAndPassengersPressedInternalButtons_GoingUpFromBottom() {
        ecs.addDestination(IDX_OF_ELEVATOR_ON_1F_GOING_UP, 4);
        ecs.addDestination(IDX_OF_ELEVATOR_ON_1F_GOING_UP, 9);
        ecs.callElevator(3, true);
        ecs.callElevator(10, true);
        ecs.callElevator(2, false);
        ecs.callElevator(11, false);

        // Next floor going up from either destinations or calls going up
        Assert.assertEquals(3, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());

        // Next floor going up from either destinations or calls going up after 3:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setCurrentFloor(3);
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());

        // Next floor going up from either destinations or calls going up after 4:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setCurrentFloor(4);
        Assert.assertEquals(9, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());

        // Next floor going up from either destinations or calls going up after 9:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setCurrentFloor(9);
        Assert.assertEquals(10, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());

        // Next floor going up from either destinations or calls going up after 10:
        // At this point it reached the top relevant need so it starts going back down.
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).setCurrentFloor(10);
        Assert.assertEquals(11, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_1F_GOING_UP).getNextDestination());
    }

    @Test
    public void GetNextDestination_TestWhenBothLobbyButtonsArePressedAndPassengersPressedInternalButtons_GoingDownFromTop() {
        ecs.addDestination(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN, 4);
        ecs.addDestination(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN, 9);
        ecs.callElevator(3, true);
        ecs.callElevator(10, true);
        ecs.callElevator(2, false);
        ecs.callElevator(11, false);

        // Next floor going up from either destinations or calls going up
        Assert.assertEquals(11, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());

        // Next floor going up from either destinations or calls going up after 11:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).setCurrentFloor(11);
        Assert.assertEquals(9, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());

        // Next floor going up from either destinations or calls going up after 9:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).setCurrentFloor(9);
        Assert.assertEquals(4, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());

        // Next floor going up from either destinations or calls going up after 4:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).setCurrentFloor(4);
        Assert.assertEquals(2, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());

        // Next floor going up from either destinations or calls going up after 2:
        ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).setCurrentFloor(2);
        Assert.assertEquals(3, ecs.getElevators().get(IDX_OF_ELEVATOR_ON_MAX_FLOOR_GOING_DOWN).getNextDestination());
    }
}
