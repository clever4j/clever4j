package ${package};

@jakarta.persistence.Table(name = "${tableName}")
public record ${className}(

    @jakarta.persistence.Id
    @jakarta.annotation.Nonnull
    String authSessionId,

    @jakarta.annotation.Nonnull
    String userId,

    @jakarta.annotation.Nonnull
    java.time.LocalDateTime createdAt,

    @jakarta.annotation.Nonnull
    java.time.LocalDateTime modifiedAt,

    @jakarta.annotation.Nonnull
    java.time.LocalDateTime expiresAt,

    @jakarta.annotation.Nullable
    String ipAddress,

    @jakarta.annotation.Nullable
    String userAgent,

    @jakarta.annotation.Nonnull
    Boolean active,

    @jakarta.annotation.Nonnull
    java.time.LocalDateTime terminatedAt,

    @jakarta.annotation.Nonnull
    String token,

    @jakarta.annotation.Nullable
    String deviceInfo,

    @jakarta.annotation.Nullable
    String location,

    @jakarta.annotation.Nonnull
    Integer failedAttempts,

    @jakarta.annotation.Nonnull
    String sessionType,

    @jakarta.annotation.Nonnull
    String loginMethod,

    @jakarta.annotation.Nullable
    String authorities
) {}
