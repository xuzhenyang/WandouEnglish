package com.idlepilot.android.wandouenglish.controller;

/**
 * Created by xuzywozz on 2015/10/24.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.os.Environment;

public class FileUtils
{
    private String SDPATH;

    public FileUtils()
    {
        SDPATH = Environment.getExternalStorageDirectory() + "/";

        //System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    //创建文件,一定不会返回空

    /**
     * @param path     直接创建文件即可，无需考虑文件夹有没有创建,若文件已存在返回null
     * @param fileName
     * @return
     */
    public File createSDFile(String path, String fileName)
    {
        File file = null;
        createSDDir(path);
        try
        {
            file = new File(SDPATH + path + fileName);
            if (file.exists() && file.isFile())
            {
                return null;
            }
            file.createNewFile();  //创建文件

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return file;
    }

    //创建目录,如果存在同名文件夹则返回该文件夹，否则创建文件
    public File createSDDir(String dirName)
    {
        File dir = new File(SDPATH + dirName);
        if (dir.exists() && dir.isDirectory())
        {
            return dir;
        }
        dir.mkdirs();  //可创建多级文件夹
        return dir;
    }


    //这里写相对目录
    public ArrayList<String> listContentsOfFile(String path)
    {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(SDPATH + path);
        File[] fileList = file.listFiles();
        if (fileList == null)
            return list;
        for (int i = 0; i < fileList.length; i++)
        {
            System.out.println(fileList[i].getName());
        }
        return list;
    }

    //判断SD卡文件夹是否存在
    public boolean isFileExist(String path, String fileName)
    {
        File file = new File(SDPATH + path + fileName);
        return file.exists();
    }

    //获得文件输入流
    public InputStream getInputStreamFromFile(String path, String fileName)
    {
        InputStream input = null;
        File file = new File(SDPATH + path + fileName);
        if (file.exists() == false)
            return null;
        try
        {
            input = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch bloc
            e.printStackTrace();
            return null;
        }

        return input;
    }


    /**
     * @param in
     * @param path     文件存储的相对路径
     * @param fileName
     * @return
     */
    public boolean saveInputStreamToFile(InputStream in, String path, String fileName)
    {
        File file = createSDFile(path, fileName); //相对路径即可
        int length = 0;
        if (file == null)
            return true;  //其实这里的情况是文件已存在
        byte[] buffer = new byte[1024];
        FileOutputStream fOut = null;
        try
        {
            fOut = new FileOutputStream(file);

            while ((length = in.read(buffer)) != -1)
            {          //要利用read返回的实际成功读取的字节数，将buffer写入文件，否则将会出现错误的字节
                fOut.write(buffer, 0, length);
            }

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                fOut.close();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public String getSDRootPath()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }

    public String getSDPATH()
    {
        return SDPATH;
    }

    public void setSDPATH(String sDPATH)
    {
        SDPATH = sDPATH;
    }

}