package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.pageButtons;
import edu.wpi.cs3733.D22.teamE.pageControlFacade;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainController implements pageButtons {
  public AnchorPane mainPane;

  @FXML Button sanitationButton;
  @FXML Button mealDeliveryButton;
  @FXML Button languageButton;
  @FXML Button medicalEquipmentButton;
  @FXML Button medicineDeliveryButton;
  @FXML Button labRequestButton;
  @FXML Button mapButton;
  @FXML Button dashboardButton;
  @FXML Button aboutButton;
  @FXML JFXToggleButton seeAuthors;
  @FXML Pane authors;
  @FXML Button helpButton;
  @FXML JFXToggleButton databaseSwitchButton;

  @FXML private Button btnMode;
  @FXML private ImageView imgMode;
  @FXML private VBox parent;
  @FXML private Label title;

  private final String darkModeURL =
      Objects.requireNonNull(Main.class.getResource("view/styles/darkMode.css")).toExternalForm();
  private final String lightModeURL =
      Objects.requireNonNull(Main.class.getResource("view/styles/default.css")).toExternalForm();
  private boolean isLightMode = true;
  private static String databaseMode = "EMBEDDED_INSTANCE";

  public void changeMode(ActionEvent event) throws FileNotFoundException {
    System.out.println("Button Works!");
    isLightMode = !isLightMode;
    if (isLightMode) {
      setLightMode();
      System.out.println("Light Mode");
    } else {
      System.out.println("Dark Mode");
      setDarkMode();
    }
  }

  private void setLightMode() throws FileNotFoundException {
    parent.getStylesheets().removeAll();
    parent.getStylesheets().add(lightModeURL);
    Image newImg =
        new Image(
            new FileInputStream(
                "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/ic-dark.png"));
    imgMode.setImage(newImg);
  }

  private void setDarkMode() throws FileNotFoundException {
    parent.getStylesheets().removeAll();
    parent.getStylesheets().add(darkModeURL);
    Image newImg =
        new Image(
            new FileInputStream(
                "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/ic-light.png"));
    imgMode.setImage(newImg);
  }

  public mainController() {}

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();
    ;

    pageControlFacade.loadPage(
        "mealDeliveryPage.fxml", thisStage); // TODO: Load exception at this line?
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("statusPage.fxml", thisStage);
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("languagePage.fxml", thisStage);
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("medicalEquipmentPage.fxml", thisStage);
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    pageControlFacade.exitApp();
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("sanitationPage.fxml", thisStage);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("labRequestPage.fxml", thisStage);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }

  @FXML
  public void aboutButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("aboutPage.fxml", thisStage);
  }

  @FXML
  public void helpButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("helpPage.fxml", thisStage);
  }

  @FXML
  public void dashboardButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("DashboardPage.fxml", thisStage);
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("defaultPage.fxml", thisStage);
  }

  @FXML
  public void seeAuthors(ActionEvent event) throws IOException {
    if (seeAuthors.isSelected()) {
      authors.setVisible(true);
    } else {
      authors.setVisible(false);
    }
  }

  @Override
  public void profButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControlFacade.loadPage("profilePage.fxml", thisStage);
  }

  @FXML
  public void databaseSwitchButton(ActionEvent event) throws IOException {
    if (databaseSwitchButton.isSelected()) {
      databaseMode = "CLIENT_INSTANCE";
    } else {
      databaseMode = "EMBEDDED_INSTANCE";
    }
  }

  public static String getDatabaseMode() {
    return databaseMode;
  }
}