package RuleTree;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.interfaces.IExpression;
import RuleEngine.interfaces.IParent;

/**
 * Created by ShengYang on 2017/6/28.
 */

public class RuleFilter implements IExpression, IParent<Rule> {

    private JSONObject result;

    private JSONArray filterData;

    private Operation rootOperation;

    private Rule parent;

    public RuleFilter() {
    }

    @Override
    public void setParent(Rule parent) {
        this.parent = parent;
    }

    @Override
    public Rule getParent() {
        return parent;
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        return rootOperation.match(inputData);
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public void setRootOperation(Operation operation) {
        this.rootOperation = operation;
    }

    public void setFilterData(JSONArray filterData) {
        this.filterData = filterData;
    }
}
