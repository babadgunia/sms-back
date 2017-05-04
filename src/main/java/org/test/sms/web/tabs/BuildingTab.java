package org.test.sms.web.tabs;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.service.university.BuildingService;
import org.test.sms.web.forms.BuildingForm;
import org.test.sms.web.utils.Reloadable;
import org.test.sms.web.utils.UIUtils;
import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LMarker;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.shared.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingTab extends VerticalLayout implements Reloadable {

    public static final TabType TYPE = TabType.BUILDING;

    private BuildingService service;

    private CachingService cachingService;

    private BuildingForm form;

    private LMap leafletMap;

    private Map<Long, LMarker> markerMap = new HashMap<>();

    public BuildingTab init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        Component map = initMap();
        addComponents(map);
        setExpandRatio(map, 1);

        return this;
    }

    public Component initMap() {
        leafletMap = new LMap();
        leafletMap.setCenter(UIUtils.TBILISI_LAT, UIUtils.TBILISI_LON);
        leafletMap.setZoomLevel(UIUtils.MAP_ZOOM_LEVEL);
        leafletMap.addBaseLayer(new LOpenStreetMapLayer(), "CloudMade");

        if (UIUtils.hasPermission(TYPE, TabPermissionType.ADD)) {
            leafletMap.addContextMenuListener(e -> {
                Point point = e.getPoint();
                BuildingForm.BuildingFormParameters parameters = new BuildingForm.BuildingFormParameters(leafletMap, markerMap, point.getLat(), point.getLon());

                form.init(TYPE, service, null, null, parameters);
            });
        }

        return leafletMap;
    }

    @Override
    public void reload() {
        List<Building> buildings = cachingService.getBuildings();
        markerMap.forEach((id, marker) -> leafletMap.removeComponent(marker));
        buildings.forEach(this::createMarker);
    }

    private void createMarker(Building building) {
        LMarker marker = new LMarker(building.getLat(), building.getLon());

        marker.setId(Long.toString(building.getId()));

        String name = building.getName();
        marker.setTitle(name);
        marker.setPopup(name + ", " + building.getAddress());

        marker.addContextMenuListener(e -> {
            Building buildingToUpdate = service.get(Long.parseLong(((LMarker) e.getSource()).getId())).get();
            form.init(TYPE, service, buildingToUpdate, null, new BuildingForm.BuildingFormParameters(leafletMap, marker));
        });

        leafletMap.addComponent(marker);
        markerMap.put(building.getId(), marker);
    }
}