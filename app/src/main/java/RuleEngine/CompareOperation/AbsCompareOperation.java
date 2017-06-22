package RuleEngine.CompareOperation;

import static RuleEngine.pub.RuleConstants.SYMBOL_KEY;
import static RuleEngine.pub.RuleConstants.SYMBOL_VALUE;

import java.util.List;

import org.json.JSONObject;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;
import RuleTree.Rule;

/**
 * Created by ShengYang on 2017/3/5.
 */

public abstract class AbsCompareOperation extends Operation {

    protected String name;
    protected BaseType value;
    protected List<BaseType> dataList;

    private Rule parent;

    public AbsCompareOperation(String symbol) {
        super(symbol);
    }

    @Override
    public Operation copy() {
        return null;
    }

    @Override
    public void parseData(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        name = jsonObject.optString(SYMBOL_KEY);
        String valueStr = jsonObject.optString(SYMBOL_VALUE);
        value = BaseType.getBaseType(valueStr);
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
    public String toString() {
        return "key: " + name + " value: " + value + "\n" + "dataList: " + dataList;
    }
}
