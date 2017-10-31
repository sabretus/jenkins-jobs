package org.jenkinsci.plugins.builder;

import hudson.model.ProminentProjectAction;

public class MyProjectAction implements ProminentProjectAction {

    @Override
    public String getIconFileName() {
        // return the path to the icon file
        return "/images/jenkins.png";
    }

    @Override
    public String getDisplayName() {
        // return the label for your link
        return "API Report";
    }

    @Override
    public String getUrlName() {
        // defines the suburl, which is appended to ...jenkins/job/jobname
        return "ws/report.html";
    }
}