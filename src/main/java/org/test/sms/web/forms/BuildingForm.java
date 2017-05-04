package org.test.sms.web.forms;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.test.sms.common.entities.university.Auditorium;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.AbstractService;
import org.test.sms.common.service.university.AuditoriumService;
import org.test.sms.common.utils.Action;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;
import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LMarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BuildingForm extends GenericForm<Building> {

    private AuditoriumService auditoriumService;

    private AuditoriumForm auditoriumForm;

    private BuildingFormParameters parameters;

    private CComboBox<String> nameBox;

    private CComboBox<String> addressBox;

    private CTextField latField;

    private CTextField lonField;

    private Grid grid;

    protected BeanItemContainer<Auditorium> auditoriumContainer;

    public void init(TabType tabType, AbstractService<Building> manager, Building entity, BeanItemContainer<Building> container, BuildingFormParameters parameters) {
        this.parameters = parameters;

        init(tabType, manager, entity, container);
    }

    @Override
    protected void initFormContent(FormLayout layout) {
        nameBox = new CComboBox<>(UIUtils.getTranslation(Translations.NAME));
        UIUtils.getTextKeys().forEach(e -> {
            nameBox.addItem(e);
            nameBox.setItemCaption(e, UIUtils.getTextValue(e));
        });

        addressBox = new CComboBox<>(UIUtils.getTranslation(Translations.ADDRESS));
        UIUtils.getTextKeys().forEach(e -> {
            addressBox.addItem(e);
            addressBox.setItemCaption(e, UIUtils.getTextValue(e));
        });

        latField = new CTextField(UIUtils.getTranslation(Translations.LAT));
        lonField = new CTextField(UIUtils.getTranslation(Translations.LON));

        grid = new Grid(UIUtils.getTranslation(Translations.AUDITORIUMS));

        auditoriumContainer = new BeanItemContainer<>(Auditorium.class);
        auditoriumContainer.addAll(entity.getAuditoriums());

        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(auditoriumContainer);

        grid.setContainerDataSource(gpc);

        gpc.addGeneratedProperty("seats", new PropertyValueGenerator<Integer>() {

            @Override
            public Integer getValue(Item item, Object itemId, Object propertyId) {
                return ((Auditorium) itemId).getSeats().size();
            }

            @Override
            public Class<Integer> getType() {
                return Integer.class;
            }
        });

        Map<String, Consumer<Auditorium>> buttonActions = new HashMap<>();

        String entityColumns = "name, seats";
        String buttonColumns = generateButtonColumns(buttonActions);
        String visibleColumns = entityColumns + "," + buttonColumns;

        initButtonColumns(grid, gpc, buttonColumns, buttonActions);

        grid.setColumns(Utils.toList(visibleColumns, ",", String.class).toArray());
        Utils.toList(visibleColumns, ",", String.class).forEach(e -> grid.getColumn(e).setHeaderCaption(UIUtils.getColumnHeaderTranslation(e)));

        grid.addItemClickListener(e -> {
            if (e.isDoubleClick()) {
                if (UIUtils.hasPermission(tabType, TabPermissionType.EDIT)) {
                    updateAuditorium((Auditorium) e.getItemId());
                }
            }
        });

        if (!isAdd) {
            nameBox.setValue(entity.getName());
            addressBox.setValue(entity.getAddress());
            latField.setValue(entity.getLat().toString());
            lonField.setValue(entity.getLon().toString());
        } else {
            latField.setValue(parameters.getLatitude().toString());
            lonField.setValue(parameters.getLongitude().toString());
        }

        Button addButton = new Button(UIUtils.getTranslation(Translations.ADD), e -> auditoriumForm.init(tabType, auditoriumService, null, auditoriumContainer));

        if (UIUtils.hasPermission(tabType, TabPermissionType.ADD)) {
            layout.addComponents(nameBox, addressBox, latField, lonField, addButton, grid);
        } else {
            layout.addComponents(nameBox, addressBox, latField, lonField, grid);
        }

        if (!UIUtils.hasPermission(tabType, TabPermissionType.DELETE)) {
            buttonLayout.addComponent(new Button(UIUtils.getTranslation(Translations.DELETE), e -> delete()), 0);
        }
    }

    private String generateButtonColumns(Map<String, Consumer<Auditorium>> buttonActions) {
        String columns = getButtonColumns(buttonActions);

        if (UIUtils.hasPermission(tabType, TabPermissionType.EDIT)) {
            String edit = "edit";
            buttonActions.put(edit, this::updateAuditorium);
            columns += edit + ", ";
        } else if (UIUtils.hasPermission(tabType, TabPermissionType.VIEW)) {
            String view = "view";
            buttonActions.put(view, this::updateAuditorium);
            columns += view + ", ";
        }

        if (UIUtils.hasPermission(tabType, TabPermissionType.DELETE)) {
            String delete = "delete";
            buttonActions.put(delete, this::deleteAuditorium);
            columns += delete + ", ";
        }
        if (Utils.isBlank(columns)) {
            return "";
        }

        return columns.substring(0, columns.lastIndexOf(", "));
    }

    private String getButtonColumns(@SuppressWarnings("unused") Map<String, Consumer<Auditorium>> buttonActions) {
        return "";
    }

    private void updateAuditorium(Auditorium auditorium) {
        auditoriumForm.init(tabType, auditoriumService, auditoriumService.get(auditorium.getId()).get(), auditoriumContainer);
    }

    private void deleteAuditorium(Auditorium auditorium) {
        UIUtils.showConfirmDialog(UIUtils.getTranslation(Translations.AUDITORIUM) + " " + auditorium.getName(), auditoriumService, auditorium, auditoriumContainer, null);
    }

    private void initButtonColumns(Grid grid, GeneratedPropertyContainer gpc, String buttonColumns, Map<String, Consumer<Auditorium>> buttonActions) {
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

            ButtonRenderer buttonRenderer = new ButtonRenderer(f -> buttonActions.get(buttonName).accept((Auditorium) f.getItemId()));
            grid.getColumn(buttonName).setRenderer(buttonRenderer);
        }
    }

    private void delete() {
        Action removeMarker = () -> parameters.getLeafletMap().removeComponent(parameters.getMarker());

        UIUtils.showConfirmDialog(entity.getName(), manager, entity, container, cachingService.getBuildings(), removeMarker, this::close);
    }

    @Override
    protected void save() {
        try {
            nameBox.validate();
            addressBox.validate();
            latField.validate();
            lonField.validate();
        } catch (InvalidValueException e) {
            nameBox.setValidationVisible(true);
            addressBox.setValidationVisible(true);
            latField.setValidationVisible(true);
            lonField.setValidationVisible(true);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Building();
        }

        entity.setName(nameBox.getValue());
        entity.setAddress(addressBox.getValue());
        entity.setLat(Double.parseDouble(latField.getValue()));
        entity.setLon(Double.parseDouble(lonField.getValue()));

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                LMarker marker = new LMarker(entity.getLat(), entity.getLon());

                marker.setId(Long.toString(entity.getId()));

                String name = entity.getName();
                marker.setTitle(name);
                marker.setPopup(name + ", " + entity.getAddress());

                LMap leafletMap = parameters.getLeafletMap();

                marker.addContextMenuListener(e -> {
                    Building buildingToUpdate = manager.get(Long.parseLong(((LMarker) e.getSource()).getId())).get();
                    init(tabType, manager, buildingToUpdate, null, new BuildingForm.BuildingFormParameters(leafletMap, marker));
                });

                leafletMap.addComponent(marker);
                parameters.getMarkerMap().put(entity.getId(), marker);
            }

            List<Building> list = cachingService.getBuildings();
            if (isAdd) {
                list.add(entity);
            } else {
                list.set(list.indexOf(entity), entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }

    public static class BuildingFormParameters {

        private LMap leafletMap;

        private Map<Long, LMarker> markerMap;

        private LMarker marker;

        private Double latitude;

        private Double longitude;

        public BuildingFormParameters(LMap leafletMap, LMarker marker) {
            this.leafletMap = leafletMap;
            this.marker = marker;
        }

        public BuildingFormParameters(LMap leafletMap, Map<Long, LMarker> markerMap, Double latitude, Double longitude) {
            this.leafletMap = leafletMap;
            this.markerMap = markerMap;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public LMap getLeafletMap() {
            return leafletMap;
        }

        public void setLeafletMap(LMap leafletMap) {
            this.leafletMap = leafletMap;
        }

        public Map<Long, LMarker> getMarkerMap() {
            return markerMap;
        }

        public void setMarkerMap(Map<Long, LMarker> markerMap) {
            this.markerMap = markerMap;
        }

        public LMarker getMarker() {
            return marker;
        }

        public void setMarker(LMarker marker) {
            this.marker = marker;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
}