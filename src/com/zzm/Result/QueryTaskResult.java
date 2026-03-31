package com.zzm.Result;
import com.zzm.TaskModel;
public class QueryTaskResult {
    public enum QueryTaskResultEnum{
        SUCCESS,
        ID_NOT_FOUND,
        INVALID_INPUT
    }
    private QueryTaskResultEnum queryTaskResultEnum;
    private TaskModel task;
    public QueryTaskResult(QueryTaskResultEnum queryTaskResultEnum,TaskModel task){
        this.queryTaskResultEnum=queryTaskResultEnum;
        this.task=task;
    }
    public QueryTaskResultEnum getQueryTaskResultEnum(){
        return queryTaskResultEnum;
    }
    public TaskModel getTask(){
        return task;
    }
}
