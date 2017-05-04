package org.test.sms.web.views;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.test.sms.common.entities.Tab;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.service.common.ActionLogService;
import org.test.sms.web.customComponents.CTabSheet;
import org.test.sms.web.tabs.BuildingTab;
import org.test.sms.web.tabs.LecturerTab;
import org.test.sms.web.tabs.ProfileTab;
import org.test.sms.web.tabs.StudentTab;
import org.test.sms.web.tabs.TextTab;
import org.test.sms.web.tabs.UserGroupTab;
import org.test.sms.web.tabs.UserTab;
import org.test.sms.web.utils.UIUtils;

public class HomepageView extends GenericView {

    public static final String NAME = "Homepage";

    private ActionLogService actionLogService;

    private BuildingTab buildingTab;

    private LecturerTab lecturerTab;

    private ProfileTab profileTab;

    private StudentTab studentTab;

    private TextTab textTab;

    private UserTab userTab;

    private UserGroupTab userGroupTab;

    private TabSheet.Tab facultyTab;

    @Override
    protected HorizontalLayout initHeader() {
        return initFullHeader();
    }

    @Override
    protected Component initContent() {
        return addTabs(new CTabSheet()).addTabChangeListener(facultyTab, FacultyView.NAME).initTab(0);
    }

    private CTabSheet addTabs(CTabSheet tabSheet) {
        for (Tab tab : UIUtils.getUserTabs()) {
            switch (tab.getType()) {
                case BUILDING: {
                    tabSheet.addTab(buildingTab.init(), UIUtils.getTranslation(Translations.BUILDINGS), FontAwesome.BUILDING_O);

                    break;
                }
                case FACULTY: {
                    facultyTab = tabSheet.addTab(new VerticalLayout(), UIUtils.getTranslation(Translations.FACULTIES), FontAwesome.UNIVERSITY);

                    break;
                }
                case LECTURER: {
                    tabSheet.addTab(lecturerTab.init(), UIUtils.getTranslation(Translations.LECTURERS), FontAwesome.GRADUATION_CAP);

                    break;
                }
                case PROFILE: {
                    tabSheet.addTab(profileTab.init(), UIUtils.getTranslation(Translations.PROFILE), FontAwesome.PHOTO);

                    break;
                }
                case STUDENT: {
                    tabSheet.addTab(studentTab.init(), UIUtils.getTranslation(Translations.STUDENTS), FontAwesome.BOOK);

                    break;
                }
                case TEXT: {
                    tabSheet.addTab(textTab.init(), UIUtils.getTranslation(Translations.TEXTS), FontAwesome.TEXT_HEIGHT);

                    break;
                }
                case USER: {
                    tabSheet.addTab(userTab.init(), UIUtils.getTranslation(Translations.USERS), FontAwesome.USER);

                    break;
                }
                case USER_GROUP: {
                    tabSheet.addTab(userGroupTab.init(), UIUtils.getTranslation(Translations.USER_GROUPS), FontAwesome.GROUP);

                    break;
                }
                default: {
                    break;
                }
            }
        }

        return tabSheet;
    }
}