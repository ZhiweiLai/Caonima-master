package com.example.zhiweilai.caonima;

/**
 * Created by zhiweilai on 2017-11-27.
 */

public class Task {

    private String taskName;
    private String creator;
    private String recipent;
    private String startDate;
    private String deadline;
    private String note;
    private String equipment;
    private String points;


    private String complete="No";

    public Task() {
    }



    public Task(String taskName, String creator, String recipent, String startDate, String deadline, String note,
                String equipment, String points) {
        super();
        this.taskName = taskName;
        this.creator = creator;
        this.recipent = recipent;
        this.startDate = startDate;
        this.deadline = deadline;
        this.note = note;
        this.equipment = equipment;
        this.points = points;
    }


    public boolean isRecipent(String name) {

        return this.recipent.equals(name);
    }

    public boolean isCreator(String name) {
        return this.creator.equals(name);
    }



    @Override
    public String toString() {
        return "Task [taskName=" + taskName + ", creator=" + creator + ", recipent=" + recipent + ", startDate="
                + startDate + ", deadline=" + deadline + ", note=" + note + ", equipment=" + equipment + ", points="
                + points + ", complete=" + complete + ", acccpted=" +  "]";
    }


    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getRecipent() {
        return recipent;
    }
    public void setRecipent(String recipent) {
        this.recipent = recipent;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getEquipment() {
        return equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    public String isComplete() {
        return complete;
    }
    public void setComplete(String complete) {
        this.complete = complete;
    }






}

