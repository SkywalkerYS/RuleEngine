package RuleEngine.LogicOperation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Operation;
import RuleEngine.OperationManager;
import RuleEngine.interfaces.IComposition;

/**
 * Created by ShengYang on 2017/3/5.
 */

public abstract class AbsLogicOperation extends Operation implements IComposition<Operation>{
    // json格式，某个操作数的下的数组中每个都进行该操作

    protected ArrayList<Operation> childOperand = null;

    private AbsLogicOperation parent;

    public AbsLogicOperation(String symbol) {
        super(symbol);
    }

    @Override
    public Operation copy() {
        return null;
    }

    @Override
    public void parseData(final JSONArray logicOpRoot) {
        if (logicOpRoot == null) {
            return;
        }
        OperationManager operations = OperationManager.INSTANCE;
        childOperand = new ArrayList<>();
        try {
            JSONArray operationJsonArray = null;
            String operationSymbol = null;
            for (int i = 0; i < logicOpRoot.length(); i++) {
                JSONObject operationJsonObject = logicOpRoot.getJSONObject(i);
                JSONArray names = operationJsonObject.names();
                // 该JSONObject只有一个操作符属性
                operationSymbol = names.optString(0);
                operationJsonArray = operationJsonObject.optJSONArray(operationSymbol);

                Operation operation = operations.getOperation(operationSymbol);
                if (operation != null) {
                    operation = operation.copy();
                    addChild(operation);
                    operation.setParent(this);
                    operation.parseData(operationJsonArray);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setParent(AbsLogicOperation parent) {
        this.parent = parent;
    }

    @Override
    public AbsLogicOperation getParent() {
        return parent;
    }

    @Override
    public void addChild(Operation child) {
        childOperand.add(child);
    }

    @Override
    public List<Operation> getChildren() {
        return childOperand;
    }

    @Override
    public String toString() {
        return "AbsLogicOperation{" +
                "childOperand=" + childOperand.toString() +
                ", parent=" + parent +
                '}';
    }
}
