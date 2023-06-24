package com.utospace.plugins.wstalk;

import hudson.model.AbstractBuild;
import hudson.model.Result;

/**
 * Enumeration of the possible build status for notification purposes.
 *
 * @author Ataxexe
 */
public enum BuildStatus {

    /**
     * Represents a build that has failed
     */
    BROKEN("Broken"),
    /**
     * Represents a build that has failed after a failed build
     */
    STILL_BROKEN("Still Broken"),
    /**
     * Represents a build that has succeeded after a failed build
     */
    FIXED("Fixed"),
    /**
     * Represents a build that has succeeded (if there is a previous build, it has been succeeded too)
     */
    SUCCESSFUL("Successful");

    private final String tag;

    BuildStatus(String tag) {
        this.tag = tag;
    }

    public String tag() {
        String tag = "latest"; // (new NotifierSettings()).alternativeMSG(this);
        if (tag != null && tag.length() > 0) {
            return tag;
        } else {
            return this.tag;
        }
    }

    /**
     * Returns the {@code BuildStatus} that represents the given build. If the given build has a previous build, it
     * must be finished.
     *
     * @param build the build to analyze
     * @return the status that represents the given build
     */
    public static BuildStatus of(AbstractBuild<?, ?> build) {
        AbstractBuild<?, ?> previousBuild = build.getPreviousBuild();
        if (build.getResult() == Result.SUCCESS) {
            if (previousBuild != null) {
                return previousBuild.getResult() == Result.SUCCESS ? SUCCESSFUL : FIXED;
            } else {
                return SUCCESSFUL;
            }
        } else {
            if (previousBuild != null) {
                return previousBuild.getResult() != Result.SUCCESS ? STILL_BROKEN : BROKEN;
            } else {
                return BROKEN;
            }
        }
    }

    @Override
    public String toString() {
        return "BuildStatus{" + tag + '}';
    }

}
