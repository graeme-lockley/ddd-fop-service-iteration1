package com.no9.app.services;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface RenderService {
    void toPDF(TemplateID templateID, InputStream source, OutputStream output) throws RenderException;

    void toPDF(TemplateID templateID, File sourceFile, OutputStream output) throws RenderException;
}
