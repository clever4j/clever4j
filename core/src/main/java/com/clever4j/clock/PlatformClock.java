package com.clever4j.clock;

import java.time.LocalDateTime;

public class PlatformClock implements Clock {

    @Override
    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
