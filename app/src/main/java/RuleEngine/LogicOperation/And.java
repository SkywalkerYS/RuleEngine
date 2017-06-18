package RuleEngine.LogicOperation;

import java.util.Map;

import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class And extends AbsLogicOperation {

    public static final String SYMBOL = "And";

    public And() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new And();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
//        return leftOperand.match(conditions) && rightOperand.match(conditions);
        boolean result = false;
        if (childOperand != null && childOperand.size() > 0) {
            result = childOperand.get(0).match(inputData);
            for (int i = 1; i < childOperand.size(); i++) {
                result &= childOperand.get(i).match(inputData);
            }
        }

        return result;

    }

}
