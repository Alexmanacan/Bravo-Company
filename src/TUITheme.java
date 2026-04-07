public class TUITheme {
    // Reset
    public static final String RESET   = "\033[0m";
    public static final String BOLD    = "\033[1m";
    public static final String DIM     = "\033[2m";
    public static final String REVERSE = "\033[7m";
    public static final String CLEAR   = "\033[2J\033[H";
    public static final String MOVE_HOME = "\033[H";
    public static final String HIDE_CURSOR = "\033[?25l";
    public static final String SHOW_CURSOR = "\033[?25h";

    // Claude Code CLI foreground colors
    public static final String FG_TEXT    = "\033[38;2;204;202;200m";
    public static final String FG_MUTED   = "\033[38;2;120;118;116m";
    public static final String FG_ACCENT  = "\033[38;2;79;152;163m";
    public static final String FG_SUCCESS = "\033[38;2;109;170;69m";
    public static final String FG_WARN    = "\033[38;2;187;101;59m";
    public static final String FG_ERROR   = "\033[38;2;209;99;167m";
    public static final String FG_BORDER  = "\033[38;2;57;56;54m";

    // Background
    public static final String BG_BASE    = "\033[48;2;30;28;26m";
    public static final String BG_SELECT  = "\033[48;2;49;59;59m"; // teal-tinted selection

    // Box-drawing characters
    public static final String TL = "┌", TR = "┐", BL = "└", BR = "┘";
    public static final String H  = "─", V  = "│";
    public static final String TM = "┬", BM = "┴", ML = "├", MR = "┤", X = "┼";

    /** Draw a panel box with a title embedded in the top border */
    public static void drawPanel(int x, int y, int w, int h,
                                  String title, boolean active) {
        String borderColor = active ? FG_ACCENT : FG_BORDER;
        String titleFmt    = active ? BOLD + FG_ACCENT : FG_MUTED;

        // Top border with title
        moveTo(x, y);
        System.out.print(borderColor + TL + H + RESET
                       + titleFmt + " " + title + " " + RESET
                       + borderColor);
        int remaining = w - title.length() - 4;
        System.out.print(H.repeat(Math.max(0, remaining)) + TR + RESET);

        // Side borders and clear content
        for (int row = 1; row < h - 1; row++) {
            moveTo(x, y + row);
            System.out.print(borderColor + V + RESET 
                           + " ".repeat(w - 2)
                           + borderColor + V + RESET);
        }

        // Bottom border
        moveTo(x, y + h - 1);
        System.out.print(borderColor + BL + H.repeat(w - 2) + BR + RESET);
    }

    /** Print text at specific terminal coordinates (1-indexed) */
    public static void moveTo(int col, int row) {
        System.out.print("\033[" + row + ";" + col + "H");
    }

    /** Print a selectable row inside a panel */
    public static void printRow(int x, int y, int w, String text, boolean selected) {
        moveTo(x + 1, y);
        String prefix = selected ? BG_SELECT + FG_ACCENT + "►" : FG_MUTED + " ";
        String content = selected ? BG_SELECT + FG_TEXT : FG_TEXT;
        String padded = String.format("%-" + (w - 3) + "s", text);
        System.out.print(prefix + " " + content + padded + RESET);
    }

    /** Status bar at bottom */
    public static void drawStatusBar(int y, int termWidth, String[] bindings) {
        moveTo(1, y);
        System.out.print(FG_MUTED + DIM);
        StringBuilder sb = new StringBuilder();
        for (String b : bindings) sb.append("  ").append(b);
        System.out.print(String.format("%-" + termWidth + "s", sb.toString()));
        System.out.print(RESET);
    }

    /** Prompt for user input with Claude Code style */
    public static void prompt(String label) {
        System.out.print(FG_ACCENT + "◆ " + RESET + FG_TEXT + label + " " + RESET);
    }
}
