package com.asy.test.data;

import java.util.*;

public class Generator {

    public static List<IdValuePair> generateIdValueList(int length) {
        List<IdValuePair> testData = new ArrayList<>();
        if (length >0) {
            for (int i = 0; i < length; i++) {
                testData.add(new IdValuePair(UUID.randomUUID().toString(), "Val" + i));
            }
        }
        return testData;
    }

    public static List<Person> generatePersonList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Marc", Optional.empty(), "Webber", new Date(), Person.Gender.MALE, UUID.randomUUID().toString(), 23L,false));
        personList.add(new Person("Alice", Optional.empty(), "Keys", new Date(), Person.Gender.FEMALE, UUID.randomUUID().toString(), 24L,false));
        personList.add(new Person("George", Optional.of("Man"), "Brown", new Date(), Person.Gender.MALE, UUID.randomUUID().toString(), 223L,true));
        personList.add(new Person("Peter", Optional.empty(), "Mikkel", new Date(), Person.Gender.MALE, UUID.randomUUID().toString(), 253L,true));
        personList.add(new Person("Judith", Optional.empty(), "Brown", new Date(), Person.Gender.FEMALE, UUID.randomUUID().toString(), 223L,false));
        personList.add(new Person("Blake", Optional.of("John"), "Rose", new Date(), Person.Gender.MALE, UUID.randomUUID().toString(), 253L,false));
        personList.add(new Person("Tai", Optional.empty(), "Gordon", new Date(), Person.Gender.MALE, UUID.randomUUID().toString(), 23L,false));
        personList.add(new Person("Jennifer", Optional.empty(), "Tatum", new Date(), Person.Gender.FEMALE, UUID.randomUUID().toString(), 23L,false));
        personList.add(new Person("June", Optional.empty(), "Offred", new Date(), Person.Gender.FEMALE, UUID.randomUUID().toString(), 23L,false));

        return personList;
    }



}
