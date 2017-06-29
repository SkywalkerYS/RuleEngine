package RuleEngine.CompareOperation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;
import RuleEngine.LogicOperation.AbsLogicOperation;

/**
 * Created by ShengYang on 2017/3/5.
 * 比较运算，包括 >, =, <, In，父节点为逻辑运算与或非，或者没有，没有父节点，说明条件只有一个
 */

public abstract class AbsCompareOperation extends Operation {

//    protected String name;
//    protected BaseType value;
    protected Map<String, BaseType> compareDataMap = new HashMap<>();
    private AbsLogicOperation parent;

    public AbsCompareOperation(String symbol) {
        super(symbol);
    }

    @Override
    public Operation copy() {
        return null;
    }

    @Override
    public void parseData(final JSONObject root) {
        if (root == null) {
            return;
        }
        try {
            JSONObject compareData = root.getJSONObject(symbol);
            JSONArray compareNames = compareData.names();
            int mapSize = compareNames.length();
            for (int i = 0; i < mapSize; i++) {
                String key = compareNames.optString(i);
                String valueStr = compareData.optString(key);
                BaseType value = BaseType.getBaseType(valueStr);
                compareDataMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        if (compareDataMap == null || compareDataMap.size() == 0) {
            return false;
        }

        boolean isMatch = true;
        Iterator iterator = compareDataMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            isMatch &=  compare(key, compareDataMap, inputData);
        }

        return isMatch;
    }

    protected abstract boolean compare(String key, Map<String, ?> compareData, Map<String, ?> inputData);

    @Override
    public void setParent(AbsLogicOperation parent) {
        this.parent = parent;
    }

    @Override
    public AbsLogicOperation getParent() {
        return parent;
    }
}
