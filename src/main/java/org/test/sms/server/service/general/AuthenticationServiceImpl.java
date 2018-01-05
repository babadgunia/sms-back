package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.PasswordResetToken;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.AuthenticationService;
import org.test.sms.common.service.general.MailService;
import org.test.sms.server.dao.interfaces.general.PasswordResetTokenDao;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Async
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDao userDao;

    private PasswordResetTokenDao passwordResetTokenDao;

    private MailService mailService;

    @Autowired
    public AuthenticationServiceImpl(UserDao userDao, PasswordResetTokenDao passwordResetTokenDao, MailService mailService) {
        this.userDao = userDao;
        this.passwordResetTokenDao = passwordResetTokenDao;
        this.mailService = mailService;
    }

    @Override
    public void resetPassword(String usernameOrEmail, String url) throws AppException {
        Optional<User> userWrapper = userDao.getForPasswordResetByEmailOrUsername(usernameOrEmail);
        if (!userWrapper.isPresent()) {
            throw new AppException(ErrorCodeType.USERNAME_NOT_FOUND);
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, userWrapper.get());
        passwordResetTokenDao.saveToken(myToken);

        mailService.sendPasswordResetMail(userWrapper.get(), url, token);
    }
}