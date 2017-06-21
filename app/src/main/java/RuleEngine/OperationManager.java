package RuleEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.LogicOperation.And;
import RuleEngine.LogicOperation.Or;

/**
 * Created by ShengYang on 2017/2/23.
 */
public enum OperationManager {
    /**
     * 枚举类型的单例
     **/
    INSTANCE;

    private final Map<String, Operation> operations = new HashMap<>();

    public void registerOperation(Operation op) {
        if (!operations.containsKey(op.getSymbol())) {
            operations.put(op.getSymbol(), op);
        }

    }

    public Operation getOperation(String symbol) {
        if (operations == null) {
            return null;
        }
        return operations.get(symbol);
    }

    public Set<String> getDefinedSymbols() {
        if (operations == null) {
            return null;
        }
        return operations.keySet();
    }

    public boolean isLogicalOperation(Operation op) {
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
