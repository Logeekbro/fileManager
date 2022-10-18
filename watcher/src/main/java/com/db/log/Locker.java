package com.db.log;

import java.util.concurrent.atomic.AtomicBoolean;

public class Locker {

    private final AtomicBoolean sign = new AtomicBoolean(false);

    public void lock(){
        while (!this.sign.compareAndSet(false, true));
    }

    public void unLock(){
        this.sign.set(false);
    }
}
