package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.*;

/**
 * Совет:
 * Начните с реализации метода {@link SimpleTextStatisticsAnalyzer#getWords(String)}.
 * Затем переиспользуйте данный метод при реализации других.
 * <p>
 * При необходимости, можно создать внутри данного класса дополнительные вспомогательные приватные методы.
 */
public class SimpleTextStatisticsAnalyzer implements TextStatisticsAnalyzer {

    /**
     * Необходимо реализовать функционал подсчета суммарной длины всех слов (пробелы, знаким препинания итд не считаются).
     * Например для текста "One, I - tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countSumLengthOfWords(String text) {
        int sum = 0;
        for (String string : this.getWords(text)) {
            sum += string.length();
        }
        return sum;
    }

    /**
     * Необходимо реализовать функционал подсчета количества слов в тексте.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countNumberOfWords(String text) {
        return this.getWords(text).size();
    }

    /**
     * Необходимо реализовать функционал подсчета количества уникальных слов в тексте (с учетом регистра).
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 5.
     * param text текст
     */
    @Override
    public int countNumberOfUniqueWords(String text) {
        return this.getUniqueWords(text).size();
    }

    /**
     * Необходимо реализовать функционал получения списка слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "three", "one", "tWo", "tWo"}
     *
     * @param text текст
     */
    @Override
    public List<String> getWords(String text) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("[A-Za-z]+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

    /**
     * Необходимо реализовать функционал получения списка уникальных слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "one", "tWo"}
     *
     * @param text текст
     */
    @Override
    public Set<String> getUniqueWords(String text) {
        Set<String> stringSet = new HashSet<>();
        Pattern p = Pattern.compile("[A-Za-z]+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            stringSet.add(m.group());
        }
        return stringSet;
    }

    /**
     * Необходимо реализовать функционал подсчета количества повторений слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должны вернуться результаты :
     * {"One" : 1, "two" : 1, "three" : 2, "one" : 1, "tWo" : 2}
     *
     * @param text текст
     */
    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> stringIntMap = new HashMap<>();
        for (String string : this.getUniqueWords(text)) {
            stringIntMap.put(string, 0);
        }
        for (String string : this.getWords(text)) {
            if (stringIntMap.containsKey(string)) {
                stringIntMap.put(string, stringIntMap.get(string) + 1);
            }
        }
        return stringIntMap;
    }

    /**
     * Необходимо реализовать функционал вывода слов из текста в отсортированном виде (по длине) в зависимости от параметра direction.
     * Например для текста "Hello, Hi, mother, father - good, cat, c!!" должны вернуться результаты :
     * ASC : {"mother", "father", "Hello", "good", "cat", "Hi", "c"}
     * DESC : {"c", "Hi", "cat", "good", "Hello", "father", "mother"}
     *
     * @param text текст
     */
    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        String[] stringArray = this.getWords(text).toArray(new String[this.getWords(text).size()]);
        String tmp = null;
        if (direction.equals(Direction.ASC)) {
            for (int i = 0; i < stringArray.length; i++) {
                for (int j = stringArray.length - 1; j > i; j--) {
                    if (stringArray[j-1].length() > stringArray[j].length()) {
                        tmp = stringArray[j];
                        stringArray[j] = stringArray[j-1];
                        stringArray[j-1] = tmp;
                    }
                }
            }
        } else {
            for (int i = 0; i < stringArray.length; i++) {
                for (int j = stringArray.length - 1; j > i; j--) {
                    if (stringArray[j-1].length() < stringArray[j].length()) {
                        tmp = stringArray[j];
                        stringArray[j] = stringArray[j-1];
                        stringArray[j-1] = tmp;
                    }
                }
            }
        }
        return new ArrayList<String>(Arrays.asList(stringArray));
    }
}
