package RuleEngine.LogicOperation;

import static RuleEngine.pub.RuleConstants.OPERATION_OR;

import java.util.List;
import java.util.Map;

import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Or extends AbsLogicOperation {
    public static final String SYMBOL = OPERATION_OR;

    public Or() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new Or();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        boolean result = false;
        if (childOperand != null && childOperand.size() > 0) {
            result = childOperand.get(0).match(inputData);
            for (int i = 1; i < childOperand.size(); i++) {
                result |= childOperand.get(i).match(inputData);
            }
        }

        return result;
    }

    public List<Operation> getChildren() {
        return childOperand;
    }
}
