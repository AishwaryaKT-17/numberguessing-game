import java.util.*;
import java.io.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static int highScore = 0;
    static int gamesPlayed = 0;
    static int gamesWon = 0;
    static int[] leaderboard = new int[3];

    public static void main(String[] args) {

        loadHighScore();
        animate("🎮 WELCOME TO THE ULTIMATE NUMBER GUESSING GAME 🤔🎮");

        boolean playAgain = true;

        while (playAgain) {
            gamesPlayed++;

            System.out.println("\nChoose Mode:");
            System.out.println("1️⃣ Easy (1-10)");
            System.out.println("2️⃣ Medium (1-50)");
            System.out.println("3️⃣ Hard (1-100)");
            System.out.println("4️⃣ Custom Range");
System.out.println("Enter ur mode:");
            int choice = sc.nextInt();

            int min = 1, max = 10;

            switch (choice) {
                case 1: max = 10; break;
                case 2: max = 50; break;
                case 3: max = 100; break;
                case 4:
                    System.out.print("Enter minimum: ");
                    min = sc.nextInt();
                    System.out.print("Enter maximum: ");
                    max = sc.nextInt();
                    break;
                default:
                    System.out.println("Invalid choice! Default Easy selected.");
            }

            int number = rand.nextInt(max - min + 1) + min;
            int attempts = 0;
            int maxAttempts = 5;
            int guess;

            System.out.println("\n✨ Guess between " + min + " and " + max);
            System.out.println("You have " + maxAttempts + " attempts!");

            while (attempts < maxAttempts) {

                System.out.print("👉 Enter guess: ");

                if (!sc.hasNextInt()) {
                    System.out.println("❌ Invalid input!");
                    sc.next();
                    continue;
                }

                guess = sc.nextInt();
                attempts++;

                if (guess == number) {
                    gamesWon++;
                    int score = (maxAttempts - attempts + 1) * 20;

                    System.out.println("\u001B[32m🎉 💥CORRECT! 💥🎉\u001B[0m");
                    System.out.println("Attempts: " + attempts);
                    System.out.println("Score: " + score + " ⭐");

                    updateLeaderboard(score);
                    updateHighScore(score);
                    break;
                }

                if (guess < number)
                    System.out.println(" Too Low⬇️");
                else
                    System.out.println(" Too High!⬆️");

                // Smart AI Hint
                if (attempts == 2) {
                    int rangeHint = 10;
                    System.out.println("💡 Hint: Number is between "
                            + Math.max(min, number - rangeHint)
                            + " and "
                            + Math.min(max, number + rangeHint));
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("💀 Game Over! Number was: " + number);
            }

            showStats();

            System.out.print("\nPlay again? (yes/no): ");
            playAgain = sc.next().equalsIgnoreCase("yes");
        }

        System.out.println("\n👋 Thanks for playing!✨");
        saveHighScore();
    }

    // Animation Effect
    static void animate(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(30); } catch (Exception e) {}
        }
        System.out.println("\n");
    }

    // High Score
    static void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            System.out.println("🔥 NEW HIGH SCORE: " + highScore);
        }
    }

    static void loadHighScore() {
        try {
            File file = new File("highscore.txt");
            if (file.exists()) {
                Scanner fileReader = new Scanner(file);
                highScore = fileReader.nextInt();
                fileReader.close();
            }
        } catch (Exception e) {}
    }

    static void saveHighScore() {
        try {
            FileWriter fw = new FileWriter("highscore.txt");
            fw.write(String.valueOf(highScore));
            fw.close();
        } catch (Exception e) {}
    }

    // Leaderboard
    static void updateLeaderboard(int score) {
        for (int i = 0; i < leaderboard.length; i++) {
            if (score > leaderboard[i]) {
                for (int j = leaderboard.length - 1; j > i; j--) {
                    leaderboard[j] = leaderboard[j - 1];
                }
                leaderboard[i] = score;
                break;
            }
        }
    }

    static void showStats() {
        System.out.println("\n📊 GAME STATS");
        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Games Won: " + gamesWon);
        System.out.println("High Score: " + highScore);

        System.out.println("🏆🎖️Leaderboard:");
        for (int i = 0; i < leaderboard.length; i++) {
            System.out.println((i + 1) + ". " + leaderboard[i]);
        }
    }
}