/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

/**
 *
 * @author user
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;

// Custom ArrayListModel that extends AbstractListModel.
// This class is used to provide a custom data model for JList components.

public class ArrayListModel<E> extends AbstractListModel<E> {

    private ArrayList<E> data;//Create an arraylist

    public ArrayListModel(ArrayList<E> data) {
        this.data = data;//Initialize the ArrayListModel with data.The ArrayList containing elements of type E.
    }

    @Override
    public int getSize() {
        return data.size();//Get the size of the data model
    }

    @Override
    public E getElementAt(int index) {
        return data.get(index);//Gets the element at the specified index in the data model.
    }
}
