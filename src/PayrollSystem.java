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

    public void run() {
        // Set terminal to raw mode to capture single keypresses
        try {
            setRawMode(true);
            
            while (running) {
                render();
                handleInput();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Restore terminal to cooked mode
            try {
                setRawMode(false);
            } catch (Exception e) {
                // Ignore
            }
            System.out.print(TUITheme.CLEAR);
            System.out.println("Payroll System closed.");
        }
    }

    private void render() {
        System.out.print(TUITheme.CLEAR + TUITheme.BG_BASE);

        // Update focused panel in TUILayout (if we're using it as state)
        // For simplicity, let's just draw panels here since TUILayout is static
        
        // Grid dimensions from TUILayout
        int termW = 120, termH = 30;
        int leftW = 38, rightW = termW - leftW;
        int topH = 16, botH = termH - topH - 1;

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
            for (int i = 0; i < employees.size(); i++) {
                TUITheme.printRow(1, 2 + i, leftW, employees.get(i).toString(), 
                                 focusedPanel == 0 && i == selectedEmployeeIndex);
            }
        }

        // Draw Summary (Placeholder)
        if (focusedPanel == 0 && !employees.isEmpty()) {
            Employee e = employees.get(selectedEmployeeIndex);
            TUITheme.moveTo(3, topH + 1);
            System.out.print(TUITheme.FG_TEXT + "ID:   " + TUITheme.FG_ACCENT + e.getId() + TUITheme.RESET);
            TUITheme.moveTo(3, topH + 2);
            System.out.print(TUITheme.FG_TEXT + "Name: " + TUITheme.FG_ACCENT + e.getName() + TUITheme.RESET);
            TUITheme.moveTo(3, topH + 3);
            System.out.print(TUITheme.FG_TEXT + "Type: " + TUITheme.FG_ACCENT + e.getType() + TUITheme.RESET);
            TUITheme.moveTo(3, topH + 4);
            if (e instanceof PartTimeEmployee pte) {
                System.out.print(TUITheme.FG_TEXT + "Rate: " + TUITheme.FG_ACCENT + pte.getHourlyRate() + "/hr" + TUITheme.RESET);
            } else {
                System.out.print(TUITheme.FG_TEXT + "Basic: " + TUITheme.FG_ACCENT + e.getBasicSalary() + TUITheme.RESET);
            }
        }

        // Status bar
        TUITheme.drawStatusBar(termH, termW, new String[]{
            "[↑↓] navigate", "[Tab] switch panel",
            "[Enter] select", "[n] new employee",
            "[q] quit"
        });

        // Move cursor to bottom
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
