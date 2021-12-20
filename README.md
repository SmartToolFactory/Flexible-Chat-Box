## Flexible Chat Row and Resizable SubcomposeLayout


Flexible chat row,  `ChatFlexBoxLayout`, that positions it's elements based on number of message text has
parent width, message and message status width.
And `SubcomposeColumn` created using **SubComposeLayout** which remeasures it's children based on
longest children. This is useful fro matching quote message and message length after position
calculation. These two composables are useful for creating dynamic message rows that positions children and positions message, message date and message status.

| Full Chat      | Chat Width   | Resizable|
| ----------|-----------| -----------|
| <img src="./screenshots/full_chat_implementation.png" width="30%"/> | <img src="./screenshots/chat_width_implementation.png"  width="30%"/> | <img src="./screenshots/resizable_implementation.gif"  width="30%"/> |


### ChatFlexBoxLayout
This is a layout that measures and positions message and another container that uses message
date or message date and message received status like messaging apps does.

There are 4 possible conditions to position message and stats
* Multiple line message with last line width and message status is shorter message text length + right padding
* Multiple line message with last line width and message status is longer message text length + right padding
* Single line message 