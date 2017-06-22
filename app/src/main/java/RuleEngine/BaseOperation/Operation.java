package RuleEngine.BaseOperation;

import org.json.JSONObject;

import RuleEngine.interfaces.IExpression;
import RuleEngine.interfaces.IParent;
import RuleTree.Rule;

/**
 * Created by ShengYang on 2017/2/23.
 */
public abstract class Operation implements IExpression, IParent<Rule> {
    protected String symbol;

    public Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract Operation copy();

    public abstract void parseData(final JSONObject jsonObject);

    @Override
    public String toString() {
        return symbol;
    }
}
