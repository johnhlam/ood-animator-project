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

This class also has a list of motions (motions specified below), which represent the states of this shape at any two given "start" and "end" ticks. It assumes and enforces the following invariants about the list:
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
The IController interface is used to represent a controller that mediates operations between a model and a view. Neither the model nor the view directly communicates with each other- all communication occurs through the controller. This interface provides two methods. The first method sets/adjusts the tick rate for the controller, and was created in anticipation for a future assignment. If an implementing class does not support tick rates, it can throw an exception. The other method in this interface runs the program. Generally, this means retrieving information from the model, and passing it into the view so that some type of output can be produced.

### TextControllerImpl
The TextControllerImpl class implements the IController interface described above. It stores exactly one IView and one IModel, both of which are final and cannot be changed. It does not support a tick rate, so it throws an exception if the setTickRate method is called. When the run method is called, this controller takes the model's list of shapes and passes it into the view, where some type of text will be outputted (such as via a file, or in a terminal).

### TimerControllerImpl
The TimerControllerImpl class implements the IController interface as well as the ActionListener interface. Like the TextControllerImpl, it stores exactly one IView and one IModel, which cannot be changed. Unlike the TextControllerImpl, it also stores a timer, a tick rate (in ticks/second), and a current tick. The timer is initialized with the tick rate (converted to milliseconds/tick), and this controller itself as the action listener. This class supports being able to change the tick rate, and if the tick rate is changed, the timer is adjusted accordingly. When the run method is called, the timer starts, and the controller begins relaying information between the model and view so that the view can draw a new image at every tick.

We choose to create two separate controller classes because we felt that the differences between the visual view and the text-based views (text and svg) were too great to create a controller that could run both types of views effectively. The greatest distinction between the visual and text-based views were that the visual view required a timer that would allow it to be redrawn at every tick, while the text views did not. The text-based views needed to be played only once to generate their respective outputs, while the visual view needed to be played continuously to generate a smooth animation. Since the views were so different, we created a TextControllerImpl that would only be run once, and a TimerControllerImpl that would be run continuously once the timer started.

## The View

### IView
The IView interface is used to represent the "view" of an animation of shapes. Implementing classes should be able to process a given list of shapes into some kind of "view". The view does not necessarily have to represent the animation with images- it may choose to represent it using text or some other medium. It contains three methods that should be offered by implementing classes:
1. A way to adjust the canvas size (by setting it to given values),
2. A way to set the maximum window size (although not all implementations may choose to support this).
3. A way to generate some type of output. This output can vary from implementation to implementation, but can range from a visual representation, to a textual representation.

### ATextualView
ATextualView is an abstract class that implements the IView abstract class. It represents a view that outputs text in some form (be it by terminal, or by file). It stores the lowest x and y values of the canvas, and its width and height. It also stores an Appendable (which is final), to which textual output can be appended. Since it stores an Appendable, it is capable of outputting to various locations, such as a file (using some type of writer), or to the terminal (via System.out). It implements the method (from the interface) that adjusts the canvas size, as well as the method that sets the maximum window size (throwing an error in the latter case). It leaves the method for generating output to extending classes, as each class may have different formats for the text.

### TextView
TextView is a class that extends the ATextualView abstract class. It stores no extra fields, and relies on the abstract class for storing information. It implements the play(List<IReadOnlyShape>) method (the method that generates output), by using each shape's print method (delegating to each shape), formats it according to the assignment's specifications, and appends it to the Appendable in the abstract class. See the assignment or Javadocs for details on how exactly the text is formatted.  

### SVGView
SVGView is a class that extends the ATextualView abstract class. Along with the fields in the abstract class, it also stores a tick rate in ticks/second so that ticks for each animation can be converted into milliseconds, as the format requires. It implements the play(List<IReadOnlyShape>) using a few helper methods that ultimately format the text according to the [SVG documentation](https://www.w3.org/TR/SVG11/) and append it to the Appendable in the abstract class. The SVG output does not currently support loopback, so animations (and their shapes) will freeze upon completion.

### VisualView
VisualView is a class that extends the JFrame class and implements the IView interface. Unlike the text-based views, the VisualView will create a window with scroll bars that will display the animation. It stores information about the canvas' width, height, maximum x-value, maximum y-value, as well as an AnimationPanel for drawing. When the constructor for this class is called, it creates a window that will pop up on the user's screen with the specified dimensions. The frame size is specified by the input file, while the setPreferredSize method is determined by the model's information about the animations largest x and y coordinates. However, the window will remain blank (without any animations or images) until the play(List<IReadOnlyShape>) is called. When the play method is called in this class, it will pass the given list of shapes into the AnimationPanel, where the shapes will actually be rendered. The play method is expected to be called on every tick, with a slightly different list of shapes, so that output will appear to be an animation. Additionally, this class also supports both adjusting the canvas size and setting the maximum window size (as specified by the IView interface).

### IAnimationPanel
The IAnimationPanel is an interface that is used to represent the panel onto which images can be drawn. It contains one method, draw(List<IReadOnlyShape>) that renders the given list of shapes onto the panel.

### AnimationPanel
AnimationPanel is a class that extends JPanel and implements the IAnimationPanel interface. It stores a list of shapes that represents what is currently being displayed on the panel. The draw method is implemented by storing the given list of shapes in the class, and by delegating the process to the repaint method inherited from the JPanel class. It overrides the paintComponent(Graphics) method from the JPanel class so that calling the method will draw the list of shapes stored in the class onto the panel. Currently, this class only supports rendering filled rectangles, and filled ellipses.

## The Excellence (main) class
Excellence is a class that contains the main method. It serves as an entry point into the program, and takes in inputs as command line arguments. Currently, four different options are supported:
1. -in *input-file*
2. -view *view-type*, which is one of "text", "svg", or "visual"
3. -out *output-file*
4. -speed *ticks-per-second*
The first two options are mandatory, the latter two are optional. If -out isn't specified, it will automatically output to System.out (if the view requires an output location). If -speed isn't specified, then it will automatically default to 1 tick/second.

To parse the arguments, the main method uses a nested static class, ArgsProcessor. ArgsProcessor reads through the String array of arguments, parses them into data, and then stores them as static constants in the class. -in options are stored as BufferedReaders, -view options are stored as Strings, -out options are stored as Appendables, and -speed options are stored as integers. The main method can then access these static variables, and use them to read in a model (using the AnimationReader and AnimationBuilder<IModelImpl>), and create an appropriate controller and view (based on the specified view type). After building the model, view, and controller (without error), the main method will then call run() on the controller, which will run the program and generate the appropriate output.
If the given view type was either "text" or "svg", and an -out option was specified (meaning that it did not default to System.out), then the main method will close (and flush) the BufferedWriter so that any remaining data stored in the writer is written out to the appropriate file.

If, for whatever reason, an error occurs (such as invalid command-line arguments, or invalid file names), an error dialog will pop up with an informative message, and the program will terminate. See the Javadocs for details on when exactly a error dialog is created.

## Changelog
We have made a few changes since the previous assignment.
* We added methods to IModel (and IModelImpl) that can return a list of motions occurring at a specific tick, and can remove a motion given a shape's id and the start tick of that motion, since we didn't do it before and the previous assignment actually required it.
* We also added getter methods to the model that return different aspects of the canvas, since it became clear that it was required by the AnimationBuilder and some of the views.
* We added a method that returns a read-only list of the model's shapes, without a tick, so that the text-based views would be able to access them without a tick.
* We implemented a nested static Builder class in the model so that our model could be constructed with the given AnimationReader class.
* We implemented the remaining tweening methods after being given details on the tweening function, Originally, we were going to have the IMotionImpl class calculate the tweening values, but we decided to place all of the calculations in the IModelShapeImpl class, because we realized we could just use the IMotion's getters and leave the calculations in the shape. If we left the calculations in the IMotionImpl class, we would have to create several extra helpers that all do very similar things with different numbers. Leaving it in the shape class meant that we could easily abstract out the formula and calculate the in-between values.
* We enforced the invariant that there could be no gaps between motions, since the previous self-evaluation specified that we should have done so.
* Finally, we added descriptions for all Controller and View related classes/interfaces, since they weren't required last assignment.

### Misc.
More minute details of the implementation (rules for constructing models, shapes, controllers, views, when smaller exceptions are thrown, how processes are coded, etc.) are documented appropriately in the actual code.
