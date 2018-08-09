package com.goodgaming.test.dto;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Economy {
    private final UUID uuid;
    private final AtomicInteger balance;

    public Economy(final UUID uuid, final int balance) {
        this.uuid = uuid;
        this.balance = new AtomicInteger(balance);
    }

    public UUID getUuid() {
        return uuid;
    }

    public AtomicInteger getBalance() {
        return balance;
    }
}
