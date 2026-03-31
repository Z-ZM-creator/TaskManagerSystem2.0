package com.zzm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.zzm.Result.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Service service = new Service();
        while (true) {
            service.LoadUserMap("userList.txt");
            System.out.println("Welcome use Task Manager System(2.0)");
            System.out.println("1.Sign up");
            System.out.println("2.Sign in");
            System.out.println("3.Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    SignUpInteraction(service, sc);
                    break;
                case 2:
                    SignInInteraction(service, sc);
                    break;
                case 3:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    // SignUpInteraction用于处理用户注册的交互逻辑，接收用户输入的用户名和密码，并调用Service类的SignUp方法进行注册
    public static void SignUpInteraction(Service service, Scanner sc) {
        while (true) {
            String UserName, Password;
            System.out.println("Please input your UserName:");
            UserName = sc.next();
            System.out.println("Please input your Password:");
            Password = sc.next();
            SignUpResult result = service.SignUp(UserName, Password);
            SignUpResult.SignUpStatus status = result.getStatus();
            if (status == SignUpResult.SignUpStatus.SUCCESS) {
                System.out.println("Sign up successfully!");
                System.out.println("Your UserName is: " + result.getUser().getUserName() + ", Your Password is: " + result.getUser().getUserPassword());
                break;
            }
            if (status == SignUpResult.SignUpStatus.ILLEGAL_ARGUMENT) {
                System.out.println("Sign up failed!Fucking Illegal Argument!");
            }
        }
    }

    //SignInInteraction用于处理用户登录的交互逻辑，接收用户输入的用户名和密码，并调用Service类的SignIn方法进行登录
    public static void SignInInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your ID:");
            String UserID = sc.next();
            System.out.println("Please input your Password:");
            String Password = sc.next();
            SignInResult result = service.SignIn(UserID, Password);
            SignInResult.SignInStatus status = result.getStatus();
            if (status == SignInResult.SignInStatus.SUCCESS) {
                System.out.println("Sign in successfully!");
                while (true) {
                    System.out.println("Choose your next operation:");
                    System.out.println("1.Create Task");
                    System.out.println("2.Query All Task");
                    System.out.println("3.Query Task By ID");
                    System.out.println("4.Start Task");
                    System.out.println("5.Complete Task");
                    System.out.println("6.Delete Task");
                    System.out.println("7.Save Task");
                    System.out.println("8.Exit");
                    int choice = sc.nextInt();
                    int judge = service.chooseNextOperation(choice);
                    if (judge == 1) {
                        CreateTaskInteraction(service, sc);
                    } else if (judge == 2) {
                        QueryAllTaskInteraction(service, sc);
                    } else if (judge == 3) {
                        QueryTaskByIDInteraction(service, sc);
                    } else if (judge == 4) {
                        StartTaskInteraction(service, sc);
                    } else if (judge == 5) {
                        CompleteTaskInteraction(service, sc);
                    } else if (judge == 6) {
                        DeleteTaskInteraction(service, sc);
                    } else if (judge == 7) {
                        //SaveTaskInteraction(service, sc);
                    } else {
                        System.out.println("Bye!");
                        break;
                    }
                }
                break;
            } else if (status == SignInResult.SignInStatus.ID_NOT_FOUND) {
                System.out.println("ID couldn't be found!");
                System.out.println("Please sign up first!");
                SignUpInteraction(service, sc);
            } else if (status == SignInResult.SignInStatus.PASSWORD_ERROR) {
                System.out.println("Password error!");
            }
        }
    }

    //CreateTaskInteraction用于处理用户创建任务的交互逻辑，接收用户输入的任务名和任务描述，并调用Service类的CreateTask方法进行创建
    public static void CreateTaskInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your task name:");
            String taskName = sc.next();
            CreateTaskResult result = service.createTask(taskName);
            CreateTaskResult.CreateTaskResultEnum status = result.getStatus();
            if (status == CreateTaskResult.CreateTaskResultEnum.INVALID_INPUT) {
                System.out.println("Invalid input!");
            } else if (status == CreateTaskResult.CreateTaskResultEnum.SUCCESS) {
                System.out.println("Create task successfully!");
                System.out.println("Your task ID is: " + result.getTask().getTaskID());
                System.out.println("Your task name is: " + result.getTask().getTaskName());
                break;
            }
        }

    }

    //QueryAllTaskInteraction用于处理用户查询任务的交互逻辑，并调用Service类的QueryAllTask方法进行查询
    public static void QueryAllTaskInteraction(Service service, Scanner sc) {
        System.out.println("All tasks:");
        HashMap<String, TaskModel> showMap = service.QueryAllTasks();
        for (String key : showMap.keySet()) {
            TaskModel taskToShow = showMap.get(key);
            System.out.println(taskToShow.toString());
        }

    }

    //QueryTaskByIDInteraction用于处理用户查询任务的交互逻辑，接收用户输入的任务ID，并调用Service类的QueryTaskByID方法进行查询
    public static void QueryTaskByIDInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your task ID:");
            String taskID = sc.next();
            QueryTaskResult result = service.QueryTask(taskID);
            QueryTaskResult.QueryTaskResultEnum status = result.getQueryTaskResultEnum();
            if (status == QueryTaskResult.QueryTaskResultEnum.ID_NOT_FOUND) {
                System.out.println("ID not found!");
            } else if (status == QueryTaskResult.QueryTaskResultEnum.INVALID_INPUT) {
                System.out.println("Invalid input!");
            } else if (status == QueryTaskResult.QueryTaskResultEnum.SUCCESS) {
                System.out.println("Query task successfully!");
                System.out.println(result.getTask().toString());
                break;
            }

        }

    }

    //StartTaskInteraction用于处理用户开始任务的交互逻辑，接收用户输入的任务Name，并调用Service类的StartTask方法进行开始任务
    public static void StartTaskInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your task ID:");
            String taskID = sc.next();
            StartTaskResult result = service.StartTask(taskID);
            StartTaskResult.StartTsakStatus status = result.getStatus();
            TaskModel task = result.getTask();
            if (status == StartTaskResult.StartTsakStatus.TASK_NOT_FOUND) {
                System.out.println("Task not found!");
            } else if (status == StartTaskResult.StartTsakStatus.IllEGAL_ARGUMENT) {
                System.out.println("Invalid input!");
            } else if (status == StartTaskResult.StartTsakStatus.TASK_ALREADY_STARTED) {
                System.out.println("Task" + task.getTaskName() + "already started!");
            } else if (status == StartTaskResult.StartTsakStatus.TASK_ALREADY_COMPLETED) {
                System.out.println("Task" + task.getTaskName() + "already completed!");
            } else if (status == StartTaskResult.StartTsakStatus.SUCCESS) {
                System.out.println("Start task successfully!");
                System.out.println("Your task ID is: " + task.getTaskID());
                System.out.println("Your task name is: " + task.getTaskName());
                System.out.println("Your task status is: " + task.getStatus());
                break;
            }
        }
    }

    //CompleteTaskInteraction用于处理用户完成任务的交互逻辑，接收用户输入的任务ID，并调用Service类的CompleteTask方法进行完成任务
    public static void CompleteTaskInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your task ID:");
            String taskID = sc.next();
            CompleteTaskResult result = service.CompleteTask(taskID);
            CompleteTaskResult.CompleteTaskStatus status = result.getStatus();
            TaskModel task = result.getTask();
            if (status == CompleteTaskResult.CompleteTaskStatus.ILLEGAL_ARGUMENT) {
                System.out.println("Invalid input!");
            } else if (status == CompleteTaskResult.CompleteTaskStatus.TASK_NOT_FOUND) {
                System.out.println("Task not found!");
            } else if (status == CompleteTaskResult.CompleteTaskStatus.TASK_NOT_STARTED) {
                System.out.println("Task" + task.getTaskName() + "not started yet!");
            } else if (status == CompleteTaskResult.CompleteTaskStatus.TASK_ALREADY_COMPLETED) {
                System.out.println("Task" + task.getTaskName() + "already completed!");
            } else if (status == CompleteTaskResult.CompleteTaskStatus.SUCCESS) {
                System.out.println("Complete task successfully!");
                System.out.println("Your task ID is: " + task.getTaskID());
                System.out.println("Your task name is: " + task.getTaskName());
                System.out.println("Your task status is: " + task.getStatus());
                break;
            }
        }
    }

    //DeleteTaskInteraction用于处理用户删除任务的交互逻辑，接收用户输入的任务ID，并调用Service类的DeleteTask方法进行删除任务
    public static void DeleteTaskInteraction(Service service, Scanner sc) {
        while (true) {
            System.out.println("Please input your task ID:");
            String taskID = sc.next();
            DeleteTaskResult result = service.DeleteTask(taskID);
            DeleteTaskResult.deleteTaskStatus status = result.getStatus();
            if (status == DeleteTaskResult.deleteTaskStatus.ILLEGAL_ARGUMENT) {
                System.out.println("Invalid input!");
            } else if (status == DeleteTaskResult.deleteTaskStatus.TASK_NOT_FOUND) {
                System.out.println("Task not found!");
            } else if (status == DeleteTaskResult.deleteTaskStatus.SUCCESS) {
                System.out.println("Delete task successfully!");
                System.out.println("Your task ID is: " + result.getTask().getTaskID());
                System.out.println("Your task name is: " + result.getTask().getTaskName());
                System.out.println("Your task status is: " + result.getTask().getStatus());
                break;
            }
        }
    }

    //SaveTaskInteraction用于处理用户保存任务的交互逻辑，接收用户输入的任务ID，并调用Service类的SaveTask方法进行保存任务
    public static void SaveTaskInteraction(Service service, Scanner sc) {
        System.out.println("Enter your ID");
        String userID = sc.next();
        service.SaveTaskMap(userID);
    }
}