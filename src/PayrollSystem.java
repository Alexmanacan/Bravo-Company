import classes.ContractualEmployee;
import classes.ProbationaryEmployee;
import classes.PartTimeEmployee;
import classes.Employee;
import classes.RegularEmployee;
import timekeeping.DailyRecord;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import output.PayslipRenderer;

public class PayrollSystem {
    private ArrayList<Employee> employees;
    private int selectedEmployeeIndex = 0;
    private int focusedPanel = 0; // 0=Employees, 1=Summary, 2=Timekeeping, 3=Payslip
    private int currentCutoff = 1; // 1 or 2
    private boolean running = true;
    private int termW = 120;
    private int termH = 30;

    public PayrollSystem() {
        employees = new ArrayList<>();
    }

    private void updateTerminalSize() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            termW = 120;
            termH = 30;
            return;
        }
        try {
            String[] cmdW = {"sh", "-c", "tput cols < /dev/tty"};
            String[] cmdH = {"sh", "-c", "tput lines < /dev/tty"};
            termW = Integer.parseInt(new java.util.Scanner(Runtime.getRuntime().exec(cmdW).getInputStream()).next());
            termH = Integer.parseInt(new java.util.Scanner(Runtime.getRuntime().exec(cmdH).getInputStream()).next());
        } catch (Exception e) {
            termW = 120; termH = 30;
        }
    }

    public void run() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        try {
            if (!isWindows) setRawMode(true);
            
            while (running) {
                updateTerminalSize();
                render();
                if (isWindows) {
                    TUITheme.moveTo(1, termH);
                    System.out.print(TUITheme.FG_ACCENT + " (Win) Command: " + TUITheme.RESET);
                    Scanner winScanner = new Scanner(System.in);
                    String input = winScanner.nextLine().toLowerCase();
                    if (!input.isEmpty()) handleWindowsInput(input);
                } else {
                    handleInput();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isWindows) {
                try { setRawMode(false); } catch (Exception e) {}
            }
            System.out.print(TUITheme.CLEAR);
            System.out.println("Payroll System closed.");
        }
    }

    private void handleWindowsInput(String input) {
        for (char ch : input.toCharArray()) {
            switch (ch) {
                case 'w': moveSelection(-1); break;
                case 's': moveSelection(1);  break;
                case 't': focusedPanel = (focusedPanel + 1) % 4; break;
                case 'n': addNewEmployee(); break;
                case 'a': if (focusedPanel == 2) addTimekeepingRecord(); break;
                case 'p': generateFullPayslip(); break;
                case 'q': running = false; return;
            }
        }
    }

    private void generateFullPayslip() {
        if (employees.isEmpty()) return;
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        try {
            if (!isWindows) setRawMode(false);
            System.out.print(TUITheme.CLEAR);
            
            Employee e = employees.get(selectedEmployeeIndex);
            PayslipRenderer.render(e, currentCutoff);
            
            System.out.println("\nPress Enter to return to dashboard...");
            new Scanner(System.in).nextLine();
            
            if (!isWindows) setRawMode(true);
        } catch (Exception e) {
            try { if (!isWindows) setRawMode(true); } catch (Exception ex) {}
        }
    }

    private void addNewEmployee() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        try {
            if (!isWindows) setRawMode(false);
            System.out.print(TUITheme.CLEAR);
            Scanner scanner = new Scanner(System.in);
            
            TUITheme.prompt("Enter ID: ");
            String id = scanner.nextLine();
            TUITheme.prompt("Enter Name: ");
            String name = scanner.nextLine();
            TUITheme.prompt("Type (1:Regular, 2:Probationary, 3:Part-Time, 4:Contractual): ");
            int type = Integer.parseInt(scanner.nextLine());
            TUITheme.prompt("Enter Salary/Rate: ");
            double rate = Double.parseDouble(scanner.nextLine());

            switch (type) {
                case 1 -> employees.add(new RegularEmployee(id, name, rate));
                case 2 -> employees.add(new ProbationaryEmployee(id, name, rate));
                case 3 -> employees.add(new PartTimeEmployee(id, name, rate));
                case 4 -> employees.add(new ContractualEmployee(id, name, rate));
                default -> System.out.println("Invalid type.");
            }
            
            if (!isWindows) setRawMode(true);
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
            try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            try { if (!isWindows) setRawMode(true); } catch (Exception ex) {}
        }
    }

    private void addTimekeepingRecord() {
        if (employees.isEmpty()) return;
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        try {
            if (!isWindows) setRawMode(false);
            System.out.print(TUITheme.CLEAR);
            Scanner scanner = new Scanner(System.in);
            
            Employee e = employees.get(selectedEmployeeIndex);
            System.out.println(TUITheme.FG_ACCENT + "Adding Time Record for: " + e.getName() + TUITheme.RESET);
            
            TUITheme.prompt("Cut-off (1: Days 1-15, 2: Days 16-31): ");
            int cutoff = Integer.parseInt(scanner.nextLine());
            TUITheme.prompt("Day of Month: ");
            int day = Integer.parseInt(scanner.nextLine());
            TUITheme.prompt("Time In (Military, e.g. 0800): ");
            int in = Integer.parseInt(scanner.nextLine());
            TUITheme.prompt("Time Out (Military, e.g. 1700): ");
            int out = Integer.parseInt(scanner.nextLine());

            e.addTimeRecord(new DailyRecord(day, in, out));
            
            if (!isWindows) setRawMode(true);
        } catch (Exception e) {
            System.out.println("Error adding record: " + e.getMessage());
            try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            try { if (!isWindows) setRawMode(true); } catch (Exception ex) {}
        }
    }

    private void render() {
        System.out.print(TUITheme.HIDE_CURSOR + TUITheme.MOVE_HOME + TUITheme.BG_BASE);
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Dynamic Grid dimensions
        int leftW = (int)(termW * 0.35); 
        int rightW = termW - leftW;
        int topH = (int)(termH * 0.55);  
        int botH = termH - topH - 1;     

        TUITheme.drawPanel(1,        1,       leftW,  topH,  "Employees",     focusedPanel == 0);
        TUITheme.drawPanel(1,        topH,   leftW,  botH,  "Summary",       focusedPanel == 1);
        TUITheme.drawPanel(leftW,   1,       rightW, topH,  "Timekeeping (Cut-off " + currentCutoff + ")",   focusedPanel == 2);
        TUITheme.drawPanel(leftW,   topH,   rightW, botH,  "Payslip (Cut-off " + currentCutoff + ")",       focusedPanel == 3);

        if (employees.isEmpty()) {
            TUITheme.moveTo(3, 3);
            System.out.print(TUITheme.FG_MUTED + "No employees found. Press 'n' to add." + TUITheme.RESET);
        } else {
            int visibleCount = topH - 3;
            for (int i = 0; i < Math.min(employees.size(), visibleCount); i++) {
                TUITheme.printRow(1, 2 + i, leftW, employees.get(i).toString(), 
                                 focusedPanel == 0 && i == selectedEmployeeIndex);
            }

            Employee selectedEmployee = employees.get(selectedEmployeeIndex);
            
            // Summary Panel
            int startYsum = topH + 1;
            TUITheme.moveTo(3, startYsum);
            System.out.print(TUITheme.FG_TEXT + "ID:   " + TUITheme.FG_ACCENT + selectedEmployee.getId() + TUITheme.RESET);
            TUITheme.moveTo(3, startYsum + 1);
            System.out.print(TUITheme.FG_TEXT + "Name: " + TUITheme.FG_ACCENT + selectedEmployee.getName() + TUITheme.RESET);
            TUITheme.moveTo(3, startYsum + 2);
            System.out.print(TUITheme.FG_TEXT + "Type: " + TUITheme.FG_ACCENT + selectedEmployee.getType() + TUITheme.RESET);
            TUITheme.moveTo(3, startYsum + 3);
            System.out.print(TUITheme.FG_TEXT + "Basic:" + TUITheme.FG_ACCENT + String.format("%.2f", selectedEmployee.getBasicSalary()) + TUITheme.RESET);

            // Timekeeping Panel
            int startXtk = leftW + 2;
            int startYtk = 2;
            TUITheme.moveTo(startXtk, startYtk);
            System.out.printf(TUITheme.FG_MUTED + "%-4s %-6s %-6s %-6s %-6s %-6s %-6s" + TUITheme.RESET, 
                              "Day", "In", "Out", "Work", "Late", "UT", "OT");
            
            ArrayList<DailyRecord> records = selectedEmployee.getTimeRecords();
            int maxVisibleTk = topH - 4;
            for (int i = 0; i < Math.min(records.size(), maxVisibleTk); i++) {
                DailyRecord r = records.get(i);
                TUITheme.moveTo(startXtk, startYtk + 1 + i);
                System.out.printf(TUITheme.FG_TEXT + "%-4d %-6.1f %-6.1f %-6.1f %-6.1f %-6.1f %-6.1f" + TUITheme.RESET,
                                  r.day, r.timeIn, r.timeOut, r.getWorkedHours(), r.getLate(), r.getUndertime(), r.getOvertime());
            }
            if (records.isEmpty()) {
                TUITheme.moveTo(startXtk, startYtk + 2);
                System.out.print(TUITheme.FG_MUTED + "No records. Press 'a' to add." + TUITheme.RESET);
            }

            // Payslip Panel Content
            List<String> payslipLines = output.PayslipRenderer.getPayslipLines(selectedEmployee, currentCutoff);
            int startXps = leftW + 2;
            int startYps = topH + 1;
            int maxVisiblePs = botH - 2;
            for (int i = 0; i < Math.min(payslipLines.size(), maxVisiblePs); i++) {
                TUITheme.moveTo(startXps, startYps + i);
                String line = payslipLines.get(i);
                if (line.contains("Net Pay")) {
                    System.out.print(TUITheme.FG_SUCCESS + TUITheme.BOLD + line + TUITheme.RESET);
                } else if (line.startsWith("---")) {
                    System.out.print(TUITheme.FG_ACCENT + line + TUITheme.RESET);
                } else if (line.contains("-")) {
                    System.out.print(TUITheme.FG_ERROR + line + TUITheme.RESET);
                } else {
                    System.out.print(TUITheme.FG_TEXT + line + TUITheme.RESET);
                }
            }
        }

        String[] bindings = isWindows ? 
            new String[]{"[w/s] move", "[Tab] panel", "[c] toggle cutoff", "[n] add emp", "[a] add time", "[p] payslip", "[q] quit"} :
            new String[]{"[↑↓] move", "[Tab] panel", "[c] toggle cutoff", "[n] add emp", "[a] add time", "[p] payslip", "[q] quit"};

        TUITheme.drawStatusBar(termH, termW, bindings);
        TUITheme.moveTo(1, termH);
        System.out.print(TUITheme.SHOW_CURSOR);
        System.out.flush();
    }


    private void handleInput() throws IOException {
        int ch = System.in.read();
        
        if (ch == 27) { // ESC
            int next1 = System.in.read();
            if (next1 == 91) {
                int next2 = System.in.read();
                if (next2 == 65) moveSelection(-1);
                else if (next2 == 66) moveSelection(1);
            }
        } else if (ch == 9) {
            focusedPanel = (focusedPanel + 1) % 4;
        } else if (ch == 'n' || ch == 'N') {
            addNewEmployee();
        } else if (ch == 'a' || ch == 'A') {
            if (focusedPanel == 2) addTimekeepingRecord();
        } else if (ch == 'c' || ch == 'C') {
            currentCutoff = (currentCutoff == 1) ? 2 : 1;
        } else if (ch == 'p' || ch == 'P') {
            generateFullPayslip();
        } else if (ch == 'q' || ch == 'Q') {
            running = false;
        } else if (ch == 'j' || ch == 'J') { 
            moveSelection(1);
        } else if (ch == 'k' || ch == 'K') { 
            moveSelection(-1);
        }
    }

    private void moveSelection(int delta) {
        if (focusedPanel == 0 && !employees.isEmpty()) {
            selectedEmployeeIndex = (selectedEmployeeIndex + delta + employees.size()) % employees.size();
        }
    }

    private void setRawMode(boolean raw) throws IOException, InterruptedException {
        String[] cmd = { "sh", "-c", raw ? "stty raw -echo < /dev/tty" : "stty cooked echo < /dev/tty" };
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
