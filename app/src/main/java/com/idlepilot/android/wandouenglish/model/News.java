package com.idlepilot.android.wandouenglish.model;


public class News
{

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    int Id;

    public String getImage()
    {
        return Image;
    }

    public void setImage(String image)
    {
        Image = image;
    }

    public String getInfo()
    {
        return Info;
    }

    public void setInfo(String info)
    {
        Info = info;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getDate()
    {
        return Date;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    String Image;
    String Info;
    String Title;
    String Date;

    public String getContentUrl()
    {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl)
    {
        this.contentUrl = contentUrl;
    }

    String contentUrl;
}
