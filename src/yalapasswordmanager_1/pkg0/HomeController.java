/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalapasswordmanager_1.pkg0;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.behavior.OptionalBoolean;
import dao.UserDao;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.MasterUser;
import model.User;
import utils.DBConnection;
import utils.EncryptUtils;
import utils.Generator;
import utils.Validator;

/**
 * FXML Controller class
 *
 * @author sushant
 */
public class HomeController implements Initializable {

    @FXML
    private JFXTextField username_txt;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXTextField password_txt;
    @FXML
    private ChoiceBox<String> choiceBoxAccount;
    @FXML
    private JFXButton add;

    private MasterUser master = new MasterUser();
    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<?, ?> col_account;
    @FXML
    private TableColumn<?, ?> col_username;
    @FXML
    private TableColumn<?, ?> col_email;
    @FXML
    private TableColumn<?, ?> col_password;

    private ObservableList<User> data;
    @FXML
    private JFXButton update;
    private int userIdForEditDelete;
    @FXML
    private JFXButton delete;

    private EncryptUtils eu = new EncryptUtils();
    @FXML
    private Text title;
    @FXML
    private JFXButton generate;
    @FXML
    private JFXTextField generatedPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        choiceBoxAccount.getItems().addAll("Google", "Facebook", "Twitter", "LinkedIn", "Instagram", "Reddit", "Tumblr", "Flickr", "Live/Hotmail", "Yahoo", "Others");
        choiceBoxAccount.setValue("Google");
        setTable();
        setCellValueFromTableToTextField();
    }

    public void setUserObject(MasterUser m) {
        // TODO
        master = m;
        System.out.println(m.getId());
        System.out.println(m.getMasterPassword());
        System.out.println(m.getMasterUsername());
        loadTable(master);

    }

    private void setTable() {

        col_account.setCellValueFactory(new PropertyValueFactory<>("account"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void loadTable(MasterUser m) {
        try {
            data = FXCollections.observableArrayList();
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from u_table where m_id =?");
            pst.setInt(1, m.getId());
            ResultSet rs = pst.executeQuery();
            System.out.println("LoadExecuted");
            while (rs.next()) {
                data.add(new User(rs.getInt("id"), rs.getString("account"), eu.dec(rs.getString("username"), master.getMasterUsername()), eu.dec(rs.getString("email"), master.getMasterUsername()), eu.dec(rs.getString("password"), master.getMasterUsername())));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableUser.setItems(data);
    }

    private void setCellValueFromTableToTextField() {
        tableUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User u1 = tableUser.getItems().get(tableUser.getSelectionModel().getSelectedIndex());
                username_txt.setText(u1.getUsername());
                email_txt.setText(u1.getEmail());
                password_txt.setText(u1.getPassword());
                choiceBoxAccount.setValue(u1.getAccount());
                userIdForEditDelete = u1.getId();
                System.out.println("USER ID for editDelete : " + userIdForEditDelete);
            }
        });
    }

    @FXML
    private void add(ActionEvent event) {

        if (!(username_txt.getText().isEmpty() && password_txt.getText().isEmpty())) {

            if (!email_txt.getText().isEmpty()) {

                String email = email_txt.getText();
                Validator v = new Validator();
                if (v.email(email)) {
                    UserDao ud = new UserDao();
                    User u = new User();
                    u.setM_id(master.getId());
                    u.setAccount(choiceBoxAccount.getValue());
                    u.setUsername(eu.enc(username_txt.getText(), master.getMasterUsername()));
                    u.setEmail(eu.enc(email_txt.getText(), master.getMasterUsername()));
                    String up = eu.enc(password_txt.getText(), master.getMasterUsername());
                    u.setPassword(up);

                    ud.insertPassword(u);
                    loadTable(master);
                } else {
                    //emailWrongFormat

                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("");
                    a.setContentText("Wrong email format.");
                    a.setHeaderText("");
                    a.showAndWait();
                }

            } else {
                UserDao ud = new UserDao();
                User u = new User();
                u.setM_id(master.getId());
                u.setAccount(choiceBoxAccount.getValue());
                u.setUsername(eu.enc(username_txt.getText(), master.getMasterUsername()));
                u.setEmail(eu.enc(email_txt.getText(), master.getMasterUsername()));
                String up = eu.enc(password_txt.getText(), master.getMasterUsername());
                u.setPassword(up);

                ud.insertPassword(u);
                loadTable(master);

            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Username and password cannot be empty");
            a.setContentText("Please fill username and password.");
            a.setHeaderText("");
            a.showAndWait();
        }/*
        UserDao ud = new UserDao();
        User u = new User();
        u.setM_id(master.getId());
        u.setAccount(choiceBoxAccount.getValue());
        u.setUsername(eu.enc(username_txt.getText(), master.getMasterUsername()));
        u.setEmail(eu.enc(email_txt.getText(), master.getMasterUsername()));
        String up = eu.enc(password_txt.getText(), master.getMasterUsername());
        u.setPassword(up);

        ud.insertPassword(u);
        loadTable(master);
         */
    }

    @FXML
    private void handleUpdate(ActionEvent event) {

        User u = new User();
        u.setAccount(choiceBoxAccount.getValue());
        u.setUsername(eu.enc(username_txt.getText(), master.getMasterUsername()));
        u.setEmail(eu.enc(email_txt.getText(), master.getMasterUsername()));
        String up = eu.enc(password_txt.getText(), master.getMasterUsername());
        u.setPassword(up);
        u.setId(userIdForEditDelete);

        UserDao ud = new UserDao();
        ud.updateUserInfo(u);

        loadTable(master);
        clearTextFields();

    }

    public void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("");
        a.setContentText(msg);
        a.setHeaderText("");
        a.showAndWait();
    }

    public void clearTextFields() {
        username_txt.setText("");
        email_txt.setText("");
        password_txt.setText("");
        choiceBoxAccount.setValue("");
    }

    @FXML
    private void handleDelete(ActionEvent event) {

        User u = new User();
        u.setAccount(choiceBoxAccount.getValue());
        u.setUsername(username_txt.getText());
        u.setEmail(email_txt.getText());
        u.setPassword(password_txt.getText());
        u.setId(userIdForEditDelete);

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setHeaderText("Do you want to delete user data?");
        a.setContentText("");

        Optional<ButtonType> result = a.showAndWait();

        if (result.get() == ButtonType.OK) {
            UserDao ud = new UserDao();
            System.out.println("yesPressed");
            ud.deleteUserInfo(u);
            loadTable(master);
            clearTextFields();
        } else {
            loadTable(master);
            clearTextFields();
        }

    }

    @FXML
    private void titleInfo(MouseEvent event) {

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

    }

    @FXML
    private void generatePassword(ActionEvent event) {
        String generated;
        Generator g = new Generator();
        generated = Generator.generateSessionKey(10);
        generatedPassword.setText(generated);
        generatedPassword.setVisible(true);
        FadeTransition fadein = new FadeTransition(Duration.seconds(1), generatedPassword);
        fadein.setFromValue(0.0);
        fadein.setToValue(1.0);
        fadein.setOnFinished(e -> fadeout());
        fadein.play();

    }

    private void fadeout() {
        FadeTransition fadeout = new FadeTransition(Duration.seconds(8), generatedPassword);
        fadeout.setFromValue(1.0);
        fadeout.setToValue(0.0);
        fadeout.play();
    }
}
