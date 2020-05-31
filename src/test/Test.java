/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import sqlLite.*;
/**
 *
 * @author PawelX
 */
public class Test {
    public static void main (String[] args)
    {
    Connect dbconn = new Connect();
    System.out.println(dbconn.contains("url"));
    dbconn.add("adres");
   }
    
}
