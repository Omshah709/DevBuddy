import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.*;

public class DevBuddy {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("\n=== DevBuddy Assistant ===");
            System.out.println("1. Pomodoro Timer");
            System.out.println("2. Save Code Snippet");
            System.out.println("3. View Git Cheat Sheet");
            System.out.println("4. Set Daily Goal");
            System.out.println("5. View File (goals/snippets)");
            System.out.println("6. Clear File (goals/snippets)");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> startPomodoro();
                    case 2 -> saveSnippet();
                    case 3 -> showGitTips();
                    case 4 -> saveGoal();
                    case 5 -> viewFileOption();
                    case 6 -> clearFileOption();
                    case 7 -> run = false;
                    default -> System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            } catch (IOException e) {
                System.out.println("File operation failed: " + e.getMessage());
            }
        }
    }

    static void startPomodoro() {
        try {
            System.out.print("Enter session minutes (e.g., 0.1): ");
            double minutes = sc.nextDouble();
            sc.nextLine();

            if (minutes <= 0) {
                System.out.println("Please enter a positive number of minutes.");
                return;
            }

            System.out.println("Pomodoro started at " + LocalTime.now() + " for " + minutes + " minutes...");
            Thread.sleep((long) (minutes * 60 * 1000));
            System.out.println("Session complete at " + LocalTime.now());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
        } catch (InterruptedException e) {
            System.out.println("Pomodoro Interrupted.");
        }
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
        System.out.println("Snippet saved.");
    }

    static void showGitTips() {
        String[] tips = {
                "git init", "git add .", "git commit -m \"message\"",
                "git status", "git log", "git push origin main"
        };
        System.out.println("=== Git Cheat Sheet ===");
        for (String tip : tips)
            System.out.println("- " + tip);
    }

    static void saveGoal() throws IOException {
        System.out.print("Today's Goal: ");
        String goal = sc.nextLine();
        Files.writeString(Path.of("goals.txt"),
                LocalTime.now() + " - " + goal + "\n",
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Goal saved.");
    }

    static void viewFileOption() throws IOException {
        System.out.print("Enter file to view (goals/snippets): ");
        String file = sc.nextLine().toLowerCase();
        Path path = switch (file) {
            case "goals" -> Path.of("goals.txt");
            case "snippets" -> Path.of("snippets.txt");
            default -> null;
        };

        if (path == null || !Files.exists(path)) {
            System.out.println("File not found.");
            return;
        }

        System.out.println("\n=== Contents of " + path.getFileName() + " ===");
        Files.lines(path).forEach(System.out::println);
    }

    static void clearFileOption() throws IOException {
        System.out.print("Which file do you want to clear? (goals/snippets): ");
        String file = sc.nextLine().toLowerCase();
        Path path = switch (file) {
            case "goals" -> Path.of("goals.txt");
            case "snippets" -> Path.of("snippets.txt");
            default -> null;
        };

        if (path == null) {
            System.out.println("Invalid file name.");
            return;
        }

        Files.writeString(path, "", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println(file + ".txt has been cleared.");
    }
}
