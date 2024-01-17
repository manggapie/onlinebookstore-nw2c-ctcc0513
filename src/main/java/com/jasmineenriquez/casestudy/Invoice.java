
package com.jasmineenriquez.casestudy;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Jasmine Enriquez
 */

public class Invoice implements Printable {
    Double bHeight;
    private String customerFullName;
    private String customerMobileNo;
    private String customerAddress;
    private String paymentMethod;
    private String total;
    private List<BookItem> bookItem;
    private  LocalDate currentDate;
    private DateTimeFormatter formatter;
    String formattedDate;
    
    Invoice(List<BookItem> bookItem, String customerFullName, String customerAddress, String customerMobileNo, String paymentMethod) {
        this.bookItem = bookItem;
        this.customerMobileNo = customerMobileNo;
        this.paymentMethod = paymentMethod;
        this.customerFullName = customerFullName;
        this.customerAddress = customerAddress;
        bHeight = Double.valueOf(bookItem.size());
        currentDate = LocalDate.now();
        // Format the current date to the desired string representation
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedDate = currentDate.format(formatter);
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int newWidth = 150;
        int newHeight = 40;
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/jasmineenriquez/casestudy/logotrans.png"));
        
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;
            
            double width = pageFormat.getImageableWidth();
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
            try {
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
                int dateOffsetX = 157;
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
                int totalOffsetX = 183; // X coordinate for the total
                
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
g2d.drawString(" Subtotal: ", 12,y);   g2d.drawString(""+MainMenu.getSubTotal()+" ",178,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Voucher: ",    12,y);    g2d.drawString(""+MainMenu.getVoucher()+" ",173,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Total: ",   12,y);     g2d.drawString(""+MainMenu.getAmountDue()+" ",178,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString(" Payment Method:",12,y);
g2d.drawString(""+paymentMethod+" ",100,y);y+=yShift;
g2d.drawString("---------------------------------------",12,y);y+=yShift;
g2d.drawString("***************************************",12,y);y+=yShift;
g2d.drawString("      THANK YOU FOR YOUR ORDER!        ",12,y);y+=yShift;
g2d.drawString("***************************************",12,y);y+=yShift;
g2d.drawString("   SOFTWARE BY: JASMINE D. ENRIQUEZ    ",12,y);y+=yShift;
g2d.drawString("   CONTACT: jdenriquez@bpsu.edu.ph     ",12,y);y+=yShift;

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            result = PAGE_EXISTS;
        }
        return result;
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
    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        
        double bodyHeight = bHeight;
        
        double headerHeight = 5.0;
        
        double footerHeight = 5.0;
        
        double width = toPPI(4);
        
        double height = toPPI(headerHeight+bodyHeight+footerHeight);
        
        paper.setSize(width, height);
        
        paper.setImageableArea(0,10,width,height - cm_to_pp(1));
        
        pf.setOrientation(PageFormat.PORTRAIT);
        
        pf.setPaper(paper);
        
        return pf;
    }
    public List<BookItem> getBookItems() {
        return bookItem;
    }
    
    public String getCustomerFullName() {
        return customerFullName;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }
    
    public String getCustomerMobileNo() {
        return customerMobileNo;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
        
    }
    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }
    
    protected static double toPPI(double inch) {
        return inch * 72d;
    }
    
    public static final double CM_PER_INCH = 0.393700787d;
    public static final double INCH_PER_CM = 2.545d;
    public static final double INCH_PER_MM = 25.45d;
    
    public static double cmsToPixel(double cms, double dpi) {
        return cmToInches(cms) * dpi;
    }
    
    public static double pixelsToCms(double pixels, double dpi) {
        return inchesToCms(pixels / dpi);
    }
    
    public static double cmToInches(double cms) {
        return cms * CM_PER_INCH;
    }
    
    public static double inchesToCms(double inch) {
        return inch * INCH_PER_CM;
    }
    public void printInvoice() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this, getPageFormat(pj));
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterAbortException e) {
                // Handle printer abort exception
            } catch (PrinterException e) {
                // Handle printer exception
            }
        }
    }
    
}