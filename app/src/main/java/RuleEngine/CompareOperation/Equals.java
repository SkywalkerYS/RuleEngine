package RuleEngine.CompareOperation;

import java.util.Map;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Equals extends AbsCompareOperation {

    public static final String SYMBOL = "Equals";

    public Equals() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new Equals();
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
