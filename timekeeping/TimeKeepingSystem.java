import java.util.ArrayList;
import java.util.Scanner;
 
class DailyRecord {
 
    int day;
    double timeIn;
    double timeOut;
 
    DailyRecord(int day, double timeIn, double timeOut) {
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }
 
    double getWorkedHours() {
 
        if (isAbsent())
            return 0;
 
        if (timeOut < timeIn)
            return 0;
 
        double hours = timeOut - timeIn;
 
        if (hours >= 8)
            hours -= 1;
 
        return hours;
    }
 
    double getOvertime() {
 
        if (timeOut > 17)
            return timeOut - 17;
 
        return 0;
    }
 
    double getUndertime() {
 
        if (isAbsent())
            return 8;
 
        if (timeOut < 17)
            return 17 - timeOut;
 
        return 0;
    }
 
    double getLate() {
 
        if (timeIn > 8)
            return timeIn - 8;
 
        return 0;
    }
 
    boolean isAbsent() {
 
        return (timeIn == 0 && timeOut == 0);
    }
 
    String formatTime(double time) {
 
        if (time == 0)
            return "ABS";
 
        int hour = (int) time;
        int minute = (int) ((time - hour) * 60);
 
        return String.format("%02d:%02d", hour, minute);
    }
 
    String formatHours(double hours) {
 
        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);
 
        return String.format("%02d:%02d", h, m);
    }
}
 
public class TimeKeepingSystem {
 
    // conversion
    static double convertToDecimal(String time) {
 
        if (time.equals("0"))
            return 0;
 
        // format check
        if (!time.matches("\\d{1,2}:\\d{2}")) {
 
            System.out.println("Invalid format! Use HH:MM.");
            return -1;
        }
 
        String[] parts = time.split(":");
 
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
 
        // range check
        if (hour < 0 || hour > 23 ||
            minute < 0 || minute > 59) {
 
            System.out.println(
                "Invalid time! Hour must be 0-23 and minute 0-59."
            );
 
            return -1;
        }
 
        return hour + (minute / 60.0);
    }
 
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
        ArrayList<DailyRecord> records = new ArrayList<>();
 
        int startDay = 1;
        int endDay = 15;
 
        System.out.println("================================");
        System.out.println("      TIMEKEEPING SYSTEM");
        System.out.println("================================");
 
        System.out.println("Select Cut-Off:");
        System.out.println("1 - Days 1 to 15");
        System.out.println("2 - Days 16 to 30");
 
        int choice = sc.nextInt();
        sc.nextLine();
 
        if (choice == 2) {
            startDay = 16;
            endDay = 30;
        }
 
        System.out.println("\nEnter Time In / Time Out");
        System.out.println("Format: HH:MM (example 8:00, 17:00)");
        System.out.println("Enter 0 if ABSENT");
 
        // input loop
        for (int day = startDay; day <= endDay; day++) {
 
            System.out.println("\nDay " + day);
 
            double timeIn;
            double timeOut;
 
            // time in
            while (true) {
 
                System.out.print("Time In : ");
                String inStr = sc.nextLine();
 
                timeIn = convertToDecimal(inStr);
 
                if (timeIn != -1)
                    break;
            }
 
            // time out
            while (true) {
 
                System.out.print("Time Out: ");
                String outStr = sc.nextLine();
 
                timeOut = convertToDecimal(outStr);
 
                if (timeOut != -1)
                    break;
            }
 
            // order check
            if (timeOut < timeIn && timeOut != 0) {
 
                System.out.println(
                    "Invalid! Time Out cannot be earlier than Time In."
                );
 
                day--;
                continue;
            }
 
            records.add(new DailyRecord(day, timeIn, timeOut));
        }
 
        double totalWorked = 0;
        double totalOvertime = 0;
        double totalUndertime = 0;
        double totalLate = 0;
        int totalAbsent = 0;
 
        System.out.println("\n================================");
        System.out.println("          DAILY RECORD");
        System.out.println("================================");
 
        System.out.printf(
            "%-6s %-8s %-8s %-8s %-8s %-8s %-8s\n",
            "Day", "In", "Out", "Work", "Late", "UT", "OT"
        );
 
        for (DailyRecord rec : records) {
 
            double worked = rec.getWorkedHours();
            double overtime = rec.getOvertime();
            double undertime = rec.getUndertime();
            double late = rec.getLate();
 
            if (rec.isAbsent())
                totalAbsent++;
 
            totalWorked += worked;
            totalOvertime += overtime;
            totalUndertime += undertime;
            totalLate += late;
 
            System.out.printf(
                "%-6d %-8s %-8s %-8s %-8s %-8s %-8s\n",
                rec.day,
                rec.formatTime(rec.timeIn),
                rec.formatTime(rec.timeOut),
                rec.formatHours(worked),
                rec.formatHours(late),
                rec.formatHours(undertime),
                rec.formatHours(overtime)
            );
        }
 
        System.out.println("\n================================");
        System.out.println("            SUMMARY");
        System.out.println("================================");
 
        DailyRecord temp = new DailyRecord(0,0,0);
 
        System.out.println("Total Worked Hours : " + temp.formatHours(totalWorked));
        System.out.println("Total Overtime     : " + temp.formatHours(totalOvertime));
        System.out.println("Total Undertime    : " + temp.formatHours(totalUndertime));
        System.out.println("Total Late Hours   : " + temp.formatHours(totalLate));
        System.out.println("Total Absences     : " + totalAbsent);
 
        sc.close();
    }
}