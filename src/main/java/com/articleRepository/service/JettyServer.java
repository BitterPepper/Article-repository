package com.articleRepository.service;

import com.articleRepository.controllers.RepositoryController;
import com.articleRepository.dao.MemoryArticleDao;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        WebAppContext context = new WebAppContext();
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*jstl.*\\.jar$");

        Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        context.setResourceBase("src/main/webapp");
        context.setWelcomeFiles(new String[]{"index.jsp"});

        ResourceConfig config = new ResourceConfig();
        injectDependencies(config);

        ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));
        context.addServlet(servletHolder, "/articleservice/*");
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static void injectDependencies(ResourceConfig config) {
        config.register(new RepositoryController(new MemoryArticleDao()));
    }
}
