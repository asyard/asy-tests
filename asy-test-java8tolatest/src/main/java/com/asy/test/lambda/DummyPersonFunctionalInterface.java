package com.asy.test.lambda;

import com.asy.test.data.Person;

@FunctionalInterface
public interface DummyPersonFunctionalInterface {
    Person createNewDummy(String name, String surname);
}
