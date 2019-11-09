import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

class PdfFileWriter {

    private static final Logger LOGGER = LogManager.getLogger(PdfFileWriter.class);

    void createFile(List<Person> persons, String[] headers, String fileName, String pathFile) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName))) {
            Document personsDocument = new Document();
            PdfWriter.getInstance(personsDocument, fileOutputStream);
            personsDocument.setPageSize(PageSize.A3.rotate());
            personsDocument.open();
            personsDocument.add(createTable(headers, persons));
            System.out.println("Файл создан. Путь: " + pathFile);
            personsDocument.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("Путь к файлу не найден " + e);
        } catch (IOException e) {
            LOGGER.error("Ошибка записи " + e);
        } catch (DocumentException e) {
            LOGGER.error("PDF файл не создан " + e);
        }
    }

    private PdfPTable createTable(String[] headers, List<Person> persons) {
        Font font = createFont();
        PdfPTable personsTable = new PdfPTable(headers.length);
        personsTable.setWidthPercentage(100);
        fillRow(personsTable, headers, font);
        for (Person person : persons) {
            fillRow(personsTable, PersonToArrayConverter.convertPersonToArray(person), font);
        }
        return personsTable;
    }

    private void fillRow(PdfPTable personsTable, String[] cellValues, Font font) {
        PdfPCell cell;
        for (String cellValue : cellValues) {
            cell = new PdfPCell(new Phrase(cellValue, font));
            personsTable.addCell(cell);
        }
    }

    private Font createFont() {
        try {
            String FONT = "resources/fontForRussianWords.ttf";
            BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, 10);
        } catch (DocumentException e) {
            System.out.println("Шрифт не установлен " + e);
        } catch (IOException e) {
            LOGGER.error("Ошибка установки шрифта " + e);
        }
        return null;
    }
}
