package RuleEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import RuleEngine.BaseOperation.Expression;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Rule {

    private String name;
    private List<Expression> expressions;
    private IMatchAction matchAction;
    private JSONObject result;

    public Rule(String name, List<Expression> expressions, IMatchAction matchAction, JSONObject result) {
        this.name = name;
        this.expressions = expressions;
        this.matchAction = matchAction;
        this.result = result;
    }

    public static class Builder {
        private List<Expression> expressions = new ArrayList<>();
        private IMatchAction matchAction;
        private JSONObject result = null;
        private String name = null;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withExpression(Expression expr) {
            expressions.add(expr);
            return this;
        }

        public Builder withAction(IMatchAction matchAction) {
            this.matchAction = matchAction;
            return this;
        }

        public Builder withResult(JSONObject result) {
            this.result = result;
            return this;
        }

        public Rule build() {
            return new Rule(name, expressions, matchAction, result);
        }
    }

    public boolean eval(Map<String, ?> bindings) {
        boolean ruleEval = true;
        for (Expression expression : expressions) {
            boolean expressionEval = expression.match(bindings);
            ruleEval &= expressionEval;
        }
        if (matchAction != null) {
            if (ruleEval) {
                matchAction.successAction();
            } else {
                matchAction.failAction();
            }
        }
        return ruleEval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public IMatchAction getMatchAction() {
        return matchAction;
    }

    public void setMatchAction(IMatchAction matchAction) {
        this.matchAction = matchAction;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }
}
