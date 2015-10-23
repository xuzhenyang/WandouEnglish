package com.idlepilot.android.wandouenglish.controller;

import com.idlepilot.android.wandouenglish.model.Word;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by xuzywozz on 2015/10/23.
 */
public class ContentHandler extends DefaultHandler
{
    public Word word = null;
    private String tagName = null;
    private String interpret = "";       //防止空指针异常
    private String orig = "";
    private String trans = "";
    private boolean isChinese = false;

    public ContentHandler()
    {
        word = new Word();
        isChinese = false;
    }

    public Word getWord()
    {
        return word;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        if (length <= 0)
            return;
        for (int i = start; i < start + length; i++)
        {
            if (ch[i] == '\n')
                return;
        }

        //去除莫名其妙的换行！

        String str = new String(ch, start, length);
        if (tagName == "key")
        {
            word.setWord(str);
        }
        else if (tagName == "ps")
        {
            if (word.getPsE().length() <= 0)
            {
                word.setPsE(str);
            }
            else
            {
                word.setPsA(str);
            }
        }
        else if (tagName == "pron")
        {
            if (word.getPronE().length() <= 0)
            {
                word.setPronE(str);
            }
            else
            {
                word.setPronA(str);
            }
        }
        else if (tagName == "pos")
        {
            isChinese = false;
            interpret = interpret + str + " ";
        }
        else if (tagName == "acceptation")
        {
            interpret = interpret + str + "\n";
            interpret = word.getInterpret() + interpret;
            word.setInterpret(interpret);
            interpret = ""; //初始化操作，预防有多个释义
        }
        else if (tagName == "orig")
        {


            orig = word.getSentOrig();
            word.setSentOrig(orig + str + "\n");


        }
        else if (tagName == "trans")
        {
            String temp = word.getSentTrans() + str + "\n";
            word.setSentTrans(temp);

        }
        else if (tagName == "fy")
        {
            isChinese = true;
            word.setInterpret(str);
        }


    }


    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
        tagName = null;


    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException
    {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        tagName = localName;
    }

    @Override
    public void endDocument() throws SAXException
    {
        // TODO Auto-generated method stub
        super.endDocument();
        if (isChinese)
            return;
        String interpret = word.getInterpret();
        if (interpret != null && interpret.length() > 0)
        {
            char[] strArray = interpret.toCharArray();
            word.setInterpret(new String(strArray, 0, interpret.length() - 1));
            //去掉解释的最后一个换行符
        }
    }
}
