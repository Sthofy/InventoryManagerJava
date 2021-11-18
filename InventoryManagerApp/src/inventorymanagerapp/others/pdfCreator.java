package inventorymanagerapp.others;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class pdfCreator {

    public static void CreatePDF(Purchases purchase, File path) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font fontData = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Paragraph title = new Paragraph(purchase.getAccountName(), fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(90);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);
            PdfPCell header1 = new PdfPCell(new Paragraph("Product Name", fontHeader));
            header1.setPadding(10f);
            header1.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell header2 = new PdfPCell(new Paragraph("Quantity", fontHeader));
            header2.setPadding(10f);
            header2.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell header3 = new PdfPCell(new Paragraph("Price per Product", fontHeader));
            header3.setPadding(10f);
            header3.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell header4 = new PdfPCell(new Paragraph("Amount", fontHeader));
            header4.setPadding(10f);
            header4.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell productName = new PdfPCell(new Paragraph(purchase.getItemName(), fontData));
            productName.setPadding(10f);
            productName.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            productName.setHorizontalAlignment(Element.ALIGN_CENTER);
            productName.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell quantity = new PdfPCell(new Paragraph(purchase.getQuantity().toString(), fontData));
            quantity.setPadding(10f);
            quantity.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            quantity.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell pricePerProcudt = new PdfPCell();
            Long pricePerProd = Math.round(purchase.getAmountDue() / purchase.getQuantity());
            Paragraph number = new Paragraph(pricePerProd.toString(), fontData);
            number.setAlignment(PdfPCell.ALIGN_CENTER);
            pricePerProcudt.addElement(number);
            header4.setPadding(10f);
            header4.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell amount = new PdfPCell(new Paragraph(String.valueOf(purchase.getAmountDue()), fontData));
            amount.setPadding(10f);
            amount.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
            amount.setHorizontalAlignment(Element.ALIGN_CENTER);
            amount.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            table.addCell(productName);
            table.addCell(quantity);
            table.addCell(pricePerProcudt);
            table.addCell(amount);

            document.add(title);
            document.add(table);
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
