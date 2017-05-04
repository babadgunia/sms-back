package org.test.sms.web.customComponents;

public class CTreeItem<T> {

    private T value;

    private boolean selected;

    public CTreeItem(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}