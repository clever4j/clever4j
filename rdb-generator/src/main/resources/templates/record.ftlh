package ${_package};

@jakarta.persistence.Table(name = "${table}")
public record ${_class}(
<#list fields as field>
    <#if field.primaryKey>@jakarta.persistence.Id</#if>
    <#if field.nullable>@jakarta.annotation.Nullable<#else>@jakarta.annotation.Nonnull</#if>
    ${field.type} ${field.name}<#if field?has_next>,</#if>
</#list>
) {}