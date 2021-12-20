## Flexible Chat Row and Resizable SubcomposeLayout

Flexible chat row,  ```ChatFlexBoxLayout```, that positions it's elements based on number of message text has
parent width, message and message status width.
And **SubcomposeColumn** created using **SubComposeLayout** which remeasures it's children based on
longest children. This is useful fro matching quote message and message length after position
calculation.
<img src="/./screenshots/full_chat_implementation.png" align="right" width="35%"/>

### ChatFlexBoxLayout
This is a layout that measures and positions message and another container that uses message
date or message date and message received status like messaging apps does.

There are 4 possible conditions to position message and stats
* Multiple line me