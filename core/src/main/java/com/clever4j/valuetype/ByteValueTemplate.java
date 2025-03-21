package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
@AllNonnullByDefault
public abstract class ByteValueTemplate<T extends ByteValueTemplate<T>> extends LongValueTemplate<T> {

    protected ByteValueTemplate(@Nullable Long value) {
        super(value);
    }

    @Nullable
    public Long getBytes() {
        return getValue();
    }

    public Long getBytesOrDefault(long defaultValue) {
        return getValueOrDefault(defaultValue);
    }

    public Long getBytesOrZero() {
        return getValueOrZero();
    }

    @Nullable
    public Long getKilobytes() {
        return convertUnit(1024L);
    }

    public Long getKilobytesOrDefault(long defaultValue) {
        return convertUnitOrDefault(1024L, defaultValue);
    }

    public Long getKilobytesOrZero() {
        return convertUnitOrZero(1024L);
    }

    @Nullable
    public Long getMegabytes() {
        return convertUnit(1024L * 1024);
    }

    public Long getMegabytesOrDefault(long defaultValue) {
        return convertUnitOrDefault(1024L * 1024, defaultValue);
    }

    public Long getMegabytesOrZero() {
        return convertUnitOrZero(1024L * 1024);
    }

    @Nullable
    public Long getGigabytes() {
        return convertUnit(1024L * 1024 * 1024);
    }

    public Long getGigabytesOrDefault(long defaultValue) {
        return convertUnitOrDefault(1024L * 1024 * 1024, defaultValue);
    }

    public Long getGigabytesOrZero() {
        return convertUnitOrZero(1024L * 1024 * 1024);
    }

    @Nullable
    public Long getTerabytes() {
        return convertUnit(1024L * 1024 * 1024 * 1024);
    }

    public Long getTerabytesOrDefault(long defaultValue) {
        return convertUnitOrDefault(1024L * 1024 * 1024 * 1024, defaultValue);
    }

    public Long getTerabytesOrZero() {
        return convertUnitOrZero(1024L * 1024 * 1024 * 1024);
    }

    @Nullable
    private Long convertUnit(long factor) {
        Long value = getValue();
        return (value != null) ? value / factor : null;
    }

    private Long convertUnitOrDefault(long factor, long defaultValue) {
        return getValueOrDefault(defaultValue) / factor;
    }

    private Long convertUnitOrZero(long factor) {
        return getValueOrZero() / factor;
    }
}
