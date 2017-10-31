package org.jenkinsci.plugins.builder;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import hudson.model.*;
import hudson.remoting.VirtualChannel;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.jenkinsci.plugins.domain.Book;
import org.jenkinsci.plugins.domain.BookJsonConvertor;
import org.jenkinsci.plugins.domain.BooksResource;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;

import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String url;

    private final BookJsonConvertor bookJsonConvertor = new BookJsonConvertor();

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

        request.addHeader("User-Agent", "Jenkins");
        HttpResponse response = client.execute(request);

        listener.getLogger().printf("Response Code : %s \n", response.getStatusLine().getStatusCode());

        String jsonStr = EntityUtils.toString(response.getEntity());

        BooksResource booksResource = bookJsonConvertor.readBooks(jsonStr);
        final List<Book> books = booksResource.getBooks();
        final boolean error = booksResource.isErr();

        listener.getLogger().println("API response");
        for(Book book : books) {
            listener.getLogger().println(book.toString());
        }
        listener.getLogger().printf("Status error: %s\n", error);

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("reporting.template");

        StringWriter writer = new StringWriter();

        mustache.execute(writer, Collections.singletonMap("books", books)).flush();

        FilePath p = new FilePath(workspace, "report.html");
        p.write(writer.toString(), "UTF-8");

        listener.getLogger().printf("Report to: %s\n", p.toURI());
    }

    @Override
    public Collection<? extends Action> getProjectActions(AbstractProject<?,?> project) {
        List<Action> actions = new ArrayList<>();
        actions.add(new MyProjectAction());

        return actions;
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
