package RuleEngine.CompareOperation;

import static pub.RuleConstants.OPERATION_IN;
import static pub.RuleConstants.SYMBOL_KEY;
import static pub.RuleConstants.SYMBOL_SET;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class In extends AbsCompareOperation {

    public static final String SYMBOL = OPERATION_IN;

    public In() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new In();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {

        Object obj = inputData.get(name);
        if (obj == null) {
            return false;
        }
        BaseType<?> type = null;
        for (int i = 0; i < dataList.size(); i++) {
            type = (BaseType<?>) dataList.get(i);
            if ((type.getType().equals(obj.getClass())) && (type.getValue().equals(obj))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void parseData(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        dataList = new ArrayList<>();

        name = jsonObject.optString(SYMBOL_KEY);
        JSONArray array = jsonObject.optJSONArray(SYMBOL_SET);

        String dataStr = null;
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                dataStr = array.optString(i);
                dataList.add(BaseType.getBaseType(dataStr));
            }
        }
    }
}
