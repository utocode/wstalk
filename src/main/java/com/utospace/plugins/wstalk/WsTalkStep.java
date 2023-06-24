package com.utospace.plugins.wstalk;

import edu.umd.cs.findbugs.annotations.Nullable;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class WsTalkStep extends Step implements Serializable {

    private static final Logger logger = Logger.getLogger(WsTalkStep.class.getName());

    private String message;

    /**
     * Creates a new instance of {@link WsTalkStep}.
     */
    @DataBoundConstructor
    public WsTalkStep(String message) {
        super();
        this.message = message;
    }

    @Override
    public StepExecution start(final StepContext context) {
        return new Execution(context, this);
    }

    public String getMessage() {
        return message;
    }

    /**
     * Actually performs the execution of the associated step.
     */
    static class Execution extends StepExecution {

        private WsTalkStep wsTalkStep;

        Execution(@Nonnull final StepContext context, final WsTalkStep step) {
            super(context);
            this.wsTalkStep = step;
        }

        @Override
        public boolean start() throws Exception {
            StepContext context = getContext();
            Run<?, ?> run = context.get(Run.class);
            TaskListener listener = getContext().get(TaskListener.class);

            String jobName = run.getParent().getName();
            long executionTime = run.getDuration();
            logger.info("Execution Time: " + executionTime + "ms");

            Message message = MessageUtil.makeMessage(run, wsTalkStep.getMessage());
            WsServer.send(message);
            getContext().onSuccess(message);
            return true;
        }

    }

    /**
     * Descriptor for this step: defines the context and the UI labels.
     */
    @Extension
    public static class Descriptor extends StepDescriptor {

        @Override
        public String getFunctionName() {
            return "wstalk";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return WsNotifier.APP_NAME;
        }

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            Set<Class<?>> requiredContext = new HashSet<>();
            requiredContext.add(TaskListener.class);
            requiredContext.add(Run.class); // Required to access the job
            return requiredContext;
        }
    }

}
