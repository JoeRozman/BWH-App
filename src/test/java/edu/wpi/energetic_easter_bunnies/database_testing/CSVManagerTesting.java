package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamE.database.CSVManager;
import edu.wpi.cs3733.D22.teamE.database.DBCreation;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class CSVManagerTesting {

  @Test
  public void testLoadLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    assertTrue(CSVManager.loadLocationCSV(CSVManager.getDefaultLocationFilename()));
  }

  @Test
  public void testSaveLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    String filename = CSVManager.getDefaultLocationFilename(); // generalize
    // CSVManager.saveLocationCSV(filename);
    CSVManager.saveAllCSVs();

    assertTrue(new File("CSVsaveFiles/" + filename).exists());
  }

  @Test
  public void testLoadEmployeesFile() throws SQLException, IOException {
    DBCreation.createTables();
    assertTrue(CSVManager.loadEmployeesCSV(CSVManager.getDefaultEmployeesFilename()));
  }

  @Test
  public void testLoadEquipmentFile() throws SQLException, IOException {
    DBCreation.createTables();
    assertTrue(CSVManager.loadEquipmentCSV(CSVManager.getDefaultEquipmentFilename()));
  }

  @Test
  public void testLoadMedEquipRequestFile() throws SQLException, IOException {
    DBCreation.createTables();
    assertTrue(CSVManager.loadMedEquipReqCSV(CSVManager.getDefaultMedEquipRequestFilename()));
  }

  @Test
  public void generateNewSavesTest() {
    CSVManager.generateNewSaves();
  }
}
