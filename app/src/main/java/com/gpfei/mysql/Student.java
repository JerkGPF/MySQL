package com.gpfei.mysql;


public class Student {

    private String s_id;
    private String s_name;
    private String s_sex;
    private String s_birth;
    private String s_class;

    public String gets_id() {
        return s_id;
    }

    public void sets_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_sex() {
        return s_sex;
    }

    public void setS_sex(String s_sex) {
        this.s_sex = s_sex;
    }

    public String getS_birth() {
        return s_birth;
    }

    public void setS_birth(String s_birth) {
        this.s_birth = s_birth;
    }

    public String getS_class() {
        return s_class;
    }

    public void setS_class(String s_class) {
        this.s_class = s_class;
    }
    @Override
    public String toString(){
        return s_id+","+s_name+","+s_sex+","+s_birth+","+s_class;
    }

}
