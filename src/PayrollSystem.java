import classes.ContractualEmployee;
import classes.ProbationaryEmployee;
import classes.PartTimeEmployee;
import classes.Employee;
import classes.RegularEmployee;
import java.util.ArrayList;
import java.io.IOException;

public class PayrollSystem {
    private ArrayList<Employee> employees;
    private int selectedEmployeeIndex = 0;
    private int focusedPanel = 0; // 0=Employees, 1=Summary, 2=Timekeeping, 3=Payslip
    private boolean running = true;

    public PayrollSystem() {
        employees = new ArrayList<>();
        // Seed some sample data
        employees.add(new RegularEmployee("2024-001", "Paquito", 35000));
        employees.add(new RegularEmployee("2024-002", "Chris", 42000));
        employees.add(new ProbationaryEmployee("2024-003", "Alex", 25000));
        employees.add(new ContractualEmployee("2024-004", "Jordan", 30000));
        employees.add(new PartTimeEmployee("2024-005", "Sam", 250));
    }

    private int termW = 120;
    private int termH = 30;

    private void updateTerminalSize() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Default for Windows as 'mode con' is unreliable to parse quickly
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
            
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            while (running) {
                updateTerminalSize();
                render();
                if (isWindows) {
                    TUITheme.moveTo(1, termH);
                    System.out.print(TUITheme.FG_ACCENT + " (Win) Command: " + TUITheme.RESET);
                    String input = scanner.nextLine().toLowerCase();
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
                case 'q': running = false; return;
            }
        }
    }

    private void render() {
        System.out.print(TUITheme.CLEAR + TUITheme.BG_BASE);
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Dynamic Grid dimensions
        int leftW = (int)(termW * 0.35); // 35% of width for sidebar
        int rightW = termW - leftW;
        int topH = (int)(termH * 0.55);  // 55% of height for top panels
        int botH = termH - topH - 1;     // 1 row for status bar

        // Draw panels
        TUITheme.drawPanel(1,        1,       leftW,  topH,  "Employees",     focusedPanel == 0);
        TUITheme.drawPanel(1,        topH,   leftW,  botH,  "Summary",       focusedPanel == 1);
        TUITheme.drawPanel(leftW,   1,       rightW, topH,  "Timekeeping",   focusedPanel == 2);
        TUITheme.drawPanel(leftW,   topH,   rightW, botH,  "Payslip",       focusedPanel == 3);

        // Draw Employees list
        if (employees.isEmpty()) {
            TUITheme.moveTo(3, 3);
            System.out.print(TUITheme.FG_MUTED + "No employees found." + TUITheme.RESET);
        } else {
            int visibleCount = topH - 3;
            for (int i = 0; i < Math.min(employees.size(), visibleCount); i++) {
                TUITheme.printRow(1, 2 + i, leftW, employees.get(i).toString(), 
                                 focusedPanel == 0 && i == selectedEmployeeIndex);
            }
        }

        // Draw Summary (Placeholder)
        if (focusedPanel == 0 && !employees.isEmpty()) {
            Employee e = employees.get(selectedEmployeeIndex);
            int startY = topH + 1;
            TUITheme.moveTo(3, startY);
            System.out.print(TUITheme.FG_TEXT + "ID:   " + TUITheme.FG_ACCENT + e.getId() + TUITheme.RESET);
            TUITheme.moveTo(3, startY + 1);
            System.out.print(TUITheme.FG_TEXT + "Name: " + TUITheme.FG_ACCENT + e.getName() + TUITheme.RESET);
            TUITheme.moveTo(3, startY + 2);
            System.out.print(TUITheme.FG_TEXT + "Type: " + TUITheme.FG_ACCENT + e.getType() + TUITheme.RESET);
            TUITheme.moveTo(3, startY + 3);
            if (e instanceof PartTimeEmployee pte) {
                System.out.print(TUITheme.FG_TEXT + "Rate: " + TUITheme.FG_ACCENT + pte.getHourlyRate() + "/hr" + TUITheme.RESET);
            } else {
                System.out.print(TUITheme.FG_TEXT + "Basic: " + TUITheme.FG_ACCENT + e.getBasicSalary() + TUITheme.RESET);
            }
        }

        String[] bindings = isWindows ? 
            new String[]{"[w/s] move", "[t] panel", "[q] quit"} :
            new String[]{"[↑↓] navigate", "[Tab] switch panel", "[q] quit"};

        TUITheme.drawStatusBar(termH, termW, bindings);
        TUITheme.moveTo(1, termH);
        System.out.flush();
    }

    private void handleInput() throws IOException {
        int ch = System.in.read();
        
        // Handle escape sequences (Arrows)
        if (ch == 27) { // ESC
            int next1 = System.in.read();
            if (next1 == 91) { // [
                int next2 = System.in.read();
                if (next2 == 65) { // Up
                    moveSelection(-1);
                } else if (next2 == 66) { // Down
                    moveSelection(1);
                }
            }
        } else if (ch == 9) { // Tab
            focusedPanel = (focusedPanel + 1) % 4;
        } else if (ch == 13 || ch == 10) { // Enter
            // Placeholder for selection logic
        } else if (ch == 'n' || ch == 'N') {
            // Placeholder for new employee logic
        } else if (ch == 'q' || ch == 'Q') {
            running = false;
        } else if (ch == 'j' || ch == 'J') { // Vim-style down
            moveSelection(1);
        } else if (ch == 'k' || ch == 'K') { // Vim-style up
            moveSelection(-1);
        }
    }

    private void moveSelection(int delta) {
        if (focusedPanel == 0 && !employees.isEmpty()) {
            selectedEmployeeIndex = (selectedEmployeeIndex + delta + employees.size()) % employees.size();
        }
    }

    private void setRawMode(boolean raw) throws IOException, InterruptedException {
        String[] cmd = {
            "sh", "-c", 
            raw ? "stty raw -echo < /dev/tty" : "stty cooked echo < /dev/tty"
        };
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
