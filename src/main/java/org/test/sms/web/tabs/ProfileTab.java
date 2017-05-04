package org.test.sms.web.tabs;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.entities.university.Student;
import org.test.sms.common.entities.university.UniversityMember;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.enums.university.GenderType;
import org.test.sms.common.service.university.LecturerService;
import org.test.sms.common.service.university.StudentService;
import org.test.sms.web.customComponents.CLabel;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

import java.util.Objects;
import java.util.Optional;

public class ProfileTab extends HorizontalLayout {

    public static final TabType TYPE = TabType.PROFILE;

    private StudentService studentService;

    private LecturerService lecturerService;

    private UniversityMember universityMember;

    public ProfileTab init() {
        initUniversityMember();

        if (Objects.isNull(universityMember)) {
            return this;
        }

        setSizeFull();

        GridLayout infoLayout = initInfoLayout();
        GridLayout photoLayout = initializePhoto();
        addComponents(infoLayout, photoLayout);

        setExpandRatio(infoLayout, 3);
        setExpandRatio(photoLayout, 2);

        return this;
    }

    private void initUniversityMember() {
        long userId = UIUtils.getUser().getId();

        if (UIUtils.hasPermission(TYPE, TabPermissionType.STUDENT_PROFILE)) {
            Optional<Student> studentWrapper = studentService.getByUserId(userId);
            universityMember = studentWrapper.isPresent() ? studentWrapper.get() : null;
        } else if (UIUtils.hasPermission(TYPE, TabPermissionType.LECTURER_PROFILE)) {
            Optional<Lecturer> lecturerWrapper = lecturerService.getByUserId(userId);
            universityMember = lecturerWrapper.isPresent() ? lecturerWrapper.get() : null;
        }
    }

    private GridLayout initInfoLayout() {
        GridLayout infoLayout = new GridLayout(3, 15);
        infoLayout.setMargin(true);
        infoLayout.setSizeFull();

        infoLayout.setColumnExpandRatio(0, 3);
        infoLayout.setColumnExpandRatio(1, 4);
        infoLayout.setColumnExpandRatio(2, 3);

        int row = 0;

        CLabel nameLabel = new CLabel(universityMember.getFirstName() + " " + universityMember.getLastName());
        nameLabel.setSizeUndefined();
        nameLabel.addStyleName(HTMLClasses.PROFILE_NAME_LABEL.getClassName());
        nameLabel.addStyleName(ValoTheme.LABEL_H2);
        infoLayout.addComponent(nameLabel, 1, row, 2, row++);
        infoLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);

        CLabel genderLabel = new CLabel(UIUtils.getTranslation(Translations.GENDER));
        infoLayout.addComponent(genderLabel, 1, row);
        GenderType gender = universityMember.getGender();
        if (gender == GenderType.FEMALE) {
            infoLayout.addComponent(new Label(UIUtils.getTranslation(Translations.FEMALE)), 2, row++);
        } else if (gender == GenderType.MALE) {
            infoLayout.addComponent(new Label(UIUtils.getTranslation(Translations.MALE)), 2, row++);
        }

        CLabel birthDateLabel = new CLabel(UIUtils.getTranslation(Translations.BIRTH_DATE));
        infoLayout.addComponent(birthDateLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getBirthDate().toString()), 2, row++);

        CLabel personalNumberLabel = new CLabel(UIUtils.getTranslation(Translations.PERSONAL_NUMBER));
        infoLayout.addComponent(personalNumberLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getPersonalNumber()), 2, row++);

        CLabel phoneNumberLabel = new CLabel(UIUtils.getTranslation(Translations.PHONE_NUMBER));
        infoLayout.addComponent(phoneNumberLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getPhoneNumber()), 2, row++);

        CLabel addressLabel = new CLabel(UIUtils.getTranslation(Translations.ADDRESS));
        infoLayout.addComponent(addressLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getAddress()), 2, row++);

        CLabel emailLabel = new CLabel(UIUtils.getTranslation(Translations.EMAIL));
        infoLayout.addComponent(emailLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getEmail()), 2, row++);

        CLabel uniEmailLabel = new CLabel(UIUtils.getTranslation(Translations.UNIVERSITY_EMAIL));
        infoLayout.addComponent(uniEmailLabel, 1, row);
        infoLayout.addComponent(new Label(universityMember.getUniEmail()), 2, row++);

        if (UIUtils.hasPermission(TabType.PROFILE, TabPermissionType.STUDENT_PROFILE)) {
            addStudentInfo(infoLayout, row);
        }

        return infoLayout;
    }

    private void addStudentInfo(GridLayout infoLayout, int curRow) {
        int row = curRow;

        Student student = (Student) universityMember;

        CLabel majorFacultyLabel = new CLabel(UIUtils.getTranslation(Translations.MAJOR_FACULTY));
        infoLayout.addComponent(majorFacultyLabel, 1, row);
        if (Objects.nonNull(student.getMajor())) {
            infoLayout.addComponent(new Label(student.getMajor().getName()), 2, row++);
        } else {
            infoLayout.addComponent(new Label(UIUtils.getTranslation(Translations.NO_MAJOR)), 2, row++);
        }

        CLabel minorFacultyLabel = new CLabel(UIUtils.getTranslation(Translations.MINOR_FACULTY));
        infoLayout.addComponent(minorFacultyLabel, 1, row);
        if (Objects.nonNull(student.getMinor())) {
            infoLayout.addComponent(new Label(student.getMinor().getName()), 2, row++);
        } else {
            infoLayout.addComponent(new Label(UIUtils.getTranslation(Translations.NO_MINOR)), 2, row++);

        }

        CLabel scolarshipLabel = new CLabel(UIUtils.getTranslation(Translations.SCHOLARSHIP));
        infoLayout.addComponent(scolarshipLabel, 1, row);
        infoLayout.addComponent(new Label(Integer.toString(student.getScholarship())), 2, row++);

        CLabel semesterLabel = new CLabel(UIUtils.getTranslation(Translations.SEMESTER));
        infoLayout.addComponent(semesterLabel, 1, row);
        infoLayout.addComponent(new Label(Integer.toString(student.getSemester())), 2, row);
    }

    private GridLayout initializePhoto() {
        GridLayout photoLayout = new GridLayout(1, 2);
        photoLayout.setSizeFull();
        photoLayout.setRowExpandRatio(0, 1);
        photoLayout.setRowExpandRatio(1, 4);

        Image photo = UIUtils.toImage(universityMember.getPhoto());

        photoLayout.addComponent(photo, 0, 1);
        photoLayout.setComponentAlignment(photo, Alignment.TOP_CENTER);

        return photoLayout;
    }
}