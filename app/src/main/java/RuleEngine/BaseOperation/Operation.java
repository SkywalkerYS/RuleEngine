package RuleEngine.BaseOperation;

import org.json.JSONObject;

/**
 * Created by ShengYang on 2017/2/23.
 */
public abstract class Operation implements Expression {
    protected String symbol;

    public Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract Operation copy();

    public abstract void parseData(final JSONObject jsonObject);
}
