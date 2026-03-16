package com.rajarata.bank.utils;

import java.util.UUID;

public class IDGenerator {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
