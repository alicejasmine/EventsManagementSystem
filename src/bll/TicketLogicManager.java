package bll;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.util.Matrix;

public class TicketLogicManager {


    public static void main(String[] args) throws IOException {
        writeEventInfoOnPDF();
    }

    public static void writeEventInfoOnPDF() throws IOException {


        String inputFilePath = "resources/ticket-party.pdf";
        String outputFilePath = "resources/ticket-party-edit.pdf";
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
        contentStream.showText("EASV PARTY");

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 30));
        contentStream.setFont(pdfFont, 18);
        contentStream.newLine();
        contentStream.showText("Location: EASV Bar");

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 60));
        contentStream.newLine();
        contentStream.showText("Date: 2023-03-15");

        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y - 90));
        contentStream.newLine();
        contentStream.showText("Time: 10:00");
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
}

