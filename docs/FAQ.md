1.  Elevator Counts are Undefined:  

The problem statement is slightly ambiguous in that we don't know the number of elevators in the system.  In order to make the problem more challenging, the thought is that we'd have to build a system that supports multiple elevators.  This is supported in the following phrase from the problem statement:

```A person requests an elevator be sent to their current floor```

PM was not directly asked this question since the wording implies that mulitple must be supported.  The key is that they used the phrase "an elevator" rather than "the elevator".  

2.  Elevator "Areas" are Undefined:

You can imagine a large building with multiple 'wings'.  Each 'wing' might have a separate elevator system, with separate request buttons that only summon an elevator for that area.  For example, pressing the button in the East Wing should not summon an elevator to that floor in the West Wing.  

PM was not directly asked this question since the design proposal handles this.  In this case we would have a separate ElevatorControlSystem for each wing.  

3.  What happens when a user enters an elevator going up, then boards the elevator and presses a floor below them.

PM was not directly asked this question since this is an implementation detail that doesn't impact the API.  For now this is not considered an error.

4.  When is the floor request button reset?  

For example, a person on the 4th floor requests to go up.  He enters the elevator, but has not yet pressed the destination button within the elevator.  The elevator doors close or start closing.  As that happens, another user approaches the 4th floor wanting to go up.

PM was not directly asked this question since it doesn't directly affect the API.  A reasonable assumption might be, that the button is unlit when the doors fully close.

5.  Do we need to support express elevators?

For the purposes of this design, I assumed no.  An example of an express elevator is a system that would have different elevators to go to the top of the building vs the middle of a building to improve the response time of those travelling to the top floors.

6.  Do we need to support capacity / weight restrictions on elevators?

For the purposes of this design, I assumed no.  An example minor optimization would be an elevator going up skipping a floor request button if it's occupancy limit was reached.

7.  Next Steps?

a.  Write the methods to simulate the elevator visiting a floor and removing users from the queue.
b.  The control system should assign an elevator to a given user / floor.  The current PR will assign the next elevator calling getNextDestination() to the next floor and not remove the pickup from the queue, so a race condition would develop.  The next iteration should assign the floor to a car and remove the floor entry from the queue (handling concurrent request)