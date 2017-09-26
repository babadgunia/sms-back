package org.test.sms.common.service.general;

import org.test.sms.common.exception.AppException;

public interface AuthenticationService {

    void resetPassword(String usernameOrEmail, String url) throws AppException;
}