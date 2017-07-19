/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author sushant
 */
public class DBConnection {

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:YalaDB.sqlite");

            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
