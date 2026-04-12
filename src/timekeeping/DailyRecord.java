package timekeeping;

public class DailyRecord {
    public int day;
    public int timeIn;  // HHmm format (e.g. 0800)
    public int timeOut; // HHmm format (e.g. 1700)

    public DailyRecord(int day, int timeIn, int timeOut) {
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    private double toHours(int militaryTime) {
        int hh = militaryTime / 100;
        int mm = militaryTime % 100;
        return hh + (mm / 60.0);
    }

    public double getWorkedHours() {
        if (isAbsent()) return 0;
        double in = toHours(timeIn);
        double out = toHours(timeOut);
        double worked = out - in;
        // Deduct 1 hour break if worked more than 5 hours
        if (worked > 5) worked -= 1.0;
        return Math.max(0, worked);
    }

    public double getOvertime() {
        if (isAbsent()) return 0;
        double out = toHours(timeOut);
        // Standard shift ends at 17:00 (5 PM)
        if (out > 17.0) return out - 17.0;
        return 0;
    }

    public double getUndertime() {
        if (isAbsent()) return 0;
        double out = toHours(timeOut);
        // Standard shift ends at 17:00 (5 PM)
        if (out < 17.0) return 17.0 - out;
        return 0;
    }

    public double getLate() {
        if (isAbsent()) return 0;
        double in = toHours(timeIn);
        // Standard shift starts at 08:00 (8 AM)
        if (in > 8.0) return in - 8.0;
        return 0;
    }

    public boolean isAbsent() {
        return (timeIn == 0 && timeOut == 0);
    }
}

