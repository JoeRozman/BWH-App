package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.mealDeliveryRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDeliveryRequestDAOImpl implements DAO<mealDeliveryRequest> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<mealDeliveryRequest> mealDeliveryRequests;

  public MealDeliveryRequestDAOImpl() throws SQLException {
    mealDeliveryRequests = new ArrayList<>();
    String query = "SELECT * FROM MEALDELIVERYREQUEST ORDER BY MEAL_REQ_ID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();

    while (rs.next()) {
      String medicineReqID = rs.getString("MEAL_REQ_ID");
      String otherNotes = rs.getString("OTHERNOTES");
      String floorID = rs.getString("FLOOR");
      String roomID = rs.getString("ROOMID");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String entree = rs.getString("ENTREE");
      String beverage = rs.getString("BEVERAGE");
      String dessert = rs.getString("DESSERT");
      String deliveryTime = rs.getString("DELIVERYTIME");

      mealDeliveryRequest request =
          new mealDeliveryRequest(
              medicineReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate(),
              entree,
              beverage,
              dessert,
              deliveryTime);
      mealDeliveryRequests.add(request);
    }
    rs.close();
  }

  @Override
  public List<mealDeliveryRequest> getAll() {
    mealDeliveryRequests = new ArrayList<>();

    try {
      String query = "SELECT * FROM MEALDELIVERYREQUEST ORDER BY MEAL_REQ_ID DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        String medicineReqID = rs.getString("MEAL_REQ_ID");
        String otherNotes = rs.getString("OTHERNOTES");
        String floorID = rs.getString("FLOOR");
        String roomID = rs.getString("ROOMID");
        boolean isUrgent = rs.getBoolean("ISURGENT");
        String status = rs.getString("STATUS");
        String staffAssignee = rs.getString("ASSIGNEE");
        java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
        java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
        String entree = rs.getString("ENTREE");
        String beverage = rs.getString("BEVERAGE");
        String dessert = rs.getString("DESSERT");
        String deliveryTime = rs.getString("DELIVERYTIME");

        mealDeliveryRequest request =
            new mealDeliveryRequest(
                medicineReqID,
                otherNotes,
                floorID,
                roomID,
                isUrgent,
                status,
                staffAssignee,
                requestDate.toLocalDate(),
                deliveryDate.toLocalDate(),
                entree,
                beverage,
                dessert,
                deliveryTime);
        mealDeliveryRequests.add(request);
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

    return mealDeliveryRequests;
  }

  @Override
  public mealDeliveryRequest get(String id) {
    for (mealDeliveryRequest request : mealDeliveryRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println("Meal Delivery Request with meal request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(mealDeliveryRequest item) {
    mealDeliveryRequests.add(item);

    try {
      String query =
          "INSERT INTO MEALDELIVERYREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getEntreeType());
      statement.setString(10, item.getBeverageType());
      statement.setString(11, item.getDessertType());
      statement.setString(12, item.getDeliveryTime());
      statement.setString(13, item.getOtherNotes());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Add Meal Request failed!"); // TODO: Come up with a better catch block
    }
  }

  @Override
  public void delete(mealDeliveryRequest request) {
    mealDeliveryRequests.remove(request);

    String query = "DELETE FROM MEALDELIVERYREQUEST WHERE MEAL_REQ_ID = (?)";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(query);
      statement.setString(1, request.getServiceRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
