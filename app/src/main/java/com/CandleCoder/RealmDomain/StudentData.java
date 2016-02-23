package com.CandleCoder.RealmDomain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by manikant.upadhyay on 2/23/2016.
 */
public class StudentData extends RealmObject {
private String Name;
/*private String Sex;*/
private int Age;
    @PrimaryKey
    private String Email;


    public StudentData(){}

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

   /* public void setSex(String sex) {
        this.Sex = sex;
    }

    public String getSex() {
        return Sex;
    }
*/
    public void setAge(int age) {
        this.Age = age;
    }

    public int getAge() {
        return Age;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getEmail() {
        return Email;
    }

}
