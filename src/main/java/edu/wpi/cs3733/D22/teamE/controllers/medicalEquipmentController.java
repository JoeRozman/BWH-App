package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.*;
import edu.wpi.cs3733.D22.teamE.database.daos.*;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.entity.medicalEquipmentRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class medicalEquipmentController extends serviceRequestPageController
    implements Initializable {

  @FXML JFXComboBox<String> equipmentType;
  @FXML JFXComboBox<Integer> equipmentQuantity;
  @FXML DatePicker deliveryDate;
  @FXML CheckBox isUrgent;
  /*@FXML TableView<medicalEquipmentRequest> medRequestTable;

  @FXML TableColumn<medicalEquipmentRequest, String> tableDeliveryDate;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRequestDate;
  @FXML TableColumn<medicalEquipmentRequest, Boolean> tableIsUrgent;
  @FXML TableColumn<medicalEquipmentRequest, String> tableEquipmentType;
  @FXML TableColumn<medicalEquipmentRequest, Integer> tableEquipmentQuantity;
  @FXML TableColumn<medicalEquipmentRequest, String> tableStaffAssignee;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRoomID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableFloorID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRequestStatus;
  @FXML TableColumn<medicalEquipmentRequest, String> tableOtherNotes;*/

  // MedicalEquipmentServiceRequestDAOImpl medEquipmentServiceRequestDB;
  // MedicalEquipmentDAOImpl medEquipmentDB;
  // LocationDAOImpl locationDB;
  DAOSystem system;

  // ObservableList<medicalEquipmentRequest> tableList;

  public medicalEquipmentController() {
    system = DAOSystemSingleton.INSTANCE.getSystem();
  }

  /**
   * Initializes the page by populating the location combo boxes, equipment combo boxes, and the
   * medical equipment request service table
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    setInfographicsCount("MED_EQUIP_REQ");
    try {
      // medEquipmentDB = new MedicalEquipmentDAOImpl();
      populateEquipComboBoxes();

      // medEquipmentServiceRequestDB = new MedicalEquipmentServiceRequestDAOImpl();
      populateMedEquipRequestTable();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Populates the equipment combo boxes, which are equipmentType and equipmentQuantity. Similarly
   * to populateLocationComboBoxes(), equipmentQuantity's selections are determined based on the
   * selection for equipmentType. equipmentQuantity's selections will be Integers from 1 to however
   * many Equipment objects of that type are available, or it will have no selections if none are
   * available.
   */
  private void populateEquipComboBoxes() {
    equipmentQuantity.setVisible(false);

    ArrayList<MedicalEquipment> allEquipment =
        (ArrayList<MedicalEquipment>) system.getAllMedicalEquipments();
    ArrayList<String> equipmentNames = new ArrayList<String>();
    HashMap<String, Integer> equipNameToQuantity = new HashMap<>();
    for (MedicalEquipment e : allEquipment) {
      if (e.checkIsClean() && !e.isInUse()) {
        String curEquipName = e.getEquipmentType();
        if (!equipmentNames.contains(curEquipName)) {
          equipmentNames.add(e.getEquipmentType());
          equipNameToQuantity.put(curEquipName, 1);
        } else {
          equipNameToQuantity.put(curEquipName, equipNameToQuantity.get(curEquipName) + 1);
        }
      }
    }
    equipmentType.setItems(FXCollections.observableArrayList(equipmentNames));
    equipmentType
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<Integer> quantityNums = new ArrayList<Integer>();
                for (int i = 1; i <= equipNameToQuantity.get(newValue); i++) {
                  quantityNums.add(i);
                }
                ObservableList<Integer> quantitySelections =
                    FXCollections.observableArrayList(quantityNums);
                equipmentQuantity.setItems(quantitySelections);
                equipmentQuantity.setVisible(true);
              }
            });
  }

  /**
   * Populates the medicalEquipmentRequest table from the database. Sets the cell values for each
   * column to be the corresponding variable in medicalEquipmentRequest.
   */
  private void populateMedEquipRequestTable() {
    /*ObservableList<medicalEquipmentRequest> medicalEquipmentRequests = populateMedEquipList();
    tableDeliveryDate.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("deliveryDate"));
    tableRequestDate.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("requestDate"));
    tableIsUrgent.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, Boolean>("isUrgent"));
    tableEquipmentType.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("equipment"));
    tableEquipmentQuantity.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, Integer>("equipmentQuantity"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("staffAssignee"));
    tableRoomID.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<medicalEquipmentRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<medicalEquipmentRequest, String> param) {
            medicalEquipmentRequest curMedicalEquipReq = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curMedicalEquipReq.getRoomID()));
          }
        });
    tableFloorID.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("floorID"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("requestStatus"));
    tableOtherNotes.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("otherNotes"));

    medRequestTable.setItems(medicalEquipmentRequests);*/
  }

  /**
   * Gets the full list of medical equipment requests currently in the database.
   *
   * @return list of medicalEquipmentRequest objects in the database
   */
  /*protected ObservableList<medicalEquipmentRequest> populateMedEquipList() {
    List<medicalEquipmentRequest> list = system.getAllMedicalEquipmentRequests();
    // TODO: FXCollections.observableArrayList(list) ???
    tableList = FXCollections.observableArrayList();
    for (medicalEquipmentRequest m : list) {
      tableList.add(m);
    }
    return tableList;
  }*/

  @FXML
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      medicalEquipmentRequest medicalEquipmentRequest = new medicalEquipmentRequest();
      medicalEquipmentRequest.setFloorID(floor.getValue());
      medicalEquipmentRequest.setRoomID(roomNameToRoomID.get(room.getValue()));
      medicalEquipmentRequest.setEquipment(equipmentType.getValue());
      medicalEquipmentRequest.setEquipmentQuantity(equipmentQuantity.getValue());
      medicalEquipmentRequest.setRequestStatus(requestStatus.getValue());
      medicalEquipmentRequest.setStaffAssignee(staffAssignee.getValue());
      medicalEquipmentRequest.setDeliveryDate(deliveryDate.getValue());
      medicalEquipmentRequest.setRequestDate(LocalDate.now());
      medicalEquipmentRequest.setIsUrgent(isUrgent.isSelected());
      medicalEquipmentRequest.setOtherNotes(notes.getText());
      medSendToDB(medicalEquipmentRequest);
      setInfographicsCount("MED_EQUIP_REQ");
      PopUp.submissionConfirmation(
          "Your medical equipment service request has been submitted.",
          submitButton.getScene().getWindow());

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", drawer.getScene().getWindow());
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    equipmentType.getSelectionModel().clearSelection();
    equipmentQuantity.getSelectionModel().clearSelection();
    deliveryDate.getEditor().clear();
    isUrgent.setSelected(false);
    requestStatus.getSelectionModel().clearSelection();
    staffAssignee.getSelectionModel().clearSelection();
    notes.clear();
    room.setVisible(false);
  }

  private void medSendToDB(medicalEquipmentRequest medEquipmentRequest) throws SQLException {
    system.addMedEquipReq(
        medEquipmentRequest); // TODO FIX MEDEQUIPREQ UPDATE TO REPLACE THIS!!!!!!!!
    // tableList.add(medEquipmentRequest);
    List<MedicalEquipment> equipmentUsed =
        system.getMedicalEquipments(
            medEquipmentRequest.getEquipment(),
            medEquipmentRequest.getEquipmentQuantity(),
            medEquipmentRequest.getRoomID(),
            medEquipmentRequest.getServiceRequestID());

    if (medEquipmentRequest.getRequestStatus().equals("Done")) {
      system.sendToCleaning(equipmentUsed);
    }
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    equipmentType.getSelectionModel().clearSelection();
    equipmentQuantity.getSelectionModel().clearSelection();
    deliveryDate.getEditor().clear();
    isUrgent.setSelected(false);
    requestStatus.getSelectionModel().clearSelection();
    staffAssignee.getSelectionModel().clearSelection();
    notes.clear();
    room.setVisible(false);
  }
}
