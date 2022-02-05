package com.letscode.SoccerChampionship.service;

import com.letscode.SoccerChampionship.entities.SoccerMatch;
import com.letscode.SoccerChampionship.entities.SoccerTeam;
import com.letscode.SoccerChampionship.utils.Printer;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.letscode.SoccerChampionship.utils.Printer.print;

//  1 — Leia o arquivo em uma estrutura de dados, remova os registros duplicados(se houver), ordene por data, time_1 e time_2 e imprima o resultado.
//  2 — Subdivida a estrutura de dados por time (time_1) mantendo as ordens anteriores e gere um arquivo por time contendo o histórico dos jogos ordenados por data.
//  3 — Gere um arquivo contendo a tabela de classificação final dos times, ordenado do que tiver mais pontos para o que tiver menos pontos (lembrando que: vitoria = 3pts, empate = 1pt, derrota = 0pt). Identificar a quantidade de vitórias, empates e derrotas de cada time.
//  Obs.: O arquivo com a tabela de classificação final deverá ser gerado no formato .csv, utilizando o separador ";", os demais poderão ser .txt.

public class FileHandler {
  private static SortedSet<SoccerMatch> matchStatistics = new TreeSet<>();

  @Getter
  private static Map<String, List<SoccerMatch>> teamMatches = new HashMap<>();

  public static String filePath(String fileName) {
    return "src/main/resources/" + fileName;
  }

  public static void getStatistcsFromFile(String fileName) {
    try {
      File file = new File(filePath(fileName));
      List<String> lines = FileUtils.readLines(file, "UTF-8");

      lines.stream().skip(1).forEach(line -> {
        String[] splittedLine = line.split(";");

        matchStatistics.add(SoccerMatch.builder().homeTeam(splittedLine[0]).awayTeam(splittedLine[1]).homeGoals(Integer.parseInt(splittedLine[2])).awayGoals(Integer.parseInt(splittedLine[3])).date(LocalDate.parse(splittedLine[4])).build());
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void readFile(String fileName) {
    try {
      File file = new File(filePath(fileName));
      List<String> lines = FileUtils.readLines(file, "UTF-8");

      if (fileName.endsWith(".csv") && !fileName.startsWith("ranking")) {
        matchStatistics.forEach(match -> print(match.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ": " + match.getHomeTeam() + " " + match.getHomeGoals() + " x " + match.getAwayGoals() + " " + match.getAwayTeam()));
      } else {
        lines.forEach((Printer::print));
      }
    } catch (IOException e) {
      e.printStackTrace();
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
    matchStatistics.forEach(match -> {
      if (!teamMatches.containsKey(match.getHomeTeam())) {
        teamMatches.put(match.getHomeTeam(), new ArrayList<>());
      }

      if (teamMatches.containsKey(match.getAwayTeam())) {
        teamMatches.get(match.getAwayTeam()).add(match);
      }

      teamMatches.get(match.getHomeTeam()).add(match);
    });

    teamMatches.forEach((team, matches) -> {
      clearFile(team + ".txt");
      matches.forEach(match -> writeFile(team + ".txt", match.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ": " + match.getHomeTeam() + " " + match.getHomeGoals() + " x " + match.getAwayGoals() + " " + match.getAwayTeam() + "\n", true));
    });
  }

  public static void writeTeamResultsRaking() {
    SortedMap<String, SoccerTeam> teamsResults = new TreeMap<>();

    getTeamMatches().forEach((team, matches) -> {
      teamsResults.put(team, SoccerTeam.builder().teamName(team).build());

      matches.forEach(match -> {
        if (Objects.equals(match.getHomeTeam(), team)) {
          if (match.getHomeGoals() > match.getAwayGoals()) {
            teamsResults.get(match.getHomeTeam()).increaseWins();
          } else if (match.getAwayGoals() == match.getHomeGoals()) {
            teamsResults.get(match.getHomeTeam()).increaseDraws();
          } else {
            teamsResults.get(match.getHomeTeam()).increaseLoses();
          }
        }

        if (Objects.equals(match.getAwayTeam(), team)) {
          if (match.getHomeGoals() < match.getAwayGoals()) {
            teamsResults.get(match.getAwayTeam()).increaseWins();
          } else if (match.getAwayGoals() == match.getHomeGoals()) {
            teamsResults.get(match.getAwayTeam()).increaseDraws();
          } else {
            teamsResults.get(match.getAwayTeam()).increaseLoses();
          }
        }

        teamsResults.get(team).calculateScore();
      });
    });

    ArrayList<SoccerTeam> listOfResults = new ArrayList<>(teamsResults.values());
    listOfResults.sort((o1, o2) -> o2.getScore() - o1.getScore());

    clearFile("teamsResultsRanking.csv");
    writeFile("teamsResultsRanking.csv", "time;vitorias;empates;derrotas;pontos\n", true);
    listOfResults.forEach((results) -> {

      writeFile("teamsResultsRanking.csv", results.getTeamName() + ";" + results.getWins() + ";" + results.getDraws() + ";" + results.getLoses() + ";" + results.getScore() + "\n", true);
    });
  }

  private static void clearFile(String fileName) {
    writeFile(fileName, "", false);
  }

  //  Só para facilitar os testes
  public static void deleteAllTextFile() {
    File[] files = new File("src/main/resources").listFiles();

    assert files != null;
    for (File file : files) {
      if (file.getName().endsWith(".txt")) {
        file.delete();
      }
    }
  }
}
