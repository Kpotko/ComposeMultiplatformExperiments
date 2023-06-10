Task:
-   Test one of the next Compose Multiplatform components.
-   Choose one component: LazyList, RangeSlider, VerticalScrollbar
-   Choose one platform: Android, iOS, Windows, Linux, macOS. Note, that Scrollbar is only available on desktop platforms.

Tests should cover:
-   user perspective - how component looks, how it reacts on actions
-   developer perspective - is API correct

Results should contain:
-   a list of test cases (main and edge cases)
-   a list of found bugs in main cases
-   a few automated tests that cover main cases (using any language/framework)

Chosen component and platform:
RangeSlider, Android.
_____________________________
Check list: (wrote check list instead of full detailed test cases, for fast finish MVP. It's able to be improved in next iterations if needed)

User Perspective Scenarios:

Visibility Tests:

1) Verify that the RangeSlider minimum and maximum markers are displayed as expected.
2) Verify that the RangeSlider steps are visible (if "steps" parameter is set and != 0)
3) Verify that the RangeSlider track doesn't have steps (is "steps" parameter is set to 0 or not set)

Interaction Tests:

4) Verify that the RangeSlider handles touch events correctly. When a user hold and drag the thumb of the RangedSlider, the thumb should move.
5) Verify that the nearest thumb of RangeSlider handles clicks correctly (When the user clicks on RangeSlider's track the nearest thumb should move to click's point)
6) Verify that when the user drags the thumb to the right, the value increases.
7) Verify that when the user drags the thumb to the left, the value decreases.
8) Verify that if the user drag the minimum thumb to maximum thumb (or maximum thumb to minimum thumb) they transform into 1 marker
9) Verify that if markers has the same values they still handles drags correctly
10) Verify that if markers has the same values they still handles clicks correctly
11) Verify that if the RangedSlider is disabled, it doesn't handle click and drags
12) Verify that thumb moves to the nearest step point when the user finish drag or click

Value Range Tests:

13) Verify that the RangeSlider does not allow to drag thumbs to left below the defined minimum value. (when the user drags thumb to the left position, value should be equals to minValue of valueRange parameter)
14) Verify that the RangeSlider does not allow to drag thumbs to right above the defined maximum value. (when the user drags thumb to the right position, value should be equals to maxValue of valueRange parameter)
15) Verify that the user cannot drag the minimum marker to a value greater than the maximum marker's current value.
16) Verify that the user cannot drag the maximum marker to a value less than the minimum marker's current value.

Accessibility Tests:

17) Verify that the RangeSlider is accessible to screen reader and announces the correct value when the minimum or maximum marker is moved.
18) RangeSlider supports RTL languages


---------------------------------
Developer Scenarios:


Functionality Test:

18) Verify that the 'onValueChange' callback is triggered while (in process) the user moves the minimum or maximum marker.
19) Verify that the 'onValueChangeFinished' callback is triggered when the user finished his move
20) Verify that the correct values are sent to the 'onValueChange' and 'onValueChangeFinished' callbacks when they are triggered.
21) Verify that default parameters is correctly set in the constructor
23) Verify that RangeSlider values can be modified programmatically even if Slider disabled. (check both cases: enabled = true, enabled = false)
24) Verify that RangeSlider is throwing exceptions if parameters set incorrectly (ex. steps parameter is set < 0)
----------------------------------

Bugs:
1) 	Title: initial Values of RangeSlider can be less than minimum and more than maximum value of ValueRange variable.
      Priority: minor
      Steps to reproduce:
   1. in RangeSlider constructor pass the next values: values = -100f..100f, valueRange = -10f..10f
   2. launch app
      Actual Result:
      initial value of slider is -100f and 100f (as we passed in 'values' parameter)
      Expected Result:
      initial value of slider is -10f and 10f (as we passed in 'valueRange' parameter)
      Additional Information:
      If we move one of thumbs, the second thumb's value changes according to 'valueRange' param

	Comment: 
		I don't really sure that it is a bug and not a 'feature', but this is a really confusing me as a developer (and behavior from Additional Information section is confusing me more), so I made a decision to mark this as a bug


2)  Title: When we change value of both Thumbs into the same value, thumbs stop moves after click action
    Priority: minor
    Steps to reproduce:
    1. 'steps' parameter should be set to 0
    2. move the left thumb to any point in the middle of track
    3. move the right thumb to the same point as left thumb
    4. click to any point of track
    Actual Result:
    thumbs did not change their values and did not change positions
    Expected Result:
    one of thumbs change value and move to click position
    Additional Information:
    onValueChangeFinished callback is triggered correctly (so it means that click action is triggered)

3) 	Title: When rangeSlider is using steps we can make "right" thumb with less value than the "left" one
      Priority: major
      Steps to reproduce:
   1. 'steps' parameter is set > 0
   2.	drag the left thumb to the middle step
   3.	hold and drag the right thumb to position more to left than left thumb.
	Actual Result:
		right thumb moves to point more to left that left thumb
		value of right thumb become less than left thumb's value
	Expected Result:
		right thumb move to the same point as left thumb
		value of left and right thumbs become the same
