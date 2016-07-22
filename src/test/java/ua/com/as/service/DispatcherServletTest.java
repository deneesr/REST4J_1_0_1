package ua.com.as.service;

import org.junit.Assert;
import org.junit.Test;
import ua.com.as.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatcherServletTest {

    private static final String METHOD_GET = "GET";
    private static final String PATH_GET = "/getTest";
    private static final String WRONG_PATH_GET = "/wrong_path_get";
    private static final String SERVLET_NAME = "mainservlet";
    private static final String WEB_INF = "WEB-INF";
    private static final String PATH_TO_WEB_INF = "src/test/resources/WEB-INF";
    private static final String PART_OF_FILE_NAME = "-mapping-cfg.xml";
    private static final String RESPONSE_GET_METHOD = "ControllerClassTest, method: getTest";

    @Test
    public void testInit() throws ServletException {
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(servletConfig.getServletName()).thenReturn(SERVLET_NAME);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext().getRealPath(WEB_INF)).thenReturn(PATH_TO_WEB_INF);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init(servletConfig);
        assertEquals(SERVLET_NAME, servlet.getServletName());
        assertEquals(PATH_TO_WEB_INF, servlet.getServletContext().getRealPath(WEB_INF));
    }

    @Test
    public void testInitParsingError() throws ServletException {
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext().getRealPath(WEB_INF)).thenReturn("test");

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init(servletConfig);
        assertNull(servlet.getServletName());
    }

    @Test
    public void testService() throws Exception {
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRealPath(WEB_INF)).thenReturn(PATH_TO_WEB_INF);
        String webInfPath = servletContext.getRealPath(WEB_INF);

        DispatcherServlet ds = mock(DispatcherServlet.class);
        when(ds.getServletName()).thenReturn(SERVLET_NAME);
        String servletName = ds.getServletName();

        File configFile = new File(webInfPath + "/" + servletName + PART_OF_FILE_NAME);
        HandlerMapper mapper = new HandlerMapperImpl();
        mapper.configure(configFile);

        ServletConfig servletConfig = mock(ServletConfig.class);
        when(ds.getServletConfig()).thenReturn(servletConfig);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn(PATH_GET);
        when(request.getMethod()).thenReturn(METHOD_GET);

        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStreamForTest servletOutputStream = new ServletOutputStreamForTest();
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ds = new DispatcherServlet();
        ds.service(request, response);

        byte[] data = servletOutputStream.baos.toByteArray();

        StringBuilder responseResult = new StringBuilder();

        for (byte b : data) {
            responseResult.append((char) b);
        }

        Assert.assertEquals(RESPONSE_GET_METHOD, responseResult.toString().trim());
    }

    @Test
    public void testServiceWrong() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getPathInfo()).thenReturn(WRONG_PATH_GET);
        when(request.getMethod()).thenReturn(METHOD_GET);

        DispatcherServlet ds = new DispatcherServlet();

        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ds.service(request, response);
    }
}

class ServletOutputStreamForTest extends ServletOutputStream {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void write(int i) throws IOException {
        baos.write(i);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }
}
