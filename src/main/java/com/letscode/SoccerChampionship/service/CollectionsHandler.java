package com.letscode.SoccerChampionship.service;

import com.letscode.SoccerChampionship.entities.SoccerMatch;
import com.letscode.SoccerChampionship.entities.SoccerTeam;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

import static com.letscode.SoccerChampionship.service.FileHandler.*;

public class CollectionsHandler {
  @Getter
  private static SortedSet<SoccerMatch> matchStatistics = new TreeSet<>();
  @Getter
  private static Map<String, List<SoccerMatch>> teamMatches = new HashMap<>();
  @Getter
  private static Map<String, SoccerTeam> teamsResults = new HashMap<>();
  @Getter
  private static ArrayList<SoccerTeam> listOfResults = new ArrayList<>();

  public static void getStatistcsFromFile(String fileName) {
    List<String> lines = getFileLines(fileName);

    lines.stream().skip(1).forEach(line -> {
      String[] splittedLine = line.split(";");

      matchStatistics.add(SoccerMatch.builder()
        .homeTeam(splittedLine[0])
        .awayTeam(splittedLine[1])
        .homeGoals(Integer.parseInt(splittedLine[2]))
        .awayGoals(Integer.parseInt(splittedLine[3]))
        .date(LocalDate.parse(splittedLine[4]))
        .build());
    });
  }

  public static void getTeamMatchesFromStatistics() {
    matchStatistics.forEach(match -> {
      if (!teamMatches.containsKey(match.getHomeTeam())) {
        teamMatches.put(match.getHomeTeam(), new ArrayList<>());
      }

      if (teamMatches.containsKey(match.getAwayTeam())) {
        teamMatches.get(match.getAwayTeam()).add(match);
      }

      teamMatches.get(match.getHomeTeam()).add(match);
    });
  }

  public static void sortListOfResults() {
    listOfResults = new ArrayList<>(getTeamsResults().values());
    listOfResults.sort((o1, o2) -> o2.getScore() - o1.getScore());
  }

  public static void rankingCheck(Map<String, SoccerTeam> teamsResults) {
    teamMatches.forEach((team, matches) -> {
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
  }
}