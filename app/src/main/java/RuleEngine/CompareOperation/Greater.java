package RuleEngine.CompareOperation;

import static pub.RuleConstants.OPERATION_GREATER;

import java.util.Map;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Greater extends AbsCompareOperation {
    public static final String SYMBOL = OPERATION_GREATER;

    public Greater() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new Greater();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        Object obj = inputData.get(name);
        if (obj == null) {
            return false;
        }
        BaseType type = value;
        if ((type.getType().equals(obj.getClass()))) {
            if (type.getType().equals(Integer.class)) {
                if ((Integer) obj > (Integer) type.getValue()) {
                    return true;
                }
            } else if (type.getType().equals(Float.class)) {
                if ((Float) obj > (Float) type.getValue()) {
                    return true;
                }
            }
        }

        return false;
    }
}
