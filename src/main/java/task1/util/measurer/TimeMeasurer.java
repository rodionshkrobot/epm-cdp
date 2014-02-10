package task1.util.measurer;

public class TimeMeasurer {
    public static Object measure(TimeMeasureExecuter timeMeasureExecuter) {

        Object object;
        long finish;

        long start = System.nanoTime();

        object = timeMeasureExecuter.execute();

        finish = System.nanoTime();

        System.out.println("Operation done in : " + (finish - start) + " ns");

        return object;
    }

}
