package org.test.sms.web.tabs;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.test.sms.common.entities.general.I18NText;
import org.test.sms.common.entities.general.Text;
import org.test.sms.common.enums.LanguageType;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.general.TextFilter;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.service.general.TextService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.TextForm;
import org.test.sms.web.utils.Reloadable;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TextTab extends VerticalLayout implements Reloadable {

    public static final TabType TYPE = TabType.TEXT;

    private TextService service;

    private CachingService cachingService;

    private TextForm textForm;

    private CSearchTextField keyField;

    private BeanItemContainer<Text> container;

    public TextTab init() {
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
        keyField = new CSearchTextField(this::reload);
        keyField.setInputPrompt(UIUtils.getTranslation(Translations.KEY));

        Button clearFilterButton = new Button(UIUtils.getTranslation(Translations.CLEAR), FontAwesome.TIMES);
        clearFilterButton.addClickListener(e -> {
            keyField.clear();
            reload();
        });

        Button searchButton = new Button(UIUtils.getTranslation(Translations.SEARCH), e -> reload());
        Button addButton = new Button(UIUtils.getTranslation(Translations.ADD), e -> textForm.init(TYPE, service, null, container));
        if (UIUtils.hasPermission(TYPE, TabPermissionType.ADD)) {
            layout.addComponents(keyField, clearFilterButton, searchButton, addButton);
        } else {
            layout.addComponents(keyField, clearFilterButton, searchButton);
        }

        layout.setExpandRatio(clearFilterButton, 1);
        layout.setComponentAlignment(clearFilterButton, Alignment.MIDDLE_RIGHT);

        return layout;
    }

    private Grid initGrid() {
        List<Consumer<Text>> buttonActions = new ArrayList<>();
        String buttonColumns = generateButtonColumns(buttonActions);

        List<String> textValueColumns = generateTextColumns();

        List<String> entityColumns = Utils.toList("key", ",", String.class);
        entityColumns.addAll(textValueColumns);

        List<String> visibleColumns = new ArrayList<>();
        visibleColumns.addAll(entityColumns);
        visibleColumns.addAll(Utils.toList(buttonColumns, ",", String.class));

        Grid grid = new Grid();
        grid.setSizeFull();

        container = new BeanItemContainer<>(Text.class);
        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(container);
        grid.setContainerDataSource(gpc);

        initButtonColumns(grid, gpc, buttonColumns, buttonActions);
        initTextValueColumns(gpc, textValueColumns);

        grid.setColumns(visibleColumns.toArray());

        for (String column : entityColumns) {
            grid.getColumn(column).setExpandRatio(1);
        }

        for (String column : visibleColumns) {
            Column c = grid.getColumn(column);
            c.setHeaderCaption(UIUtils.getColumnHeaderTranslation(column));
        }

        grid.addItemClickListener(e -> {
            if (e.isDoubleClick()) {
                update((Text) e.getItemId());
            }
        });

        return grid;
    }

    private List<String> generateTextColumns() {
        List<String> list = new ArrayList<>();

        Arrays.stream(LanguageType.values()).forEach(e -> list.add(e.toString()));

        return list;
    }

    private String initTextValueColumns(GeneratedPropertyContainer gpc, List<String> textValueColumns) {
        for (String column : textValueColumns) {
            gpc.addGeneratedProperty(column, new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object itemId, Object propertyId) {
                    Optional<I18NText> textWrapper = ((Text) itemId).getValues().stream().filter(e -> e.getLanguage().toString().equals(column)).findFirst();
                    return textWrapper.map(I18NText::getValue).orElse("No Value");
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });

        }

        return null;
    }

    private String generateButtonColumns(List<Consumer<Text>> buttonActions) {
        String columns = "";

        if (UIUtils.hasPermission(TYPE, TabPermissionType.EDIT)) {
            buttonActions.add(e -> update(e));
            columns += "edit,";
        } else if (UIUtils.hasPermission(TYPE, TabPermissionType.VIEW)) {
            buttonActions.add(e -> update(e));
            columns += "view,";
        }

        if (UIUtils.hasPermission(TYPE, TabPermissionType.DELETE)) {
            buttonActions.add(e -> delete(e));
            columns += "delete,";
        }

        if (Utils.isBlank(columns)) {
            return "";
        }

        return columns.substring(0, columns.lastIndexOf(","));
    }

    private void initButtonColumns(Grid grid, GeneratedPropertyContainer gpc, String buttonColumns, List<Consumer<Text>> buttonActions) {
        List<String> buttonColumnsList = Utils.toList(buttonColumns, ",", String.class);

        for (int i = 0; i < buttonColumnsList.size(); i++) {
            int index = i;
            String column = buttonColumnsList.get(index);

            gpc.addGeneratedProperty(column, new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object itemId, Object propertyId) {
                    return UIUtils.getTranslation(Enum.valueOf(Translations.class, column.toUpperCase()));
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });

            ButtonRenderer buttonRenderer = new ButtonRenderer(e -> buttonActions.get(index).accept((Text) e.getItemId()));
            grid.getColumn(column).setRenderer(buttonRenderer);
        }
    }

    private void update(Text text) {
        textForm.init(TYPE, service, service.get(text.getId()).get(), container);
    }

    private void delete(Text text) {
        UIUtils.showConfirmDialog(text.getKey(), service, text, container, cachingService.getTexts(null));
    }

    @Override
    public void reload() {
        TextFilter filter = new TextFilter();

        filter.setKey(keyField.getValue());

        container.removeAllItems();
        container.addAll(cachingService.getTexts(filter));
        container.addAll(service.getList(null));
    }
}