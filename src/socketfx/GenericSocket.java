package socketfx;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import mdsystem.MDSystem;

public abstract class GenericSocket implements SocketListener {
    
    private final static Logger LOGGER =
            Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public MDSystem mainInterface;
    public int port;
    protected Socket socketConnection = null;
    private BufferedWriter output = null;
    
    private BufferedReader input = null;
    private boolean ready = false;
    private Thread socketReaderThread;
    private Thread setupThread;
    private int debugFlags;
    
    /**
     * Returns true if the specified debug flag is set.
     * @param flag Debug flag in question
     * @return true if the debug flag 'flag' is set.
     */
    public boolean debugFlagIsSet(int flag) {
        return ((flag & debugFlags) != 0);
    }
    
    public void SetGui(MDSystem gui)
    {
        mainInterface = gui;
    }
    
    public MDSystem GetGui()
    {
        if (mainInterface != null)
            return mainInterface;
        else
            return null;
    }
            
    /**
     * Turn on debugging option.
     * @param flags The debugging flags to enable
     */
    public void setDebugFlags(int flags) {
        debugFlags = flags;
    }
    
    /**
     * Get the current set of debug flags.
     * @return the current debug flag bitmask
     */
    public int getDebugFlags() {
        return debugFlags;
    }

    /**
     * Turn off debugging option.
     */
    public void clearDebugFlags() {
        debugFlags = Constants.instance().
                DEBUG_NONE;
    }

    /**
     * Set up a connection in the background.  This method returns no status,
     * however the onClosedStatus(boolean) method will be called when the
     * status of the socket changes, either opened or closed (for whatever
     * reason).
     */

    
    public boolean connect1() {
             try {
                initSocketConnection();
                if (socketConnection != null && !socketConnection.isClosed()) {
                    /*
                     * Get input and output streams
                     */
                    input = new BufferedReader(new InputStreamReader(
                            socketConnection.getInputStream()));
                    output = new BufferedWriter(new OutputStreamWriter(
                            socketConnection.getOutputStream()));
                    output.flush();
                }
                /*
                 * Notify SocketReaderThread that it can now start.
                 */
                //freddie notifyReady();
            } catch (IOException e) {
                if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                    LOGGER.info(e.getMessage());
                    
                }
                /*
                 * This will notify the SocketReaderThread that it should exit.
                 */
                //freddie notifyReady();
                return false;
            }
             return true;            
    }
    
 
    public void connect() {
        try {
            /*
             * Background thread to set up and open the input and
             * output data streams.
             */
            setupThread = new SetupThread();
            setupThread.start();
            /*
             * Background thread to continuously read from the input stream.
             */
            socketReaderThread = new SocketReaderThread();
            socketReaderThread.start();
        } catch (Exception e) {
            if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                LOGGER.info(e.getMessage());
            }
        }  
    }
    
        public void startReading() {
        try {
            socketReaderThread = new SocketReaderThread();
            socketReaderThread.start();
        } catch (Exception e) {
            if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                LOGGER.info(e.getMessage());
            }
        }  
    }
        

    /**
     * Shutdown and close GenericSocket instance in an orderly fashion.
     * As per the Java Socket API, once a Socket has been closed, it is not
     * available for further networking use (i.e. can't be reconnected
     * or rebound) A new Socket needs to be created.
     */
    public void shutdown() {
        close();
    }

    /**
     * Close down the GenericSocket infrastructure.  As per the Java Socket
     * API, once a Socket has been closed, it is not available for
     * further networking use (i.e. can't be reconnected or rebound).
     * A new Socket needs to be created.
     *
     * For certain implementations (e.g. ProviderSocket), the
     * closeAdditionalSockets() method may need to be more than just a
     * null method.
     */
    private void close() {
        try {
            if (socketConnection != null && !socketConnection.isClosed()) {
                socketConnection.close();
            }
            /*
             * closeAdditionalSockets() has to be implemented in a subclass.
             * In some cases nothing may be requied (null method), but for
             * others (e.g. SocketServer), the method can be used to
             * close additional sockets.
             */
            closeAdditionalSockets();
            if (debugFlagIsSet(Constants.instance().DEBUG_STATUS)) {
                LOGGER.info("Connection closed");
                mainInterface.log("Connection closed");
            }
            /*
             * The onClosedStatus() method has to be implemented by
             * a sublclass.  If used in conjunction with JavaFX,
             * use Platform.runLater() to force this method to run
             * on the main thread.
             */
            onClosedStatus(true);
        } catch (IOException e) {
            if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    /**
     * This method is invoked to do instance-specific socket initialization.
     * Due to the different ways that sockets are set up (e.g.
     * ServerSocket vs Socket), the implementation details go here.
     * Initialization up to and including either accept() or connect() take
     * place here.
     * @throws java.net.SocketException
     */
    protected abstract void initSocketConnection() throws SocketException;

    /**
     * This method is called to close any additional sockets that are
     * internally used.  In some cases (e.g. SocketClient), this method
     * should do nothing.  In others (e.g. SocketServer), this method should
     * close the internal ServerSocket instance.
     */
    protected abstract void closeAdditionalSockets();

    /*
     * Synchronized method set up to wait until the SetupThread is
     * sufficiently initialized.  When notifyReady() is called, waiting
     * will cease.
     */
    private synchronized void waitForReady() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    /*
     * Synchronized method responsible for notifying waitForReady()
     * method that it's OK to stop waiting.
     */
    private synchronized void notifyReady() {
        ready = true;
        notifyAll();
    }

    /**
     * Send a message in the form of a String to the socket.  A NEWLINE will
     * automatically be appended to the message.
     *
     * @param msg The String message to send
     */
    public void sendMessage(String msg) {
        try {
            output.write(msg, 0, msg.length());
            output.newLine();
            output.flush();
            if (debugFlagIsSet(Constants.instance().DEBUG_SEND)) {
                String logMsg = "send> " + msg;
                LOGGER.info(logMsg);
            }
        } catch (IOException e) {
            if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    class SetupThread extends Thread {

        @Override
        public void run() {
            try {
                initSocketConnection();
                if (socketConnection != null && !socketConnection.isClosed()) {
                    /*
                     * Get input and output streams
                     */
                    input = new BufferedReader(new InputStreamReader(
                            socketConnection.getInputStream()));
                    output = new BufferedWriter(new OutputStreamWriter(
                            socketConnection.getOutputStream()));
                    output.flush();
                }
                /*
                 * Notify SocketReaderThread that it can now start.
                 */
                //freddie notifyReady();
            } catch (IOException e) {
                if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
                    LOGGER.info(e.getMessage());
                }
                /*
                 * This will notify the SocketReaderThread that it should exit.
                 */
                //freddie notifyReady();
            }
        }
    }

    class SocketReaderThread extends Thread {

        
//        public double[] toDouble(byte[] bytes, int offset, int num) {
//            double[] da = new double[num / 8];
//            for (int i = 0; i < num / 8; i++) {
//                //byte[] te = new byte[8];
//               //System.arraycopy(bytes, offset + i * 8, te, 0, 8);
//                da[i] = ByteBuffer.wrap(bytes, offset + i * 8, 8).getDouble();
//            }
//            return da;
//        }
        
        @Override
        public void run() {
            /*
             * Wait until the socket is set up before beginning to read.
             */
            // freddie waitForReady();
            /* 
             * Now that the readerThread has started, it's safe to inform
             * the world that the socket is open, if in fact, it is open.
             * If used in conjunction with JavaFX, use Platform.runLater()
             * when implementing this method to force it to run on the main
             * thread.
             */
            if (socketConnection != null && socketConnection.isConnected()) {
                onClosedStatus(false) ;
            }
            /*
             * Read from from input stream one line at a time
             */
            char[] buf = new char[1024 * 1024];
            while (true) {
                try {
                    if (input != null) {
                        //String line;
                        int nrLen;
                        //.read(buf);
                        nrLen = input.read(buf,0, 4);
                        if (buf[0] == 'f' &&
                            buf[1] == 'a' &&
                            buf[2] == 'f' &&
                            buf[3] == 'e')
                        {
                            char[] ca = new char[4]; 
                            nrLen = input.read(ca,0, 4);
                            short pointNum = (short) (ca[0]<< 8 | ca[1] & 0xFF);                             
                            short sigNum = (short) (ca[2]<< 8 | ca[3] & 0xFF);
                            int readlen = pointNum * sigNum * 8 + sigNum * 4;
                            nrLen = input.read(buf,0, readlen);
                            

                        
                        
                        

                        
                        String[] tagArray = new String[sigNum];
                        double[][] data = new double[sigNum][pointNum];
                        

                        
                        
                        
                      //  System.out.printf("HEAD:%c%c%c%c Points:%d SignalNum: %d\n", (char)buf[0],(char)buf[1],(char)buf[2],(char)buf[3],
                      //                          pointNum, sigNum);
                        
                        for (int i = 0; i < sigNum; i++)
                        {
                            tagArray[i] = String.format("%c%c%c%c", 
                                                    (char)buf[i * 4 + 0],
                                                    (char)buf[i * 4 + 1],
                                                    (char)buf[i * 4 + 2],
                                                    (char)buf[i * 4 + 3]);
                        }
                        
                        //int dataOffset = 8 + sigNum * 4;
                        int headLen = 4 * sigNum;
                        
                        byte[] bt = new byte[nrLen - headLen];
                        for (int i = 0; i < nrLen - headLen; i++) {
                            bt[i] = (byte) buf[headLen + i];
                        }
                        
                      //  double[] scr = toDouble(bt, 0, nrLen - headLen);                        
//                        for (int i = 0; i < sigNum; i++)
//                        {
//                            for (int k = 0; k < pointNum; k++ )
//                            {
//                                data[i][k] = scr[k * pointNum + i];
//                            }
//                        }
                        
                        for (int i = 0; i < sigNum; i++)
                        {
                           // mainInterface.UpdataTable(tagArray[i],String.valueOf(data[i][0]));
                                                        byte []tempw = new byte[8]; 
                            System.arraycopy(bt,i*8,tempw,0,8); //, nrLen, bt, headLen, nrLen);
                            mainInterface.UpdataTable(tagArray[i],tempw);


                            System.out.println(ByteBuffer.wrap(bt,i*8,8).array());
                            
                        }
                        
                                                }
                        else
                        {
                            continue;
                        }
                        //     System.out.printf("tag %d: %c%c%c%c\n", i,
                        //             (char)buf[8 + i * 4 + 0],
                        //             (char)buf[8 + i * 4 + 1],
                        //             (char)buf[8 + i * 4 + 2],
                        //             (char)buf[8 + i * 4 + 3]);
                        //}
                        
                        //int dataOffset = 8 + 4 * sigNum;

                        

//                        System.out.println("****************************");
//                        double[] data = toDouble(bt, nrLen - headLen);
//                        for (int i = 0; i < pointNum * sigNum; i++) {
//                            System.out.println(data[i]);
//                        } 
                        
 //                       System.out.println("-----------------------------");



//                        while ((line = input.readLine()) != null) {
//                           // String logMsg = "recv> " + line;
//                           // System.out.println(logMsg);
//                            for (int i = 0; i < MonitorServer.clientList.size(); i++) {
//                                client c = MonitorServer.clientList.get(i);
//                                c.output.write(logMsg, 0, logMsg.length());
//                                c.output.newLine();
//                                c.output.flush();
//                            }
//                        }
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            
            
            
//            try {
//                if (input != null) {
//                    String line;
//                    while ((line = input.readLine()) != null) {
//                        if (debugFlagIsSet(Constants.instance().DEBUG_RECV)) {
//                            String logMsg = "recv> " + line;
//                            LOGGER.info(logMsg);
//                        }
//                        
//                        
//                        
//                        
//                        /*
//                         * The onMessage() method has to be implemented by
//                         * a sublclass.  If used in conjunction with JavaFX,
//                         * use Platform.runLater() to force this method to run
//                         * on the main thread.
//                         */
//                        onMessage(line);
//                    }
//                }
//            } catch (IOException e) {
//                if (debugFlagIsSet(Constants.instance().DEBUG_EXCEPTIONS)) {
//                    LOGGER.info(e.getMessage());
//                }
//            } finally {
//                close();
//            }
            
        }
    }
    
    public GenericSocket() {
        this(Constants.instance().DEFAULT_PORT,
                Constants.instance().DEBUG_NONE);
    }

    public GenericSocket(int port) {
        this(port, Constants.instance().DEBUG_NONE);
    }

    public GenericSocket(int port, int debugFlags) {
        this.port = port;
        this.debugFlags = debugFlags;
    }
}
