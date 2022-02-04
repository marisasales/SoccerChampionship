package com.letscode.SoccerChampionship;

import static com.letscode.SoccerChampionship.service.FileHandler.*;

public class Main {
  public static void main(String[] args) {
    readFile("brasileirao2020.csv");

    createTeamFile("times.txt");
    readFile("times.txt");
  }
}
