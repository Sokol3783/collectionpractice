package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestCollections {

    // 1 --------------------------------
    @Test 
    void testPrintList() {
        list.forEach(System.out::println);
    }

    // 2 --------------------------------
    @Test 
    void testChangeWeightOfFirstByOne() {
        //todo Изменить вес первой коробки на 1.
        HeavyBox heavyBox = list.get(0);
        heavyBox.setWeight(heavyBox.getWeight()+ 1);
        assertEquals(new HeavyBox(1,2,3,5), list.get(0));
    }

    // 3 --------------------------------
    @Test 
    void testDeleteLast() {
        //todo Удалить предпоследнюю коробку.
        list.remove(list.size()-2);
        assertEquals(6, list.size());
        assertEquals(new HeavyBox(1,2,3,4), list.get(0));
        assertEquals(new HeavyBox(1,3,3,4), list.get(list.size()-2));
    }

    // 4 --------------------------------
    @Test 
    void testConvertToArray() {
        //todo Получить массив содержащий коробки из коллекции тремя способами и вывести на консоль.
      HeavyBox[] arr = Arrays.copyOf(list.toArray(), list.size(), HeavyBox[].class);

      assertArrayEquals(new HeavyBox[]{
          new HeavyBox(1,2,3,4),
          new HeavyBox(3,3,3,4),
          new HeavyBox(2,6,5,3),
          new HeavyBox(2,3,4,7),
          new HeavyBox(1,3,3,4),
          new HeavyBox(1,2,3,4),
          new HeavyBox(1,1,1,1)
      }, arr);

      System.out.println(Arrays.toString(arr));

      arr = list.toArray(new HeavyBox[0]);

      assertArrayEquals(new HeavyBox[]{
          new HeavyBox(1,2,3,4),
          new HeavyBox(3,3,3,4),
          new HeavyBox(2,6,5,3),
          new HeavyBox(2,3,4,7),
          new HeavyBox(1,3,3,4),
          new HeavyBox(1,2,3,4),
          new HeavyBox(1,1,1,1)
      }, arr);

      System.out.println(Arrays.toString(arr));

      arr = list.toArray(new HeavyBox[list.size()]);

      assertArrayEquals(new HeavyBox[]{
          new HeavyBox(1,2,3,4),
          new HeavyBox(3,3,3,4),
          new HeavyBox(2,6,5,3),
          new HeavyBox(2,3,4,7),
          new HeavyBox(1,3,3,4),
          new HeavyBox(1,2,3,4),
          new HeavyBox(1,1,1,1)
      }, arr);

      System.out.println(Arrays.toString(arr));

    }

    // 5 --------------------------------
    @Test 
    void testDeleteBoxesByWeight() {
        // todo удалить все коробки, которые весят 4
        list.removeIf(s -> s.getWeight() == 4);
        assertEquals(3, list.size());
    }

    // 6 --------------------------------
    @Test 
    void testSortBoxesByWeight() {
        list.sort(new Comparator<HeavyBox>() {
            @Override
            public int compare(HeavyBox o1, HeavyBox o2) {

                int compare = Integer.compare(o1.getWeight(), o2.getWeight());
                if (compare == 0) {
                    return Double.compare(o1.getVolume(), o2.getVolume());
                }
                return compare;
            }
        });

        // отсортировать коробки по возрастанию веса. При одинаковом весе - по возрастанию объема
        assertEquals(new HeavyBox(1,1,1,1), list.get(0));
        assertEquals(new HeavyBox(2,3,4,7), list.get(6));
        assertEquals(new HeavyBox(1,2,3,4), list.get(3));
        assertEquals(new HeavyBox(1,3,3,4), list.get(4));
    }

    // 7 --------------------------------
    @Test 
    void testClearList() {
        //todo Удалить все коробки.
        list.removeAll(list);
        assertTrue(list.isEmpty());
    }

    // 8 --------------------------------
    @Test 
    void testReadAllLinesFromFileToList() {
        // todo Прочитать все строки в коллекцию
        List<String> lines = reader.lines().toList();
        assertEquals(19, lines.size());
        assertEquals("", lines.get(8));
    }

    // 9 --------------------------------
    @Test 
    void testReadAllWordsFromFileToList() throws IOException {
        // todo прочитать все строки, разбить на слова и записать в коллекцию
        List<String> words = readAllWordsFromFileToList();
        assertEquals(257, words.size());
    }

    List<String> readAllWordsFromFileToList() throws IOException {
        String line = "";
        List<String> words = new ArrayList<>();
        while ((line = reader.readLine()) != null ) {
            String[] wordsInLine = line.replaceAll("\\W", " ").trim().split(" ");
            for (String word : wordsInLine) {
                if (word.length() > 0) words.add(word.toLowerCase());
            }
        }
        return words;
    }

    // 10 -------------------------------
    @Test 
    void testFindLongestWord() throws IOException {
        // todo Найти самое длинное слово
        assertEquals("conversations", findLongestWord());
    }

    private String findLongestWord() throws IOException {
        List<String> strings = readAllWordsFromFileToList();
        strings.sort(Comparator.comparing(s -> s.length()));
        return strings.get(strings.size()-1);
    }

    // 11 -------------------------------
    @Test 
    void testAllWordsByAlphabetWithoutRepeat() throws IOException {
        // todo Получить список всех слов по алфавиту без повторов
        List<String> result = new ArrayList<>(new HashSet<>(readAllWordsFromFileToList()));
        result.sort(String.CASE_INSENSITIVE_ORDER);

        assertEquals("alice", result.get(5));
        assertEquals("all", result.get(6));
        assertEquals("without", result.get(134));
        assertEquals(138, result.size());
    }

    // 12 -------------------------------
    @Test 
    void testFindMostFrequentWord() throws IOException {
        // todo Найти самое часто вcтречающееся слово
       assertEquals("the", mostFrequentWord());
    }

    // 13 -------------------------------
    @Test 
    void testFindWordsByLengthInAlphabetOrder() throws IOException {
        // todo получить список слов, длиной не более 5 символов, переведенных в нижний регистр, в порядке алфавита, без повторов
        List<String> strings = new ArrayList<>(new HashSet<>(readAllWordsFromFileToList()));
        strings.removeIf(s -> s.length() > 5);
        strings.sort(String.CASE_INSENSITIVE_ORDER);

        assertEquals(94, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("alice", strings.get(2));
        assertEquals("would", strings.get(strings.size() - 1));
    }

    private String mostFrequentWord() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        List<String> strings = readAllWordsFromFileToList();
        int max = 0;
        String frequentWord = "";
        for (String word : strings) {
            Integer count = map.get(word);
            if (count != null) {
                map.put(word, ++count);
                if (count > max) {
                    max = count;
                    frequentWord = word;
                }
            } else {
                map.put(word, 1);
            }
        }

        return frequentWord;
    }

    List<HeavyBox> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>(List.of(
                new HeavyBox(1,2,3,4),
                new HeavyBox(3,3,3,4),
                new HeavyBox(2,6,5,3),
                new HeavyBox(2,3,4,7),
                new HeavyBox(1,3,3,4),
                new HeavyBox(1,2,3,4),
                new HeavyBox(1,1,1,1)
        ));
    }

    static final String REGEXP = "\\W+"; // for splitting into words

    private BufferedReader reader;

    @BeforeEach
    public void setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("Text.txt"), StandardCharsets.UTF_8);
    }

    @AfterEach
    public void closeBufferedReader() throws IOException {
        reader.close();
    }
}