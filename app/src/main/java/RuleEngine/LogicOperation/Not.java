package RuleEngine.LogicOperation;

import java.util.Map;

import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Not extends AbsLogicOperation {

    public static final String SYMBOL = "Not";

    public Not() {
        super(SYMBOL);
    }

    @Override
    public Operation copy() {
        return new Not();
    }

    @Override
    public boolean match(Map<String, ?> inputData) {
        // todo 非的定义
        return false;
    }

}
