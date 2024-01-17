 
package com.jasmineenriquez.casestudy;
import static com.jasmineenriquez.casestudy.Index.comparePasswords;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

 public class MainMenu extends javax.swing.JFrame {
     public  int xPos = 0;
     int a = 0;
     Point initialClick;
     private static String currentCard = "browse";
     private Timer animationTimer;
     public boolean isAnimating = false;
     private static JFrame instance;
     public static boolean isOpen = false;
     private static BufferedImage collapse = null;
     private JPanel cartContentPanel;
     boolean dialogOpen = false;
     MouseAdapter addToCart;
     VoucherSystem voucherSystem;
     private int cartBookCount = 0;
     private static List<BookItem> orderList = new ArrayList<>();
     private boolean isSelected = false;
     private boolean isSelected2 = false;
     private boolean isSelected3 = false;
     private int loggedId;
     private String customerusername = "";
     private String customeremail = "";
     private String customerpassword = "";
     private String customerbirthday = "";
     byte[] customerprofilePicture = null;
     private boolean isProfileChanged = false;
     boolean isPasswordChanged = false;
     private ArrayList<Integer> bookId = new ArrayList<>();            
     private ArrayList<String>  title = new ArrayList<>();
     private ArrayList<String>  author = new ArrayList<>();
     private ArrayList<String> publisher = new ArrayList<>();
     private ArrayList<String> description = new ArrayList<>();
     private ArrayList<String>  size = new ArrayList<>();
     private ArrayList<String> genre = new ArrayList<>();
     private ArrayList<Integer> pages = new ArrayList<>();
     private ArrayList<String>  languages = new ArrayList<>();
     private ArrayList<Double> price = new ArrayList<>();
     private ArrayList<byte[]>  coverImage = new ArrayList<>();
     private ArrayList<Integer> stockCount = new ArrayList<>();
     private ArrayList<String> pricesWithPeso = new ArrayList<>();
    


     int colCount = 4;
     int rowCount = 0;
     int bookWidth = 190;
     int bookHeight = 310;
     int spacing = 40;
     int x = spacing;
     int y = spacing;
     
     public MainMenu(int loggedId) {
         initComponents();
         
         voucherSystem = new VoucherSystem();
         voucherSystem.addVoucher("hahaha", 100.0, LocalDate.of(2023, 06, 24));
         voucherSystem.addVoucher("lolomo", 150.0, LocalDate.of(2024, 6, 30));
         voucherSystem.addVoucher("hehehe", 5000.0, LocalDate.of(2024, 6, 30));
         instance = this;
         cartContentPanel = parent2;
         this.loggedId = loggedId;
         
         
         // Retrieve customer information
         List<User> customers = getUserInfo(loggedId);
         if (!customers.isEmpty()) {
             User user = customers.get(0);
             customerusername = user.getUsername();
             customerpassword = user.getPassword();
             customeremail = user.getEmail();
             customerbirthday = user.getBirthday();
             customerprofilePicture = user.getProfilePicture();
             // ...and so on
         }
         ArrayList<Book> books = getBooks();
         
        for (Book book : books) {
            // Extract the book details
            int bookIdValue = book.getBookId();
            String titleValue = book.getTitle();
            String authorValue = book.getAuthor();
            String publisherValue = book.getPublisher();
            String descriptionValue = book.getDescription();
            String sizeValue = book.getSize();
            String genreValue = book.getGenre();
            int pagesValue = book.getPages();
            String languagesValue = book.getLanguages();
            Double priceValue = book.getPrice();
            byte[] coverImageValue = book.getCoverImage();
            int stockCountValue = book.getStockCount();

            // Add the book details to the respective ArrayLists
            bookId.add(bookIdValue);
            title.add(titleValue);
            author.add(authorValue);
            publisher.add(publisherValue);
            description.add(descriptionValue);
            size.add(sizeValue);
            genre.add(genreValue);
            pages.add(pagesValue);
            languages.add(languagesValue);
            price.add(priceValue);
            coverImage.add(coverImageValue);
            stockCount.add(stockCountValue);
         
        }
        rowCount = (int) Math.ceil((double) title.size() / colCount);
        DecimalFormat pesoFormat = new DecimalFormat("₱0.00");

      
        for (double prices : price) {
            String formattedPrice = pesoFormat.format(prices);
            pricesWithPeso.add(formattedPrice);
        }
        
         
         // Perform desired operations with the user data
         System.out.println("User ID: " + loggedId);
         System.out.println("Username: " + customerusername);
         System.out.println("Password: " + customerpassword);
         System.out.println("Email: " + customeremail);
         System.out.println("Birthday: " + customerbirthday);
         setProfilePicture(customerprofilePicture);
         setWelcome(customerusername);
         totalPanel.remove(removeVoucher);
         
         
         // Create a document filter to limit the length of the text field
         DocumentFilter usernameFilter = new DocumentFilter() {
             int maxLength = 10;
             
             @Override
             public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                 // Check if the resulting text length exceeds the maximum length
                 if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
                     super.insertString(fb, offset, string, attr);
                 }
             }
             
             @Override
             public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                 // Check if the resulting text length exceeds the maximum length
                 if ((fb.getDocument().getLength() + text.length() - length) <= maxLength) {
                     super.replace(fb, offset, length, text, attrs);
                 }
             }
         };
         
// Set the document filter to the usernameF JTextField
((AbstractDocument) userName.getDocument()).setDocumentFilter(usernameFilter);



parent2.setPreferredSize(new Dimension(bookWidth + spacing * 2, 0));
TextPrompt firstname = new TextPrompt("First Name", fName,false);
firstname.setForeground(new java.awt.Color(178, 140, 151));
firstname.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
fName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt middlename = new TextPrompt("Middle Name", mName,false);
middlename.setForeground(new java.awt.Color(178, 140, 151));
middlename.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
mName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt lastname = new TextPrompt("Last Name", lName,false);
lastname.setForeground(new java.awt.Color(178, 140, 151));
lastname.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
lName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt street1 = new TextPrompt("Street", street,false);
street1.setForeground(new java.awt.Color(178, 140, 151));
street1.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
street.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt barangay1 = new TextPrompt("Barangay", barangay,false);
barangay1.setForeground(new java.awt.Color(178, 140, 151));
barangay1.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
barangay.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt mun = new TextPrompt("City", city,false);
mun.setForeground(new java.awt.Color(178, 140, 151));
mun.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
city.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt prov = new TextPrompt("Province", province,false);
prov.setForeground(new java.awt.Color(178, 140, 151));
prov.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
province.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt zipcode = new TextPrompt("Zip Code", zip,false);
zipcode.setForeground(new java.awt.Color(178, 140, 151));
zipcode.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
zip.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

TextPrompt account = new TextPrompt("Full Name", accoutname,false);
account.setForeground(new java.awt.Color(178, 140, 151));
account.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
accoutname.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
accoutname.setEnabled(false);

oldPass.setEchoChar('•');
confirmPass.setEchoChar('•');
newPass.setEchoChar('•');

TextPrompt oldpass = new TextPrompt("Current Password", oldPass,false);
oldpass.setForeground(new java.awt.Color(178, 140, 151));
oldpass.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

TextPrompt newpass = new TextPrompt("New Password", newPass,false);
newpass.setForeground(new java.awt.Color(178, 140, 151));
newpass.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

TextPrompt username = new TextPrompt("Username", userName,false);
username.setForeground(new java.awt.Color(178, 140, 151));
username.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

TextPrompt useremail = new TextPrompt("Email", email,false);
useremail.setForeground(new java.awt.Color(178, 140, 151));
useremail.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));


TextPrompt cpassword = new TextPrompt("Confirm Password", confirmPass,false);
cpassword.setForeground(new java.awt.Color(178, 140, 151));
cpassword.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

TextPrompt birthday1 = new TextPrompt("mm/dd/yyyy", birthday,false);
birthday1.setForeground(new java.awt.Color(178, 140, 151));
birthday1.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
birthday.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String text = birthday.getText();
        
        if (text.length() >= 10 || !(Character.isDigit(c) || c == '/')) {
            e.consume();  // Ignore the event
            return;
        }
        
        if (Character.isDigit(c)) {
            if (text.length() == 2 || text.length() == 5) {
                text += "/";
                birthday.setText(text);
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
            String text = birthday.getText();
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                birthday.setText(text);
            }
        }
    }
});

chooseFile.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        uploadButtonMouseClicked(evt);
    }
});

checkOut.addComponentListener(new ComponentAdapter() {
    @Override
    public void componentShown(ComponentEvent e) {
        fName.requestFocus();
        
    }
});
FocusTraversalUtils.setCustomFocusTraversalPolicy(checkOut.getParent(),
        new Component[]{fName, mName, lName, street,barangay,city,province,zip,mobileno,accoutname,accountno,voucherfield});


userName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
confirmPass.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
email.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
birthday.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
oldPass.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));
newPass.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));


TextPrompt accountn = new TextPrompt("Account No.", accountno,false);
accountn.setForeground(new java.awt.Color(178, 140, 151));
accountn.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
accountno.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
accountno.setEnabled(false);
TextPrompt vouch = new TextPrompt("Enter Code", voucherfield,false);
vouch.setForeground(new java.awt.Color(178, 140, 151));
vouch.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));
voucherfield.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
paymentMethod.setFont(new java.awt.Font("Consolas", 1, 16));
paymentMethod.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> source = (JComboBox<String>) e.getSource();
        String selectedOption = (String) source.getSelectedItem();
        if(!selectedOption.equals("COD")) {
            accoutname.setEnabled(true);
            accountno.setEnabled(true);
        }
        else {
            accoutname.setEnabled(false);
            accountno.setEnabled(false);
            accoutname.setText("");
            accountno.setText("");
        }
    }
});

zip.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') ||
                (c == KeyEvent.VK_BACK_SPACE) ||
                (c == KeyEvent.VK_DELETE))) {
            e.consume();
        }
    }
});
accountno.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') ||
                (c == KeyEvent.VK_BACK_SPACE) ||
                (c == KeyEvent.VK_DELETE))) {
            e.consume();
        }
    }
});
MobileNo outerObject = new MobileNo();
MobileNo.NumericDocument innerObj = outerObject.new NumericDocument();
mobileno.setDocument(innerObj);
mobileno.setText("+63");
mobileno.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyTyped(java.awt.event.KeyEvent evt) {
        outerObject.textFieldKeyTyped(evt);
    }
});

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
        
        
    }
});




searchButton.setEnabled(false);



jLabel5.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adjust collapse icon padding
try {
    collapse = ImageIO.read(getClass().getResource("/com/jasmineenriquez/casestudy/expand.png"));
} catch (IOException e) {
    e.printStackTrace();
}
jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 170, 50, 170));

JPanel faqPanel = new JPanel();

faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
faqPanel.setBackground(new Color(247, 237, 227));


// Add the faqPanel instances...

// Calculate the total preferred height required for all faqPanel instances


// FAQ 1
JPanel faq1Panel = createFAQPanel("Are the books new or used?", "Our entire collection of books is brand new and in excellent condition.");
faqPanel.add(faq1Panel);

// FAQ 2
JPanel faq2Panel = createFAQPanel("What payment methods are accepted?", "You have multiple options for payment, including convenient "
        + "pay centers such as BDO, Metrobank, GCASH, Landbank, or you can choose cash on delivery.");
faqPanel.add(faq2Panel);

// FAQ 3
JPanel faq3Panel = createFAQPanel("How long does it take for the book to be delivered?", "The delivery time generally ranges from 3 to 4 days, varying on the destination.");
faqPanel.add(faq3Panel);

// FAQ 4
JPanel faq4Pane1 = createFAQPanel("What are the shipping options availabe?", "We ensure hassle-free shipping by delivering the books directly to your doorstep.");
faqPanel.add(faq4Pane1);

// FAQ 5
JPanel faq5Pane1 = createFAQPanel("Are there any additional fees or taxes?", "Rest assured, there are no hidden fees. The total amount you see is the final amount you'll need to pay.");
faqPanel.add(faq5Pane1);

// FAQ 6
JPanel faq6Pane1 = createFAQPanel("Do you offer gift wrapping or personalized messages?", "Certainly! "
        + "We provide the option of gift wrapping and including a personalized message, which serves as our expression of gratitude for your purchase."
        + " Feel free to reach out to us, and we will promptly get in touch with "
        + "you to discuss further particulars and make the necessary arrangements for your gift.");
faqPanel.add(faq6Pane1);

//         Add the FAQ panel to a scrollable pane
//        JScrollPane scrollPane = new JScrollPane(faqPanel);

jPanel2.add(faqPanel, BorderLayout.CENTER);

TextPrompt search = new TextPrompt("Please type your search here", searchTextField,false);
search.setForeground(new java.awt.Color(178, 140, 151));
search.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));


searchTextField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

// Customize the horizontal scrollbar to be invisible
jScrollPane1.setHorizontalScrollBar(null);
jScrollPane1.getViewport().setDoubleBuffered(true);
jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
jScrollPane1.getVerticalScrollBar().setBackground(new Color(178, 140, 151));
jScrollPane2.setHorizontalScrollBar(null);
jScrollPane2.setBorder(BorderFactory.createEmptyBorder());
jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
jScrollPane2.getVerticalScrollBar().setBackground(new Color(178, 140, 151));


jScrollPane3.setHorizontalScrollBar(null);
jScrollPane3.setBorder(BorderFactory.createEmptyBorder());
jScrollPane3.getVerticalScrollBar().setUnitIncrement(16);
jScrollPane3.getVerticalScrollBar().setBackground(new Color(178, 140, 151));

jScrollPane5.setHorizontalScrollBar(null);
jScrollPane5.setBorder(BorderFactory.createEmptyBorder());
jScrollPane5.getVerticalScrollBar().setUnitIncrement(16);
jScrollPane5.getVerticalScrollBar().setBackground(new Color(178, 140, 151));

jScrollPane4.setHorizontalScrollBar(null);
jScrollPane4.setBorder(BorderFactory.createEmptyBorder());
jScrollPane4.getVerticalScrollBar().setUnitIncrement(16);
jScrollPane4.getVerticalScrollBar().setBackground(new Color(178, 140, 151));

TextPrompt birthday = new TextPrompt("Please type your message here", jTextArea1,true);

birthday.setForeground(new java.awt.Color(178, 140, 151));
birthday.setFont(new java.awt.Font("Century Gothic", Font.ITALIC, 18));

jScrollPane1.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(133, 88, 111);
    }
});

jScrollPane2.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(133, 88, 111);
    }
});
jScrollPane3.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(133, 88, 111);
    }
});
jScrollPane4.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(133, 88, 111);
    }
});
jScrollPane5.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(133, 88, 111);
    }
});


hamburger.addMouseListener(hamburgerMouseListener);

            // Create book panels
            for (int i = 0; i < title.size(); i++) {
                final int countIndex = i;
                JPanel bookPanel = new JPanel();
                bookPanel.setBackground(new Color(247, 237, 227));
                bookPanel.setLayout(null);
                bookPanel.setPreferredSize(new Dimension(bookWidth, bookHeight));
                //    // Add book cover image using JLabel
                JLabel coverLabel = new JLabel();
                BufferedImage originalImage = null;
                originalImage = convertToBufferedImage(coverImage.get(i));
                Image scaledImage = originalImage.getScaledInstance(190, 260, Image.SCALE_SMOOTH);
                coverLabel.setIcon(new ImageIcon(scaledImage));
                coverLabel.setBounds(0, 0, 190, 280);
                bookPanel.add(coverLabel);
                coverLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                coverLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        coverLabel.setToolTipText(title.get(countIndex));
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        
                    }
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        bookDescription(bookDescription,countIndex);
                        CardLayout cardLayout = (CardLayout) container.getLayout();
                        cardLayout.show(container, "bookdescription");
                        
                    }
                });
                //Label of the book
                JLabel booklabel = new JLabel(title.get(i));
                booklabel.setBounds(10, 35, 100, 20);
                bookPanel.add(booklabel);
                // Add spacing at the bottom by adjusting the position
                int bottomSpacing = 35;
                bookPanel.add(Box.createVerticalStrut(bottomSpacing));
                // Add panel at the bottom with color (133, 88, 111)
                JPanel pricePanel = new JPanel();
                pricePanel.setBackground(new Color(133, 88, 111));
                pricePanel.setBounds(0, bookHeight-bottomSpacing, bookWidth-45, 35);
                // Add JLabel for price ("P300.00") with white foreground color
                JLabel priceLabel = new JLabel(pricesWithPeso.get(i));
                priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                Font priceFont = new Font("Consolas",Font.BOLD, 18);
                priceLabel.setFont(priceFont);
                priceLabel.setForeground(Color.WHITE);
                pricePanel.setLayout(null);
                priceLabel.setBounds(37, -6, 200, 50);
                pricePanel.add(priceLabel);
                bookPanel.add(pricePanel);
                // Add spacing to the right by adjusting the position
                int rightSpacing = 10;
                bookPanel.add(Box.createHorizontalStrut(rightSpacing));
                // Add panel to the right without exceeding bookPanel width
                int rightPanelWidth = bookWidth - rightSpacing - 20;
                JPanel rightPanel = new JPanel();
                rightPanel.setBackground(new Color(133, 88, 111));
                rightPanel.setBounds(150, bookHeight - 35, 40, 35); // Adjusted height to accommodate "Add to Cart" label
                // Add JLabel for "Add to Cart" with white foreground color
                JLabel addToCartLabel = new JLabel();
                addToCartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addToCart = new MouseAdapter() {
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(isOpen) {
                            return;
                        }
                        addToCartLabel.getParent().setBackground(new Color(188, 160, 167));
                        addToCartLabel.getParent().repaint();
                        
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(isOpen) {
                            return;
                        }
                        addToCartLabel.getParent().setBackground(new Color(133, 88, 111));
                        addToCartLabel.getParent().repaint();
                    }
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(isOpen) {
                            return;
                        }
                        addToCart(countIndex);
                        
                        
                    }
                };
                addToCartLabel.addMouseListener(addToCart);
                addToCartLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/shoppingcart.png"))); // NOI18N
                rightPanel.setLayout(null);
                addToCartLabel.setForeground(Color.WHITE);
                addToCartLabel.setBounds(5, 0, 35, 35);
                rightPanel.add(addToCartLabel);
                bookPanel.add(rightPanel);
                bookPanel.setBounds(x, y, bookWidth, bookHeight);
                bookPanel.putClientProperty("x", x);
                bookPanel.putClientProperty("y", y);
                parent.add(bookPanel);
                x += bookWidth + spacing;
                if (x >= colCount * (bookWidth + spacing)) {
                    x = spacing;
                    y += bookHeight + spacing;
                }
              
 }
 
 int parentHeight = (bookHeight + spacing) * rowCount + spacing;
 parent.setPreferredSize(new Dimension(400, parentHeight));
 
 ActionListener searchAction = new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if(!isOpen) {
                 String query = searchTextField.getText().toLowerCase();
 
                 if (query.isEmpty()) {
                     resetBookList();
                     return;
                 }
 
                 boolean matchFound = false;
 
                 for (Component comp : parent.getComponents()) {
                     if (comp instanceof JPanel) {
                         JPanel bookPanel = (JPanel) comp;
                         JLabel nameLabel = (JLabel) bookPanel.getComponent(1);
                         String bookName = nameLabel.getText().toLowerCase();
 
                         if (bookName.contains(query)) {
                             bookPanel.setBounds(spacing, spacing, bookWidth, bookHeight);
                             bookPanel.setVisible(true);
                             matchFound = true;
                         } else {
                             bookPanel.setVisible(false);
                         }
                     }
                 }
 
                 if (!matchFound) {
                  
              Toolkit.getDefaultToolkit().beep();
             JFrame parentFrame = getInstance();
            
                 NoResult noresult = new NoResult(parentFrame, true);
                 noresult.setLocationRelativeTo(parentFrame);
                 noresult.setVisible(true);
                 
                   
 //                  JOptionPane.showMessageDialog(null, "No results found", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                     searchTextField.selectAll();
                     searchTextField.setText("");
                     resetBookList();
                     
                 }
                 
               
                 
                 parent.revalidate();
                 parent.repaint();
               
                   
                 jScrollPane1.getVerticalScrollBar().setValue(0);
             }
             }
         };
 
          searchButton.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 searchAction.actionPerformed(null);
             }
 
             @Override
             public void mouseEntered(MouseEvent e) {
 //                searchButton.setBackground(Color.GRAY);
             }
 
             @Override
             public void mouseExited(MouseEvent e) {
 //                searchButton.setBackground(Color.LIGHT_GRAY);
             }
         });
 
         searchTextField.addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent evt) {
                 if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A) {
                     searchTextField.selectAll();
                  
                 } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                     SwingUtilities.invokeLater(new Runnable() {
                         @Override
                         public void run() {
                             String query = searchTextField.getText();
                             if (query.isEmpty()) {
                                 resetBookList();
                               
                                
                             }
                             
                              
                         }
                     });
                 }
             }
             @Override
             public void keyReleased(KeyEvent evt) {
                 String query = searchTextField.getText();
                 searchButton.setEnabled(!query.isEmpty());
 
                 if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                     if (!query.isEmpty()) {
                     
                                        
                         searchAction.actionPerformed(null);
                     } else {
                    
                         resetBookList();
                         
                     }
                 }
             }
         });
     }
  private void resetBookList() {
         for (Component comp : parent.getComponents()) {
             if (comp instanceof JPanel) {
                 JPanel bookPanel = (JPanel) comp;
                 bookPanel.setVisible(true);
                 bookPanel.setLocation((int) bookPanel.getClientProperty("x"),
                         (int) bookPanel.getClientProperty("y"));
             }
         }
      
         parent.revalidate();
         parent.repaint();
     
        
       
         jScrollPane1.getVerticalScrollBar().setValue(0);
     }

 private void addToCart(int bookIndex) {
        boolean existingBook = false;
        BufferedImage bookcover = null;
    
        
         if (cartContentPanel.getComponentCount() == 1 && cartContentPanel.getComponent(0) == cartEmpty) {
        cartContentPanel.remove(cartEmpty);
    }
 
     for (Component comp : cartContentPanel.getComponents()) {
         if (comp instanceof JPanel) {
             JPanel cartBookPanel = (JPanel) comp;
             JTextArea nameLabel = (JTextArea) cartBookPanel.getComponent(1);
             JLabel priceLabel = (JLabel) cartBookPanel.getComponent(3);
             JComboBox<String> comboBox = (JComboBox<String>) cartBookPanel.getComponent(4);
             JSpinner spinner = (JSpinner) cartBookPanel.getComponent(5);
               System.out.println("Name: " + nameLabel.getText());
 
             if (nameLabel.getText().equals(title.get(bookIndex))) {
                 existingBook = true;
               System.out.println("Updating total for book: " + nameLabel.getText());
 
 //
                 int quantity = (int) spinner.getValue();
                 quantity++;
                 spinner.setValue(quantity);
               
                 double price1 = Double.parseDouble(pricesWithPeso.get(bookIndex).substring(1));
                 if (comboBox.getSelectedItem().equals("HardCover")) {
                     price1 *= 1.15; // Apply 15% increase for Hardcover
                 }
 
                 double total = price1 * quantity;
 
                 totalValue.setText(String.format("₱%.2f", total));
               
                 
                 break;
             }
             updateTotal();
         }
     }

     if (!existingBook) {
        
         JPanel cartBookPanel = new JPanel();
         cartBookPanel.setLayout(null);
         cartBookPanel.setBackground(new Color(247, 237, 227));

         cartBookPanel.setPreferredSize(new Dimension(bookWidth+400, bookHeight-30));
         cartBookCount++;
    
            CustomRadioButton radioButton = new CustomRadioButton();
       
           
         
         radioButton.setBounds(15, 140, 20, 20);
         radioButton.addActionListener(e -> updateTotal());
         cartBookPanel.add(radioButton);
         
        JTextArea nameLabel = new JTextArea(title.get(bookIndex));
        nameLabel.setBackground(new Color(247, 237, 227));

        nameLabel.setEditable(false);
        nameLabel.setLineWrap(true);
        nameLabel.setWrapStyleWord(true);
       
        nameLabel.setBounds(270, 60, 200, 100);
        nameLabel.setPreferredSize(new Dimension(200, 100));

        nameLabel.setHighlighter(null);
     
//         JLabel nameLabel = new JLabel(books[bookIndex]);
//         nameLabel.setBounds(270, 80,1000, 40);
         nameLabel.setFont(new java.awt.Font("Century Gothic", 1, 25));
         cartBookPanel.add(nameLabel);
 
         JLabel bookCoverLabel = new JLabel(title.get(bookIndex));
         bookcover = convertToBufferedImage(coverImage.get(bookIndex));
         Image scaledImage = bookcover.getScaledInstance(190, 260, Image.SCALE_SMOOTH);
         bookCoverLabel.setIcon(new ImageIcon(scaledImage));
         bookCoverLabel.setBounds(50, 0, 190, 280);
         cartBookPanel.add(bookCoverLabel);
         
         
         JLabel priceLabel = new JLabel(pricesWithPeso.get(bookIndex));
         priceLabel.setBounds(270, 210, 120, 30);
         priceLabel.setForeground(Color.white);
         priceLabel.setBorder(BorderFactory.createEmptyBorder(10, 7, 5, 5));
         priceLabel.setOpaque(true);
         priceLabel.setBackground(new Color(133, 88, 111));
         priceLabel.setFont(new java.awt.Font("Consolas", 1, 16));
         cartBookPanel.add(priceLabel);
         
   
 
         String[] items = {"Paperback", "Hardcover"};
         CustomComboBox comboBox = new CustomComboBox(items);
         comboBox.setFont(new java.awt.Font("Consolas", 1, 16));
         comboBox.setBounds(270, 170, 120, 30);
         comboBox.addActionListener(e -> updateTotal());
         cartBookPanel.add(comboBox);
        CustomSpinner spinner = new CustomSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spinner.setBounds(400, 170, 60, 30);
        spinner.addChangeListener(e -> updateTotal());
       
        cartBookPanel.add(spinner);
        
        

//
        cartBookPanel.setBounds(10, (bookHeight) * cartContentPanel.getComponentCount(), bookWidth+400, bookHeight-30);
        cartContentPanel.add(cartBookPanel); // Update the preferred size and revalidate the cartContentPanel
            int parentHeight = (bookHeight) * cartContentPanel.getComponentCount() + spacing;
            cartContentPanel.setPreferredSize(new Dimension(bookWidth, parentHeight));
            cartContentPanel.revalidate();
            cartContentPanel.repaint();
            
                   if (cartContentPanel.getComponentCount() == 1) {
            CustomRadioButton radioButton1 = (CustomRadioButton) cartBookPanel.getComponent(0);
            radioButton1.setSelected(true);
          
            
        }
  
            

            // Update the scroll pane's viewport position
            JViewport viewport = jScrollPane4.getViewport();
          
//            Rectangle bounds = cartBookPanel.getBounds();
            viewport.setViewPosition(new Point(0, 0));
            
            
            

    }
     updateTotal();
    
  
    System.out.println("Number of books in cart: " +   cartBookCount);
    cartCount.setText(Integer.toString(cartBookCount));
   
    
}
 private void setProfilePicture(byte[] imageData) {
         byte[] user_picture = convertJLabelIconToBytes(profile);
        if (imageData != null) {
        int width = 150; // Specify the desired width
        int height = 150; // Specify the desired height
        BufferedImage resizedImage = resizeImageFromByte(imageData, width, height);
  

 
         if (resizedImage != null) {
                ImageIcon profilePicture = new ImageIcon(resizedImage);
                profileLabel.setIcon(profilePicture);
                profile.setIcon(profilePicture);
               
        } else {
            // Handle the case when resizing fails
            // Display a default profile picture or an error message
            
        }
     }
        else {
            profileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/defaultpic.jpg")));
            profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/defaultpic.jpg")));

        }
    
}
 public BufferedImage convertToBufferedImage(byte[] imageData) {
    BufferedImage bufferedImage = null;
    try {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        bufferedImage = ImageIO.read(bis);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return bufferedImage;
}
 private void bookDescription(JPanel bookDescriptionPanel, int index) {
     BufferedImage bookcover = null;
     bookDescriptionPanel.removeAll();
     
     JLabel bookCoverLabel = new JLabel(title.get(index));
     bookcover = convertToBufferedImage(coverImage.get(index));
     Image scaledImage = bookcover.getScaledInstance(190, 260, Image.SCALE_SMOOTH);
     bookCoverLabel.setIcon(new ImageIcon(scaledImage));
     bookCoverLabel.setBounds(50, 30, 190, 280);
     bookDescriptionPanel.add(bookCoverLabel);
     
         
         
             
//JTextArea bookTitle = new JTextArea(books[index] + " " + "by" + " " + authors[index]);
//bookTitle.setBackground(new Color(247,237,227));
//
//bookTitle.setEditable(false);
//bookTitle.setLineWrap(true);
//bookTitle.setBounds(250, 60, 600, 60);
//bookTitle.setWrapStyleWord(true);
//bookTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
//bookTitle.setHighlighter(null);
//  bookDescriptionPanel.add(bookTitle);
//
//
//JTextArea sypnosisLabel = new JTextArea(sypnosis[index]);
//sypnosisLabel.setBackground(new Color(247,237,227));
//
//sypnosisLabel.setEditable(false);
//sypnosisLabel.setLineWrap(true);
//sypnosisLabel.setWrapStyleWord(true);
//sypnosisLabel.setHighlighter(null);
//sypnosisLabel.setBounds(250, 130, 500, 200);
//sypnosisLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
//sypnosisLabel.setPreferredSize(new Dimension(500,200));
  // Add panel at the bottom with color (133, 88, 111)
  
   JPanel addCartPanel = new JPanel();
     addCartPanel.setBackground(new Color(133, 88, 111));
     addCartPanel.setBounds(50, bookHeight+40, 190, 35);
     
     
     JLabel addCartLabel = new JLabel("Add To Cart");
     addCartLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
     Font addCartLabelFont = new Font("Consolas",Font.BOLD, 18);
     addCartLabel.setFont(addCartLabelFont);
     addCartLabel.setForeground(Color.WHITE);
     addCartPanel.setLayout(null);
     addCartLabel.setBounds(40, -6, 200, 50);
     
     addCartPanel.add(addCartLabel);
     bookDescription.add(addCartPanel);
     
  
     addCartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    MouseAdapter addToCart = new MouseAdapter() {
      
         @Override
                 public void mouseEntered(MouseEvent e) {
                  
                     addCartLabel.getParent().setBackground(new Color(188, 160, 167));
                     addCartLabel.getParent().repaint();
                     
                 }
 
                 @Override
                 public void mouseExited(MouseEvent e) {
                 
                     addCartLabel.getParent().setBackground(new Color(133, 88, 111));
                     addCartLabel.getParent().repaint();
                 }
 
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     
                     addToCart(index);
                    
                     
                 }
    };
     
     addCartLabel.addMouseListener(addToCart);

        
      
    
     
     
     
  
     JPanel pricePanel = new JPanel();
     pricePanel.setBackground(new Color(133, 88, 111));
     pricePanel.setBounds(50, bookHeight, 190, 35);
 
     // Add JLabel for price ("P300.00") with white foreground color
     JLabel priceLabel = new JLabel(pricesWithPeso.get(index));
     priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
     Font priceFont = new Font("Consolas",Font.BOLD, 18);
     priceLabel.setFont(priceFont);
     priceLabel.setForeground(Color.WHITE);
     pricePanel.setLayout(null);
     priceLabel.setBounds(55, -6, 200, 50);
       
     pricePanel.add(priceLabel);
     bookDescription.add(pricePanel);
  

        // Create a JTextPane
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html"); // Set content type to HTML
        textPane.setEditable(false);
        textPane.setHighlighter(null);

        // Format the text using HTML tags
     String formattedText = "<html>"
        + "<h1 style='font-weight: bold; font-size: 24px; margin-bottom: 1px; font-family: Century Gothic;'>" + title.get(index) + "</h1>"
        + "<h6 style='font-weight: bold; font-size: 14px; margin-bottom: 1px; font-family: Century Gothic;'>" + "By " + author.get(index) + "</h6>"
        + "<p style='font-size: 10px; margin-bottom: 5px; font-family: Century Gothic;'><b>" + publisher.get(index) + "</b></p>"
         + "<p style='font-size: 12px; font-family: Century Gothic;'>" + description.get(index) + "</p>"
           + "<p style='font-size: 12px; margin-bottom: 5px; font-family: Century Gothic;'><b>" + "Book Details:" + "</b></p>"    
         + "<hr style='border-top: 2px solid black; margin: 5px 0;'>"
        + "<p style='font-size: 10px; font-family: Century Gothic; margin-top: 0; padding-top: 5px;'>Size: " + size.get(index) + "</p>"
        + "<hr style='border-top: 2px solid black; margin: 5px 0;'>"
        + "<p style='font-size: 10px; font-family: Century Gothic; margin-top: 0; padding-top: 5px;'>Genre: " + genre.get(index) + "</p>"
        + "<hr style='border-top: 2px solid black; margin: 5px 0;'>"
        + "<p style='font-size: 10px; font-family: Century Gothic; margin-top: 0; padding-top: 5px;'>Pages: " + pages.get(index) + "</p>"
        + "<hr style='border-top: 2px solid black; margin: 5px 0;'>"
        + "<p style='font-size: 10px; font-family: Century Gothic; margin-top: 0; padding-top: 5px;'>Language: " + languages.get(index) + "</p>"
        + "</html>";

        // Set the formatted text to the JTextPane
        textPane.setText(formattedText);
        textPane.setBounds(250,30,600,800);
        textPane.setBackground(new Color(247,237,227));
        // Set the preferred size for the JTextPane
        textPane.setPreferredSize(new Dimension(600, 800));

            returnLabel.addMouseListener(returnButton);

            
//            bookDescriptionPanel.add(sypnosisLabel);
            bookDescriptionPanel.add(returnLabel);
            bookDescriptionPanel.add(textPane);
            bookDescriptionPanel.revalidate();
            bookDescriptionPanel.repaint();
            
        
  
          
            
            
 }
  private  MouseListener returnButton = new MouseAdapter() {
        
        
    @Override
    public void mouseClicked(MouseEvent evt) {
            
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "browse");
        currentCard = "browse";
        subBar.add(jPanel4);
        subBar.add(jPanel8);
        searchTextField.setEnabled(true);
        xPos = 0;
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.getVerticalScrollBar().setEnabled(true);
        searchTextField.setFocusable(true);
         jTextArea1.setFocusable(true);
        isOpen = false;
      
                
            }};
  

private static void showOrderSummaryPanel(JPanel cartContentPanel, JPanel orderSumPanel) {
    // Clear the order summary panel
    orderSumPanel.removeAll();
    boolean isOrderSummaryEmpty = true;
    int y = 10; // Initial y-coordinate for positioning components
    double originalSubtotal = 0.0; // Store the original subtotal
    double subtotal = 0.0; // Initialize the subtotal
    
      if (cartContentPanel.getComponentCount() == 0) {
        orderList.clear(); // Clear the bookItems ArrayList
        System.out.println("All books removed from the order");
        return;
    }

    // Iterate over the child components of the cartContentPanel
    Component[] components = cartContentPanel.getComponents();
    for (Component component : components) {
        if (component instanceof JPanel) {
            JPanel cartBookPanel = (JPanel) component;
            CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);

            // Check if the radio button is selected
            if (!radioButton.isSelected()) {
                 JTextArea bookName = (JTextArea) cartBookPanel.getComponent(1);
                 removeBookFromArrayList(bookName.getText());
                continue; // Skip this book panel if not selected
            }

            JTextArea bookName = (JTextArea) cartBookPanel.getComponent(1);
            JLabel price = (JLabel) cartBookPanel.getComponent(3);
            JComboBox<String> comboBox = (JComboBox<String>) cartBookPanel.getComponent(4);
            JSpinner quantity = (JSpinner) cartBookPanel.getComponent(5);

            // Create the formatted string for the book
            double bookPrice = Double.parseDouble(price.getText().substring(1));
            int qty = (int) quantity.getValue();

            // Apply the price difference for hardcover
            String bookType = (String) comboBox.getSelectedItem();
            if (bookType.equals("Hardcover")) {
                bookPrice *= 1.15; // Increase the price by 15%
            }

            double totalPrice = bookPrice * qty;
            String bookSummary = String.format("%s (%d)", bookName.getText(), qty);
            String bookPriceSum = String.format(" ₱%.2f", totalPrice);

            JTextArea nameLabel = new JTextArea(bookSummary);
            nameLabel.setBackground(new Color(223, 211, 195));
            nameLabel.setEditable(false);
            nameLabel.setLineWrap(true);
            nameLabel.setWrapStyleWord(true);
            nameLabel.setBounds(10, y, 200, 40);
            nameLabel.setPreferredSize(new Dimension(200, 40));
            nameLabel.setFont(new java.awt.Font("Century Gothic", 1, 14));
            nameLabel.setHighlighter(null);

            JLabel bookPriceLabel = new JLabel(bookPriceSum);
            bookPriceLabel.setFont(new java.awt.Font("Consolas", 1, 15));
            bookPriceLabel.setBounds(210, y + 3, 300, 20);

            orderSumPanel.add(bookPriceLabel);
            orderSumPanel.add(nameLabel);
            y += 45; // Increment y-coordinate for the next component

            originalSubtotal += totalPrice;
            subtotal += totalPrice;
            isOrderSummaryEmpty = false;
            
             // Create a new BookItem object and add it to the bookItems list
               // Check if the book already exists in the bookItems ArrayList
            boolean bookExists = false;
            for (BookItem book : orderList) {
                if (book.getItemName().equals(bookName.getText())) {
                    // Update the existing book entry
                    book.setBookType(bookType);
                    book.setQuantity(qty);
                    book.setUnitPrice( bookPrice);
                    book.setTotal(totalPrice);
                   


           bookExists = true;
          
                    
            System.out.println("BookItem added existed: " + book.getItemName());
            System.out.println(book.getId());
            System.out.println(book.getBookType());
            System.out.println(book.getQuantity());
            System.out.println(book.getUnitPrice());
            System.out.println(book.getTotal());
                    
                    break;
                    
                    
                }
            }

            // If the book doesn't exist, add it to the bookItems ArrayList
            if (!bookExists) {
                int newId = orderList.size() + 1; // Generate a new ID
                BookItem newBook = new BookItem(newId, bookName.getText(), bookType, qty, bookPrice, totalPrice);
                orderList.add(newBook);
                      System.out.println("BookItem added not existed: " + newBook.getItemName());
            System.out.println(newBook.getId());
            System.out.println(newBook.getBookType());
            System.out.println(newBook.getQuantity());
            System.out.println(newBook.getUnitPrice());
            System.out.println(newBook.getTotal());
            }
            
         
            
        }
    }

    if (isOrderSummaryEmpty) {
        subtotal = 0.00;
    }

    // Check if the voucher value label is available and the order summary is not empty
    if (voucherValue != null && !isOrderSummaryEmpty) {
        double voucherAmount = Double.parseDouble(voucherValue.getText().substring(2)); // Extract the voucher amount
        subtotal -= voucherAmount; // Deduct the voucher amount from the subtotal
    }

    subTotalValue.setText(String.format("₱%.2f", originalSubtotal)); // Display the original subtotal
    amountDue.setText(String.format("₱%.2f", Math.max(0, subtotal))); // Display the updated amount due (minimum 0)

    // Set the preferred size of the orderSumPanel
    Dimension preferredSize = new Dimension(300, y + 10);
    orderSumPanel.setPreferredSize(preferredSize);

    // Refresh the orderSumPanel
    orderSumPanel.revalidate();
    orderSumPanel.repaint();
}




 private void updateTotal() {
    double total = 0.0;

    for (Component comp : parent2.getComponents()) {
        if (comp instanceof JPanel) {
            JPanel cartBookPanel = (JPanel) comp;
            CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);
            JLabel priceLabel = (JLabel) cartBookPanel.getComponent(3);
             JComboBox<String> comboBox = (JComboBox<String>) cartBookPanel.getComponent(4);
            JSpinner spinner = (JSpinner) cartBookPanel.getComponent(5);

            if (radioButton.isSelected()) {
              
               double price = Double.parseDouble(priceLabel.getText().substring(1));
                int quantity = (int) spinner.getValue();

               
                if (comboBox.getSelectedItem().equals("Hardcover")) {
                    price *= 1.15; // Apply 15% increase for Hardcover
                    
                }

                total += price * quantity;
            }
          
        }
    }
           
  totalValue.setText(String.format("₱%.2f", total));

  
}
 public static String getAmountDue() {
     return amountDue.getText();
 }
 public static String getSubTotal() {
     return subTotalValue.getText();
 }
 public static String getVoucher() {
     return voucherValue.getText();
 }
 private void selectAll() {
            if (cartContentPanel.getComponentCount() == 1 && cartContentPanel.getComponent(0) == cartEmpty) {
            return;
    }
    for (Component comp : cartContentPanel.getComponents()) {
        if (comp instanceof JPanel) {
            JPanel cartBookPanel = (JPanel) comp;
           CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);
            radioButton.setSelected(true);
        }
    }

    updateTotal();
 
}
 private void unselectAll() {
      if (cartContentPanel.getComponentCount() == 1 && cartContentPanel.getComponent(0) == cartEmpty) {
            return;
    }
    for (Component comp : cartContentPanel.getComponents()) {
        if (comp instanceof JPanel) {
            JPanel cartBookPanel = (JPanel) comp;
           CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);
            radioButton.setSelected(false);
        }
    }

    updateTotal();
}

 private void deleteSelected() {
          if (cartContentPanel.getComponentCount() == 1 && cartContentPanel.getComponent(0) == cartEmpty) {
            return;
    }
           Component[] components = cartContentPanel.getComponents();
    List<Component> componentsToRemove = new ArrayList<>();

    for (int i = components.length - 1; i >= 0; i--) {
        Component comp = components[i];
        if (comp instanceof JPanel) {
            JPanel cartBookPanel = (JPanel) comp;
           CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);
            if (radioButton.isSelected()) {
                componentsToRemove.add(cartBookPanel);
                 JTextArea bookName = (JTextArea) cartBookPanel.getComponent(1);
                 removeBookFromArrayList(bookName.getText());
                
                 
            
            }
        }
    }

    int removedCount = componentsToRemove.size();
    for (Component comp : componentsToRemove) {
        cartContentPanel.remove(comp);
    }
     

    if (removedCount > 0) {
        int remainingCount = cartContentPanel.getComponentCount();
        int panelHeight = bookHeight -30;

        for (int i = 0; i < remainingCount; i++) {
            
            JPanel cartBookPanel = (JPanel) cartContentPanel.getComponent(i);
            cartBookPanel.setBounds(10, i * bookHeight , bookWidth+400, panelHeight);
        }
        
             int parentHeight = (bookHeight) * cartContentPanel.getComponentCount() + spacing;
            cartContentPanel.setPreferredSize(new Dimension(bookWidth, parentHeight));
            cartContentPanel.revalidate();

        cartContentPanel.revalidate();
        cartContentPanel.repaint();
        
       
  

        updateTotal();
       
        cartCount.setText(String.valueOf(remainingCount));
        cartBookCount = remainingCount;
        // Check if the cart is empty
        if (remainingCount == 0) {
            // Add the empty cart panel back to the cartContentPanel
            cartContentPanel.add(cartEmpty);
             orderList.clear();
            cartContentPanel.revalidate();
            cartContentPanel.repaint();
            
   
        }
    } else {
        JOptionPane.showMessageDialog(getInstance(), "No books selected for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        closeBtn = new javax.swing.JLabel();
        browsePanel = new javax.swing.JPanel();
        browseLogo = new javax.swing.JLabel();
        browseLabel = new javax.swing.JLabel();
        cartPanel = new javax.swing.JPanel();
        cartLogo = new javax.swing.JLabel();
        cartLabel = new javax.swing.JLabel();
        userPanel = new javax.swing.JPanel();
        userLogo = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        messagePanel = new javax.swing.JPanel();
        messageLogo = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        faqPanel = new javax.swing.JPanel();
        faqLogo = new javax.swing.JLabel();
        faqLabel = new javax.swing.JLabel();
        logoutPanel = new javax.swing.JPanel();
        logoutLogo = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        container = new javax.swing.JPanel();
        browse = new javax.swing.JPanel();
        subBar = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        searchButton = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        cartCount = new javax.swing.JLabel();
        jPanel5 = new RoundedPanel(new java.awt.Dimension(100,100),0);
        cartLogoCount = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        parent = new javax.swing.JPanel();
        cart = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        parent2 = new javax.swing.JPanel();
        cartEmpty = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel8 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        totalValue = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        user = new javax.swing.JPanel();
        chooseFile = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel23 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        email = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        userName = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        birthday = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        oldPass = new javax.swing.JPasswordField();
        jLabel36 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        confirmPass = new javax.swing.JPasswordField();
        jLabel38 = new javax.swing.JLabel();
        saveInfo = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel39 = new javax.swing.JLabel();
        deleteAccount = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel40 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        newPass = new javax.swing.JPasswordField();
        jLabel42 = new javax.swing.JLabel();
        profileLabel = new javax.swing.JLabel();
        profileLabel2 = new javax.swing.JLabel();
        message = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel3 = new javax.swing.JLabel();
        faq = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        checkOut = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        lName = new javax.swing.JTextField();
        totalPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        voucherValue = new javax.swing.JLabel();
        amountDue = new javax.swing.JLabel();
        subTotalValue = new javax.swing.JLabel();
        removeVoucher = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        mobileno = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        mName = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        fName = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        street = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        barangay = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        city = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        province = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        accountno = new javax.swing.JTextField();
        applyVoucherPanel =  new RoundedPanel(new java.awt.Dimension(45,45),0);
        applyVoucher = new javax.swing.JLabel();
        orderSumMain = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        orderSumPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        String[] items = {"COD", "BDO", "BPI", "METROBANK", "LANDBANK"};
        paymentMethod = new CustomComboBox(items);
        jPanel35 = new javax.swing.JPanel();
        accoutname = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        zip = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        voucherfield = new javax.swing.JTextField();
        jPanel34 =  new RoundedPanel(new java.awt.Dimension(45,45),0);
        jLabel27 = new javax.swing.JLabel();
        bookDescription = new javax.swing.JPanel();
        returnLabel = new javax.swing.JLabel();
        titleBar = new javax.swing.JPanel();
        hamburger = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        exitBtn = new javax.swing.JLabel();
        minimizeBtn = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(247, 237, 227));
        mainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mainPanelMouseDragged(evt);
            }
        });
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mainPanelMousePressed(evt);
            }
        });
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBarPanel.setBackground(new java.awt.Color(133, 88, 111));
        sideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/close.png"))); // NOI18N
        closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeBtnMouseExited(evt);
            }
        });
        sideBarPanel.add(closeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 0, -1, -1));

        browsePanel.setBackground(new java.awt.Color(133, 88, 111));
        browsePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        browsePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browsePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browsePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browsePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browsePanelMouseExited(evt);
            }
        });

        browseLogo.setForeground(new java.awt.Color(255, 255, 255));
        browseLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/home.png"))); // NOI18N

        browseLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        browseLabel.setForeground(new java.awt.Color(255, 255, 255));
        browseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        browseLabel.setText("Browse");

        javax.swing.GroupLayout browsePanelLayout = new javax.swing.GroupLayout(browsePanel);
        browsePanel.setLayout(browsePanelLayout);
        browsePanelLayout.setHorizontalGroup(
            browsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browsePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(browseLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        browsePanelLayout.setVerticalGroup(
            browsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(browseLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(browsePanelLayout.createSequentialGroup()
                .addComponent(browseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        sideBarPanel.add(browsePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 40));

        cartPanel.setBackground(new java.awt.Color(133, 88, 111));
        cartPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        cartPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cartPanel.setPreferredSize(new java.awt.Dimension(250, 40));
        cartPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cartPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cartPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cartPanelMouseExited(evt);
            }
        });

        cartLogo.setForeground(new java.awt.Color(255, 255, 255));
        cartLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/cart2.png"))); // NOI18N

        cartLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cartLabel.setForeground(new java.awt.Color(255, 255, 255));
        cartLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cartLabel.setText("Cart");

        javax.swing.GroupLayout cartPanelLayout = new javax.swing.GroupLayout(cartPanel);
        cartPanel.setLayout(cartPanelLayout);
        cartPanelLayout.setHorizontalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cartPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cartLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        cartPanelLayout.setVerticalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cartPanelLayout.createSequentialGroup()
                .addGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cartLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sideBarPanel.add(cartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 250, -1));

        userPanel.setBackground(new java.awt.Color(133, 88, 111));
        userPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        userPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userPanelMouseExited(evt);
            }
        });

        userLogo.setForeground(new java.awt.Color(255, 255, 255));
        userLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/user.png"))); // NOI18N

        userLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setText("User Info");

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(userLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sideBarPanel.add(userPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 250, 40));

        messagePanel.setBackground(new java.awt.Color(133, 88, 111));
        messagePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        messagePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        messagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                messagePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                messagePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                messagePanelMouseExited(evt);
            }
        });

        messageLogo.setForeground(new java.awt.Color(255, 255, 255));
        messageLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/support.png"))); // NOI18N

        messageLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(255, 255, 255));
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageLabel.setText("Message Us");

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(messageLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messageLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        sideBarPanel.add(messagePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 250, 40));

        faqPanel.setBackground(new java.awt.Color(133, 88, 111));
        faqPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        faqPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        faqPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                faqPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                faqPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                faqPanelMouseExited(evt);
            }
        });

        faqLogo.setForeground(new java.awt.Color(255, 255, 255));
        faqLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/faq.png"))); // NOI18N

        faqLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        faqLabel.setForeground(new java.awt.Color(255, 255, 255));
        faqLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        faqLabel.setText("FAQs");

        javax.swing.GroupLayout faqPanelLayout = new javax.swing.GroupLayout(faqPanel);
        faqPanel.setLayout(faqPanelLayout);
        faqPanelLayout.setHorizontalGroup(
            faqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faqPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(faqLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(faqLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
        );
        faqPanelLayout.setVerticalGroup(
            faqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faqPanelLayout.createSequentialGroup()
                .addGroup(faqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(faqLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faqLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sideBarPanel.add(faqPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 250, 40));

        logoutPanel.setBackground(new java.awt.Color(133, 88, 111));
        logoutPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(178, 140, 151)));
        logoutPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutPanelMouseExited(evt);
            }
        });

        logoutLogo.setForeground(new java.awt.Color(255, 255, 255));
        logoutLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/logout.png"))); // NOI18N

        logoutLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        logoutLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutLabel.setText("Logout");

        javax.swing.GroupLayout logoutPanelLayout = new javax.swing.GroupLayout(logoutPanel);
        logoutPanel.setLayout(logoutPanelLayout);
        logoutPanelLayout.setHorizontalGroup(
            logoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(logoutLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        logoutPanelLayout.setVerticalGroup(
            logoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(logoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(logoutLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(logoutLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        sideBarPanel.add(logoutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 250, 40));

        userNameLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNameLabel.setText("Hello, null!");
        sideBarPanel.add(userNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 250, -1));

        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/defaultpic.jpg"))); // NOI18N
        profile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        sideBarPanel.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 150, 150));

        mainPanel.add(sideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 660));

        container.setBackground(new java.awt.Color(247, 237, 227));
        container.setLayout(new java.awt.CardLayout());

        browse.setBackground(new java.awt.Color(247, 237, 227));
        browse.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        subBar.setBackground(new java.awt.Color(247, 237, 227));

        jPanel4.setBackground(new java.awt.Color(223, 211, 195));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchButton.setBackground(new java.awt.Color(223, 211, 195));
        searchButton.setForeground(new java.awt.Color(255, 255, 102));
        searchButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/search2.png"))); // NOI18N
        searchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchButton.setOpaque(true);
        jPanel4.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 40, 40));

        searchTextField.setBackground(new java.awt.Color(223, 211, 195));
        searchTextField.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(133, 88, 111));
        searchTextField.setBorder(null);
        searchTextField.setPreferredSize(new java.awt.Dimension(80, 40));
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });
        jPanel4.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));

        jPanel8.setBackground(new java.awt.Color(247, 237, 227));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cartCount.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        cartCount.setForeground(new java.awt.Color(255, 255, 255));
        cartCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cartCount.setText("0");
        jPanel8.add(cartCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 20, -1));

        jPanel5.setBackground(new java.awt.Color(133, 88, 111));
        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel8.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 20, 19));

        cartLogoCount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/cart.png"))); // NOI18N
        jPanel8.add(cartLogoCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 45));

        javax.swing.GroupLayout subBarLayout = new javax.swing.GroupLayout(subBar);
        subBar.setLayout(subBarLayout);
        subBarLayout.setHorizontalGroup(
            subBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subBarLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 462, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        subBarLayout.setVerticalGroup(
            subBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(subBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        browse.add(subBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 60));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(993, 3000));

        parent.setBackground(new java.awt.Color(247, 237, 227));
        parent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        parent.setLayout(null);
        jScrollPane1.setViewportView(parent);

        browse.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 980, 450));

        container.add(browse, "browse");

        cart.setBackground(new java.awt.Color(247, 237, 227));
        cart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        parent2.setBackground(new java.awt.Color(247, 237, 227));
        parent2.setPreferredSize(new java.awt.Dimension(0, 0));
        parent2.setLayout(null);

        cartEmpty.setBackground(new java.awt.Color(247, 237, 227));
        cartEmpty.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/emptyCart.png"))); // NOI18N
        cartEmpty.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 250, 210));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel7.setText("Your cart is empty");
        cartEmpty.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 240, -1));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Looks like you haven't added anything yet!");
        cartEmpty.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 350, 60));

        jPanel7.setBackground(new java.awt.Color(133, 88, 111));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Add Items Now");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cartEmpty.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 340, 190, 40));

        parent2.add(cartEmpty);
        cartEmpty.setBounds(0, 0, 980, 440);

        jScrollPane4.setViewportView(parent2);

        cart.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 450));

        jPanel9.setBackground(new java.awt.Color(247, 237, 227));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(133, 88, 111));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11MouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Check Out");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 110, 40));

        jPanel14.setBackground(new java.awt.Color(247, 237, 227));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Select All");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(199, 10, 80, 50));

        jPanel15.setBackground(new java.awt.Color(247, 237, 227));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Delete");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 70, 50));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel1.setText("Total:");
        jPanel9.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 0, 50, 70));

        totalValue.setFont(new java.awt.Font("Consolas", 1, 30)); // NOI18N
        totalValue.setForeground(new java.awt.Color(133, 88, 111));
        totalValue.setText("₱0.00");
        jPanel9.add(totalValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 180, 70));

        jPanel16.setBackground(new java.awt.Color(247, 237, 227));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Unselect All");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setPreferredSize(new java.awt.Dimension(49, 20));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 90, 50));

        cart.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 980, 70));

        container.add(cart, "cart");

        user.setBackground(new java.awt.Color(247, 237, 227));
        user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userMouseClicked(evt);
            }
        });
        user.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chooseFile.setBackground(new java.awt.Color(133, 88, 111));
        chooseFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chooseFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chooseFileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chooseFileMouseExited(evt);
            }
        });
        chooseFile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Choose a File");
        chooseFile.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 40));

        user.add(chooseFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 150, 40));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(133, 88, 111));
        jLabel29.setText("Change Password");
        user.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 260, -1));

        jLabel30.setBackground(new java.awt.Color(133, 88, 111));
        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(133, 88, 111));
        jLabel30.setText("Edit Information");
        user.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 850, -1));

        jPanel12.setBackground(new java.awt.Color(223, 211, 195));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        email.setBackground(new java.awt.Color(223, 211, 195));
        email.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        email.setForeground(new java.awt.Color(133, 88, 111));
        email.setBorder(null);
        email.setPreferredSize(new java.awt.Dimension(80, 40));
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel12.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));

        user.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 300, 40));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel31.setText("Confirm Password");
        user.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 140, 40));

        jPanel13.setBackground(new java.awt.Color(223, 211, 195));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userName.setBackground(new java.awt.Color(223, 211, 195));
        userName.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        userName.setForeground(new java.awt.Color(133, 88, 111));
        userName.setBorder(null);
        userName.setPreferredSize(new java.awt.Dimension(80, 40));
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        jPanel13.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, -1));

        user.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 300, 40));

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel32.setText("Username");
        user.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 80, 40));

        jPanel18.setBackground(new java.awt.Color(223, 211, 195));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        birthday.setBackground(new java.awt.Color(223, 211, 195));
        birthday.setColumns(10);
        birthday.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        birthday.setForeground(new java.awt.Color(133, 88, 111));
        birthday.setBorder(null);
        birthday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthdayActionPerformed(evt);
            }
        });
        jPanel18.add(birthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 40));

        user.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 300, 40));

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel33.setText("Email");
        user.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 80, 40));

        jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(133, 88, 111));
        jLabel34.setText("Account Information");
        user.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 260, -1));

        jPanel19.setBackground(new java.awt.Color(223, 211, 195));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setBackground(new java.awt.Color(223, 211, 195));
        jLabel35.setForeground(new java.awt.Color(255, 255, 102));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel35.setOpaque(true);
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });
        jPanel19.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        oldPass.setBackground(new java.awt.Color(223, 211, 195));
        oldPass.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        oldPass.setForeground(new java.awt.Color(133, 88, 111));
        oldPass.setBorder(null);
        jPanel19.add(oldPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        user.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 300, 40));

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel36.setText("Birthday");
        user.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 80, 40));

        jPanel20.setBackground(new java.awt.Color(223, 211, 195));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setBackground(new java.awt.Color(223, 211, 195));
        jLabel37.setForeground(new java.awt.Color(255, 255, 102));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.setOpaque(true);
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        jPanel20.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        confirmPass.setBackground(new java.awt.Color(223, 211, 195));
        confirmPass.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        confirmPass.setForeground(new java.awt.Color(133, 88, 111));
        confirmPass.setBorder(null);
        jPanel20.add(confirmPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        user.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 380, 300, 40));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel38.setText("Current Password");
        user.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 130, 40));

        saveInfo.setBackground(new java.awt.Color(133, 88, 111));
        saveInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveInfoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveInfoMouseExited(evt);
            }
        });
        saveInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Save Information");
        saveInfo.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 40));

        user.add(saveInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 170, 40));

        deleteAccount.setBackground(new java.awt.Color(133, 88, 111));
        deleteAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteAccountMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteAccountMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteAccountMouseExited(evt);
            }
        });
        deleteAccount.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Delete Account");
        deleteAccount.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 40));

        user.add(deleteAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 440, 170, 40));

        jPanel21.setBackground(new java.awt.Color(223, 211, 195));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setBackground(new java.awt.Color(223, 211, 195));
        jLabel41.setForeground(new java.awt.Color(255, 255, 102));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel41.setOpaque(true);
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });
        jPanel21.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 40, 40));

        newPass.setBackground(new java.awt.Color(223, 211, 195));
        newPass.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        newPass.setForeground(new java.awt.Color(133, 88, 111));
        newPass.setBorder(null);
        jPanel21.add(newPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        user.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 300, 40));

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel42.setText("New Password");
        user.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 130, 40));

        profileLabel.setBackground(new java.awt.Color(133, 88, 111));
        profileLabel.setForeground(new java.awt.Color(133, 88, 111));
        profileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/defaultpic.jpg"))); // NOI18N
        profileLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(133, 88, 111), 3));
        user.add(profileLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 150, 150));

        profileLabel2.setBackground(new java.awt.Color(133, 88, 111));
        profileLabel2.setForeground(new java.awt.Color(133, 88, 111));
        profileLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/defaultpic.jpg"))); // NOI18N
        profileLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(133, 88, 111), 3));
        user.add(profileLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 150, 150));

        container.add(user, "user");

        message.setBackground(new java.awt.Color(247, 237, 227));
        message.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(133, 88, 111));
        jLabel4.setText("How can we help you?");
        message.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 40, 600, -1));

        jScrollPane2.setBackground(new java.awt.Color(223, 211, 195));
        jScrollPane2.setBorder(null);
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(133, 88, 111), 2));

        jTextArea1.setBackground(new java.awt.Color(247, 237, 227));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(133, 88, 111));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(null);
        jScrollPane2.setViewportView(jTextArea1);

        message.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 595, 290));

        jPanel1.setBackground(new java.awt.Color(133, 88, 111));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Send");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        message.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 410, 110, 40));

        container.add(message, "message");

        faq.setBackground(new java.awt.Color(247, 237, 227));
        faq.setPreferredSize(new java.awt.Dimension(980, 600));
        faq.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setBackground(new java.awt.Color(247, 237, 227));
        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setBackground(new java.awt.Color(247, 237, 227));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(133, 88, 111));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Frequently Asked Questions");
        jPanel2.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jScrollPane3.setViewportView(jPanel2);

        faq.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 510));

        container.add(faq, "faq");

        checkOut.setBackground(new java.awt.Color(247, 237, 227));
        checkOut.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/return.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        checkOut.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 40));

        jPanel17.setBackground(new java.awt.Color(223, 211, 195));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lName.setBackground(new java.awt.Color(223, 211, 195));
        lName.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lName.setForeground(new java.awt.Color(133, 88, 111));
        lName.setBorder(null);
        lName.setPreferredSize(new java.awt.Dimension(80, 40));
        lName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lNameActionPerformed(evt);
            }
        });
        jPanel17.add(lName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 160, 40));

        totalPanel.setBackground(new java.awt.Color(223, 211, 195));
        totalPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel20.setText("Voucher");
        totalPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 80, 30));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel21.setText("AMOUNT DUE");
        totalPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel24.setText("SubTotal");
        totalPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        voucherValue.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        voucherValue.setText("-₱0.00");
        totalPanel.add(voucherValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 120, 30));

        amountDue.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        amountDue.setText("₱0.00");
        totalPanel.add(amountDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 100, 40));

        subTotalValue.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        subTotalValue.setText("₱0.00");
        totalPanel.add(subTotalValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 100, 40));

        removeVoucher.setBackground(new java.awt.Color(133, 88, 111));
        removeVoucher.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        removeVoucher.setForeground(new java.awt.Color(133, 88, 111));
        removeVoucher.setText("REMOVE");
        removeVoucher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeVoucherMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeVoucherMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeVoucherMouseExited(evt);
            }
        });
        totalPanel.add(removeVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 76, 80, 20));

        checkOut.add(totalPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 300, 320, 130));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel15.setText("Account #");
        checkOut.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 80, 40));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel16.setText("Name");
        checkOut.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 40));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel17.setText("Address");
        checkOut.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 60, 40));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel18.setText("Payment");
        checkOut.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 70, 40));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel19.setText("Voucher");
        checkOut.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 70, 40));

        jPanel25.setBackground(new java.awt.Color(223, 211, 195));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mobileno.setBackground(new java.awt.Color(223, 211, 195));
        mobileno.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        mobileno.setForeground(new java.awt.Color(133, 88, 111));
        mobileno.setBorder(null);
        mobileno.setPreferredSize(new java.awt.Dimension(80, 40));
        mobileno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilenoActionPerformed(evt);
            }
        });
        jPanel25.add(mobileno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 160, 40));

        jPanel26.setBackground(new java.awt.Color(223, 211, 195));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mName.setBackground(new java.awt.Color(223, 211, 195));
        mName.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        mName.setForeground(new java.awt.Color(133, 88, 111));
        mName.setBorder(null);
        mName.setPreferredSize(new java.awt.Dimension(80, 40));
        mName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNameActionPerformed(evt);
            }
        });
        jPanel26.add(mName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 160, 40));

        jPanel27.setBackground(new java.awt.Color(223, 211, 195));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fName.setBackground(new java.awt.Color(223, 211, 195));
        fName.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        fName.setForeground(new java.awt.Color(133, 88, 111));
        fName.setBorder(null);
        fName.setPreferredSize(new java.awt.Dimension(80, 40));
        fName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fNameActionPerformed(evt);
            }
        });
        jPanel27.add(fName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160, 40));

        jPanel28.setBackground(new java.awt.Color(223, 211, 195));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        street.setBackground(new java.awt.Color(223, 211, 195));
        street.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        street.setForeground(new java.awt.Color(133, 88, 111));
        street.setBorder(null);
        street.setPreferredSize(new java.awt.Dimension(80, 40));
        street.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streetActionPerformed(evt);
            }
        });
        jPanel28.add(street, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 160, 40));

        jPanel29.setBackground(new java.awt.Color(223, 211, 195));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barangay.setBackground(new java.awt.Color(223, 211, 195));
        barangay.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        barangay.setForeground(new java.awt.Color(133, 88, 111));
        barangay.setBorder(null);
        barangay.setPreferredSize(new java.awt.Dimension(80, 40));
        barangay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangayActionPerformed(evt);
            }
        });
        jPanel29.add(barangay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 160, 40));

        jPanel30.setBackground(new java.awt.Color(223, 211, 195));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        city.setBackground(new java.awt.Color(223, 211, 195));
        city.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        city.setForeground(new java.awt.Color(133, 88, 111));
        city.setBorder(null);
        city.setPreferredSize(new java.awt.Dimension(80, 40));
        city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityActionPerformed(evt);
            }
        });
        jPanel30.add(city, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 160, 40));

        jPanel31.setBackground(new java.awt.Color(223, 211, 195));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        province.setBackground(new java.awt.Color(223, 211, 195));
        province.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        province.setForeground(new java.awt.Color(133, 88, 111));
        province.setBorder(null);
        province.setPreferredSize(new java.awt.Dimension(80, 40));
        province.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                provinceActionPerformed(evt);
            }
        });
        jPanel31.add(province, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 160, 40));

        jPanel32.setBackground(new java.awt.Color(223, 211, 195));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountno.setBackground(new java.awt.Color(223, 211, 195));
        accountno.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        accountno.setForeground(new java.awt.Color(133, 88, 111));
        accountno.setBorder(null);
        accountno.setPreferredSize(new java.awt.Dimension(80, 40));
        accountno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountnoActionPerformed(evt);
            }
        });
        jPanel32.add(accountno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 160, 40));

        applyVoucherPanel.setBackground(new java.awt.Color(133, 88, 111));
        applyVoucherPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        applyVoucherPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                applyVoucherPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                applyVoucherPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                applyVoucherPanelMouseExited(evt);
            }
        });

        applyVoucher.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        applyVoucher.setForeground(new java.awt.Color(255, 255, 255));
        applyVoucher.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        applyVoucher.setText("Apply");

        javax.swing.GroupLayout applyVoucherPanelLayout = new javax.swing.GroupLayout(applyVoucherPanel);
        applyVoucherPanel.setLayout(applyVoucherPanelLayout);
        applyVoucherPanelLayout.setHorizontalGroup(
            applyVoucherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, applyVoucherPanelLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(applyVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        applyVoucherPanelLayout.setVerticalGroup(
            applyVoucherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(applyVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        checkOut.add(applyVoucherPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 440, 110, 40));

        orderSumMain.setBackground(new java.awt.Color(223, 211, 195));
        orderSumMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBorder(null);
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        orderSumPanel.setBackground(new java.awt.Color(223, 211, 195));
        orderSumPanel.setLayout(null);
        jScrollPane5.setViewportView(orderSumPanel);

        orderSumMain.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 190));

        checkOut.add(orderSumMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 320, 190));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel2.setText("Order Summary");
        checkOut.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 230, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel25.setText("Personal Details");
        checkOut.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 230, 30));

        checkOut.add(paymentMethod, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 160, 40));

        jPanel35.setBackground(new java.awt.Color(223, 211, 195));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accoutname.setBackground(new java.awt.Color(223, 211, 195));
        accoutname.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        accoutname.setForeground(new java.awt.Color(133, 88, 111));
        accoutname.setBorder(null);
        accoutname.setPreferredSize(new java.awt.Dimension(80, 40));
        accoutname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accoutnameActionPerformed(evt);
            }
        });
        jPanel35.add(accoutname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, 160, 40));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel26.setText("Name");
        checkOut.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 70, 40));

        jPanel36.setBackground(new java.awt.Color(223, 211, 195));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        zip.setBackground(new java.awt.Color(223, 211, 195));
        zip.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        zip.setForeground(new java.awt.Color(133, 88, 111));
        zip.setBorder(null);
        zip.setPreferredSize(new java.awt.Dimension(80, 40));
        zip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipActionPerformed(evt);
            }
        });
        jPanel36.add(zip, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 160, 40));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel28.setText("Mobile #");
        checkOut.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 70, 40));

        jPanel47.setBackground(new java.awt.Color(223, 211, 195));
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        voucherfield.setBackground(new java.awt.Color(223, 211, 195));
        voucherfield.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        voucherfield.setForeground(new java.awt.Color(133, 88, 111));
        voucherfield.setBorder(null);
        voucherfield.setPreferredSize(new java.awt.Dimension(80, 40));
        voucherfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voucherfieldActionPerformed(evt);
            }
        });
        jPanel47.add(voucherfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        checkOut.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, 160, 40));

        jPanel34.setBackground(new java.awt.Color(133, 88, 111));
        jPanel34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel34MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel34MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel34MouseExited(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Place Order");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        checkOut.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 440, 140, -1));

        container.add(checkOut, "checkout");

        bookDescription.setBackground(new java.awt.Color(247, 237, 227));
        bookDescription.setLayout(null);

        returnLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/return.png"))); // NOI18N
        returnLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookDescription.add(returnLabel);
        returnLabel.setBounds(5, 0, 35, 40);

        container.add(bookDescription, "bookdescription");

        mainPanel.add(container, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 980, 510));

        titleBar.setBackground(new java.awt.Color(247, 237, 227));
        titleBar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(133, 88, 111)));

        hamburger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/hamburger.png"))); // NOI18N
        hamburger.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hamburger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hamburgerMouseClicked(evt);
            }
        });

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/logo.png"))); // NOI18N

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

        javax.swing.GroupLayout titleBarLayout = new javax.swing.GroupLayout(titleBar);
        titleBar.setLayout(titleBarLayout);
        titleBarLayout.setHorizontalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleBarLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(hamburger, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addComponent(minimizeBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitBtn))
        );
        titleBarLayout.setVerticalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleBarLayout.createSequentialGroup()
                .addGroup(titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titleBarLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(hamburger, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(titleBarLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exitBtn)
                    .addComponent(minimizeBtn))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        mainPanel.add(titleBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("MainForm");

        setSize(new java.awt.Dimension(983, 659));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void setWelcome(String username) {
        userNameLabel.setText("Hello, " + username+"!");
    }
    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseClicked
        
     if (isAnimating) {
        return; // Ignore the click if an animation is already in progress
     }
    if (xPos == 250 || dialogOpen) {
        
        if (dialogOpen)
            xPos = 250;
        
        sideBarPanel.setSize(xPos, 660);
        int targetWidth = 0; // Target width for the panel
        int animationDuration = 250; // Duration of the animation in milliseconds
        int animationDelay = 5; // Delay between each step of the animation in milliseconds
        int steps = animationDuration / animationDelay; // Number of steps

        int stepSize = (xPos - targetWidth) / steps; // Amount to change the panel size in each step
        animationTimer = new Timer(animationDelay, new ActionListener() {
            private int currentStep = steps;

            @Override
            public void actionPerformed(ActionEvent e) {
                xPos -= stepSize;
                if (xPos < targetWidth) {
                    xPos = targetWidth; // Limit the width to the target width
                }
                sideBarPanel.setSize(xPos, 660);
                repaint();

                currentStep--;
                if (currentStep <= 0) {
                    ((Timer) e.getSource()).stop();
                    xPos = targetWidth;
                    hamburger.addMouseListener(hamburgerMouseListener);
                    isAnimating = false; // Animation completed
                    searchTextField.setEnabled(true);
                    jScrollPane1.setWheelScrollingEnabled(true);
                    jScrollPane1.getVerticalScrollBar().setEnabled(true);
                    searchTextField.setFocusable(true);
                    isOpen = false;
                    jTextArea1.setFocusable(true);
                    fName.setFocusable(true);
                    mName.setFocusable(true);
                    lName.setFocusable(true);
                    street.setFocusable(true);
                    barangay.setFocusable(true);
                    city.setFocusable(true);
                    zip.setFocusable(true);
                    mobileno.setFocusable(true);
                    accoutname.setFocusable(true);
                    accountno.setFocusable(true);
                    province.setFocusable(true);
                    mobileno.setFocusable(true);
                    voucherfield.setFocusable(true);
                        
                }
            }
        });
        animationTimer.start();
        isAnimating = true; // Animation started
    }
        
    }//GEN-LAST:event_closeBtnMouseClicked
    private void uploadButtonMouseClicked(java.awt.event.MouseEvent evt) {
        
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG/PNG Images", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = getFileExtension(selectedFile);
            
            if (extension != null && (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))) {
                try {
                    
                    BufferedImage originalImage = ImageIO.read(selectedFile);
                    BufferedImage resizedImage = resizeImage(originalImage, 150, 150);
                    profileLabel.setIcon(new ImageIcon(resizedImage));
                    byte[] user_picture = convertLabelIconToBytes(profileLabel);
                    if (!isByteArrayEqual(user_picture, customerprofilePicture)) {
                        isProfileChanged = true;
                        
                        
                    }
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose a JPEG or PNG image.", "Error", JOptionPane.ERROR_MESSAGE);
                isProfileChanged = false;
            }
        }
    }
       public static boolean compareByteArrays(byte[] byteArray1, byte[] byteArray2) {
        return Arrays.equals(byteArray1, byteArray2);
    }

      private BufferedImage getImageFromIcon(Icon icon) {
    if (icon instanceof ImageIcon) {
        Image image = ((ImageIcon) icon).getImage();
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
    return null;
}

    private byte[] convertImageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
private boolean isSameImage(ImageIcon newProfilePicture) throws IOException {
    BufferedImage newImage = convertImageIconToBufferedImage(newProfilePicture);
    byte[] newImageBytes = convertImageToBytes(newImage);
    byte[] currentImageBytes = convertImageToBytes(convertIconToBufferedImage(profileLabel.getIcon()));

    return Arrays.equals(newImageBytes, currentImageBytes);
}

private BufferedImage convertImageIconToBufferedImage(ImageIcon icon) {
    BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufferedImage.createGraphics();
    icon.paintIcon(null, g2d, 0, 0);
    g2d.dispose();
    return bufferedImage;
}

    private boolean isSameImage(byte[] imageBytes1, byte[] imageBytes2) {
        if (imageBytes1.length != imageBytes2.length) {
            return false;
        }
        

        for (int i = 0; i < imageBytes1.length; i++) {
            if (imageBytes1[i] != imageBytes2[i]) {
                return false;
            }
        }

        return true;
    }
private boolean areImagesEqual(BufferedImage image1, BufferedImage image2) {
    if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
        return false;
    }

    for (int y = 0; y < image1.getHeight(); y++) {
        for (int x = 0; x < image1.getWidth(); x++) {
            if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                return false;
            }
        }
    }

    return true;
}

public BufferedImage convertIconToBufferedImage(Icon icon) {
    BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufferedImage.createGraphics();
    icon.paintIcon(null, g2d, 0, 0);
    g2d.dispose();
    return bufferedImage;
}


         public static byte[] getImageBytes(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
        public static byte[] convertJLabelIconToBytes(JLabel label) {
        Icon icon = label.getIcon();
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon) icon).getImage();

            BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
            );

            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();
                return imageBytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    
}
      private static String getFileExtension(File file) {
        String extension = null;
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }
    
    private void browsePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsePanelMouseEntered
        setPanelColor(browsePanel,new Color(178, 140, 151));
    }//GEN-LAST:event_browsePanelMouseEntered
    public ArrayList<User> getUserInfo(int userId) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    ArrayList<User> customer = new ArrayList<User>();

    try {
        connection = DatabaseConnection.getConnection();
        
      if (connection != null) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            // Retrieve the user data from the result set
            int userId1 = resultSet.getInt("user_id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            byte[] profilePicture = resultSet.getBytes("profile_picture");
            String birthday = resultSet.getString("birthday");
            User user = new User(userId1, username, email, birthday, password, profilePicture);
            customer.add(user);
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
    return customer;

    }
    private void setDefaultUserInfo(String username, String useremail, String userbirthday) {
        userName.setText(username);
        email.setText(useremail);
        
       
      // Create a DateTimeFormatter for the input format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Parse the birthday string into a LocalDate object
        LocalDate birth = LocalDate.parse(userbirthday, inputFormatter);
        
        // Create a DateTimeFormatter for the output format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        // Format the birthday as a string in the desired format
        String formattedBirthday = birth.format(outputFormatter);
        
        birthday.setText(formattedBirthday);
    }
    private void browsePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsePanelMouseExited
        resetPanelColor(browsePanel);
    }//GEN-LAST:event_browsePanelMouseExited

    private void exitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitBtnMouseClicked

    private void cartPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartPanelMouseEntered
        setPanelColor(cartPanel,new Color(178, 140, 151));
    }//GEN-LAST:event_cartPanelMouseEntered

    private void cartPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartPanelMouseExited
        resetPanelColor(cartPanel);
    }//GEN-LAST:event_cartPanelMouseExited

    private void messagePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagePanelMouseEntered
        setPanelColor(messagePanel,new Color(178, 140, 151));
    }//GEN-LAST:event_messagePanelMouseEntered

    private void messagePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagePanelMouseExited
        resetPanelColor(messagePanel);
    }//GEN-LAST:event_messagePanelMouseExited

    private void logoutPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseEntered
        setPanelColor(logoutPanel,new Color(178, 140, 151));
    }//GEN-LAST:event_logoutPanelMouseEntered

    private void logoutPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseExited
        resetPanelColor(logoutPanel);
    }//GEN-LAST:event_logoutPanelMouseExited

    private void faqPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqPanelMouseExited
       resetPanelColor(faqPanel);
    }//GEN-LAST:event_faqPanelMouseExited

    private void faqPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqPanelMouseEntered

        setPanelColor(faqPanel,new Color(178, 140, 151));
    }//GEN-LAST:event_faqPanelMouseEntered

    private void userPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPanelMouseExited
        resetPanelColor(userPanel);
    }//GEN-LAST:event_userPanelMouseExited

    private void userPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPanelMouseEntered
        setPanelColor(userPanel,new Color(178, 140, 151));
    }//GEN-LAST:event_userPanelMouseEntered

    private void mainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMousePressed
       initialClick = evt.getPoint();
       getComponentAt(initialClick);
    }//GEN-LAST:event_mainPanelMousePressed

    private void mainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseDragged
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
    }//GEN-LAST:event_mainPanelMouseDragged

    private void cartPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartPanelMouseClicked
      
        CardLayout cardLayout = (CardLayout) container.getLayout();
          cardLayout.show(container, "cart");
          currentCard = "cart";
          subBar.removeAll();
          subBar.repaint();
          subBar.revalidate();
          xPos = 0;
          searchTextField.setEnabled(true);
          jScrollPane1.setWheelScrollingEnabled(true);
          jScrollPane1.getVerticalScrollBar().setEnabled(true);
          searchTextField.setFocusable(true);
           jTextArea1.setFocusable(true);
          isOpen = false;
          
          
        
       
       
    }//GEN-LAST:event_cartPanelMouseClicked
    public static JFrame getInstance() {
        return instance;
    }
    private void browsePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsePanelMouseClicked
       
       
        if(!currentCard.equals("browse")) {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "browse");
        currentCard = "browse";
        subBar.add(jPanel4);
        subBar.add(jPanel8);
        searchTextField.setEnabled(true);
        xPos = 0;
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.getVerticalScrollBar().setEnabled(true);
        searchTextField.setFocusable(true);
         jTextArea1.setFocusable(true);
        isOpen = false;
        }

    }//GEN-LAST:event_browsePanelMouseClicked
   
private static JPanel createFAQPanel(String question, String answer) {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(247, 237, 227));
   
   int borderThickness = 3; // Specify the desired border thickness
Color borderColor = new Color(133, 88, 111); // Specify the desired border color

// Set the line border with increased thickness and the specified color
panel.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
    panel.setLayout(new BorderLayout());

    JLabel questionLabel = new JLabel(question);
    questionLabel.setFont(new java.awt.Font("Century Gothic", Font.BOLD, 18));
    questionLabel.setForeground(borderColor);
    questionLabel.setHorizontalAlignment(SwingConstants.LEFT); // Set question label alignment to left
    questionLabel.setVerticalAlignment(SwingConstants.CENTER); // Set question label alignment to center vertically
    questionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5)); // Adjust question label padding
    panel.add(questionLabel, BorderLayout.CENTER);

    JLabel collapseIcon = new JLabel(); // Down arrow icon
    collapseIcon.setIcon(new ImageIcon(collapse));
    collapseIcon.setPreferredSize(new Dimension(40,40));
    collapseIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    collapseIcon.setHorizontalAlignment(SwingConstants.RIGHT);
    collapseIcon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // Adjust collapse icon padding
    panel.add(collapseIcon, BorderLayout.EAST);

    JTextArea answerTextArea = new JTextArea(answer);
    answerTextArea.setFont(new java.awt.Font("Century Gothic", Font.PLAIN, 14));
    answerTextArea.setBorder(null);
     answerTextArea.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
    answerTextArea.setLineWrap(true); // Enable line wrapping
    answerTextArea.setWrapStyleWord(true);

  
    answerTextArea.setEditable(false);
    answerTextArea.setHighlighter(null);
    answerTextArea.setBackground(new Color(247, 237, 227)); // Set background color to match faqPanel

    // Calculate the preferred height for the answerTextArea, including the tail of characters
   
    int textWidth = answerTextArea.getPreferredSize().width - answerTextArea.getInsets().left - answerTextArea.getInsets().right;
   
    int fixedTextAreaHeight = 75;
    int preferredWidth = textWidth + answerTextArea.getInsets().left + answerTextArea.getInsets().right;
    answerTextArea.setPreferredSize(new Dimension(preferredWidth, fixedTextAreaHeight));
    
    


    JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
    answerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scroll
    answerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll
    answerScrollPane.getViewport().setPreferredSize(answerTextArea.getPreferredSize()); // Set the preferred size of the viewport
    answerScrollPane.setBorder(null); // Remove the line border from the scroll pane

    answerScrollPane.setVisible(false); // Set answer panel to be initially hidden
    panel.add(answerScrollPane, BorderLayout.SOUTH);

    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBackground(new Color(247, 237, 227));
    containerPanel.add(panel, BorderLayout.NORTH);
    containerPanel.add(answerScrollPane, BorderLayout.CENTER);

    collapseIcon.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            
            boolean isVisible = answerScrollPane.isVisible();
            if (!isOpen) {
            answerScrollPane.setVisible(!isVisible);
            collapseIcon.setIcon(isVisible ? new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/expand.png")) : new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/collapse.png")));
            panel.revalidate(); // Trigger revalidation to adjust layout
            }
        }
            
    });

    return containerPanel;



}
public ArrayList<Book> getBooks() {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    ArrayList<Book> books = new ArrayList<>();
    
    try {
        connection = DatabaseConnection.getConnection();
        
        if (connection != null) {
            String sql = "SELECT * FROM books";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                // Retrieve the book data from the result set
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                String description = resultSet.getString("description");
                String size = resultSet.getString("size");
                String genre = resultSet.getString("genre");
                int pages = resultSet.getInt("pages");
                String languages = resultSet.getString("languages");
                double price = resultSet.getDouble("price");
                byte[] coverImage = resultSet.getBytes("cover_image");
                int stockCount = resultSet.getInt("stock_count");
                
                 // Convert the double price to BigDecimal
                BigDecimal priceBig = BigDecimal.valueOf(price);
                
                // Create a Book object
                Book book = new Book(bookId, title, author, publisher, description, size, genre, pages, languages, price, coverImage, stockCount);
                
                // Add the book to the list
                books.add(book);
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
    
    return books;
}
    private void logoutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseClicked
        
        dialogOpen = true;       
      
        JFrame parentFrame = getInstance();
        ExitConfimation exitconfirm = new ExitConfimation(parentFrame, true);
        exitconfirm.setLocationRelativeTo(parentFrame);
        
        exitconfirm.setVisible(true);
        
               
    }//GEN-LAST:event_logoutPanelMouseClicked

    private void messagePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagePanelMouseClicked
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "message");
        currentCard = "message";
        subBar.removeAll();
        subBar.revalidate();
        subBar.repaint();
        xPos = 0;
        searchTextField.setEnabled(true);
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.getVerticalScrollBar().setEnabled(true);
        searchTextField.setFocusable(true);
        jTextArea1.setFocusable(true);
        isOpen = false;
    }//GEN-LAST:event_messagePanelMouseClicked

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
         setPanelColor(jPanel1,new Color(178, 140, 151));
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        resetPanelColor(jPanel1);
    }//GEN-LAST:event_jPanel1MouseExited

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void faqPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqPanelMouseClicked
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "faq");
        currentCard = "faq";
        subBar.removeAll();
        subBar.revalidate();
        subBar.repaint(); 
        xPos = 0;
        searchTextField.setEnabled(true);
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.getVerticalScrollBar().setEnabled(true);
        searchTextField.setFocusable(true);
        jTextArea1.setFocusable(true);
        isOpen = false;
    }//GEN-LAST:event_faqPanelMouseClicked

    private void exitBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseEntered
        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/exitentered.png"))); // NOI18N
    }//GEN-LAST:event_exitBtnMouseEntered

    private void exitBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseExited
        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/exit.png")));
    }//GEN-LAST:event_exitBtnMouseExited

    private void minimizeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseClicked
//       this.setState(Frame.ICONIFIED);
            xPos = 0;   
    }//GEN-LAST:event_minimizeBtnMouseClicked

    private void minimizeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseEntered
         minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimizeentered.png")));
       
    }//GEN-LAST:event_minimizeBtnMouseEntered

    private void minimizeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeBtnMouseExited
         minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/minimize.png")));
    }//GEN-LAST:event_minimizeBtnMouseExited

    private void closeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseEntered
        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/closeentered.png")));
    }//GEN-LAST:event_closeBtnMouseEntered

    private void closeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseExited
        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/close.png")));
    }//GEN-LAST:event_closeBtnMouseExited

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       deleteSelected();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        JLabel label = new JLabel();
        label.setBounds(440, 160, 48, 21);
        parent2.add(label);
        parent2.revalidate();
        parent2.repaint();
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        unselectAll();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        selectAll();
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        jLabel9.setForeground(new Color(133, 88, 111));
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
       jLabel9.setForeground(Color.black);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
          jLabel11.setForeground(new Color(133, 88, 111));
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
          jLabel11.setForeground(Color.black);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
           jLabel10.setForeground(new Color(133, 88, 111));
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
            jLabel10.setForeground(Color.black);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        selectAll();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
       unselectAll();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jPanel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseEntered
        jPanel11.setBackground(new Color(178, 140, 151));
    }//GEN-LAST:event_jPanel11MouseEntered

    private void jPanel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseExited
       resetPanelColor(jPanel11);
    }//GEN-LAST:event_jPanel11MouseExited

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        jPanel7.setBackground(new Color(178, 140, 151));
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
         resetPanelColor(jPanel7);
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        if(!currentCard.equals("browse")) {
            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "browse");
            currentCard = "browse";
            subBar.add(jPanel4);
            subBar.add(jPanel8);
            searchTextField.setEnabled(true);
            xPos = 0;
            jScrollPane1.setWheelScrollingEnabled(true);
            jScrollPane1.getVerticalScrollBar().setEnabled(true);
            searchTextField.setFocusable(true);
            jTextArea1.setFocusable(true);
            isOpen = false;
        }        
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "cart");
        currentCard = "cart";
        subBar.removeAll();
        subBar.repaint();
        subBar.revalidate();
        xPos = 0;
        searchTextField.setEnabled(true);
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.getVerticalScrollBar().setEnabled(true);
        searchTextField.setFocusable(true);
        jTextArea1.setFocusable(true);
        isOpen = false;
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "cart");
        currentCard = "cart";
         
         
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        boolean radioButtonSelected = false;
        mName.setFocusable(true);
        lName.setFocusable(true);
        street.setFocusable(true);
        barangay.setFocusable(true);
        city.setFocusable(true);
        zip.setFocusable(true);
        mobileno.setFocusable(true);
        accoutname.setFocusable(true);
        accountno.setFocusable(true);
        province.setFocusable(true);
        mobileno.setFocusable(true);
        voucherfield.setFocusable(true);
        fName.setFocusable(true);
        if (cartContentPanel.getComponentCount() == 1 && cartContentPanel.getComponent(0) == cartEmpty) {
            JOptionPane.showMessageDialog(getInstance(), "Cart is Empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Component[] components = cartContentPanel.getComponents();
            
            for (int i = components.length - 1; i >= 0; i--) {
                Component comp = components[i];
                if (comp instanceof JPanel) {
                    JPanel cartBookPanel = (JPanel) comp;
                    CustomRadioButton radioButton = (CustomRadioButton) cartBookPanel.getComponent(0);
                    if (radioButton.isSelected()) {
                        radioButtonSelected = true;
                        
                    }
                }
            }
            
            if (radioButtonSelected) {
                showOrderSummaryPanel(cartContentPanel, orderSumPanel);
                CardLayout cardLayout = (CardLayout) container.getLayout();
                cardLayout.show(container, "checkout");
                
            }
            else {
                JOptionPane.showMessageDialog(getInstance(), "No books selected for checkout.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        
    }//GEN-LAST:event_jPanel11MouseClicked

    private void voucherfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voucherfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_voucherfieldActionPerformed
       private void updateLabelState() {
           if (isSelected) {
               
               oldPass.setEchoChar('\u0000');
               jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
           } else {
               
               oldPass.setEchoChar('•');
               jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
           }
       }
       private void updateLabelState2() {
           if (isSelected2) {
               
               confirmPass.setEchoChar('\u0000');
               jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
           } else {
               
               confirmPass.setEchoChar('•');
               jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
           }
       }
       private void updateLabelState3() {
           if (isSelected3) {
               
               newPass.setEchoChar('\u0000');
               jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eye.png"))); // NOI18N
           } else {
               
               newPass.setEchoChar('•');
               jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/eyeblind.png"))); // NOI18N
           }
       }
       
      
    private void hamburgerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hamburgerMouseClicked
       
    }//GEN-LAST:event_hamburgerMouseClicked

    private void userPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPanelMouseClicked
      
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (DatabaseConnection.isDatabaseConnected()) {
                    // Retrieve customer information
                    List<User> customers = getUserInfo(loggedId);
                    if (!customers.isEmpty()) {
                        User user = customers.get(0);
                        customerusername = user.getUsername();
                        customerpassword = user.getPassword();
                        customeremail = user.getEmail();
                        customerbirthday = user.getBirthday();
                        customerprofilePicture = user.getProfilePicture();
                    }
                    
                } else {
                    // Database is not connected
                    JOptionPane.showMessageDialog(getInstance(), "Unable to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
            
            @Override
            protected void done() {
                setDefaultUserInfo(customerusername,customeremail,customerbirthday);
                setProfilePicture(customerprofilePicture);
                isProfileChanged = false;
                setWelcome(customerusername);
                resetPassFields();
                CardLayout cardLayout = (CardLayout) container.getLayout();
                cardLayout.show(container, "user");
                currentCard = "user";
                subBar.removeAll();
                subBar.repaint();
                subBar.revalidate();
                xPos = 0;
                searchTextField.setEnabled(true);
                jScrollPane1.setWheelScrollingEnabled(true);
                jScrollPane1.getVerticalScrollBar().setEnabled(true);
                searchTextField.setFocusable(true);
                jTextArea1.setFocusable(true);
                isOpen = false;
            }
        };
        
        worker.execute();

          
    }//GEN-LAST:event_userPanelMouseClicked

    private void userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userMouseClicked
        
    }//GEN-LAST:event_userMouseClicked
    private static void removeBookFromArrayList(String bookName) {
        BookItem bookToRemove = null;
        for (BookItem book : orderList) {
            if (book.getItemName().equals(bookName)) {
                bookToRemove = book;
                break;
            }
        }
        
        if (bookToRemove != null) {
            orderList.remove(bookToRemove);
            System.out.println("Book removed: " + bookName);
        }
    }
    private void fNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fNameActionPerformed
    private void resetPassFields() {
        oldPass.setText("");
        newPass.setText("");
        confirmPass.setText("");
    }
    private void mNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mNameActionPerformed

    private void lNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lNameActionPerformed

    private void streetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_streetActionPerformed

    private void barangayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barangayActionPerformed

    private void cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityActionPerformed

    private void provinceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provinceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_provinceActionPerformed

    private void zipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_zipActionPerformed

    private void mobilenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilenoActionPerformed

    private void accoutnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accoutnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accoutnameActionPerformed

    private void accountnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountnoActionPerformed

    private void applyVoucherPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyVoucherPanelMouseEntered
        applyVoucherPanel.setBackground(new java.awt.Color(178, 140, 151));
    }//GEN-LAST:event_applyVoucherPanelMouseEntered

    private void applyVoucherPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyVoucherPanelMouseExited
        applyVoucherPanel.setBackground(new java.awt.Color(133, 88, 111));
    }//GEN-LAST:event_applyVoucherPanelMouseExited

    private void jPanel34MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel34MouseEntered
         jPanel34.setBackground(new java.awt.Color(178, 140, 151));
    }//GEN-LAST:event_jPanel34MouseEntered

    private void jPanel34MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel34MouseExited
         jPanel34.setBackground(new java.awt.Color(133, 88, 111));
    }//GEN-LAST:event_jPanel34MouseExited

    private void removeVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVoucherMouseClicked
        // Reset the voucher total value label
        voucherValue.setText("-₱0.00");
        
        // Reset the amount due total
        double subtotal = Double.parseDouble(subTotalValue.getText().substring(1));
        amountDue.setText(String.format("₱%.2f", subtotal));
        removeVoucherLabel();
    }//GEN-LAST:event_removeVoucherMouseClicked

    private void removeVoucherMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVoucherMouseEntered
       removeVoucher.setForeground(Color.black);
                              
    }//GEN-LAST:event_removeVoucherMouseEntered

    private void removeVoucherMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVoucherMouseExited
        removeVoucher.setForeground(new Color(133, 88, 111));
    }//GEN-LAST:event_removeVoucherMouseExited

    private void applyVoucherPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyVoucherPanelMouseClicked
        String voucherCode = voucherfield.getText().trim();
        if(voucherCode.equals("")) {
            return;
        }
        if (voucherSystem.isVoucherCodeValid(voucherCode)) {
            if (voucherSystem.isVoucherExpired(voucherCode)) {
                JOptionPane.showMessageDialog(getInstance(), "The voucher has expired.", "Error", JOptionPane.ERROR_MESSAGE);
                
            } else {
                double voucherAmount = voucherSystem.getVoucherAmount(voucherCode);
                System.out.println("Voucher amount: " + voucherAmount);
                
                // Update the voucher value label
                voucherValue.setText(String.format("-₱%.2f", voucherAmount));
                
                // Get the subtotal value
                double subtotal = 0.00;
                if (!subTotalValue.getText().isEmpty()) {
                    subtotal = Double.parseDouble(subTotalValue.getText().substring(1));
                }
                
                // Update the amount due total if orderSumPanel is not empty
                if (subtotal > 0.00) {
                    double newAmountDue = Math.max(0.00, subtotal - voucherAmount);
                    amountDue.setText(String.format("₱%.2f", newAmountDue));
                }
                
                addRemoveVoucherLabel();
            }
        } else {
            JOptionPane.showMessageDialog(getInstance(), "Invalid voucher code.", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
    }//GEN-LAST:event_applyVoucherPanelMouseClicked
    private boolean isFilledUp() {
        boolean isAllFieldsFilled = true;
        String firstName = fName.getText();
        String middleName = mName.getText();
        String lastName = lName.getText();
        String streetName = street.getText();
        String barangayName = barangay.getText();
        String cityName = city.getText();
        String provinceName = province.getText();
        String zipCode = zip.getText();
        String mobileNo = mobileno.getText();
        String selectedOption = (String) paymentMethod.getSelectedItem();
        
        if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || streetName.isEmpty() ||
                barangayName.isEmpty() || cityName.isEmpty() || provinceName.isEmpty() || zipCode.isEmpty() ||
                mobileNo.isEmpty()) {
            isAllFieldsFilled = false;
            JOptionPane.showMessageDialog(getInstance(), "Please enter your  personal details.", "Error", JOptionPane.ERROR_MESSAGE);
            
        }else {
            // Check if mobile number has at least 10 digits
            String mobileNumberWithoutPrefix = mobileNo.replace("+63", "").replaceAll("\\D", "");
            if (mobileNumberWithoutPrefix.length() < 10) {
                isAllFieldsFilled = false;
                JOptionPane.showMessageDialog(getInstance(), "Please enter valid mobile no.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
        
        
        if (isAllFieldsFilled) {
            // Check account name and account number fields
            String accountName = accoutname.getText();
            String accountNo = accountno.getText();
            
            if (!selectedOption.equals("COD")) {
                if (accountName.isEmpty() || accountNo.isEmpty()) {
                    isAllFieldsFilled = false;
                    JOptionPane.showMessageDialog(getInstance(), "Please enter your bank details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }

        return isAllFieldsFilled;
  }
    private void jPanel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel34MouseClicked
        if(!isFilledUp()) {
            return;
        }
        
        String firstName = fName.getText();
        String middleName = mName.getText();
        String lastName = lName.getText();
        String streetName = street.getText();
        String barangayName = barangay.getText();
        String cityName = city.getText();
        String provinceName = province.getText();
        String zipCode = zip.getText();
        String mobileNo = mobileno.getText();
        String selectedOption = (String) paymentMethod.getSelectedItem();
        String fullName = firstName + " " + middleName + " " + lastName;
        String address = streetName + " " + barangayName + " " + cityName + " " + provinceName + " " + zipCode;
        Invoice invoice = new Invoice(orderList,fullName, address,mobileNo,selectedOption);
        InvoicePanel invoicePanel = new InvoicePanel(orderList,fullName, address,mobileNo,selectedOption);
        JFrame parentFrame = getInstance();
        Receipt receipt = new Receipt(parentFrame,true,invoicePanel);
        receipt.setInvoicePanel(invoicePanel);
        receipt.setLocationRelativeTo(parentFrame);
        receipt.setVisible(true);
        
        
    }//GEN-LAST:event_jPanel34MouseClicked

    private void chooseFileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseFileMouseEntered
         chooseFile.setBackground(new java.awt.Color(178, 140, 151));
    }//GEN-LAST:event_chooseFileMouseEntered

    private void chooseFileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseFileMouseExited
         chooseFile.setBackground(new java.awt.Color(133, 88, 111));
    }//GEN-LAST:event_chooseFileMouseExited

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void birthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthdayActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
          isSelected = !isSelected;
                updateLabelState();
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        isSelected2 = !isSelected2;
                updateLabelState2();
    }//GEN-LAST:event_jLabel37MouseClicked

    private void saveInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveInfoMouseClicked
    
  
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                
                if (DatabaseConnection.isDatabaseConnected()) {
                    
                    // Create a SwingWorker to perform the database operations
                    // Retrieve customer information
                    List<User> customers = getUserInfo(loggedId);
                    if (!customers.isEmpty()) {
                        User user = customers.get(0);
                        customerusername = user.getUsername();
                        customerpassword = user.getPassword();
                        customeremail = user.getEmail();
                        customerbirthday = user.getBirthday();
                        customerprofilePicture = user.getProfilePicture();
                    }
                    
                    
                    String username = userName.getText().trim().replaceAll("\\s+", "");
                    String useremail = email.getText().trim().replaceAll("\\s+", "");
                    String userbirthday = birthday.getText().trim().replaceAll("\\s+", "");
                    String currentpassword = oldPass.getText().trim().replaceAll("\\s+", "");
                    String newpassword = newPass.getText().trim().replaceAll("\\s+", "");
                    String confirmpass = confirmPass.getText().trim().replaceAll("\\s+", "");
                    
                    // Create a DateTimeFormatter for the input format
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Parse the birthday string into a LocalDate object
                    LocalDate birth = LocalDate.parse(customerbirthday, inputFormatter);
                    // Create a DateTimeFormatter for the output format
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    // Format the birthday as a string in the desired format
                    String formattedBirthday = birth.format(outputFormatter);
                    
                    byte[] user_picture = convertLabelIconToBytes(profileLabel);
                    // Check if required fields are not empty
                    if (username.isEmpty() || useremail.isEmpty() || userbirthday.isEmpty()) {
                        JOptionPane.showMessageDialog(getInstance(), "Please fill in all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    boolean hasChanges = false; // Flag to track if any changes have been made
                    
                    // Check if username has changed
                    if (!username.equals(customerusername)) {
                        hasChanges = true;
                        System.out.println("Username has changed.");
                    }
                    
                    // Check if birthday has changed
                    if (!userbirthday.equals(formattedBirthday)) {
                        hasChanges = true;
                        System.out.println("Birthday has changed.");
                    }
                    
                    // Check if email has changed
                    if (!useremail.equals(customeremail)) {
                        hasChanges = true;
                        System.out.println("Email has changed.");
                    }
                    // Check if profile has changed
                    if (isProfileChanged && (!Objects.deepEquals(user_picture, customerprofilePicture) || (customerprofilePicture == null && user_picture != null))) {
                        hasChanges = true;
                        System.out.println("Profile picture has changed.");
                    }
                    // If no changes detected, show message and return
                    if (!hasChanges && newpassword.isEmpty() && confirmpass.isEmpty() && currentpassword.isEmpty()) {
                        JOptionPane.showMessageDialog(getInstance(), "No changes were made.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                    // If customerprofilePicture is null and there is no profile picture change, set user_picture to null
                    if (customerprofilePicture == null && !isProfileChanged) {
                        user_picture = null;
                    }
                    // Validate the username
                    String usernameErrorMessage = Index.isValidUsername(username);
                    
                    if (usernameErrorMessage != null) {
                        JOptionPane.showMessageDialog(getInstance(), usernameErrorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    // Check if the username is different from the current username
                    if (!username.equals(customerusername)) {
                        // Check for duplicate username
                        String duplicateUsername = Index.checkDuplicateUser(username, null);
                        if (duplicateUsername != null) {
                            JOptionPane.showMessageDialog(getInstance(), duplicateUsername, "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                    
                    // Check if the email is different from the current email
                    if (!useremail.equals(customeremail)) {
                        // Check for duplicate email
                        String duplicateEmail = Index.checkDuplicateUser(null, useremail);
                        if (duplicateEmail != null) {
                            JOptionPane.showMessageDialog(getInstance(), duplicateEmail, "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                    // Validate the email address
                    if (!Index.isGmailAddress(useremail)) {
                        // Email is not a valid Gmail address
                        JOptionPane.showMessageDialog(getInstance(), "Email must be a valid Gmail address.", "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                        
                    }
                    if (!Index.isValidDate(userbirthday)) {
                        JOptionPane.showMessageDialog(getInstance(), "Invalid date format. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    
                    
                    
                    // Compare passwords if necessary
                    if (!newpassword.isEmpty() || !confirmpass.isEmpty()) {
                        // Check if the current password is empty
                        if (currentpassword.isEmpty()) {
                            JOptionPane.showMessageDialog(getInstance(), "Please enter your current password.", "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        } else {
                            // Compare current password
                            if (!comparePasswords(currentpassword.toCharArray(), customerpassword.toCharArray())) {
                                JOptionPane.showMessageDialog(getInstance(), "Incorrect current password.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check if new password field is empty
                            if (newpassword.isEmpty()) {
                                JOptionPane.showMessageDialog(getInstance(), "Please enter a new password.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check password strength
                            PasswordStrength passwordStrength = Index.getPasswordStrength(newpassword);
                            
                            if (!passwordStrength.hasUppercase()) {
                                JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one uppercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            if (!passwordStrength.hasLowercase()) {
                                JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one lowercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            if (!passwordStrength.hasNumeric()) {
                                JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one numeric digit.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            if (!passwordStrength.hasSpecial()) {
                                JOptionPane.showMessageDialog(getInstance(), "Password must contain at least one special character.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            if (!passwordStrength.isLengthValid()) {
                                JOptionPane.showMessageDialog(getInstance(), "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check if confirm password field is empty
                            if (confirmpass.isEmpty()) {
                                JOptionPane.showMessageDialog(getInstance(), "Please confirm your new password.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check if new password and confirm password match
                            if (!newpassword.equals(confirmpass)) {
                                JOptionPane.showMessageDialog(getInstance(), "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check if the current password matches the customer's password
                            if (!comparePasswords(currentpassword.toCharArray(), customerpassword.toCharArray())) {
                                JOptionPane.showMessageDialog(getInstance(), "Incorrect current password.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                            
                            // Check if new password is the same as the current password
                            if (comparePasswords(newpassword.toCharArray(), customerpassword.toCharArray())) {
                                JOptionPane.showMessageDialog(getInstance(), "New password cannot be the same as the current password.", "Error", JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                        }
                        isPasswordChanged = true;
                    } else {
                        newpassword = null;
                        // Handle empty password fields separately
                        if (!currentpassword.isEmpty()) {
                            JOptionPane.showMessageDialog(getInstance(), "Please enter a new password.", "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                            
                        }
                    }
          
                    // Password is not changed, pass the current password to updateUserInfo
                    updateUserInfo(loggedId, username, customerpassword, useremail, user_picture, userbirthday, isPasswordChanged ? newpassword : null);
                    setProfilePicture(user_picture);
                    isProfileChanged = false;
                    hasChanges = false;
                    setWelcome(username);
                    resetPassFields();
                    JOptionPane.showMessageDialog(getInstance(), "User information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Database is not connected
                    JOptionPane.showMessageDialog(getInstance(), "Unable to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
            
            @Override
            protected void done() {
                if (isPasswordChanged) {
                    // Dispose the current frame and show the Index class
                    dispose();
                    Index index = new Index();
                    index.setVisible(true);
                }
                
            }
        };
        
        worker.execute();
    }//GEN-LAST:event_saveInfoMouseClicked
    public ImageIcon convertToImageIcon(Icon icon) {
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();
        
        return new ImageIcon(bufferedImage);
    }
    
    // Method to compare two byte arrays
    private boolean isByteArrayEqual(byte[] array1, byte[] array2) {
        return Arrays.equals(array1, array2);
    }
    public byte[] convertLabelIconToBytes(JLabel label) {
        Icon icon = label.getIcon();
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon) icon).getImage();
            
            BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
            );
            
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baos.toByteArray();
        }
        
        return null; // Return null if the label doesn't have an ImageIcon
    }
    private void saveInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveInfoMouseEntered
        saveInfo.setBackground(new java.awt.Color(178, 140, 151));    
    }//GEN-LAST:event_saveInfoMouseEntered
    
    private void saveInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveInfoMouseExited
        saveInfo.setBackground(new java.awt.Color(133, 88, 111));
    }//GEN-LAST:event_saveInfoMouseExited
    
    private void deleteAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAccountMouseClicked
        int choice = JOptionPane.showConfirmDialog(getInstance(), "Are you sure you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            deleteAccount(loggedId);
            JOptionPane.showMessageDialog(getInstance(), "Account deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            Index index = new  Index();
            index.setVisible(true);
        }
    }//GEN-LAST:event_deleteAccountMouseClicked
    private static void deleteAccount(int userId) {
        // Simulating database deletion
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                String sql = "DELETE FROM users WHERE user_id=?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.executeUpdate();
                System.out.println("User deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    private void updateUserInfo(int userId, String username, String password, String email, byte[] profilePicture, String birthday, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                String sql = "UPDATE users SET username=?, password=?, email=?, profile_picture=?, birthday=? WHERE user_id=?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(3, email);
                // Update the user information in the database, including the new password if provided
                // ...
                if (newPassword != null) {
                    statement.setString(2, newPassword);
                } else {
                    statement.setString(2, password);
                }
                // ...
                
                // Parse and format the birthday value
                DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = inputFormat.parse(birthday);
                String formattedBirthday = outputFormat.format(parsedDate);
                statement.setBytes(4, profilePicture != null ? profilePicture : null);
                statement.setString(5, formattedBirthday);
                statement.setInt(6, userId);
                
                
                statement.executeUpdate();
                System.out.println("User information updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    private static byte[] getImageBytes(ImageIcon imageIcon) {
        if (imageIcon != null) {
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.createGraphics();
            imageIcon.paintIcon(null, g, 0, 0);
            g.dispose();
            
            try {
                File tempFile = File.createTempFile("temp", ".png");
                ImageIO.write(bufferedImage, "png", tempFile);
                return java.nio.file.Files.readAllBytes(tempFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    private void deleteAccountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAccountMouseEntered
         deleteAccount.setBackground(new java.awt.Color(178, 140, 151));    
    }//GEN-LAST:event_deleteAccountMouseEntered

    private void deleteAccountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAccountMouseExited
        deleteAccount.setBackground(new java.awt.Color(133, 88, 111));
    }//GEN-LAST:event_deleteAccountMouseExited

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        isSelected3 = !isSelected3;
          updateLabelState3();       
    }//GEN-LAST:event_jLabel41MouseClicked
    private void addRemoveVoucherLabel() {
        totalPanel.add(removeVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 76, 80, 20));
        // Show the "Remove Voucher" label
        removeVoucher.setForeground(new Color(133, 88, 111));
        totalPanel.revalidate();
        totalPanel.repaint();
        // You can add additional code here if needed
}

    private void removeVoucherLabel() {
        totalPanel.remove(removeVoucher); // Hide the "Remove Voucher" label
        // You can add additional code here if needed
        totalPanel.revalidate();
        totalPanel.repaint();
}
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }
    private BufferedImage resizeImageFromByte(byte[] imageData, int width, int height) {
        try {
            // Convert byte array to BufferedImage
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(bais);
            
            if (originalImage != null) {
                // Create a new BufferedImage with the desired type
                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                
                // Resize the image
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(originalImage, 0, 0, width, height, null);
                g2d.dispose();
                
                return resizedImage;
            } else {
                System.out.println("Error: Unsupported or invalid image format");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null; // Return null if there's an error
        
    }
    private MouseListener hamburgerMouseListener = new MouseAdapter() {
        
        
        @Override
        public void mouseClicked(MouseEvent evt) {
            
            if (isAnimating) {
                return; // Ignore the click if an animation is already in progress
            }
            
            if (xPos == 0) {
//            searchTf.setEnabled(false);
searchTextField.setFocusable(false);
jTextArea1.setFocusable(false);
jScrollPane1.setWheelScrollingEnabled(false);
jScrollPane1.getVerticalScrollBar().setEnabled(false);
fName.setFocusable(false);
mName.setFocusable(false);
lName.setFocusable(false);
street.setFocusable(false);
barangay.setFocusable(false);
city.setFocusable(false);
zip.setFocusable(false);
mobileno.setFocusable(false);
province.setFocusable(false);
accoutname.setFocusable(false);
accountno.setFocusable(false);
voucherfield.setFocusable(false);

sideBarPanel.setSize(0, 660);
int targetWidth = 250; // Target width for the panel
int animationDuration = 250; // Duration of the animation in milliseconds
int animationDelay = 5; // Delay between each step of the animation in milliseconds
int steps = animationDuration / animationDelay; // Number of steps

int stepSize = (targetWidth - xPos) / steps; // Amount to change the panel size in each step
animationTimer = new Timer(animationDelay, new ActionListener() {
    private int currentStep = 0;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        xPos += stepSize;
        if (xPos > targetWidth) {
            xPos = targetWidth; // Limit the width to the target width
        }
        sideBarPanel.setSize(xPos, 660);
        repaint();
        
        currentStep++;
        if (currentStep >= steps) {
            ((Timer) e.getSource()).stop();
            xPos = targetWidth;
            isAnimating = false; // Animation completed
            isOpen = true;
        }
    }
});
animationTimer.start();
isAnimating = true; // Animation started

            }
        }
        
    };
    
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            
               
            }
        });
    }
    public void setPanelColor(JPanel panel, Color color) {
        panel.setBackground(color);
        
    }
    public void resetPanelColor(JPanel panel) {
        panel.setBackground(new Color(133, 88, 111));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountno;
    private javax.swing.JTextField accoutname;
    private static javax.swing.JLabel amountDue;
    private javax.swing.JLabel applyVoucher;
    private javax.swing.JPanel applyVoucherPanel;
    private javax.swing.JTextField barangay;
    private javax.swing.JTextField birthday;
    private javax.swing.JPanel bookDescription;
    private javax.swing.JPanel browse;
    private javax.swing.JLabel browseLabel;
    private javax.swing.JLabel browseLogo;
    private javax.swing.JPanel browsePanel;
    private javax.swing.JPanel cart;
    private javax.swing.JLabel cartCount;
    private javax.swing.JPanel cartEmpty;
    private javax.swing.JLabel cartLabel;
    private javax.swing.JLabel cartLogo;
    private javax.swing.JLabel cartLogoCount;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JPanel checkOut;
    private javax.swing.JPanel chooseFile;
    private javax.swing.JTextField city;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JPasswordField confirmPass;
    private static javax.swing.JPanel container;
    private javax.swing.JPanel deleteAccount;
    private javax.swing.JTextField email;
    private javax.swing.JLabel exitBtn;
    private javax.swing.JTextField fName;
    private javax.swing.JPanel faq;
    private javax.swing.JLabel faqLabel;
    private javax.swing.JLabel faqLogo;
    private javax.swing.JPanel faqPanel;
    private javax.swing.JLabel hamburger;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField lName;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel logoutLogo;
    private javax.swing.JPanel logoutPanel;
    private javax.swing.JTextField mName;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel message;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel messageLogo;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JLabel minimizeBtn;
    private javax.swing.JTextField mobileno;
    private javax.swing.JPasswordField newPass;
    private javax.swing.JPasswordField oldPass;
    private javax.swing.JPanel orderSumMain;
    private javax.swing.JPanel orderSumPanel;
    private javax.swing.JPanel parent;
    private javax.swing.JPanel parent2;
    private javax.swing.JComboBox<String> paymentMethod;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JLabel profileLabel2;
    private javax.swing.JTextField province;
    private javax.swing.JLabel removeVoucher;
    private static javax.swing.JLabel returnLabel;
    private javax.swing.JPanel saveInfo;
    private javax.swing.JLabel searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel sideBarPanel;
    private javax.swing.JTextField street;
    public javax.swing.JPanel subBar;
    private static javax.swing.JLabel subTotalValue;
    private javax.swing.JPanel titleBar;
    private javax.swing.JPanel totalPanel;
    private javax.swing.JLabel totalValue;
    private javax.swing.JPanel user;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLogo;
    private javax.swing.JTextField userName;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JPanel userPanel;
    private static javax.swing.JLabel voucherValue;
    private javax.swing.JTextField voucherfield;
    private javax.swing.JTextField zip;
    // End of variables declaration//GEN-END:variables
}
