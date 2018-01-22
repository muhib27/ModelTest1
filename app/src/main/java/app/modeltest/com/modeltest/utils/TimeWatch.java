package app.modeltest.com.modeltest.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by BLACK HAT on 27-Apr-16.
 */
public class TimeWatch {
    long starts;

    private TimeWatch() {
        reset();
    }

    public static TimeWatch start() {
        return new TimeWatch();
    }

    public TimeWatch reset() {
        starts = System.currentTimeMillis();
        return this;
    }

    public long time() {
        long ends = System.currentTimeMillis();
        return ends - starts;
    }

    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.MILLISECONDS);
    }
}