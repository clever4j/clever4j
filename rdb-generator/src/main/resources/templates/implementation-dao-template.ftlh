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
import ${recordModel.name()};

@SuppressWarnings({"DataFlowIssue", "unused", "DuplicatedCode", "SqlSourceToSinkFlow"})
public abstract class ${simpleName} {

    protected final ConnectionProvider connectionProvider;
    protected final PostgreSqlBuilder builder = new PostgreSqlBuilder();

    public ${simpleName}(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<${recordModel.simpleName()}> getAll() {
        return getAll(connectionProvider.getConnection("${database.id()}"));
    }

    public List<${recordModel.simpleName()}> getAll(Connection connection) {
        Select select = Select.build()
        <#list recordModel.fields() as f>
            .column("${f.columnName()}")
        </#list>
            .from("${recordModel.table().name()}");

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetch(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    public ${recordModel.simpleName()} getById(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>) {
        return getById(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, connectionProvider.getConnection("${database.id()}"));
    }

    @jakarta.annotation.Nullable
    public ${recordModel.simpleName()} getById(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, Connection connection) {
        Select select = buildSelect()
            .where(where -> {
            <#list recordModel.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
            </#list>
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetchOne(sql, context, connection);
    }

    public ${recordModel.simpleName()} requireById(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>) {
        return requireById(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, connectionProvider.getConnection("${database.id()}"));
    }

    public ${recordModel.simpleName()} requireById(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, Connection connection) {
        ${recordModel.simpleName()} record = getById(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, connection);

        if (record == null) {
            throw new IllegalStateException("${recordModel.simpleName()} with id <#list recordModel.primaryKeys() as f>%s<#if f?has_next>, </#if></#list>.".formatted(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>));
        }

        return record;
    }

    // getByIn ---------------------------------------------------------------------------------------------------------
    <#list recordModel.fields() as f>
    protected List<${recordModel.simpleName()}> getBy${f.pascalName()}In(List<${f.type().getName()}> in, Connection connection) {
        Select select = buildSelect()
            .where(where -> {
                where.in("${f.columnName()}", in);
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);

        return fetch(sql, context, connection);
    }

    </#list>
    // -----------------------------------------------------------------------------------------------------------------
    protected Select buildSelect() {
        return Select.build()
        <#list recordModel.fields() as f>
            .column("${f.columnName()}")
        </#list>
            .from("${recordModel.table().name()}");
    }

    @jakarta.annotation.Nullable
    protected ${recordModel.simpleName()} extractOne(List<${recordModel.simpleName()}> records) {
        if (records.isEmpty()) {
            return null;
        } else if (records.size() == 1) {
            return records.getFirst();
        } else {
            throw new IllegalStateException("More then one ${recordModel.simpleName()} record");
        }
    }

    // fetch -----------------------------------------------------------------------------------------------------------
    @jakarta.annotation.Nullable
    protected ${recordModel.simpleName()} fetchOne(Select select, Connection connection) {
        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetchOne(sql, context, connection);
    }

    @jakarta.annotation.Nullable
    protected ${recordModel.simpleName()} fetchOne(String query, BuildContext context, Connection connection) {
        List<${recordModel.simpleName()}> result = fetch(query, context, connection);

        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.getFirst();
        } else {
            throw new IllegalStateException("More then one ${recordModel.simpleName()} record");
        }
    }

    protected List<${recordModel.simpleName()}> fetch(Select select, Connection connection) {
        BuildContext context = new BuildContext();
        String sql = builder.build(select, context);
        return fetch(sql, context, connection);
    }

    protected List<${recordModel.simpleName()}> fetch(String query, BuildContext context, Connection connection) {
        List<${recordModel.simpleName()}> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            context.prepareStatement(statement);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new ${recordModel.simpleName()} (
                <#list recordModel.fields() as field>
                    ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
                </#list>
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ${recordModel.simpleName()} mapResultSet(ResultSet resultSet) {
        try {
            return new ${recordModel.simpleName()} (
            <#list recordModel.fields() as field>
                ${generateCreateJavaType(field)}<#if field?has_next>,</#if>
            </#list>
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // INSERT ----------------------------------------------------------------------------------------------------------
    public void insert(${recordModel.simpleName()} record) {
        insert(record, connectionProvider.getConnection("${database.id()}"));
    }

    public void insert(${recordModel.simpleName()} record, Connection connection) {
        Insert insert = Insert.build()
            .into("${recordModel.table().name()}")
            <#list recordModel.fields() as f>
            .value("${f.columnName()}", record.${f.name()}())
            </#list>
        ;

        BuildContext context = new BuildContext();
        String sql = builder.build(insert, context);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            context.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE ----------------------------------------------------------------------------------------------------------
    public void delete(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>) {
        delete(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>,</#if></#list>, connectionProvider.getConnection("${database.id()}"));
    }

    public void delete(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, Connection connection) {
        Delete delete = Delete.build()
            .table("${recordModel.table().name()}")
            .where(where -> {
            <#list recordModel.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
            </#list>
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(delete, context);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            context.prepareStatement(statement);
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
            .table("${recordModel.table().name()}");

        BuildContext context = new BuildContext();
        String sql = builder.build(delete, context);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            context.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE ----------------------------------------------------------------------------------------------------------
    public void update(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, ${recordModel.simpleName()} record) {
        update(<#list recordModel.primaryKeys() as f>${f.name()}<#if f?has_next>, </#if></#list>, record, connectionProvider.getConnection("${database.id()}"));
    }

    public void update(<#list recordModel.primaryKeys() as f>${f.type().getName()} ${f.name()}<#if f?has_next>,</#if></#list>, ${recordModel.simpleName()} record, Connection connection) {
        Update update = Update.build()
            .table("${recordModel.table().name()}")
            <#list recordModel.fields() as f>
            .set("${f.columnName()}", record.${f.name()}())
            </#list>
            .where(where -> {
            <#list recordModel.primaryKeys() as f>
                where.equal("${f.columnName()}", ${f.name()});
            </#list>
            });

        BuildContext context = new BuildContext();
        String sql = builder.build(update, context);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            context.prepareStatement(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // connection ------------------------------------------------------------------------------------------------------
    protected Connection getConnection() {
        return connectionProvider.getConnection("${database.id()}");
    }

    // -----------------------------------------------------------------------------------------------------------------
}