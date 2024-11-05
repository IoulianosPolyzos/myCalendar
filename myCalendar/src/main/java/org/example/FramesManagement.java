/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static org.example.Event.*;
import static org.example.Task.tasks;


/**
 *
 * @author user
 */
public class FramesManagement {
       
    protected static JFrame frame;
    private static boolean isValidFile = false;//Boolean variable to check if the loaded file is valid
    static JFileChooser fileChooser = new JFileChooser();
    static File selectedFile = fileChooser.getSelectedFile();//File to be loaded
    static String filePath;
    
    
    TimeTeller teller = TimeService.getTeller();
    LocalDateTime now = teller.now();


    static Event event = new Event(null, null, null, null);
    Task task = new Task(null, null, null, null, null, null);
    Appointment appointment = new Appointment(null, null, null, null, null);
    public void createAndShowGUI() {
    SwingUtilities.invokeLater(() -> {
       frame = new JFrame("CALENDAR");//Create a window
        frame.setSize(800, 800);//Set the size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        frame.setResizable(false);

      

        frame.setLocationRelativeTo(null); 

        JMenuBar menuBar = new JMenuBar();//Main Menu
        JMenu filesMenu = new JMenu("Files");

        JMenuItem newMenuItem = new JMenuItem("New");//"New" button is to create a new ical file
        JMenuItem loadMenuItem = new JMenuItem("Load");//"Load" button is to load a ical file




        // Νέα υπο-επιλογή μενού για το "Add"
        JMenu addSubMenu = new JMenu("Add");//"Add" button to add a task or an appointment

        JMenu updateSubMenu = new JMenu("Update");//"Update" button


        filesMenu.add(newMenuItem);
        filesMenu.add(loadMenuItem);


        menuBar.add(filesMenu);//Add "Files" button to main menu

        
        menuBar.add(addSubMenu);//Add "Add" button to main menu
        menuBar.add(updateSubMenu);//Add "Update" button to main menu

        
        JMenuItem taskMenuItem = new JMenuItem("Task");//Add a task
        JMenuItem appointmentMenuItem = new JMenuItem("Appointment");//Add an appointment

        JMenuItem updateTitle = new JMenuItem("Title");//Button to update the Title of an event
        JMenuItem updateDate = new JMenuItem("Date");//Button to update Date of an event
        JMenuItem updateDescription = new JMenuItem("Description");//Button to update the Description of an event
        JMenuItem updateDuration = new JMenuItem("Duration");//Button to update the Duration of an event
        JMenuItem updateDeadline = new JMenuItem("Deadline");//Button to update the Deadline of an event
        JMenuItem updateStatus = new JMenuItem("Status");//Button to update the Status of an event

        //Add the above buttons to "Update" button
        updateSubMenu.add(updateTitle);
        updateSubMenu.add(updateDate);
        updateSubMenu.add(updateDescription);
        updateSubMenu.add(updateDuration);
        updateSubMenu.add(updateDeadline);
        updateSubMenu.add(updateStatus);

        //Add the "Task" and "Appointment" button to "Add" button
        addSubMenu.add(taskMenuItem);
        addSubMenu.add(appointmentMenuItem);
        JMenuItem displayMenuItem = new JMenu("Display");//"Display" button is used to display a list of events

        JMenuItem dayMenuItem = new JMenuItem("Day");//"Day" button is to display the events that are going to happen until the end of the day
        JMenuItem weekMenuItem = new JMenuItem("Week");//"Week" button is to dipslay the events that are going to happen until the end of the week
        JMenuItem monthMenuItem = new JMenuItem("Month");//"Month" button is to display the events that are going to happen until the end of the month
        JMenuItem pastDayMenuItem = new JMenuItem("Past Day");//"Past day" button is to display the events from the beginning of the day until now
        JMenuItem pastWeekMenuItem = new JMenuItem("Past Week");//"Past Week" button is to display the events from the beginning of the week until now
        JMenuItem pastMonthMenuItem = new JMenuItem("Past Month");////"Past Month" button is to display the events from the beginning of the month until now
        JMenuItem todoMenuItem = new JMenuItem("To-Do");//"To-Do" button is to display the incomplete tasks that  have not passed the deadline
        JMenuItem dueMenuItem = new JMenuItem("Due");//"Due" button is to display the incomplete tasks that have passed the deadline
        JMenuItem allEventsMenuItem = new JMenuItem("All Events");//"All Events " button is to display all events that are found in the ical file

        //Add above buttons to "Display" button
        displayMenuItem.add(dayMenuItem);
        displayMenuItem.add(weekMenuItem);
        displayMenuItem.add(monthMenuItem);
        displayMenuItem.add(pastDayMenuItem);
        displayMenuItem.add(pastWeekMenuItem);
        displayMenuItem.add(pastMonthMenuItem);
        displayMenuItem.add(todoMenuItem);
        displayMenuItem.add(dueMenuItem);
        displayMenuItem.add(allEventsMenuItem);

        menuBar.add(displayMenuItem);

        frame.setJMenuBar(menuBar);

        allEventsMenuItem.addActionListener(new ActionListener() {//"All events" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                    
                    EventQueue.invokeLater(() -> {
                       event.sortEventsByDate(events,now);//Sort the events to the current time
                       new ListPanel(events).display();
                    


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        dayMenuItem.addActionListener(new ActionListener() {//"Day" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> futureEventsDay = event.FutureEventsDay();
                        event.sortEventsByDate(futureEventsDay,now);//Sort the events to the current time
                        new ListPanel(futureEventsDay).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        weekMenuItem.addActionListener(new ActionListener() {//"Week" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                  
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> futureEventsWeek = event.FutureEventsWeek();
                        event.sortEventsByDate(futureEventsWeek,now);//Sort the events to the current time
                        new ListPanel(futureEventsWeek).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        monthMenuItem.addActionListener(new ActionListener() {//"Month" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
               
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> futureEventsMonth = event.FutureEventsMonth();
                        event.sortEventsByDate(futureEventsMonth,now);//Sort the events to the current time
                        new ListPanel(futureEventsMonth).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        pastDayMenuItem.addActionListener(new ActionListener() {//"Past Day" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                   
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> pastEventsDay = event.PastEventsDay();
                        event.sortEventsByDate(pastEventsDay,now);//Sort the events to the current time
                        new ListPanel(pastEventsDay).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        pastWeekMenuItem.addActionListener(new ActionListener() {//"Past Week" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                   
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> pastEventsWeek = event.PastEventsWeek();
                        event.sortEventsByDate(pastEventsWeek,now);//Sort the events to the current time
                        new ListPanel(pastEventsWeek).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        pastMonthMenuItem.addActionListener(new ActionListener() {//"Past Month" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                    
                    EventQueue.invokeLater(() -> {
                        ArrayList<Event> pastEventsMonth = event.PastEventsMonth();
                        event.sortEventsByDate(pastEventsMonth,now);//Sort the events to the current time
                        new ListPanel(pastEventsMonth).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        todoMenuItem.addActionListener(new ActionListener() {//"To-Do" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                    
                    EventQueue.invokeLater(() -> {
                        ArrayList<Task> ToDoTasks = task.ToDoTasks();
                        task.sortTasksByDate(ToDoTasks,now);//Sort tasks to the current time
                        new TaskListPanel(ToDoTasks).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        dueMenuItem.addActionListener(new ActionListener() {//"Due" button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {//Check if the loaded file is valid
                
                    EventQueue.invokeLater(() -> {
                         ArrayList<Task> DueTasks = task.DueTasks();
                         task.sortTasksByDate(DueTasks,now);//Sort tasks to the current time
                        new TaskListPanel(DueTasks).display();


                    });
                } else {//If the file is not valid display a message
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot display Day View.");
                }
            }
        });

        newMenuItem.addActionListener(new ActionListener() {//"New" button to create an ical file
            @Override
            public void actionPerformed(ActionEvent e) {
             
                 filePath = JOptionPane.showInputDialog(frame, "Enter file name:");//Name of the ical file

                if (filePath != null && !filePath.trim().isEmpty()) {
                    
                    File newFile = new File(filePath);

                    try {
                        if (newFile.createNewFile()) {
                            JOptionPane.showMessageDialog(frame, "New file created successfully!");
                            Event.AddAtStart(filePath);//Write 4 lines at start of the ical file ("BEGIN:VCALENDAR","VERSION:2.0","PRODID:-//Office Holidays Ltd.//EN","CALSCALE:GREGORIAN")
                            Event.AppendIcal(filePath);//Write line "END:VCALENDAR" at the end of the ical file
                            isValidFile = IsValidIcal(filePath);

                        } else {
                            JOptionPane.showMessageDialog(frame, "File already exists. Please choose a different name.");//If file already exists display a message
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error creating the new file.");
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid file name. Please try again.");
                }
            }
        });

        loadMenuItem.addActionListener(new ActionListener() {//"Load" button
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                     selectedFile = fileChooser.getSelectedFile();
                     filePath = selectedFile.getAbsolutePath();//Get full path of the selected file

                    isValidFile = IsValidIcal(filePath); 

                    if (isValidFile) {
                        JOptionPane.showMessageDialog(frame, "File is valid!");
                        addSubMenu.setEnabled(true);  
                    } else {
                        JOptionPane.showMessageDialog(frame, "File is not valid!");
                        addSubMenu.setEnabled(false);  
                    }
                }
            }
        });

       updateTitle.addActionListener(new ActionListener() {//Button to update the Title of an event
           @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    event.changeEventTitle(filePath);
                    
                }
            }
       });
       updateDescription.addActionListener(new ActionListener(){//Button to update the Description of an event
        @Override 
        public void actionPerformed (ActionEvent e){
           if(isValidFile){
               event.changeEventDescription(filePath);
               
           }
       }
           
    });

       updateDeadline.addActionListener(new ActionListener() {//Button to update the Deadline of a task
           @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    task.changeTaskDeadline(filePath);
                    
                }
            }
       });

       updateDuration.addActionListener(new ActionListener() {//Button to update the Duration of an appointment
           @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    appointment.changeAppointmentDuration(filePath);
                    
                }
            }
       });

       updateDate.addActionListener(new ActionListener() {//Button to update the Date of an event
           @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    event.changeEventDate(filePath);
                    
                }
            }
       });

       updateStatus.addActionListener(new ActionListener(){//Button to update the Status of a task
           @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    task.changeTaskStatus(filePath);
                    
                }
            }
       });


        
        taskMenuItem.addActionListener(new ActionListener() {//Button to add a task
            @Override
            public void  actionPerformed(ActionEvent e) {
                if (isValidFile) {
                    task.addTask();//Call addTask method 

                   
                    Event.RemoveLineFromFile(filePath, "END:VCALENDAR");//Remove last line from ical file
                    Event.saveIcalFile(task, filePath);//Save ical file
                    Event.AppendIcal(filePath); //Add "END:VCALENDAR" at the end of the ical file

                    
                } else {
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot add task.");
                }

            }
        });

        appointmentMenuItem.addActionListener(new ActionListener() {//Button to add an appointment
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidFile) {
                   

                    appointment.addAppointment();//Call addAppointment method
                    Event.RemoveLineFromFile(filePath, "END:VCALENDAR"); //Remove last line from ical file
                    Event.saveIcalFile(appointment, filePath); //Save ical file
                    Event.AppendIcal(filePath); //Add "END:VCALENDAR" at the end of the ical file


                    
                } else {
                    JOptionPane.showMessageDialog(frame, "File is not valid. Cannot add appointment.");
                }

            }
        });


        updateSubMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (events.isEmpty() && tasks.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please load or create a file first!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Updating event or task...");
                }
            }
        });


        ArrayList<LocalDateTime> deadlinesAndTimes = new ArrayList<>();

        
        for (Task task : tasks) {
            deadlinesAndTimes.add(task.getDeadline());
        }

        
        for (Event event : events) {
            deadlinesAndTimes.add(event.getDate());
        }



        frame.setVisible(true);

    });

  }


  public static void showEventNotification() {
        JOptionPane.showMessageDialog(frame, "You Have an EVENT now!", "Reminder!", JOptionPane.INFORMATION_MESSAGE);
  }

}

