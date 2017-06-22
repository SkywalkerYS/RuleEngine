package RuleEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import RuleEngine.BaseOperation.Operation;

/**
 * Created by ShengYang on 2017/2/23.
 */
public enum OperationManager {
    /**
     * 枚举类型的单例
     **/
    INSTANCE;

    /**
     * 所有支持的运算
     */
    private final Map<String, Operation> operations = new HashMap<>();

    /**
     * 添加一个运算
     */
    public void registerOperation(Operation op) {
        if (!operations.containsKey(op.getSymbol())) {
            operations.put(op.getSymbol(), op);
        }

    }

    /**
     * 增加一个运算
     * @param symbol 算法的标识
     * @return
     */
    public Operation getOperation(String symbol) {
        if (operations == null) {
            return null;
        }
        return operations.get(symbol);
    }

    /**
     * 获取所有支持的运算标识
     * @return
     */
    public Set<String> getDefinedSymbols() {
        if (operations == null) {
            return null;
        }
        return operations.keySet();
    }
}
