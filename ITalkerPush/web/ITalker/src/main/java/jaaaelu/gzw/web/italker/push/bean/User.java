package jaaaelu.gzw.web.italker.push.bean;

/**
 * Created by admin on 2017/5/19.
 */
public class User {
    private String name;
    private int sex;

    public User(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
