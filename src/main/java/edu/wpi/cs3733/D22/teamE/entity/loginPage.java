package edu.wpi.cs3733.D22.teamE.entity;

public class loginPage {

  public static boolean verifyUser(String username, String password) {
    // todo : send username and password to database and verify
    return (!password.isEmpty() && !username.isEmpty());
  }
}