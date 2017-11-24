package com.suver.nate.patientscheduler;
import com.suver.nate.patientscheduler.Models.Schedule;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Created by nates on 11/24/2017.
 */

public class ScheduleSort implements Comparator<Schedule> {
    public int compare(Schedule a, Schedule b)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime date1 = fmt.parseDateTime(a.getStartDate()); //todo:  sort by time as well.
        DateTime date2 = fmt.parseDateTime(b.getStartDate());
        return Seconds.secondsBetween(date2.toLocalDate(), date1.toLocalDate()).getSeconds();
    }
}
