package org.jenkinsci.plugins.sample;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.*;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;

import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;


public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String url;

    @DataBoundConstructor
    public HelloWorldBuilder(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", "Jenkins");
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
            + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        String responseBody = result.toString();

        JSONObject obj = new JSONObject(responseBody);
        String responseMessage = obj.getString("message");

        listener.getLogger().println("Api respons message: " + responseMessage);
    }

    @Symbol("api")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }
    }

}
