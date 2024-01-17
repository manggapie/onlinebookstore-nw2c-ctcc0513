
package com.jasmineenriquez.casestudy;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CustomComboBox extends JComboBox<String> {
    private Color backgroundColor = new Color(133, 88, 111);
    private Color textColor = Color.WHITE;
    private Color arrowColor = Color.WHITE;
    private Color selectionColor = new Color(178, 140, 151);
    
    public CustomComboBox(String[] items) {
        super(items);
        setUI(CustomComboBoxUI.createUI(this));
    }
    
    public static class CustomComboBoxUI extends BasicComboBoxUI {
        private Color backgroundColor = new Color(133, 88, 111);
        private Color textColor = Color.WHITE;
        private Color arrowColor = Color.WHITE;
        private Color selectionColor = new Color(178, 140, 151);
        
        public static ComponentUI createUI(JComponent c) {
            return new CustomComboBoxUI();
        }
        
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JComboBox<?> comboBox = (JComboBox<?>) c;
            comboBox.setOpaque(true);
            comboBox.setBackground(backgroundColor);
            comboBox.setForeground(textColor);
            comboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            comboBox.setFont(comboBox.getFont().deriveFont(Font.BOLD));
            comboBox.setFocusable(false);
        }
        
        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = new BasicArrowButton(BasicArrowButton.SOUTH, backgroundColor, arrowColor, arrowColor, arrowColor) {
                @Override
                public void paint(Graphics g) {
                    // Paint only the arrow without the box
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paint(g);
                }
            };
            arrowButton.setBorder(BorderFactory.createEmptyBorder());
            arrowButton.setFocusPainted(false);
            arrowButton.setFocusable(false);
            return arrowButton;
        }
        
        @Override
        protected ListCellRenderer<Object> createRenderer() {
            return new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    renderer.setForeground(textColor);
                    renderer.setBackground(backgroundColor);
                    if (isSelected || cellHasFocus) {
                        renderer.setBackground(selectionColor);
                    }
                    return renderer;
                }
            };
        }
        
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Remove the highlighting of the selected item
        }
    }
}

