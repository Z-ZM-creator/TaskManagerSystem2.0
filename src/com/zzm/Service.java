package com.zzm;

import java.util.HashMap;

import com.zzm.Result.*;

import java.io.*;

public class Service {
    //新建一个UserMap，存储用户名和密码
    private HashMap<String,UserModel> userMap=new HashMap<>();
    private HashMap<String,TaskModel> currentTaskMap;
    private UserModel currentUser;
    //SaveUsersMap用于存储用户列表
    //if结构错误，判断文件不存在后创建，跳出if判断后再进行文件写入
    //文件名前后不一致，保存文件为User List.txt，加载文件为UserList.txt
    public void SaveUserMap() {
        try {
            File file = new File("userList.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String key : userMap.keySet()) {
                String line = key + "-" + userMap.get(key).getUserName() + "-" + userMap.get(key).getUserPassword();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //LoadUsersMap用于加载用户列表
    //line.length()!=3的问题在哪
    public void LoadUserMap(String FilePath){
        try {
            File file = new File(FilePath);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            long maxid=-1;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] lines = line.split("-");
                if (lines.length != 3) {
                    continue;
                }
                String userID = lines[0];
                String userName = lines[1];
                String password = lines[2];
                //使用新构造方法，读取文件时使用原有ID，避免ID丢失
                userMap.put(userID, new UserModel(userID,userName, password));
                long idNum=Long.parseLong(String.valueOf(userID));
                if(idNum>maxid){
                    maxid=idNum;
                }
            }
            UserModel.setNextId(maxid+1);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //SaveTaskMap方法用于保存任务列表
    public void SaveTaskMap(String UserId){
        try {
            UserModel user = userMap.get(UserId);
            HashMap<String, TaskModel> taskMap = user.getTaskMap();
            String filePath = "User_" + UserId + "_TaskList.txt";
           File file=new File(filePath);
           if(!file.exists()){
               file.createNewFile();
           }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String key : taskMap.keySet()) {
                TaskModel task = taskMap.get(key);
                String line = task.getTaskID() + "-" + task.getTaskName() + "-" + task.getStatus();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //LoadTaskMap方法用于加载任务列表
    //ID计数器丢失，每次重载后计数器从0开始，更改查找max，获得最大值。
    public void LoadTaskMap(String UserId){
        try {
            UserModel user = userMap.get(UserId);
            String filePath = "User_" + UserId + "_TaskList.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
                long maxid=-1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length != 3) {
                    continue;
                }
                String TaskId = parts[0];
                String taskName = parts[1];
                TaskModel.TaskStatus status = TaskModel.TaskStatus.valueOf(parts[2]);
                //ID加载时丢失原有ID ，
                TaskModel task = new TaskModel(TaskId, taskName, status);
                user.getTaskMap().put(TaskId, task);
                if(Long.parseLong(TaskId)>maxid){
                    maxid=Long.parseLong(TaskId);
                }
                TaskModel.setNextId(maxid+1);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //SignUp方法用于注册用户，接收用户名和密码，并返回注册结果。
    //SignUp没有调用saveUserMap方法
    //SignUp没有调用saveTaskMap方法,导致下一次登录后无法显示任务
    public SignUpResult SignUp(String UserName,String Password){
        SignUpResult signUpResult;
        if(UserName==null||Password==null){
            return signUpResult=new SignUpResult (SignUpResult.SignUpStatus.ILLEGAL_ARGUMENT,null);
        }else{
            UserModel user=new UserModel(UserName,Password);
                userMap.put(user.getUserID(), user);
            SaveUserMap();
            SaveTaskMap(user.getUserID());
            return signUpResult=new SignUpResult(SignUpResult.SignUpStatus.SUCCESS,user);
            }
    }
//SignIn方法用于登录用户，接收用户名和密码，并返回登录结果。
    //password比较方式错误，比较引用和内容比较不同，应该使用!userMap.get(UserID).getUserPassword().equals(Password)
    public SignInResult SignIn(String UserID,String Password){
        SignInResult signInResult;
        if(!userMap.containsKey(UserID)){
            return signInResult=new SignInResult(SignInResult.SignInStatus.ID_NOT_FOUND,null);
        }else if(!userMap.get(UserID).getUserPassword().equals(Password)){
            return signInResult=new SignInResult(SignInResult.SignInStatus.PASSWORD_ERROR,null);
        }else{
            UserModel user=userMap.get(UserID);
            currentTaskMap=user.getTaskMap();
            currentUser=user;
            LoadTaskMap(user.getUserID());
            return signInResult=new SignInResult(SignInResult.SignInStatus.SUCCESS,user);
        }
    }

    //isIdAvailable方法用于判断ID是否可用，及ID是否已经存在，ID存在代表不可用.
    public TaskModel.TaskResult isIdAvailable(String ID) {
        TaskModel task = currentTaskMap.get(ID);
        if (task == null) {
            return TaskModel.TaskResult.SUCCESS;
        } else {
            return TaskModel.TaskResult.DUPLICATE_ID;
        }
    }

    //isIDExists方法用于判断ID是否存在，即ID是否已经存在，ID存在则返回SUCCESS，反之返回TASK_NOT_FOUND
    public TaskModel.TaskResult isIDExists(String ID) {
        TaskModel task = currentTaskMap.get(ID);
        if (task == null) {
            return TaskModel.TaskResult.TASK_NOT_FOUND;
        } else {
            return TaskModel.TaskResult.SUCCESS;
        }
    }

    //chooseNextOperation方法用于选择下一步操作，接收任务ID，并返回下一步操作。

    public int chooseNextOperation(int choice) {
        switch (choice) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            default:
                return -1;
        }
    }

    //createTask方法用于创建任务，接收任务ID和任务名称，并调用isIdAvailable方法判断ID是否可用，如果可用则创建任务，并返回创建成功。
    public CreateTaskResult createTask(String taskName){
        CreateTaskResult createTaskResult;
        if(taskName==null){
            return createTaskResult=new CreateTaskResult(CreateTaskResult.CreateTaskResultEnum.INVALID_INPUT,null);
        }
        TaskModel task=new TaskModel(taskName);
        currentTaskMap.put(task.getTaskID(), task);
        SaveTaskMap(currentUser.getUserID());
        return createTaskResult=new CreateTaskResult(CreateTaskResult.CreateTaskResultEnum.SUCCESS,task);
    }

    //QueryAllTasks方法用于查询所有任务，返回一个包含所有任务的列表。
    public HashMap<String,TaskModel> QueryAllTasks(){
        return currentTaskMap;
    }

    //QueryTask方法用于查询指定任务，接收任务ID，并返回指定任务的详细信息。
    public QueryTaskResult QueryTask(String taskID){
        QueryTaskResult queryTaskResult;
        if(taskID==null){
            return queryTaskResult=new QueryTaskResult(QueryTaskResult.QueryTaskResultEnum.INVALID_INPUT,null);
        }else if(isIDExists(taskID)==TaskModel.TaskResult.TASK_NOT_FOUND){
            return queryTaskResult=new QueryTaskResult(QueryTaskResult.QueryTaskResultEnum.ID_NOT_FOUND,null);
        }
        return queryTaskResult=new QueryTaskResult(QueryTaskResult.QueryTaskResultEnum.SUCCESS,currentTaskMap.get(taskID));
    }

    //StartTask方法用于开始任务，接收任务ID，并调用isIDExists方法判断ID是否存在，如果存在则开始任务，并返回开始成功。
    public StartTaskResult StartTask(String taskID){
        StartTaskResult startTaskResult;
        if(taskID==null){
            return startTaskResult=new StartTaskResult(StartTaskResult.StartTsakStatus.IllEGAL_ARGUMENT, null);
        }else if(isIDExists(taskID)==TaskModel.TaskResult.TASK_NOT_FOUND){
            return startTaskResult=new StartTaskResult(StartTaskResult.StartTsakStatus.TASK_NOT_FOUND, null);
        }else if(currentTaskMap.get(taskID).getStatus()==TaskModel.TaskStatus.IN_PROGRESS){
            return startTaskResult=new StartTaskResult(StartTaskResult.StartTsakStatus.TASK_ALREADY_STARTED,currentTaskMap.get(taskID));
        }else if(currentTaskMap.get(taskID).getStatus()==TaskModel.TaskStatus.DONE){
            return startTaskResult=new StartTaskResult(StartTaskResult.StartTsakStatus.TASK_ALREADY_COMPLETED, currentTaskMap.get(taskID));
        }
        currentTaskMap.get(taskID).setStatus(TaskModel.TaskStatus.IN_PROGRESS);
        SaveTaskMap(currentUser.getUserID());
        return startTaskResult=new StartTaskResult(StartTaskResult.StartTsakStatus.SUCCESS, currentTaskMap.get(taskID));
    }

    //CompleteTask方法用于完成任务，接收任务ID，并调用isIDExists方法判断ID是否存在，如果存在则完成任务，并返回完成成功。
    public CompleteTaskResult CompleteTask(String taskID){
        CompleteTaskResult completeTaskResult;
        if(taskID==null){
            return completeTaskResult=new CompleteTaskResult(CompleteTaskResult.CompleteTaskStatus.ILLEGAL_ARGUMENT, null);
        }else if(isIDExists(taskID)==TaskModel.TaskResult.TASK_NOT_FOUND){
            return completeTaskResult=new CompleteTaskResult(CompleteTaskResult.CompleteTaskStatus.TASK_NOT_FOUND, null);
        }else if(currentTaskMap.get(taskID).getStatus()==TaskModel.TaskStatus.TO_DO){
            return completeTaskResult=new CompleteTaskResult(CompleteTaskResult.CompleteTaskStatus.TASK_NOT_STARTED, currentTaskMap.get(taskID));
        }else if(currentTaskMap.get(taskID).getStatus()==TaskModel.TaskStatus.DONE){
            return completeTaskResult=new CompleteTaskResult(CompleteTaskResult.CompleteTaskStatus.TASK_ALREADY_COMPLETED, currentTaskMap.get(taskID));
        }
        currentTaskMap.get(taskID).setStatus(TaskModel.TaskStatus.DONE);
        SaveTaskMap(currentUser.getUserID());
        return completeTaskResult=new CompleteTaskResult(CompleteTaskResult.CompleteTaskStatus.SUCCESS, currentTaskMap.get(taskID));
    }

    //DeleteTask方法用于删除任务，接收任务ID，并调用isIDExists方法判断ID是否存在，如果存在则删除任务，并返回删除成功。
    public DeleteTaskResult DeleteTask(String taskID){
        DeleteTaskResult deleteTaskResult;
        if(taskID==null){
            return deleteTaskResult=new DeleteTaskResult(DeleteTaskResult.deleteTaskStatus.ILLEGAL_ARGUMENT, null);
        }else if(isIDExists(taskID)==TaskModel.TaskResult.TASK_NOT_FOUND){
            return deleteTaskResult=new DeleteTaskResult(DeleteTaskResult.deleteTaskStatus.TASK_NOT_FOUND, null);
        }
        TaskModel task=currentTaskMap.get(taskID);
        currentTaskMap.remove(taskID);
        SaveTaskMap(currentUser.getUserID());
        return deleteTaskResult=new DeleteTaskResult(DeleteTaskResult.deleteTaskStatus.SUCCESS, task);
    }
}
