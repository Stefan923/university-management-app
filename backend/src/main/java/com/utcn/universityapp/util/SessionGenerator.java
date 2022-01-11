package com.utcn.universityapp.util;

import java.util.UUID;

public final class SessionGenerator {

    private SessionGenerator() { }

    public static String generateSession() {
        return String.valueOf(System.currentTimeMillis()).substring(8, 13)
                + UUID.randomUUID().toString().substring(1,10);
    }

}
