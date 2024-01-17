package com.jasmineenriquez.casestudy;


import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class CustomSpinner extends JSpinner {
    
    public CustomSpinner(SpinnerModel model) {
        super(model);
        setUI(CustomSpinnerUI.createUI(this));
    }
    
    public static class CustomSpinnerUI extends BasicSpinnerUI {
        private Color backgroundColor = new Color(133, 88, 111);
        private Color textColor = Color.WHITE;
        private Color arrowColor = Color.WHITE;
        private Color selectionColor = new Color(178, 140, 151);
        
        public static ComponentUI createUI(JComponent c) {
            return new CustomSpinnerUI();
        }
        
        public void installUI(JComponent c) {
            super.installUI(c);
            JSpinner spinner = (JSpinner) c;
            spinner.setOpaque(false);
            spinner.setBackground(backgroundColor);
            spinner.setForeground(textColor);
            spinner.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            spinner.setFont(spinner.getFont().deriveFont(Font.BOLD));
            
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                JTextField textField = defaultEditor.getTextField();
                textField.setEditable(false);
                textField.setBackground(backgroundColor);
                textField.setHighlighter(null);
                textField.setForeground(textColor);
                textField.setCaretColor(textColor);
            }
            
            spinner.setFocusable(false);
        }
        
        
        @Override
        protected Component createNextButton() {
            JButton nextButton = new BasicArrowButton(BasicArrowButton.NORTH, new Color(133, 88, 111), Color.WHITE, Color.WHITE, Color.WHITE) {
                @Override
                public void paint(Graphics g) {
                    // Paint only the arrow without the box
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paint(g);
                }
            };
            nextButton.setBorder(BorderFactory.createEmptyBorder());
            nextButton.setFocusPainted(false);
            nextButton.setFocusable(false);
            
            // Add ActionListener to increment the spinner value
            nextButton.addActionListener(e -> {
                Object value = spinner.getNextValue();
                if (value != null) {
                    spinner.setValue(value);
                }
            });
            
            return nextButton;
        }
        
        @Override
        protected Component createPreviousButton() {
            JButton previousButton = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(133, 88, 111), Color.WHITE, Color.WHITE, Color.WHITE) {
                @Override
                public void paint(Graphics g) {
                    // Paint only the arrow without the box
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paint(g);
                }
            };
            previousButton.setBorder(BorderFactory.createEmptyBorder());
            previousButton.setFocusPainted(false);
            previousButton.setFocusable(false);
            
            // Add ActionListener to decrement the spinner value
            previousButton.addActionListener(e -> {
                Object value = spinner.getPreviousValue();
                if (value != null) {
                    spinner.setValue(value);
                }
            });
            
            return previousButton;
        }
        
        
        @Override
        public void paint(Graphics g, JComponent c) {
            JSpinner spinner = (JSpinner) c;
            if (spinner.isEnabled()) {
                g.setColor(backgroundColor);
                g.fillRect(0, 0, spinner.getWidth(), spinner.getHeight());
                super.paint(g, c);
            } else {
                g.setColor(backgroundColor);
                g.fillRect(0, 0, spinner.getWidth(), spinner.getHeight());
            }
        }
        
        @Override
        protected void installListeners() {
            super.installListeners();
            spinner.addChangeListener(e -> {
                JComponent editor = spinner.getEditor();
                if (editor instanceof JSpinner.DefaultEditor) {
                    JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                    defaultEditor.getTextField().setForeground(textColor);
                }
            });
        }
    }
}

