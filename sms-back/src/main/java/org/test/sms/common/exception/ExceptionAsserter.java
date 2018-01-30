package org.test.sms.common.exception;

import static org.junit.Assert.assertEquals;

public class ExceptionAsserter {

    private Throwable throwable;

    private ExceptionAsserter(Throwable throwable) {
        this.throwable = throwable;
    }

    public static ExceptionAsserter assertThrows(ExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.throwException();
        } catch (Throwable throwable) {
            return new ExceptionAsserter(throwable);
        }

        throw new AssertionError("expected exception was not thrown");
    }

    public ExceptionAsserter expect(Class<? extends Throwable> throwable) {
        assertEquals(throwable.getSimpleName(), this.throwable.getClass().getSimpleName());

        return this;
    }

    public ExceptionAsserter expectMessage(String message) {
        assertEquals(message, throwable.getMessage());

        return this;
    }
}