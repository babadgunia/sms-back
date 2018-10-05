package org.test.sms.common.service.general;

import org.test.sms.common.exception.AppException;

public interface AuthenticationService {

    void resetPassword(String usernameOrEmail, String url) throws AppException;

    boolean isValidPasswordResetToken(long id, String token);

    void saveNewPassword(long userId, String password);
}