package org.example;

import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static org.example.Appointment.getDaysInMonth;
import static org.example.FramesManagement.filePath;
import static org.example.FramesManagement.frame;
import static org.example.Task.tasks;

public class Event  {

    //variables
    protected String title;

    protected String description;
    protected LocalDateTime date,startDate,dateCreated;
    protected int year,month,day,hour,minute,second;
    protected static ArrayList<Event> events=new ArrayList<>();//arraylists
    protected static ArrayList<Event> futureEventsDay =new ArrayList<>();
    protected static ArrayList<Event> futureEventsWeek=new ArrayList<>();
    protected static ArrayList<Event> futureEventsMonth=new ArrayList<>();
    protected static ArrayList<Event> PastEventsWeek=new ArrayList<>();
    protected static ArrayList<Event> PastEventsDay=new ArrayList<>();
    protected static ArrayList<Event> PastEventsMonth=new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event(String title, String description, LocalDateTime date,LocalDateTime dateCreated) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.dateCreated=dateCreated;
    }//event constructor

    protected String getTitle() {
        return title;
    }//setters and getters

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }


    protected boolean validYear(String yearString) {
        // Check if the year is valid, return false if not
        try {
            int year = Integer.parseInt(yearString);
            if (year <= 1000 || year > 9999) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }

    protected boolean validMonth(String monthString) {
        // Check if the month is valid, return false if not
        try {
            int month = Integer.parseInt(monthString);
            if (month < 1 || month > 12) {  // Month must be 1-12
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }

    protected boolean validDay(String dayString) {
        // Check if the day is valid, return false if not
        try {
            int day = Integer.parseInt(dayString);
            int maxDays;

            if (day < 1 || day > 31) {  // Day must be 1-31
                return false;
            }

            // Check the maximum days based on the month
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                maxDays = 31;   // Different length in each month
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                maxDays = 30;
            } else {    // Check maximum days of February
                maxDays = Year.isLeap(year) ? 29 : 28;  // If year is leap
            }

            if (day > maxDays) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }

    protected boolean validHour(String hourString) {
        // Check if the hour is valid, return false if not
        try {
            int hour = Integer.parseInt(hourString);
            if (hour < 0 || hour > 23) {    // Hour must be 0-23
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }

    protected boolean validMinute(String minuteString) {
        // Check if the minute is valid, return false if not
        try {
            int minute = Integer.parseInt(minuteString);
            if (minute < 0 || minute > 59) {    // Minute must be 0-59
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }

    protected boolean validSecond(String secondString) {
        // Check if the second is valid, return false if not
        try {
            int second = Integer.parseInt(secondString);
            if (second < 0 || second > 59) {    // Same with minute
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            // If the user types a non-number, return false
            return false;
        }
    }


    public static void saveIcalFile(Event event, String fileName) {
       try (FileWriter fileWriter = new FileWriter(fileName,true)) {
           //add ical content to file Writer
           fileWriter.write(createICalContent(event));
       } catch (IOException e) {
           //print error message
           System.out.println("Error during the creation of ical file: " + e.getMessage());
       }
   }
    public static String IcalStart(String filename){    //this method is used to add on an ical file the top 4 lines
         StringBuilder icalContentBuilder = new StringBuilder();
            icalContentBuilder.append("BEGIN:VCALENDAR\n");
            icalContentBuilder.append("VERSION:2.0\n");
            icalContentBuilder.append("PRODID:-//Office Holidays Ltd.//EN\n");
            icalContentBuilder.append("CALSCALE:GREGORIAN\n");
            return icalContentBuilder.toString();

    }
     public static String EndOfIcal(String filename){   //this method is used to add on an ical file the last line "END:VCALENDAR"
         StringBuilder icalContentBuilder = new StringBuilder();
         icalContentBuilder.append("END:VCALENDAR");
         return icalContentBuilder.toString();
     }
     public static void AddAtStart(String filename){
         try (FileWriter fileWriter = new FileWriter(filename,true)) {
               //write the top 4 lines on an ical file
               fileWriter.write(IcalStart(filename));
           } catch (IOException e) {

               System.out.println("Error during the creation of ical file:" + e.getMessage());
           }
       }
    public static void AppendIcal(String filename){
        try (FileWriter fileWriter = new FileWriter(filename,true)) {
               //write the last line on an ical file
               fileWriter.write(EndOfIcal(filename));
           } catch (IOException e) {

               System.out.println("Error during the creation of ical file: " + e.getMessage());
           }

    }

    public static void RemoveLineFromFile(String filename,String lineToRemove) {//removing the last line of an existing ical file in order to write an event succesfully and not after the line "END:VCALENDAR"
    
        String file=filename; // filename

        try {
            File inputFile = new File(file);//source file
            File tempFile = new File("tempfile.ics");//destination file

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));//reads from source file
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));//write to destination file

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                
                if (currentLine.equals(lineToRemove)) {
                    continue; 
                }
                
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();

            // Replace the original file with the temp file.
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);//rename the tempfile to input file and delete the temp file.With this way we keep one file
                
            } else {
               
            }
        } catch (IOException e) {
            System.out.println("Error during the editing of the file " + e.getMessage());
        }
    }

    static String createICalContent(Event event) {
         StringBuilder icalContentBuilder = new StringBuilder();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");  //Date format

         TimeTeller teller = TimeService.getTeller();
         LocalDateTime now = teller.now();

         // We convert the current time to the desired format.
         String dtStamp = now.format(formatter);

         //If the event is of type task
         if (event instanceof Task) {
             Task task = (Task) event;   // Convert the event to a Task
             // Start building a VTODO (To-Do) component in iCalendar format
             icalContentBuilder.append("BEGIN:VTODO\n");
             icalContentBuilder.append("CLASS:PUBLIC\n");
             icalContentBuilder.append("DTSTAMP:" + dtStamp + "\n"); //Here, also we add the time that task created
             icalContentBuilder.append("DTSTART;VALUE=DATE:" + task.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + "\n"); //Here we add the time the task starts with the specific format
             icalContentBuilder.append("DUE;VALUE=DATE:" + task.getDeadline().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + "\n"); //Here we add the time the task ends with the specific format
             icalContentBuilder.append("SUMMARY:" + task.getTitle() + "\n"); //title

             icalContentBuilder.append("DESCRIPTION:" + task.getDescription() + "\n");
             icalContentBuilder.append("STATUS:" + task.statusTask + "\n"); //Here we add if it is Completed Or not

             //VALARM for the task
             icalContentBuilder.append("BEGIN:VALARM\n");
             icalContentBuilder.append("TRIGGER:PT0M\n"); // Trigger the alarm at the time of the event
             icalContentBuilder.append("ACTION:DISPLAY\n");    // Display a message
             icalContentBuilder.append("END:VALARM\n");

             icalContentBuilder.append("END:VTODO\n"); //close a VTODO component

         } else if (event instanceof Appointment) {//If the event is of type appointment
             Appointment appointment = (Appointment) event; // Convert the event to a Appointment
             // Start building a VEVENT (Event) component in iCalendar format
             icalContentBuilder.append("BEGIN:VEVENT\n");
             icalContentBuilder.append("CLASS:PUBLIC\n");
             icalContentBuilder.append("DTSTAMP:" + dtStamp + "\n"); //Here, also we add the time that task created
             icalContentBuilder.append("DTSTART;VALUE=DATE:" + appointment.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + "\n");//Here we add the time the task starts with the specific format
             icalContentBuilder.append("DURATION:PT" + appointment.getDuration().toHours() + "H" + appointment.getDuration().minusHours(appointment.getDuration().toHours()).toMinutes() + "M\n");// Set the duration of the event in iCalendar format (Hours and Minutes)
             icalContentBuilder.append("SUMMARY:" + appointment.getTitle() + "\n"); //title
             icalContentBuilder.append("DESCRIPTION:" + appointment.getDescription() + "\n");

             //VALARM for the appointment
             icalContentBuilder.append("BEGIN:VALARM\n");
             icalContentBuilder.append("TRIGGER:PT0M\n"); // Trigger the alarm at the time of the event
             icalContentBuilder.append("ACTION:DISPLAY\n"); // Display a message
             icalContentBuilder.append("END:VALARM\n");

             icalContentBuilder.append("END:VEVENT\n");  //close a VEVENT component
         }
         return icalContentBuilder.toString();
    }

    static int tempEventsValid = 0;
    static int tempTaskValid = 0;

    public static boolean IsValidIcal(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            boolean endVcalendar = false;

            // Check if the first line of the iCalendar data is "BEGIN:VCALENDAR"
            if (!reader.readLine().trim().equals("BEGIN:VCALENDAR")) {
                return false;
            }

            //boolean variables, which help us to know if the iCalendar data is valid
            boolean versionFound = false;    // Check if VERSION:2.0 is present
            boolean calscaleFound = false;   // Check if CALSCALE:GREGORIAN is present
            boolean prodidFound = false; // Check if PRODID:-//Office Holidays Ltd.//EN is present
            int number = 0;

            // Read the next three lines to find and validate iCalendar properties
            while (number < 3) {
                line = reader.readLine().trim();
                number += 1;

                if (line.equals("VERSION:2.0")) {
                    versionFound = true;
                } else if (line.equals("CALSCALE:GREGORIAN")) {
                    calscaleFound = true;
                } else if (line.equals("PRODID:-//Office Holidays Ltd.//EN")) {
                    prodidFound = true;
                }
            }

            // Check if all the required lines have been found or return false( not valid ical file)
            if (!versionFound || !calscaleFound || !prodidFound) {
                return false;
            }

            int tempEvents = 0;

            int tempTasks = 0;

            int lineCounter = 4; //now we are in 4th line of the file

            // Continue reading lines from the 'reader' until the end of the file is reached
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                lineCounter++; //increase the line beacause we gonna check line 5

                // Check if it's the 5th line and verify it's a valid iCalendar component
                if (lineCounter == 5) {
                    if (!line.equals("BEGIN:VEVENT") && !line.equals("BEGIN:VTODO") && !line.equals("END:VCALENDAR")) {
                        return false;
                    }
                }


                if (line.equals("BEGIN:VEVENT")) {
                    tempEvents += 1;// How many times did it find BEGIN:VEVENT
                    if (IsValidEvent(reader)) {

                        tempEventsValid += 1; // How many times it was valid
                    }
                } else if (line.equals("BEGIN:VTODO")) {
                    tempTasks += 1;// How many times did it find BEGIN:VTODO
                    if (IsValidTask(reader)) {

                        tempTaskValid += 1; // How many times it was valid
                    }
                }else if(line.equals("END:VCALENDAR")) {

                    endVcalendar = true;
                    // Return true if all conditions are met: endVcalendar is true, tempEvents equals tempEventsValid, and tempTasks equals tempTaskValid
                    // This means that every time we found a task or appointment, they were valid.
                    return ((endVcalendar) && (tempEvents == tempEventsValid) && (tempTasks == tempTaskValid));

                }else if (!line.equals("BEGIN:VEVENT") && !line.equals("BEGIN:VTODO")) {// If the line is not "BEGIN:VEVENT" or "BEGIN:VTODO", return false
                    return false;
                }


            }

            // Return true if all conditions are met: endVcalendar is true, tempEvents equals tempEventsValid, and tempTasks equals tempTaskValid
            // This means that every time we found a task or appointment, they were valid.
            return ((endVcalendar) && (tempEvents == tempEventsValid) && (tempTasks == tempTaskValid));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Αν υπάρξει IOException ή δεν βρεθεί έγκυρο event ή task
    }

    private static boolean IsValidEvent(BufferedReader reader) throws IOException {
        String line;
        // Flags to track the presence of specific iCalendar properties in a VEVENT component
        boolean summaryFound = false;    // Check if SUMMARY property is found
        boolean descriptionFound = false;  // Check if DESCRIPTION property is found
        boolean classFound = false;        // Check if CLASS property is found
        boolean endveventFound = false;    // Check if END:VEVENT is found
        boolean dtstartFound = false;      // Check if DTSTART property is found
        boolean dtstampFound = false;      // Check if DTSTAMP property is found
        boolean durationFound = false;     // Check if DURATION property is found

        boolean beginAlarmFound = false;         // Check if BEGIN:VALARM is found
        boolean endAlarmFound = false;           // Check if END:VALARM is found
        boolean triggerFound = false;            // Check if TRIGGER property is found
        boolean actionFound = false;             // Check if ACTION property is found

        // Variables to store date-time values and content for iCalendar properties in a VEVENT component
        LocalDateTime dtstampDateTime = null;   // DTSTAMP date-time value
        LocalDateTime dtstartDateTime = null;   // DTSTART date-time value
        String descriptionContent = "";        // Content of the DESCRIPTION property
        String summaryContent = "";            // Content of the SUMMARY property
        Duration eventDuration = null;          // Duration of the event, if specified


        int numberOfLines=0;
        // Read lines until "END:VEVENT" is encountered
        while ((line = reader.readLine()) != "END:VEVENT" ) {
            line = line.trim();
             Appointment newAppointment=new Appointment("","",null,null,null);

            // Check if the line starts with "DTSTAMP:" and validate the date-time format
             if (line.startsWith("DTSTAMP:") && IsValidDateTime(line.substring("DTSTAMP:".length()))) {

                dtstampFound=true;
                dtstampDateTime = ConvertToDateTime(line.substring("DTSTAMP:".length()).trim()); // Convert the substring after "DTSTAMP:" to a LocalDateTime and assign it to dtstampDateTime

            } else if (line.startsWith("DTSTART;VALUE=DATE:") && IsValidDateTime(line.substring("DTSTART;VALUE=DATE:".length()))) {// Check if the line starts with "DTSTART;VALUE=DATE:" and validate the date-time format

                dtstartFound=true;
                dtstartDateTime=ConvertToDateTime(line.substring("DTSTART;VALUE=DATE:".length()).trim()); // Convert the substring after "DTSTART;VALUE=DATE:" to a LocalDateTime and assign it to dtstartDateTime

            } else if (line.startsWith("DURATION:") && IsValidDuration(line.substring("DURATION:".length()))) { // Check if the line starts with "DURATION:" and validate the duration format

                durationFound=true;
                eventDuration = Duration.parse(line.substring("DURATION:".length()).trim());    // Parse the substring after "DURATION:" as a Duration and assign it to eventDuration

            }else if (line.startsWith("SUMMARY:")) {    // Check if the line starts with "SUMMARY:"

                summaryFound = true;
                summaryContent = line.substring("SUMMARY:".length()); // Extract the content after "SUMMARY:" and assign it to summaryContent

            } else if (line.startsWith("DESCRIPTION:")) {   // Check if the line starts with "DESCRIPTION:"

                 descriptionFound = true;
                 descriptionContent = line.substring("DESCRIPTION:".length()); // Extract the content after "DESCRIPTION:" and assign it to descriptionContent

            }   else if (line.startsWith("CLASS:PUBLIC")) {  // Check if the line starts with "CLASS:PUBLIC"
                classFound = true;
            } else if (line.equals("END:VEVENT")) { // Check if the line starts with "END:VEVENT"

                endveventFound =true;
                // Create a new Appointment object with extracted properties and add it to the events list
                newAppointment =new Appointment(summaryContent,descriptionContent,dtstartDateTime,dtstampDateTime ,eventDuration);
                events.add(newAppointment);


                 // Return true if all the required iCalendar properties within a VEVENT component are found
                return (summaryFound && descriptionFound && classFound && endveventFound &&
                        dtstartFound && dtstampFound && durationFound && beginAlarmFound &&
                        triggerFound && actionFound && endAlarmFound);

            } else if(line.equals("BEGIN:VALARM")){

                 // if we have found endAlarm before beginAlarm return false(which means that is not valid)
                 if(endAlarmFound==true){
                    return false;
                 }
                 beginAlarmFound = true;

            }else if(line.equals("TRIGGER:PT0M")){

                 triggerFound=true;

            }else if(line.equals("ACTION:DISPLAY")){

                 actionFound=true;

            }else if(line.equals("END:VALARM")){

                 endAlarmFound=true;

            }

            numberOfLines+=1; //increase the number of line in each loop
            //we have this 'if' because we want to check if we have all the requirements
            //1. DTSTAMP 2.DTSTART;VALUE=DATE: 3.DURATION: 4.SUMMARY:
            //5. DESCRIPTION: 6. CLASS:PUBLIC 7.BEGIN:VALARM 8.TRIGGER:PT0M
            //9. ACTION:DISPLAY 10.AlarmDescription 11. END:VALARM
            if(numberOfLines>10){
                break;
            }

        }

        // Return true if all the required iCalendar properties within a VEVENT component are found
        return (summaryFound && descriptionFound && classFound && endveventFound &&
                dtstartFound && dtstampFound && durationFound && beginAlarmFound &&
                triggerFound && actionFound && endAlarmFound);
    }

     private static boolean IsValidTask(BufferedReader reader) throws IOException {
         String line;

         // Flags to track the presence of specific iCalendar properties in a VTODO component
         boolean summaryFound = false;    // Check if SUMMARY property is found
         boolean descriptionFound = false;  // Check if DESCRIPTION property is found
         boolean classFound = false;        // Check if CLASS property is found
         boolean statusFound = false;       // Check if STATUS property is found
         boolean dueFound = false;          // Check if DUE property is found
         boolean dtstartFound = false;      // Check if DTSTART property is found
         boolean dtstampFound = false;      // Check if DTSTAMP property is found
         boolean endvtodoFound = false;     // Check if END:VTODO is found

         boolean beginAlarmFound = false;         // Check if BEGIN:VALARM is found
         boolean endAlarmFound = false;           // Check if END:VALARM is found
         boolean triggerFound = false;            // Check if TRIGGER property is found
         boolean actionFound = false;             // Check if ACTION property is found


         // Variables to store content and date-time values for iCalendar properties in a VTODO component
         String descriptionContent = "";       // Content of the DESCRIPTION property
         String summaryContent = "";           // Content of the SUMMARY property
         String status = "";                   // Value of the STATUS property
         LocalDateTime dtstampDateTime = null;  // Value of the DTSTAMP property
         LocalDateTime dtstartDateTime = null;  // Value of the DTSTART property
         LocalDateTime dueDateTime = null;      // Value of the DUE property


         int numberOfLines=0;
         // Read lines until "END:VTODO" is encountered
         while ((line = reader.readLine()) != "END:VTODO") {

             line = line.trim();
             Task newTask=new Task("","",null,null,"",null);

             // Check if the line starts with "DTSTAMP:" and validate the date-time format
              if (line.startsWith("DTSTAMP:") && IsValidDateTime(line.substring("DTSTAMP:".length()))) {

                 dtstampFound=true;
                 dtstampDateTime = ConvertToDateTime(line.substring("DTSTAMP:".length()).trim()); // Convert the substring after "DTSTAMP:" to a LocalDateTime and assign it to dtstampDateTime

             } else if (line.startsWith("DTSTART;VALUE=DATE:") && IsValidDateTime(line.substring("DTSTART;VALUE=DATE:".length()))) {// Check if the line starts with "DTSTART;VALUE=DATE:" and validate the date-time format

                 dtstartFound=true;
                 dtstartDateTime=ConvertToDateTime(line.substring("DTSTART;VALUE=DATE:".length()).trim()); // Convert the substring after "DTSTART;VALUE=DATE:" to a LocalDateTime and assign it to dtstartDateTime

             } else if (line.startsWith("DUE;VALUE=DATE:") && IsValidDateTime(line.substring("DUE;VALUE=DATE:".length()))) { // Check if the line starts with "DUE;VALUE=DATE:" and validate the date-time format

                 dueFound=true;
                 dueDateTime = ConvertToDateTime(line.substring("DUE;VALUE=DATE:".length()).trim()); // Convert the substring after "DUE;VALUE=DATE:" to a LocalDateTime and assign it to dueDateTime

             } else if (line.startsWith("SUMMARY:")) {   // Check if the line starts with "SUMMARY:"

                 summaryFound = true;
                 summaryContent = line.substring("SUMMARY:".length()); // Extract the content after "SUMMARY:" and assign it to summaryContent

             } else if (line.startsWith("DESCRIPTION:")) {   // Check if the line starts with "DESCRIPTION:"
                 descriptionFound = true;
                 descriptionContent = line.substring("DESCRIPTION:".length()); // Extract the content after "DESCRIPTION:" and assign it to descriptionContent

             }else if (line.startsWith("CLASS:PUBLIC")) { // Check if the line starts with "CLASS:PUBLIC"
                  classFound = true;


                  // Check if the line starts with "STATUS:" and the trimmed value is "COMPLETED," "NEEDS-ACTION,".
             }else if (line.startsWith("STATUS:") && ((line.substring("STATUS:".length()).trim().equals("COMPLETED") || line.substring("STATUS:".length()).trim().equals("NEEDS-ACTION")||line.substring("STATUS:".length()).trim().equals("UNKNOWN")))) {

                  statusFound = true;
                  status = line.substring("STATUS:".length()).trim(); // Extract the trimmed value after "STATUS:" and assign it to the status variable

              }
             else if (line.equals("END:VTODO")) {

                 // Create a new Task object with extracted properties and add it to the events and tasks lists
                 newTask=new Task(summaryContent,descriptionContent,dtstartDateTime,dtstampDateTime,status,dueDateTime);

                 endvtodoFound=true;
                 events.add(newTask);
                 tasks.add(newTask);

                 // Return true if all the required iCalendar properties within a VTODO component are found
                 return (summaryFound && descriptionFound && classFound && statusFound &&  dueFound && dtstartFound && dtstampFound && endvtodoFound &&
                         beginAlarmFound && triggerFound && actionFound && endAlarmFound);

             }else if(line.equals("BEGIN:VALARM")){

                  // if we have found endAlarm before beginAlarm return false(which means that is not valid)
                 if(endAlarmFound==true){
                     return false;
                 }
                 beginAlarmFound = true;

             }else if(line.equals("TRIGGER:PT0M")){

                 triggerFound=true;

             }else if(line.equals("ACTION:DISPLAY")){

                 actionFound=true;

             }else if(line.equals("END:VALARM")){

                 endAlarmFound=true;

             }

             // Check if both DTSTART and DUE properties are found
             if(dtstartFound==true && dueFound==true){

                 // Check if the DTSTART date-time is after the DUE date-time
                 if(dtstartDateTime.isAfter(dueDateTime)){
                     // Return false if the DTSTART is after the DUE, indicating an invalid date-time sequence
                     // Τhe deadline cannot be before the start time.
                     return false;
                 }
             }
             numberOfLines+=1; //+1 in each loop

             //we have this 'if' because we want to check if we have all the requirements
             //1. DTSTAMP 2.DTSTART;VALUE=DATE: 3.DUE;VALUE=DATE: 4.SUMMARY:
             //5. DESCRIPTION: 6. CLASS:PUBLIC 7.STATUS: 8.BEGIN:VALARM 9.TRIGGER:PT0M
             //10. ACTION:DISPLAY 11.AlarmDescription 12. END:VALARM
             if(numberOfLines>11){

                 break;
             }

         }

         // Return true if all the required iCalendar properties within a VTODO component are found
         return (summaryFound && descriptionFound && classFound && statusFound &&  dueFound && dtstartFound && dtstampFound && endvtodoFound &&
                 beginAlarmFound && triggerFound && actionFound && endAlarmFound);

     }

    public static void updateICalEventTitle(String fileName, String oldSummary, String newSummary) {
        try {
            // Create File objects for the input and temporary files
            File inputFile = new File(fileName);
            File tempFile = new File("tempfile.ics");

            // Initialize readers and writers
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Read each line from the input file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line starts with "SUMMARY:" and contains the oldSummary
                if (currentLine.startsWith("SUMMARY:") && currentLine.substring("SUMMARY:".length()).equals(oldSummary)) {
                    // Update the line with the newSummary
                    currentLine = "SUMMARY:" + newSummary;
                }

                // Write the current line (either updated or unchanged) to the temporary file
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Close the readers and writers
            writer.close();
            reader.close();

            // Replace the original file with the updated temporary file
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            // Handle IOException, for example by printing the stack trace
            e.printStackTrace();
        }
    }

    public void changeEventTitle(String fileName) {
        // Prompt the user to enter the event title to be changed
        String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);
        boolean eventFound = false;

        // Check if the user input is not null and not empty
        if (userInputTitle != null && !userInputTitle.isEmpty()) {
            // Iterate through each event in the 'events' list
            for (Event event : events) {
                // Check if the event is an instance of Appointment
                if (event instanceof Appointment) {
                    Appointment appointment = (Appointment) event;

                    // Check if the title of the appointment matches the user input
                    if (appointment.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Prompt the user for the new title
                        JTextField titleField = new JTextField();
                        Object[] field = {"New Title:", titleField,};
                        int result = JOptionPane.showConfirmDialog(null, field, "Change Appointment Title", JOptionPane.OK_CANCEL_OPTION);

                        // If the user clicks OK, update the appointment details
                        if (result == JOptionPane.OK_OPTION) {
                            String newTitle = titleField.getText();
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();
                            LocalDateTime newDateCreated = now;
                            String oldTitle = appointment.getTitle();
                            LocalDateTime oldDateCreated = appointment.getDateCreated();

                            // Update appointment details
                            appointment.setTitle(newTitle);
                            appointment.setDateCreated(newDateCreated);

                            // Update the event title and date created in the iCal file
                            updateICalEventTitle(fileName, oldTitle, newTitle);
                            updateICalEventDateCreated(fileName, oldDateCreated, newDateCreated);

                            // Show a success message
                            JOptionPane.showMessageDialog(frame, "Appointment updated successfully!");
                            break;
                        }
                    }
                }
            }

            // Iterate through each event in the 'events' list (for Task)
            for (Event event : events) {
                // Check if the event is an instance of Task
                if (event instanceof Task) {
                    Task task = (Task) event;

                    // Check if the title of the task matches the user input
                    if (task.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Prompt the user for the new title
                        JTextField titleField = new JTextField();
                        Object[] field = {"New Title:", titleField,};
                        int result = JOptionPane.showConfirmDialog(null, field, "Change Task Title", JOptionPane.OK_CANCEL_OPTION);

                        // If the user clicks OK, update the task details
                        if (result == JOptionPane.OK_OPTION) {
                            String newTitle = titleField.getText();
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();
                            LocalDateTime newDateCreated = now;
                            String oldTitle = task.getTitle();
                            LocalDateTime oldDateCreated = task.getDateCreated();

                            // Update task details
                            task.setTitle(newTitle);
                            task.setDateCreated(newDateCreated);

                            // Update the event title and date created in the iCal file
                            updateICalEventTitle(fileName, oldTitle, newTitle);
                            updateICalEventDateCreated(fileName, oldDateCreated, newDateCreated);

                            // Show a success message
                            JOptionPane.showMessageDialog(frame, "Task updated successfully!");
                            break;
                        }
                    }
                }
            }

            // If the event with the specified title is not found, show a warning message
            if (!eventFound) {
                JOptionPane.showMessageDialog(null, "Event with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }



    public static void updateICalEventDate(String fileName, LocalDateTime oldDate, LocalDateTime newDate) {
        try {
            // Create File objects for the input and temporary files
            File inputFile = new File(fileName);
            File tempFile = new File("tempfile.ics");

            // Initialize readers and writers
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Create a DateTimeFormatter for serializing/deserializing dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

            // Read each line from the input file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line starts with "DTSTART;VALUE=DATE:" and the date matches the oldDate
                if (currentLine.startsWith("DTSTART;VALUE=DATE:") && LocalDateTime.parse(currentLine.substring("DTSTART;VALUE=DATE:".length()), formatter).equals(oldDate)) {
                    // Update the line with the newDate
                    currentLine = "DTSTART;VALUE=DATE:" + newDate.format(formatter);
                }

                // Write the current line (either updated or unchanged) to the temporary file
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Close the readers and writers
            writer.close();
            reader.close();

            // Replace the original file with the updated temporary file
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            // Handle IOException, for example by printing the stack trace
            e.printStackTrace();
        }
    }

    public static void updateICalEventDateCreated(String fileName, LocalDateTime oldDateCreated, LocalDateTime newDateCreated) {
        try {
            // Create File objects for the input and temporary files
            File inputFile = new File(fileName);
            File tempFile = new File("tempfile.ics");

            // Initialize readers and writers
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Create a DateTimeFormatter for serializing/deserializing dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

            // Read each line from the input file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line starts with "DTSTAMP:" and the date matches the oldDateCreated
                if (currentLine.startsWith("DTSTAMP:") && LocalDateTime.parse(currentLine.substring("DTSTAMP:".length()), formatter).equals(oldDateCreated)) {
                    // Update the line with the newDateCreated
                    currentLine = "DTSTAMP:" + newDateCreated.format(formatter);
                }

                // Write the current line (either updated or unchanged) to the temporary file
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Close the readers and writers
            writer.close();
            reader.close();

            // Replace the original file with the updated temporary file
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            // Handle IOException, for example by printing the stack trace
            e.printStackTrace();
        }
    }

    // Method to change the date of an event (either Appointment or Task)
    public void changeEventDate(String fileName) {
        // Prompt user to enter the title of the event to be changed
        String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);

        // Flag to check if the event is found
        boolean eventFound = false;

        // Check if the entered title is not empty
        if (userInputTitle != null && !userInputTitle.isEmpty()) {

            // Iterate through the list of events
            for (Event event : events) {
                if (event instanceof Appointment) {
                    Appointment appointment = (Appointment) event;

                    // Check if the title of the event matches the user input
                    if (appointment.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Fields to input the new date components
                        JTextField DateYearField = new JTextField();
                        JTextField DateMonthField = new JTextField();
                        JTextField DateDayField = new JTextField();
                        JTextField DateHoursField = new JTextField();
                        JTextField DateMinutesField = new JTextField();
                        JTextField DateSecondsField = new JTextField();

                        // Array of fields for user input
                        Object[] fields = {
                                "Date",
                                "Year:", DateYearField,
                                "Month:", DateMonthField,
                                "Day", DateDayField,
                                "Hour:", DateHoursField,
                                "Minutes:", DateMinutesField,
                                "Seconds:", DateSecondsField,
                        };

                        // Show the dialog for user input
                        int result = JOptionPane.showConfirmDialog(null, fields, "Change Event Date", JOptionPane.OK_CANCEL_OPTION);

                        // If the user clicks OK
                        if (result == JOptionPane.OK_OPTION) {
                            LocalDateTime newDate;

                            // Input validation for year
                            String yearString = "";
                            year = Integer.parseInt(DateYearField.getText());
                            while (!validYear(DateYearField.getText())) {
                                if (validYear(yearString)) {
                                    year = Integer.parseInt(yearString);
                                    DateYearField.setText(yearString);
                                    break;
                                } else {
                                    year = Integer.parseInt(yearString);
                                    JOptionPane.showMessageDialog(null, "Please add a valid year ");
                                }
                            }

                            // Input validation for month
                            String monthString = "";
                            month = Integer.parseInt(DateMonthField.getText());
                            while (!validMonth(DateMonthField.getText())) {
                                monthString = JOptionPane.showInputDialog("Enter a valid month (1-12):");

                                if (validMonth(monthString)) {
                                    month = Integer.parseInt(monthString);
                                    DateMonthField.setText(monthString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid month");
                                }
                            }

                            // Input validation for day
                            String dayString = "";
                            day = Integer.parseInt(DateDayField.getText());
                            while (!validDay(DateDayField.getText())) {
                                dayString = JOptionPane.showInputDialog("Enter a valid day (1-31):");

                                if (validDay(dayString)) {
                                    day = Integer.parseInt(dayString);
                                    DateDayField.setText(dayString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid day");
                                }
                            }

                            // Input validation for hour
                            String hourString = "";
                            hour = Integer.parseInt(DateHoursField.getText());
                            while (!validHour(DateHoursField.getText())) {
                                hourString = JOptionPane.showInputDialog("Enter a valid hour (0-23):");

                                if (validHour(hourString)) {
                                    hour = Integer.parseInt(hourString);
                                    DateHoursField.setText(hourString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                                }
                            }

                            // Input validation for minute
                            String minuteString = "";
                            minute = Integer.parseInt(DateMinutesField.getText());
                            while (!validMinute(DateMinutesField.getText())) {
                                minuteString = JOptionPane.showInputDialog("Enter a valid minute (0-59):");

                                if (validMinute(minuteString)) {
                                    minute = Integer.parseInt(minuteString);
                                    DateMinutesField.setText(minuteString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid minute");
                                }
                            }

                            // Input validation for second
                            String secondString = "";
                            second = Integer.parseInt(DateSecondsField.getText());
                            while (!validSecond(DateSecondsField.getText())) {
                                secondString = JOptionPane.showInputDialog("Enter a valid second (0-59):");

                                if (validSecond(secondString)) {
                                    second = Integer.parseInt(secondString);
                                    DateSecondsField.setText(secondString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid second");
                                }
                            }

                            // Create a new LocalDateTime object with the user-input components
                            newDate = LocalDateTime.of(year, month, day, hour, minute, second);

                            // Get the current date and time
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();

                            // Create a new date for the event creation
                            LocalDateTime NewDateCreated = now;

                            // Get the old date and date created for the event
                            LocalDateTime oldDate = appointment.getDate();
                            LocalDateTime oldDateCreated = appointment.getDateCreated();

                            // Update the event date and date created
                            appointment.setDate(newDate);
                            appointment.setDateCreated(NewDateCreated);

                            // Update the iCal file with the new date
                            updateICalEventDate(fileName, oldDate, newDate);
                            updateICalEventDateCreated(fileName, oldDateCreated, NewDateCreated);

                            // Show a success message
                            JOptionPane.showMessageDialog(frame, "Appointment updated successfully!");
                            break;
                        }
                    }
                }
            }

            // Check if the event is a Task
            for (Event event : events) {
                if (event instanceof Task) {
                    Task task = (Task) event;

                    // Check if the title of the task matches the user input
                    if (task.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Fields to input the new date components
                        JTextField DateYearField = new JTextField();
                        JTextField DateMonthField = new JTextField();
                        JTextField DateDayField = new JTextField();
                        JTextField DateHoursField = new JTextField();
                        JTextField DateMinutesField = new JTextField();
                        JTextField DateSecondsField = new JTextField();

                        // Array of fields for user input
                        Object[] fields = {
                                "Date",
                                "Year:", DateYearField,
                                "Month:", DateMonthField,
                                "Day", DateDayField,
                                "Hour:", DateHoursField,
                                "Minutes:", DateMinutesField,
                                "Seconds:", DateSecondsField,
                        };

                        // Show the dialog for user input
                        int result = JOptionPane.showConfirmDialog(null, fields, "Change Task Date", JOptionPane.OK_CANCEL_OPTION);

                        // If the user clicks OK
                        if (result == JOptionPane.OK_OPTION) {
                            LocalDateTime newDate;

                            // Input validation for year
                            String yearString = "";
                            year = Integer.parseInt(DateYearField.getText());
                            while (!validYear(DateYearField.getText())) {
                                if (validYear(yearString)) {
                                    year = Integer.parseInt(yearString);
                                    DateYearField.setText(yearString);
                                    break;
                                } else {
                                    year = Integer.parseInt(yearString);
                                    JOptionPane.showMessageDialog(null, "Please add a valid year ");
                                }
                            }

                            // Input validation for month
                            String monthString = "";
                            month = Integer.parseInt(DateMonthField.getText());
                            while (!validMonth(DateMonthField.getText())) {
                                monthString = JOptionPane.showInputDialog("Enter a valid month (1-12):");

                                if (validMonth(monthString)) {
                                    month = Integer.parseInt(monthString);
                                    DateMonthField.setText(monthString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid month");
                                }
                            }

                            // Input validation for day
                            String dayString = "";
                            day = Integer.parseInt(DateDayField.getText());
                            while (!validDay(DateDayField.getText())) {
                                dayString = JOptionPane.showInputDialog("Enter a valid day (1-31):");

                                if (validDay(dayString)) {
                                    day = Integer.parseInt(dayString);
                                    DateDayField.setText(dayString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid day");
                                }
                            }

                            // Input validation for hour
                            String hourString = "";
                            hour = Integer.parseInt(DateHoursField.getText());
                            while (!validHour(DateHoursField.getText())) {
                                hourString = JOptionPane.showInputDialog("Enter a valid hour (0-23):");

                                if (validHour(hourString)) {
                                    hour = Integer.parseInt(hourString);
                                    DateHoursField.setText(hourString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                                }
                            }

                            // Input validation for minute
                            String minuteString = "";
                            minute = Integer.parseInt(DateMinutesField.getText());
                            while (!validMinute(DateMinutesField.getText())) {
                                minuteString = JOptionPane.showInputDialog("Enter a valid minute (0-59):");

                                if (validMinute(minuteString)) {
                                    minute = Integer.parseInt(minuteString);
                                    DateMinutesField.setText(minuteString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid minute");
                                }
                            }

                            // Input validation for second
                            String secondString = "";
                            second = Integer.parseInt(DateSecondsField.getText());
                            while (!validSecond(DateSecondsField.getText())) {
                                secondString = JOptionPane.showInputDialog("Enter a valid second (0-59):");

                                if (validSecond(secondString)) {
                                    second = Integer.parseInt(secondString);
                                    DateSecondsField.setText(secondString);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid second");
                                }
                            }

                            // Create a new LocalDateTime object with the user-input components
                            newDate = LocalDateTime.of(year, month, day, hour, minute, second);

                            // Get the current date and time
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();

                            // Create a new date for the event creation
                            LocalDateTime NewDateCreated = now;

                            // Get the old date and date created for the event
                            LocalDateTime oldDate = task.getDate();
                            LocalDateTime oldDateCreated = task.getDateCreated();

                            // Update the event date and date created
                            task.setDate(newDate);
                            task.setDateCreated(NewDateCreated);

                            // Update the iCal file with the new date
                            updateICalEventDate(fileName, oldDate, newDate);
                            updateICalEventDateCreated(fileName, oldDateCreated, NewDateCreated);

                            // Show a success message
                            JOptionPane.showMessageDialog(frame, "Task updated successfully!");
                            break;
                        }
                    }
                }
            }
        }

        // If the event is not found, display a warning message
        if (!eventFound) {
            JOptionPane.showMessageDialog(null, "Event with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to update the DESCRIPTION field of an iCalendar event
    public static void updateICalEventDescription(String fileName, String oldDescription, String newDescription) {
        try {
            // Open input and output files
            File inputFile = new File(fileName);
            File tempFile = new File("tempfile.ics");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Read each line from the input file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line starts with "DESCRIPTION:" and matches the old description
                if (currentLine.startsWith("DESCRIPTION:") && currentLine.substring("DESCRIPTION:".length()).equals(oldDescription)) {
                    // Update the DESCRIPTION field with the new description
                    currentLine = "DESCRIPTION:" + newDescription;
                }
                // Write the line to the temporary file
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Close file readers and writers
            writer.close();
            reader.close();

            // Replace the original file with the new file containing the updated DESCRIPTION line
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            // Handle any exceptions that occur during file manipulation
            e.printStackTrace();
        }
    }

    // Method to change the DESCRIPTION of an event
    public void changeEventDescription(String fileName) {
        // Get user input for the event title to be changed
        String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);
        boolean eventFound = false;

        // Check if the user input is not empty
        if (userInputTitle != null && !userInputTitle.isEmpty()) {
            // Loop through each event in the list
            for (Event event : events) {
                // Check if the event is an instance of Appointment
                if (event instanceof Appointment) {
                    Appointment appointment = (Appointment) event;

                    // Check if the title of the appointment matches the user input
                    if (appointment.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Get user input for the new description
                        JTextField descriptionField = new JTextField();
                        Object[] field = {"New Description:", descriptionField,};
                        int result = JOptionPane.showConfirmDialog(null, field, "Change Appointment Description", JOptionPane.OK_CANCEL_OPTION);

                        // Check if the user clicked OK
                        if (result == JOptionPane.OK_OPTION) {
                            String newDescription = descriptionField.getText();
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();
                            LocalDateTime NewDateCreated = now;
                            String oldDescription = appointment.getDescription();
                            LocalDateTime oldDateCreated = appointment.getDateCreated();

                            // Update the appointment object
                            appointment.setDescription(newDescription);
                            appointment.setDateCreated(NewDateCreated);

                            // Update the iCalendar file
                            updateICalEventDescription(fileName, oldDescription, newDescription);
                            updateICalEventDateCreated(fileName, oldDateCreated, NewDateCreated);

                            // Display a success message to the user
                            JOptionPane.showMessageDialog(frame, "Appointment updated successfully!");
                            break;
                        }
                    }
                }
            }

            // Loop through each event in the list again (for tasks)
            for (Event event : events) {
                // Check if the event is an instance of Task
                if (event instanceof Task) {
                    Task task = (Task) event;

                    // Check if the title of the task matches the user input
                    if (task.getTitle().equals(userInputTitle)) {
                        eventFound = true;

                        // Get user input for the new description
                        JTextField descriptionField = new JTextField();
                        Object[] field = {"New Description:", descriptionField,};
                        int result = JOptionPane.showConfirmDialog(null, field, "Change Task Description", JOptionPane.OK_CANCEL_OPTION);

                        // Check if the user clicked OK
                        if (result == JOptionPane.OK_OPTION) {
                            String newDescription = descriptionField.getText();
                            TimeTeller teller = TimeService.getTeller();
                            LocalDateTime now = teller.now();
                            LocalDateTime NewDateCreated = now;
                            String oldDescription = task.getDescription();
                            LocalDateTime oldDateCreated = task.getDateCreated();

                            // Update the task object
                            task.setDescription(newDescription);
                            task.setDateCreated(NewDateCreated);

                            // Update the iCalendar file
                            updateICalEventDescription(fileName, oldDescription, newDescription);
                            updateICalEventDateCreated(fileName, oldDateCreated, NewDateCreated);

                            // Display a success message to the user
                            JOptionPane.showMessageDialog(frame, "Task updated successfully!");
                            break;
                        }
                    }
                }
            }

            // Check if the event was not found
            if (!eventFound) {
                JOptionPane.showMessageDialog(null, "Event with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    // Method to get future events for the day
    public ArrayList<Event> FutureEventsDay() {
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        LocalDateTime endOfDay = teller.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Loop through events to find those within the future day
        for (Event e : events) {
            LocalDateTime eventDateTime = e.getDate();

            if (eventDateTime.isAfter(LocalDateTime.now()) && eventDateTime.isBefore(endOfDay) && !futureEventsDay.contains(e)) {
                futureEventsDay.add(e);
            }
        }

        return futureEventsDay;
    }

    // Method to get future events for the week
    public ArrayList<Event> FutureEventsWeek() {
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        LocalDateTime endOfWeek = now.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Loop through events to find those within the future week
        for (Event e : events) {
            LocalDateTime eventDateTime = e.getDate();

            if (eventDateTime.isAfter(now) && eventDateTime.isBefore(endOfWeek) && eventDateTime.isAfter(startOfWeek) && !futureEventsWeek.contains(e)) {
                futureEventsWeek.add(e);
            }
        }

        return futureEventsWeek;
    }

    // Method to get future events for the month
    public ArrayList<Event> FutureEventsMonth() {
        LocalDate currentDate = LocalDate.now();
        int daysOfMonth = getDaysInMonth(currentDate);

        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        LocalDateTime endOfMonth = now.withDayOfMonth(daysOfMonth).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Loop through events to find those within the future month
        for (Event e : events) {
            LocalDateTime eventDateTime = e.getDate();
            if (eventDateTime.isAfter(LocalDateTime.now()) && eventDateTime.isBefore(endOfMonth) && !futureEventsMonth.contains(e)) {
                futureEventsMonth.add(e);
            }
        }

        return futureEventsMonth;
    }

    // Method to get past events for the day
    public ArrayList<Event> PastEventsDay() {
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();

        // Loop through events to find those within the past day
        for (Event e : events) {
            if (e.getDate().isBefore(now.plusDays(1)) && e.getDate().isAfter(startOfDay) && !PastEventsDay.contains(e)) {
                PastEventsDay.add(e);
            }
        }
        return PastEventsDay;
    }

    // Method to get past events for the week
    public ArrayList<Event> PastEventsWeek() {
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7).minusSeconds(1);

        // Loop through events to find those within the past week
        for (Event e : events) {
            LocalDateTime EventDateTime = e.getDate();

            if (EventDateTime.isBefore(now) && EventDateTime.isBefore(endOfWeek) && EventDateTime.isAfter(startOfWeek) && !PastEventsWeek.contains(e)) {
                PastEventsWeek.add(e);
            }
        }

        return PastEventsWeek;
    }

    
    // Method to get past events for the month
    public ArrayList<Event> PastEventsMonth() {
        ArrayList<Event> pastEventsMonth = new ArrayList<>();
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        LocalDateTime start_month = now.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);

        // Loop through events to find those within the past month
        for (Event e : events) {
            LocalDateTime eventDate = e.getDate();
            if (eventDate.isEqual(start_month) || (eventDate.isAfter(start_month) && eventDate.isBefore(now))) {
                pastEventsMonth.add(e);
            }
        }
        return pastEventsMonth;
    }


    private static boolean IsValidDateTime(String dateTime) {//check if the date time is valid
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");//format being used
        try {
            LocalDateTime.parse(dateTime, formatter);//convert the date time is given by user to the format
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validates if the given duration string follows the ISO 8601 duration format
    private static boolean IsValidDuration(String duration) {

        // Regular expression for ISO 8601 duration format
        String durationRegex = "P(?:T(?:[0-9]+H)?(?:[0-9]+M)?(?:[0-9]+S)?)?";

        // Compile the pattern and create a matcher for the given duration
        Pattern pattern = Pattern.compile(durationRegex);
        Matcher matcher = pattern.matcher(duration);

        // Return true if the duration matches the pattern
        return matcher.matches();
    }

    // Converts a string representing a date-time in the "yyyyMMdd'T'HHmmss'Z'" format to a LocalDateTime object
    private static LocalDateTime ConvertToDateTime(String dtstampString) {

        // Define the date-time formatter for the specified pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        // Parse the input string using the formatter and return the LocalDateTime object
        return LocalDateTime.parse(dtstampString, formatter);
    }
   public class EventComparator implements Comparator<Event> {//Event comparator implements interface Comparator type Event
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();

    public  EventComparator(LocalDateTime now) {
        this.now = now;
    }//store current date and time

    @Override
    public int compare(Event event1, Event event2) {//compare two events by collecting from each event the difference between date and current dateTime
        long diff1 = Math.abs(Duration.between(event1.getDate(), now).toMillis());
        long diff2 = Math.abs(Duration.between(event2.getDate(), now).toMillis());

        return Long.compare(diff1, diff2);//compare the two differences
    }
}

    protected  void sortEventsByDate(ArrayList<Event> events, LocalDateTime now) {
        EventComparator eventComparator = new EventComparator(now);
        events.sort(eventComparator);
    }//sorting the events in the list from the closest event to the current timestamp.

    @Override
    public String toString(){
        return"Event:" + getTitle() +
                "\nDate and Time:" + getDate()+
                "\nDescription:" + getDescription();
    }

}

