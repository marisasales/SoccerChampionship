package com.letscode.SoccerChampionship.utils;

import java.util.Scanner;

public class Scan {
  private final static Scanner scan = new Scanner(System.in);

  public static int enterInt() {
    return scan.nextInt();
  }

  public static String enterString() {
    return scan.next();
  }
}
