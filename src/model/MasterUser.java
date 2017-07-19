/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author sushant
 */
public class MasterUser {
    private int id;
    private String masterUsername;
    private String masterPassword;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the masterUsername
     */
    public String getMasterUsername() {
        return masterUsername;
    }

    /**
     * @param masterUsername the masterUsername to set
     */
    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    /**
     * @return the masterPassword
     */
    public String getMasterPassword() {
        return masterPassword;
    }

    /**
     * @param masterPassword the masterPassword to set
     */
    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }
}
