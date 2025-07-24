import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.*;

public class DevBuddy {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean run = true;
        while (run) {
            System.out.println("\n=== DevBuddy Assistant ===");
            System.out.println("1. Pomodoro Timer");
            System.out.println("2. Save Code Snippet");
            System.out.println("3. View Git Cheat Sheet");
            System.out.println("4. Set Daily Goal");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // flush

            switch (choice) {
                case 1 -> startPomodoro();
                case 2 -> saveSnippet();
                case 3 -> showGitTips();
                case 4 -> saveGoal();
                case 5 -> run = false;
                default -> System.out.println("Invalid!");
            }
        }
    }

    static void startPomodoro() {
        System.out.print("Enter session minutes: ");
        int min = sc.nextInt();
        sc.nextLine();
        System.out.println("Pomodoro started at " + LocalTime.now() + " for " + min + " minutes...");
        try {
            Thread.sleep(min * 60 * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Pomodoro Interrupted");
        }
        System.out.println("✅ Session complete at " + LocalTime.now());
    }

    static void saveSnippet() throws IOException {
        System.out.print("Snippet title: ");
        String title = sc.nextLine();
        System.out.println("Enter code (type END to finish):");
        StringBuilder code = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("END")) break;
            code.append(line).append("\n");
        }
        Files.writeString(Path.of("snippets.txt"),
                "### " + title + "\n" + code + "\n",
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("✅ Snippet saved.");
    }

    static void showGitTips() {
        String[] tips = {
                "git init", "git add .", "git commit -m \"message\"",
                "git status", "git log", "git push origin main"
        };
        System.out.println("=== Git Cheat Sheet ===");
        for (String tip : tips)
            System.out.println("➤ " + tip);
    }

    static void saveGoal() throws IOException {
        System.out.print("Today's Goal: ");
        String goal = sc.nextLine();
        Files.writeString(Path.of("goals.txt"),
                LocalTime.now() + " ➤ " + goal + "\n",
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("✅ Goal saved.");
    }
}
