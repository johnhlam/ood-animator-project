CS 3500 - Object Oriented Design - Summer 1 2019

John Lam and David Zhang

We decided to implement our animator project using a variety different classes and interfaces, using the Model-View-Controller pattern.

## The Model

### IModel
The IModel interface is used to represent the logic for animating different shapes. It guarantees that any implementing classes will be able to provide the following functions:
1. Returning a String representation of the animation
2. Adding motions via its parameters (as long as the motions don't conflict with each other - specific details below)
3. Adding shapes via its parameters (as long as no shape with the same id exists)
4. Removing a shape from the model given its id
5. Returning a new *read-only* list of the model's updated shapes by giving it an non-negative integer tick value
6. Removing a motion given the shape's id and the tick that the motion starts at
7. Returning a list of motions that are occurring at a specified tick
8. Returning the starting x-value, starting y-value, width, height, maximum x-value, and maximum y-value of the animation
9. Returning a new *read-only* list of the model's shapes (with each shape's initial state and list of motions)

### IModelImpl and IModelImpl.Builder
The IModelImpl class implements the IModel interface described above. It stores information about the model's minimum x/y, its maximum x/y values, and the canvas' width and height. It also stores a list of shapes that represents what is currently being drawn/animated. Each individual shape will contain the information required for it to be animated, while the model class itself just contains the shapes with its state as what it was initialized as. All methods that the IModel interface requires are fully implemented. In order to return a read-only list of the model's updated shapes at a given tick, each shape in the list is responsible for returning a read-only version of itself at that tick (via delegation). Consequently, the controller and/or view receive a new list of read-only shapes for every tick they query for.

This class also contains a nested static builder class (accessible by calling the builder() method in the enclosing class), that implements the given AnimationBuilder interface (given by the assignment). The nested class stores the same fields as the enclosing class. It implements most of the methods required by the AnimationBuilder interface. The only one it doesn't implement properly (it just returns the builder without changing it) is the addKeyFrame method, because we were told by professors and TAs that we could leave a stub there. Upon calling build(), all of the fields in the builder class are used to create an instance of IModelImpl. The nested class is used in conjunction with the AnimationReader (given by the assignment) to build models from text files.

### IReadOnlyShape
The IReadOnlyShape interface represents a shape for the model, but only provides methods that can read from the shape. It provides getter methods that return the values that the shape holds, as well as a method to print a String representation of the shape. It provides no other methods than the aforementioned methods, and provides no means to mutate the shape. This read-only shape is passed into the controller and the view, so that they can read information from this shape, but not write any information to it.

### IModelShape
The IModelShape interface also represents a shape for the model. It extends the IReadOnlyShape interface, meaning that it will provide all the functionalities required to read from a shape, but in addition to those, also provides methods that get the state of the shape at a given tick, add motions to this shape, and remove motions from this shape (given a start tick). The model itself will interact with IModelShapes, and be able get shapes at a certain tick, and add/remove motions, while the controller and view are only be able to read from the same shapes as IReadOnlyShapes.

### IModelShapeImpl and ShapeType
The IModelShapeImpl class implements the IModelShape interface above, and its purpose is to store data about the shape (and its characteristics). It implements all methods that IModelShape requires. All of its fields are final, and *cannot be mutated* (as in cannot be set to different values/references).

This class has a field of type ShapeType, which is an enum that represents what type of shape this instance of the class represents. At the moment, there are only two choices: Rectangle and Ellipse, but more types of shapes can easily be added to ShapeType. The ShapeType in a given shape will determine how that shape will be depicted on in the view. ShapeType also offers a public method that returns a string representation of itself ("rectangle" for RECTANGLE, and "ellipse" for ELLIPSE), and a public method that returns a ShapeType given a String (the String has to be one of "rectangle", or "ellipse", or else an exception is thrown).

This class also has contains a list of motions (motions specified below), which represent the states of this shape at any two given "start" and "end" ticks. It assumes and enforces the following invariants about the list:
1. This list will always be sorted by tick (i.e. in chronological order) by inserting incoming motions in order.
2. Any two adjacent motions (defined as two motions in the same list/same shape that have the same ending and starting tick values, with respect to their positions in the list) will have the same ending and starting states, with respect to their positions in the list. In other words, the motion that comes first in the list should have the same ending state as the starting state of the motion that comes *immediately after* it in the list, and vice versa. 
3. There are no overlapping intervals between any two motions in this list. In the future, if it is the case that motions can overlap as long as they perform different actions (e.g. x-y movement vs color changing), we will enforce those rules, but for now, we disallow any overlapping motions based on tick. 
4. There are no gaps in the list, meaning that the previous motion's end tick must the same as the following motion's start tick (and vice versa). The list of motions does not necessarily have to begin at tick 0. Details of how the list of motions affects tweening are described below.

In order to calculate the shape's state at a given tick, it uses the tweening function given by the assignment. If the list of motions is empty, then the shape will take the state of its fields (as in whatever it's other fields have been initialized as). If the given tick is before the first motion's start tick, then the state of the shape at the start tick of the first motion is returned. If the given tick is after the end tick of the last motion in the list, then the state of the shape at the end tick of the last motion is returned (freezing it on the screen after all motions have been completed). 

Since details for birth and death of shapes have not been specified, we have left it so that a shape is always on the screen until removed from the model's list.

### IMotion
The IMotion interface provides various getter methods that retrieve information from this motion, and also provides a method that returns a string representation of this motion. An implementation of this interface represents the start state and end state of an animation at an arbitrary start and end tick (although the end tick cannot be before the start tick).

### IMotionImpl
The IMotionImpl class implements the IMotion interface above, and implements all of its methods. It holds the start and end state of shapes through individual fields. It does not hold a shape itself as we thought that Shapes should have knowledge of motions, but not the other way around. All of the fields in the IMotionImpl class are final, and thus *cannot be mutated*.

## The Controller

### IController
The IController interface is used to represent a controller that mediates operations between a model and a view. Neither the model nor the view directly communicate with each other- all communication occurs through the controller. This interface provides two methods. The first method sets/adjusts the tick rate for the controller, and was created in anticipation for a future assignment. If an implementing class does not support tick rates, it can throw an exception. The other method in this interface runs the program. Generally, this means retrieving information from the model, and passing it into the view so that some type of output can be produced.

### TextControllerImpl
The TextControllerImpl class implements the IController interface described above. It stores exactly one IView and one IModel, both of which are final and cannot be changed. It does not support a tick rate, so it throws an exception if the setTickRate method is called. When the run method is called, this controller takes the model's list of shapes and passes it into the view, where some type of text will be outputed (such as via a file, or in a terminal).

### TimerControllerImpl
The TimerControllerImpl class implements the IController interface as well as the ActionListener interface. Like the TextControllerImpl, it stores exactly one IView and one IModel, which cannot be changed. Unlike the TextControllerImpl, it also stores a timer, a tick rate (in ticks/second), and a current tick. The timer is initialized with the tick rate (converted to milliseconds/tick), and this controller itself as the action listener. This class supports being able to change the tick rate, and if the tick rate is changed, the timer is adjusted accordingly. When the run method is called, the timer starts, and the controller begins relaying information between the model and view so that the view can draw an new image at every tick.

We choose to create two separate controller classes because we felt that the differences between the visual view and the text-based views (text and svg) were too great to create a controller that could run both types of views effectively. The greatest distinction between the visual and text-based views were that the visual view required a timer that would allow it to be redrawn at every tick, while the text views did not. The text-based views needed to be played only once to generate their respective outputs, while the visual view needed to be played continuously to generate a smooth animation. Since the views were so different, we created a TextControllerImpl that would only be run once, and a TimerControllerImpl that would be run continuously once the timer started. 

## The View

### IView
### ATextualView
### TextView
### SVGView
### VisualView
### IAnimationPanel
### AnimationPanel

## The Excellence (main) class


### Misc.
More minute details of the implementation (rules for constructing a shape or model, when smaller exceptions are thrown, how processes are coded, etc) are documented appropriately in the actual code.
