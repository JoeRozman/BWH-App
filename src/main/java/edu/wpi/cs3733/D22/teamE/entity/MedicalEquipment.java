package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicalEquipment extends Equipment {
  private String med_equipmentID;
  private String equipmentType;

  /*
    public static void main(String[] args) throws SQLException {
      MedicalEquipment equip =
          new MedicalEquipment(
              "BED01", "Balls", false, true, "OR BED Park", "OR BED Park", "ABBPRK03", "BED", 5);
      System.out.println(equip.getXCoord());
      System.out.println(equip.getYCoord());
      System.out.println(equip.getFloor());
    }
  */
  public MedicalEquipment(
      String equipmentID,
      String med_equipmendID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation,
      String currentLocation,
      String equipmentType,
      int numID) {
    super(equipmentID, isInUse, isClean, cleanLocation, storageLocation, currentLocation, numID);
    this.med_equipmentID = med_equipmendID;
    this.equipmentType = equipmentType;
  }

  @Override
  public void use() {
    setisInUse(true);
    setClean(false);
  }

  public void cleanEquipment() {
    setisInUse(false);
    setClean(true);
    setCurrentLocation(getStorageLocation());
    setMed_equipmentID(null); // Hopefully won't break when entered into DB Table
  }

  public boolean checkIsClean() {
    return super.isClean();
  }

  public String getMed_equipmentID() {
    return med_equipmentID;
  }

  public void setMed_equipmentID(String med_equipmentID) {
    this.med_equipmentID = med_equipmentID;
  }

  public String getEquipmentType() {
    return equipmentType;
  }

  public void setEquipmentType(String equipmentType) {
    this.equipmentType = equipmentType;
  }

  public String getFloor() throws SQLException {
    Connection connection = AccountsManager.getInstance().getConnection();
    String locationID = getCurrentLocation();
    String result = "";

    Statement statement = connection.createStatement();
    String query = "SELECT FLOOR FROM TOWERLOCATIONS WHERE NODEID = '" + locationID + "'";
    ResultSet rs = statement.executeQuery(query);

    if (rs.next()) {
      result = rs.getString("FLOOR");
    }
    return result;
  }

  @Override
  public String toString() {
    return this.getEquipmentType() + " : " + super.toString();
  }
}
