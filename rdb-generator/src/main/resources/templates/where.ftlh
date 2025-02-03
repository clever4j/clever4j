package ${packageName};

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "DuplicatedCode"})
public class ${name} {

    private WhereOperator operator;
    private final List<${name}> where = new ArrayList<>();

    // WHERE IN
    <#list fields as f>
    private List<${f.type.getCanonicalName()}> ${f.name}In = new ArrayList<>();

    private List<${f.type.getCanonicalName()}> ${f.name}Like = new ArrayList<>();
    private WhereOperator ${f.name}LikeOperator = WhereOperator.AND;

    private List<${f.type.getCanonicalName()}> ${f.name}Gte = new ArrayList<>();
    private WhereOperator ${f.name}Operator = WhereOperator.AND;

    </#list>
    private ${name}(WhereOperator operator) {
        this.operator = operator;
    }

    public static ${name} buildOr() {
        return new ${name}(WhereOperator.OR);
    }

    public static ${name} buildAnd() {
        return new ${name}(WhereOperator.AND);
    }

    public ${name} operator(WhereOperator operator) {
        this.operator = operator;
        return this;
    }

    <#list fields as f>
    public ${name} ${f.name}In(List<${f.type.getCanonicalName()}> in) {
        this.${f.name}In = in;
        return this;
    }

    public ${name} ${f.name}Like(List<${f.type.getCanonicalName()}> like, WhereOperator operator) {
        this.${f.name}Like = like;
        this.${f.name}LikeOperator = operator;
        return this;
    }

    public ${name} ${f.name}Gte(List<${f.type.getCanonicalName()}> gte, WhereOperator operator) {
        this.${f.name}Gte = gte;
        this.${f.name}GteO = operator;
        return this;
    }

    </#list>
    public ${name} or() {
        ${name} where = new ${name}(WhereOperator.OR);
        this.where.add(where);
        return where;
    }

    public ${name} and() {
        ${name} where = new ${name}(WhereOperator.AND);
        this.where.add(where);
        return where;
    }

    public String build(List<Object> statementObjects) {
        StringBuilder query = new StringBuilder();

        <#list fields as f>
        buildIn("${f.name}", this.${f.name}In, query, statementObjects);
        buildCondition("${f.name}", "LIKE", this.${f.name}Like, this.${f.name}LikeOperator, query, statementObjects);

        </#list>

        if (!where.isEmpty()) {
            if (!query.isEmpty()) {
                query.append(" ").append(operator.name()).append(" ");
            }

            for (int i = 0; i < where.size(); i++) {
                query.append("(").append(where.get(i).build(statementObjects)).append(")");

                if (i < where.size() - 1) {
                    query.append(" ").append(operator.name()).append(" ");
                }
            }
        }

        return query.toString();
    }

    private void buildIn(String column, List<?> values, StringBuilder query, List<Object> statementObjects) {
        if (values.isEmpty()) {
            return;
        }

        if (!query.isEmpty()) {
            query.append(" ").append(operator.name()).append(" ");
        }

        query.append(column).append(" IN (");

        for (int i = 0; i < values.size(); i++) {
            query.append("?");

            if (i < values.size() - 1) {
                query.append(", ");
            }

            statementObjects.add(values.get(i));
        }

        query.append(")");
    }

    private void buildCondition(String column, String condition, List<?> values, WhereOperator operator, StringBuilder query, List<Object> statementObjects) {
        if (values.isEmpty()) {
            return;
        }

        if (!query.isEmpty()) {
            query.append(" ").append(operator.name()).append(" ");
        }

        query.append("(");

        for (int i = 0; i < values.size(); i++) {
            query.append(column).append(" ").append(condition).append(" ?");

            statementObjects.add(values.get(i));

            if (i < values.size() - 1) {
                query.append(" ").append(operator.name()).append(" ");
            }
        }

        query.append(")");
    }
}