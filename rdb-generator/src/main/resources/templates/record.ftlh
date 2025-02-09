package ${packageName};

@jakarta.persistence.Table(name = "${table.name()}")
public record ${simpleName}(
<#list fields as field>
    <#if field.primaryKey>@jakarta.persistence.Id</#if>
    <#if field.nullable>@jakarta.annotation.Nullable<#else>@jakarta.annotation.Nonnull</#if>
    ${field.type.getCanonicalName()} ${field.name}<#if field?has_next>,</#if>
</#list>
) {}