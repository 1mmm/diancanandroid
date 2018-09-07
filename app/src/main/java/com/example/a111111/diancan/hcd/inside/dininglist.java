package com.example.a111111.diancan.hcd.inside;

public class dininglist {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String price) {
        this.menu = menu;
    }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    private String uid;
    private String menu;
    private String time;
    private String num;


    public dininglist(String id,String uid, String menu, String time,String num) {
        this.uid = uid;
        this.menu = menu;
        this.time = time;
        this.id = id;
        this.num = num;

    }




}
