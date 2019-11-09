import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        final String[] headers = {"Фамилия", "Имя", "Отчество",
                "Возраст", "Пол", "Дата рождения", "Место рождения",
                "Индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};
        List<Person> persons = new ArrayList<>();
        RandusClient randusClient = new RandusClient();
        int countPersons = readNumber();
        try {
            isCorrectNumber(countPersons);
            createListPersons(countPersons, randusClient, persons);
            createExcelFile(persons, headers);
            createPdfFile(persons, headers);
        } catch (IllegalArgumentException e) {
            LOGGER.info("Файлы не созданы. Введенное число не входит в указанный интервал");
        } catch (NullPointerException e) {
            LOGGER.error("Невозможно создать файлы " + e);
        }
    }

    private static int readNumber() {
        System.out.println("Введите число от 1 до 30:");
        int countPersons = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            countPersons = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            LOGGER.error("Введено некорректное число. " + e);
        } catch (IOException e) {
            LOGGER.error("Файлы не созданы. " + e);
        }
        return countPersons;
    }

    private static void createListPersons(
            int countPersons, RandusClient randusClient,
            List<Person> persons) throws NullPointerException {
        Person person;
        for (int i = 0; i < countPersons; i++) {
            person = randusClient.getRandomPerson();
            persons.add(person);
        }
    }

    private static void createExcelFile(List<Person> persons, String[] headers) {
        ExcelWriter excelWriter = new ExcelWriter();
        String fileExcelName = "Persons.xls";
        excelWriter.createFile(persons, headers, fileExcelName, createFilePath(fileExcelName));
    }

    private static void createPdfFile(List<Person> persons, String[] headers) {
        PdfFileWriter pdfFileWriter = new PdfFileWriter();
        String filePdfName = "Persons.pdf";
        pdfFileWriter.createFile(persons, headers, filePdfName, createFilePath(filePdfName));
    }

    private static String createFilePath(String fileName) {
        File currentDirectory = new File("");
        return currentDirectory.getAbsolutePath() + "\\" + fileName;
    }

    private static void isCorrectNumber(int countPersons) {
        if (countPersons < 1 || countPersons > 30)
            throw new IllegalArgumentException();
    }
}