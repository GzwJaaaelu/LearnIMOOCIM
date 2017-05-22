package com.google.jaaaule.gzw.italker;

/**
 * Created by admin on 2017/5/22.
 */

public class UserQuery implements IUser {

    @Override
    public String query(int hashCode) {
        return "User: " + hashCode;
    }
}
