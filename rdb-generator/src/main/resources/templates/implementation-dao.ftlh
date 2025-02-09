<#ftl output_format='plainText'>
package ${packageName};

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clever4j.rdb.sql.BuildContext;
import com.clever4j.rdb.connection.ConnectionProvider;
import com.clever4j.rdb.sql.Insert;
import com.clever4j.rdb.sql.Delete;
import com.clever4j.rdb.sql.Update;
import com.clever4j.rdb.sql.Select;
import com.clever4j.rdb.sql.PostgreSqlBuilder;

@SuppressWarnings({"DataFlowIssue", "unused", "StringBufferReplaceableByString", "DuplicatedCode"})
public class ${name} {

    private final ConnectionProvider connectionProvider;
    private final PostgreSqlBuilder builder = new PostgreSqlBuilder();

    public ${name}(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<${recordName}> getAll() {
        return getAll(connectionProvider.getConnection());
    }

    public List<${recordName}> getAll(Connection connection) {
        Select select = Select.build()
        <#list recordFields as f>
            .column("${f.columnName}")
        </#list>
            .from("${tableName}");

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetch(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    public ${recordName} getById(${primaryKeyFieldParametersInline}) {
        return getById(<#list primaryKeyFields as f>${f.name}<#if f?has_next>,</#if></#list>, connectionProvider.getConnection());
    }

    @jakarta.annotation.Nullable
    public ${recordName} getById(${primaryKeyFieldParametersInline}, Connection connection) {
        Select select = Select.build()
        <#list recordFields as f>
            .column("${f.columnName}")
        </#list>
            .from("${tableName}")
            .where(where -> {
            <#list primaryKeyFields as f>
                where.equal("${f.columnName}", ${f.name})<#if !f?has_next>;</#if>
            </#list>
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetchOne(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    private ${recordName} fetchOne(String query, BuildContext context, Connection connection) {
        List<${recordName}> result = fetch(query, context, connection);

        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.getFirst();
        } else {
            throw new IllegalStateException("More then one ${recordName} record");
        }
    }

    private List<${recordName}> fetch(String query, BuildContext context, Connection connection) {
        List<${recordName}> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            context.prepareStatement(statement);

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
    public void insert(${recordName} record) {
        insert(record, connectionProvider.getConnection());
    }

    public void insert(${recordName} record, Connection connection) {
        Insert insert = Insert.build()
            .into("${tableName}")
            <#list recordFields as f>
            .value("${f.columnName}", record.${f.name}())
            </#list>
        ;

        BuildContext buildContext = new BuildContext();
        String sql = builder.build(insert, buildContext);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            buildContext.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE ----------------------------------------------------------------------------------------------------------
    public void delete(${primaryKeyFieldParametersInline}) {
        delete(<#list primaryKeyFields as f>${f.name}<#if f?has_next>,</#if></#list>, connectionProvider.getConnection());
    }

    public void delete(${primaryKeyFieldParametersInline}, Connection connection) {
        Delete delete = Delete.build()
            .table("${tableName}")
            .where(where -> {
            <#list primaryKeyFields as f>
                where.equal("${f.columnName}", ${f.name})<#if !f?has_next>;</#if>
            </#list>
            });

        BuildContext buildContext = new BuildContext();
        String sql = builder.build(delete, buildContext);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            buildContext.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        deleteAll(connectionProvider.getConnection());
    }

    public void deleteAll(Connection connection) {
        Delete delete = Delete.build()
            .table("${tableName}");

        BuildContext buildContext = new BuildContext();
        String sql = builder.build(delete, buildContext);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            buildContext.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE ----------------------------------------------------------------------------------------------------------
    public void update(${primaryKeyFieldParametersInline}, ${recordName} record) {
        update(<#list primaryKeyFields as f>${f.name}<#if f?has_next>,</#if></#list>, record, connectionProvider.getConnection());
    }

    public void update(${primaryKeyFieldParametersInline}, ${recordName} record, Connection connection) {
        Update update = Update.build()
            .table("${tableName}")
            <#list recordFields as f>
            .set("${f.columnName}", record.${f.name}())
            </#list>
            .where(where -> {
            <#list primaryKeyFields as f>
                where.equal("${f.columnName}", ${f.name})<#if !f?has_next>;</#if>
            </#list>
            });

        BuildContext buildContext = new BuildContext();
        String sql = builder.build(update, buildContext);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            buildContext.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}