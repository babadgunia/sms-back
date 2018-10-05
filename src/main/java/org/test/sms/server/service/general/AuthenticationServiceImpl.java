package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.PasswordResetToken;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.AuthenticationService;
import org.test.sms.common.service.general.MailService;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.PasswordResetTokenDao;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
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
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, userWrapper.get());
        LocalDateTime expiryDate = LocalDateTime.now();
        expiryDate = expiryDate.plusMinutes(60l);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetTokenDao.saveToken(passwordResetToken);

        mailService.sendPasswordResetMail(userWrapper.get(), url, token);
    }

    @Override
    public boolean isValidPasswordResetToken(long id, String token) {
        boolean isValid = true;
        PasswordResetToken passToken = passwordResetTokenDao.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            isValid = false;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (passToken != null && passToken.getExpiryDate().isBefore(currentDateTime)){
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void saveNewPassword(long userId, String password) {
        userDao.saveNewPassword(userId, new BCryptPasswordEncoder().encode(password));
    }
}