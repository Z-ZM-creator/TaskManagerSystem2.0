package com.zzm.Result;
import com.zzm.UserModel;
public class SignInResult {
    public enum SignInStatus{
        SUCCESS,//成功操作
        ID_NOT_FOUND,//ID不存在
        PASSWORD_ERROR//密码错误
    }
    private SignInStatus status;
    private UserModel user;
    public SignInResult(SignInStatus status,UserModel user){
        this.status=status;
        this.user=user;
    }
    public SignInStatus getStatus(){
        return status;
    }
    public UserModel getUser(){
        return user;
    }
}
