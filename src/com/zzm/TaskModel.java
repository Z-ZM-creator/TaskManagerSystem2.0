package com.zzm;

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
    public TaskModel(String taskName){
        this.TaskID=String.format("%10d",nextId++);
        this.TaskName=taskName;
        this.status=TaskStatus.TO_DO;
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
    @Override
    public String toString(){
        return "TaskID:"+TaskID+" TaskName:"+TaskName+" Status:"+status;
    }
}
