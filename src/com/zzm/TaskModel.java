package com.zzm;

import jdk.jshell.Snippet;

public class TaskModel {
    public enum TaskStatus{
        TO_DO,
        IN_PROGRESS,
        DONE
    }
    public enum TaskResult{
        SUCCESS,
        TASK_NOT_FOUND,//任务不存在
        INVALID_STATUS_TRANSITION,//无效的状态转换
        DUPLICATE_ID,//重复的ID
        ILLEGAL_ARGUMENT//非法参数
    }
    private static long nextId=0L;
    private String TaskID;
    private String TaskName;
    private TaskStatus status;
    //这个构造方法只用于新建任务时，ID自动分配，默认状态
    public TaskModel(String taskName){
        this.TaskID=String.format("%010d",nextId++);
        this.TaskName=taskName;
        this.status=TaskStatus.TO_DO;
    }
    //这个二元素的构造方法用于文件载入时读取原有ID。
    public TaskModel(String taskID,String taskName,TaskStatus status){
        this.TaskID=taskID;
        this.TaskName=taskName;
        this.status=status;
    }
    public String getTaskID(){
        return TaskID;
    }
    public String getTaskName(){
        return TaskName;
    }
        public void setStatus(TaskStatus status){
            this.status=status;
        }
    public TaskStatus getStatus(){
        return status;
    }
    public static void setNextId(long id){
        nextId=id;
    }
    @Override
    public String toString(){
        return "TaskID:"+TaskID+" TaskName:"+TaskName+" Status:"+status;
    }
}
