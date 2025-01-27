<#ftl output_format='plainText'>
package ${packageName};

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"DataFlowIssue", "unused", "StringBufferReplaceableByString", "DuplicatedCode"})
public class ${name} {

    @jakarta.annotation.Nullable
    public ${recordName} getById(${primaryKeyFieldParametersInline}, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ${columnsInline} ");
        query.append("FROM ${tableName} ");
        query.append("WHERE <#list primaryKeyFields as f>${f.columnName} = ?<#if f?has_next> AND </#if></#list> ");

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

<#list recordFields as f>
    public List<${recordName}> ${f.getByInName()}(${f.type.getCanonicalName()} in, Connection connection) {
        return ${f.getByInName()}(List.of(in), connection);
    }

    public List<${recordName}> ${f.getByInName()}(List<${f.type.getCanonicalName()}> in, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ${columnsInline} ");
        query.append("FROM ${tableName} ");
        query.append("WHERE ${f.columnName} IN (");

        for (int i = 0; i < in.size(); i++) {
            query.append("?");

            if (i < in.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            List<${recordName}> result = null;

            for (int i = 0; i < in.size(); i++) {
                statement.setObject(i + 1, in.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (result == null) {
                    result = new ArrayList<>();
                }

                result.add(new ${recordName} (
                <#list recordFields as field>
                    ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
                </#list>
                ));
            }

            return result != null ? result : Collections.emptyList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

</#list>
    public List<${recordName}> getAll(Connection connection) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT ${columnsInline} ");
        query.append("FROM ${tableName}");

        return executeQuery(query.toString(), connection);
    }

    private List<${recordName}> executeQuery(String query, Connection connection) {
        List<${recordName}> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new ${recordName} (
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

    private ${recordName} mapResultSet(ResultSet resultSet) {
        try {
            return new ${recordName} (
            <#list recordFields as field>
                ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
            </#list>
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // INSERT ----------------------------------------------------------------------------------------------------------
    public void insert(${recordName} record, Connection connection) {
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

    // DELETE ----------------------------------------------------------------------------------------------------------
    public void delete(${primaryKeyFieldParametersInline}, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ${tableName}");
        query.append("WHERE <#list primaryKeyFields as f>${f.columnName} = ?<#if f?has_next> AND </#if></#list>");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
        <#list primaryKeyFields as f>
            statement.setObject(${f?index + 1}, ${f.name});
        </#list>
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE ----------------------------------------------------------------------------------------------------------
    public void update(${recordName} record, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ${tableName} SET ");
    <#list recordFields as f>
        query.append("${f.name} = ?<#if f?has_next>,</#if> ");
    </#list>
        query.append("WHERE <#list primaryKeyFields as f>${f.columnName} = ?<#if f?has_next> AND </#if></#list>");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
        <#list recordFields as f>
            statement.setObject(${f?index + 1}, record.${f.name}());
        </#list>
        <#list primaryKeyFields as f>
            statement.setObject(${f?index + recordFieldsSize + 1}, record.${f.name}());
        </#list>
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}