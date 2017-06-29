package RuleEngine.CompareOperation;

import static RuleEngine.pub.RuleConstants.OPERATION_IN;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class In extends AbsCompareOperation {

    public static final String SYMBOL = OPERATION_IN;

    private Map<String, Set<BaseType>> compareDataSetMap = new HashMap<>();

    public In() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new In();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {

        if (compareDataSetMap == null || compareDataSetMap.size() == 0) {
            return false;
        }

        boolean isMatch = true;
        Iterator iterator = compareDataSetMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            isMatch &=  compare(key, compareDataSetMap, inputData);
        }

        return isMatch;
    }

    @Override
    protected boolean compare(String key, Map<String, ?> compareData, Map<String, ?> inputData) {
        Object obj = inputData.get(key);
        if (obj == null) {
            return false;
        }

        Set<BaseType> valueSet = (HashSet<BaseType>) compareData.get(key);

        for (BaseType baseType : valueSet) {
            if ((baseType.getType().equals(obj.getClass())) && (baseType.getValue().equals(obj))) {
                return true;
            }
        }
        return false;
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
                JSONArray valueSet = compareData.getJSONArray(key);
                Set<BaseType> dataList = new HashSet<>();
                if (valueSet != null) {
                    String dataStr = null;
                    for (int j = 0; j < valueSet.length(); j++) {
                        dataStr = valueSet.optString(j);
                        dataList.add(BaseType.getBaseType(dataStr));
                    }
                }

                compareDataSetMap.put(key, dataList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
