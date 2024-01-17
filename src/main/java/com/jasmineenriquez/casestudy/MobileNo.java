
package com.jasmineenriquez.casestudy;


import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MobileNo extends JFrame {
    
    public void textFieldKeyTyped(java.awt.event.KeyEvent evt) {
        JTextField textField = (JTextField) evt.getSource();
        if (textField.getCaretPosition() <= 3) {
            textField.setCaretPosition(3);
        }
    }
    
    public  class NumericDocument extends PlainDocument {
        private static final int MAX_LENGTH = 13; // "+63" + 10 digits
        
        @Override
        public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
            if (offset < 3) {
                super.insertString(offset, text, attr);
            } else if (offset >= 3 && offset < MAX_LENGTH) {
                int length = Math.min(MAX_LENGTH - offset, text.length());
                String filteredText = getFilteredText(text.substring(0, length));
                super.insertString(offset, filteredText, attr);
            }
        }
        
        private String getFilteredText(String text) {
            StringBuilder filteredText = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isDigit(c)) {
                    filteredText.append(c);
                }
            }
            return filteredText.toString();
        }
        
        @Override
        public void remove(int offset, int length) throws BadLocationException {
            int docLength = getLength();
            if (offset < 3) {
                return;
            } else if (offset >= 3 && offset < docLength) {
                int deleteLength = Math.min(length, docLength - offset);
                super.remove(offset, deleteLength);
            }
        }
    }
    
    
}