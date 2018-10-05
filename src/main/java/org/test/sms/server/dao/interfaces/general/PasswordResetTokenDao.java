package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.PasswordResetToken;

public interface PasswordResetTokenDao {

    PasswordResetToken saveToken(PasswordResetToken token);

    PasswordResetToken findByToken(String token);
}