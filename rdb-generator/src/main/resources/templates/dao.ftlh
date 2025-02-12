<#ftl output_format='plainText'>
package ${packageName};

import java.sql.Connection;
import java.util.List;

@SuppressWarnings({"DataFlowIssue", "unused", "StringBufferReplaceableByString", "DuplicatedCode"})
public interface ${simpleName} {

    List<${recordName}> getAll();

    List<${recordName}> getAll(Connection connection);

    @jakarta.annotation.Nullable
    ${recordName} getById(${primaryKeyFieldParametersInline});

    @jakarta.annotation.Nullable
    ${recordName} getById(${primaryKeyFieldParametersInline}, Connection connection);

    void insert(${recordName} record);

    void insert(${recordName} record, Connection connection);

    void delete(${primaryKeyFieldParametersInline});

    void delete(${primaryKeyFieldParametersInline}, Connection connection);

    void deleteAll();

    void deleteAll(Connection connection);

    void update(${primaryKeyFieldParametersInline}, ${recordName} record);

    void update(${primaryKeyFieldParametersInline}, ${recordName} record, Connection connection);
}