package com.asy.test.zemberek;

import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;

import java.io.IOException;

public class NormalizationApp {

    public static void main(String[] args) throws IOException {
        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
        TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);


        String[] words = {"okuyabileceğimden","okuyablirim", "Ankara", "Ankar'ada", "3'de", "3'te", "naber"};
        boolean result = false;
        for (String word : words) {
            result = spellChecker.check(word);
            if (result) {
                System.out.println(word + " = " + result);
            } else {
                System.out.println(word + " = " + result + " >>> " + spellChecker.suggestForWord(word));
            }

        }
    }

}
