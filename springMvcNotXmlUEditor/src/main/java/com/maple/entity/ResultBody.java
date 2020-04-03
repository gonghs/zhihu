package com.maple.entity;

import java.util.List;

/**
 * Created by TYZ034 on 2018/3/14.
 */
public class ResultBody {
    private String state = "SUCCESS";
    private List list;
    private int start;
    private int total;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        if(getList()!=null){
            return getList().size();
        }else{
            return 0;
        }

    }

    public void setTotal(int total) {
        this.total = total;
    }
}
