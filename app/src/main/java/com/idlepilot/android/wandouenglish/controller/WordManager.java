package com.idlepilot.android.wandouenglish.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idlepilot.android.wandouenglish.model.Word;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by xuzywozz on 2015/10/23.
 */
public class WordManager
{

    private static final String TAG = "WordManager";

    public Context context = null;
    private DataBaseHelperDict dbHelper = null;
    private SQLiteDatabase dbR = null;
    private SQLiteDatabase dbW = null;
    public String tableName = null;

    public WordManager()
    {

    }

    public WordManager(Context paramContext, String paramString)
    {
        this.context = paramContext;
        this.tableName = paramString;
        //DatabaseContext重写openOrCreateDatabase 将数据库保存在sd卡
        DatabaseContext dbContext = new DatabaseContext(paramContext);
        this.dbHelper = new DataBaseHelperDict(dbContext, paramString);
        this.dbR = this.dbHelper.getReadableDatabase();
        this.dbW = this.dbHelper.getWritableDatabase();
    }

    protected void finalize()
            throws Throwable
    {
        this.dbR.close();
        this.dbW.close();
        this.dbHelper.close();
        super.finalize();
    }

    public String getInterpret(String searchedWord)
    {
        Cursor localCursor = this.dbR.query(this.tableName, new String[]{"interpret"}, "word=?", new String[]{searchedWord}, null, null, null);
        if (!localCursor.moveToNext())
        {
            localCursor.close();
            return null;
        }
        String str = localCursor.getString(localCursor.getColumnIndex("interpret"));
        localCursor.close();
        return str;
    }

    public String getPronEngUrl(String searchedWord)
    {
        Cursor localCursor = this.dbR.query(this.tableName, new String[]{"prone"}, "word=?", new String[]{searchedWord}, null, null, null);
        if (!localCursor.moveToNext())
        {
            localCursor.close();
            return null;
        }
        String str = localCursor.getString(localCursor.getColumnIndex("prone"));
        localCursor.close();
        return str;
    }

    public String getPronUSAUrl(String searchedWord)
    {
        Cursor localCursor = this.dbR.query(this.tableName, new String[]{"prona"}, "word=?", new String[]{searchedWord}, null, null, null);
        if (!localCursor.moveToNext())
        {
            localCursor.close();
            return null;
        }
        String str = localCursor.getString(localCursor.getColumnIndex("prona"));
        localCursor.close();
        return str;
    }

    public String getPsEng(String searchedWord)
    {
        Cursor localCursor = this.dbR.query(this.tableName, new String[]{"pse"}, "word=?", new String[]{searchedWord}, null, null, null);
        if (!localCursor.moveToNext())
        {
            localCursor.close();
            return null;
        }
        String str = localCursor.getString(localCursor.getColumnIndex("pse"));
        localCursor.close();
        return str;
    }

    public String getPsUSA(String searchedWord)
    {
        Cursor localCursor = this.dbR.query(this.tableName, new String[]{"psa"}, "word=?", new String[]{searchedWord}, null, null, null);
        if (!localCursor.moveToNext())
        {
            localCursor.close();
            return null;
        }
        String str = localCursor.getString(localCursor.getColumnIndex("psa"));
        localCursor.close();
        return str;
    }

    public ArrayList<Word> getStrangeWordlist()
    {
        ArrayList<Word> strangeWordlist = new ArrayList<Word>();
        Cursor cursor = dbR.rawQuery("select * from dict where isStrange = 0", null);
        while(cursor.moveToNext())
        {
            Word word = new Word();
            word.setWord(cursor.getString(0));
            word.setPsE(cursor.getString(1));
            word.setPronE(cursor.getString(2));
            word.setPsA(cursor.getString(3));
            word.setPronA(cursor.getString(4));
            word.setInterpret(cursor.getString(5));
            word.setSentOrig(cursor.getString(6));
            word.setSentTrans(cursor.getString(7));
            word.setIsStrange(cursor.getInt(8));
            strangeWordlist.add(word);
        }
        cursor.close();
        return strangeWordlist;
    }

    public Word getWordFromDict(String searchedWord)
    {
        Word word = new Word();//预防空指针异常
//		db.execSQL("create table dict(word text,pse text,prone text,psa text,prona text," +
//				"interpret text, sentorig text, senttrans text)");
        String[] columns = new String[]{"word",
                "pse", "prone", "psa", "prona", "interpret", "sentorig", "senttrans"};

        String[] strArray = new String[8];
        Cursor cursor = dbR.query(tableName, columns, "word=?", new String[]{searchedWord}, null, null, null);
        while (cursor.moveToNext())
        {
            for (int i = 0; i < strArray.length; i++)
            {
                strArray[i] = cursor.getString(cursor.getColumnIndex(columns[i]));

            }
            word = new Word(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4], strArray[5], strArray[6], strArray[7]);
        }
        cursor.close();
        return word;
    }

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
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return word;
    }

    public void insertWordToDict(Word word, boolean isOverWrite)
    {
        Log.i(TAG, "插入单词：" + word.getWord());
        if (word == null)
        {          //避免空指针异常
            return;
        }
        Cursor cursor = null;
        try
        {
            ContentValues values = new ContentValues();
            values.put("word", word.getWord());
            values.put("pse", word.getPsE());
            values.put("prone", word.getPronE());
            values.put("psa", word.getPsA());
            values.put("prona", word.getPronA());
            values.put("interpret", word.getInterpret());
            values.put("sentorig", word.getSentOrig());
            values.put("senttrans", word.getSentTrans());
            cursor = dbR.query(tableName, new String[]{"word"}, "word=?", new String[]{word.getWord()}, null, null, null);
            if (cursor.getCount() > 0)
            {
                if (isOverWrite == false)//首先看看数据库中有没有这个单词，若词典库中已经有了这一个单词，所以不再操作
                    return;
                else
                {              //执行更新操作
                    dbW.update(tableName, values, "word=?", new String[]{word.getWord()});
                    Log.i(TAG, "更新");
                }
            } else
            {
                dbW.insert(tableName, null, values);
                //这里可能会发生空指针异常，到时候考虑
            }
        } catch (Exception e)
        {

        } finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    public void updateWordToDict(Word word)
    {
        Log.i(TAG, "更新单词：" + word.getWord());
        if (word == null)
        {          //避免空指针异常
            return;
        }
        Cursor cursor = null;
        try
        {
            ContentValues values = new ContentValues();
            values.put("word", word.getWord());
            values.put("pse", word.getPsE());
            values.put("prone", word.getPronE());
            values.put("psa", word.getPsA());
            values.put("prona", word.getPronA());
            values.put("interpret", word.getInterpret());
            values.put("sentorig", word.getSentOrig());
            values.put("senttrans", word.getSentTrans());
            values.put("isStrange", word.isStrange());
            cursor = dbR.query(tableName, new String[]{"word"}, "word=?", new String[]{word.getWord()}, null, null, null);
            if (cursor.getCount() > 0)
            {
                //执行更新操作
                dbW.update(tableName, values, "word=?", new String[]{word.getWord()});
                Log.i(TAG, "更新");
            } else
            {
                dbW.insert(tableName, null, values);
                //这里可能会发生空指针异常，到时候考虑
            }
        } catch (Exception e)
        {

        } finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    public boolean isWordExist(String searchedWord)
    {
        Cursor localCursor = null;
        try
        {
            localCursor = this.dbR.query(this.tableName, new String[]{"word"}, "word=?", new String[]{searchedWord}, null, null, null);
            if (localCursor.getCount() > 0)
            {
                localCursor.close();
                return true;
            }
            localCursor.close();
            return false;
        } finally
        {
            if (localCursor != null)
                localCursor.close();
        }
    }
}
