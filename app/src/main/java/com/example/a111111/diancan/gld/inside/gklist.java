package com.example.a111111.diancan.gld.inside;

public class gklist {
    private String firstname;
    private String nichen;
    private String code;
    private String lastname;
    private String level;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNichen() {
        return nichen;
    }

    public void setNichen(String nichen) {
        this.nichen = nichen;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String age;
    private String id;



    public gklist() {
    }

    public gklist(String id,String lastname, String firstname, String code,String nichen,String level,String age) {
        this.level = level;
        this.age = age;
        this.lastname = lastname;
        this.id = id;
        this.code = code;
        this.nichen=nichen;
        this.firstname=firstname;
    }




}
