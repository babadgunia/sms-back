package org.test.sms.common.exception;

@FunctionalInterface
public interface ExceptionThrower {

    void throwException() throws Throwable;
}