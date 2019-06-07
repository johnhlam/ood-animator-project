CS 3500 - Object Oriented Design - Summer 1 2019
John Lam and David Zhang

We decided to implement our model for our animator project using a few different classes and interfaces. 
#### IModel
The IModel interface is used to represent the logic for animating different shapes. It guarantees that any implementing classes will be able to provide the following functions:
1. Returning a String representation of the animation
2. Adding motions via its parameters (as long as the motions don't conflict with each other)
3. Adding shapes via its parameters (as long as no shape with the same id exists)
4. Returning a list of the model's shapes as a (read-only) list
5. Removing a shape from the model given its id
6. Updating the model's shapes by giving it an non-negative integer tick value

#### IModelImpl
The IModelImpl class implements the IModel interface described above. It stores a list of shapes that represents what is currently being drawn/animated. Each individual shape will contain the information required for it to be animated, while the model class itself just contains the states of the shapes at an arbitrary tick. All methods that the IModel interface requires are fully implemented, with the exception of the last method listed above. We are awaiting details on the how the tweening function will work, before we can properly update the model's shapes with a tick value. We will most likely end up delegating the details of updating the shapes to the shape class itself, where it would return a new shape with the updated fields. 

#### IReadOnlyShape
The IReadOnlyShape interface represents a shape for the model, but only provides methods that can read from the shape. In other words, it provides getter methods that return the values that the shape holds. It provides no other methods than the getter methods listed above, and provides no means to mutate the shape. This read-only shape will be passed into the controller and the view, so that they can read information from this shape, but not write any information to it. 

#### IModelShape
The IModelShape interface also represents a shape for the model. It extends the IReadOnlyShape interface, meaning that it will provide all the functionalities required to read from a shape, but in addition to those, also provides methods that print a String representation of the shape, gets the shape's id, gets the state of the shape at a given tick, and adds motions to this shape. The model itself will interact with IModelShapes, and be able to receive more specific output and add motions in, while the controller and view will only be able to read from the same shapes as IReadOnlyShapes. 

#### IModelShapeImpl
