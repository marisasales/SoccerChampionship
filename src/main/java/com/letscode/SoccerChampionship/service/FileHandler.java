package com.letscode.SoccerChampionship.service;

import com.letscode.SoccerChampionship.utils.Print;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.letscode.SoccerChampionship.service.CollectionsHandler.*;
import static com.letscode.SoccerChampionship.utils.Print.*;

public class FileHandler {
  private static final String RANKING_FILE_NAME = "teamsResultsRanking.csv";

  public static String filePath(String fileName) {
    return "src/main/resources/" + fileName;
  }

  public static List<String> getFileLines(String fileName) {
    List<String> lines = null;
    try {
      File file = new File(filePath(fileName));
      lines = FileUtils.readLines(file, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  };

  public static void readFile(String fileName) {
    List<String> lines = getFileLines(fileName);

    if (fileName.equals(RANKING_FILE_NAME)) {
      printRanking();
    } else if (fileName.endsWith(".csv")) {
      printMatchesResult();
    } else {
      lines.forEach((Print::print));
    }
  }

  public static void writeFile(String fileName, String content, boolean append) {
    try {
      File file = new File(filePath(fileName));

      if (!file.exists()) {
        print("File created: " + fileName);
      }

      FileUtils.writeStringToFile(file, content, "UTF-8", append);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeTeamFiles() {
    getTeamMatches().forEach((team, matches) -> {
      clearFile(team + ".txt");
      matches.forEach(match -> writeFile(team + ".txt",
        match.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ": "
          + match.getHomeTeam() + " "
          + match.getHomeGoals() + " x "
          + match.getAwayGoals() + " "
          + match.getAwayTeam() + "\n",
        true));
    });
  }

  public static void writeTeamResultsRaking() {
    clearFile(RANKING_FILE_NAME);
    writeFile(RANKING_FILE_NAME, "time;vitorias;empates;derrotas;pontos\n", true);

    getListOfResults().forEach((results) -> {
      writeFile(RANKING_FILE_NAME,
        results.getTeamName() + ";"
          + results.getWins() + ";"
          + results.getDraws() + ";"
          + results.getLoses() + ";"
          + results.getScore() + "\n",
        true);
    });
  }

  public static void clearFile(String fileName) {
    writeFile(fileName, "", false);
  }

  public static void deleteFiles() {
    File[] files = new File("src/main/resources").listFiles();

    assert files != null;
    for (File file : files) {

      if (!file.getName().equals("matchesResult.csv")) {
        file.delete();
      }
    }
  }
}
