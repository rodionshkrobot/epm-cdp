package task1;

import task1.statistic.TextStatisticsClient;
import task1.util.Pair;
import task1.util.measurer.TimeMeasureExecuter;
import task1.util.measurer.TimeMeasurer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {

    private static final String INIT_HELP_KEY = "init";
    private static final String MID_FLOW_HELP_KEY = "midFlow";
    private static final String UTIL_HELP_KEY = "util";
    private static final String FILE_RUN_COMMAND = "file";
    private static final String FIND_RUN_COMMAND = "find";
    private static final String UNIQUE_RUN_COMMAND = "unique";
    private static final String QUIT_RUN_COMMAND = "quit";
    private static final String DEFAULT_FILE_NAME = "war-and-peace.txt";

    private static List<TextStatisticsClient> implementations;
    private static boolean implementationsInitialized = false;

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        getHelpMessage(INIT_HELP_KEY);
        getHelpMessage(MID_FLOW_HELP_KEY);
        getHelpMessage(UTIL_HELP_KEY);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            switch (s.toLowerCase().split(" ")[0]) {
                case FILE_RUN_COMMAND:
                    initImplementations(s.substring(FILE_RUN_COMMAND.length() + 1, s.length()));
                    break;
                case FIND_RUN_COMMAND:

                    if (!implementationsInitialized)
                        initImplementations(null);

                    for (TextStatisticsClient implementation : implementations) {
                        System.out.println("Implementation : " + implementation.getImplementationVersion());
                        List<Pair<String, Integer>> pairs = getWordsEntryCount(implementation, parseMultipleWords(s.substring(FIND_RUN_COMMAND.length() + 1, s.length())));
                        for (Pair<String, Integer> pair : pairs) {
                            System.out.println("Word : " + pair.getFirst() + " . Entries : " + pair.getSecond());
                        }
                        System.out.println("----------------------");
                    }
                    break;
                case UNIQUE_RUN_COMMAND:

                    if (!implementationsInitialized)
                        initImplementations(null);

                    for (TextStatisticsClient implementation : implementations) {
                        System.out.println("Implementation : " + implementation.getImplementationVersion());
                        Integer uniqueWords = getUniqueWords(implementation);
                        System.out.println("Unique words count : " + uniqueWords);
                        System.out.println("----------------------");
                    }
                    break;
                case QUIT_RUN_COMMAND:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
    }


    private static void initImplementations(String fileName) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        TextStatisticsClient hashMapTextStatisticsClient = new TextStatisticsClient(new FileInputStream(fileName == null ? DEFAULT_FILE_NAME : fileName), HashMap.class);
        TextStatisticsClient treeMapTextStatisticsClient = new TextStatisticsClient(new FileInputStream(fileName == null ? DEFAULT_FILE_NAME : fileName), TreeMap.class);
        TextStatisticsClient linkedHashMapTextStatisticsClient = new TextStatisticsClient(new FileInputStream(fileName == null ? DEFAULT_FILE_NAME : fileName),LinkedHashMap.class);

        implementations = new ArrayList<>();

        implementations.add(hashMapTextStatisticsClient);
        implementations.add(linkedHashMapTextStatisticsClient);
        implementations.add(treeMapTextStatisticsClient);

        implementationsInitialized = true;

    }


    private static void getHelpMessage(String helpCase) {
        switch (helpCase) {
            case INIT_HELP_KEY:
                System.out.println("\"" + FILE_RUN_COMMAND + " %filename\"      name of the file to read. if none specified \"war-and-peace.txt\" will be used");
                break;
            case MID_FLOW_HELP_KEY:
                System.out.println("\"" + FIND_RUN_COMMAND + " %word%\"         get number of entries of the %word%. Multiple words can be entered divided by \",\"");
                System.out.println("\"" + UNIQUE_RUN_COMMAND + "\"              to get the number of unique words in text");
                break;
            case UTIL_HELP_KEY:
                System.out.println("\"" + QUIT_RUN_COMMAND + "\"                to exit");
        }
    }

    private static Integer getUniqueWords(final TextStatisticsClient textStatisticsClient) {
        return (Integer) TimeMeasurer.measure(new TimeMeasureExecuter() {
            @Override
            public Object execute() {
                return textStatisticsClient.getUniqueWords();
            }
        });
    }

    private static List<Pair<String, Integer>> getWordsEntryCount(final TextStatisticsClient textStatisticsClient, final String... words) {
        return (List<Pair<String, Integer>>) TimeMeasurer.measure(new TimeMeasureExecuter() {
            @Override
            public Object execute() {
                return textStatisticsClient.getWordEntryCount(words);
            }
        });

    }

    private static String[] parseMultipleWords(String wordsLine) {
        return wordsLine.split(",");
    }
}
