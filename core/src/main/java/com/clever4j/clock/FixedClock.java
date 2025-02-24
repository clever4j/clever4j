package com.clever4j.clock;

import java.time.LocalDateTime;

public class FixedClock implements Clock {

    private LocalDateTime time;

    public FixedClock(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime getNow() {
        return time;
    }
}
