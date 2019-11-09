import com.github.javafaker.Faker;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class JsonParser {

    private Locale russian = Locale.forLanguageTag("ru");
    private Faker fakerAddress = new Faker(new Locale("ru"));

    Person getPerson(String response) {
        if (response == null) {
            throw new NullPointerException();
        }
        JSONObject jsonPerson = new JSONObject(response);
        String surname = jsonPerson.getString("lname");
        String name = jsonPerson.getString("fname");
        String patronymic = jsonPerson.getString("patronymic");
        String date = jsonPerson.getString("date");
        String sex = jsonPerson.getString("gender");
        int postcode = jsonPerson.getInt("postcode");
        String city = jsonPerson.getString("city");
        String street = jsonPerson.getString("street");
        int house = jsonPerson.getInt("house");
        int apartment = jsonPerson.getInt("apartment");

        return new Person(surname, name, patronymic, countAge(date), sex(sex),
                parseBirthdayDate(date), getBornCity(), postcode, getRegion(),
                city, street, house, apartment);
    }

    private String getBornCity() {
        return fakerAddress.address().city();
    }

    private int countAge(String date) {
        return LocalDate.now().getYear() - parseBirthdayDate(date).getYear();
    }

    private LocalDate parseBirthdayDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy").withLocale(russian);
        return LocalDate.from(dateFormatter.parse(date));
    }

    private Sex sex(String sex) {
        if (sex.toLowerCase().equals("m")) {
            return Sex.MAN;
        } else if (sex.toLowerCase().equals("w")) {
            return Sex.WOMAN;
        } else {
            return null;
        }
    }

    private String getRegion() {
        return fakerAddress.address().state();
    }
}