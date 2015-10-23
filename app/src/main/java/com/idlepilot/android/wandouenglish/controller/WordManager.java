package com.idlepilot.android.wandouenglish.controller;

import com.idlepilot.android.wandouenglish.model.Word;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by xuzywozz on 2015/10/23.
 */
public class WordManager
{
    public Word getWordFromInternet(String searchedWord)
    {
        Word word = null;
        String tempWord = searchedWord;
        if (tempWord == null && tempWord.equals(""))
            return null;
        char[] array = tempWord.toCharArray();
        if (array[0] > 256)           //是中文，或其他语言的的简略判断
            tempWord = "_" + URLEncoder.encode(tempWord);
        InputStream in = null;
        String str = null;
        try
        {
            String tempUrl = NetOperator.urlPre + tempWord + NetOperator.urlLow;
            in = NetOperator.getInputStreamByUrl(tempUrl);    //从网络获得输入流
            if (in != null)
            {
                //new FileUtils().saveInputStreamToFile(in, "", "gfdgf.txt");    
                XMLParser xmlParser = new XMLParser();
                InputStreamReader reader = new InputStreamReader(in, "utf-8");        //最终目的获得一个InputSource对象用于传入形参
                ContentHandler contentHandler = new ContentHandler();
                xmlParser.parseJinShanXml(contentHandler, new InputSource(reader));
                word = contentHandler.getWord();
                word.setWord(searchedWord);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return word;
    }
}
