package com.letscode.SoccerChampionship.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static java.util.Comparator.comparing;

@AllArgsConstructor
@Getter
@Builder
public class SoccerMatch implements Comparable<SoccerMatch> {
  private String homeTeam;
  private String awayTeam;
  private int homeGoals;
  private int awayGoals;
  private LocalDate date;

  @Override
  public int compareTo(SoccerMatch o) {
    return comparing(SoccerMatch::getDate)
      .thenComparing(SoccerMatch::getHomeTeam)
      .thenComparing(SoccerMatch::getAwayTeam)
      .compare(this, o);
  }
}
