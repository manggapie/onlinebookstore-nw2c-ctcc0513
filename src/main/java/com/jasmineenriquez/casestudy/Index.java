
package com.jasmineenriquez.casestudy;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.Calendar;

/**
 *
 * @author Jasmine Enriquez
 */
public class Index extends javax.swing.JFrame {
    Point initialClick;
    private boolean isSelected = false;
    private boolean isSelected2 = false;
    private boolean isSelected3 = false;
    private static JFrame instance;
    /**
     * Creates new form Login
     */
    public Index() {
        initComponents();
        instance = this;
        
        // Create a document filter to limit the length of the text field
        DocumentFilter usernameFilter = new DocumentFilter() {
            int maxLength = 10;
            
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Check if the resulting text length exceeds the maximum length
                if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Check if the resulting text length exceeds the maximum length
                if ((fb.getDocument().getLength() + text.length() - length) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
        
// Set the document filter to the usernameF JTextField
((AbstractDocument) usernameF.getDocument()).setDocumentFilter(usernameFilter);
((AbstractDocument) userlogin.getDocument()).setDocumentFilter(usernameFilter);

minimizeBtn.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        getInstance().setState(Frame.ICONIFIED); // Minimize the frame
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (getInstance().getExtendedState() != Frame.ICONIFIED) {
            // Set the entered icon only if the frame is not minimized
            minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimizeentered.png"))); // NOI18N
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimize.png")));
    }
});

// Add a WindowFocusListener to update the icon when the frame gains or loses focus
this.addWindowFocusListener(new WindowFocusListener() {
    @Override
    public void windowGainedFocus(WindowEvent e) {
        if (getInstance().getExtendedState() != Frame.ICONIFIED) {
            // Set the original icon when the frame gains focus
            minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimize.png")));
        }
    }
    
    @Override
    public void windowLostFocus(WindowEvent e) {
        // No action needed
    }
});

// Create an Action for the login action
Action loginAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        login();
        
        // Simulate Mouse Entered event
        MouseEvent enteredEvent = new MouseEvent(loginButton, MouseEvent.MOUSE_ENTERED,
                System.currentTimeMillis(), 0, 0, 0, 0, false);
        loginButton.dispatchEvent(enteredEvent);
        
        // Check if JOptionPane is displayed
        if (!isJOptionPaneDisplayed()) {
            // Delay the execution of Mouse Exited event simulation
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform actions only if the mouse is not inside the loginButton
                    
                    // Simulate Mouse Exited event
                    MouseEvent exitEvent = new MouseEvent(loginButton, MouseEvent.MOUSE_EXITED,
                            System.currentTimeMillis(), 0, 0, 0, 0, false);
                    loginButton.dispatchEvent(exitEvent);
                    
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
};

// Get the input map of the parent container that holds the login button
InputMap inputMap = ((JComponent) loginButton.getParent()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "login");

// Get the action map of the parent container that holds the login button
ActionMap actionMap = ((JComponent) loginButton.getParent()).getActionMap();
actionMap.put("login", loginAction);

// Create a flag to track mouse entered state


// Add MouseListener to the login button
loginButton.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        login();
        // Check if JOptionPane is displayed
        if (!isJOptionPaneDisplayed()) {
            // Delay the execution of Mouse Exited event simulation
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform actions only if the mouse is not inside the loginButton
                    
                    // Simulate Mouse Exited event
                    MouseEvent exitEvent = new MouseEvent(loginButton, MouseEvent.MOUSE_EXITED,
                            System.currentTimeMillis(), 0, 0, 0, 0, false);
                    loginButton.dispatchEvent(exitEvent);
                    
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    };
    
    @Override
    public void mouseEntered(MouseEvent e) {
        enableHighLight(loginButton);
    
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        reset(loginButton);
        
    }
});
// Set the login button to be focusable
loginButton.setFocusable(true);

FocusTraversalUtils.setCustomFocusTraversalPolicy(loginButton.getParent(),
        new Component[]{userlogin, passlogin});

loginPane.addComponentListener(new ComponentAdapter() {
    @Override
    public void componentShown(ComponentEvent e) {
        userlogin.requestFocus();
        loginButton.setFocusable(true);
        
    }
});



//for Sign Up
// Create an Action for the signup action
Action signUpAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        signUp();
        
        
        // Simulate Mouse Entered event
        MouseEvent enteredEvent = new MouseEvent(signUp, MouseEvent.MOUSE_ENTERED,
                System.currentTimeMillis(), 0, 0, 0, 0, false);
        signUp.dispatchEvent(enteredEvent);
        
        // Check if JOptionPane is displayed
        if (!isJOptionPaneDisplayed()) {
            // Delay the execution of Mouse Exited event simulation
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform actions only if the mouse is not inside the loginButton
                    
                    // Simulate Mouse Exited event
                    MouseEvent exitEvent = new MouseEvent(signUp, MouseEvent.MOUSE_EXITED,
                            System.currentTimeMillis(), 0, 0, 0, 0, false);
                    signUp.dispatchEvent(exitEvent);
                    
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
};


// Get the input map of the parent container that holds the login button
InputMap inputMap1 = ((JComponent) signUp.getParent()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
inputMap1.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "signUp");

// Get the action map of the parent container that holds the login button
ActionMap actionMap1 = ((JComponent) signUp.getParent()).getActionMap();
actionMap1.put("signUp", signUpAction);

// Create a flag to track mouse entered state


// Add MouseListener to the login button
signUp.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        signUp();
        
        // Check if JOptionPane is displayed
        if (!isJOptionPaneDisplayed()) {
            // Delay the execution of Mouse Exited event simulation
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform actions only if the mouse is not inside the loginButton
                    
                    // Simulate Mouse Exited event
                    MouseEvent exitEvent = new MouseEvent(signUp, MouseEvent.MOUSE_EXITED,
                            System.currentTimeMillis(), 0, 0, 0, 0, false);
                    signUp.dispatchEvent(exitEvent);
                    
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    };
    
    @Override
    public void mouseEntered(MouseEvent e) {
        enableHighLight(signUp);
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        reset(signUp);
       
    }
});
// Set the signUp button to be focusable
signUp.setFocusable(true);

// Add KeyListener to each text field
usernameF.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            signUp();
            
            // Simulate Mouse Entered event
            MouseEvent enteredEvent = new MouseEvent(signUp, MouseEvent.MOUSE_ENTERED,
                    System.currentTimeMillis(), 0, 0, 0, 0, false);
            signUp.dispatchEvent(enteredEvent);
            
            // Check if JOptionPane is displayed
            if (!isJOptionPaneDisplayed()) {
                // Delay the execution of Mouse Exited event simulation
                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform actions only if the mouse is not inside the loginButton
                        
                        // Simulate Mouse Exited event
                        MouseEvent exitEvent = new MouseEvent(signUp, MouseEvent.MOUSE_EXITED,
                                System.currentTimeMillis(), 0, 0, 0, 0, false);
                        signUp.dispatchEvent(exitEvent);
                        
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            
            
            
        }
    }
});

emailF.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            signUp();
            
            // Simulate Mouse Entered event
            MouseEvent enteredEvent = new MouseEvent(signUp, MouseEvent.MOUSE_ENTERED,
                    System.currentTimeMillis(), 0, 0, 0, 0, false);
            signUp.dispatchEvent(enteredEvent);
            
            // Check if JOptionPane is displayed
            if (!isJOptionPaneDisplayed()) {
                // Delay the execution of Mouse Exited event simulation
                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform actions only if the mouse is not inside the loginButton
                        
                        // Simulate Mouse Exited event
                        MouseEvent exitEvent = new MouseEvent(signUp, MouseEvent.MOUSE_EXITED,
                                System.currentTimeMillis(), 0, 0, 0, 0, false);
                        signUp.dispatchEvent(exitEvent);
                        
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            
            
            
        }
    }
});

birthdayF.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            signUp();
            
            // Simulate Mouse Entered event
            MouseEvent enteredEvent = new MouseEvent(signUp, MouseEvent.MOUSE_ENTERED,
                    System.currentTimeMillis(), 0, 0, 0, 0, false);
            signUp.dispatchEvent(enteredEvent);
            
            // Check if JOptionPane is displayed
            if (!isJOptionPaneDisplayed()) {
                // Delay the execution of Mouse Exited event simulation
                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform actions only if the mouse is not inside the loginButton
                        
                        // Simulate Mouse Exited event
                        MouseEvent exitEvent = new MouseEvent(signUp, MouseEvent.MOUSE_EXITED,
                                System.currentTimeMillis(), 0, 0, 0, 0, false);
                        signUp.dispatchEvent(exitEvent);
                        
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            
            
            
        }
    }
});

// Set custom focus traversal policy for the frame's content pane
FocusTraversalUtils.setCustomFocusTraversalPolicy(signUp.getParent(),
        new Component[]{usernameF, emailF,birthdayF,passF,cPassF,signUp});
//SwingUtilities.invokeLater(() -> {
//    usernameField.requestFocus();
//});

signUpPane.addComponentListener(new ComponentAdapter() {
    @Override
    public void componentShown(ComponentEvent e) {
        usernameF.requestFocus();
        
    }
});



TextPrompt birthday = new TextPrompt("mm/dd/yyyy", birthdayF,false);
birthday.setForeground(new java.awt.Color(178, 140, 151));
birthday.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
birthdayF.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String text = birthdayF.getText();
        
        if (text.length() >= 10 || !(Character.isDigit(c) || c == '/')) {
            e.consume();  // Ignore the event
            return;
        }
        
        if (Character.isDigit(c)) {
            if (text.length() == 2 || text.length() == 5) {
                text += "/";
                birthdayF.setText(text);
            } else if (text.length() == 10) {
                e.consume();  // Ignore the event if the year is complete
            }
        } else if (c == '/') {
            if (text.length() != 2 && text.length() != 5) {
                e.consume();  // Ignore the event
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String text = birthdayF.getText();
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                birthdayF.setText(text);
            }
        }
    }
});


        passlogin.setEchoChar('•');
        passF.setEchoChar('•');
        cPassF.setEchoChar('•');
        TextPrompt pass = new TextPrompt("Password", passlogin,false);
        pass.setForeground(new java.awt.Color(178, 140, 151));
        pass.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

        TextPrompt name = new TextPrompt("Username", usernameF,false);
        name.setForeground(new java.awt.Color(178, 140, 151));
        name.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

        TextPrompt email = new TextPrompt("Gmail", emailF,false);
        email.setForeground(new java.awt.Color(178, 140, 151));
        email.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

        TextPrompt password = new TextPrompt("Password", passF,false);
        password.setForeground(new java.awt.Color(178, 140, 151));
        password.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

        TextPrompt cpassword = new TextPrompt("Confirm Password", cPassF,false);
        cpassword.setForeground(new java.awt.Color(178, 140, 151));
        cpassword.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));


        TextPrompt username = new TextPrompt("Username", userlogin,false);
        username.setForeground(new java.awt.Color(178, 140, 151));
        username.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

        userlogin.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        birthdayF.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
        usernameF.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
        emailF.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
        passlogin.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passF.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cPassF.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parent = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        loginPanel = new RoundedPanel(new java.awt.Dimension(45,45),0);
        loginLabel = new javax.swing.JLabel();
        signUpPanel = new RoundedPanel(new java.awt.Dimension(45,45),0);
        signupLabel = new javax.swing.JLabel();
        exitBtn = new javax.swing.JLabel();
        minimizeBtn = new javax.swing.JLabel();
        loginPane = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        loginButton = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        userlogin = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        passlogin = new javax.swing.JPasswordField();
        signUpPane = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        birthdayF = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        usernameF = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        emailF = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        passF = new javax.swing.JPasswordField();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cPassF = new javax.swing.JPasswordField();
        signUp = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        parent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                parentMouseDragged(evt);
            }
        });
        parent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                parentMousePressed(evt);
            }
        });
        parent.setLayout(new java.awt.CardLayout());

        mainPanel.setBackground(new java.awt.Color(247, 237, 227));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/logo.png"))); // NOI18N
        mainPanel.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 400, 110));

        loginPanel.setBackground(new java.awt.Color(133, 88, 111));
        loginPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginPanelMouseExited(evt);
            }
        });

        loginLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        loginLabel.setForeground(new java.awt.Color(255, 255, 255));
        loginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginLabel.setText("Login");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        mainPanel.add(loginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, 220, 40));

        signUpPanel.setBackground(new java.awt.Color(133, 88, 111));
        signUpPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpPanelMouseExited(evt);
            }
        });

        signupLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        signupLabel.setForeground(new java.awt.Color(255, 255, 255));
        signupLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signupLabel.setText("Sign Up");

        javax.swing.GroupLayout signUpPanelLayout = new javax.swing.GroupLayout(signUpPanel);
        signUpPanel.setLayout(signUpPanelLayout);
        signUpPanelLayout.setHorizontalGroup(
            signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(signupLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        signUpPanelLayout.setVerticalGroup(
            signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(signupLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        mainPanel.add(signUpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, -1));

        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/exit.png"))); // NOI18N
        exitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitBtnMouseExited(evt);
            }
        });
        mainPanel.add(exitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, -1, -1));

        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimize.png"))); // NOI18N
        minimizeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeBtnMouseExited(evt);
            }
        });
        mainPanel.add(minimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, -1, -1));

        parent.add(mainPanel, "main");

        loginPane.setBackground(new java.awt.Color(247, 237, 227));
        loginPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(133, 88, 111));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(133, 88, 111));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");
        loginPane.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 850, 150));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/return.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        loginPane.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        loginButton.setBackground(new java.awt.Color(133, 88, 111));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Login");
        loginButton.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 40));

        loginPane.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, 170, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/lock.png"))); // NOI18N
        loginPane.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 30, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/email.png"))); // NOI18N
        loginPane.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, -1, 40));

        jPanel2.setBackground(new java.awt.Color(223, 211, 195));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userlogin.setBackground(new java.awt.Color(223, 211, 195));
        userlogin.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        userlogin.setForeground(new java.awt.Color(133, 88, 111));
        userlogin.setBorder(null);
        userlogin.setPreferredSize(new java.awt.Dimension(80, 40));
        jPanel2.add(userlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));
        userlogin.getAccessibleContext().setAccessibleName("");

        loginPane.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 300, 40));

        jPanel3.setBackground(new java.awt.Color(223, 211, 195));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setBackground(new java.awt.Color(223, 211, 195));
        jLabel9.setForeground(new java.awt.Color(255, 255, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.setOpaque(true);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        passlogin.setBackground(new java.awt.Color(223, 211, 195));
        passlogin.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        passlogin.setForeground(new java.awt.Color(133, 88, 111));
        passlogin.setBorder(null);
        jPanel3.add(passlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        loginPane.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 300, 40));

        parent.add(loginPane, "login");

        signUpPane.setBackground(new java.awt.Color(247, 237, 227));
        signUpPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(223, 211, 195));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        birthdayF.setBackground(new java.awt.Color(223, 211, 195));
        birthdayF.setColumns(10);
        birthdayF.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        birthdayF.setForeground(new java.awt.Color(133, 88, 111));
        birthdayF.setBorder(null);
        birthdayF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthdayFActionPerformed(evt);
            }
        });
        jPanel4.add(birthdayF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 40));

        signUpPane.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 300, 40));

        jPanel5.setBackground(new java.awt.Color(223, 211, 195));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameF.setBackground(new java.awt.Color(223, 211, 195));
        usernameF.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        usernameF.setForeground(new java.awt.Color(133, 88, 111));
        usernameF.setBorder(null);
        usernameF.setPreferredSize(new java.awt.Dimension(80, 40));
        usernameF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFActionPerformed(evt);
            }
        });
        jPanel5.add(usernameF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));

        signUpPane.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 300, 40));

        jPanel6.setBackground(new java.awt.Color(223, 211, 195));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emailF.setBackground(new java.awt.Color(223, 211, 195));
        emailF.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        emailF.setForeground(new java.awt.Color(133, 88, 111));
        emailF.setBorder(null);
        emailF.setPreferredSize(new java.awt.Dimension(80, 40));
        emailF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFActionPerformed(evt);
            }
        });
        jPanel6.add(emailF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));

        signUpPane.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 300, 40));

        jPanel7.setBackground(new java.awt.Color(223, 211, 195));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setBackground(new java.awt.Color(223, 211, 195));
        jLabel10.setForeground(new java.awt.Color(255, 255, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setOpaque(true);
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        passF.setBackground(new java.awt.Color(223, 211, 195));
        passF.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        passF.setForeground(new java.awt.Color(133, 88, 111));
        passF.setBorder(null);
        jPanel7.add(passF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        signUpPane.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 300, 40));

        jPanel8.setBackground(new java.awt.Color(223, 211, 195));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setBackground(new java.awt.Color(223, 211, 195));
        jLabel11.setForeground(new java.awt.Color(255, 255, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        cPassF.setBackground(new java.awt.Color(223, 211, 195));
        cPassF.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        cPassF.setForeground(new java.awt.Color(133, 88, 111));
        cPassF.setBorder(null);
        jPanel8.add(cPassF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        signUpPane.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, 300, 40));

        signUp.setBackground(new java.awt.Color(133, 88, 111));
        signUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpMouseExited(evt);
            }
        });
        signUp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sign Up");
        signUp.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 40));

        signUpPane.add(signUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, 170, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/return.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        signUpPane.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/lock.png"))); // NOI18N
        signUpPane.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, 40));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/person.png"))); // NOI18N
        signUpPane.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/email.png"))); // NOI18N
        signUpPane.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, -1, 40));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/birthday.png"))); // NOI18N
        signUpPane.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, -1, 40));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/lock.png"))); // NOI18N
        signUpPane.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, -1, 40));

        jLabel17.setBackground(new java.awt.Color(133, 88, 111));
        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(133, 88, 111));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Sign Up");
        signUpPane.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 860, 150));

        parent.add(signUpPane, "signup");
        signUpPane.getAccessibleContext().setAccessibleParent(signUpPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(876, 514));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void loginPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginPanelMouseEntered
        enableHighLight(loginPanel);
    }//GEN-LAST:event_loginPanelMouseEntered
    // Check if a JOptionPane is currently displayed
    public boolean isJOptionPaneDisplayed() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof Frame || window instanceof Dialog) {
                Component focusedComponent = ((Window) window).getMostRecentFocusOwner();
                if (focusedComponent instanceof JOptionPane) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void loginPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginPanelMouseExited
        reset(loginPanel);
    }//GEN-LAST:event_loginPanelMouseExited
    
    private void exitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitBtnMouseClicked
    
    private void signUpPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpPanelMouseEntered
        enableHighLight(signUpPanel);
    }//GEN-LAST:event_signUpPanelMouseEntered
    
    private void signUpPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpPanelMouseExited
        reset(signUpPanel);
    }//GEN-LAST:event_signUpPanelMouseExited
    
    private void signUpPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpPanelMouseClicked
        
        CardLayout cardLayout = (CardLayout) parent.getLayout();
        cardLayout.show(parent, "signup");
        
    }//GEN-LAST:event_signUpPanelMouseClicked
    
    private void loginPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginPanelMouseClicked
        
        CardLayout cardLayout = (CardLayout) parent.getLayout();
        cardLayout.show(parent, "login");
        
    }//GEN-LAST:event_loginPanelMouseClicked
    public static JFrame getInstance() {
        return instance;
    }
    private void parentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parentMousePressed
        initialClick = evt.getPoint();
        getComponentAt(initialClick);
        
    }//GEN-LAST:event_parentMousePressed
    public static void insertUser(String username, String email, String birthday, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                String sql = "INSERT INTO users (username, password, email, profile_picture, birthday) VALUES (?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);
                
        //        byte[] profilePicture = getDefaultProfilePicture();
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);

        // Parse and format the birthday value
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = inputFormat.parse(birthday);
        String formattedBirthday = outputFormat.format(parsedDate);

        statement.setNull(4, Types.BLOB);
        //        statement.setBytes(4, profile);
        statement.setString(5, formattedBirthday);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("User inserted successfully!");
        } else {
            System.out.println("Failed to insert user.");
        }
            }
        } catch (SQLException e) {
            e.printStackTrace();
//} catch (IOException e) {
//    e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Invalid date format for birthday.");
        } finally {
            // Close the statement and connection
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Close the database connection
            DatabaseConnection.closeConnection();
        }
        
        
    }
    public static int loginUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = -1;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                String sql = "SELECT user_id FROM users WHERE BINARY username = ? AND BINARY password = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                
                resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                    System.out.println("Login successful! User ID: " + userId);
                } else {
                    System.out.println("Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DatabaseConnection.closeConnection();
        }
        
        return userId;
        
        
    }
    public static byte[] getDefaultProfilePicture() throws IOException {
        // Load the default image file
        InputStream is = Index.class.getResourceAsStream("/com/jasmineenriquez/casestudy/defaultpic.jpg");
        BufferedImage defaultImage = ImageIO.read(is);
        
        // Convert the image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(defaultImage, "jpg", baos);
        return baos.toByteArray();
    }
    private void parentMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parentMouseDragged
        // get location of Window
        int thisX = this.getLocation().x;
        int thisY = this.getLocation().y;
        
        // Determine how much the mouse moved since the initial click
        int xMoved = evt.getX() - initialClick.x;
        int yMoved = evt.getY() - initialClick.y;
        
        // Move window to this position
        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        this.setLocation(X, Y);
    }//GEN-LAST:event_parentMouseDragged
    
    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        CardLayout cardLayout = (CardLayout) parent.getLayout();
        cardLayout.show(parent, "main");
        passlogin.setText("");
        userlogin.setText("");
        isSelected = false;
        updateLabelState();
        
    }//GEN-LAST:event_jLabel3MouseClicked
    private void login() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (DatabaseConnection.isDatabaseConnected()) {
                    String username = userlogin.getText().trim().replaceAll("\\s+", "");
                    String password = passlogin.getText().trim().replaceAll("\\s+", "");
                    
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(getInstance(), "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                        
                    } else {
                        int loggedInId = loginUser(username, password);
                        if (loggedInId != -1) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    dispose();
                                    MainMenu menu = new MainMenu(loggedInId);
                                    menu.setVisible(true);
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(getInstance(), "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(getInstance(), "Unable to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                return null;
            }
            
            @Override
            protected void done() {
                // Enable the login button if needed
            }
        };
        
        
// Execute the SwingWorker
worker.execute();
    }   private void updateLabelState() {
        if (isSelected) {
            
            passlogin.setEchoChar('\u0000');
            jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
        } else {
            
            passlogin.setEchoChar('•');
            jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        }
    }
    private void updateLabelState2() {
        if (isSelected2) {
            
            passF.setEchoChar('\u0000');
            jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
        } else {
            
            passF.setEchoChar('•');
            jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        }
    }
    private void updateLabelState3() {
        if (isSelected3) {
            
            cPassF.setEchoChar('\u0000');
            jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
        } else {
            
            cPassF.setEchoChar('•');
            jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        }
    }
    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        
        isSelected = !isSelected;
        updateLabelState();
        
    }//GEN-LAST:event_jLabel9MouseClicked
    public static byte[] getProfilePicture(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        byte[] profilePictureData = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                String sql = "SELECT profile_picture FROM users WHERE user_id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    // Retrieve the profile picture data from the result set
                    profilePictureData = resultSet.getBytes("profile_picture");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the result set, statement, and connection
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Close the database connection
            DatabaseConnection.closeConnection();
        }
        
        return profilePictureData;
        
        
    }
    
    private void usernameFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFActionPerformed
    
    private void birthdayFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthdayFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthdayFActionPerformed
    
    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        isSelected2 = !isSelected2;
        updateLabelState2();
    }//GEN-LAST:event_jLabel10MouseClicked
    
    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        isSelected3 = !isSelected3;
        updateLabelState3();
    }//GEN-LAST:event_jLabel11MouseClicked
    private void signUp() {
        // Create a SwingWorker to perform the database operations
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (DatabaseConnection.isDatabaseConnected()) {
                    // Database is connected, proceed with sign up
                    
                    String username = usernameF.getText().trim().replaceAll("\\s+", "");
                    String email = emailF.getText().trim().replaceAll("\\s+", "");
                    String birthday = birthdayF.getText().trim().replaceAll("\\s+", "");
                    String password = passF.getText().trim().replaceAll("\\s+", "");
                    String cpassword = cPassF.getText().trim().replaceAll("\\s+", "");
                    
                    // Check for empty fields
                    if (username.isEmpty() || email.isEmpty() || birthday.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                        JOptionPane.showMessageDialog(getInstance(), "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Validate the birthday date
                        if (!isValidDate(birthday)) {
                            JOptionPane.showMessageDialog(getInstance(), "Invalid date format. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Validate the username
                            String usernameErrorMessage = isValidUsername(username);
                            if (usernameErrorMessage != null) {
                                JOptionPane.showMessageDialog(getInstance(), usernameErrorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String duplicateUsername = checkDuplicateUser(username, email);
                                String duplicateEmail = checkDuplicateUser(username, email);
                                
                                if (duplicateUsername != null) {
                                    JOptionPane.showMessageDialog(getInstance(), duplicateUsername, "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (duplicateEmail != null) {
                                    JOptionPane.showMessageDialog(getInstance(), duplicateEmail, "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // Validate the email address
                                    if (!isGmailAddress(email)) {
                                        // Email is not a valid Gmail address
                                        JOptionPane.showMessageDialog(getInstance(), "Email must be a valid Gmail address.", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        // Validate the password strength
                                        PasswordStrength passwordStrength = getPasswordStrength(password);
                                        
                                        if (!passwordStrength.hasUppercase()) {
                                            JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one uppercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
                                        } else if (!passwordStrength.hasLowercase()) {
                                            JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one lowercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
                                        } else if (!passwordStrength.hasNumeric()) {
                                            JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one numeric digit.", "Error", JOptionPane.ERROR_MESSAGE);
                                        } else if (!passwordStrength.hasSpecial()) {
                                            JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one special character.", "Error", JOptionPane.ERROR_MESSAGE);
                                        } else if (!passwordStrength.isLengthValid()) {
                                            JOptionPane.showMessageDialog(getInstance(), "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            // Compare passwords
                                            if (comparePasswords(password.toCharArray(), cpassword.toCharArray())) {
                                                // Passwords match
                                                // Call your insertUser() method here to insert the user data into the database
                                                insertUser(username, email, birthday, password);
                                                clearFormFields();
                                                showSuccessDialog();
                                            } else {
                                                // Passwords do not match
                                                JOptionPane.showMessageDialog(getInstance(), "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Database is not connected
                    JOptionPane.showMessageDialog(getInstance(), "Unable to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                return null;
            }
            
            @Override
            protected void done() {
                // Enable the sign-up button if needed
                
            }
        };
        
// Disable the sign-up button to prevent multiple clicks


// Execute the SwingWorker
worker.execute();
    }
    public static boolean isValidDate(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false); // Disable lenient mode to enforce strict date parsing
        
        try {
            java.util.Date date = dateFormat.parse(dateStr);
            
            // Get the current date
            Calendar currentDate = Calendar.getInstance();
            
            // Subtract 100 years from the current date
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.YEAR, -100);
            
            // Compare the parsed date with the minimum date
            if (date.after(minDate.getTime()) && date.before(currentDate.getTime())) {
                return true; // Date is valid
            } else {
                return false; // Date is outside the valid range
            }
        } catch (ParseException e) {
            return false; // Date is invalid
        }
    }
    
    
    // getPasswordStrength method
    public static PasswordStrength getPasswordStrength(String password) {
        PasswordStrength strength = new PasswordStrength();
        
        if (password.length() >= 8) {
            strength.setLengthValid(true);
        }
        
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int numericCount = 0;
        int specialCount = 0;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            } else if (Character.isDigit(c)) {
                numericCount++;
            } else {
                specialCount++;
            }
        }
        
        strength.setUppercaseValid(uppercaseCount > 0);
        strength.setLowercaseValid(lowercaseCount > 0);
        strength.setNumericValid(numericCount > 0);
        strength.setSpecialValid(specialCount > 0);
        strength.setUppercaseCount(uppercaseCount);
        strength.setLowercaseCount(lowercaseCount);
        strength.setNumericCount(numericCount);
        strength.setSpecialCount(specialCount);
        
        return strength;
    }
    public static String isValidUsername(String username) {
        // Check if username contains only alphanumeric characters and symbols
        if (!username.matches("^[a-zA-Z0-9@#$%^&+=]+$")) {
            return "Username can only contain symbols, characters, and numbers.";
        }
        
        // Check if username length is between 6 and 10 characters
        if (username.length() < 4 || username.length() > 10) {
            return "Username must be between 4 and 10 characters long.";
        }
        
        return null; // Return null if username is valid
    }
    
    public static boolean isGmailAddress(String email) {
        // Regular expression pattern for Gmail address validation
        String GMAIL_PATTERN = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@gmail\\.com$";
        return email.matches(GMAIL_PATTERN);
    }
    public static boolean comparePasswords(char[] pass1, char[] pass2) {
        if (pass1.length != pass2.length) {
            return false;
        }
        
        for (int i = 0; i < pass1.length; i++) {
            if (pass1[i] != pass2[i]) {
                return false;
            }
        }
        
        return true;
    }
    public static String checkDuplicateUser(String username, String email) {
        Connection connection = null;
        PreparedStatement usernameStatement = null;
        PreparedStatement emailStatement = null;
        ResultSet usernameResultSet = null;
        ResultSet emailResultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            // Check for duplicate username
            String usernameSql = "SELECT * FROM users WHERE username = ?";
            usernameStatement = connection.prepareStatement(usernameSql);
            usernameStatement.setString(1, username);
            usernameResultSet = usernameStatement.executeQuery();
            
            
            
            if (usernameResultSet.next()) {
                return "Username already exists";
            }
            
            // Check for duplicate email
            String emailSql = "SELECT * FROM users WHERE email = ?";
            emailStatement = connection.prepareStatement(emailSql);
            emailStatement.setString(1, email);
            emailResultSet = emailStatement.executeQuery();
            
            if (emailResultSet.next()) {
                return "Email already exists";
            }
            
            // No duplicates found
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close the result sets, statements, and connection
            try {
                if (usernameResultSet != null) {
                    usernameResultSet.close();
                }
                if (emailResultSet != null) {
                    emailResultSet.close();
                }
                if (usernameStatement != null) {
                    usernameStatement.close();
                }
                if (emailStatement != null) {
                    emailStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Close the database connection
            DatabaseConnection.closeConnection();
        }
    }
    
    public void showSuccessDialog() {
        JOptionPane.showMessageDialog(getInstance(), "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showDatabaseErrorDialog() {
        JOptionPane.showMessageDialog(getInstance(), "Failed to connect to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    public void clearFormFields() {
        usernameF.setText("");
        emailF.setText("");
        birthdayF.setText("");
        passF.setText("");
        cPassF.setText("");
    }
    private void signUpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpMouseEntered
        enableHighLight(signUp);
    }//GEN-LAST:event_signUpMouseEntered
    
    private void signUpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpMouseExited
        reset(signUp);
    }//GEN-LAST:event_signUpMouseExited
    
    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        CardLayout cardLayout = (CardLayout) parent.getLayout();
        cardLayout.show(parent, "main");
        usernameF.setText("");
        emailF.setText("");
        birthdayF.setText("");
        passF.setText("");
        cPassF.setText("");
        isSelected2 = false;
        isSelected3 = false;
        updateLabelState2();
        updateLabelState3();
        
    }//GEN-LAST:event_jLabel4MouseClicked
    
    private void emailFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFActionPerformed
    
    private void exitBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseEntered
        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/exitentered.png")));
    }//GEN-LAST:event_exitBtnMouseEntered
    
    private void exitBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseExited
        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/exit.png")));
    }//GEN-LAST:event_exitBtnMouseExited
    
    private void minimizeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseClicked

    }//GEN-LAST:event_minimizeBtnMouseClicked
    
    private void minimizeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseEntered
        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimizeentered.png")));
        
    }//GEN-LAST:event_minimizeBtnMouseEntered
    
    private void minimizeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseExited
        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimize.png")));
    }//GEN-LAST:event_minimizeBtnMouseExited
    private void enableHighLight(javax.swing.JPanel panel) {
        panel.setBackground(new java.awt.Color(178, 140, 151));
    }
    private void reset(javax.swing.JPanel panel) {
        panel.setBackground(new java.awt.Color(133, 88, 111));
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Index().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField birthdayF;
    private javax.swing.JPasswordField cPassF;
    private javax.swing.JTextField emailF;
    private javax.swing.JLabel exitBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel loginButton;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPanel loginPane;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel minimizeBtn;
    private javax.swing.JPanel parent;
    private javax.swing.JPasswordField passF;
    private javax.swing.JPasswordField passlogin;
    private javax.swing.JPanel signUp;
    private javax.swing.JPanel signUpPane;
    private javax.swing.JPanel signUpPanel;
    private javax.swing.JLabel signupLabel;
    private javax.swing.JTextField userlogin;
    private javax.swing.JTextField usernameF;
    // End of variables declaration//GEN-END:variables
}
