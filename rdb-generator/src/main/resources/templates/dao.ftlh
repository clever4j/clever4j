<#ftl output_format='plainText'>
package ${packageName};

import ${baseDaoModel.name()};

<#list database.recordAnnotations() as a>@${a}</#list>
@SuppressWarnings({"unused"})
public interface ${simpleName} extends ${baseDaoModel.simpleName()} {

}