/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 *
 * @author freddie
 */
public class globalParam {
    globalParam()
    {
//                try {
//            Handler handler = new FileHandler("test.log", 10000, 2);
//            Logger.getLogger("").addHandler(handler);
//        } catch (IOException | SecurityException e) {
//            System.out.println(e);
//        }
    }
    public final static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static boolean bglbLogin = false;
    public static String glbstrServerAddress = "127.0.0.1";
    public static String glbstrServerPort = "2015";
    public static long glblDay = 24 * 3600 * 1000;
    public static String glbCurUser = "admin";
    public static Staff glbcuffStaff;
     //  private final static Logger LOGGER
    //        = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
}
