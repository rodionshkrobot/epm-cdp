package com.github.rodionshkrobot.task1.statistic;

import com.github.rodionshkrobot.task1.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextStatisticsClient {


    private Map<String, Integer> words;

    public TextStatisticsClient(InputStream inputStream, Class mapClazz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        words = (Map<String, Integer>) mapClazz.getConstructor().newInstance();
        initStorage(inputStream);
    }

    private void initStorage(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String nextLine = bufferedReader.readLine();
        while (nextLine != null) {
            Pattern pattern = Pattern.compile("[\\w+\\']+");
            Matcher matcher = pattern.matcher(nextLine.toLowerCase());
            while (matcher.find())
                words.put(matcher.group(), words.containsKey(matcher.group()) ? words.get(matcher.group()) + 1 : 1);
            nextLine = bufferedReader.readLine();
        }
        bufferedReader.close();
        inputStream.close();
    }

    public List<Pair<String, Integer>> getWordEntryCount(String... word) {
        List<Pair<String, Integer>> pairs = new LinkedList<>();

        //just for logging purposes
        Integer count;

        for (String s : word) {
            count = words.get(s.toLowerCase());
            pairs.add(new Pair<>(s.toLowerCase(), count));
        }

        return pairs;
    }

    public int getUniqueWords() {
        return words.keySet().size();
    }

    public String getImplementationVersion() {
        return words.getClass().toString();
    }

}
