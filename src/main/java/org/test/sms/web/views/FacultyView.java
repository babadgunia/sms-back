package org.test.sms.web.views;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.test.sms.common.enums.Translations;
import org.test.sms.web.customComponents.CTabSheet;
import org.test.sms.web.tabs.CourseTab;
import org.test.sms.web.tabs.ExamTab;
import org.test.sms.web.tabs.FacultyTab;
import org.test.sms.web.utils.UIUtils;

public class FacultyView extends GenericView {

    public static final String NAME = "Faculty";

    private FacultyTab facultyTab;

    private CourseTab courseTab;

    private ExamTab examTab;

    private TabSheet.Tab backTab;

    @Override
    protected HorizontalLayout initHeader() {
        return initFullHeader();
    }

    @Override
    protected Component initContent() {
        return addTabs(new CTabSheet()).addTabChangeListener(backTab, HomepageView.NAME).initTab(1);
    }

    private CTabSheet addTabs(CTabSheet tabSheet) {
        backTab = tabSheet.addTab(new VerticalLayout(), UIUtils.getTranslation(Translations.BACK), FontAwesome.BACKWARD);
        tabSheet.addTab(facultyTab.init(), UIUtils.getTranslation(Translations.FACULTIES), FontAwesome.UNIVERSITY);
        tabSheet.addTab(courseTab.init(), UIUtils.getTranslation(Translations.COURSES), FontAwesome.BOOK);
        tabSheet.addTab(examTab.init(), UIUtils.getTranslation(Translations.EXAMS), FontAwesome.ETSY);

        return tabSheet;
    }
}