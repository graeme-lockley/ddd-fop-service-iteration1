package com.no9.app.utils;

public class TemplateID {
    final private String templateURI;

    public TemplateID(String templateURI) {
        this.templateURI = templateURI;
    }

    public String getTemplateURI() {
        return templateURI;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        return templateURI.equals(((TemplateID) object).templateURI);
    }

    @Override
    public int hashCode() {
        return templateURI.hashCode();
    }
}
