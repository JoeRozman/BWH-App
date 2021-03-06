package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.ProfilePictureManager;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class HeaderController implements Initializable {

  @FXML JFXToggleNode homeButton;
  @FXML JFXToggleNode aboutUsButton;
  @FXML JFXToggleNode helpButton;
  @FXML JFXButton profileButton;
  @FXML Circle profilePicture;
  @FXML SVGPath bwhLogo;
  @FXML HBox bwhLogoAndLabel;

  ToggleGroup headerPageButtons;
  JFXToggleNode selectedPageButton;
  ContextMenu profileMenu;
  MenuItem profileMenuProfile;
  MenuItem profileMenuLogout;
  MenuItem profileMenuExit;

  @FXML public JFXHamburger burger;
  // Node box;
  Node box;
  JFXDrawer drawer;
  BorderPane root;

  public HeaderController(JFXDrawer drawer, BorderPane root) {
    this.drawer = drawer;
    this.root = root;
  }

  public HeaderController() {}

  public void initialize(URL location, ResourceBundle resources) {

    headerPageButtons = new ToggleGroup();
    homeButton.setToggleGroup(headerPageButtons);
    aboutUsButton.setToggleGroup(headerPageButtons);
    helpButton.setToggleGroup(headerPageButtons);
    selectedPageButton = homeButton;

    updatePFP();

    bwhLogoAndLabel.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {
            pageControl.loadCenter("homePage.fxml", (Stage) root.getScene().getWindow());
          }
        });

    highlightHeaderButtonHandler(homeButton);
    highlightHeaderButtonHandler(aboutUsButton);
    highlightHeaderButtonHandler(helpButton);

    headerButtonClickedHandler(homeButton, "homePage.fxml");
    headerButtonClickedHandler(aboutUsButton, "aboutPage.fxml");
    headerButtonClickedHandler(helpButton, "helpPage.fxml");

    profileHandler();

    handleClickable(burger);
    handleClickable(bwhLogoAndLabel);
    handleClickable(profileButton);

    drawerHandler();

    /*
    assert (burger != null);
    assert (drawer != null);
    HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(burger);
    box = pageControl.getPageRoot("sidePanel.fxml");
    System.out.println(box);
    drawer.setSidePane(box);
    drawer.setOnDrawerClosed(
        new EventHandler<JFXDrawerEvent>() {
          @Override
          public void handle(JFXDrawerEvent event) {
            box.setDisable(true);
            drawer.setDisable(true);
            transition.setRate(-1);
            transition.play();
          }
        });
    box.setDisable(true);
    drawer.setDisable(true);

    transition.setRate(-1);
    burger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();
          if (drawer.isOpened()) {
            drawer.close();
            box.setDisable(true);
            drawer.setDisable(true);
          } else {
            drawer.open();
            box.setDisable(false);
            drawer.setVisible(true);
            drawer.setDisable(false);
          }
        });
     */

    ProfilePictureManager.setHeaderReference(this);
  }

  public void updatePFP() {
    Employee currentEmployee = AccountsManager.getInstance().getEmployee();
    Image image;
    try {
      InputStream is = ProfilePictureManager.getPersonalPicture(currentEmployee);
      image = new Image(is);
    } catch (Exception e) {
      URL url = Main.class.getResource("view/icons/profilepic.png");
      assert url != null;
      InputStream is = null;
      try {
        is = url.openStream();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      image = new Image(is);
    }
    profilePicture.setFill(new ImagePattern(image));
  }

  private void handleClickable(Node clickable) {
    clickable.setOnMouseEntered(
        mouseEvent -> {
          root.getScene().setCursor(Cursor.HAND);
        });
    clickable.setOnMouseExited(
        mouseEvent -> {
          root.getScene().setCursor(Cursor.DEFAULT);
        });
  }

  private void headerButtonClickedHandler(JFXToggleNode headerButton, String url) {
    headerButton.setOnMouseClicked(
        mouseEvent -> {
          pageControl.loadCenter(url, (Stage) root.getScene().getWindow());
          headerButton.setSelected(false);
        });
  }

  private void highlightHeaderButtonHandler(JFXToggleNode headerButton) {
    headerButton.setOnMouseEntered(
        mouseEvent -> {
          headerButton.getStyleClass().clear();
          headerButton.getStyleClass().add("headerSelectedButton");
          root.getScene().setCursor(Cursor.HAND);
        });

    headerButton.setOnMouseExited(
        mouseEvent -> {
          headerButton.getStyleClass().clear();
          headerButton.getStyleClass().add("headerButton");
          root.getScene().setCursor(Cursor.DEFAULT);
        });
  }

  private void profileHandler() {
    profileMenu = new ContextMenu();
    profileMenu.getStyleClass().add("lessRounded");

    profileMenuProfile = new MenuItem("See Profile");
    profileMenuProfile.getStyleClass().add("bodyText");
    profileMenuProfile.getStyleClass().add("lessRounded");

    profileMenuLogout = new MenuItem("Logout");
    profileMenuLogout.getStyleClass().add("bodyText");

    profileMenuExit = new MenuItem("Exit Application");
    profileMenuExit.getStyleClass().add("bodyText");
    profileMenuExit.getStyleClass().add("lessRounded");

    // TODO: change the profile imageview based on the current user's profile picture
    profileMenu.getItems().addAll(profileMenuProfile, profileMenuLogout, profileMenuExit);

    profileMenuProfile.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            pageControl.loadCenter("profilePage.fxml", (Stage) root.getScene().getWindow());
            profileMenu.hide();
          }
        });

    profileMenuLogout.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            pageControl.loadPage("loginPage.fxml", (Stage) root.getScene().getWindow());
            profileMenu.hide();
          }
        });

    profileMenuExit.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            pageControl.exitApp(profileButton.getScene().getWindow());
          }
        });

    profileButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            profileMenu.show(profileButton, Side.BOTTOM, 0, 0);
          }
        });
  }

  @FXML
  private void drawerHandler() {
    burger.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
            drawer.toggle();
          }
        });
  }
}
