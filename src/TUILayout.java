import java.util.Scanner;

public class TUILayout {

    static final int TERM_W = 120;
    static final int TERM_H = 30;

    // Panel dimensions — lazygit style split
    static final int LEFT_W   = 38;
    static final int RIGHT_W  = TERM_W - LEFT_W;
    static final int TOP_H    = 16;
    static final int BOT_H    = TERM_H - TOP_H - 1; // 1 row for status bar

    static int focusedPanel = 0; // 0=topLeft, 1=botLeft, 2=topRight, 3=botRight

    public static void render() {
        System.out.print(TUITheme.CLEAR + TUITheme.BG_BASE);

        // Four panels — lazygit grid
        TUITheme.drawPanel(1,        1,       LEFT_W,  TOP_H,  "Employees",     focusedPanel == 0);
        TUITheme.drawPanel(1,        TOP_H,   LEFT_W,  BOT_H,  "Summary",       focusedPanel == 1);
        TUITheme.drawPanel(LEFT_W,   1,       RIGHT_W, TOP_H,  "Timekeeping",   focusedPanel == 2);
        TUITheme.drawPanel(LEFT_W,   TOP_H,   RIGHT_W, BOT_H,  "Payslip",       focusedPanel == 3);

        // Status bar
        TUITheme.drawStatusBar(TERM_H, TERM_W, new String[]{
            "[↑↓] navigate", "[Tab] switch panel",
            "[Enter] select", "[n] new employee",
            "[q] quit"
        });

        // Reset cursor to safe position
        TUITheme.moveTo(1, TERM_H);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        render();

        // Main event loop goes here
        // You'll replace this with keyboard input handling
        sc.nextLine();
    }
}
