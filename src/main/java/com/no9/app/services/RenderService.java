package com.no9.app.services;

import com.no9.app.utils.TemplateID;

import java.io.InputStream;
import java.io.OutputStream;

public interface RenderService {
    void toPDF(TemplateID templateID, InputStream source, OutputStream output) throws RenderException;
}
