package pub;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.LogicOperation.And;
import RuleEngine.LogicOperation.Or;

/**
 * Created by ShengYang on 2017/6/22.
 */

public class RuleUtils {

    public static boolean isLogicalOperation(Operation op) {
        if (op == null) {
            return false;
        }
        String operationSymbol = op.getSymbol();
        if (operationSymbol.equals(And.SYMBOL) || operationSymbol.equals(Or.SYMBOL)) {
            return true;
        }

        return false;
    }
}
