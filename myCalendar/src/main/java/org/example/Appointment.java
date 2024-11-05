package org.example;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static org.example.FramesManagement.frame;

public class Appointment extends Event {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Duration duration;  //variable for duration

   private long hours;  //variable for hours
    private long minutes;   //variable for minutes

    //appoitnment constructor
    public Appointment(String title, String description, LocalDateTime date,LocalDateTime dateCreated, Duration duration) {
        super(title, description, date,dateCreated);
        this.duration = duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }   //setters and getters

    public Duration getDuration() {
        return duration;
    }

    public static int getDaysInMonth(LocalDate date) {
        return date.lengthOfMonth();
    }

   
    

    public void addAppointment() {
        JTextField titleField = new JTextField();//Field for Title
        JTextField descriptionField = new JTextField();//Field for Description
        JTextField DateYearField = new JTextField();//Field for Year
        JTextField DateMonthField = new JTextField();//Field for Month
        JTextField DateDayField = new JTextField();//Field for Day
        JTextField DateHoursField = new JTextField();//Field for Hours
        JTextField DateMinutesField = new JTextField();//Field for Minutes
        JTextField DateSecondsField = new JTextField();//Field for Seconds
        JTextField DurationHoursField = new JTextField();//Field for the Duration hours
        JTextField DurationMinutesField = new JTextField();//Field for the Duration minutes
        
        
        Object[] fields = {
            "Appointment Title:", titleField,
            "Task Description:", descriptionField,
            "Date",
            "Year:", DateYearField,
            "Month:", DateMonthField,
            "Day", DateDayField,
            "Hour:", DateHoursField,
            "Minutes:", DateMinutesField,
            "Seconds:", DateSecondsField,
            "Duration:",
            "Hours:", DurationHoursField,
            "Minutes:", DurationMinutesField
                
                
                
                
        };//Using an array for all fields
        int result = JOptionPane.showConfirmDialog(null, fields, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);
        
        
        if (result == JOptionPane.OK_OPTION) {
            title = titleField.getText();//Here we get user input for title
            
            String yearString ="";
            year=Integer.parseInt(DateYearField.getText());//Here we get user input for year and convert it to integer 
            while (!validYear(DateYearField.getText())) {//Check with validYear method if user enters a valid year,if not then a message is displayed and re-ask user to give a valid year
                
                if (validYear(yearString)) {
                    year=Integer.parseInt(yearString);
                    DateYearField.setText(yearString);
                    break;
                }
                else {
                    year = Integer.parseInt(yearString);
                    JOptionPane.showMessageDialog(null, "Please add a valid year ");
                }
            }
            
            String monthString = "";
            month=Integer.parseInt(DateMonthField.getText());
            while (!validMonth(DateMonthField.getText())) {//Same with year
                monthString = JOptionPane.showInputDialog("Enter a valid month (1-12):");
                
                if (validMonth(monthString)) {
                    month = Integer.parseInt(monthString);
                    DateMonthField.setText(monthString); 
                    break; 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid month");
                }
            }
            
            String dayString = "";
            day = Integer.parseInt(DateDayField.getText());
            while (!validDay(DateDayField.getText())) {//Same 
                dayString = JOptionPane.showInputDialog("Enter a valid day (1-31):");
                
                if (validDay(dayString)) {
                    day = Integer.parseInt(dayString);
                    DateDayField.setText(dayString); // Ενημέρωση του πεδίου κειμένου με την τιμή του μήνα
                    break; // Έξοδος από την επανάληψη μόλις βρεθεί έγκυρος μήνας
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid day");
                }
            }
            
            String hourString="";
            hour = Integer.parseInt(DateHoursField.getText());
            while (!validHour(DateHoursField.getText())) {//Same 
                hourString = JOptionPane.showInputDialog("Enter a valid hour (0-23):");
                
                if (validHour(hourString)) {
                    hour = Integer.parseInt(hourString);
                    DateHoursField.setText(hourString); 
                    break; 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                }
            }
            
            String minuteString="";
            minute = Integer.parseInt(DateMinutesField.getText());
            while (!validMinute(DateMinutesField.getText())) {//Same 
                minuteString = JOptionPane.showInputDialog("Enter a valid minute (0-59):");
                
                if (validMinute(minuteString)) {
                    minute = Integer.parseInt(minuteString);
                    DateMinutesField.setText(minuteString); 
                    break; 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid minute");
                }
            }
            
            String secondString="";
            second = Integer.parseInt(DateSecondsField.getText());
            while (!validSecond(DateSecondsField.getText())) {//Same 
                secondString = JOptionPane.showInputDialog("Enter a valid second (0-59):");
                
                if (validSecond(secondString)) {
                    second = Integer.parseInt(secondString);
                    DateSecondsField.setText(secondString); 
                    break; 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid second");
                }
            }
            
            
            
            
            
            
            date = LocalDateTime.of(year, month, day, hour, minute, second);
            
           
            description = descriptionField.getText();//Get the description input
            hours=Integer.parseInt(DurationHoursField.getText());//
            minutes = Integer.parseInt(DurationMinutesField.getText());
            duration = Duration.ofHours(hours).plusMinutes(minutes);    //create and store duration
            TimeTeller teller = TimeService.getTeller();
            LocalDateTime now = teller.now();
            
            dateCreated=now;  
            //variable dateCreated is used for dtStamp on the ical file and is initialised with the time and date the event is created
            Appointment newAppointment=new Appointment(title,description,date,dateCreated,duration);    //create appointment and store its attributes
            events.add(newAppointment);//add appointment to events arraylist
            JOptionPane.showMessageDialog(null, "Appointment has been added successfully!");//A message is displayed to inform the user
        }
    }
  
    
    
 
 
    
    public static void updateICalEventDuration(String fileName, Duration oldDuration, Duration newDuration) {
        //Method to change the Appointment Duration inside the ical file
    try {
        File inputFile = new File(fileName);
        File tempFile = new File("tempfile.ics");//Create a temp file that stores the ical file content  

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("DURATION:")) {
                currentLine = "DURATION:" + newDuration; //Read file content and if line "DURATION:" is found , then the new Duration is written 
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);//Rename the temp file to the file that user has loaded
                                             //The old file is deleted
        } else {
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
   public void changeAppointmentDuration(String fileName) {
       //Method to update the Appointment Duration
    String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);
    //Ask user which event does he/she want to change the duration of 
    boolean eventFound = false;

    if (userInputTitle != null && !userInputTitle.isEmpty()) {
        for (Event event : events) {//Search in the events list if there is an appointment with the name given by user
            if (event instanceof Appointment) {
                Appointment appointment = (Appointment) event;

                if (appointment.getTitle().equals(userInputTitle)) {
                    eventFound = true;
                    JTextField DurationHoursField = new JTextField();//Field to update the Duration Hours
                    JTextField DurationMinutesField = new JTextField();//Field to update the Duration Minutes
                    Object[] fields = {"New Duration:",
                                       "Hours:",DurationHoursField,
                                       "Minutes:",DurationMinutesField};//Array with the fields
                    int result = JOptionPane.showConfirmDialog(null, fields, "Change Appointment Description", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        String stringNewDurationHours = DurationHoursField.getText();
                        long newDurationHours = Integer.parseInt(stringNewDurationHours);
                        String stringNewDurationMinutes = DurationMinutesField.getText();
                        long newDurationMinutes = Integer.parseInt(stringNewDurationMinutes);
                        Duration newDuration = Duration.ofHours(newDurationHours).plusMinutes(newDurationMinutes);
                        TimeTeller teller = TimeService.getTeller();
                        LocalDateTime now = teller.now();
                        LocalDateTime NewdateCreated = now;

                        Duration oldDuration = appointment.getDuration();
                        LocalDateTime OldDateCreated = appointment.getDateCreated();//Get the date the appointment was created
                        appointment.setDuration(newDuration);//Set the new Duration
                        appointment.setDateCreated(NewdateCreated);//Set the new Date Created
                        updateICalEventDuration(fileName, oldDuration, newDuration);//Update the old Duration with the new one
                        updateICalEventDateCreated(fileName, OldDateCreated, OldDateCreated);//Update the old date Created with the new one
                        JOptionPane.showMessageDialog(frame,"Appointment has been updated succesfully!");//A confirmation message is displayed to inform the user
                        break;
                    }
                }
            }
        }

        if (!eventFound) {
            JOptionPane.showMessageDialog(null, "Event with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
            //If the event is not found , a message is displayed to inform the user
        }
    }
}
  
    

 

  
   



    @Override
    public String toString() {
        return String.format(
            "<html><b>Appointment:</b> %s<br><b>Created:</b> %s<br><b>Date:</b> %s<br><b>Description:</b> %s<br><b>Duration:</b> %s</html>",
            getTitle(), getDateCreated().format(formatter), getDate(), getDescription(), getDuration()
        );
    }

}
    
