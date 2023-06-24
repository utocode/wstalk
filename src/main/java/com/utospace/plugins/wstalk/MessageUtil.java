package com.utospace.plugins.wstalk;

import com.fasterxml.jackson.core.JsonProcessingException;
import hudson.model.*;
import hudson.scm.ChangeLogSet;
import hudson.scm.SCM;
import jenkins.triggers.SCMTriggerItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MessageUtil {

    private static final Logger logger = Logger.getLogger(MessageUtil.class.getName());

    private MessageUtil() {
    }

    public static final Message makeMessage(AbstractBuild<?, ?> build, String status) throws JsonProcessingException {
        AbstractProject<?, ?> job = build.getProject();
        logger.info(build.toString());
        logger.info(job.toString());

        Message message = new Message();
        CauseAction causeAction = build.getAction(CauseAction.class);
        Map<String, String> builds = build.getBuildVariables();

        ParametersAction parameterAction = build.getAction(ParametersAction.class);
        Map<String, String> paramsMap = new HashMap<>();
        if (parameterAction != null) {
            List<ParameterValue> param1 = parameterAction.getAllParameters();
            param1.forEach(it -> paramsMap.put(it.getName(), it.getFormattedDescription()));
//            ObjectMapper objectMapper = new ObjectMapper();
//            String params = objectMapper.writeValueAsString(paramsMap);
//            logger.info(params);
        }

        message.setJob(job.getName());
        message.setCauseAction(causeAction);
        message.setParamsMap(paramsMap);
        message.setBuilds(builds);
        message.setUrl(job.getAbsoluteUrl());
        message.setIconColor(build.getIconColor().toString());
        message.setNumber(build.getNumber());
        message.setStatus(status == null ? "" : status);
        message.setResult(build.getResult() == null ? "" : build.getResult().toString());
        message.setDuration(build.getTimestampString());
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    public static final Message makeMessage(AbstractProject<?, ?> project, String status) {
        logger.info("project: " + project.toString());

        Message message = new Message();
        message.setJob(project.getName());
        message.setUrl(project.getAbsoluteUrl());
        message.setIconColor(project.getIconColor().toString());
        message.setNumber(project.getNextBuildNumber() - 1);
        message.setStatus(status == null ? "" : status);
        message.setResult(status);
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    public static final Message makeJobMessage(Job<?, ?> job, String status) {
        logger.info("job: " + job.toString());

        Message message = new Message();
        message.setJob(job.getName());
        message.setUrl(job.getAbsoluteUrl());
        message.setIconColor(job.getIconColor().toString());
        message.setNumber(job.getNextBuildNumber() - 1);
        message.setStatus(status == null ? "" : status);
        message.setResult(status);
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    public static final Message makeMessage(Run<?, ?> run, String statusMessage) throws JsonProcessingException {
        Message message = null;

        Result jobStatus = run.getResult();
        logger.info("jobStatus: " + jobStatus);

        Item parentItem = run.getParent();
        logger.info("run: " + run);
        logger.info("run.getParent: " + parentItem);
        if (parentItem instanceof AbstractBuild) {
            message = makeMessage((AbstractBuild<?, ?>) parentItem, jobStatus.toString());
        } else if (parentItem instanceof Job) {
            message = makeJobMessage((Job<?, ?>) parentItem, jobStatus.toString());
            message.setDuration(run.getTimestampString());
        } else {
            message = makeMessage((AbstractBuild<?, ?>) run, jobStatus == null ? "???" : jobStatus.toString());
        }

        ParametersAction parametersAction = run.getAction(ParametersAction.class);
        if (parametersAction != null && parametersAction.getParameters() != null) {
            message.setParameterAction(parametersAction);
        }

//        Collection<? extends SCM> scm = getSCM(run.getParent());
        for (Action action : run.getAllActions()) {
            if (action instanceof ChangeLogSet) {
                ChangeLogSet<?> changeLogSet = (ChangeLogSet<?>) action;
                for (ChangeLogSet.Entry entry : changeLogSet) {
                    String commitId = entry.getCommitId();
                    String author = entry.getAuthor().getFullName();
                    String msg = entry.getMsg();
                }
                message.setChangeLogSet(((ChangeLogSet) action));
            } else if (action instanceof CauseAction) {
                message.setCauseAction(((CauseAction) action));
            }
        }

        return message;
    }

    public static final Collection<? extends SCM> getSCM(Job<?, ?> job) {
        Collection<? extends SCM> scm = null;
        SCMTriggerItem scmTriggerItem = SCMTriggerItem.SCMTriggerItems.asSCMTriggerItem(job);
        if (scmTriggerItem != null) {
            scm = scmTriggerItem.getSCMs();
        }
        return scm;
    }

}