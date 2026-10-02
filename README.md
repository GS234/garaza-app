# Garaža App

_Simple Android application for automatic garage door control._

### About
This is a mobile (Android) application for controlling garage door. App communicates with a basic IoT device connected to home network that controls a switch for opening and closing the door. The system was primarily designed to replace the need for a manufacturer's remote control, which relies on batteries that need to be replaced regularly.

The app also includes a convenient quick settings tile which makes the task of opening the door easier.

### How it works?
Upon tapping on the tile or swiping a slider within the app, a GET request is sent to an IoT device, triggering a switch that simulates a push of a physical button. Request URL can be specified as variables BASE_URL and REQUEST_PARAMS inside a file _local.properties_.

Example:

__local.properties__:
```
...
BASE_URL=http://example.url/
REQUEST_PARAMS=open?o=1
...
```
The request from example is then combined into ```http://example.url/open?o=1```.

