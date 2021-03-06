package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.medicineDelivery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements DAO<RequestInterface> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<RequestInterface> serviceRequests;
  DAO<medicalEquipmentRequest> medicalEquipmentServiceRequestDAO =
      new MedicalEquipmentServiceRequestDAOImpl();
  DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
  DAO<languageInterpreterRequest> languageInterpreterRequestDAO = new LanguageRequestDAOImpl();
  DAO<mealDeliveryRequest> mealDeliveryRequestDAO = new MealDeliveryRequestDAOImpl();
  DAO<medicineDelivery> medicineDeliveryDAO = new MedicineDeliveryDAOImpl();
  DAO<sanitationRequest> sanitationRequestDAO = new SanitationRequestDAOImpl();
  DAO<giftDeliveryRequest> giftDeliveryRequestDAO = new GiftRequestDAOImpl();
  DAO<securityRequest> securityRequestDAO = new SecurityRequestDAOImpl();
  DAO<facilitiesRequest> facilitiesRequestDAO = new FacilitiesRequestDAOImpl();

  public ServiceRequestDAOImpl() throws SQLException {
    serviceRequests = new ArrayList<>();

    serviceRequests.addAll(medicalEquipmentServiceRequestDAO.getAll());
    serviceRequests.addAll(labRequestDAO.getAll());
    serviceRequests.addAll(languageInterpreterRequestDAO.getAll());
    serviceRequests.addAll(mealDeliveryRequestDAO.getAll());
    serviceRequests.addAll(medicineDeliveryDAO.getAll());
    serviceRequests.addAll(sanitationRequestDAO.getAll());
    serviceRequests.addAll(giftDeliveryRequestDAO.getAll());
    serviceRequests.addAll(securityRequestDAO.getAll());
    serviceRequests.addAll(facilitiesRequestDAO.getAll());
  }

  @Override
  public List<RequestInterface> getAll() {
    List<RequestInterface> requests = new ArrayList<>();

    requests.addAll(medicalEquipmentServiceRequestDAO.getAll());
    requests.addAll(labRequestDAO.getAll());
    requests.addAll(languageInterpreterRequestDAO.getAll());
    requests.addAll(mealDeliveryRequestDAO.getAll());
    requests.addAll(medicineDeliveryDAO.getAll());
    requests.addAll(sanitationRequestDAO.getAll());
    requests.addAll(giftDeliveryRequestDAO.getAll());
    requests.addAll(securityRequestDAO.getAll());
    requests.addAll(facilitiesRequestDAO.getAll());

    return requests;
  }

  public void printAll() {
    for (RequestInterface request : serviceRequests) {
      System.out.println(request.getRequestType() + " : " + request.getServiceRequestID());
    }
  }

  /*public void getCoords() throws SQLException {
    DAO<Location> locationDAO = new LocationDAOImpl();
    for (RequestInterface request : serviceRequests) {
      try {
        Location location = locationDAO.get(request.getRoomID());
        request.setxCoord(location.getXcoord());
        request.setyCoord(location.getYcoord());
        request.setFloorID(location.getFloor());
      } catch (NullPointerException e) {
        e.printStackTrace();
        request.setxCoord(-1);
        request.setyCoord(-1);
      }
    }
  }*/

  @Override
  public RequestInterface get(String id) {
    for (RequestInterface request : serviceRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }
    System.out.println("Service Request with service request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(RequestInterface request) {
    delete(request);
    serviceRequests.add(request);
    switch (request.getRequestType()) {
      case LAB_REQUEST:
        labRequestDAO.update((labRequest) request);
        break;
      case FACILITIES_REQ:
        facilitiesRequestDAO.update((facilitiesRequest) request);
        break;
      case MED_DELIV_REQ:
        medicineDeliveryDAO.update((medicineDelivery) request);
        break;
      case MEAL_DELIV_REQ:
        mealDeliveryRequestDAO.update((mealDeliveryRequest) request);
        break;
      case MED_EQUIP_REQ:
        medicalEquipmentServiceRequestDAO.update((medicalEquipmentRequest) request);
        break;
      case SANITATION_REQ:
        sanitationRequestDAO.update((sanitationRequest) request);
        break;
      case LANG_INTERP_REQ:
        languageInterpreterRequestDAO.update((languageInterpreterRequest) request);
        break;
      case GIFT_REQUEST:
        giftDeliveryRequestDAO.update((giftDeliveryRequest) request);
        break;
      case SECURITY_REQ:
        securityRequestDAO.update((securityRequest) request);
      default:
        break;
    }
  }

  @Override
  public void delete(RequestInterface request) {
    boolean found = false;
    for (RequestInterface r : serviceRequests) {
      if (r.getServiceRequestID().equals(request.getServiceRequestID())) {
        found = true;
        break;
      }
    }
    if (!found) return;
    serviceRequests.remove(request);

    switch (request.getRequestType()) {
      case LAB_REQUEST:
        labRequestDAO.delete((labRequest) request);
        break;
      case FACILITIES_REQ:
        facilitiesRequestDAO.delete((facilitiesRequest) request);
        break;
      case MED_DELIV_REQ:
        medicineDeliveryDAO.delete((medicineDelivery) request);
        break;
      case MEAL_DELIV_REQ:
        mealDeliveryRequestDAO.delete((mealDeliveryRequest) request);
        break;
      case MED_EQUIP_REQ:
        medicalEquipmentServiceRequestDAO.delete((medicalEquipmentRequest) request);
        break;
      case SANITATION_REQ:
        sanitationRequestDAO.delete((sanitationRequest) request);
        break;
      case LANG_INTERP_REQ:
        languageInterpreterRequestDAO.delete((languageInterpreterRequest) request);
        break;
      case GIFT_REQUEST:
        giftDeliveryRequestDAO.delete((giftDeliveryRequest) request);
        break;
      case SECURITY_REQ:
        securityRequestDAO.delete((securityRequest) request);
      default:
        break;
    }
  }

  public void updateRoomLocation(RequestInterface request, int newXCoord, int newYCoord)
      throws SQLException {
    String query =
        "UPDATE TOWERLOCATIONS SET XCOORD = "
            + newXCoord
            + ", YCOORD = "
            + newYCoord
            + " WHERE NODEID = '"
            + request.getRoomID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }
}
