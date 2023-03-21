package bll;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import be.Event;
import be.Ticket;
import dal.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.util.Matrix;

public class TicketLogicManager {

    private TicketDAO ticketDAO = new TicketDAO();

    public static void main(String[] args) throws IOException {
        //writeEventInfoOnPDF();
        //for(int i = 0; i < 100; i++){
        //    System.out.println(generateType1UUID());}
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    public void crateTicket(String customerName, String customerEmail, int eventID){
        String str = generateType1UUID().toString();
        Ticket ticket = new Ticket(str, customerName, customerEmail, eventID);
        ticketDAO.createTicket(ticket);
    }

    public static void writeEventInfoOnPDF(Event event, Ticket ticket) throws IOException {


        String inputFilePath = "resources/ticket-party.pdf";
        String outputFilePath = "resources/ticket-party"+ ticket.getTicketID()+ ".pdf";

        File inputFile = new File(inputFilePath);
        PDDocument doc = Loader.loadPDF(inputFile);
        PDPage page = doc.getPage(0);

        // Create a new content stream to write on the page
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
        //5th parameters needs to be true to have right coordinate system and text not upside down


        PDFont pdfFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        contentStream.setFont(pdfFont, 25);
        contentStream.setNonStrokingColor(Color.white);

        // Get the dimensions of the page
        PDRectangle pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();

        //Set the margin value
        float marginTop = 20;
        float x = pageWidth / 4;
        System.out.println(x);
        float y = pageHeight - marginTop;

        // Write the event information on the page
        contentStream.beginText();

        contentStream.newLineAtOffset(x, y);
        contentStream.showText(event.getName());

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 30));
        contentStream.setFont(pdfFont, 18);
        contentStream.newLine();
        contentStream.showText(event.getLocation());

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 60));
        contentStream.newLine();
        contentStream.showText(event.getDate().toString());

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 90));
        contentStream.newLine();
        contentStream.showText(event.getTime().toString());
        contentStream.endText();

        contentStream.close();
        File outputFile = new File(outputFilePath);
        doc.save(outputFile);
        doc.close();

        PDDocument editedDoc = Loader.loadPDF(new File(outputFilePath));

        //Read the contents of the page
        PDFTextStripper stripper = new PDFTextStripper();
        String pageText = stripper.getText(editedDoc);

        System.out.println(pageText);

        editedDoc.close();
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
}

