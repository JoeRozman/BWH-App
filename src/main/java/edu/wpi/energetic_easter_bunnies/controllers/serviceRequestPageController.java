package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public abstract class serviceRequestPageController extends containsSideMenu {

  @FXML TextField notes;
  @FXML Button submitButton;
  @FXML Button resetButton;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  @FXML JFXComboBox<String> floor;
  @FXML JFXComboBox<String> room;

  LocationDAOImpl locationDB;

  serviceRequestPageController() {
    super();
  }

  /**
   * Calls the initialize function for containsSideMenu and then populates the location combo boxes.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    try {
      populateLocationComboBoxes();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes and returns a LocationDAOImpl object to be used for populating location combo boxes
   * @return an initialized LocationDAOImpl object
   * @throws SQLException if there is an error accessing the database
   */
  private LocationDAOImpl initalizeLocationDAO() throws SQLException {
    locationDB = new LocationDAOImpl();
    return locationDB;
  }

  /**
   * Populates the location combo boxes by first grabbing all the floors of the hospital and then
   * mapping them to an ArrayList of all the rooms on that floor. The room combo box selections are
   * determined based on the selection from the floor combo box.
   */
  protected void populateLocationComboBoxes() throws SQLException {
    //Initializes the locationDB object
    initalizeLocationDAO();

    /*
      Sets up data structures for storing floor and room values.
      A hashmap with floor name keys and lists of room names as values is used to
      associate rooms with their corresponding floor
    */
    List<Location> locations = locationDB.getAllLocations();
    List<String> floors = new ArrayList<>();
    HashMap<String, ArrayList<String>> floorToRooms = new HashMap<>();
    //Makes the room combo box hidden until a floor value is selected
    room.setVisible(false);

    //Populates location list and hashmap
    for (Location l : locations) {
      String floor = l.getFloor();
      if (!floors.contains(floor)) {
        floors.add(floor);
      }
      ArrayList<String> roomsOnFloor;
      if (!floorToRooms.containsKey(floor)) {
        roomsOnFloor = new ArrayList<>();
      } else {
        roomsOnFloor = floorToRooms.get(floor);
      }
      roomsOnFloor.add(l.getShortName());
      floorToRooms.put(floor, roomsOnFloor);
    }

    //Sets value of floor combo box to list of floor names
    floor.setItems(FXCollections.observableArrayList(floors));

    //Adds a listener to the floor combo box so that once a selection is made, the corresponding room values
    //for that floor populate the room combo box
    floor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> roomsToDisplay =
                    FXCollections.observableArrayList((floorToRooms.get(newValue)));
                room.setItems(roomsToDisplay);
                room.setVisible(true);
              }
            });
  }

  public boolean sendToDB(serviceRequest request) {
    // todo : implement DB communication
    return true;
  }

  public void populateRequestTable() {
    // todo : get all service requests as list
    // todo : filter through to match MY type
    // todo : populate my table
  }

  @FXML
  public abstract void submitButton(ActionEvent event) throws SQLException;
}
