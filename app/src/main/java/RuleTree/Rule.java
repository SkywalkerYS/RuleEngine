package RuleTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.interfaces.IComposition;
import RuleEngine.interfaces.IMatchAction;
import RuleEngine.interfaces.IParent;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Rule implements IComposition<Operation>, IParent<RuleSet> {

    // 规则的名称
    private String name;
    // 是否命中的回调
    private IMatchAction matchAction;
    // 命中后的处理结果
    private JSONObject result;
    // 父节点，属于哪个规则集合
    private RuleSet parent;
    // 子节点，该Rule的所有操作
    private List<Operation> childOperations = new ArrayList<>();

    public Rule(String name, List<Operation> operations, IMatchAction matchAction, JSONObject result) {
        this.name = name;
        this.childOperations = operations;
        this.matchAction = matchAction;
        this.result = result;
    }

    public static class Builder {
        private List<Operation> operations = new ArrayList<>();
        private IMatchAction matchAction;
        private JSONObject result = null;
        private String name = null;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withExpression(Operation operate) {
            operations.add(operate);
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
            return new Rule(name, operations, matchAction, result);
        }
    }

    public boolean eval(Map<String, ?> bindings) {
        boolean ruleEval = true;
        for (Operation operation : childOperations) {
            boolean operationEval = operation.match(bindings);
            ruleEval &= operationEval;
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

    public JSONObject getResult() {
        return result;
    }

    @Override
    public void setParent(RuleSet parent) {
        this.parent = parent;
    }

    @Override
    public RuleSet getParent() {
        return parent;
    }

    @Override
    public void addChild(Operation operation) {
        if (childOperations != null) {
            childOperations.add(operation);
            operation.setParent(this);
        }
    }

    @Override
    public List<Operation> getChildren() {
        return childOperations;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", childOperations=" + childOperations +
                '}';
    }
}
