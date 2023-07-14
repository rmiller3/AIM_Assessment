public class FloorButtonStateKey {
    private int floor = -1;
    private boolean upDirection = true;

    public FloorButtonStateKey(int floor, boolean upDirection) {
        this.floor = floor;
        this.upDirection = upDirection;
    }

    public int getFloor() { return floor; }
    public boolean getUpDirection() { return upDirection; }
}
