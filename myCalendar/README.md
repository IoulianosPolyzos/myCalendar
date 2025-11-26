# Project: Implementation of a Calendar in Java

This project is an implementation of a calendar using a desktop (GUI) application.
## Classes and Interface:



 - **Main**: Executes the program and handles necessary checks for reminders (alarms).

- **Event**: Superclass containing the basic attributes and structure of an event, as well as handling of an iCal file (write, save, check valid file).

- **Appointment**: Subclass of Event that inherits event attributes and adds a Duration for an appointment. This class also includes methods to add an appointment to the calendar.

- **Task**: Subclass of Event that inherits event attributes and adds a Deadline for a task. This class also includes methods to add a task to the calendar.

- **ListPanel**: Inherits from JPanel and is used to handle array lists and display them correctly (with a display button).

- **FramesManagement**: Handles all windows and their respective buttons.

- **TaskListPanel**: Similar to ListPanel, but specifically for handling array lists related to tasks (Due, ToDo).
 - **ArrayListModel**: Inherits from AbstractListModel and supports the functionality of ListPanel and TaskListPanel.




## Οδηγίες Εκτέλεσης

To run the application, follow these steps:

1. Open a terminal and navigate to the project folder containing the pom.xml file.
2. Run the command: `mvn package` . This will create the .jar file.
3. Go to the target folder where the .jar file was created.
4. Run the command: `java -jar myCalendar-1.0-SNAPSHOT.jar ` to execute the program.

- *The project folder also contains a report and a sample .ics file for testing.**


---

This README provides a brief description of the project and instructions for using the code. The project was implemented using Java 17 LTS.


