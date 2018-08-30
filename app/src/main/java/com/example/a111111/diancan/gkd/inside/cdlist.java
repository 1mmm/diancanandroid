package com.example.a111111.diancan.gkd.inside;

public class cdlist {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String name;
    private String price;
    private String desc;
    private String img;



    public cdlist() {
    }

    public cdlist(String id,String name, String price, String desc,String img) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.id = id;
        this.desc = desc;
    }




}
