package com.zzm.Result;
import com.zzm.TaskModel;
public class CreateTaskResult {
    public enum CreateTaskResultEnum {
        SUCCESS, // 创建任务成功
        INVALID_INPUT // 输入无效
    }
    private CreateTaskResultEnum status;
    private TaskModel task;
    public CreateTaskResult(CreateTaskResultEnum status, TaskModel task) {
        this.status = status;
        this.task = task;
    }
    public CreateTaskResultEnum getStatus() {
        return status;
    }
    public TaskModel getTask() {
        return task;
    }
}
