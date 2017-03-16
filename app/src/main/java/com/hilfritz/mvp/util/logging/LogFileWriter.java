package com.hilfritz.mvp.util.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Hilfritz Camallere on 27/2/17.
 * PC name herdmacbook1
 */

public class LogFileWriter {

    public static void appendLog(String text, String absoluteFilePath)
    {
        File logFile = new File(absoluteFilePath);
        LogFileWriter.appendLog(text, logFile);
    }

    public static void appendLog(String text, File logFile)
    {
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
