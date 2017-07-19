/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testClass;

import java.util.Scanner;
import utils.EncryptUtils;

/**
 *
 * @author sushant
 */
public class MainTest {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        EncryptUtils eu = new EncryptUtils();
        while (true) {
            System.out.println("PasswordFromDB:  ");
            String password = reader.nextLine();
            System.out.println("Key:  ");
            String key = reader.nextLine();
            System.out.println("UserVisiblePassword: " + eu.dec(password, key));
            System.out.println("");
        }
    }
}
