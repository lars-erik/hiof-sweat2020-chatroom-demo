package org.hiof.chatroom.core;

import java.time.Instant;
import java.util.concurrent.Callable;

public class TimeFactory {
    public static Callable<Instant> nowFactory = () -> Instant.now();
    public static Instant now() throws Exception { return nowFactory.call(); }
    public static void reset() {
        nowFactory = () -> Instant.now();
    }
}
