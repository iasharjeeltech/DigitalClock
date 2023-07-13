
import java.util.*;
import java.text.*;

public class digitalclock {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println(dateFormat.format(new Date()));
            }
        }, 0, 1000);
    }
}
