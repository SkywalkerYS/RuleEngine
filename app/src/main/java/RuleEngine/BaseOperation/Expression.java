package RuleEngine.BaseOperation;

import java.util.Map;

/**
 * Created by ShengYang on 2017/2/22.
 */
public interface Expression {
    boolean match(final Map<String, ?> inputData);
}
