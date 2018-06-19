# RemoteHelper
An android library to add remote assistance to your application
This is an open source android library that helps application developers include remote assistance in their applications.
The library can either be built from scratch using the source code by opening it as an Android Studio Project and firing a fresh gradle build
or can be downloaded from the app/build/outputs/aar as an aar file
For using the library into their application, developers need to transform their code in three steps:
1. Add gradle dependency to the downloaded aar file
2. Replace all your default widgets with custom classes provided in the library.
  For example, replace TextView with RecorderTextView, EditText with RecorderEditText, and so on.
3. Finally, replace all your activity classes with custom classes provided in the library.
  For example, if HomeActivity extends AppCompatActivity, then replace it with HomeActivity extends RecorderAppCompatActivity

Once the changes are done, fire a new gradle build for your app (If the app does not use INTERNET permission than you have to add that).
The remote assistance would be provided using an ongoing notification
where users can request for assistance using "REQUEST" button and provide assistance using "CONNECT" button.
Currently, as this is a prototype, peer devices need to be in the same network for the functionalities to work.
OFFLINE MODE would allow users to record a series of events as a "Data.xml" file created in the app default folder.
Users can then send this file to anyone else having the same application to replay the same series of events.
