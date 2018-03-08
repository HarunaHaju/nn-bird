package com.lwx.nn.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Runnable {

    private GamePad gamePad;

    private JPanel panel_btn;
    private JButton btn_start;
    private JButton btn_pause;
    private JButton btn_restart;

    private boolean isRunning;

    private Thread thread;
    private static final int THREAD_TIME = 15;

    public MainFrame(){
        thread = new Thread(this);
        isRunning = true;
        setUI();
        thread.start();
    }

    private void setUI(){
        gamePad = new GamePad();

        panel_btn = new JPanel();
        btn_start = new JButton("Start");
        btn_start.addActionListener(e->this.methodStart());

        btn_pause = new JButton("Pause");
        btn_pause.addActionListener(e->this.methodPause());

        btn_restart = new JButton("Restart");
        btn_restart.addActionListener(e->this.methodRestart());

        panel_btn.setSize(432,70);
        panel_btn.setBackground(Color.black);
        panel_btn.add(btn_start);
        panel_btn.add(btn_pause);
        panel_btn.add(btn_restart);

        this.setLayout(new BorderLayout());
        this.add(gamePad,BorderLayout.CENTER);
        this.add(panel_btn,BorderLayout.SOUTH);

        this.setSize(432, 714);
        this.setTitle("NeuralBird by lwx");

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation(width / 2 - 216, height / 2 - 307);
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void methodStart(){
        if (!isRunning){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    private void methodPause(){
        isRunning = false;
    }
    private void methodRestart(){
        gamePad.resetAll();
        this.repaint();
        methodStart();
    }

    @Override
    public void run() {
        long timer = 0;
        while (isRunning){
            timer = System.currentTimeMillis();
            this.repaint();
            gamePad.logicStep();
            timer = System.currentTimeMillis() - timer;
            if (THREAD_TIME - timer > 0L) {
                try {
                    Thread.sleep(THREAD_TIME - timer);
                } catch (Exception e) {
                    isRunning = false;
                    e.printStackTrace();
                }
            }
        }
    }
}