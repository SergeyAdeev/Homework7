class PersonToArrayConverter {
    static String[] convertPersonToArray(Person person) {
        return new String[]{
                person.getSurname(), person.getName(), person.getPatronymic(),
                String.valueOf(person.getAge()), person.getSex(), person.getBirthday(),
                person.getBornCity(), String.valueOf(person.getPostcode()), person.getCountry(),
                person.getRegion(), person.getCity(), person.getStreet(),
                String.valueOf(person.getHouse()), String.valueOf(person.getApartment())
        };
    }
}
