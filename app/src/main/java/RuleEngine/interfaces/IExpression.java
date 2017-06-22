package RuleEngine.interfaces;

import java.util.Map;

/**
 * Created by ShengYang on 2017/2/22.
 */
public interface IExpression {
    boolean match(final Map<String, ?> inputData);
}
