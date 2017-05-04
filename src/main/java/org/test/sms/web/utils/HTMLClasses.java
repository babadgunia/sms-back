package org.test.sms.web.utils;

public enum HTMLClasses {

    AUDITORIUM_LIST_SELECT("auditorium-add"),

    AUTOCOMPLETE_OFF("autocompleteOff"),

    BUTTON_ON_ACCORDION("accordionButton"),

    CUSTOM_ACCORDION("customAccordion"),

    CUSTOM_TWIN_COL_SELECT("customTwinColSelect"),

    ERROR_IMAGE("errorImage"),

    FULL_WIDTH("fullWidth"),

    LIST_SELECT_BUTTONS("listselect-button"),

    NO_BORDER("noBorder"),

    NUMBER_INPUT("numberInput"),

    PROFILE_NAME_LABEL("profileNameLabel"),

    RIGHT_ALIGNMENT("rightAlignment");

    private String className;

    private HTMLClasses(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}