package com.lwx.nn;

import com.lwx.nn.view.MainFrame;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFun {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(MainFun.class.getName()).log(Level.FINE,e.getMessage());
            e.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame();
    }
}