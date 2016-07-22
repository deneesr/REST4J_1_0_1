package ua.com.as;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ParsingException;
import ua.com.as.service.HandlerMapper;
import ua.com.as.service.HandlerMapperImpl;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * <p>The DispatcherServlet is an actual Servlet (inherits from the GenericServlet base class),
 * that dispatches requests to handlers. Need to be declared in web.xml of web application.
 *
 * @see HandlerMapper
 */
public class DispatcherServlet extends GenericServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DispatcherServlet.class);
    private HandlerMapper mapper = new HandlerMapperImpl();

    /**
     * <p>Servlet initialization method. Constructs a new instance
     * of {@link HandlerMapper} class and prepared <code>config file</code>.
     *
     * @throws ServletException when servlet encounters difficulty {@link ServletException }.
     */
    @Override
    public void init() throws ServletException {
        try {
            String webInfPath = this.getServletContext().getRealPath("WEB-INF");
            LOG.debug("WebInfPath: {}", webInfPath);
            String servletName = this.getServletName();
            LOG.debug("ServletName: {}", servletName);
            File configFile = new File(webInfPath + "/" + servletName + "-mapping-cfg.xml");
            LOG.debug("ConfigFile: {}", configFile);
            mapper.configure(configFile);
        } catch (ParsingException e) {
            LOG.error("Parsing error", e);
        }
    }

    /**
     * <p>This method is called by the servlet container to allow the servlet to respond to a request.
     * Sends request to {@link HandlerMapper}.
     *
     * @param request  the ServletRequest object that contains the client's request
     * @param response the ServletResponse object that will contain the servlet's response
     * @throws ServletException when servlet encounters difficulty {@link ServletException}
     * @throws IOException      when an I/O exception has occurred {@link IOException}
     */
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LOG.debug("Request is received");

        String path = httpRequest.getPathInfo();
        LOG.debug("Path: {}", path);
        String httpMethodName = httpRequest.getMethod();
        LOG.debug("HttpMethod: {}", httpMethodName);

        try {
            String result = mapper.getRestResponse(httpRequest, path, httpMethodName);
            response.getOutputStream().println(result);
            LOG.debug("Response is sent\n");
        } catch (RuntimeException e) {
            LOG.error("Error", e);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND, "source not found");
        }
    }
}
