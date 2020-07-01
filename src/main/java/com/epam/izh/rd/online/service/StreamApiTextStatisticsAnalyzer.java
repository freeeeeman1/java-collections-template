package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.*;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return this.getWords(text).stream().mapToInt(String::length).sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return this.getWords(text).stream().mapToInt(n -> 1).sum();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return this.getUniqueWords(text).stream().mapToInt(n -> 1).sum();
    }

    @Override
    public List<String> getWords(String text) {
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            stringList.add(m.group());
        }
        return stringList;
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return this.getWords(text).stream().distinct().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        return this.getWords(text).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, n -> 1, Integer::sum)));
//        return emptyMap();
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        if (direction.equals(Direction.ASC)) {
            return this.getWords(text).stream().sorted((str1, str2) -> str1.length() - str2.length()).collect(Collectors.toList());
        } else {
            return this.getWords(text).stream().sorted((str1, str2) -> str2.length() - str1.length()).collect(Collectors.toList());
        }
    }
}
