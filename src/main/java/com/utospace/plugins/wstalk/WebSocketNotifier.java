//package com.utospace.plugins.wstalk;
//
//import java.io.IOException;
//import java.net.URI;
//
//import javax.servlet.ServletException;
//
//import org.glassfish.tyrus.server.Server;
//import org.kohsuke.stapler.DataBoundConstructor;
//import org.kohsuke.stapler.QueryParameter;
//import org.kohsuke.stapler.StaplerRequest;
//
//import hudson.Extension;
//import hudson.Launcher;
//import hudson.model.AbstractBuild;
//import hudson.model.AbstractProject;
//import hudson.model.Build;
//import hudson.model.BuildListener;
//import hudson.tasks.BuildStepDescriptor;
//import hudson.tasks.BuildStepMonitor;
//import hudson.tasks.Notifier;
//import hudson.tasks.Publisher;
//import hudson.util.FormValidation;
//import jakarta.websocket.DeploymentException;
//import jakarta.websocket.EncodeException;
//import jakarta.websocket.Session;
//import net.sf.json.JSONObject;
//
//public class WebSocketNotifier extends Notifier {
//
//    private static final String APP_NAME = "WebSocket Notifier";
//
//    private static int DEFAULT_SERVER_PORT = 9092;
//
//    private static final String START = "START";
//
//    private Server wsServer = null;
//
//    private Session client = null;
//
//    @DataBoundConstructor
//    public WebSocketNotifier() {
//        initialized();
//    }
//
//    public void initialized() {
//        if (wsServer == null) {
//            wsServer = new Server("localhost", DEFAULT_SERVER_PORT, "/", null, JenkinsEndpoint.class);
//        }
//        try {
//            wsServer.start();
//            client = connectToClient();
//        } catch (DeploymentException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {
//        String target = null;
//        BuildStatus status = BuildStatus.of(build);
//
//        try {
//            Message message = MessageUtil.makeMessage(build, null, status);
//            client.getBasicRemote().sendObject(message);
//        } catch (IOException | EncodeException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean prebuild(Build build, BuildListener listener) {
//        boolean useStatusFormat = getDescriptor().useStatusFormat();
//        if (useStatusFormat) {
////            WsServer.send(build, START, useStatusFormat);
//        }
//        try {
//            BuildStatus status = BuildStatus.of(build);
//            Message message = MessageUtil.makeMessage(build, "START", status);
//            client.getBasicRemote().sendObject(message);
//        } catch (IOException | EncodeException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public BuildStepMonitor getRequiredMonitorService() {
//        return BuildStepMonitor.BUILD;
//    }
//
//    @Override
//    public WebSocketDescriptor getDescriptor() {
//        return (WebSocketDescriptor) super.getDescriptor();
//    }
//
//    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
//    public static final class WebSocketDescriptor extends BuildStepDescriptor<Publisher> {
//
//        private int port = 9090;
//        private boolean useStatusFormat = false;
//        private int pingInterval = 20;
//
//        public int port() {
//            return port;
//        }
//
//        public boolean useStatusFormat() {
//            return useStatusFormat;
//        }
//
//        public boolean keepalive() {
//            return pingInterval >= 0;
//        }
//
//        public int pingInterval() {
//            if (keepalive())
//                return pingInterval;
//            else
//                return 20;
//        }
//
//        public WebSocketDescriptor() {
//            load();
//        }
//
//        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
//            // Indicates that this builder can be used with all kinds of project types
//            return true;
//        }
//
//        public String getDisplayName() {
//            return APP_NAME;
//        }
//
//        public FormValidation doCheckPort(@QueryParameter String value) throws IOException, ServletException {
//            try {
//                Integer.parseInt(value);
//                return FormValidation.ok();
//            } catch (NumberFormatException ex) {
//                return FormValidation.error("invalid port number");
//            }
//        }
//
//        @Override
//        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
//            port = formData.getInt("port");
//            useStatusFormat = formData.getBoolean("useStatusFormat");
//            if (formData.has("keepalive")) {
//                JSONObject keepalive = formData.getJSONObject("keepalive");
//                pingInterval = keepalive.getInt("pingInterval");
//            } else {
//                pingInterval = -1;
//            }
//
//            save();
////            WsServer.reset(port, pingInterval);
//            return super.configure(req, formData);
//        }
//    }
//
//    private static Session connectToClient() {
//        // Replace the URI with the actual WebSocket URL of the client
//        URI clientURI = URI.create("ws://localhost:" + DEFAULT_SERVER_PORT + "/jenkins");
//        WebSocketClientEndpoint clientEndpoint = new WebSocketClientEndpoint();
//
//        try {
//            return clientEndpoint.connect(clientURI);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
