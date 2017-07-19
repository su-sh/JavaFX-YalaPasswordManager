/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author sushant
 */
public class Validator {

    public boolean email(String check) {
        String email_pat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(email_pat);
        Matcher matcher = pattern.matcher(check);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean username(String check) {
        String username_pat = "[a-zA-Z1-9]{3,12}";

        Pattern pattern = Pattern.compile(username_pat);
        Matcher matcher = pattern.matcher(check);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

}
