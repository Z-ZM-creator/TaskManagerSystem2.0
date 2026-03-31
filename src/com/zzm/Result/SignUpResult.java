package com.zzm.Result;
import com.zzm.UserModel;
public class SignUpResult {
    public enum SignUpStatus{
        SUCCESS,
        ILLEGAL_ARGUMENT
    }
    public SignUpStatus status;
    public UserModel user;
    public SignUpResult(SignUpStatus  status,UserModel  user){
        this.status=status;
        this.user=user;
    }
    public SignUpStatus getStatus(){
        return status;
    }
    public UserModel getUser(){
        return user;
    }
}
