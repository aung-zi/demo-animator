# Glossary/Usage Guide

### Model

The Model is represented by the two interfaces(`AnimatorModel` and `AnimatorModelState`), and the
class `SimpleAnimatorModel`.

`AnimatorModel` contains the methods to mutate the data structures that has the state of the model,
and `AnimatorModelState` contains the methods that return the state of the model.

The Model stores the state of the program using the following data structures:

(1) `timeframe: TreeMap<Integer, ArrayList<Shape>>`

the keys represent the frame of a motion. the values represent a list of shapes, meaning the shape
is there at that particular frame. `TreeMap` is used to take advantage of its methods when
implementing `SVGView`.

(2) `shapes: LinkedHashMap<Shape, Integer>`

the keys represent the all the shapes currently added to the model. The keys represent the count, or
how many instance of shapes there are in `timeframe`.

There are a few other things to note:

- `Shape` defined in this implementation holds a specific meaning. In general, a `Shape`
  contains the following properties: (1) `width`, (2) `height`, (3) `center` (represented by
  `Posn`), (4) `color`, `id`, `type`. However, the shape is set to be equal if the `id` and the
  `type` are the same. This will allow the model to keep track of the `Shape`.
- The model should ideally never contain the shape in every frame, in which it is in motion. It
  should only keep track of the frame at which the shape first and last appear for a particular
- The model also contains information about the `tempo` or `speed` of the animation, as well as the
  height and width of the canvas at which the animation will be drawn on.
- It is made easier to initialize the model with `TweenModelBuilder`. Discussions about the Builder
  is featured below.

#### `TweenModelBuilder`

The Builder works as follows:

- Instead of keeping track of frames which could result in overlapping motions, the Builder keeps
  track of the different operations that the user calls before finally building it. To represent
  this, there is a new set of different objects represented by the interface `Operation` and the
  classes (`OperationColorChange`, `OperationMove`, `OperationName`, `OperationNone`,
  `OperationScaleChange` and the abstract class `OperationSimple`). These objects represent a set of
  different available operations that the user has in his/her hands to create a motion. These 
  operations are stored in the `LinkedHashMap<Integer, ArrayList<Operation>>` where the 
  `Integer` represents the frame.
- When the user choose to run `build()`, the data structure mentioned earlier extracts all the 
  useful information about the state of the `Shape` and add them to the model.
- Note `LifeOfShape` is a unit representing a life of a `Shape`.

For reference to particular method, please refer to their respective Javadocs.

### View

The View is represented by the interface `AnimatorView`. It encompasses all the different views
(`TextAnimatorView`, `SVGAnimatorView`, `VisualAnimatorView`, `InteractiveAnimatorView`)
implemented in the project. Therefore, it contains all the methods of these classes. Also, they are
thrown `UnsupportedOperationException` by default.

There are a few other things to note:

- Because there are many similarities between `VisualAnimatorView` and `InteractiveAnimatorView`,
  there is an abstracted class called `GraphicsAnimatorView` which contains all the methods that
  these two different views share.
- `VisualAnimatorView` and `InteractiveAnimatorView` contain an extended version of `JPanel`,
  `InteractiveAnimatorPanel` and `VisualAnimatorPanel`, which are abstracted out the interface
  `AnimatorPanel` and the class `SimpleAnimatorPanel`.
- These different views are categorized by the enum, `ViewType`.
- There is `AnimatorViewFactory`, which takes in an input (one of "text", "visual",
  "interactive", "svg") and `AnimatorModel` and returns the type of view, depending on the input.

For reference to particular method, please refer to their respective Javadocs.

### Controller

The Controller is represented by the interface `AnimatorController` and the class `Command`, and it
controls the `AnimatorView` that it takes in. The Controller is made flexible with how it handles
different type of view by implementing a `ViewListener`:

The `ViewListener` is essentially a `Map<ViewType, Runnable>` which, depending on the type of view
the `controller` is given, will run a set of configuration to execute the view. Use `run()`
after initializing to call the `ViewListener` to automatically execute the view.

There are other a few other things to note:
- The Controller also configures keyboards, buttons and timer for specific view to perform as
  expected.
- Note that `Duration` is a unit representing a "tick" used to control the `timer`.

For reference to particular method, please refer to their respective Javadocs.



# Log from Previous Assignments

## Assignment 4: Animator (Part 1)

### Building Blocks

There are two immutable value objects defined for the Animator:

- **`Posn`** representing the position of the shape on `Animator`
  <br/><br/>

  `Posn` contains the coordinates `x`, `y` of the `Shape`. The user can retrieve its state with get
  methods.


- **`Shape`** representing all the two-dimensional shapes used in `Animator`
  <br/><br/>
  `Shape` encompasses all the possible shapes that will be used in the Animator. It contains all the
  information that the user will necessarily need to animate objects. In this implementation, it
  contains the `width`, `height`, `center` `position`, and `color`, `id`,
  `type`. There are two additional metadata that it contains that make sure the state of the model
  is preserved as expected. <br/><br/>

---

#### More On Invariants

Before talking about interfaces used in implementing the model, we need to talk about the invariants
for our design decisions. With the current implementation, we want to make sure at any given state,
the motions are joined together and there are no overlaps. We also want to make sure that all
frames (again, represented by `Integers`) starts at 0 at any given state.

---

### Model Interface

The Model implements two interfaces based on whether the method directly mutates the
model (`AnimatorModel`) or returns a preview of the state (`AnimatorModelState`).

There are three basic methods in `AnimatorModel`:

- **`addShape()` - which adds the `Shape`**
  <br/><br/>

  When the user first wants to the add a shape, the user has to admit the `Shape` into the model by
  keeping an invariant of having a range (i.e. from start to end).


- **`addMotion()` - which creates the `motion`,**
  <br/><br/>

  After initializing the `Shape`, the user can keep adding as much motion that he/she wants by
  giving a frame that is not between the previously admitted range. This ensures that there is not
  overlapping, and a joint motion in place.


- **`removeShape()` - which deletes the `Shape`.**
  <br/><br/>

  The user may use this method to remove the occurrence of the `Shape` across the animation.

They interact directly with the keeper of the state of the model, which is represented by an
`HashMap` of `Integer` representing the tick and the `ArrayList` of `Shapes`. There is also another
data structure, an `ArrayList` of `Shapes` that keep track of the number of `Shapes` that are added
into the model. This additional list is required to make it easier to retrieve the `Shapes` from
the `HashMap`.

-----

#### A Word on `moving`, `resizing` and `changing color`

In actual implementation, it is useful for users to have methods, such as `move`, `resize`,
`changeColor` that modify the Shapes and makes the animations more dynamic. We have decided to leave
it to the controller because we believe that these methods are higher functions, implemented from
these three methods that we outlined in the Interface. The paragraph below talks about the model
recognizes and stores a motion.

The field `type` determines, which child of `Shape`, makes it is to easily interpret itself in text.
On the other hand, the field `id` makes it possible to allow the `Shape` to be equal even if they
are not in fact*. (`equals()` is modified for this purpose.) For example, the model will interpret
the user trying to create a motion from tick 0 to tick 5 of expanding a rectangle as follows:
the `Rectangle`, which we will call it as `R` will be initialized from tick 0 to 1. Then, the user
will have the expanded rectangle in tick 5. When the `Rectangle`
are stored as a state, the program will recognize that the two rectangle are the same, even though
the width and height have changed. The user may try to break the program by creating a
`Rectangle` that override the currently implemented `Rectangle` by giving it the same id. However,
it will not pass because the program is ensured to not be able to admit the same
`Rectangle` twice.

-----

Meanwhile, the second Interface contains methods that allow user to preview the current state of the
model:

- **`getShapes()`**
  <br/><br/>

  Returns a list that contains all the Shapes implemented so far.


- **`getShapeInFrame(int frame, Shape s)`**
  <br/><br/>

  Returns the `Shape` if it exists in the frame.


- **`getFramesContainingShape(Shape s)`**
  <br/><br/>

  Returns the list of frames (represented by `Integer`) by traversing through the `Hashmap` to find
  the Shape.

### View

The View renders the state of the model. At this stage, it is only capable of producing a String
formatted output of the current state of the model.

## Assignment 5 Animator (Part 2)

## Changes

- We add 'move', 'reColor', and 'reSize' methods inside the Shape class, all these three methods
  will modify the properties of the shape by creating a new Shape instead of just mutating the
  existing shape.

- For Model, we add a new 'addMotion' method, with given initialized Shape, start time and end time,
  to insert motion between and store in the 'timeframe' field; And we also create a new field '
  signTime' to store the special time during the motion. At the same time, we have new methods '
  getShapeInSignTime' and 'getSignTimesContainingShape' which works the same as 'getShapeInFrame'
  and 'getFramesContainingShape' but targeting the different field which is 'signTime'; Moreover, we
  change our original 'addMotion' method to store the motion in 'signTime' instead of '
  timeframe';Also, we have 'setBounds' method to set up the bounds for canvas, meanwhile we have '
  getBoundsWidth' and 'getBoundsHeight' to get the information of canvas bounds.

TweenModelBuilder:

- This builder is the public face for instantiating the model, and contains the methods that users
  are familiar with. It takes advantage of the basic methods defined in Assignment 4 to perform "
  actual" operations that the user might want to use.

TextView:

- We are using the 'toString' method from view class for Assignment 4, adding a small step that
  dividing the timeframe by tick to calculate the exactly time, also we changes to use 'signTime'
  instead of 'timeframe' to build the text view.

SVGView:

- This method contains one static method that will render the state in the model into an SVG.

GraphicsView:

- We are extending JPanel to create our custom panel to display the animation. AnimatorView contains
  JScrollPane to introduce scrolling features. Otherwise, the design is referenced from the lecture.

AnimatorViewFactory:

- As instructed.

Main:

- The main method acts as a mini controller in this implementation. It checks for arguments
  accordingly, and has a timer installed for visual view.

## Assignment 6 Easy Animator (Part 3)

### Redesigning the Animator

Although the previous assignment's implementation of the Animator worked for some degree, the
TweenBuilder was redesigned to be able to render files that have a huge number of motions, such as
big-bang and toh-12.

Instead of storing every frame in the model, the redesigned implementation only stores the most
significant frames in the model, and relies on `getShapeInFrame` to get the state of the shapes at
any given reasonable frame. The model also now uses TreeMap and LinkedHashMap to accommodate the
change made. It is suitable here to represent the timeline using a `TreeMap` and all the other list
as a `LinkedHashMap` because `TreeMap` has built-in methods that make it easy to find the closest "
significant" frame. On the other hand, `LinkedHashMap` is used because it returns the items it was
put in order, so the sequence is preserved, hence making it easier to create both the visual and SVG
view.

There is this question from the previous assignment about how to store the motion correctly in the
builder, when the user could choose to make the motion in just about any order. This is another key
redesign that was being implemented in the submission. The Builder now uses
`Operation` to record the type of motion that the user want to create. This is advantageous to
making the change as the methods are called because the builder does not need to constantly worry
about the state of the frame had the user decide to manipulate the state later in the chained method
call before the model is actually built.

### Interactive View and Interface Segregation of Views

There are two recommended options in the instructions to implement interface for view, and we have
decided to keep only one interface called `AnimatorView` which has all the methods of any view
class. Of course, there is a default option made in the Interface that throws an
`UnsupportedOperationException` if the class happens to not support that method.

For Interactive View, we have a panel with 5 different buttons: Restart (r), Slow Down (SHIFT +
MINUS), Play/Pause (s), Speed Up (SHIFT + PLUS), Loop Toggle (l) and a label (shows the current
speed) to control the animation. To enable, the user just have to add "interactive" to -view
command line option.

Because there will only be one controller to control different kinds of view, there should be
some sort of efficient ways to handle them. This is where it is important to define an enum for
each type of view.

### Controller

There is one Controller in this submission which works for all the view. It holds the timer and
has three different listeners: keys, buttons and different types of view. The implementation of
the controller is referenced from the example code in the lecture. The controller has a
map for different keystrokes, button clicks and views, and depending on the situation, the
controller looks up the map and fires the action defined in the map.

### AnimationGenerator

There are two different animations tagged with the submission. The first animation represents a
bubble sort and the program is available in the source file. The other one is an improved
version of Tower of Hanoi. The animation adds an elegant transition to the animation.
