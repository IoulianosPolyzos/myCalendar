/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author user
 */
public class TaskListPanel extends JPanel {
    private JList<Task> list; //Create a Jlist

    public TaskListPanel(ArrayList<Task> data) {
        super(new BorderLayout());

        ArrayListModel<Task> model = new ArrayListModel<>(data);
        list = new JList<>(model);
        list.addListSelectionListener(new SelectionHandler());//Added to the Jlist to watch user options
        JScrollPane jsp = new JScrollPane(list);//Create Scroll Panel to help user to scroll up and down all events that are displayed in the window
        this.add(jsp, BorderLayout.CENTER);
    }
    

    private class SelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {// Check if the event is not in the process of adjustment
                Event selectedEvent = list.getSelectedValue();// Retrieve the selected Event from the JList
                if (selectedEvent != null) {
                    System.out.println(selectedEvent.getTitle());// Print the title of the selected Event to the console
                }
            }
        }
    }
    void display() {
        JFrame f = new JFrame("ListPanel");//Create and display a frame
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(this);//Add list panel to frame
        f.pack();//Adjust the size of the window 
        f.setLocationRelativeTo(null);//Set the position of the window at the center of the screen
        f.setVisible(true);//Set the window visible to the user
    }
}  
    
    