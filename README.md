CS 3500 - Object Oriented Design - Summer 1 2019
John Lam and David Zhang

We decided to implement our model for our animator project using a few classes and interfaces. 
#### IModel
Our IModel interface is used to represent the logic for animating different shapes. It guarantees that any implementing classes will be able to provide the following functions:
1. Returning a String representation of the animation
2. Adding motions via its parameters
3. Adding shapes via its parameters
4. Returning a list of the model's shapes as a (read-only) list
5. Updating the model's shapes by giving it an integer tick value

#### IModelImpl
Our IModelImpl class implements the IModel interface described above. It stores a list of shapes that represents what is currently being drawn/animated. Each individual shape will contain the information required for it to be animated, while the model class itself just contains the states of the shapes at an arbitrary tick. All methods that the IModel interface requires are fully implemented, with the exception of the last method listed above. We are awaiting details on the how the tweening function will work, before we can properly update the model's shapes with a tick value. We will most likely end up delegating the details of updating the shapes to the IShapeImpl class itself, where it would return a new IModelShape with the 
