/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.MasterUser;
import utils.DBConnection;

/**
 *
 * @author sushant
 */
public class MasterUserDao {

    public int getId(String masterUsername) {
        String qry = "select m_id from m_table where m_username = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, masterUsername);
            ResultSet rs = pst.executeQuery();
            int r = rs.getInt("m_id");
            pst.close();
            con.close();
            return r;

        } catch (Exception e) {
        }

        return 0;
    }

    /*   public List uniqueUser(String m) {

        String qry = "select masterUsername from master_table";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            ResultSet rs = pst.executeQuery();

            List<String> masterUserList = new ArrayList<>();

            while (rs.next()) {
                String username = rs.getString("masterUsername");
//                System.out.println(username);
                masterUserList.add(username);
            }

            pst.close();
            return masterUserList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
     */
    public void signup(MasterUser m) {
        String qry = "insert into m_table (m_username,m_password) values (?,?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, m.getMasterUsername());
            pst.setString(2, m.getMasterPassword());

            pst.executeUpdate();
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<MasterUser> getUserList() {
        String qry = "select * from m_table";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            ResultSet rs = pst.executeQuery();
            List<MasterUser> mList = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString("m_username");
                String password = rs.getString("m_password");
                MasterUser m = new MasterUser();
                m.setMasterUsername(username);
                m.setMasterPassword(password);
                mList.add(m);
            }
            System.out.println("Return_mList");
            pst.close();
            con.close();
            return mList;
        } catch (Exception e) {
        }
        return null;
    }

    public boolean verify(MasterUser m) {
        String qry = "select * from m_table where m_username=? and m_password=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, m.getMasterUsername());
            pst.setString(2, m.getMasterPassword());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("TRUEEE");
                pst.close();
                con.close();
                return true;
            } else {
                pst.close();
                con.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
