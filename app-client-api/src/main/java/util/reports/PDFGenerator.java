package util.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;

public class PDFGenerator {

	public void gerarPDF(String content) {
		Document document = new Document();

		try {
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

}
