package com.letscode.SoccerChampionship.entities;

import lombok.*;

import java.time.LocalDate;

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
//    return date.compareTo(o.getDate()) == 0
//      ? homeTeam.compareTo(o.getHomeTeam())
//      : date.compareTo(o.getDate());

//  Comparar também por awayTeam
    return date.compareTo(o.getDate()) == 0
      ? homeTeam.compareTo(o.getHomeTeam()) == 0
        ? awayTeam.compareTo(o.getAwayTeam())
        : homeTeam.compareTo(o.getHomeTeam())
      : date.compareTo(o.getDate());
  }
}
