package com.no9.app.ui;

import com.no9.app.adaptors.FOPRenderService;
import com.no9.app.services.RenderException;
import com.no9.app.services.RenderService;
import com.no9.app.services.TemplateID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class RetrievePDF extends HttpServlet {
    private RenderService renderService;

    private static final String XML_SOURCE_RESOURCE_NAME = "Hello.xml";
    private static final TemplateID XSLT_TEMPLATE_ID = new TemplateID("/HelloWorld.xsl");
    private static final String RESULT_FILE_NAME = "HelloWorld.pdf";

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + RESULT_FILE_NAME + "\"");

        try (InputStream inputStream = new FileInputStream(getResourceFile(XML_SOURCE_RESOURCE_NAME))) {
            getRenderService().toPDF(XSLT_TEMPLATE_ID, inputStream, resp.getOutputStream());
        } catch (FileNotFoundException|RenderException ex) {
            resp.setContentType("text/html");
            resp.getOutputStream().println("<h1>Exception</h1>");
            resp.getOutputStream().println(ex.getMessage());
        }
    }

    private File getResourceFile(String resourceName) {
        return new File(RetrievePDF.class.getClassLoader().getResource(resourceName).getFile());
    }

    private RenderService getRenderService() {
        if (renderService == null) {
            renderService = new FOPRenderService(getServletContext());
        }
        return renderService;
    }
}