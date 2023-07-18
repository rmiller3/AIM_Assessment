public interface FloorButtonState {

    /**
     *
     * @param floor  The floor number being queried.
     * @param upDirection  True if the query is for an active up request, False for an active down request
     * @return  True if there's an active request on the floor matching the parameter conditions
     */
    boolean getActiveFloorRequest(int floor, boolean upDirection);

    /**
     * Represents a user request for an elevator via the lobby button.  The user can make a request to go up
     * or down.  This API allows for clearing a given request, which may not be used by an end user.  One could
     * imagine an elevator making the clear request after it visits a floor.
     * @param floor The floor making the request
     * @param upDirection  True if the request to set or clear is for the up, otherwise the request is for going down
     * @param on  True if an elevator needs to stop at the given floor, otherwise it will clear any existing need
     */
    void setOrClearActiveFloorRequest(int floor, boolean upDirection, boolean on);
}
