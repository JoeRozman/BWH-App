package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentDAOImpl implements DAO<MedicalEquipment> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<MedicalEquipment> equipmentList;

  public MedicalEquipmentDAOImpl() throws SQLException {
    equipmentList = new ArrayList<>();
    String query = "SELECT * FROM EQUIPMENT ORDER BY EQUIPMENTID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    int numID = 0;
    while (rs.next()) {
      String equipmentID = rs.getString("EQUIPMENTID");
      String med_EquipmentID = rs.getString("MED_EQUIP_REQ_ID");
      boolean isInUse = rs.getBoolean("isInUse");
      boolean isClean = rs.getBoolean("isClean");
      String cleanLocation = rs.getString("cleanLocationID");
      String storageLocation = rs.getString("storageLocationID");
      String currentLocation = rs.getString("currentLocationID");
      String equipmentType = rs.getString("equipmentType");

      MedicalEquipment equipment =
          new MedicalEquipment(
              equipmentID,
              med_EquipmentID,
              isInUse,
              isClean,
              cleanLocation,
              storageLocation,
              currentLocation,
              equipmentType,
              numID);
      equipmentList.add(equipment);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<MedicalEquipment> getAll() {
    return equipmentList;
  }

  @Override
  public MedicalEquipment get(String id) {
    for (MedicalEquipment equipment : equipmentList) {
      if (equipment.getEquipmentID().equals(id)) return equipment;
    }
    System.out.println("Location with equipment id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(MedicalEquipment equipment) {
    equipmentList.add(equipment);
  }

  @Override
  public void delete(MedicalEquipment equipment) {
    equipmentList.remove(equipment);
  }

  public List<MedicalEquipment> getMedicalEquipments(
      String equipmentType, int equipmentQuantity, String roomID, String MED_EQUIPMENTID)
      throws SQLException { // TODO: Maybe figure out better way than a double for-loop
    List<MedicalEquipment> equipments = new ArrayList<>();

    // TODO: make this loop and break logic bit more elegant if you get the chance
    // I made it and it's a little wonky - Tim
    int i = 0;
    for (MedicalEquipment equipment : equipmentList) {
      if (equipment.getEquipmentType().equalsIgnoreCase(equipmentType)
          && equipment.checkIsClean()) {
        equipment.use(); // Sets it to be in use and be not clean anymore
        equipment.setMed_equipmentID(MED_EQUIPMENTID); // Binds equipment to the MedEquip Request
        equipment.setCurrentLocation(roomID); // TODO: Add Patient Rooms to TowerLocations.CSV
        equipments.add(equipment);

        // DB Query to update said values
        String query =
            "UPDATE EQUIPMENT SET "
                + "\""
                + "isInUse"
                + "\""
                + " = "
                + true
                + ","
                + "\""
                + "isClean"
                + "\""
                + " = "
                + false
                + ","
                + "\""
                + "currentLocationID"
                + "\""
                + " = '"
                + roomID
                + "', MED_EQUIP_REQ_ID = '"
                + MED_EQUIPMENTID
                + "' WHERE EQUIPMENTID = '"
                + equipment.getEquipmentID()
                + "'"; // Make sure this is actually formatted right
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println(query);
        statement.executeUpdate(); // TODO: FIX "currentLocationID" can't be found??"
        i++;
      }
      if (i == equipmentQuantity) {
        break;
      }
    }
    return equipments;
  }

  public void sendToCleaning(List<MedicalEquipment> equipments) throws SQLException {
    for (MedicalEquipment equipment : equipments) {
      equipment.setCurrentLocation(
          equipment.getCleanLocation()); // Sets current location to where the cleaning location is

      // TODO: Add MedicalEquipment.cleanEquipment() here?

      // DB Query to set currentLocation to cleaningLocation
      String query =
          "UPDATE EQUIPMENT SET CURRENTLOCATIONID = '"
              + equipment.getCleanLocation()
              + "' WHERE EQUIPMENTID = '"
              + equipment.getEquipmentID()
              + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    }
  }
}