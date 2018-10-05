package org.test.sms.server.service.general;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.service.general.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Async
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;

    private Configuration freeMarkerConfiguration;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, Configuration freeMarkerConfiguration) {
        this.mailSender = mailSender;

        this.freeMarkerConfiguration = freeMarkerConfiguration;
        this.freeMarkerConfiguration.setClassForTemplateLoading(getClass(), "/email");
    }

    @Override
    public void sendCredentialsMail(User user, String password) {
        mailSender.send((MimeMessage message) -> {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("SMS app login credentials");
            helper.setTo(user.getEmail());

            Map<String, Object> model = new HashMap<>();

            model.put("name", user.getName());
            model.put("username", user.getUsername());
            model.put("password", password);

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate("credentialsMailTemplate.ftl"), model);
            helper.setText(text, true);

            addAppIcon(helper);
        });
    }

    private void addAppIcon(MimeMessageHelper helper) throws MessagingException {
        helper.addInline("appIcon", new ClassPathResource("email/appIcon.png"));
    }

    @Override
    public void sendPasswordResetMail(User user, String url, String token) {
        mailSender.send((MimeMessage message) -> {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("SMS app password reset");
            helper.setTo(user.getEmail());

            Map<String, Object> model = new HashMap<>();

            model.put("name", user.getUsername());
            model.put("link", "http://localhost:4200/updatePassword/?token=" + token + "&id=" + user.getId());

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate("resetPasswordTemplate.ftl"), model);
            helper.setText(text, true);

            addAppIcon(helper);
        });
    }
}