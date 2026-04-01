package com.zzm;
import java.util.HashMap;
public class UserModel {
    private static long nextId=0L;
    private String UserName;
    String UserID;
    String password;
    HashMap<String,TaskModel> taskMap;
    public UserModel(String UserName,String Password){
        this.UserName=UserName;
        this.password=Password;
        this.UserID=String.format("%010d",nextId++);
        this.taskMap=new HashMap<>();
    }
    //新构造方法用于载入时读取原有ID
    public UserModel(String UserID,String UserName,String Password){
        this.UserID=UserID;
        this.UserName=UserName;
        this.password=Password;
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
    public String getUserID(){
        return UserID;
    }
    //setNextID方法用于载入时设置ID
    public static void setNextId(long id){
        nextId=id;
    }
    @Override
    public String toString(){
        return "UserName:"+UserName+" UserID:"+UserID;
    }

}
