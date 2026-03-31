package com.zzm.Result;
import com.zzm.TaskModel;
public class StartTaskResult {
    public enum StartTsakStatus{
        SUCCESS,//任务开始成功
        TASK_NOT_FOUND,//任务未找到
        TASK_ALREADY_STARTED,//任务已开始
        TASK_ALREADY_COMPLETED,//任务已结束
        IllEGAL_ARGUMENT//非法参数
    }
    private StartTsakStatus status;
    private TaskModel task;
    public StartTaskResult(StartTsakStatus status,TaskModel task){
        this.status=status;
        this.task=task;
    }
    public StartTsakStatus getStatus(){
        return status;
    }
    public TaskModel getTask(){
        return task;
    }
}
