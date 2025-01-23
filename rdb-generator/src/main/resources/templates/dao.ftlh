package ${packageName};

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ${className} {

    public List<${recordGenerator.className}> getAll(Connection connection) {
        StringBuilder sql = new StringBuilder();
        List<${recordGenerator.className}> result = new ArrayList<>();

        sql.append("SELECT ");
        <#list recordGenerator.fields as field>
        sql.append("${field.colum} ");
        </#list>
        sql.append("FROM ${recordGenerator.table.name}");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new AuthSessionRecord(
                    <#list recordGenerator.fields as field>
                        sql.append("${field.colum}")<#if field?has_next>,</#if>
                    </#list>
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}