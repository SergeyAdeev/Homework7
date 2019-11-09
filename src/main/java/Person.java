import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Person {
    private String surname;
    private String name;
    private String patronymic;
    private int age;
    private Sex sex;
    private LocalDate birthday;
    private String bornCity;
    private int postcode;
    private String region;
    private String city;
    private String street;
    private int house;
    private int apartment;

    Person(String surname, String name, String patronymic, int age,
           Sex sex, LocalDate birthday, String bornCity, int postcode,
           String region, String city, String street,
           int house, int apartment) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.bornCity = bornCity;
        this.postcode = postcode;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    String getPatronymic() {
        return patronymic;
    }

    String getBirthday() {
        return parseBirthdayDate();
    }

    int getAge() {
        return age;
    }

    String getBornCity() {
        return bornCity;
    }

    String getCountry() {
        return "Россия";
    }

    int getPostcode() {
        return postcode;
    }

    String getRegion() {
        return region;
    }

    String getCity() {
        return city;
    }

    String getStreet() {
        return street;
    }

    int getHouse() {
        return house;
    }

    int getApartment() {
        return apartment;
    }

    String getSex() {
        return sex.getSex();
    }

    private String parseBirthdayDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateFormatter.format(birthday);
    }
}