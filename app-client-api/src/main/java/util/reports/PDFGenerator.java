package util.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import util.logs.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;

public class PDFGenerator {

    public void gerarPDF(String content) {
        Document document = new Document();

        try {
            content = formatTableContent(content);
            String userHome = System.getProperty("user.home");
            String downloadsDir = userHome + FileSystems.getDefault().getSeparator() + "Downloads";

            String fileName = "documento.pdf";

            String filePath = downloadsDir + FileSystems.getDefault().getSeparator() + fileName;

            int counter = 1;
            while (new File(filePath).exists()) {
                fileName = "documento" + counter + ".pdf";
                filePath = downloadsDir + FileSystems.getDefault().getSeparator() + fileName;
                counter++;
            }

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Paragraph paragraph = new Paragraph(content);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            document.close();
            System.out.println("PDF gerado com sucesso e salvo em: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatTableContent(String content) {
        content = content.replaceAll("[|+-]", "");
        String[] lines = content.split("\\r?\\n");
        StringBuilder formattedContent = new StringBuilder();

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 2) {
                parts[0] = parts[0].replaceAll("\\s+", ": ");
                formattedContent.append(parts[0]).append("|").append(parts[1]).append("\n");
            } else {
                formattedContent.append(line).append("\n");
            }
        }

        return formattedContent.toString();
    }

}
