import java.util.ArrayList;
import java.util.Scanner;

// OBJECT CLASS
class DailyRecord {

    int day;
    double timeIn;
    double timeOut;

    DailyRecord(int day, double timeIn, double timeOut) {
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    // WORKED HOURS
    double getWorkedHours() {

        if (timeIn == 0 && timeOut == 0)
            return 0;

        return timeOut - timeIn;
    }

    // OVERTIME (after 5PM)
    double getOvertime() {

        if (timeOut > 17)
            return timeOut - 17;

        return 0;
    }

    // UNDERTIME (before 5PM)
    double getUndertime() {

        if (timeOut == 0)
            return 8; // Absent whole day

        if (timeOut < 17)
            return 17 - timeOut;

        return 0;
    }

    // LATE (after 8AM)
    double getLate() {

        if (timeIn > 8)
            return timeIn - 8;

        return 0;
    }

    // ABSENT CHECK
    boolean isAbsent() {

        return (timeIn == 0 && timeOut == 0);
    }
}

public class TimeKeepingSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<DailyRecord> records =
                new ArrayList<>();

        int startDay = 1;
        int endDay = 15;

        System.out.println("================================");
        System.out.println("      TIMEKEEPING SYSTEM");
        System.out.println("================================");

        // CUT-OFF SELECTION
        System.out.println("Select Cut-Off:");
        System.out.println("1 - Days 1 to 15");
        System.out.println("2 - Days 16 to 30");

        int choice = sc.nextInt();

        if (choice == 2) {

            startDay = 16;
            endDay = 30;

        }

        System.out.println("\nEnter Time In / Time Out");
        System.out.println("Use 0 if ABSENT");
        System.out.println("Format: 8.0 = 8AM, 17.0 = 5PM");

        // INPUT LOOP (15 DAYS)
        for (int day = startDay;
             day <= endDay;
             day++) {

            System.out.println("\nDay " + day);

            System.out.print("Time In : ");
            double timeIn = sc.nextDouble();

            System.out.print("Time Out: ");
            double timeOut = sc.nextDouble();

            records.add(
                    new DailyRecord(
                            day,
                            timeIn,
                            timeOut
                    )
            );
        }

        // TOTAL VARIABLES
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
                "Day",
                "In",
                "Out",
                "Work",
                "Late",
                "UT",
                "OT"
        );

        // PROCESS RECORDS
        for (DailyRecord rec : records) {

            double worked =
                    rec.getWorkedHours();

            double overtime =
                    rec.getOvertime();

            double undertime =
                    rec.getUndertime();

            double late =
                    rec.getLate();

            if (rec.isAbsent())
                totalAbsent++;

            totalWorked += worked;
            totalOvertime += overtime;
            totalUndertime += undertime;
            totalLate += late;

            // DISPLAY PER DAY
            System.out.printf(
                    "%-6d %-8.1f %-8.1f %-8.1f %-8.1f %-8.1f %-8.1f\n",
                    rec.day,
                    rec.timeIn,
                    rec.timeOut,
                    worked,
                    late,
                    undertime,
                    overtime
            );
        }

        // FINAL SUMMARY
        System.out.println("\n================================");
        System.out.println("            SUMMARY");
        System.out.println("================================");

        System.out.println(
                "Total Worked Hours : "
                        + totalWorked
        );

        System.out.println(
                "Total Overtime     : "
                        + totalOvertime
        );

        System.out.println(
                "Total Undertime    : "
                        + totalUndertime
        );

        System.out.println(
                "Total Late Hours   : "
                        + totalLate
        );

        System.out.println(
                "Total Absences     : "
                        + totalAbsent
        );

        sc.close();
    }
}