CS 3500 - Object Oriented Design - Summer 1 2019

John Lam and David Zhang

We decided to implement our animator project using a variety different classes and interfaces, using the Model-View-Controller pattern.

## The Model

### IModel
The IModel interface is used to represent the logic for animating different shapes. It guarantees that any implementing classes will be able to provide the following functions:
1. Returning a String representation of the animation
2. Adding keyframes via its parameters (as long as the keyframes don't conflict with each other - specific details below)
3. Adding shapes via its parameters (as long as no shape with the same id exists)
4. Removing a shape from the model given its id
5. Returning a new *read-only* list of the model's updated shapes by giving it an non-negative integer tick value
6. Removing a keyframe given the shape's id and the tick that the keyframe starts at
7. Returning a list of keyframes that are occurring at a specified tick
8. Returning the starting x-value, starting y-value, width, height, maximum x-value, and maximum y-value of the animation, as well as the final tick of the animation
9. Returning a new *read-only* list of the model's shapes (with each shape's initial state and list of keyframes)

### IModelImpl and IModelImpl.Builder
The IModelImpl class implements the IModel interface described above. It stores information about the model's minimum x/y, its maximum x/y values, and the canvas' width and height. It also stores a list of shapes that represents what is currently being drawn/animated. Each individual shape will contain the information required for it to be animated, while the model class itself just contains the shapes with its state as what it was initialized as. All methods that the IModel interface requires are fully implemented. In order to return a read-only list of the model's updated shapes at a given tick, each shape in the list is responsible for returning a read-only version of itself at that tick (via delegation). Consequently, the controller and/or view receive a new list of read-only shapes for every tick they query for.

This class also contains a nested static builder class (accessible by calling the builder() method in the enclosing class), that implements the given AnimationBuilder interface (given by the assignment). The nested class stores the same fields as the enclosing class. It implements most of the methods required by the AnimationBuilder interface. The only one it doesn't implement properly (it just returns the builder without changing it) is the addKeyFrame method, because we were told by professors and TAs that we could leave a stub there. Upon calling build(), all of the fields in the builder class are used to create an instance of IModelImpl. The nested class is used in conjunction with the AnimationReader (given by the assignment) to build models from text files.

### IReadOnlyShape
The IReadOnlyShape interface represents a shape for the model, but only provides methods that can read from the shape. It provides getter methods that return the values that the shape holds, as well as a method to print a String representation of the shape. It provides no other methods than the aforementioned methods, and provides no means to mutate the shape. This read-only shape is passed into the controller and the view, so that they can read information from this shape, but not write any information to it.

### IModelShape
The IModelShape interface also represents a shape for the model. It extends the IReadOnlyShape interface, meaning that it will provide all the functionalities required to read from a shape, but in addition to those, also provides methods that get the state of the shape at a given tick, add keyframes to this shape, and remove keyframes from this shape (given a start tick). The model itself will interact with IModelShapes, and be able get shapes at a certain tick, and add/remove keyframes, while the controller and view are only be able to read from the same shapes as IReadOnlyShapes.

### IModelShapeImpl and ShapeType
The IModelShapeImpl class implements the IModelShape interface above, and its purpose is to store data about the shape (and its characteristics). It implements all methods that IModelShape requires. All of its fields are final, and *cannot be mutated* (as in cannot be set to different values/references).

This class has a field of type ShapeType, which is an enum that represents what type of shape this instance of the class represents. At the moment, there are only two choices: Rectangle and Ellipse, but more types of shapes can easily be added to ShapeType. The ShapeType in a given shape will determine how that shape will be depicted on in the view. ShapeType also offers a public method that returns a string representation of itself ("rectangle" for RECTANGLE, and "ellipse" for ELLIPSE), and a public method that returns a ShapeType given a String (the String has to be one of "rectangle", or "ellipse", or else an exception is thrown).

This class also has a list of keyframes (keyframes specified below), which represent the states of this shape at any two given "start" and "end" ticks. It assumes and enforces the following invariants about the list:
1. This list will always be sorted by tick (i.e. in chronological order), as incoming keyframes should be in order.
2. There are no conflicting keyframes in the list - as in, two keyframes with the same tick but different fields will never exist.

In order to calculate the shape's state at a given tick, it uses the tweening function given by the assignment. If the list of keyframes is empty or if the given tick is before the first keyframe's tick, then the shape will take the state of its fields (as in whatever it's other fields have been initialized as). If the given tick is after the tick of the last keyframe in the list, then the shape will disappear. However, this means that the animation will stop at the final tick.

### IKeyframe
The IKeyframe interface provides various getter methods that retrieve information from this keyframe, and also provides a method that returns a string representation of this keyframe. An implementation of this interface represents what the state of a shape should be at the given tick.

### IKeyframeImpl
The IKeyframeImpl class implements the IKeyframe interface above, and implements all of its methods. It holds the state of shapes through individual fields. It does not hold a shape itself as we thought that Shapes should have knowledge of keyframes, but not the other way around. All of the fields in the IKeyframeImpl class are final, and thus *cannot be mutated*.

### TestModel
The TestModel class implements the IModel interface above, and serves as an auxiliary class for testing. It takes an Appendable, and is used in tandem with the real controller implementation and a mock view. It ensures that the wiring between the view, controller, and model works properly by appending appropriate messages to the Appendable that can be tested for.

## The Controller

### IController
The IController interface is used to represent a controller that mediates operations between a model and a view. Neither the model nor the view directly communicates with each other- all communication occurs through the controller. This interface provides two methods. The first method gets the view to output text, however the view implements it. If the view is visual-based, it may throw an UnsupportedOperationException. The second method gets the view to render the animation, however the view implements it. If the view is text-based, it may throw an UnsupportedOperationException.

### ControllerImpl
The ControllerImpl class implements the IController interface above. It contains an IView, an IModel, a Timer, tick rate, current tick, and an Appendable. It has a timer to facilitate rendering the animation for visual views, and an Appendable to pass onto text-based views. The timer takes in an anonymous ActionListener that tells the view to render an image at a given tick. The anonymous ActionListener will also loop the animation if the option is required and is currently selected (kept track by a boolean value in the class). The controller additionally implements the Features (below) interface that implements the features that GUI view may support. Upon construction, a controller will attempt to set itself as the Features of its view. To perform the features, it may mutate the model to add/modify/remove shapes and keyframes, or pause/start the timer.

### TestController
The TestController class functions as an auxiliary class that takes in an Appendable and prints to it based on what features its view calls for. It is used with a mock view and no model. All the feature methods print an appropriate method to the Appendable, and this message can be checked for during testing.

## The View

### IView
The IView interface is used to represent the "view" of an animation of shapes. Implementing classes should be able to process a given list of shapes into some kind of "view". The view does not necessarily have to represent the animation with images- it may choose to represent it using text or some other medium. It contains the following key methods that should be offered by implementing classes:
1. A way to adjust the canvas size (by setting it to given values),
2. A way to generate some type of output. This output can vary from implementation to implementation, but can range from a visual representation, to a textual representation.
3. A way to set the features that the view supports, though non-GUI views will most likely not support this.
4. A way to set the shapes for the view to represent, though most views will get this information through the method mentioned in 2.

### ATextualView
ATextualView is an abstract class that implements the IView abstract class. It represents a view that outputs text in some form (be it by terminal, or by file). It stores the lowest x and y values of the canvas, and its width and height. Since it receives an Appendable through the toOutput method, it is capable of outputting to various locations, such as a file (using some type of writer), or to the terminal (via System.out). It implements the method (from the interface) that adjusts the canvas size, as well as the method that sets the features for the view (throwing an error in the latter case). It also does nothing for setting the shapes, as it will receive this information in the form of a method parameter (in toOutput). It leaves the method for generating output to extending classes, as each class may have different formats for the text.

### TextView
TextView is a class that extends the ATextualView abstract class. It stores no extra fields, and relies on the abstract class for storing information. It implements the toOutput(List<IReadOnlyShape>, ...) method (the method that generates output), by using each shape's print method (delegating to each shape), formating it according to the assignment's specifications, and appends it to the Appendable parameter. See the assignment or Javadocs for details on how exactly the text is formatted.  

### SVGView
SVGView is a class that extends the ATextualView abstract class. It makes use of the tick rate provided from the toOutput method (in ticks/second so that ticks for each animation can be converted into milliseconds, as the format requires). It implements the toOutput(List<IReadOnlyShape>, ...) using a few helper methods that ultimately format the text according to the [SVG documentation](https://www.w3.org/TR/SVG11/) and append it to the Appendable parameter. The SVG output does not currently support loopback, so animations (and their shapes) will freeze upon completion.

### VisualView
VisualView is a class that extends the JFrame class and implements the IView interface. Unlike the text-based views, the VisualView will create a window with scroll bars that will display the animation. It stores information about the canvas' width, height, maximum x-value, maximum y-value, as well as an AnimationPanel for drawing. When the constructor for this class is called, it creates a window that will pop up on the user's screen with the specified dimensions. The frame size is specified by the input file, while the setPreferredSize method is determined by the model's information about the animations largest x and y coordinates. However, the window will remain blank (without any animations or images) until the render(List<IReadOnlyShape>) is called. When the play method is called in this class, it will pass the given list of shapes into the AnimationPanel, where the shapes will actually be rendered. The play method is expected to be called on every tick, with a slightly different list of shapes, so that output will appear to be an animation. It does not use the setShapes method specified by the interface, and if called, the method will have no effect. Additionally, this class also supports both adjusting the canvas size and setting the maximum window size (as specified by the IView interface), but does not support the setFeatures method, and will throw an UnsupportedOperationException if the latter method is called.

### IAnimationPanel
The IAnimationPanel is an interface that is used to represent the panel onto which images can be drawn. It contains two methods: draw(List<IReadOnlyShape>) that renders the given list of shapes onto the panel, and setCanvas(...) that sets the size of the panel based on the inputs.

### AnimationPanel
AnimationPanel is a class that extends JPanel and implements the IAnimationPanel interface. It stores a list of shapes that represents what is currently being displayed on the panel. The draw method is implemented by storing the given list of shapes in the class, and by delegating the process to the repaint method inherited from the JPanel class. It overrides the paintComponent(Graphics) method from the JPanel class so that calling the method will draw the list of shapes stored in the class onto the panel. Currently, this class only supports rendering filled rectangles, and filled ellipses.

### EditorView
EditorView is a class that extends JFrame and implements the IView interface and is essentially a composite view, taking in the AnimationPanel from above as a field. It utilizes the Swing library to render a variety of panels, buttons, text fields, and labels. It takes in a Features class (below) that represents the features that this editor view supports. To listen to the low-level events emitted by the buttons, the class also implements ActionListener. The view uses JLists to dynamically show the shapes of the animation, and any selected shape's keyframes. Users can perform all operations specified by the Features interface through the interactive aspects of the view. To add a shape, the user can select a shape option and enter an ID. To remove a shape, the user can click a shape and click remove. These operations are mirrored to remove/add/modify a keyframe.

Some specifications:
* The animation will start out stopped on the first frame upon initialization.
* Clicking on the "Speed up" or "Slow down" buttons will increment or decrement the tick rate by 5, respectively (with a minimum tick rate of 1 tick/second).
* The user can modify shapes and keyframes in real time, but may not see the effects until another loop through the animation.
* A shape must be selected to remove it.
* Both a keyframe and its corresponding shape must be selected to remove the keyframe.
* Modifying keyframes is based on the currently selected shape and the equality of the ticks between the two keyframes.
* The animation will stop on the last tick of the animation if loopback is not enabled.
* If a shape has no keyframes, it won't appear on the screen. If it has one keyframe, it will appear briefly for that one tick. A shape will not appear before its first keyframe, and will disappear after its last.
* If a new keyframe is added with an x and y value larger than the existing max, the frame will not update dynamically.
* Adding an identical/existing keyframe will do nothing.
* Upon adding a shape or keyframe, both selections from the lists are deselected/refreshed.
* The minimum size of the editor frame is 750 x 400.
* The keyframe textfields will fill up based on the previously selected keyframe.

### ViewFactory
ViewFactory is a class for generating one of the four concrete implementations of IView listed above. It contains a method that takes in a String (one of "text", "svg", "visual", or "edit"), and will output an IView of the corresponding type. Each view that the ViewFactory returns has to be configured externally by the controller (which takes in an IView) to add view-specific attributes to it (such as canvas size, or tick rates).

### Features
The Features interface represents the features that a GUI view can support. Implementing classes should determine how exactly the features are implemented. The features that it supports are: playing/pausing/restarting the animation, enabling loopback, adding/remove a shape, adding/remove/modifying a keyframe, and speeding up/slowing down.

## The Excellence (main) class
Excellence is a class that contains the main method. It serves as an entry point into the program, and takes in inputs as command line arguments. Currently, four different options are supported:
1. -in *input-file*
2. -view *view-type*, which is one of "text", "svg", "visual", or "edit"
3. -out *output-file*
4. -speed *ticks-per-second*
The first two options are mandatory, the latter two are optional. If -out isn't specified, it will automatically output to System.out (if the view requires an output location). If -speed isn't specified, then it will automatically default to 1 tick/second.

To parse the arguments, the main method uses a nested static class, ArgsProcessor. ArgsProcessor reads through the String array of arguments, parses them into data, and then stores them as static constants in the class. -in options are stored as BufferedReaders, -view options are stored as Strings, -out options are stored as Appendables, and -speed options are stored as integers. The main method can then access these static variables, and use them to read in a model (using the AnimationReader and AnimationBuilder<IModelImpl>), and create an appropriate controller and view (based on the specified view type, and using ViewFactory). After building the model, view, and controller (without error), the main method will then call run() on the controller, which will run the program and generate the appropriate output.
If the given view type was either "text" or "svg", and an -out option was specified (meaning that it did not default to System.out), then the main method will close (and flush) the BufferedWriter so that any remaining data stored in the writer is written out to the appropriate file.

If, for whatever reason, an error occurs (such as invalid command-line arguments, or invalid file names), an error dialog will pop up with an informative message, and the program will terminate. See the Javadocs for details on when exactly a error dialog is created.

## Changelog
For Assignment 7:
* We added a view factory class to help construct views for us. This included refactoring views to be constructed with no arguments.
* Refactored the model to use keyframes instead of motions. This includes the model builder, tweening, and any other animation-related logic. This also includes deleting the notion of a Motion.
* Merged all the controllers into one controller - ControllerImpl. This controller contains logic for the timer, as well as the features our GUI view can perform.
* Implemented a Features interface. This interface represents the features that our editor view supports. Examples of features are playing the animation, stopping the animation, restarting the animation, and modifying the animation in various forms.
* Implemented a new editor view that allows for user interaction with the animation in the form of the features mentioned above through buttons and text fields.
* Changed controller to have two different methods for two different types of views - visual/timer based views and text-based views.
* Changed view interface to have two different methods for two different types of views - visual/timer based views and text-based views. Also gave the option to set the shapes that the view has to render and the features they can support.
* Updated our Excellence (main) method/file to properly support the changes we made.
* Added test versions of the model, controller, and view to help test listeners and wiring.

For Assignment 6:
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
