package com.sample.sendhistory;

public class SearchModel
{
    public SearchModel(int index, String address, int rate)
    {
        this.index = index;
        this.address = address;
        this.rate = rate;
    }

    public int index = 0;
    public String address = " ";
    public int rate = 0;


    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    @Override
    public String toString()
    {
        return "Model{" +
                "index=" + index +
                ", address='" + address + '\'' +
                ", rate=" + rate +
                '}';
    }
}
