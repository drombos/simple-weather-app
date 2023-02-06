package org.example.ui.thymeleaf;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.App;
import org.example.Main;
import org.example.handler.AddLocationHandler;
import org.example.handler.DisplayLocationsHandler;
import org.example.handler.DownloadForecastsHandler;
import org.example.ui.submenu.AddLocationUI;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"", "/add", "/display", "/forecast"},
        loadOnStartup = 1)
public class WeatherServlet extends HttpServlet {
    private App app = App.getInstance();
    private JakartaServletWebApplication servlet;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = getServletContext();
        this.servlet = JakartaServletWebApplication.buildApplication(servletContext);

        Main.main(new String[0]);
        app = App.getInstance();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        process(request, response);
    }

    private boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            IWebExchange webExchange = servlet.buildExchange(request, response);
            IWebRequest webRequest = webExchange.getRequest();

            String requestPath = webRequest.getPathWithinApplication();
            // Path within application might contain the ";jsessionid" fragment due to URL rewriting
            int fragmentIndex = requestPath.indexOf(';');
            if (fragmentIndex >= 0) {
                requestPath = requestPath.substring(0, fragmentIndex);
            }

            Path path = Path.from(requestPath);
            if (path == null) {
                app.invalidCommand();
                return false;
            }

            ContextParametersSource contextSource = null;
            switch (path) {
                case ADD -> {
                    AddLocationUI ui = app.getUI().getAddLocationMenu();
                    GETParametersProcessor GETProcessor = (GETParametersProcessor) ui;
                    GETProcessor.takeGETParameters(webRequest);
                    contextSource = (ContextParametersSource) ui;
                    app.performAction(AddLocationHandler.class);
                }
                case DISPLAY -> {
                    contextSource = (ContextParametersSource) app.getUI().getDisplayLocationsMenu();
                    app.performAction(DisplayLocationsHandler.class);
                }
                case FORECAST -> {
                    contextSource = (ContextParametersSource) app.getUI().getDownloadForecastsMenu();
                    app.performAction(DownloadForecastsHandler.class);
                }
                case INDEX -> contextSource = context -> new HashMap<>();
            }

            if (contextSource == null) {
                app.invalidCommand();
                return false;
            }

            WebContext context = new WebContext(webExchange, request.getLocale());
            context.setVariables(contextSource.getContextVariables(context));

            ThymeleafEngine.getInstance().process(path.template, context, response.getWriter());
//            context.setVariable("name", "someName");
//            context.setVariable("date", "someDate");

//            ThymeleafEngine.getInstance().process("add", context, response.getWriter());
            return true;

        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ignored) {

            }
            throw new ServletException(e);
        }
    }

}
