package org.test.sms.server.service.general;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.service.general.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@Transactional
@Async
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;

    private Configuration freeMarkerConfiguration;

//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(environment.getRequiredProperty("mail.server"));
//        mailSender.setPort(environment.getRequiredProperty("mail.port", Integer.class));
//        mailSender.setUsername(environment.getRequiredProperty("mail.username"));
//        mailSender.setPassword(environment.getRequiredProperty("mail.password"));
//
//        Properties properties = mailSender.getJavaMailProperties();
//
//        properties.put("mail.transport.protocol", environment.getRequiredProperty("mail.transport.protocol"));
//        properties.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
//        properties.put("mail.smtp.starttls.enable", environment.getRequiredProperty("mail.smtp.starttls.enable"));
//
//        return mailSender;
//    }
//
//    @Bean
//    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
//        return new FreeMarkerConfigurationFactoryBean();
//    }

    @Autowired
    public MailServiceImpl( Configuration freeMarkerConfiguration) {
        this.mailSender = new JavaMailSenderImpl();

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

            model.put("url", url + "/" + token);

//            TODO create new template ex: passwordResetMailTemplate.ftl
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate("credentialsMailTemplate.ftl"), model);
            helper.setText(text, true);

            addAppIcon(helper);
        });
    }
}