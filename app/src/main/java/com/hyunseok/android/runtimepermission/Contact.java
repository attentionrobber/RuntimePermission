package com.hyunseok.android.runtimepermission;

import java.util.ArrayList;

/**
 * 전화번호부 Pure Old Java Object(POJO) - 변수와 getter, setter로만 구성된 클래스
 * Created by Administrator on 2017-02-01.
 */

public class Contact {
    private int id;
    private String name;
    private ArrayList<String> phoneNum;

    public Contact() {
        phoneNum = new ArrayList<>(); // 초기화해줌.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTelOne() {
        if(phoneNum.size() > 0)
            return phoneNum.get(0);
        else
            return null;
    }
    public  ArrayList<String> getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(ArrayList<String> phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * 하나만 추가, 삭제 할 때
     * @param phoneNum
     */
    public void addTel(String phoneNum) {
        this.phoneNum.add(phoneNum);
    }
    public void removeTel(String phoneNum) {
        this.phoneNum.remove(phoneNum);
    }
}
