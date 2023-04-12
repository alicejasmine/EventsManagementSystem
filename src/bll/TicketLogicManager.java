package bll;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import be.*;
import be.Event;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dal.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;

public class TicketLogicManager {

    private TicketDAO ticketDAO = new TicketDAO();
    private EventDAO eventDAO = new EventDAO();
    private TicketTypeDAO ticketTypeDAO = new TicketTypeDAO();
    private SpecialTicketDAO specialTicketDAO = new SpecialTicketDAO();


    public static void main(String[] args) throws IOException, WriterException, PrinterException {

    }

    public List<Ticket> searchTickets(String query, int idOfEvent) {
        List<Ticket> tickets = ticketDAO.getTicketForEvent(idOfEvent);
        List<Ticket> filtered = new ArrayList<>();

        for (Ticket t : tickets) {
            if (("" + t.getTicketID()).contains(query) || t.getCustomerName().toLowerCase().contains(query.toLowerCase()) || t.getCustomerEmail().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    public void crateTicket(String customerName, String customerEmail, int eventID) {
        String str = generateType1UUID().toString();
        Ticket ticket = new Ticket(str, customerName, customerEmail, eventID);
        ticketDAO.createTicket(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        ticketDAO.deleteTicket(ticket);
    }

    /**
     * Method to print event info on a ticket using Apache PDFbox libraries and QR code
     */
    public static PDDocument writeEventInfoOnTicket(Event event, Ticket ticket) {

        try {
            String inputFilePath = "resources/TicketsBackground/Ticket-background.pdf";
            File inputFile = new File(inputFilePath);
            PDDocument doc = Loader.loadPDF(inputFile);
            PDPage page = doc.getPage(0);

            //Create a new content stream to write on the ticket
            //5th parameters needs to be true to have right coordinate system and text not upside down
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);


            PDFont pdfFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            contentStream.setFont(pdfFont, 25);
            contentStream.setNonStrokingColor(Color.white);

            PDRectangle pageSize = page.getMediaBox();
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();

            float margin = 20;
            float x = pageWidth / 4;
            float y = pageHeight - margin;

            //Write Event Info
            contentStream.beginText();

            contentStream.newLineAtOffset(x, y);
            contentStream.showText(event.getName());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 30));
            contentStream.setFont(pdfFont, 18);
            contentStream.newLine();
            contentStream.showText(event.getLocation());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 50));
            contentStream.newLine();
            contentStream.showText(event.getDate().toString());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 70));
            contentStream.newLine();
            contentStream.showText("Start time: " + event.getTime().toString());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 90));
            contentStream.newLine();
            contentStream.showText(event.getNotes());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 110));
            contentStream.newLine();
            contentStream.showText("End Time: " + event.getEndTime().toString());

            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 130));
            contentStream.newLine();
            contentStream.showText("Location guidance: " + event.getLocationGuidance());

            contentStream.endText();


            //Write QR CODE

            int qrCodeSize = 80;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(ticket.getTicketID(), BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            PDImageXObject qrImageXObject = LosslessFactory.createFromImage(doc, qrImage);
            contentStream.drawImage(qrImageXObject, margin, (pageHeight - qrCodeSize) / 2 + 5, qrCodeSize, qrCodeSize);
            contentStream.close();
            return doc;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Method to print event info on a SpecialTicket using Apache PDFbox libraries and QR code
     */
    public static PDDocument writeEventInfoOnSpecialTicket(Event event, SpecialTicket specialTicket, TicketType ticketType) {

        try {
            String inputFilePath = "resources/TicketsBackground/SpecialTicket-background.pdf";
            File inputFile = new File(inputFilePath);
            PDDocument doc = Loader.loadPDF(inputFile);
            PDPage page = doc.getPage(0);

            //Create a new content stream to write on the ticket
            //5th parameters needs to be true to have right coordinate system and text not upside down
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);

            PDFont pdfFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            contentStream.setFont(pdfFont, 25);
            contentStream.setNonStrokingColor(Color.white);

            PDRectangle pageSize = page.getMediaBox();
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();

            float margin = 20;
            float x = pageWidth / 4;
            float y = pageHeight - margin;

            //Write Event Info
            contentStream.beginText();

            if (event.getName().equals("No Event")) {

                contentStream.newLineAtOffset(x, y - 80);
                contentStream.setFont(pdfFont, 25);
                contentStream.showText(ticketType.getTicketTypeName());

            } else {
                contentStream.newLineAtOffset(x, y-30);
                contentStream.showText(ticketType.getTicketTypeName());

                contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 70));
                contentStream.setFont(pdfFont, 18);
                contentStream.newLine();
                contentStream.showText(event.getName());

                contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 90));
                contentStream.newLine();
                contentStream.showText(event.getDate().toString());

                contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 110));
                contentStream.newLine();
                contentStream.showText("Start time: " + event.getTime().toString());

            }
            contentStream.endText();


            //Write QR CODE

            int qrCodeSize = 80;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(specialTicket.getSpecialTicketID(), BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            PDImageXObject qrImageXObject = LosslessFactory.createFromImage(doc, qrImage);
            contentStream.drawImage(qrImageXObject, margin, (pageHeight - qrCodeSize) / 2 + 5, qrCodeSize, qrCodeSize);
            contentStream.close();
            return doc;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * method to save the ticket with the last 4 digits of the ticketID
     */
    public static void saveTicket(Event event, Ticket ticket) {
        try {
            PDDocument document = writeEventInfoOnTicket(event, ticket);
            String s = ticket.getTicketID();
            String outputFilePath = "resources/Tickets/Ticket-" + event.getName() + "-" + s.substring(s.length() - 4) + ".pdf";
            File outputFile = new File(outputFilePath);
            document.save(outputFile);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to save the special ticket with the last 4 digits of the ticketID
     *
     * @return
     */
    public static void saveSpecialTicket(Event event, SpecialTicket specialTicket, TicketType ticketType) {
        try {
            PDDocument document = writeEventInfoOnSpecialTicket(event, specialTicket, ticketType);
            String s = specialTicket.getSpecialTicketID();
            String outputFilePath = "resources/SpecialTickets/SpecialTicket-" + event.getName() + "-" + s.substring(s.length() - 4) + ".pdf";
            File outputFile = new File(outputFilePath);
            document.save(outputFile);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * method to print the ticket
     */
    public void printTicket(PDDocument document) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            if (job.printDialog()) {
                job.print();
            }
            document.close();
        } catch (IOException | PrinterException e) {

        }
    }


    /**
     * method to print the ticket
     */
    public void printSpecialTicket(PDDocument document) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            if (job.printDialog()) {
                job.print();
            }
            document.close();
        } catch (IOException | PrinterException e) {

        }
    }

    public ImageView createTicketPreview(Event event, Ticket ticket) throws IOException {
        if (event != null) {
            PDDocument document = writeEventInfoOnTicket(event, ticket);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bufferedImage = pdfRenderer.renderImage(0);
            WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView imageView = new ImageView(fxImage);
            return imageView;
        }
        throw new IllegalStateException("Event cannot be null");
    }


    public ImageView createSpecialTicketPreview(Event event, SpecialTicket specialTicket, TicketType ticketType) throws IOException {
        if (event != null) {
            PDDocument document = writeEventInfoOnSpecialTicket(event, specialTicket, ticketType);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bufferedImage = pdfRenderer.renderImage(0);
            WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView imageView = new ImageView(fxImage);
            return imageView;
        }
        throw new IllegalStateException("Event cannot be null");
    }

    private static long get64LeastSignificantBits() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }

    private static long get64MostSignificantBits() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }

    public static UUID generateType1UUID() {
        long most64SigBits = get64MostSignificantBits();
        long least64SigBits = get64LeastSignificantBits();
        return new UUID(most64SigBits, least64SigBits);
    }


    public void addTicketType(String ticketTypeName) {
        TicketType ticketType = new TicketType(ticketTypeName);
        ticketTypeDAO.createTicketType(ticketType);
    }

    public List<SpecialTicket> createSpecialTicket(TicketType selectedTicketType, Event selectedEvent, int maxQuantity) {

        return specialTicketDAO.createSpecialTicket(selectedTicketType, selectedEvent, maxQuantity);

    }


    public List<TicketType> getTicketTypes() {
        return ticketTypeDAO.getAllTicketTypes();
    }

    public ObservableList getSpecialTicketsInfo() {
        return specialTicketDAO.getSpecialTicketsInfo();

    }

    public ObservableList getSpecialTicketOverview() {
        try {
            return specialTicketDAO.getSpecialTicketOverview();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteSpecialTicket(SpecialTicket ticket) {
        specialTicketDAO.deleteSpecialTicket(ticket);
    }


}
