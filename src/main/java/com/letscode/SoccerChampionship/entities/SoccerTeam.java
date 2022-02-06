package com.letscode.SoccerChampionship.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SoccerTeam {
  private String teamName;
  private int wins;
  private int draws;
  private int loses;
  private int score;

  public void increaseWins(){
    this.wins++;
  }

  public void increaseLoses(){ this.loses++; }

  public void increaseDraws(){
    this.draws++;
  }

  public void calculateScore(){
    this.score = (this.wins * 3) + (this.draws);
  }
}
