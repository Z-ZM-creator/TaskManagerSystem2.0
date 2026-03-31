package com.zzm.Result;
import com.zzm.TaskModel;
public class DeleteTaskResult {
    public enum deleteTaskStatus {
        SUCCESS,
        TASK_NOT_FOUND,
        ILLEGAL_ARGUMENT
    }
    private deleteTaskStatus status;
    private TaskModel task;
    public DeleteTaskResult(deleteTaskStatus status, TaskModel task) {
        this.status = status;
        this.task = task;
    }
    public deleteTaskStatus getStatus() {
        return status;
    }
    public TaskModel getTask() {
        return task;
    }
}
