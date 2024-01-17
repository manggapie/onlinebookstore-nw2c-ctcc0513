
package com.jasmineenriquez.casestudy;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Jasmine Enriquez
 */
public class InvoicePanel extends JPanel implements Printable {
    private String customerFullName;
    private String customerMobileNo;
    private String customerAddress;
    private String paymentMethod;
    private String total;
    private List<BookItem> bookItem;
    private  LocalDate currentDate;
    private DateTimeFormatter formatter;
    String formattedDate;
    public InvoicePanel(List<BookItem> bookItem, String customerFullName, String customerAddress, String customerMobileNo, String paymentMethod) {
        this.bookItem = bookItem;
        this.customerMobileNo = customerMobileNo;
        this.paymentMethod = paymentMethod;
        this.customerFullName = customerFullName;
        this.customerAddress = customerAddress;
        setLayout(new BorderLayout());
        currentDate = LocalDate.now();
        // Format the current date to the desired string representation
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedDate = currentDate.format(formatter);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        double scaleX = 1.9;
        double scaleY = 1.9;
        
        // Apply the scaling transformation
        g2d.scale(scaleX, scaleY);
        drawContent(g2d);
        // Dispose of the Graphics2D object
        g2d.dispose();
        
        
    }
    private int calculateTotalHeight() {
        int totalHeight = 50;
        int yShift = 10;
        int headerRectHeight = 15;
        
        // Calculate the height for each section of your content and add it to the total height
        totalHeight += 50 * yShift; // Height of the lines and text before "Bill to"
        totalHeight += headerRectHeight; // Height of the header rectangle
        
        // Calculate the height for the customer information section
        int maxLineWidth1 = 15;
        List<String> customerfname = splitTextIntoLines(customerFullName, maxLineWidth1);
        totalHeight += customerfname.size() * yShift;
        
        List<String> customeradd = splitTextIntoLines(customerAddress, maxLineWidth1);
        totalHeight += customeradd.size() * yShift;
        
        List<String> customerno = splitTextIntoLines(customerMobileNo, maxLineWidth1);
        totalHeight += customerno.size() * yShift;
        
        for (BookItem order : bookItem) {
            String bookName = order.getItemName();
            int maxLineWidth = 10; // Adjust the maximum width based on your requirements
            List<String> bookNameLines = splitTextIntoLines(bookName, maxLineWidth);
            totalHeight += bookNameLines.size() * yShift;
            
            String quantity = String.valueOf(order.getQuantity());
            List<String> quantityLines = splitTextIntoLines(quantity, maxLineWidth);
            totalHeight += quantityLines.size() * yShift;
            
            String price = order.getUnitPrice1();
            List<String> priceLines = splitTextIntoLines(price, maxLineWidth);
            totalHeight += priceLines.size() * yShift;
            
            String itemTotal = order.getTotal1();
            List<String> itemTotalLines = splitTextIntoLines(itemTotal, maxLineWidth);
            totalHeight += itemTotalLines.size() * yShift;
        }
        
        totalHeight += 10 * yShift; // Height of the lines and text after the book items
        totalHeight += 5 * yShift; // Height of the payment method text
        totalHeight += 10 * yShift; // Height of the lines and text after the payment method
        totalHeight += headerRectHeight; // Height of the footer rectangle
        
        return totalHeight;
    }
    @Override
    public Dimension getPreferredSize() {
        int totalHeight = calculateTotalHeight(); // Calculate the total height of the content
        int width = 300; // Set a fixed width for the panel
        return new Dimension(width, totalHeight);
    }
    
    
    private void drawContent(Graphics g2d) {
        double scaleX = (double) getWidth() / getPreferredSize().width;
        double scaleY = (double) getHeight() / getPreferredSize().height;
        
        
        int newWidth = 150;
        int newHeight = 40;
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/logotrans.png"));
        
        
        
        int y = 15;
        int yShift = 10;
        int headerRectHeight = 15;
        // Assuming you want to resize the image to a width of 150 and height of 40
        
        
        g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
        g2d.drawImage(icon.getImage(), 40,10,newWidth,newHeight, null);y+=yShift+30;
        g2d.drawString("---------------------------------------",12,y);y+=yShift;
        g2d.drawString("              BOOKHAVEN                ",12,y);y+=yShift;
        g2d.drawString("          ONLINE BOOK STORE            ",12,y);y+=yShift;
        g2d.drawString("   Tortugas, Balanga City, Bataan      ",12,y);y+=yShift;
        g2d.drawString("     www.facebook.com/bookhaven        ",12,y);y+=yShift;
        g2d.drawString("            +639128562896              ",12,y);y+=yShift;
        g2d.drawString("---------------------------------------",12,y);y+=headerRectHeight;
        g2d.drawString(" Bill to:                  Date:       ",12,y);y+=yShift;
        g2d.drawString("---------------------------------------",12,y);y+=headerRectHeight;
        int customerNameOffsetX = 17;
        int dateOffsetX = 155;
        int customerAddOffsetX = 17;
        int customerMobileOffsetX = 17;
        
        int initialYCoordinate1 = y;
        
        int maxLineWidth1 = 15;
        List<String> customerfname = splitTextIntoLines(customerFullName,maxLineWidth1);
        // Print or draw the book name lines
        for (int i = 0; i < customerfname.size(); i++) {
            String line = customerfname.get(i);
            
            if (i == 0) {
                g2d.drawString(line, customerNameOffsetX, y);
                g2d.drawString(""+formattedDate+" ",dateOffsetX,y);
            } else {
                g2d.drawString(line, customerNameOffsetX, y);
            }
            
            y += yShift;
        }
        y += yShift-5;
        
        List<String> customeradd = splitTextIntoLines(customerAddress,maxLineWidth1);
        // Print or draw the book name lines
        for (int i = 0; i < customeradd.size(); i++) {
            String line = customeradd.get(i);
            g2d.drawString(line, customerAddOffsetX, y);
            
            y += yShift;
        }
        y += yShift-5;
        
        List<String> customerno = splitTextIntoLines(customerMobileNo,maxLineWidth1);
        // Print or draw the book name lines
        for (int i = 0; i < customerno.size(); i++) {
            String line = customerno.get(i);
            g2d.drawString(line, customerMobileOffsetX, y);
            
            y += yShift;
        }
        y += yShift-5;
        
        y = y + yShift-10;
        
        g2d.drawString("---------------------------------------",12,y);y+=headerRectHeight;
        g2d.drawString(" Book Name   Qty   Unit Price   Total  ",12,y);y+=yShift;
        g2d.drawString("---------------------------------------",12,y);y+=headerRectHeight;
        
        int nameOffsetX = 17; // X coordinate for the book name
        int quantityOffsetX = 85; // X coordinate for the quantity
        int priceOffsetX = 125; // X coordinate for the price
        int totalOffsetX = 178; // X coordinate for the total
        
// Use the existing `y` variable as the initial Y coordinate for drawing
int initialYCoordinate = y;

for (BookItem order : bookItem) {
    String bookName = order.getItemName();
    int bookNameLength = bookName.length();
    int maxLineWidth = 10; // Adjust the maximum width based on your requirements
    
    // Split the book name into multiple lines if it exceeds the maximum width
    List<String> bookNameLines = splitTextIntoLines(bookName, maxLineWidth);
    
    // Draw the book name lines
    for (int i = 0; i < bookNameLines.size(); i++) {
        String line = bookNameLines.get(i);
        
        // If it's the first line, align the quantity, price, and total to its position
        if (i == 0) {
            g2d.drawString(line, nameOffsetX, y);
            g2d.drawString(String.valueOf(order.getQuantity()), quantityOffsetX, y);
            g2d.drawString(String.valueOf(order.getUnitPrice1()), priceOffsetX, y);
            g2d.drawString(String.valueOf(order.getTotal1()), totalOffsetX, y);
        } else {
            g2d.drawString(line, nameOffsetX, y);
        }
        
        y += yShift;
    }
    
    // Add vertical spacing between book items
    y += yShift-5;
}

// Update the original `y` variable with the new Y coordinate after drawing book items
y = y + yShift-10;

g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Subtotal: ", 12,y);   g2d.drawString(""+MainMenu.getSubTotal()+" ",170,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Voucher: ",    12,y);    g2d.drawString(""+MainMenu.getVoucher()+" ",165,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Total: ",   12,y);     g2d.drawString(""+MainMenu.getAmountDue()+" ",170,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Payment Method:",12,y);
g2d.drawString(""+paymentMethod+" ",100,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString("***************************************",12,y);y+=yShift;
g2d.drawString("      THANK YOU FOR YOUR ORDER!        ",12,y);y+=yShift;
g2d.drawString("***************************************",12,y);y+=yShift;
g2d.drawString("   SOFTWARE BY: JASMINE D. ENRIQUEZ    ",12,y);y+=yShift;
g2d.drawString("   CONTACT: jdenriquez@bpsu.edu.ph     ",12,y);y+=yShift;


    }
    private List<String> splitTextIntoLines(String text, int maxLineWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 <= maxLineWidth) {
                currentLine.append(word).append(" ");
            } else {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
            }
        }
        
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
        
        return lines;
    }
    /**
     * Create a BufferedImage for Swing components. The entire component will be
     * captured to an image.
     *
     * @param component Swing component to create image from
     * @return  image the image for the given region
     */
    public static BufferedImage createImage(JComponent component)
    {
        Dimension d = component.getSize();
        
        if (d.width == 0 || d.height == 0)
        {
            d = component.getPreferredSize();
            component.setSize(d);
        }
        
        Rectangle region = new Rectangle(0, 0, d.width, d.height);
        return createImage(component, region);
    }
    
    /**
     * Create a BufferedImage for Swing components. All or part of the component
     * can be captured to an image.
     *
     * @param component Swing component to create image from
     * @param region The region of the component to be captured to an image
     * @return  image the image for the given region
     */
    public static BufferedImage createImage(JComponent component, Rectangle region)
    {
        //  Make sure the component has a size and has been layed out.
        //  (necessary check for components not added to a realized frame)
        
        if (!component.isDisplayable())
        {
            Dimension d = component.getSize();
            
            if (d.width == 0 || d.height == 0)
            {
                d = component.getPreferredSize();
                component.setSize(d);
            }
            
            component.invalidate();
            component.doLayout();
        }
        
        BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        //  Paint a background for non-opaque components,
        //  otherwise the background will be black
        
        if (!component.isOpaque())
        {
            g2d.setColor(component.getBackground());
            g2d.fillRect(region.x, region.y, region.width, region.height);
        }
        
        g2d.translate(-region.x, -region.y);
        component.paint(g2d);
        g2d.dispose();
        return image;
    }
    public void printInvoice() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        if (job.printDialog()) {
            try {
                job.print();
                
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        // Calculate the printable area dimensions
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight();
        double margin = 0; // Adjust the margin value as needed
        double printableWidth = pageWidth - (2 * margin);
        double printableHeight = pageHeight - (2 * margin);
        
        // Get the preferred dimensions of the invoice panel
        Dimension panelSize = this.getPreferredSize();
        double panelWidth = panelSize.getWidth();
        double panelHeight = panelSize.getHeight();
        
        // Calculate the scale factor to fit the panel within the printable area
        double scaleX = printableWidth / panelWidth;
        double scaleY = printableHeight / panelHeight;
        double scaleFactor = Math.min(scaleX, scaleY);
        
        // Calculate the scaled dimensions
        double scaledWidth = panelWidth * scaleFactor;
        double scaledHeight = panelHeight * scaleFactor;
        
        // Calculate the horizontal and vertical translations to center the scaled panel within the printable area
        double horizontalMargin = (printableWidth - scaledWidth) /2;
        double verticalMargin = (printableHeight - scaledHeight) /2 ;
        
        g2d.translate(margin + horizontalMargin - 53, margin + verticalMargin);
        
        // Scale the graphics context
        g2d.scale(scaleFactor, scaleFactor);
        
        // Print the invoice panel
        this.print(g2d);
        
        return PAGE_EXISTS;
        
        
    }
}
