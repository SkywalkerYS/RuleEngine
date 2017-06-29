package RuleEngine.CompareOperation;

import static RuleEngine.pub.RuleConstants.OPERATION_EQUALS;

import java.util.Map;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Equals extends AbsCompareOperation {

    public static final String SYMBOL = OPERATION_EQUALS;

    public Equals() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new Equals();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        return super.match(inputData);
    }

    protected boolean compare (String key, Map<String, ?> compareData, Map<String, ?> inputData) {
        Object obj = inputData.get(key);
        if (obj == null) {
            return false;
        }
        BaseType type = (BaseType) compareData.get(key);

        if ((type.getType().equals(obj.getClass()))) {
            if (type.getType().equals(Integer.class)) {
                if (obj == type.getValue()) {
                    return true;
                }
            } else if (type.getType().equals(Float.class)) {
                if (obj == type.getValue()) {
                    return true;
                }
            } else if (type.getType().equals(String.class)) {
                if (obj.equals(type.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }
}
