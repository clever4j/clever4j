<#ftl output_format='plainText'>
package ${packageName};

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.clever4j.rdb.sql.BuildContext;
import com.clever4j.rdb.connection.ConnectionProvider;
import com.clever4j.rdb.sql.Insert;
import com.clever4j.rdb.sql.Delete;
import com.clever4j.rdb.sql.Update;
import com.clever4j.rdb.sql.Select;
import com.clever4j.rdb.sql.PostgreSqlBuilder;
import ${record.name()};

@SuppressWarnings({"DataFlowIssue", "unused", "StringBufferReplaceableByString", "DuplicatedCode"})
public class ${simpleName} {

    private final ConnectionProvider connectionProvider;
    private final PostgreSqlBuilder builder = new PostgreSqlBuilder();

    public ${simpleName}(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<${record.simpleName()}> getAll() {
        return getAll(connectionProvider.getConnection("${database.id()}"));
    }

    public List<${record.simpleName()}> getAll(Connection connection) {
        Select select = Select.build()
        <#list record.fields() as f>
            .column("${f.columnName()}")
        </#list>
            .from("${record.table().name()}");

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetch(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    public ${record.simpleName()} getById(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>) {
        return getById(<#list record.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, connectionProvider.getConnection("${database.id()}"));
    }

    @jakarta.annotation.Nullable
    public ${record.simpleName()} getById(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, Connection connection) {
        Select select = Select.build()
        <#list record.fields() as f>
            .column("${f.columnName()}")
        </#list>
            .from("${record.table().name()}")
            .where(where -> {
            <#list record.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
            </#list>
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetchOne(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    private ${record.simpleName()} fetchOne(String query, BuildContext context, Connection connection) {
        List<${record.simpleName()}> result = fetch(query, context, connection);

        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.getFirst();
        } else {
            throw new IllegalStateException("More then one ${record.simpleName()} record");
        }
    }

    private List<${record.simpleName()}> fetch(String query, BuildContext context, Connection connection) {
        List<${record.simpleName()}> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            context.prepareStatement(statement);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new ${record.simpleName()} (
                <#list record.fields() as field>
                    ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
                </#list>
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ${record.simpleName()} mapResultSet(ResultSet resultSet) {
        try {
            return new ${record.simpleName()} (
            <#list record.fields() as field>
                ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
            </#list>
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // INSERT ----------------------------------------------------------------------------------------------------------
    public void insert(${record.simpleName()} record) {
        insert(record, connectionProvider.getConnection("${database.id()}"));
    }

    public void insert(${record.simpleName()} record, Connection connection) {
        Insert insert = Insert.build()
            .into("${record.table().name()}")
            <#list record.fields() as f>
            .value("${f.columnName()}", record.${f.name()}())
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
    public void delete(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>) {
        delete(<#list record.primaryKeys() as f>${f.name()}<#if f?has_next>,</#if></#list>, connectionProvider.getConnection("${database.id()}"));
    }

    public void delete(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, Connection connection) {
        Delete delete = Delete.build()
            .table("${record.table().name()}")
            .where(where -> {
            <#list record.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
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
        deleteAll(connectionProvider.getConnection("${database.id()}"));
    }

    public void deleteAll(Connection connection) {
        Delete delete = Delete.build()
            .table("${record.table().name()}");

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
    public void update(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, ${record.simpleName()} record) {
        update(<#list record.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, record, connectionProvider.getConnection("${database.id()}"));
    }

    public void update(<#list record.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, ${record.simpleName()} record, Connection connection) {
        Update update = Update.build()
            .table("${record.table().name()}")
            <#list record.fields() as f>
            .set("${f.columnName()}", record.${f.name()}())
            </#list>
            .where(where -> {
            <#list record.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
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