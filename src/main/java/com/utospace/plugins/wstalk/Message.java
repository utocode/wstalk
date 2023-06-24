package com.utospace.plugins.wstalk;

import hudson.model.CauseAction;
import hudson.model.ParametersAction;
import hudson.scm.ChangeLogSet;

import java.util.Map;
import java.util.SortedMap;

public class Message {

    private String job;

    private Integer number;

    private String iconColor;

    private String url;

    private Map<String, String> builds;

    private CauseAction causeAction;

    private Map<String, String> paramsMap;

    private ParametersAction parameterAction;

    private ChangeLogSet<?> changeLogSet;

    private String status;

    private String result;

    private String commitId;

    private String author;

    private String message;

    private String duration;

    private long timestamp;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getBuilds() {
        return builds;
    }

    public void setBuilds(Map<String, String> builds) {
        this.builds = builds;
    }

    public CauseAction getCauseAction() {
        return causeAction;
    }

    public void setCauseAction(CauseAction causeAction) {
        this.causeAction = causeAction;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public ParametersAction getParameterAction() {
        return parameterAction;
    }

    public void setParameterAction(ParametersAction parameterAction) {
        this.parameterAction = parameterAction;
    }

    public ChangeLogSet<?> getChangeLogSet() {
        return changeLogSet;
    }

    public void setChangeLogSet(ChangeLogSet<?> changeLogSet) {
        this.changeLogSet = changeLogSet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
