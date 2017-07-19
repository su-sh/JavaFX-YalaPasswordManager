/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalapasswordmanager_1.pkg0;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.MasterUserDao;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MasterUser;
import sun.security.krb5.internal.crypto.Aes128;
import utils.EncryptUtils;

/**
 *
 * @author sushant
 */
public class FXMLDocumentController implements Initializable {

    private String masterUsername;
    private String masterPassword;
    private String masterPassword1;

    private EncryptUtils eu = new EncryptUtils();
    @FXML
    private JFXTextField username_txt;
    private JFXTextField mpassword_txt;
    private JFXTextField mpassword1_txt;
    @FXML
    private JFXButton login_btn;
    @FXML
    private JFXButton signup_btn;

    @FXML
    private JFXPasswordField mpassword0_pf;
    @FXML
    private JFXPasswordField mpassword1_pf;
    @FXML
    private Text text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        FadeTransition fadein = new FadeTransition(Duration.seconds(5), text);
        fadein.setFromValue(0.0);
        fadein.setToValue(1.0);
        fadein.play();
    }

    @FXML
    private void login_action(ActionEvent event) {
        initUi();
        MasterUser m = new MasterUser();
        String mun = eu.enc(masterUsername, "sir alex");
        m.setMasterUsername(mun);
        String mp = eu.enc(masterPassword, masterUsername);
        m.setMasterPassword(mp);

        boolean un = false, pw = false;
        if (masterPassword.equals(masterPassword1)) {
            un = true;
        }
        if (!masterUsername.isEmpty()) {
            pw = true;
        }

        MasterUserDao md = new MasterUserDao();

        if (md.verify(m)) {
            try {

                int u_id;
                String u_uname = username_txt.getText();
                u_id = md.getId(m.getMasterUsername());
                System.out.println("UIDD" + u_id);
                m.setId(u_id);

                //sucessful login
                AlertBOX("login");
                
                System.out.println(m.getId());
                System.out.println(m.getMasterUsername());
                System.out.println("");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Home.fxml").openStream());
                HomeController homeController = (HomeController) loader.getController();
                homeController.setUserObject(m);
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
//            test_label.setText("LOGIN unSUCESS");
            AlertBOX("loginerr");
        }

    }

    @FXML
    private void signup_action(ActionEvent event) {
        initUi();
        MasterUserDao md = new MasterUserDao();

        //Check for unique username
        boolean un = false, pw = false;
        boolean unique = true;
        if (masterPassword.equals(masterPassword1)) {
            un = true;
        }
        if (!masterUsername.isEmpty()) {
            pw = true;
        }
        if (un && pw) {
            MasterUser m = new MasterUser();
            String mun = eu.enc(masterUsername, "sir alex");
            m.setMasterUsername(mun);
            String mp = eu.enc(masterPassword, masterUsername);
            m.setMasterPassword(mp);
            List<MasterUser> m_userlist = new ArrayList<MasterUser>();
            m_userlist = md.getUserList();
            for (MasterUser mu : m_userlist) {
                if (m.getMasterUsername().equals(mu.getMasterUsername())) {
                    unique = false;
                }
            }
            
            if (unique) {
                md.signup(m);
//                test_label.setText("SIGNUP SUCESS");
                AlertBOX("signup");
            } else {
                AlertBOX("signuperr");
            }
            
        } else {
            alert("Password donot match");
//            test_label.setText("PasswordDonotMatch");
        }

    }

    private void initUi() {
        masterUsername = username_txt.getText();
        masterPassword = mpassword0_pf.getText();
        masterPassword1 = mpassword1_pf.getText();
    }

    public void alert(String msg) {

    }

    @FXML
    private void about(MouseEvent event) {

        AlertBOX("about");

    }

    void AlertBOX(String s) {
        if (s.equals("login")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("");
            a.setContentText("Login Sucessful");
            a.setHeaderText("");
            a.showAndWait();

        } else if (s.equals("about")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Yala Password Manager");
            alert.setContentText("Developed by Sushant Shrestha. ");

            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 320);
            Label label = new Label("About Yala Password Manager");

            TextArea textArea = new TextArea("Yala password manager stores and retrives passwords from encrypted database.\n\nUsing strong password and using different password for different sites is a must. It can be difficult to rembember different passwords stored for different sites. With Yala password manager user can create and store passwords securly in encripted database. ");
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        } else if (s.equals("signup")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("");
            a.setContentText("Signup Sucessful");
            a.setHeaderText("");
            a.showAndWait();

        } else if (s.equals("loginerr")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("");
            a.setContentText("Login Error");
            a.setHeaderText("Please retry with correct username and password.");
            a.showAndWait();
        } else if (s.equals("signuperr")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("");
            a.setContentText("Username taken. Please choose another username.");
            a.setHeaderText("");
            a.showAndWait();
        }
    }
}
