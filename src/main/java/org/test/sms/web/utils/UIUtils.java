package org.test.sms.web.utils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.entities.I18NText;
import org.test.sms.common.entities.Tab;
import org.test.sms.common.entities.Text;
import org.test.sms.common.entities.User;
import org.test.sms.common.entities.UserGroup;
import org.test.sms.common.enums.ActionType;
import org.test.sms.common.enums.LanguageType;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.AbstractService;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.service.common.ActionLogService;
import org.test.sms.common.utils.Action;
import org.test.sms.common.utils.AppUtils;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.web.customComponents.CConfirmDialog;
import org.test.sms.web.customComponents.CNotification;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UIUtils {

    public static final String ADMINISTRATOR = "ADMINISTRATOR";

    public static final String FULL_WIDTH = "100%";

    public static final String ACCORDION_TAB_HEIGHT = "400px";

    public static final String IMAGE_FORMAT_JPG = "image/jpg";

    public static final String IMAGE_FORMAT_PNG = "image/png";

    public static final String IMAGE_FORMAT_JPEG = "image/jpeg";

    public static final String IMAGE_FILTER = "image/*";

    public static final long IMAGE_SIZE_IN_FORM = 50;

    public static final double TBILISI_LAT = 41.715324;

    public static final double TBILISI_LON = 44.788170;

    public static final double MAP_ZOOM_LEVEL = 12;

    public static final DateFormat SHORT_YEAR_NO_SECONDS_DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

//	navigation

    public static void navigateTo(String view) {
        UI.getCurrent().getNavigator().navigateTo(view);
    }

//	ip info

    public static String getIpAddress() {
        return Page.getCurrent().getWebBrowser().getAddress();
    }

//	user

    public static User getUser() {
        return VaadinSession.getCurrent().getAttribute(User.class);
    }

    private static UserGroup getUserGroup() {
        return getUser().getUserGroup();
    }

    public static List<Tab> getUserTabs() {
        return getUserGroup().getTabs().parallelStream().sorted(Comparator.comparingInt(Tab::getPosition)).collect(Collectors.toList());
    }

    public static LanguageType getUserLanguage() {
        User user = getUser();

        return Objects.nonNull(user) ? user.getLanguage() : LanguageType.EN;
    }

    public static boolean hasPermission(TabType tab, TabPermissionType permission) {
        return getUserTabs().parallelStream().filter(e -> e.getType() == tab).findFirst().get().getPermissions().contains(permission);
    }

    public static boolean isAdministrator() {
        return getUserGroup().getName().equals(ADMINISTRATOR);
    }

//	notifications

    public static void showFailNotification(String text) {
        Notification.show(text, Type.ERROR_MESSAGE);
    }

    public static void showSuccessNotification() {
        new CNotification(getTranslation(Translations.OPERATION_SUCCESS), Position.BOTTOM_RIGHT, (int) (DateUtils.SECOND), ValoTheme.NOTIFICATION_SUCCESS).show(Page.getCurrent());
    }

    public static <T extends AppEntity> void showConfirmDialog(String item, AbstractService<T> manager, T entity, BeanItemContainer<T> container, List<T> cachedList, Action... additionalActions) {
        CConfirmDialog.show(getTranslation(Translations.DELETE), getTranslation(Translations.CONFIRM_DELETE, item), getTranslation(Translations.YES), getTranslation(Translations.CANCEL), () -> {
            try {
                long id = entity.getId();

                Optional<T> entityWrapper = manager.get(id);
                if (entityWrapper.isPresent()) {
                    manager.delete(entityWrapper.get().getId());
                }

                AppUtils.getBean(ActionLogService.class).add(ActionType.DELETE, "removing " + entity.getClass().getSimpleName() + " with id " + id, getUser().getUsername(), getIpAddress());

                if (Objects.nonNull(container)) {
                    container.removeItem(entity);
                }

                if (Objects.nonNull(cachedList)) {
                    cachedList.remove(entity);
                }

                return true;
            } catch (AppException e) {
                showFailNotification(getTranslation(e));
            }

            return false;
        }, additionalActions);
    }

//	fields

    public static void validate(TextField... textFields) {
        Arrays.stream(textFields).parallel().forEach(AbstractField::validate);
    }

    public static void setValidationVisible(TextField... textFields) {
        Arrays.stream(textFields).parallel().forEach(field -> field.setValidationVisible(true));
    }

//	translations

    public static String getTranslation(Translations translation, Object... params) {
        return translation.getTranslation(getUserLanguage(), params);
    }

    public static String getTranslation(Enum<?> enumObject, Object... params) {
        return getTranslation(Enum.valueOf(Translations.class, enumObject.toString()), params);
    }

    public static String getTranslation(AppException exception) {
        return getTranslation(exception.getErrorCode(), exception.getParams());
    }

    public static String getColumnHeaderTranslation(String input) {
        String modifiedInput = input.replace(".", "").toUpperCase();
        Optional<Translations> textWrapper = Arrays.stream(Translations.values()).parallel().filter(e -> e.toString().replace("_", "").equals(modifiedInput)).findFirst();

        return textWrapper.isPresent() ? getTranslation(textWrapper.get()) : input;
    }

//	texts

    private static List<Text> getTexts() {
        return AppUtils.getBean(CachingService.class).getTexts(null);
    }

    public static List<String> getTextKeys() {
        return getTexts().stream().map(Text::getKey).collect(Collectors.toList());
    }

    public static List<String> getTextKeys(List<String> keys, String value) {
        Stream<Text> stream = AppUtils.getBean(CachingService.class).getTexts(null).stream().filter(e -> keys.contains(e.getKey()));

        return stream.filter(e -> e.getValues().stream().filter(f -> f.getLanguage() == getUserLanguage()).findFirst().get().getValue().toUpperCase().contains(value.toUpperCase())).map(Text::getKey)
                .collect(Collectors.toList());
    }

    public static String getTextValue(String key) {
        Optional<Text> text = getTexts().parallelStream().filter(e -> e.getKey().equals(key)).findFirst();
        if (text.isPresent()) {
            Optional<I18NText> textWrapper = text.get().getValues().stream().filter(e -> e.getLanguage() == getUserLanguage()).findAny();
            if (textWrapper.isPresent()) {
                return textWrapper.get().getValue();
            }
        }

        return key;
    }

//	image

    public static Image toImage(byte[] bytes) {
        if (Objects.isNull(bytes)) {
            return new Image();
        }

        StreamSource imageSource = (StreamSource) () -> new ByteArrayInputStream(bytes);
        Resource image = new StreamResource(imageSource, "");

        return new Image("", image);
    }
}