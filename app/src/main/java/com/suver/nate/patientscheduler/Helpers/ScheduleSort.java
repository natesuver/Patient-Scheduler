package com.suver.nate.patientscheduler.Helpers;

import com.suver.nate.patientscheduler.Models.ScheduleListItem;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * Created by nates on 11/24/2017.
 */

public class ScheduleSort implements Comparator<ScheduleListItem> {
    public int compare(ScheduleListItem a, ScheduleListItem b)
    { //the data from the api is not sorted by date, so we need to do that here
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime date1 = fmt.parseDateTime(a.getStartDate()); //todo:  sort by time as well.
        DateTime date2 = fmt.parseDateTime(b.getStartDate());
        return Seconds.secondsBetween(date2.toLocalDate(), date1.toLocalDate()).getSeconds();
    }
}
