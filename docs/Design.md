## Goal:  
Design an elevator system capable of supporting multiple elevators that meets the requirements in the problem statement.  Focus on the APIs.

### APIs Proposed:

#### 1.  int CallElevator(int floor, boolean up);

##### Requirement Scenario:  
A person requests an elevator be sent to their current floor
##### Description:  
This requests an elevator to a given floor with the desire to go up or down
##### Parameters:  
-  floor: the numerical floor number.
-  up:  If true, the up request button was pressed.  If false, down.

##### Returns:  
Nothing <p>
An alternative design could be to make this return either the index of the elevator that will respond to the request or that elevator object itself.  Void was chosen for maximum flexibility.  We now have the freedom to create a queue of requests and pick which elevator at a later time.  


##### Example Implementation:  
Adds the current floor to the queue.  If it's already there (enqueued), it doesn't re-add it.  A hashmap could be used to store the contents of the queue for faster lookup.  A queue would allow the system to prioritize the requests and ensure no user is waiting excessively.

#### 2.  void AddDestination(int elevator, int floor)

##### Requirement Scenario:  
A person requests that they be brought to a floor
##### Description:  
Adds the given floor to the given elevator object's destination list.

##### Parameters:
-  elevator:  The index of the elevator to add the destination to (an alternative could be to take an elevator object).
-  floor:  The floor to add to.

##### Example Implementation:  
In the design proposal below, the elevator object itself stores a queue of destinations (which the ElevatorControlSystem could query).  The thought was that this would be an API on the elevator itself.

#### 3.  List<Integer> RequestDestinations(int elevator)
##### Requirement Scenario:  
An elevator car requests all floors that it’s current passengers are servicing (e.g. to light up the buttons that show which floors the car is going to)

##### Parameters:
-  elevator:  The index of the elevator to get the list from (an alternative could be to take an elevator object).

##### Returns:  
The list of floor numbers.

#### 4.  int GetNextDestination(int elevator)

##### Requirement Scenario:  
An elevator car requests the next floor it needs to service
##### Parameters:
-  elevator.  The elevator to get the destination for

##### Returns:  The floor number of the destination.

### Detailed Design.

While our design is just for the API, it is helpful to think about an example implementation.  One such implementation could be the following:

1.  An ElevatorControlSystem class which contains:
    1.  A list of Elevator objects
    2.  A list of FloorButton objects.  Perhaps this can be modeled as two button objects per floor, one to represent the requestor wants to go up and one to represent that the person wants to go down.  
    3.  A FIFO queue of requested floors
2.  An Elevator class which contains:
    1.  The current floor
    2.  Whether it is going up or down.
    3.  A "desinationRequest" subroutine which represents a boarded passenger desiring to go to a certain destination.  
    4.  A FIFO queue of destinations.  
3.  A FloorButton class which contains:
    1.  The floor it represents
    2.  Whether it represents an 'up' request or a 'down' request.  
    3.  Whether it is pressed or not (these buttons typically remain lit until the request is serviced so we should model that state).  
    4.  A "press" subroutine which could create an event in a queuing service like Kafka

### Alternative implementions:

Replace the FloorButton class with a "Floor" class that models all requests for the Floor.  

Rather than having a separate up and down FloorButton on each floor in the ElevatorControlSystem, we have a list of single "Floor" classes.

##### Pros:  
Simplifies the ElevatorControlSystem class
##### Cons:  
Adds complexity to the new "Floor" class