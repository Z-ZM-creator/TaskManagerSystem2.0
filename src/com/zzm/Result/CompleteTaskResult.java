package com.zzm.Result;
import com.zzm.TaskModel;
public class CompleteTaskResult {
    public enum CompleteTaskStatus{
        SUCCESS,
        TASK_NOT_FOUND,
        ILLEGAL_ARGUMENT,
        TASK_NOT_STARTED,
        TASK_ALREADY_COMPLETED
    }
    public CompleteTaskStatus status;
    public TaskModel task;
    public CompleteTaskResult(CompleteTaskStatus status,TaskModel task){
        this.status=status;
        this.task=task;
    }
    public CompleteTaskStatus getStatus(){
        return status;
    }
    public TaskModel getTask(){
        return task;
    }
}
