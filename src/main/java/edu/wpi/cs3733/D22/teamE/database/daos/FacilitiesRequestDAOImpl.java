package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.facilitiesRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacilitiesRequestDAOImpl implements DAO<facilitiesRequest> {

  static Connection connection = AccountsManager.getInstance().getConnection();
  List<facilitiesRequest> facilitiesRequests;

  public FacilitiesRequestDAOImpl() throws SQLException {
    facilitiesRequests = new ArrayList<>();
    String query = "SELECT * FROM FACILITIESREQUEST ORDER BY FACILITIESREQID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      String ReqID = rs.getString("FACILITIESREQID");
      String ReqType = rs.getString("FACILITIESREQTYPE");
      String timeFrame = rs.getString("TIMEFRAME");
      String floorID = rs.getString("FLOORID");
      String roomID = rs.getString("ROOMID");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");

      java.sql.Date deliveryDate = Date.valueOf(LocalDate.now());
      java.sql.Date requestDate = Date.valueOf(LocalDate.now());

      facilitiesRequest facilitiesRequest =
          new facilitiesRequest(
              ReqID,
              ReqType,
              timeFrame,
              floorID,
              roomID,
              isUrgent,
              staffAssignee,
              requestStatus,
              otherNotes,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate());
      facilitiesRequests.add(facilitiesRequest);
    }
    rs.close();
  }

  /**
   * Return all lab requests
   *
   * @return a list of all lab requests
   */
  @Override
  public List<facilitiesRequest> getAll() {
    facilitiesRequests = new ArrayList<>();

    try {
      String query = "SELECT * FROM FACILITIESREQUEST ORDER BY FACILITIESREQID DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        String ReqID = rs.getString("FACILITIESREQID");
        String ReqType = rs.getString("FACILITIESREQTYPE");
        String timeFrame = rs.getString("TIMEFRAME");
        String floorID = rs.getString("FLOORID");
        String roomID = rs.getString("ROOMID");
        boolean isUrgent = rs.getBoolean("ISURGENT");
        String staffAssignee = rs.getString("STAFFASSIGNEE");
        String requestStatus = rs.getString("REQUESTSTATUS");
        String otherNotes = rs.getString("OTHERNOTES");

        java.sql.Date deliveryDate = Date.valueOf(LocalDate.now());
        java.sql.Date requestDate = Date.valueOf(LocalDate.now());

        facilitiesRequest facilitiesRequest =
            new facilitiesRequest(
                ReqID,
                ReqType,
                timeFrame,
                floorID,
                roomID,
                isUrgent,
                staffAssignee,
                requestStatus,
                otherNotes,
                requestDate.toLocalDate(),
                deliveryDate.toLocalDate());
        facilitiesRequests.add(facilitiesRequest);
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

    return facilitiesRequests;
  }

  /**
   * Method to get a specific facilities request
   *
   * @param id The id of the specific lab request wanted
   * @return
   */
  @Override
  public facilitiesRequest get(String id) {
    for (facilitiesRequest facilitiesRequest : facilitiesRequests) {
      if (facilitiesRequest.getServiceRequestID().equals(id)) return facilitiesRequest;
    }
    System.out.println("Facilities Request with id " + id + " not found");
    throw new NullPointerException();
  }

  /**
   * Add a new lab request to the DB
   *
   * @param facilitiesRequest lab request to add
   */
  @Override
  public void update(facilitiesRequest facilitiesRequest) {
    facilitiesRequests.add(facilitiesRequest);

    try {
      String query =
          "INSERT INTO FACILITIESREQUEST VALUES ('"
              + facilitiesRequest.getServiceRequestID()
              + "','"
              + facilitiesRequest.getFacilitiesReqType()
              + "','"
              + facilitiesRequest.getTimeFrame()
              + "','"
              + facilitiesRequest.getFloorID()
              + "','"
              + facilitiesRequest.getRoomID()
              + "','"
              + facilitiesRequest.getIsUrgent()
              + "','"
              + facilitiesRequest.getStaffAssignee()
              + "','"
              + facilitiesRequest.getRequestStatus()
              + "','"
              + facilitiesRequest.getRequestDate()
              + "','"
              + facilitiesRequest.getDeliveryDate()
              + "','"
              + facilitiesRequest.getOtherNotes()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      System.out.println(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes facilities Request from DB
   *
   * @param facilitiesRequest lab request to delete
   */
  @Override
  public void delete(facilitiesRequest facilitiesRequest) {

    // Deleting the facilities request from the array list
    facilitiesRequests.remove(facilitiesRequest);

    // Remove lab request in the db
    try {
      String query =
          "DELETE FROM FACILITIESREQUEST WHERE FACILITIESREQID = ('"
              + facilitiesRequest.getServiceRequestID()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
