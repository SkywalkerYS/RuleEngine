package RuleEngine.LogicOperation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Expression;
import RuleEngine.BaseOperation.Operation;
import RuleEngine.OperationManager;

/**
 * Created by ShengYang on 2017/3/5.
 */

public abstract class AbsLogicOperation extends Operation {
    // json格式，某个操作数的下的数组中每个都进行该操作

    protected ArrayList<Expression> childOperand = null;

    public AbsLogicOperation(String symbol) {
        super(symbol);
    }

    @Override
    public Operation copy() {
        return null;
    }

    @Override
    public void parseData(final JSONObject root) {
        if (root == null) {
            return;
        }
        OperationManager operations = OperationManager.INSTANCE;
        childOperand = new ArrayList<Expression>();
        try {
            JSONArray childOp = root.names();
            JSONObject jsonObject = null;
            String operationSymbol = null;
            if (childOp != null) {
                for (int i = 0; i < childOp.length(); i++) {
                    operationSymbol = childOp.optString(i);
                    jsonObject = root.optJSONObject(operationSymbol);
                    if (root.get(operationSymbol) != null) {
                        Operation op = operations.getOperation(operationSymbol);
                        if (op != null) {
                            op = op.copy();
                            childOperand.add(op);
                            op.parseData(jsonObject);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
