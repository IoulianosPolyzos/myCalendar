package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import static org.example.Event.events;
import static org.example.Task.tasks;


public class Main {

    public static void main(String[] args) {
        // Create an instance of FramesManagement
        FramesManagement frameManagement = new FramesManagement();
        // Create and show the GUI
        frameManagement.createAndShowGUI();

        // Set up a Timer to execute the checkAlarms() task every second
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                checkAlarms();
            }
        }, 0, 1000);
    }

    // Method to check alarms (tasks and events)
    public static void checkAlarms() {
        // Get the TimeTeller instance from TimeService
        TimeTeller teller = TimeService.getTeller();
        // Get the current date and time
        LocalDateTime currentDateTime = teller.now();

        // Collect deadlines from tasks
        ArrayList<LocalDateTime> deadlines = new ArrayList<>();
        for (Task task : tasks) {
            deadlines.add(task.getDeadline());
        }

        // Collect event times from events
        ArrayList<LocalDateTime> eventTimes = new ArrayList<>();
        for (Event event : events) {
            eventTimes.add(event.getDate());
        }

        // Check for notifications every second
        for (LocalDateTime dateTime : deadlines) {

            // we want the time 30min before the deadline of the task
            LocalDateTime eventDateTimeBefore = dateTime.minusMinutes(30);

            //because system runs very fast in order to catch the correct time we add 2sec
            // By doing that we are sure that the alarm will ring on time.
            LocalDateTime eventDateTimelater = eventDateTimeBefore.plusSeconds(2);
            if (currentDateTime.isAfter(eventDateTimeBefore) && currentDateTime.isBefore(eventDateTimelater)) {
                // print the notification
                showTaskNotification(dateTime);
            }
        }

        for (LocalDateTime dateTime : eventTimes) {

            // we want the time 30min before the time of the event
            LocalDateTime eventDateTimeBefore = dateTime.minusMinutes(30);

            //because system runs very fast in order to catch the correct time we add 2sec
            // By doing that we are sure that the alarm will ring on time.
            LocalDateTime eventDateTimelater = eventDateTimeBefore.plusSeconds(2);
            if (currentDateTime.isAfter(eventDateTimeBefore) && currentDateTime.isBefore(eventDateTimelater)) {
                // print the notification
                showEventNotification(dateTime);
            }
        }
    }

    // Notification for tasks
    public static void showTaskNotification(LocalDateTime taskDateTime) {
        JOptionPane.showMessageDialog(FramesManagement.frame, "The Deadline Of The Task Is About To End at " +
                taskDateTime.format(DateTimeFormatter.ofPattern("HH:mm")) + "!", "Task Reminder!", JOptionPane.INFORMATION_MESSAGE);
    }

    // Notification for events
    public static void showEventNotification(LocalDateTime eventDateTime) {
        JOptionPane.showMessageDialog(FramesManagement.frame, "You Have an EVENT at " +
                eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm")) + "!", "Event Reminder!", JOptionPane.INFORMATION_MESSAGE);
    }

}

