package org.test.sms.common.enums.general;

public enum FileFormatType {

    CSV,

    HTML,

    JPG,

    JSON,

    LATEX,

    PDF,

    XML;

    public String getName() {
        return toString().toLowerCase();
    }

    public String getFileExtension() {
        return "." + getName();
    }

    public String getContentType() {
        switch (this) {
            case CSV:
                return "text/csv";
            case HTML:
                return "text/html";
            case JSON:
                return "application/json";
            case PDF:
                return "application/pdf";
            case XML:
            default:
                return "application/octet-stream";
        }
    }
}