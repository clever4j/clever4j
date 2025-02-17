<#ftl output_format='plainText'>
package ${packageName};

import com.clever4j.rdb.connection.ConnectionProvider;
import ${baseImplementationDaoModel.name()};

<#list database.baseImplementationDaoAnnotations() as a>@${a}</#list>
@SuppressWarnings({"unused", "DuplicatedCode"})
public class ${simpleName} extends ${baseImplementationDaoModel.simpleName()} {
    public ${simpleName}(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }
}