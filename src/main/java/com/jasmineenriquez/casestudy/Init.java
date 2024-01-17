
package com.jasmineenriquez.casestudy;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jasmine Enriquez
 */
public class Init {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // Set the look and feel to the Windows look and feel
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        Splash splash = new Splash();
        Index index = new Index();
        splash.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(100);
                splash.jProgressBar1.setValue(i);
                if (i == 100) {
                    splash.dispose();
                    index.setVisible(true);
                }
            }
        } catch (Exception e) {
            
        }
        
    }
}
