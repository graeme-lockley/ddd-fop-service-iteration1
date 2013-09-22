package com.no9.app.adaptors;

import com.no9.app.services.RenderException;
import com.no9.app.services.RenderService;
import com.no9.app.services.TemplateID;
import com.no9.app.utils.FOPUtils;
import org.apache.fop.apps.FOPException;

import javax.servlet.ServletContext;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FOPRenderService implements RenderService {
    private FOPUtils fopUtils;

    public FOPRenderService(ServletContext servletContext) {
        fopUtils = new FOPUtils(servletContext);
    }

    @Override
    public void toPDF(TemplateID templateID, InputStream source, OutputStream output) throws RenderException {
        try {
            fopUtils.xmlToPDF(getXmlSource(source), getXslTemplate(templateID), output);
        } catch (FOPException | IOException | TransformerException ex) {
            throw new RenderException(ex);
        }
    }

    private Templates getXslTemplate(TemplateID templateID) throws TransformerException {
        return fopUtils.getXSLTemplate(templateID.getTemplateURI());
    }

    private StreamSource getXmlSource(InputStream source) {
        return new StreamSource(source);
    }
}
