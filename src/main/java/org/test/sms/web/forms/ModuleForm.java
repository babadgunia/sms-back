package org.test.sms.web.forms;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.RichTextArea;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Module;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.service.AbstractService;
import org.test.sms.web.customComponents.CNumberField;
import org.test.sms.web.utils.UIUtils;

import java.util.List;
import java.util.Objects;

public class ModuleForm extends GenericForm<Module> {

    private AbstractService<Course> courseManager;

    private Course course;

    private CNumberField maxGrade;

    private RichTextArea descriptionTextArea;

    public void init(TabType tabType, Module module, AbstractService<Course> courseManager, Course course) {
        this.course = course;
        this.courseManager = courseManager;
        super.init(tabType, null, module, null);
    }

    @Override
    protected void initFormContent(FormLayout layout) {
        initNameBox(layout, (Objects.nonNull(entity) ? entity.getName() : null));
        maxGrade = new CNumberField();
        maxGrade.setCaption(UIUtils.getTranslation(Translations.MAX_GRADE));
        descriptionTextArea = new RichTextArea(UIUtils.getTranslation(Translations.DESCRIPTION));

        if (!isAdd) {
            descriptionTextArea.setValue(entity.getDescription());
            maxGrade.setValue(entity.getMaxGrade() + "");
        }

        layout.addComponents(maxGrade, descriptionTextArea);
    }

    @Override
    protected void save() {
        if (isAdd) {
            entity = new Module();
        }

        entity.setName(nameBox.getValue());
        entity.setCourse(course);
        entity.setDescription(descriptionTextArea.getValue());
        entity.setMaxGrade(Integer.valueOf(maxGrade.getValue()));
        if (isAdd) {
            course.getModules().add(entity);
        } else {
            List<Module> courseModules = course.getModules();
            for (int i = 0; i < courseModules.size(); i++) {
                if (courseModules.get(i).getId() == entity.getId()) {
                    courseModules.remove(i);
                    courseModules.add(i, entity);
                    break;
                }
            }
        }
    }
}