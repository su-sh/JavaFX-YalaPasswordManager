/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import model.User;
import utils.DBConnection;

/**
 *
 * @author sushant
 */
public class UserDao {

    public void insertPassword(User u) {
        String qry = "insert into u_table (m_id,account,username,email,password) values (?,?,?,?,?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setInt(1, u.getM_id());
            pst.setString(2, u.getAccount());
            pst.setString(3, u.getUsername());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getPassword());

            System.out.println("executeQEY");

            pst.executeUpdate();

            con.close();

        } catch (Exception e) {
        }
    }

    public void updateUserInfo(User u) {
        String qry = "update u_table set account=?,username=?,email=?,password=? where id=?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, u.getAccount());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getPassword());
            pst.setInt(5, u.getId());

            int i = pst.executeUpdate();
            if (i == 1) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("");
                a.setContentText("User info updated!");
                a.setHeaderText("");
                a.showAndWait();
            }
            pst.close();
            con.close();
        } catch (Exception e) {
        }

    }

    public void deleteUserInfo(User u) {
        String qry = "delete from u_table where id=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);

            pst.setInt(1, u.getId());

            int i = pst.executeUpdate();
            if (i == 1) {

            }
            pst.close();
            con.close();
        } catch (Exception e) {
        }
    }

}
