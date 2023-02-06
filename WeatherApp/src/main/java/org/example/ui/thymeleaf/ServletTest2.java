package org.example.ui.thymeleaf;

import jakarta.servlet.ServletConfig;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet(urlPatterns = "/weather",
        loadOnStartup = 2,
        name = "SimpleWeatherApp",
        description = "Pogodynka z kursu SDA, grupa IV")
public class ServletTest2 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private JakartaServletWebApplication app;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = getServletContext();
        this.app = JakartaServletWebApplication.buildApplication(servletContext);
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
            IWebExchange webExchange = app.buildExchange(request, response);

            //Nie przetwarzaj zasobów typu css, obrazy itd.
            if (request.getRequestURI().startsWith("/css") ||
                    request.getRequestURI().startsWith("/images") ||
                    request.getRequestURI().startsWith("/favicon")) {
                return false;
            }

            return true;

        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ignored) {

            }
            throw new ServletException(e);
        }

//        IWebExchange webExchange = app.buildExchange(request, response);
//        WebContext context = new WebContext(webExchange, request.getLocale());
//
//        context.setVariable("name", "Javax Name Test 2");
//        context.setVariable("date", "Javax Date Test 2");
//
//        ThymeleafEngine.getInstance().getContextVariables("index", context, response.getWriter());
    }
}
