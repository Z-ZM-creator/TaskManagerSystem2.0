package com.zzm;
import java.util.HashMap;
public class UserModel {
//    public enum UserStatus{
//        //不存在
//        NOT_EXIST,
//        //正常
//        NORMAL
//    }
    private static long nextId=0L;
    private String UserName;
    String UserID;
    String password;
    HashMap<String,TaskModel> taskMap;
    public UserModel(String UserName,String Password){
        this.UserName=UserName;
        this.password=Password;
        this.UserID=String.format("%10d",nextId++);
        this.taskMap=new HashMap<>();
    }
    public String getUserName(){
        return UserName;
    }
    public HashMap<String,TaskModel> getTaskMap(){
        return taskMap;
    }
    public String getUserPassword(){
        return password;
    }
    @Override
    public String toString(){
        return "UserName:"+UserName+" UserID:"+UserID;
    }

}
