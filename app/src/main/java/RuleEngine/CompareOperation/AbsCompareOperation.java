package RuleEngine.CompareOperation;

import java.util.List;

import org.json.JSONObject;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/3/5.
 */

public abstract class AbsCompareOperation extends Operation {

    public static final String SYMBOL_KEY = "key";
    public static final String SYMBOL_VALUE = "value";
    public static final String SYMBOL_SET = "set";

    protected String name;
    protected BaseType value;
    protected List<BaseType> dataList;

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

}
