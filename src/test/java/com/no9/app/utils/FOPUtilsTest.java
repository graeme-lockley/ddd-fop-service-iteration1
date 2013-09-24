package com.no9.app.utils;

import com.no9.app.services.TemplateID;
import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.Templates;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.Assert.*;

public class FOPUtilsTest {
    private FOPUtils fopUtils;
    private static final TemplateID XSLT_TEMPLATE_ID = new TemplateID("resource://HelloWorld.xsl");

    @Before
    public void setUp() throws Exception {
//        servletContext = new MockUp<ServletContext>() {
//            @Mock
//            public InputStream getResourceAsStream(String resourceName) {
//                numberOfCallsToGetResourceAsStream += 1;
//                try {
//                    return new FileInputStream("src/main/webapp" + resourceName);
//                } catch (FileNotFoundException e) {
//                    return null;
//                }
//            }
//        }.getMockInstance();
        FOPUtils.resetCache();
        fopUtils = new FOPUtils();
    }

    @Test
    public void should_get_an_XML_template() throws Exception {
        Templates template = fopUtils.getXSLTemplate(XSLT_TEMPLATE_ID.getTemplateURI());

        assertNotNull(template);
    }

    @Test
    public void should_show_that_the_template_is_pulled_out_of_the_cache() throws Exception {
        Templates firstTemplate = fopUtils.getXSLTemplate(XSLT_TEMPLATE_ID.getTemplateURI());
        Templates secondTemplate = fopUtils.getXSLTemplate(XSLT_TEMPLATE_ID.getTemplateURI());

        assertEquals(firstTemplate, secondTemplate);
    }

    @Test(expected = javax.xml.transform.TransformerException.class)
    public void should_raise_an_exception_for_an_unknown_template() throws Exception {
        fopUtils.getXSLTemplate("resource://Bob.xsl");
    }

    @Test
    public void should_render_the_XML_content_using_the_XSLT() throws Exception {
        Templates template = fopUtils.getXSLTemplate(XSLT_TEMPLATE_ID.getTemplateURI());
        StreamSource xmlSource = xmlAsStreamSource(getResourceFile("Hello.xml"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        fopUtils.xmlToPDF(xmlSource, template, outputStream);

        String pdfOutput = outputStream.toString();
        assertTrue(pdfOutput.startsWith("%PDF-1.4"));
    }

    private StreamSource xmlAsStreamSource(File xmlFile) {
        return new StreamSource(xmlFile);
    }

    private File getResourceFile(String resourceName) {
        return new File(FOPUtilsTest.class.getClassLoader().getResource(resourceName).getFile());
    }
}

