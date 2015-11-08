package com.idlepilot.android.wandouenglish.model;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by xuzywozz on 2015/10/23.
 */
public class Word
{
    //分别为单词名称、英音发音、英音发音mp3、美音发音、美音发音mp3、基本释义、例句、例句翻译
    private String word = null;
    private String psE = null;
    private String pronE = null;
    private String psA = null;
    private String pronA = null;
    private String interpret = null;
    private String sentOrig = null;
    private String sentTrans = null;
    //1表示不是生词 0表示是生词 （SQLite不能存放boolean
    private int isStrange = 1;

    public Word()
    {
        this.word = "";
        this.psE = "";
        this.pronE = "";
        this.psA = "";
        this.pronA = "";
        this.interpret = "";
        this.sentOrig = "";
        this.sentTrans = "";
        this.isStrange = 1;
    }

    public Word(String word, String psE, String pornE, String psA, String pornA, String interpret, String sentOrig, String sentTrans)
    {
        this.word = word;
        this.psE = psE;
        this.pronE = pornE;
        this.psA = psA;
        this.pronA = pornA;
        this.interpret = interpret;
        this.sentOrig = sentOrig;
        this.sentTrans = sentTrans;
    }

    public Word(String word, String psE, String pornE, String psA, String pornA, String interpret, String sentOrig, String sentTrans, int isStrange)
    {
        this.word = word;
        this.psE = psE;
        this.pronE = pornE;
        this.psA = psA;
        this.pronA = pornA;
        this.interpret = interpret;
        this.sentOrig = sentOrig;
        this.sentTrans = sentTrans;
        this.isStrange = isStrange;
    }

    public ArrayList<String> getOrigList()
    {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader(this.sentOrig));
        String str = null;
        try
        {
            while ((str = br.readLine()) != null)
            {
                list.add(str);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getTransList()
    {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader(this.sentTrans));
        String str = null;
        try
        {
            while ((str = br.readLine()) != null)
            {
                list.add(str);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getPsE()
    {
        return psE;
    }

    public void setPsE(String psE)
    {
        this.psE = psE;
    }

    public String getPronE()
    {
        return pronE;
    }

    public void setPronE(String pronE)
    {
        this.pronE = pronE;
    }

    public String getPsA()
    {
        return psA;
    }

    public void setPsA(String psA)
    {
        this.psA = psA;
    }

    public String getPronA()
    {
        return pronA;
    }

    public void setPronA(String pronA)
    {
        this.pronA = pronA;
    }

    public String getInterpret()
    {
        return interpret;
    }

    public void setInterpret(String interpret)
    {
        this.interpret = interpret;
    }

    public String getSentOrig()
    {
        return sentOrig;
    }

    public void setSentOrig(String sentOrig)
    {
        this.sentOrig = sentOrig;
    }

    public String getSentTrans()
    {
        return sentTrans;
    }

    public void setSentTrans(String sentTrans)
    {
        this.sentTrans = sentTrans;
    }

    public int isStrange()
    {
        return isStrange;
    }

    public void setIsStrange(int isStrange)
    {
        this.isStrange = isStrange;
    }

    public void printInfo()
    {
        System.out.println(this.word);
        System.out.println(this.psE);
        System.out.println(this.pronE);
        System.out.println(this.psA);
        System.out.println(this.pronA);
        System.out.println(this.interpret);
        System.out.println(this.sentOrig);
        System.out.println(this.sentTrans);
        System.out.println(this.isStrange);
    }
}
