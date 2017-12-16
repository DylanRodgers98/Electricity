
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    static int interval;
    static Timer timer;

    public static void timer(int time) {
        int secs = time;
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = secs;
        System.out.print(secs + ", ");
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                System.out.print(setInterval() + ", ");
            }
        }, delay, period);
    }

    private static int setInterval() {
        if (interval == 1) {
            timer.cancel();
        }
        return --interval;
    }
}
