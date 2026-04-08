package timekeeping;

public class DailyRecord {
    public int day;
    public double timeIn;
    public double timeOut;

    public DailyRecord(int day, double timeIn, double timeOut) {
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public double getWorkedHours() {
        if (timeIn == 0 && timeOut == 0)
            return 0;
        return timeOut - timeIn;
    }

    public double getOvertime() {
        if (timeOut > 17)
            return timeOut - 17;
        return 0;
    }

    public double getUndertime() {
        if (timeOut == 0)
            return 8;
        if (timeOut < 17)
            return 17 - timeOut;
        return 0;
    }

    public double getLate() {
        if (timeIn > 8)
            return timeIn - 8;
        return 0;
    }

    public boolean isAbsent() {
        return (timeIn == 0 && timeOut == 0);
    }
}
