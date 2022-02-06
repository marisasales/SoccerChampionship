package com.letscode.SoccerChampionship.service;

import com.letscode.SoccerChampionship.utils.Scan;

import static com.letscode.SoccerChampionship.service.CollectionsHandler.*;
import static com.letscode.SoccerChampionship.service.FileHandler.*;
import static com.letscode.SoccerChampionship.utils.Print.*;

public class MenuHandler {
  public static void chooseMenuOption() {
    int selectedOptionInMenu;

    getStatistcsFromFile("matchesResult.csv");
    getTeamMatchesFromStatistics();
    rankingCheck(getTeamsResults());
    sortListOfResults();

    do {
      printMenu();
      selectedOptionInMenu = Scan.enterInt();

      switch (selectedOptionInMenu) {
        case 1:
          openFile();
          break;
        case 2:
          writeTeamFiles();
          break;
        case 3:
          writeTeamResultsRaking();
          break;
        case 4:
          deleteFiles();
          print("Successfully, deleted files!");
          break;
        case 5:
          print("Thank you for your time! Bye!");
          break;
        default:
          print("Invalid option, please, choose a number from 1 to 5!");
      }
    } while (selectedOptionInMenu != 5);
  }

  private static void openFile() {
    presentFiles();
    print("Please, enter the file name you want to access (e.g. Corinthians.txt): ");
    String fileName = Scan.enterString();
    readFile(fileName);
  }
}
