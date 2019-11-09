import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

class ExcelWriter {

    private static final Logger LOGGER = LogManager.getLogger(ExcelWriter.class);
    private Workbook personsWorkbook;
    private Sheet personsSheet;

    void createFile(List<Person> persons, String[] headers, String fileName, String pathFile) {
        personsWorkbook = new HSSFWorkbook();
        personsSheet = personsWorkbook.createSheet("PersonsSheet");
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName))) {
            createTable(persons, fileOutputStream, headers);
            personsWorkbook.close();
            System.out.println("Файл создан. Путь: " + pathFile);
        } catch (FileNotFoundException e) {
            LOGGER.error("Excel файл не создан. " + e);
        } catch (IOException e) {
            LOGGER.error("Ошибка записи в файл " + e);
        }
    }

    private void fillRow(String[] cellValues, int rowNumber) {
        Row row = personsSheet.createRow(rowNumber);
        for (int i = 0; i < cellValues.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cellValues[i]);
        }
    }

    private void autoSizeColumn(int columns) {
        for (int i = 0; i < columns; i++) {
            personsSheet.autoSizeColumn(i);
        }
    }

    private void createTable(List<Person> persons, FileOutputStream fileOutputStream,
                             String[] headers) throws IOException {
        fillRow(headers, 0);
        for (int i = 0; i < persons.size(); i++) {
            fillRow(PersonToArrayConverter.convertPersonToArray(persons.get(i)), i + 1);
        }
        autoSizeColumn(headers.length);
        personsWorkbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
