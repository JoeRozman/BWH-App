package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.MealDeliveryRequest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class MealDeliveryController extends ServiceRequestPageController implements Initializable {
  @FXML ComboBox<String> entreeDropDown;
  @FXML ComboBox<String> beverageDropDown;
  @FXML ComboBox<String> dessertDropDown;
  @FXML TextField roomNumberTxt;
  @FXML DatePicker dateTime;
  @FXML TextField timeTxt;
  @FXML CheckBox asapCheck;
  @FXML TextArea otherNotesTxt;

  MealDeliveryRequest mealDeliveryRequest = new MealDeliveryRequest();

  ObservableList<String> meals =
      FXCollections.observableArrayList(
          "Chicken Parmesan", "Turkey Dinner", "Chicken Noodle Soup", "Two Cookies");
  ObservableList<String> beverages =
      FXCollections.observableArrayList("Water", "Coca-Cola", "Pepsi", "Root Beer");
  ObservableList<String> desserts =
      FXCollections.observableArrayList("Cookies", "Vanilla Cake", "Tiramisu", "Chocolate Cake");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    entreeDropDown.setItems(meals);
    beverageDropDown.setItems(beverages);
    dessertDropDown.setItems(desserts);
  }

  public MealDeliveryController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      mealDeliveryRequest.setEntreeType(entreeDropDown.getValue());
      mealDeliveryRequest.setBeverageType(beverageDropDown.getValue());
      mealDeliveryRequest.setDessertType(dessertDropDown.getValue());
      mealDeliveryRequest.setRoomNumber(Integer.parseInt("0" + roomNumberTxt.getText()));
      mealDeliveryRequest.setDeliveryDate(dateTime.getValue());
      mealDeliveryRequest.setDeliveryTime(Integer.parseInt("0" + timeTxt.getText()));
      mealDeliveryRequest.setASAP(asapCheck.isSelected());
      mealDeliveryRequest.setOtherNotes(otherNotesTxt.getText());
      if (entreeDropDown.getValue().isEmpty()
          || entreeDropDown.getValue().isBlank()
          || beverageDropDown.getValue().isEmpty()
          || beverageDropDown.getValue().isBlank()
          || dessertDropDown.getValue().isEmpty()
          || dessertDropDown.getValue().isBlank()
          || dateTime.getValue().equals(0)
          || dateTime.getValue().equals(0)) {
        throw new NullPointerException();
      }
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  //  @FXML
  //  private void resetButton(ActionEvent event) {
  //    entreeDropDown.clearSelection();
  //    room.getSelectionModel().clearSelection();
  //    equipmentType.getSelectionModel().clearSelection();
  //    equipmentQuantity.getSelectionModel().clearSelection();
  //    deliveryDate.clear();
  //    deliveryTime.clear();
  //    isUrgent.setSelected(false);
  //    notes.clear();
  //  }
}
