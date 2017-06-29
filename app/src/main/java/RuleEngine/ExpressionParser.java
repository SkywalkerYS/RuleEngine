package RuleEngine;

import static RuleEngine.pub.RuleConstants.SYMBOL_RESULT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.pub.RuleUtils;
import RuleTree.RuleFilter;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class ExpressionParser {

    private static final OperationManager operations = OperationManager.INSTANCE;

    /**
     * 递归解析，白名单中的某个规则
     *
     * @param root
     *
     * @return 返回一个根节点的操作
     */
    public static RuleFilter parseFilter(JSONObject root) {
        if (root == null) {
            return null;
        }

        JSONArray childNames = root.names();
        String operationName = null;
        JSONObject jsonObject = null;
        //        Operation rootOperation = null;
        RuleFilter ruleFilter = new RuleFilter();
        for (int i = 0; i < childNames.length(); i++) {

            operationName = childNames.optString(i);

            if (operationName.equals(SYMBOL_RESULT)) {
                jsonObject = root.optJSONObject(operationName);
                ruleFilter.setResult(jsonObject);
                continue;
            }

            String rootOperationName = operationName;

            if (operations.getOperation(rootOperationName) == null) {
                return null;
            }

            Operation operation = operations.getOperation(rootOperationName).copy();

            try {
                JSONArray childOp = (JSONArray) root.get(rootOperationName);

                if (childOp == null || operation == null) {
                    return null;
                }

                operation.parseData(childOp);
                ruleFilter.setFilterData(childOp);
                ruleFilter.setRootOperation(operation);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return ruleFilter;
    }


    private static void parseOperation(Operation operation, JSONArray root) {
        if (root == null) {
            return;
        }

        if (RuleUtils.isLogicalOperation(operation)) {
            operation.parseData(root);
            parseOperation(operation,root);
        } else {
            operation.parseData(root);
        }

    }
}
