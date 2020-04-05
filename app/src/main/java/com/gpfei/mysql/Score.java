package com.gpfei.mysql;

public class Score {
    private String s_name;
    private String mark;


    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString(){
        return mark;
    }
}
