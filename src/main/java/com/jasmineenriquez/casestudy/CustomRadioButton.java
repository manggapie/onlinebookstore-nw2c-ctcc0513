package com.jasmineenriquez.casestudy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomRadioButton extends JComponent {
    private boolean selected;
    private ActionListener listener;
    
    public CustomRadioButton() {
        selected = false;
        setPreferredSize(new Dimension(20, 20));
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSelected(!isSelected());
                if (listener != null) {
                    listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "selected"));
                }
            }
        });
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    
    public void addActionListener(ActionListener listener) {
        this.listener = listener;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Outer Circle
        Color outerColor = new Color(178, 140, 151);
        g2d.setColor(outerColor);
        g2d.fillOval(x, y, diameter, diameter);
        
        if (isSelected()) {
            // Inner Circle
            int innerDiameter = diameter / 2;
            int innerX = (getWidth() - innerDiameter) / 2;
            int innerY = (getHeight() - innerDiameter) / 2;
            
            Color innerColor = new Color(133, 88, 111);
            g2d.setColor(innerColor);
            g2d.fillOval(innerX, innerY, innerDiameter, innerDiameter);
        } else {
            // Inner Circle (Center)
            int innerDiameter = diameter / 3;
            int innerX = (getWidth() - innerDiameter) / 2;
            int innerY = (getHeight() - innerDiameter) / 2;
            
            g2d.setColor(Color.WHITE);
            g2d.fillOval(innerX, innerY, innerDiameter, innerDiameter);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Radio Button");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            CustomRadioButton radioButton = new CustomRadioButton();
            radioButton.setSelected(true);
            
            radioButton.addActionListener(e -> {
                if (radioButton.isSelected()) {
                    System.out.println("Selected");
                    // Perform any desired action when the radio button is selected
                } else {
                    System.out.println("Deselected");
                    // Perform any desired action when the radio button is deselected
                }
            });
            
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(radioButton);
            frame.pack();
            frame.setVisible(true);
        });
    }
}