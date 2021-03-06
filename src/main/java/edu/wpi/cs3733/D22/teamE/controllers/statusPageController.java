package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.customUI.CustomJFXButtonTableCell;
import edu.wpi.cs3733.D22.teamE.customUI.CustomTextFieldTableCell;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This is the controller class for the Status Page. It inherits from the containsSideMenu class to
 * give the side menu functionality.
 */
public class statusPageController implements Initializable {
  @FXML TextField filterFieldDate;
  @FXML TextField filterFieldID;
  @FXML TextField filterFieldType;
  @FXML TextField filterFieldStatus;
  @FXML TextField filterFieldAssign;
  @FXML JFXButton deleteReqButton;
  @FXML TableView<serviceRequestModel> requestTable;
  @FXML TableColumn<serviceRequestModel, String> idColumn;
  @FXML TableColumn<serviceRequestModel, String> typeColumn;
  @FXML TableColumn<serviceRequestModel, String> statusColumn;
  @FXML TableColumn<serviceRequestModel, String> assignedColumn;
  @FXML TableColumn<serviceRequestModel, String> dateColumn;
  @FXML TableColumn<serviceRequestModel, RequestInterface> buttonColumn;
  DAOSystem db;

  /** Constructor */
  public statusPageController() {}

  /**
   * Initialize the super class and the database object.
   *
   * @param url unneeded here - inherited parameter
   * @param rb uneeded here - inherited parameter
   */
  public void initialize(URL url, ResourceBundle rb) {
    genTable();
    boolean admin =
        (AccountsManager.getInstance().getAccount().getAuthorityLevel() >= Account.adminPerm);

    if (!admin) {
      deleteReqButton.setDisable(true);
      deleteReqButton.setVisible(false);
    }
  }

  public void genTable() {
    ObservableList<serviceRequestModel> requestList = populateList();
    requestTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    FilteredList<serviceRequestModel> filteredData = new FilteredList<>(requestList, p -> true);
    filterFieldID
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  requestModel -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return requestModel.getID().toLowerCase().contains(lowerCaseFilter);
                  });
            });

    filterFieldType
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  requestModel -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return requestModel.getType().toLowerCase().contains(lowerCaseFilter);
                  });
            });

    filterFieldStatus
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  requestModel -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return requestModel.getStatus().toLowerCase().contains(lowerCaseFilter);
                  });
            });

    filterFieldAssign
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  requestModel -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return requestModel.getAssignee().toLowerCase().contains(lowerCaseFilter);
                  });
            });
    filterFieldDate
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  requestModel -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return requestModel.getRequestDate().toLowerCase().contains(lowerCaseFilter);
                  });
            });
    idColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("ID"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("Type"));
    typeColumn.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    typeColumn.setEditable(false);
    statusColumn.setCellValueFactory(
        new PropertyValueFactory<serviceRequestModel, String>("Status"));
    // statusColumn.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    /*statusColumn.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<serviceRequestModel, String>>() {
      @Override
      public void handle(TableColumn.CellEditEvent<serviceRequestModel, String> event) {
        serviceRequest r =
            db.getServiceRequest(
                event.getTableView().getItems().get(event.getTablePosition().getRow()).getID());
        r.setRequestStatus(event.getNewValue());
        db.updateServiceRequest(r);
        genTable();
      }
    });*/
    assignedColumn.setCellValueFactory(
        new PropertyValueFactory<serviceRequestModel, String>("Assignee"));
    // assignedColumn.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    // assignedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    /*assignedColumn.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<serviceRequestModel, String>>() {
      @Override
      public void handle(TableColumn.CellEditEvent<serviceRequestModel, String> event) {
        serviceRequest r =
            db.getServiceRequest(
                event.getTableView().getItems().get(event.getTablePosition().getRow()).getID());
        r.setStaffAssignee(event.getNewValue());
        db.updateServiceRequest(r);
        genTable();
      }
    });*/
    dateColumn.setCellValueFactory(
        new PropertyValueFactory<serviceRequestModel, String>("requestDate"));
    buttonColumn.setCellValueFactory(new PropertyValueFactory<>("request"));
    buttonColumn.setCellFactory(CustomJFXButtonTableCell.forTableColumn(this));
    requestTable.setItems(filteredData);
  }

  /**
   * Populates the table with the respective data for the active service requests.
   *
   * @return an ObservableList of serviceRequestModels
   */
  protected ObservableList<serviceRequestModel> populateList() {
    List<RequestInterface> list = DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests();
    ObservableList<serviceRequestModel> tableList = FXCollections.observableArrayList();
    ArrayList<String> usedIDS = new ArrayList<>();
    for (RequestInterface r : list) {
      if (!usedIDS.contains(r.getServiceRequestID())) {
        usedIDS.add(r.getServiceRequestID());
        tableList.add(
            new serviceRequestModel(
                r.getServiceRequestID(),
                r.getRequestStatus(),
                r.getRequestType().toString(),
                r.getStaffAssignee(),
                r.getRequestDate().toString(),
                ((r.getDeliveryDate() == null) ? "" : r.getDeliveryDate().toString()),
                r.getIsUrgent(),
                r.getOtherNotes(),
                r));
      }
    }
    return tableList;
  }

  @FXML
  public void deleteReqButton(ActionEvent event) {
    ArrayList<serviceRequestModel> p =
        new ArrayList<>(requestTable.getSelectionModel().getSelectedItems());
    for (serviceRequestModel req : p) {
      RequestInterface r = DAOSystemSingleton.INSTANCE.getSystem().getServiceRequest(req.getID());
      DAOSystemSingleton.INSTANCE.getSystem().delete(r);
    }
    genTable();
  }

  // todo : add open request(s) here
}
