# Assignment-1 - Odyssey Survey App
My code solutions to Mobile Computing's subject at IIITD
<h2>Odyssey Survey App</h2>
<p>The Odyssey survey app is designed to take feedback from participants of Odyssey. The first activity contains a form with TextViews and EditText boxes that ask the user to enter their name and role (active participant or audience).</p>
<p>After entering their name and role, the user can click a next button to proceed to the next screen. This screen displays five events of the Odyssey (Music, Dance, Play, Fashion, and Food) and checkboxes for each event that the user attended. Additionally, there is an EditText box for the user to provide a numerical rating (on a scale of 1-5) for each event.</p>
<p>The second activity also contains Submit and Clear buttons. The Submit button shows the entered information in a third activity and the Clear button allows the user to clear the information. Upon submission, a Toast is displayed to confirm that the entry has been recorded. The app should be robust across rotations.</p>
<p>For each change in the state of the activities, the app should log an INFO statement and display a Toast indicating the change in state. For example, if the state of the first activity changes from Resumed to Paused, the log should contain the statement "State of activity &lt;name_of_the_activity&gt; changed from Resumed to Paused". The app should log and provide a Toast for all possible state transitions for each activity.</p>
<h3>Bonus:</h3>
<p>Design the rating in a star-marked way on a scale of 1-5.</p>

