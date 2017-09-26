package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.User;

public interface MailService {

    void sendCredentialsMail(User user, String password);

    void sendPasswordResetMail(User user, String url, String token);
}