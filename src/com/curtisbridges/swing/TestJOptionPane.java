package com.curtisbridges.swing;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TestJOptionPane {
    private boolean isShown = false;
    private JFrame  frame;

    public TestJOptionPane() {
        JFrame frame = new JFrame("Test JOptionPane");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    public void showDialog(final String title) {
        synchronized (JOptionPane.class) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        if (!TestJOptionPane.this.isShown) {
                            TestJOptionPane.this.isShown = true;
                            JOptionPane.showMessageDialog(frame, "Message: " + title);
                        }
                    }
                });
            }
            catch (Exception exc) {
                System.out.println("Interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        final TestJOptionPane pane = new TestJOptionPane();

        for (int index = 0; index < 3; index++) {
            final int which = index;

            new Thread(new Runnable() {
                public void run() {
                    pane.showDialog(Integer.toString(which));
                }
            }).start();
        }
    }
}
