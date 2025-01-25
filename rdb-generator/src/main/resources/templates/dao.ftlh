<#ftl output_format='plainText'>
package ${packageName};

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"DataFlowIssue", "unused", "StringBufferReplaceableByString"})
public class ${className} {

    @jakarta.annotation.Nullable
    public ${recordClassName} getById(${primaryKeyFieldParametersInline}, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ${columnsInline} ");
        query.append("FROM ${tableName} ");
        query.append("WHERE <#list primaryKeyFields as f>${f.column.name} = ?<#if f?has_next> AND </#if></#list> ");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            <#list primaryKeyFields as f>
            statement.setObject(${f?index + 1}, ${f.name});
            </#list>

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapResultSet(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<${recordClassName}> getAll(Connection connection) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT ${columnsInline} ");
        query.append("FROM ${tableName}");

        return executeQuery(query.toString(), connection);
    }

    private List<${recordClassName}> executeQuery(String query, Connection connection) {
        List<${recordClassName}> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new ${recordClassName} (
                <#list recordFields as field>
                    ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
                </#list>
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ${recordClassName} mapResultSet(ResultSet resultSet) {
        try {
            return new ${recordClassName} (
            <#list recordFields as field>
                ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
            </#list>
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // INSERT ----------------------------------------------------------------------------------------------------------
    public void insert(${recordClassName} record, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ${tableName} (${columnsInline}) VALUES (${columnsQuestionMarkJoined})");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            <#list recordFields as f>
            statement.setObject(${f?index + 1}, record.${f.name}());
            </#list>

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}