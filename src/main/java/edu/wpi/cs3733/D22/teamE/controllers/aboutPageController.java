package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class aboutPageController {

  public aboutPageController() throws FileNotFoundException {}

  @FXML Hyperlink wilsonWong;
  @FXML Pane joeyPane;

  private String joeyBio =
      "Joey Rozman was our Lead Software Engineer. Joey is also a very active member in Tau Kappa Epsilon, power-lifts in his free time, and loves baking.";
  private String colinBio =
      "Colin Mettler was our Assistant-Lead Front-End Engineer. In his free time, he plays for WPI's CS:GO team, relishes his days as a lacrosse player, and throws rocks at Phil. ";
  private String ericBio =
      "Eric Zhou was our Assistant-Lead Back-End Engineer. Eric was very pleased when Joey misunderstood a Slack message to mean that we were meeting at a gas station off campus.  He also supports identity theft.";
  private String jeremyBio =
      "Jeremy Bornstein was a Full-Time Back-End Engineer.  I guess you could say he was BORN to do this.  Jeremy also loves baking.";
  private String shenBio = "Shen Fang was a Full-Time Back-End Engineer.  That is all.";
  private String timBio =
      "Tim Connors was a Full-Time Front-End Engineer. Tim spear-headed our efforts for connecting the front and back end.";
  private String danielBio =
      "Daniel Stoiber was our Scrum Master. Daniel was our young-gun being that he was our only freshmen, and yet he still knew more than the RBE majors on the team.";
  private String hannahBio =
      "Hannah Zink was our Project Manager. Hannah was one of the RBE majors on team, so she was already desensitized to the pain this class caused.";
  private String philBio =
      "Phillip Brush was our Documentation Analyst. Phil was the other RBE major on the team, and was technically overloading during this team.  It's a miracle he survived.";
  private String sizheBio =
      "Sizhe Li was our Product Owner. Frank contributed a lot to the UI overhaul despite his nonexistent class attendance.";

  @FXML
  public void wilsonWongLink(ActionEvent event) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("http://wilsonwong.org/"));
  }

  @FXML
  public void joeyPopUp(MouseEvent event) {
    PopUp.aboutPopUp(joeyBio, ((Node) event.getSource()).getScene().getWindow(), "Joey");
  }

  @FXML
  public void colinPopUp(MouseEvent event) {
    PopUp.aboutPopUp(colinBio, ((Node) event.getSource()).getScene().getWindow(), "Colin");
  }

  @FXML
  public void ericPopUp(MouseEvent event) {
    PopUp.aboutPopUp(ericBio, ((Node) event.getSource()).getScene().getWindow(), "Eric");
  }

  @FXML
  public void jeremyPopUp(MouseEvent event) {
    PopUp.aboutPopUp(jeremyBio, ((Node) event.getSource()).getScene().getWindow(), "Jeremy");
  }

  @FXML
  public void shenPopUp(MouseEvent event) {
    PopUp.aboutPopUp(shenBio, ((Node) event.getSource()).getScene().getWindow(), "Shen");
  }

  @FXML
  public void timPopUp(MouseEvent event) {
    PopUp.aboutPopUp(timBio, ((Node) event.getSource()).getScene().getWindow(), "Tim");
  }

  @FXML
  public void danielPopUp(MouseEvent event) {
    PopUp.aboutPopUp(danielBio, ((Node) event.getSource()).getScene().getWindow(), "Daniel");
  }

  @FXML
  public void hannahPopUp(MouseEvent event) {
    PopUp.aboutPopUp(hannahBio, ((Node) event.getSource()).getScene().getWindow(), "Hannah");
  }

  @FXML
  public void philPopUp(MouseEvent event) {
    PopUp.aboutPopUp(philBio, ((Node) event.getSource()).getScene().getWindow(), "Phil");
  }

  @FXML
  public void sizhePopUp(MouseEvent event) {
    PopUp.aboutPopUp(sizheBio, ((Node) event.getSource()).getScene().getWindow(), "Sizhe");
  }
}
