package com.letscode.SoccerChampionship;

import com.letscode.SoccerChampionship.entities.SoccerTeam;
import com.letscode.SoccerChampionship.service.FileHandler;

import java.util.*;

import static com.letscode.SoccerChampionship.service.FileHandler.*;

public class Main {
  public static void main(String[] args) {
// ******* TESTE DE ARQUIVO *******
    String file = "matchesResult.csv";
    getStatistcsFromFile(file);
//    readFile(file);
//    System.out.println("*".repeat(50));
//    writeTeamFiles();
    deleteAllTextFile();

//   System.out.println("*".repeat(50));
//    writeTeamResultsRaking();

//  TODO: Menu

  }
}
