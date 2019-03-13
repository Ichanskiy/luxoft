package com.ichanskyi.luxoft.exeptions;

public class DuplicateEmailException extends RuntimeException {
    public static void createDuplicateEmailException() {
        throw new DuplicateEmailException();
    }
}
