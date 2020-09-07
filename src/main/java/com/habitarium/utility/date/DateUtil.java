package main.java.com.habitarium.utility.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static int lastDayCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
