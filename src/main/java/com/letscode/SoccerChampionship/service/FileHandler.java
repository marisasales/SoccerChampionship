package com.letscode.SoccerChampionship.service;

import com.letscode.SoccerChampionship.entities.SoccerMatch;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.letscode.SoccerChampionship.utils.Printer.print;

public class FileHandler {
  private static SortedSet<SoccerMatch> matchStatistics = new TreeSet<>();

  public static String filePath(String fileName) {
    return "src/main/resources/" + fileName;
  }

  private static void getStatistcsFromFile(String line) {
    String[] splittedLine = line.split(";");

    matchStatistics.add(SoccerMatch.builder()
      .homeTeam(splittedLine[0])
      .awayTeam(splittedLine[1])
      .homeGoals(Integer.parseInt(splittedLine[2]))
      .awayGoals(Integer.parseInt(splittedLine[3]))
      .date(LocalDate.parse(splittedLine[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")))
      .build());
  }

  public static void readFile(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath(fileName)))) {
      String line = reader.readLine();

      while (line != null) {
        if (fileName.equals("brasileirao2020.csv")) {
          getStatistcsFromFile(line);
          // TODO: um print mais organizado
        } else {
          print(line);
        }

        line = reader.readLine();
      }

    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public static void createTeamFile(String fileName) {
    try (PrintWriter fileWriter = new PrintWriter(new FileWriter(filePath(fileName)))) {

      matchStatistics.forEach(match -> fileWriter.print(match.getDate() + ": "
        + match.getHomeTeam() + " "
        + match.getHomeGoals() + " x "
        + match.getAwayGoals() + " "
        + match.getAwayTeam() + "\n"
      ));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
