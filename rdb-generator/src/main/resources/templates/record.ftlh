package ${packageName};

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
<#list database.recordAnnotations() as a>@${a}</#list>
@Table(name = "${table.name()}")
public record ${simpleName}(

<#list fields as field>
    <#if field.primaryKey>@Id</#if>
    <#if field.nullable>@Nullable<#else>@Nonnull</#if>
    @Column(name = "${field.name()}")
    ${field.type.getCanonicalName()} ${field.name}<#if field?has_next>,</#if>
</#list>
) {}