package java8.exercises;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Exercises {
// Exercise 12: Create nested maps, where the outer map is a map from the
// first letter of the word to an inner map. (Use a string of length one
// as the key.) The inner map, in turn, is a mapping from the length of the
// word to a list of words with that length. Don't bother with any lowercasing
// or uniquifying of the words.
//
// For example, given the words "foo bar baz bazz" the string
// representation of the result would be:
//     {f={3=[foo]}, b={3=[bar, baz], 4=[bazz]}}.

    @Test
    public void nestedMaps() throws IOException {
        List<String> list = reader
                .lines()
                .flatMap(s -> Arrays.stream(s.split(REGEXP)))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

//                .collect(Collectors.groupingBy(s -> s.substring(0,1),
//                        Collectors.groupingBy(String::length)));

        Map<String, Map<Integer, List<String>>> map = new HashMap<>();
        for (String s : list) {
            if (!map.containsKey(s.substring(0, 1))) {
                map.put(s.substring(0, 1), new HashMap<>() {{
                    put(s.length(), new ArrayList<>(List.of(s)));
                }});
            } else if (!map.get(s.substring(0, 1)).containsKey(s.length())) {
                map.get(s.substring(0, 1)).put(s.length(), new ArrayList<>(List.of(s)));
            } else map.get(s.substring(0, 1)).get(s.length()).add(s);
        }

        assertEquals("[From, Feed]", map.get("F").get(4).toString());
        assertEquals("[by, be, by]", map.get("b").get(2).toString());
        assertEquals("[the, thy, thy, thy, too, the, the, thy, the, the, the]",
                map.get("t").get(3).toString());
        assertEquals("[beauty, bright]", map.get("b").get(6).toString());
    }

// ===== TEST INFRASTRUCTURE ==================================================

    static List<String> wordList = Arrays.asList(
            "every", "problem", "in", "computer", "science",
            "can", "be", "solved", "by", "adding", "another",
            "level", "of", "indirection");
    // Butler Lampson

    static final String REGEXP = "\\W+"; // for splitting into words

    private BufferedReader reader;

    @BeforeEach
    public void setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @AfterEach
    public void closeBufferedReader() throws IOException {
        reader.close();
    }
}