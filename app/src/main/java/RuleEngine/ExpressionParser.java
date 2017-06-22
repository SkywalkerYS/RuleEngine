package RuleEngine;

import static RuleEngine.pub.RuleConstants.SYMBOL_RESULT;

import org.json.JSONArray;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.pub.RuleUtils;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class ExpressionParser {

    private static final OperationManager operations = OperationManager.INSTANCE;

    /**
     * 递归解析，白名单中的某个规则
     * @param root
     * @return 返回一个根节点的操作
     */
    public static Operation parse(JSONObject root) {
        if (root == null) {
            return null;
        }
        JSONArray childOp = root.names();
        String operationName = null;
        JSONObject jsonObject = null;
        Operation rootOperation = null;
        for (int i = 0; i < childOp.length(); i++) {
            operationName = childOp.optString(i);
            if (operationName.equals(SYMBOL_RESULT)) {
                continue;
            }

            jsonObject = root.optJSONObject(operationName);
            if (jsonObject == null) {
                return null;
            }

            rootOperation = operations.getOperation(operationName);

            if (rootOperation != null
                    && RuleUtils.isLogicalOperation(rootOperation)) {

                rootOperation.parseData(jsonObject);
                // 逻辑运算需要递归
                parse(jsonObject);
            } else {
                Operation operation = operations.getOperation(operationName);
                if (operation != null) {
                    operation.parseData(jsonObject);
                }
            }
        }

        return rootOperation;
    }
}
