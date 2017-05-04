package org.test.sms.web.utils;

public enum JSFunctions {

    AUTOCOMPLETE_OFF("autocompleteOff"),

    BUTTON_ON_ACCORDION("buttonOnAccordion"),

    NUMBER_INPUT("numberInput");

    private String functionName;

    private JSFunctions(String functionName) {
        this.functionName = functionName;
    }

    public String getJSFunction(String... parameters) {
        String result = functionName + "(";

        for (String parameter : parameters) {
            result += "'" + parameter + "'" + ",";
        }

        result = result.substring(0, result.length() - 1);
        result += ")";

        return result;
    }
}