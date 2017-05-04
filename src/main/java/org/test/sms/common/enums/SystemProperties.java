package org.test.sms.common.enums;

public enum SystemProperties {

    APP_ENCODING("sms.appEncoding", SystemPropertyType.STRING),

    APP_NAME("sms.appName", SystemPropertyType.STRING);

    private String key;

    private SystemPropertyType type;

    SystemProperties(String key, SystemPropertyType type) {
        this.key = key;
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        switch (type) {
            case STRING:
                return (T) System.getProperty(key);
            case INTEGER:
                return (T) Integer.getInteger(key);
            case LONG:
                return (T) Long.getLong(key);
            case DOUBLE:
                return (T) Double.valueOf(System.getProperty(key));
            default: {
                throw new IllegalArgumentException("illegal system property type");
            }
        }
    }
}