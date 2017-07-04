/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import mdsystem.MonitorVariable;
import mdsystem.Monitor;
import javafx.collections.ObservableList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import static mdsystem.globalParam.glbCurUser;
import static mdsystem.globalParam.glbcuffStaff;
import javafx.collections.ObservableList;
import mdsystem.MDSystem;


/**
 *
 * @author freddie
 */


//import org.apache.derby.*;


/**
 * <p>
 * This sample program is a minimal Java application showing JDBC access to a
 * Derby database.</p>
 * <p>
 * Instructions for how to run this program are
 * given in <A HREF=example.html>example.html</A>, by default located in the
 * same directory as this source file ($DERBY_HOME/demo/programs/simple/).</p>
 * <p>
 * Derby applications can run against Derby running in an embedded
 * or a client/server framework.</p>
 * <p>
 * When Derby runs in an embedded framework, the JDBC application and Derby
 * run in the same Java Virtual Machine (JVM). The application
 * starts up the Derby engine.</p>
 * <p>
 * When Derby runs in a client/server framework, the application runs in a
 * different JVM from Derby. The connectivity framework (in this case the Derby
 * Network Server) provides network connections. The client driver is loaded
 * automatically.</p>
 */
public class DBAdmin
{
    /* the default framework is embedded */
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    public DBAdmin(MDSystem md)
    {
        gui = md;
    }
    MDSystem gui;

    /**
     * <p>
     * Starts the demo by creating a new instance of this class and running
     * the <code>go()</code> method.</p>
     * <p>
     * When you run this application, you may give one of the following
     * arguments:
     *  <ul>
          <li><code>embedded</code> - default, if none specified. Will use
     *        Derby's embedded driver. This driver is included in the derby.jar
     *        file.</li>
     *    <li><code>derbyclient</code> - will use the Derby client driver to
     *        access the Derby Network Server. This driver is included in the
     *        derbyclient.jar file.</li>
     *  </ul>
     * <p>
     * When you are using a client/server framework, the network server must
     * already be running when trying to obtain client connections to Derby.
     * This demo program will will try to connect to a network server on this
     * host (the localhost), see the <code>protocol</code> instance variable.
     * </p>
     * <p>
     * When running this demo, you must include the correct driver in the
     * classpath of the JVM. See <a href="example.html">example.html</a> for
     * details.
     * </p>
     * @param args This program accepts one optional argument specifying which
     *        connection framework (JDBC driver) to use (see above). The default
     *        is to use the embedded JDBC driver.
     */
    
   // public static void main(String[] args)
    //{
    //    new DBAdmin().go(args);
    //    System.out.println("SimpleApp finished");
    //}

    /**
     * <p>
     * Starts the actual demo activities. This includes creating a database by
     * making a connection to Derby (automatically loading the driver),
     * creating a table in the database, and inserting, updating and retrieving
     * some data. Some of the retrieved data is then verified (compared) against
     * the expected results. Finally, the table is deleted and, if the embedded
     * framework is used, the database is shut down.</p>
     * <p>
     * Generally, when using a client/server framework, other clients may be
     * (or want to be) connected to the database, so you should be careful about
     * doing shutdown unless you know that no one else needs to access the
     * database until it is rebooted. That is why this demo will not shut down
     * the database unless it is running Derby embedded.</p>
     *
     * @param args - Optional argument specifying which framework or JDBC driver
     *        to use to connect to Derby. Default is the embedded framework,
     *        see the <code>main()</code> method for details.
     * @see #main(String[])
     */
               public ObservableList<String> listtable;
    void setSelectList(ObservableList<String> list)
    {
        listtable = list;
    }
    void go()
    {
        /* parse the arguments to determine which framework is desired*/
        //parseArguments(args);

        System.out.println("SimpleApp starting in " + framework + " mode");

        /* We will be using Statement and PreparedStatement objects for
         * executing SQL. These objects, as well as Connections and ResultSets,
         * are resources that should be released explicitly after use, hence
         * the try-catch-finally pattern used below.
         * We are storing the Statement and Prepared statement object references
         * in an array list for convenience.
         */
        Connection conn = null;
        ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
        PreparedStatement psInsert;
        PreparedStatement psInsert_1;
        PreparedStatement psUpdate;
        PreparedStatement psDelete;
        Statement s;
        ResultSet rs = null;
 
        try
        {
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");
            
            

            /* By default, the schema APP will be used when no username is
             * provided.
             * Otherwise, the schema name is the same as the user name (in this
             * case "user1" or USER1.)
             *
             * Note that user authentication is off by default, meaning that any
             * user can connect to your database using any password. To enable
             * authentication, see the Derby Developer's Guide.
             */

            String dbName = "monitorDB"; // the name of the database

            /*
             * This connection specifies create=true in the connection URL to
             * cause the database to be created when connecting for the first
             * time. To remove the database, remove the directory derbyDB (the
             * same as the database name) and its contents.
             *
             * The directory derbyDB will be created under the directory that
             * the system property derby.system.home points to, or the current
             * directory (user.dir) if derby.system.home is not set.
             */
            //DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);

            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);

            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);

            // We create a table...
            s.execute("create table a001(time Timestamp, value double)");
            s.execute("create table location(num int, addr varchar(40))");
            System.out.println("Created table location");

            // and add a few rows...

            /* It is recommended to use PreparedStatements when you are
             * repeating execution of an SQL statement. PreparedStatements also
             * allows you to parameterize variables. By using PreparedStatements
             * you may increase performance (because the Derby engine does not
             * have to recompile the SQL statement each time it is executed) and
             * improve security (because of Java type checking).
             */
            // parameter 1 is num (int), parameter 2 is addr (varchar)
            psInsert = conn.prepareStatement(
                        "insert into location values (?, ?)");
            statements.add(psInsert);

            psInsert.setInt(1, 1956);
            psInsert.setString(2, "Webster St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1956 Webster");

            psInsert.setInt(1, 1910);
            psInsert.setString(2, "Union St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1910 Union");
            
            Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
            double value = 1.29;
            psInsert_1 = conn.prepareStatement(
                        "insert into a001 values (?, ?)");
            statements.add(psInsert_1);
            psInsert_1.setTimestamp(1, time); //  .setInt(time, value);
            psInsert_1.setDouble(2, value); //.setString(2, "Webster St.");
            psInsert_1.executeUpdate();
            System.out.printf("Inserted %s %f\n",time.toString(),value);
            
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(DBAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
            time.setTime(Calendar.getInstance().getTime().getTime());
            value = 1.98;
            psInsert_1.setTimestamp(1, time); //  .setInt(time, value);
            psInsert_1.setDouble(2, value); //.setString(2, "Webster St.");
            psInsert_1.executeUpdate();
            System.out.printf("Inserted %s %f\n",time.toString(),value);
            
            
            

            // Let's update some rows as well...

            // parameter 1 and 3 are num (int), parameter 2 is addr (varchar)
            psUpdate = conn.prepareStatement(
                        "update location set num=?, addr=? where num=?");
            statements.add(psUpdate);

            psUpdate.setInt(1, 180);
            psUpdate.setString(2, "Grand Ave.");
            psUpdate.setInt(3, 1956);
            psUpdate.executeUpdate();
            System.out.println("Updated 1956 Webster to 180 Grand");

            psUpdate.setInt(1, 300);
            psUpdate.setString(2, "Lakeshore Ave.");
            psUpdate.setInt(3, 180);
            psUpdate.executeUpdate();
            System.out.println("Updated 180 Grand to 300 Lakeshore");


            /*
               We select the rows and verify the results.
             */
            rs = s.executeQuery(
                    "SELECT num, addr FROM location ORDER BY num");

            /* we expect the first returned column to be an integer (num),
             * and second to be a String (addr). Rows are sorted by street
             * number (num).
             *
             * Normally, it is best to use a pattern of
             *  while(rs.next()) {
             *    // do something with the result set
             *  }
             * to process all returned rows, but we are only expecting two rows
             * this time, and want the verification code to be easy to
             * comprehend, so we use a different pattern.
             */

            int number; // street number retrieved from the database
            boolean failure = false;
            if (!rs.next())
            {
                failure = true;
                reportFailure("No rows in ResultSet");
            }

            if ((number = rs.getInt(1)) != 300)
            {
                failure = true;
                reportFailure(
                        "Wrong row returned, expected num=300, got " + number);
            }

            if (!rs.next())
            {
                failure = true;
                reportFailure("Too few rows");
            }

            if ((number = rs.getInt(1)) != 1910)
            {
                failure = true;
                reportFailure(
                        "Wrong row returned, expected num=1910, got " + number);
            }

            if (rs.next())
            {
                failure = true;
                reportFailure("Too many rows");
            }

            if (!failure) {
                System.out.println("Verified the rows");
            }

            // delete the table
            s.execute("drop table location");
            System.out.println("Dropped table location");
            
            
            rs = s.executeQuery(
                    "SELECT time, value FROM a001");
            
            while(rs.next())
            {
                System.out.println(rs.getTime(1).toString());
                System.out.println(rs.getDouble(2));
            }
            
            s.execute("drop table a001");
            System.out.println("Dropped table a001");

            /*
               We commit the transaction. Any changes will be persisted to
               the database now.
             */
            conn.commit();
            System.out.println("Committed the transaction");

            /*
             * In embedded mode, an application should shut down the database.
             * If the application fails to shut down the database,
             * Derby will not perform a checkpoint when the JVM shuts down.
             * This means that it will take longer to boot (connect to) the
             * database the next time, because Derby needs to perform a recovery
             * operation.
             *
             * It is also possible to shut down the Derby system/engine, which
             * automatically shuts down all booted databases.
             *
             * Explicitly shutting down the database or the Derby engine with
             * the connection URL is preferred. This style of shutdown will
             * always throw an SQLException.
             *
             * Not shutting down when in a client environment, see method
             * Javadoc.
             */

            if (framework.equals("embedded"))
            {
                try
                {
                    // the shutdown=true attribute shuts down Derby
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");

                    // To shut down a specific database only, but keep the
                    // engine running (for example for connecting to other
                    // databases), specify a database in the connection URL:
                    //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
                }
                catch (SQLException se)
                {
                    if (( (se.getErrorCode() == 50000)
                            && ("XJ015".equals(se.getSQLState()) ))) {
                        // we got the expected exception
                        System.out.println("Derby shut down normally");
                        // Note that for single database shutdown, the expected
                        // SQL state is "08006", and the error code is 45000.
                    } else {
                        // if the error code or SQLState is different, we have
                        // an unexpected exception (shutdown failed)
                        System.err.println("Derby did not shut down normally");
                        printSQLException(se);
                    }
                }
            }
        }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement)statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }

            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
    }
    
    public void creatDB()
    {
        
    }
    
    Connection conn = null;
    ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
    PreparedStatement psInsert;
    PreparedStatement psInsert_1;
    PreparedStatement psUpdate;
    Statement s;
    ResultSet rs = null;
    
    
    
    public boolean addUser(Staff staff)
    {
        try {
            String sqlInsert = "insert into " + "userInfo" + " values (?, ?,?,?,?,?)";
            psInsert_1 = conn.prepareStatement(sqlInsert);
            statements.add(psInsert_1);
            psInsert_1.setString(1, staff.getStrName());  //  .setTimestamp(1, time); //  .setInt(time, value);
            psInsert_1.setString(2, staff.getStrPassword()); //.setString(2, "Webster St.");
            psInsert_1.setString(3, staff.getStrStaffID()); //.setString(2, "Webster St.");
            psInsert_1.setString(4, staff.getStrFirstName()); //.setString(2, "Webster St.");
            psInsert_1.setString(5, staff.getStrLastName()); //.setString(2, "Webster St.");
            psInsert_1.setString(6, staff.getStrEmail()); //.setString(2, "Webster St.");
            psInsert_1.executeUpdate();
            System.out.println("Add  user into database.");
            return true;
        } catch (SQLException sqle) {
            printSQLException(sqle);
            return false;
        }
    }
    
    

    public boolean deleteUser(Staff staff) {
        try {
            String sqlInsert = String.format("DELETE FROM userInfo WHERE username='%s'", staff.getStrName());
            s.execute(sqlInsert);
            return true;
        } catch (SQLException sqle) {
            printSQLException(sqle);
            return false;
        }
    }
            
        
        public boolean updateUser(Staff staff)
    {
        try {
            
                  
            
            // parameter 1 and 3 are num (int), parameter 2 is addr (varchar)
            psUpdate = conn.prepareStatement(
                        "update userInfo set username=?, password=?,userID=? ,userfirstname=? ,userlastname=? ,useremail=? where username=?");
            statements.add(psUpdate);
            
            System.out.println(staff.getStrName());
             System.out.println(staff.getStrPassword());
              System.out.println(staff.getStrStaffID());
               System.out.println(staff.getStrFirstName());
                System.out.println(staff.getStrLastName());
                 System.out.println(staff.getStrEmail());
                  System.out.println(staff.getStrName());

            psUpdate.setString(1, staff.getStrName());
            psUpdate.setString(2, staff.getStrPassword());
            psUpdate.setString(3, staff.getStrStaffID());
            psUpdate.setString(4, staff.getStrFirstName());
            psUpdate.setString(5, staff.getStrLastName());
            psUpdate.setString(6, staff.getStrEmail());
            psUpdate.setString(7, staff.getStrName());



            psUpdate.executeUpdate();
            System.out.println("Updated  Webster to 180 Grand");
            
            

            return true;
        } catch (SQLException sqle) {
            printSQLException(sqle);
            return false;
        }
    }
    //-1  user is not exsit
    //0 passowrd is not correct
    //1 sucessfull
    public int loginCheck(String userName,String userPassword)
    {
         try
            {
                
                
                String sqlstr = "SELECT username,password,userID,userfirstname,userlastname,useremail FROM userInfo WHERE username = '" + userName +"'";
                rs = s.executeQuery(sqlstr);
                if (!rs.next())
                {
                    
                    System.out.println("User is not exsit.");
                    return -1;
                }
                else
                {    
                    if (userPassword.equals(rs.getString(2)))
                    {
                        glbcuffStaff = new Staff(rs.getString(1),     //login id
                rs.getString(2),           //password
                rs.getString(3),           //staffID
                rs.getString(4),           //user first name
                rs.getString(5),           //user last  name
                rs.getString(6));        //user email
                        System.out.println("Successful lonining");
                        return 1;
                    }
                    else
                    {
                        System.out.println("password is incorrect.");
                        return 0;
                    }                    
                }
            }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
            return 0;
        } 
    }
    public void log(String strlog)
    {
        gui.log(strlog);
    }
    public void addTable(ObservableList<MonitorVariable> listMon) {
        try {
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");
            String dbName = "monitorDB";
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);
            System.out.println("Connected to and created database " + dbName);
            log("Connected to Database monitorDB");
            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.

            conn.setAutoCommit(true);
            s = conn.createStatement();
            statements.add(s);
        } catch (SQLException sqle) {
            log("Fail to cnnected to database monitorDB");
            printSQLException(sqle);
        }
        // Create to a table to storage the user name and password for logging in;
        String sqlUserTable;
//        try{
//            
//        
//               //     s.execute("drop table userInfo");
//            System.out.println("Dropped table a001");
//        } catch (SQLException sqle) {
//            printSQLException(sqle);
//        }
                
         

        //sqlUserTable = "create table " + "userInfo" + "(username varchar(16), password varchar(6))";
        sqlUserTable = "create table userInfo (username varchar(16),"
                + "password varchar(6),"
                + "userID varchar(6),"
                + "userfirstname varchar(16),"
                + "userlastname varchar(16),"
                + "useremail varchar(50))";
        try {
            s.execute(sqlUserTable);
            System.out.println(sqlUserTable);
            log("Create table userTnfo.");
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }
        
        //If admin user dose not exsited, insert a "admin, admin" as the first item in userInfo 
         try
            {
                String sqlstr = "SELECT username, password FROM userInfo WHERE username = 'admin'";
                rs = s.executeQuery(sqlstr);
                if (!rs.next())
                {
                    try {
                        String strName = "admin";
                        String strPassword = "admin";
                        String sqlInsert = "insert into " + "userInfo" + " values (?, ?,?,?,?,?)";
                        psInsert_1 = conn.prepareStatement(sqlInsert);
                        statements.add(psInsert_1);
                        psInsert_1.setString(1, strName);  //  .setTimestamp(1, time); //  .setInt(time, value);
                        psInsert_1.setString(2, strPassword); //.setString(2, "Webster St.");
                        psInsert_1.setString(3, "00001"); //.setString(2, "Webster St.");
                        psInsert_1.setString(4, ""); //.setString(2, "Webster St.");
                        psInsert_1.setString(5, ""); //.setString(2, "Webster St.");
                        psInsert_1.setString(6, ""); //.setString(2, "Webster St.");
                        psInsert_1.executeUpdate();
                        System.out.println("Add admin user into database.");
                        log("Add user: admin into db.");
                    } catch (SQLException sqle) {
                        printSQLException(sqle);
                    }
                }
                else
                {    
                    System.out.printf("Admin: %s Password: %s\n",rs.getString(1)  ,rs.getString(2));
                   //System.out.println(rs.getDouble(2));
                }
            }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        } 
        
        
                    
        

        
        
         //create tables for each monitor data type
        for (int i = 0; i < listMon.size(); i++) {
            MonitorVariable mon = listMon.get(i);
            String sql;
            if (mon.getColMax().equals("null") && mon.getColMin().equals("null")) {
                sql = "create table " + mon.getColID() + "(time Timestamp, value int)";

            } else {
                sql = "create table " + mon.getColID() + "(time Timestamp, value double)";
            }
            try {
                s.execute(sql);
                System.out.println(sql);
                log("Create table" + mon.getColID());
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
        
        
        try {
            conn.commit();
            System.out.println("Committed the transaction");
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }

    }

        //finally {
//            // release all open resources to avoid unnecessary memory usage
//
//            // ResultSet
//            try {
//                if (rs != null) {
//                    rs.close();
//                    rs = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//
//            // Statements and PreparedStatements
//            int i = 0;
//            while (!statements.isEmpty()) {
//                // PreparedStatement extend Statement
//                Statement st = (Statement)statements.remove(i);
//                try {
//                    if (st != null) {
//                        st.close();
//                        st = null;
//                    }
//                } catch (SQLException sqle) {
//                    printSQLException(sqle);
//                }
//            }

            //Connection
//            try {
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//        }
        
        
    
    
    public void addData(String tableName, double value)
    {
        String sqlstr = "insert into " + tableName + " values (?, ?)";
        Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
//            psInsert_1 = conn.prepareStatement(
//                        "insert into a001 values (?, ?)");
    try{
        psInsert_1 = conn.prepareStatement(sqlstr);
        statements.add(psInsert_1);
        psInsert_1.setTimestamp(1, time); //  .setInt(time, value);
        psInsert_1.setDouble(2, value); //.setString(2, "Webster St.");
        psInsert_1.executeUpdate();
    }
    catch (SQLException sqle)
        {
            printSQLException(sqle);
        }
//    finally {
//            // release all open resources to avoid unnecessary memory usage
//
//            // ResultSet
//            try {
//                if (rs != null) {
//                    rs.close();
//                    rs = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//
//            // Statements and PreparedStatements
//            int i = 0;
//            while (!statements.isEmpty()) {
//                // PreparedStatement extend Statement
//                Statement st = (Statement)statements.remove(i);
//                try {
//                    if (st != null) {
//                        st.close();
//                        st = null;
//                    }
//                } catch (SQLException sqle) {
//                    printSQLException(sqle);
//                }
//            }
//
//            //Connection
//            try {
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//        }
    
    }
    

    public void addData(String tableName, int value)
    {
        String sqlstr = "insert into " + tableName + " values (?, ?)";
        Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
//            psInsert_1 = conn.prepareStatement(
//                        "insert into a001 values (?, ?)");
    try{
        psInsert_1 = conn.prepareStatement(sqlstr);
        statements.add(psInsert_1);
        psInsert_1.setTimestamp(1, time); //  .setInt(time, value);
        psInsert_1.setInt(2, value); //.setString(2, "Webster St.");
        psInsert_1.executeUpdate();
    }
    catch (SQLException sqle)
        {
            printSQLException(sqle);
        }
//finally {
//            // release all open resources to avoid unnecessary memory usage
//
//            // ResultSet
//            try {
//                if (rs != null) {
//                    rs.close();
//                    rs = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//
//            // Statements and PreparedStatements
//            int i = 0;
//            while (!statements.isEmpty()) {
//                // PreparedStatement extend Statement
//                Statement st = (Statement)statements.remove(i);
//                try {
//                    if (st != null) {
//                        st.close();
//                        st = null;
//                    }
//                } catch (SQLException sqle) {
//                    printSQLException(sqle);
//                }
//            }
//
//            //Connection
//            try {
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//        }   
    }  
    
            public void select(String tableName,ObservableList<String> listtable,Date start, Date end)
        {
            
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String startTime = formatter.format(start);
           String endTime = formatter.format(end);
           
           
//            yyyy-mm-dd hh:mm:ss.fffffffff
//            
//            
//                               String s;
//          SimpleDateFormat formatter;
//          Date date = new Date();
//           formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//           try{
//           Date date1 = formatter.parse("2010-06-20 19:37:13");
//                        s = formatter.format(date1);
//  System.out.println(s);
//           }
//           catch (Exception e)
//           {
//               System.out.println(e);
//           }
//            
//            YYYY-MM-DD HH:MI:SS
//                    
//                    From_date >= '2013-01-03' AND
//        To_date   <= '2013-01-09'
                    
                    
            try
            {
                String sqlstr = "SELECT time, value FROM " + tableName;

            rs = s.executeQuery(sqlstr);
            
            while(rs.next())
            {
            //    System.out.println(rs.getTime(1).toString());
            //    System.out.println(rs.getDouble(2));
                String st = rs.getTime(1).toString() + rs.getDouble(2);
                listtable.add(st);
            }   
            }
            catch (SQLException sqle)
        {
            printSQLException(sqle);
        } 
            }
            
            
//    public void select(String tableName, List<Monitor> monList, boolean bwarning, Date startTime, Date endTime) {
//
//        SimpleDateFormat formatter;
//        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        formatter.format(startTime);
//    }

        public void select(String tableName, List<Monitor> monList,boolean bwarning,String strStartTime, String strEndTime) 
        {
            
            
            SimpleDateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            String sqlstr;
            boolean bStart,bEnd;
//            try {
//                strStartTime = formatter.format(startTime);
//                bStart = true;
//            } catch (Exception e) {
//                System.out.println(e);
//                bStart = false;
//            }
//            try {
//                strEndTime = formatter.format(endTime);
//                bEnd = true;
//            } catch (Exception e) {
//                System.out.println(e);
//                bEnd = false;
//            }

            if (!strStartTime.equals("") && !strEndTime.equals("")) {
                sqlstr = String.format("SELECT time, value FROM %s WHERE time > '%s' AND time < '%s'",
                        tableName, strStartTime, strEndTime);
            } else if (strStartTime.equals("") && !strEndTime.equals("")) {
                sqlstr = String.format("SELECT time, value FROM %s WHERE time < '%s'",
                        tableName, strEndTime);
            } else if (!strStartTime.equals("") && strEndTime.equals(""))
            {
                sqlstr = String.format("SELECT time, value FROM %s WHERE time > '%s'",
                        tableName, strStartTime);
            }
            else
            {
                sqlstr = String.format("SELECT time, value FROM %s ",
                        tableName);
            }
   
            try
            {
               // String sqlstr = "SELECT time, value FROM " + tableName;// + " WHERE time > '2017-06-21 01:00:00' AND time < '2017-06-21 05:40:00'";

            rs = s.executeQuery(sqlstr);
            
            int IDNum = 0;
            
            while(rs.next())
            {
            //    System.out.println(rs.getTime(1).toString());
            //    System.out.println(rs.getDouble(2));
                IDNum++;
                 String stTime = rs.getTimestamp(1).toString().substring(0, 19);
                
                for (int i = 0 ; i < monList.size(); i++)
                {
                    Monitor mon = monList.get(i);
                    
                    if (!mon.getID().equals(tableName))
                        continue;
                    
                    int valueInt;
                    double valueDouble;
                    switch (mon.type) {
                        case YESNO:                            
                        case ENUM:
                            valueInt = rs.getInt(2);
                            if (bwarning)
                            {
                                //just display warnning data
                                if (!mon.varify(valueInt))
                                {
                                   listtable.add(String.format("[%06d-WARNING] %s # %d", IDNum,stTime,valueInt));
                                }                          
                            }
                            else
                            {
                               listtable.add(String.format("[%06d] %s # %d", IDNum,stTime,valueInt));
                            }
                            break;
                        case MIN:
                        case MAX:                        
                        case MINMAX:
                            valueDouble = rs.getDouble(2);
                            if (bwarning)
                            {
                                //just display warnning data
                                if (!mon.varify(valueDouble))
                                {
                                    listtable.add(String.format("[%06d-WARNING] %s # %f", IDNum,stTime,valueDouble));
                                   //listtable.add(String.format("[%05d] ", IDNum) + stTime + " # " + valueDouble);
                                }                          
                            }
                            else
                            {
                                listtable.add(String.format("[%06d] %s # %.6f", IDNum,stTime,valueDouble));
                               // listtable.add(String.format("[%06d] ", IDNum) +  stTime + " # " + valueDouble);
                            }
                            break;
                    }                   
                    
                }

            }   
            }
            catch (SQLException sqle)
        {
            printSQLException(sqle);
        } 
            }
//            finally {
//            // release all open resources to avoid unnecessary memory usage
//
//            // ResultSet
//            try {
//                if (rs != null) {
//                    rs.close();
//                    rs = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//
//            // Statements and PreparedStatements
//            int i = 0;
//            while (!statements.isEmpty()) {
//                // PreparedStatement extend Statement
//                Statement st = (Statement)statements.remove(i);
//                try {
//                    if (st != null) {
//                        st.close();
//                        st = null;
//                    }
//                } catch (SQLException sqle) {
//                    printSQLException(sqle);
//                }
//            }
//
//            //Connection
//            try {
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException sqle) {
//                printSQLException(sqle);
//            }
//        }   
            
        
        
        
    public void closeDB() {
        

if (conn == null) {
    System.out.println("Connection has been closed");
    log("db connection has been closed.");
    return;
}



            if (framework.equals("embedded")) {
                try {
                    // the shutdown=true attribute shuts down Derby
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");

                    // To shut down a specific database only, but keep the
                    // engine running (for example for connecting to other
                    // databases), specify a database in the connection URL:
                    //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
                } catch (SQLException se) {
                    if (((se.getErrorCode() == 50000)
                            && ("XJ015".equals(se.getSQLState())))) {
                        // we got the expected exception
                        System.out.println("Derby shut down normally");
                        // Note that for single database shutdown, the expected
                        // SQL state is "08006", and the error code is 45000.
                    } else {
                        // if the error code or SQLState is different, we have
                        // an unexpected exception (shutdown failed)
                        System.err.println("Derby did not shut down normally");
                        printSQLException(se);
                    }
                }
            }
        
      

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement) statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }

            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        
    }
    

    /**
     * Reports a data verification failure to System.err with the given message.
     *
     * @param message A message describing what failed.
     */
    private void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    /**
     * Parses the arguments given and sets the values of this class's instance
     * variables accordingly - that is, which framework to use, the name of the
     * JDBC driver class, and which connection protocol to use. The
     * protocol should be used as part of the JDBC URL when connecting to Derby.
     * <p>
     * If the argument is "embedded" or invalid, this method will not change
     * anything, meaning that the default values will be used.</p>
     * <p>
     * @param args JDBC connection framework, either "embedded" or "derbyclient".
     * Only the first argument will be considered, the rest will be ignored.
     */
    private void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }
    
    public void listUser(String userName,ObservableList<Staff> admList)
    {
        //if current user is admin, list all users
        //if not, just list his own information
        
       // ObservableList<Staff> admList = FXCollections.observableArrayList(); 
        
        
        if (userName.equals("admin"))
        {
            try
            {
                String sqlstr = "SELECT username,password,userID,userfirstname,userlastname,useremail FROM userInfo";
                rs = s.executeQuery(sqlstr);
                while(rs.next())
                {
                    admList.add(new Staff(rs.getString(1),     //login id
                    rs.getString(2),           //password
                    rs.getString(3),           //staffID
                    rs.getString(4),           //user first name
                    rs.getString(5),           //user last  name
                    rs.getString(6)));         //user email     
                }
            }
            catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
        else
        {
            try
            {
                String sqlstr = String.format("SELECT username,password,userID,userfirstname,userlastname,useremail FROM userInfo WHERE username='%s'",userName);
                rs = s.executeQuery(sqlstr);
                rs.next();
                admList.add(new Staff(rs.getString(1),     //login id
                rs.getString(2),           //password
                rs.getString(3),           //staffID
                rs.getString(4),           //user first name
                rs.getString(5),           //user last  name
                rs.getString(6)));         //user email

            }
            catch (SQLException sqle) {
                printSQLException(sqle);
            
            }
        }
    }
}

