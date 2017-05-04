package org.test.sms.web.tabs;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.service.AbstractService;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.converter.DateConverter;
import org.test.sms.web.converter.TextConverter;
import org.test.sms.web.converter.TranslationConverter;
import org.test.sms.web.customComponents.CSearchComboBox;
import org.test.sms.web.customComponents.CSearchPopupDateField;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.GenericForm;
import org.test.sms.web.utils.Reloadable;
import org.test.sms.web.utils.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GenericTab<T extends AppEntity> extends VerticalLayout implements Reloadable {

    protected TabType tabType = TabType.FACULTY;

    private Class<T> entityClass;

    protected AbstractService<T> manager;

    protected CachingService cachingService;

    protected BeanItemContainer<T> container;

    protected GenericForm<T> form;

    protected abstract void initSearchFilterContent(HorizontalLayout layout);

    protected abstract String getEntityColumns();

    protected abstract String getNestedEntityColumns();

    protected abstract String getButtonColumns(Map<String, Consumer<T>> buttonActions);

    protected abstract String getTextConverterColumns();

    protected abstract String getTranslationConverterColumns();

    protected abstract String getColumnOrder();

    protected abstract void update(T entity);

    protected abstract void delete(T entity);

    protected abstract void performAdditionalReloadActions();

    protected abstract List<T> getEntityList();

    public GenericTab<T> init(TabType tabType, Class<T> entityClass, AbstractService<T> manager, GenericForm<T> form) {
        this.tabType = tabType;
        this.entityClass = entityClass;
        this.manager = manager;
        this.form = form;

        setSizeFull();
        setMargin(true);
        setSpacing(true);

        Grid grid = initGrid();
        addComponents(initSearchFilter(), grid);
        setExpandRatio(grid, 1);

        return this;
    }

    private HorizontalLayout initSearchFilter() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth(UIUtils.FULL_WIDTH);
        layout.setSpacing(true);

        initSearchFilterContent(layout);

        Button clearFilterButton = new Button(UIUtils.getTranslation(Translations.CLEAR), e -> {
            for (int i = 0; i < layout.getComponentCount(); i++) {
                Component component = layout.getComponent(i);

                if (layout.getComponent(i) instanceof CSearchTextField) {
                    ((CSearchTextField) component).clear();
                }

                if (layout.getComponent(i) instanceof CSearchPopupDateField) {
                    ((CSearchPopupDateField) component).clear();
                }

                if (layout.getComponent(i) instanceof Panel) {
                    ((CSearchComboBox<?>) ((Panel) component).getContent()).clear();
                }
            }

            reload();
        });

        Button searchButton = new Button(UIUtils.getTranslation(Translations.SEARCH), e -> reload());
        Button addButton = new Button(UIUtils.getTranslation(Translations.ADD), e -> form.init(tabType, manager, null, container));

        if (UIUtils.hasPermission(tabType, TabPermissionType.ADD)) {
            layout.addComponents(clearFilterButton, searchButton, addButton);
        } else {
            layout.addComponents(clearFilterButton, searchButton);

        }

        layout.setExpandRatio(clearFilterButton, 1);
        layout.setComponentAlignment(clearFilterButton, Alignment.MIDDLE_RIGHT);

        return layout;
    }

    @SuppressWarnings("unchecked")
    private Grid initGrid() {
        Map<String, Consumer<T>> buttonActions = new HashMap<>();

        String entityColumns = getEntityColumns();
        String nestedEntityColumns = getNestedEntityColumns();
        String buttonColumns = generateButtonColumns(buttonActions);
        String visibleColumns = entityColumns + "," + nestedEntityColumns + "," + buttonColumns;
        String textConverterColumns = getTextConverterColumns();
        String translationConverterColumns = getTranslationConverterColumns();
        String columnOrder = getColumnOrder();

        Grid grid = new Grid();
        grid.setSizeFull();

        container = new BeanItemContainer<>(entityClass);
        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(container);
        grid.setContainerDataSource(gpc);

        List<String> nestedEntityColumnsList = Utils.toList(nestedEntityColumns, ",", String.class);
        nestedEntityColumnsList.forEach(e -> container.addNestedContainerProperty(e));

        initButtonColumns(grid, gpc, buttonColumns, buttonActions);

        grid.setColumns(Utils.toList(visibleColumns, ",", String.class).toArray());
        grid.setColumnOrder(Utils.toList(columnOrder, ",", String.class).toArray());

        Utils.toList(entityColumns, ",", String.class).parallelStream().forEach(e -> grid.getColumn(e).setExpandRatio(1));
        nestedEntityColumnsList.parallelStream().forEach(e -> grid.getColumn(e).setExpandRatio(1));
        Utils.toList(visibleColumns, ",", String.class).forEach(e -> grid.getColumn(e).setHeaderCaption(UIUtils.getColumnHeaderTranslation(e)));
        Utils.toList(textConverterColumns, ",", String.class).parallelStream().forEach(e -> grid.getColumn(e).setConverter(new TextConverter()));
        Utils.toList(translationConverterColumns, ",", String.class).parallelStream().forEach(e -> grid.getColumn(e).setConverter(new TranslationConverter()));

        if (visibleColumns.contains("startDate, endDate")) {
            Utils.toList("startDate, endDate", ",", String.class).parallelStream().forEach(e -> grid.getColumn(e).setConverter(new DateConverter()));
        }

        grid.addItemClickListener(e -> {
            if (e.isDoubleClick()) {
                if (UIUtils.hasPermission(tabType, TabPermissionType.EDIT)) {
                    update((T) e.getItemId());
                }
            }
        });

        return grid;
    }

    private String generateButtonColumns(Map<String, Consumer<T>> buttonActions) {
        String columns = getButtonColumns(buttonActions);

        if (UIUtils.hasPermission(tabType, TabPermissionType.EDIT)) {
            String edit = "edit";
            buttonActions.put(edit, this::update);
            columns += edit + ", ";
        } else if (UIUtils.hasPermission(tabType, TabPermissionType.VIEW)) {
            String view = "view";
            buttonActions.put(view, this::update);
            columns += view + ", ";
        }

        if (UIUtils.hasPermission(tabType, TabPermissionType.DELETE)) {
            String delete = "delete";
            buttonActions.put(delete, this::delete);
            columns += delete + ", ";
        }
        if (Utils.isBlank(columns)) {
            return "";
        }

        return columns.substring(0, columns.lastIndexOf(", "));
    }

    private void initButtonColumns(Grid grid, GeneratedPropertyContainer gpc, String buttonColumns, Map<String, Consumer<T>> buttonActions) {
        for (String buttonName : Utils.toList(buttonColumns, ",", String.class)) {
            gpc.addGeneratedProperty(buttonName, new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object itemId, Object propertyId) {
                    return UIUtils.getTranslation(Enum.valueOf(Translations.class, buttonName.toUpperCase()));
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });

            @SuppressWarnings("unchecked")
            ButtonRenderer buttonRenderer = new ButtonRenderer(f -> buttonActions.get(buttonName).accept((T) f.getItemId()));
            grid.getColumn(buttonName).setRenderer(buttonRenderer);
        }
    }

    @Override
    public void reload() {
        performAdditionalReloadActions();

        container.removeAllItems();
        container.addAll(getEntityList());
    }
}