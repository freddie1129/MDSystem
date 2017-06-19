/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdsystem;
import java.nio.ByteBuffer;
import mdsystem.Monitor;

/**
 *
 * @author freddie
 */
public class MonitorEnum extends Monitor{
    
    int [] valueEnum;
    public MonitorEnum(String strID,String strName, String strUnit, String strInfo,int[] array)
    {
        super(strID,strName,strUnit, strInfo);
        valueEnum = new int[array.length]; 
        System.arraycopy(array, 0, valueEnum, 0, array.length);
        type = Type.ENUM;
    }   
    public void setEnum(int[] array)
    {
        System.arraycopy(array, 0, valueEnum, 0, array.length);
    }
    public int [] getEnum()
    {
        int [] re = new int[valueEnum.length];
        System.arraycopy(valueEnum, 0, re, 0, valueEnum.length);
        return re;
    }
    
    @Override
    public boolean varify(int value)
    { 
        for (int i = 0; i < valueEnum.length; i++)
        {
            if (value == valueEnum[i])
                setStatus(true);
                return true;
        }
        setStatus(false);
        return false;
    }
    
    @Override
    public boolean varify(byte[] value)
    {   
        int d = (value[0] << 8) | (value[1] & 0xff);
        boolean b = varify(d);
        setStatus(b);
        return b;
    }
    
    @Override
    public String getDisplayStr(byte [] b)
    {
        return String.valueOf(ByteBuffer.wrap(b,0,4).getInt());
    }
}
