package com.github.rodionshkrobot.task1.util.measurer;

import java.io.IOException;

public class TimeMeasurer {
    public static Object measure(TimeMeasureExecuter timeMeasureExecuter) throws IOException {

        Object object;
        long finish;

        long start = System.nanoTime();

        object = timeMeasureExecuter.execute();

        finish = System.nanoTime();

        System.out.println("Operation done in : " + (finish - start) + " ns");

        return object;
    }

}
