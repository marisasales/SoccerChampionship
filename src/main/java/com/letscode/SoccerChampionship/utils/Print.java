package com.letscode.SoccerChampionship.utils;

import java.io.File;
import java.time.format.DateTimeFormatter;

import static com.letscode.SoccerChampionship.service.CollectionsHandler.*;

public class Print {
  private static final String SEPARATOR = "-".repeat(44);

  public static void print(String content) {
    System.out.println(content);
  }

  public static void printMenu() {
    print(SEPARATOR + "\n" +
      " ".repeat(12) + "SOCCER CHAMPIONSHIP\n" +
      SEPARATOR + "\n" +
      " ".repeat(10) + "1 - Read File\n" +
      " ".repeat(10) + "2 - Create team files\n" +
      " ".repeat(10) + "3 - Create final results\n" +
      " ".repeat(10) + "4 - Delete files\n" +
      " ".repeat(10) + "5 - Quit running\n" +
      SEPARATOR + "\n" +
      "Choose an option: ");
  }

  public static void presentFiles() {
    File[] files = new File("src/main/resources").listFiles();

    print(SEPARATOR);
    print("List of files:");

    assert files != null;
    for (File file : files) {
      print(file.getName());
    }

    print(SEPARATOR);
  }

  public static void printMatchesResult() {
    System.out.printf("%-21s", "Date");
    System.out.printf("%-21s", "Home Team");
    System.out.printf("%-15s", "Home Goals");
    System.out.printf("%-15s", "Away Goals");
    System.out.printf("%s%n", "Away Team");

    getMatchStatistics().forEach(match -> {
      System.out.printf("%-21s", match.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      System.out.printf("%-21s", match.getHomeTeam());
      System.out.printf("%-15s", match.getHomeGoals());
      System.out.printf("%-15s", match.getAwayGoals());
      System.out.printf("%s%n", match.getAwayTeam());
    });
  }

  public static void printRanking() {
    System.out.printf("%-21s", "Name");
    System.out.printf("%-10s", "Wins");
    System.out.printf("%-10s", "Draws");
    System.out.printf("%-10s", "Loses");
    System.out.printf("%-10s%n", "Score");

    getListOfResults().forEach(result -> {
        System.out.printf("%-21s", result.getTeamName());
        System.out.printf("%-10s", result.getWins());
        System.out.printf("%-10s", result.getDraws());
        System.out.printf("%-10s", result.getLoses());
        System.out.printf("%-10s%n", result.getScore());
      }
    );
  }
}
