package com.no9.app.adaptors;

import com.no9.app.services.RenderException;
import com.no9.app.services.RenderService;
import com.no9.app.services.TemplateID;
import com.no9.app.utils.FOPUtils;
import org.apache.fop.apps.FOPException;

import javax.servlet.ServletContext;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import static org.apache.commons.io.IOUtils.closeQuietly;

public class FOPRenderService implements RenderService {
    private FOPUtils fopUtils;

    public FOPRenderService(ServletContext servletContext) {
        fopUtils = new FOPUtils(servletContext);
    }

    @Override
    public void toPDF(TemplateID templateID, InputStream source, OutputStream output) throws RenderException {
        try {
            fopUtils.xmlToPDF(new StreamSource(source), fopUtils.getXSLTemplate(templateID.getTemplateURI()), output);
        } catch (FOPException|IOException|TransformerException ex) {
            throw new RenderException(ex);
         }
    }

    @Override
    public void toPDF(TemplateID templateID, File sourceFile, OutputStream output) throws RenderException {
        InputStream source = null;
        try {
            source = new FileInputStream(sourceFile);
            toPDF(templateID, source, output);
        } catch (FileNotFoundException ex) {
            throw new RenderException(ex);
        } finally {
            closeQuietly(source);
        }
    }
}
