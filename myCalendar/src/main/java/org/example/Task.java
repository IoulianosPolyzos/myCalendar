package org.example;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Year;
import java.util.Comparator;
import java.util.function.Function;
import static org.example.FramesManagement.frame;

public class Task extends Event {
    private boolean isCompleted;    //boolean variable for the status of the task
    private LocalDateTime deadline; //variable for deadline
    protected static ArrayList<Task> tasks = new ArrayList<>();  //tasks arraylist
    protected ArrayList<Task> dueTasks = new ArrayList<>();  //arraylist for tasks that aren't completed and the deadline has passed will be printed
    protected ArrayList<Task> toDoTasks = new ArrayList<>(); //arraylist for tasks that aren't completed and the deadline has not passed will be printed
    protected String statusTask;    //string for task status
    private int yearDeadline, monthDeadline, dayDeadline, hourDeadline, minuteDeadline, secondDeadline;  //variables for deadline
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //Used for date format

    public Task(String title, String description, LocalDateTime date, LocalDateTime dateCreated, String statusTask, LocalDateTime deadline) {
        super(title, description, date, dateCreated);
        this.statusTask = statusTask;
        this.deadline = deadline;
    }//Task constructor

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }   //setters and getters

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


    public boolean isCompleted() {
        return isCompleted;
    }


    public String SetStatus(String status) { //method to write staus on an ical file
        if (status.equals("YES") || status.equals("yes")) {
            return "COMPLETED";
        } else if (status.equals("NO") || status.equals("no")) {
            return "NEEDS-ACTION";
        } else {
            return "UNKNOWN";
        }
    }

    public String getStatus() {
        return this.statusTask;
    }

    public void addTask() {
        JTextField titleField = new JTextField();//Field for Title
        JTextField descriptionField = new JTextField();//Field for Description
        JTextField DateYearField = new JTextField();//Field for Year
        JTextField DateMonthField = new JTextField();//Field for Month
        JTextField DateDayField = new JTextField();//Field for Day
        JTextField DateHoursField = new JTextField();//Field for Hours
        JTextField DateMinutesField = new JTextField();//Field for Minutes
        JTextField DateSecondsField = new JTextField();//Field for Seconds
        JTextField DeadlineYearField = new JTextField();//Field for Year
        JTextField DeadlineMonthField = new JTextField();//Field for Month
        JTextField DeadlineDayField = new JTextField();//Field for Day
        JTextField DeadlineHoursField = new JTextField();//Field for Hours 
        JTextField DeadlineMinutesField = new JTextField();//Field for Minutes
        JTextField DeadlineSecondsField = new JTextField();//Field for Seconds
        JTextField StatusField = new JTextField();//Field for Status
        Object[] fields = {
        "Task Title:", titleField,
        "Task Description:", descriptionField,
        "Date",
        "Year:", DateYearField,
        "Month:", DateMonthField,
        "Day", DateDayField,
        "Hour:", DateHoursField,
        "Minutes:", DateMinutesField,
        "Seconds:", DateSecondsField,
        "Deadline:",
        "Year:", DeadlineYearField,
        "Month:", DeadlineMonthField,
        "Day", DeadlineDayField,
        "Hour:", DeadlineHoursField,
        "Minutes:", DeadlineMinutesField,
        "Seconds:", DeadlineSecondsField,
        
        
        
        
    };//Using an array for all fields
        int result = JOptionPane.showConfirmDialog(null, fields, "Add Task", JOptionPane.OK_CANCEL_OPTION);//
        
        if (result == JOptionPane.OK_OPTION) {
        title = titleField.getText();//Here we get user input for title

       String yearString ="";
       year=Integer.parseInt(DateYearField.getText());//Here we get user input for year and convert it to integer 
       while (!validYear(DateYearField.getText())) {//Check with validYear method if user enters a valid year,if not then a message is displayed and re-ask user to give a valid year
       yearString = JOptionPane.showInputDialog("Enter a valid year:");       
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
                DateDayField.setText(dayString); 
                break; 
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

    
       String DeadlineyearString ="";
       yearDeadline=Integer.parseInt(DeadlineYearField.getText());
       while (!validYear(DeadlineYearField.getText())) {//Same 
       DeadlineyearString = JOptionPane.showInputDialog("Enter a valid year:");    
            if (validYear(DeadlineyearString)) {
                yearDeadline=Integer.parseInt(DeadlineyearString);
                DeadlineYearField.setText(DeadlineyearString);
                break;
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please add a valid year ");
            }
        }
        while(yearDeadline<year){//In this while loop , it is checked if deadline year is before of the date year, if true then a message is displayed and re ask user to give a valid deadline year 
        DeadlineyearString = JOptionPane.showInputDialog("Deadline year cant be earlier than date year.Enter a valid year for deadline");
        yearDeadline=Integer.parseInt(DeadlineyearString);
        DeadlineYearField.setText(DeadlineyearString);                
            if(yearDeadline>=year){
                yearDeadline=Integer.parseInt(DeadlineyearString);
                DeadlineYearField.setText(DeadlineyearString);
            }
            else {
                JOptionPane.showMessageDialog(null,"Deadline year cant be earlier than date year");    
           }       
        }
        String DeadlineMonthString = "";
        monthDeadline=Integer.parseInt(DeadlineMonthField.getText());
        while (!validMonth(DeadlineMonthField.getText())) {//Same 
        DeadlineMonthString = JOptionPane.showInputDialog("Enter a valid month (1-12):");

            if (validMonth(DeadlineMonthString)) {
                monthDeadline = Integer.parseInt(DeadlineMonthString);
                DeadlineMonthField.setText(DeadlineMonthString);
                break; 
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid month");
            }
        }
        while(yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue())){//Same with deadline check
        DeadlineMonthString = JOptionPane.showInputDialog("Deadline month cant be earlier than date month.Enter a valid month for deadline");
        monthDeadline=Integer.parseInt(DeadlineMonthString);
        DeadlineMonthField.setText(DeadlineMonthString);                
            if(monthDeadline>=month){
                monthDeadline=Integer.parseInt(DeadlineMonthString);
                DeadlineMonthField.setText(DeadlineMonthString);
            }
            else {
                JOptionPane.showMessageDialog(null,"Deadline month cant be earlier than date month");    
           }       
        }
        
        String DeadlineDayString = ""; 
        dayDeadline = Integer.parseInt(DeadlineDayField.getText());
        while (!validDay(DeadlineDayField.getText())) {//Same 
        DeadlineDayString = JOptionPane.showInputDialog("Enter a valid day (1-31):");

            if (validDay(DeadlineDayString)) {
                dayDeadline = Integer.parseInt(DeadlineDayString);
                DeadlineDayField.setText(DeadlineDayString); 
                break; 
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid day");
            }
        }
        
        while (yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
              (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth())){//Same with deadline check
        DeadlineDayString = JOptionPane.showInputDialog("Deadline day cant be earlier than date day.Enter a valid day for deadline");
        dayDeadline=Integer.parseInt(DeadlineDayString);
        DeadlineDayField.setText(DeadlineDayString);                
            if(dayDeadline>=day){
                dayDeadline=Integer.parseInt(DeadlineDayString);
                DeadlineDayField.setText(DeadlineDayString);
            }
            else {
                JOptionPane.showMessageDialog(null,"Deadline day cant be earlier than date day");    
           }       
        }
        
        String DeadlineHourString="";
        hourDeadline = Integer.parseInt(DeadlineHoursField.getText());
        while (!validHour(DeadlineHoursField.getText())) {//Same
        DeadlineHourString = JOptionPane.showInputDialog("Enter a valid hour (0-23):");

            if (validHour(DeadlineHourString)) {
                hourDeadline = Integer.parseInt(DeadlineHourString);
                DeadlineHoursField.setText(DeadlineHourString); 
                break; 
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid hour");
            }
        }
        
        while(yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
             (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth()) ||
             (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline < date.getHour())){//Same with deadline check
        DeadlineHourString = JOptionPane.showInputDialog("Deadline hour cant be earlier than date hour.Enter a valid hour for deadline");
        hourDeadline=Integer.parseInt(DeadlineHourString);
        DeadlineHoursField.setText(DeadlineHourString);
            if(hourDeadline>=hour){
                hourDeadline=Integer.parseInt(DeadlineHourString);
                DeadlineHoursField.setText(DeadlineHourString);
            }
            else {
                JOptionPane.showMessageDialog(null,"Deadline hour cant be earlier than date hour");
           }
        }
        
        String DeadlineMinuteString="";
        minuteDeadline = Integer.parseInt(DeadlineMinutesField.getText());
        while (!validMinute(DeadlineMinutesField.getText())) {//Same
        DeadlineMinuteString = JOptionPane.showInputDialog("Enter a valid minute (0-59):");

            if (validMinute(DeadlineMinuteString)) {
                minuteDeadline = Integer.parseInt(DeadlineMinuteString);
                DeadlineMinutesField.setText(DeadlineMinuteString); 
                break; 
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid minute");
            }
        }
        
        while(yearDeadline < date.getYear() ||
             (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
             (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth()) ||
             (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline < date.getHour()) ||
             (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline == date.getHour() && minuteDeadline < date.getMinute())) {//Same with deadline year
        DeadlineMinuteString = JOptionPane.showInputDialog("Deadline minute cant be earlier than date minute.Enter a valid minute for deadline");
        minuteDeadline=Integer.parseInt(DeadlineMinuteString);
        DeadlineMinutesField.setText(DeadlineMinuteString);                
            if(minuteDeadline>=minute){
                minuteDeadline=Integer.parseInt(DeadlineMinuteString);
                DeadlineMinutesField.setText(DeadlineMinuteString);
            }
            else {
                JOptionPane.showMessageDialog(null,"Deadline minute cant be earlier than date minute");    
           }       
        }
        
        String DeadlineSecondString="";
        secondDeadline = Integer.parseInt(DeadlineSecondsField.getText());
        while (!validSecond(DeadlineSecondsField.getText())) {//Same
        DeadlineSecondString = JOptionPane.showInputDialog("Enter a valid second (0-59):");

            if (validSecond(DeadlineSecondString)) {
                second = Integer.parseInt(DeadlineSecondString);
                DeadlineSecondsField.setText(DeadlineSecondString); 
                break; 
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid second");
            }
        }
        
        
        
        
        
        
        
        deadline = LocalDateTime.of(yearDeadline, monthDeadline, dayDeadline, hourDeadline, minuteDeadline, secondDeadline);
        //Deadline is now completed
        

        
        String[] options = {"YES", "NO"};
        int isCompletedOption = JOptionPane.showOptionDialog(null, "Is the task completed?", "Completion Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        //After a task is added, a message is displayed with two options("YES" and "NO") to ask user if the task is completed,user can press any of these two buttons.
        statusTask = (isCompletedOption == 0) ? "COMPLETED" : "NEEDS-ACTION";//After user pressed one of the two buttons, "COMPLETED" or NEEDS-ACTION" is written to ical file

        
        dateCreated = LocalDateTime.now();//store the date the task was created

       
        Task newTask = new Task(title, description, date, dateCreated, statusTask, deadline);
        tasks.add(newTask);//Add task to tasks list
        events.add(newTask);//Add task to events list

        JOptionPane.showMessageDialog(null, "Task has been added successfully!");//Display a confirmation message that user added a task
      }
    }     
  

  

   

 
   
  public static void updateIcalDeadline(String fileName, LocalDateTime oldDeadline, LocalDateTime newDeadline){
      //Method to change the Task Deadline inside the ical file
        try {
        File inputFile = new File(fileName);
        File tempFile = new File("tempfile.ics");//Create a temp file that stores the ical file content  

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("DUE;VALUE=DATE:")) {//Read file content and if line "DUE;VALUE=DATE" is found , then the new Deadline is written 
                currentLine = "DUE;VALUE=DATE:" +newDeadline.format(formatter); 
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
    public void changeTaskDeadline(String fileName) {
        //Method to update the Task Deadline 
       String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);
       //Ask user which event does he/she want to change the deadline of 
       boolean taskFound = false;
        if (userInputTitle != null && !userInputTitle.isEmpty()) {
         for (Event event : events) {//Search in the events list if there is a task with the name given by user
                 if (event instanceof Task) {
                    Task task = (Task) event;
                    
                    if (task.getTitle().equals(userInputTitle)) {
                        taskFound=true;
                        JTextField DeadlineYearField = new JTextField();//Field to update the Deadline Year
                        JTextField DeadlineMonthField = new JTextField();//Field to update the Deadline Month
                        JTextField DeadlineDayField = new JTextField();//Field to update the Deadline Day
                        JTextField DeadlineHoursField = new JTextField();//Field to update the Deadline Hour
                        JTextField DeadlineMinutesField = new JTextField();//Field to update the Deadline Minutes
                        JTextField DeadlineSecondsField = new JTextField();//Field to update the Deadline Seconds

        
        
                        Object[] fields = {

                        "Deadline",
                        "Year:", DeadlineYearField,
                        "Month:", DeadlineMonthField,
                        "Day", DeadlineDayField,
                        "Hour:", DeadlineHoursField,
                        "Minutes:", DeadlineMinutesField,
                        "Seconds:", DeadlineSecondsField,
             
        
        
        
        
                };//Array with the fields
                    int result = JOptionPane.showConfirmDialog(null, fields, "Add Task", JOptionPane.OK_CANCEL_OPTION);


                    if (result == JOptionPane.OK_OPTION) {
                        LocalDateTime newDeadline;
                        date=task.getDate();//Get the task date in order to check then if the updated deadline is before the date

                   String DeadlineYearString ="";
                   yearDeadline=Integer.parseInt(DeadlineYearField.getText());
                   while (!validYear(DeadlineYearField.getText())) {

                        if (validYear(DeadlineYearString)) {
                            yearDeadline=Integer.parseInt(DeadlineYearString);
                            DeadlineYearField.setText(DeadlineYearString);
                            break;
                        } 
                        else {
                            yearDeadline = Integer.parseInt(DeadlineYearString);
                            JOptionPane.showMessageDialog(null, "Please add a valid year ");
                        }
                    }
                   while(yearDeadline<date.getYear()){
                        DeadlineYearString = JOptionPane.showInputDialog("Deadline year cant be earlier than date year.Enter a valid year for deadline");
                        yearDeadline=Integer.parseInt(DeadlineYearString);
                        DeadlineYearField.setText(DeadlineYearString);                
                            if(yearDeadline>=year){
                                yearDeadline=Integer.parseInt(DeadlineYearString);
                                DeadlineYearField.setText(DeadlineYearString);
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Deadline year cant be earlier than date year");    
                           }       
                        }

                    String DeadlineMonthString = "";
                    monthDeadline=Integer.parseInt(DeadlineMonthField.getText());
                    while (!validMonth(DeadlineMonthField.getText())) {
                        DeadlineMonthString = JOptionPane.showInputDialog("Enter a valid month (1-12):");

                        if (validMonth(DeadlineMonthString)) {
                            monthDeadline = Integer.parseInt(DeadlineMonthString);
                            DeadlineMonthField.setText(DeadlineMonthString); 
                            break; 
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid month");
                        }
                    }
                    while(yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue())){
                        DeadlineMonthString = JOptionPane.showInputDialog("Deadline month cant be earlier than date month.Enter a valid month for deadline");
                        monthDeadline=Integer.parseInt(DeadlineMonthString);
                        DeadlineMonthField.setText(DeadlineMonthString);                
                            if(monthDeadline>=month){
                                monthDeadline=Integer.parseInt(DeadlineMonthString);
                                DeadlineMonthField.setText(DeadlineMonthString);
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Deadline month cant be earlier than date month");    
                           }       
                        }

                    String DeadlineDayString = ""; 
                    dayDeadline = Integer.parseInt(DeadlineDayField.getText());
                    while (!validDeadlineDay(DeadlineDayField.getText())) {
                     DeadlineDayString = JOptionPane.showInputDialog("Enter a valid day (1-31):");

                        if (validDeadlineDay(DeadlineDayString)) {
                            dayDeadline = Integer.parseInt(DeadlineDayString);
                            DeadlineDayField.setText(DeadlineDayString); 
                            break; 
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid day");
                        }
                    }
                    while (yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
                          (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth())){
                     DeadlineDayString = JOptionPane.showInputDialog("Deadline day cant be earlier than date day.Enter a valid day for deadline");
                      dayDeadline=Integer.parseInt(DeadlineDayString);
                      DeadlineDayField.setText(DeadlineDayString);                
                          if(dayDeadline>=day){
                              dayDeadline=Integer.parseInt(DeadlineDayString);
                              DeadlineDayField.setText(DeadlineDayString);
                          }
                          else {
                              JOptionPane.showMessageDialog(null,"Deadline day cant be earlier than date day");    
                         }       
                      }
                    

                    String DeadlineHourString="";
                    hourDeadline = Integer.parseInt(DeadlineHoursField.getText());
                    while (!validHour(DeadlineHoursField.getText())) {
                    DeadlineHourString = JOptionPane.showInputDialog("Enter a valid hour (0-23):");

                        if (validHour(DeadlineHourString)) {
                            hourDeadline = Integer.parseInt(DeadlineHourString);
                            DeadlineHoursField.setText(DeadlineHourString); 
                            break; 
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                        }
                    }
                    while(yearDeadline < date.getYear() || (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
                         (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth()) ||
                         (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline < date.getHour())){//same with month (according to date year and date month and date day)
                   DeadlineHourString = JOptionPane.showInputDialog("Deadline hour cant be earlier than date hour.Enter a valid hour for deadline");
                   hourDeadline=Integer.parseInt(DeadlineHourString);
                   DeadlineHoursField.setText(DeadlineHourString);                
                       if(hourDeadline>=hour){
                           hourDeadline=Integer.parseInt(DeadlineHourString);
                           DeadlineHoursField.setText(DeadlineHourString);
                       }
                       else {
                           JOptionPane.showMessageDialog(null,"Deadline hour cant be earlier than date hour");    
                      }       
                   }

                    String DeadlineMinuteString="";
                    minuteDeadline = Integer.parseInt(DeadlineMinutesField.getText());
                    while (!validMinute(DeadlineMinutesField.getText())) {
                    DeadlineMinuteString = JOptionPane.showInputDialog("Enter a valid minute (0-59):");

                        if (validMinute(DeadlineMinuteString)) {
                            minuteDeadline = Integer.parseInt(DeadlineMinuteString);
                            DeadlineMinutesField.setText(DeadlineMinuteString); 
                            break; 
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid minute");
                        }
                    }
                     while(yearDeadline < date.getYear() ||
                          (yearDeadline == date.getYear() && monthDeadline < date.getMonthValue()) ||
                          (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline < date.getDayOfMonth()) ||
                          (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline < date.getHour()) ||
                          (yearDeadline == date.getYear() && monthDeadline == date.getMonthValue() && dayDeadline == date.getDayOfMonth() && hourDeadline == date.getHour() && minuteDeadline < date.getMinute())) {//same with month (according to date year and date month and date day and also date day hour)
                       DeadlineMinuteString = JOptionPane.showInputDialog("Deadline minute cant be earlier than date minute.Enter a valid minute for deadline");
                       minuteDeadline=Integer.parseInt(DeadlineMinuteString);
                       DeadlineMinutesField.setText(DeadlineMinuteString);                
                           if(minuteDeadline>=minute){
                               minuteDeadline=Integer.parseInt(DeadlineMinuteString);
                               DeadlineMinutesField.setText(DeadlineMinuteString);
                           }
                           else {
                               JOptionPane.showMessageDialog(null,"Deadline minute cant be earlier than date minute");    
                          }       
                    }

                    String DeadlineSecondString="";
                    secondDeadline = Integer.parseInt(DeadlineSecondsField.getText());
                    while (!validSecond(DeadlineSecondsField.getText())) {
                    DeadlineSecondString = JOptionPane.showInputDialog("Enter a valid second (0-59):");

                        if (validSecond(DeadlineSecondString)) {
                            secondDeadline = Integer.parseInt(DeadlineSecondString);
                            DeadlineSecondsField.setText(DeadlineSecondString); 
                            break; 
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid second");
                        }
                    }
                    
                        newDeadline=LocalDateTime.of(yearDeadline, monthDeadline, dayDeadline, hourDeadline, minuteDeadline, secondDeadline);  //store date
                        TimeTeller teller = TimeService.getTeller();
                        LocalDateTime now = teller.now();
                        LocalDateTime NewDateCreated=now; 
                        LocalDateTime oldDeadline = task.getDeadline();
                        LocalDateTime oldDateCreated = task.getDateCreated();//Get the date the task was created
                        task.setDeadline(newDeadline);//Set the new Deadline
                        task.setDateCreated(NewDateCreated);//Set the new Date Created
                        updateICalEventDateCreated(fileName,oldDateCreated,NewDateCreated);//Update the old date Created with the new one
                        updateIcalDeadline(fileName, oldDeadline, newDeadline);//Update the old Deadline with the new one
                        JOptionPane.showMessageDialog(frame,"Task has been updated succesfully!");//A confirmation message is displayed to inform the user
                        break;
                    } 
            }
        }
      }
                if (!taskFound) {
               JOptionPane.showMessageDialog(null, "Task with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
               //If the event is not found , a message is displayed to inform the user
              }
                
    }
}
     protected boolean validDeadlineDay(String dayString) {  //check if the day is valid,if not return false
        try {
            
            
            
            int day = Integer.parseInt(dayString);
            int maxDays;

            if (day < 1 || day > 31) {  //day must be 1-31
                
                return false;
            }

            if (monthDeadline == 1 || monthDeadline == 3 || monthDeadline == 5 || monthDeadline == 7 || monthDeadline == 8 || monthDeadline == 10 || monthDeadline == 12) {
                maxDays = 31;   //different length in each month
            } else if (monthDeadline == 4 || monthDeadline == 6 || monthDeadline == 9 || monthDeadline == 11) {
                maxDays = 30;
            } else {    //check maximum days of February
                maxDays= Year.isLeap(year)?29:28;//if year is leap

            }

            if (day > maxDays) {
                
                return false;
            }//if number of days is given by user,is greater than maximum days of month then return false

            return true;
        } catch (NumberFormatException e) {
            
            return false;
        }
    }
    

public static void updateIcalStatus(String fileName,String oldStatus, String newStatus){
    //A method to update the Task Status inside ical file
    try {
        File inputFile = new File(fileName);
        File tempFile = new File("tempfile.ics");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("STATUS:")) {
                currentLine = "STATUS:" +newStatus; // Update the ical field Status
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

       
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        } else {
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    
}
    public  void changeTaskStatus(String fileName){
        //A method to update Task Status
        String userInputTitle = JOptionPane.showInputDialog(null, "Enter Event title you want to change:", "Event Title", JOptionPane.QUESTION_MESSAGE);
        boolean eventFound = false;
            if (userInputTitle != null && !userInputTitle.isEmpty()) {
                for (Event event : events) {
                        if (event instanceof Task) {
                            Task task = (Task) event;

                            if (task.getTitle().equals(userInputTitle)) {//Check if there is a task with the name given 
                                eventFound = true;
                                String[] options = {"YES", "NO"};
                                int isCompletedOption = JOptionPane.showOptionDialog(null, "Is the task completed?", "Completion Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                String newStatus = (isCompletedOption == 0) ? "COMPLETED" : "NEEDS-ACTION";

                                TimeTeller teller = TimeService.getTeller();
                                LocalDateTime now = teller.now();
                                LocalDateTime NewdateCreated=now; 
                                String oldStatus = task.getStatus();
                                task.SetStatus(newStatus);
                                LocalDateTime oldDateCreated = task.getDateCreated();
                                task.setDateCreated(dateCreated);
                                updateIcalStatus(fileName, oldStatus, newStatus);
                                updateICalEventDateCreated(fileName,oldDateCreated,NewdateCreated);
                                JOptionPane.showMessageDialog(frame,"Task has been updated succesfully!");
                                break;
                            } 
                        }
                }
              if (!eventFound) {
               JOptionPane.showMessageDialog(null, "Event with title '" + userInputTitle + "' not found.", "Event Not Found", JOptionPane.WARNING_MESSAGE);
               //If the event is not found , a message is displayed to inform the user
            }                  
        }
    }






    public ArrayList<Task>DueTasks(){

        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        for(Task task:tasks){
            if (!task.getStatus().equals("COMPLETED")&&task.getDeadline().isBefore(now)&& !dueTasks.contains(task)){//if task is not completed and task deadline is before now and then add task to dueTasks arraylist
                dueTasks.add(task);
            }
        }
        return dueTasks;
    }
    public ArrayList<Task>ToDoTasks(){

        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();
        for(Task task:tasks){
            if (!task.getStatus().equals("COMPLETED")&&task.getDeadline().isAfter(now)&& !toDoTasks.contains(task)){//if task is not completed and task deadline is after now and then add task to todoTasks arraylist
                toDoTasks.add(task);
            }
        }

        return toDoTasks;
    }



      public class TaskComparator implements Comparator<Task> {//Task comparator implements interface Comparator type Task
        TimeTeller teller = TimeService.getTeller();
        LocalDateTime now = teller.now();

    public  TaskComparator(LocalDateTime now) {
        this.now = now;
    }//store current date and time

    @Override
    public int compare(Task t1, Task t2) {//compare two tasks by collecting from each task the difference between date and current dateTime
        long diff1 = Math.abs(Duration.between(t1.getDate(), now).toMillis());
        long diff2 = Math.abs(Duration.between(t2.getDate(), now).toMillis());

        return Long.compare(diff1, diff2);//compare two differences
    }
}
    
    public  void sortTasksByDate(ArrayList<Task> tasks, LocalDateTime now) {
        TaskComparator taskComparator = new TaskComparator(now);
        tasks.sort(taskComparator);
    }//sorting the tasks in the list from the closest task to the current timestamp.

    


    @Override
public String toString(){
    return String.format(
        "<html><b>Task:</b> %s<br><b>Created:</b> %s<br><b>Date:</b> %s<br><b>Description:</b> %s<br><b>Deadline:</b> %s<br><b>Status:</b> %s</html>",
        getTitle(), getDateCreated().format(formatter), getDate(), getDescription(), getDeadline(), statusTask
    );
}
}