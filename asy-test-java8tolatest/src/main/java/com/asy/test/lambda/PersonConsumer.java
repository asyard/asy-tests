package com.asy.test.lambda;

import com.asy.test.data.Person;

import java.util.Objects;

@FunctionalInterface
public interface PersonConsumer {
    void accept(Person person);

    default PersonConsumer andThen(PersonConsumer after) {
        Objects.requireNonNull(after);
        return (t) -> { accept(t); after.accept(t); };
    }
}
