package RuleEngine.pub;

/**
 * Created by ShengYang on 2017/6/22.
 * 规则定义的一些常量
 */

public class RuleConstants {

    // 白名单文件
    public static final String RULE_FILE = "rules.json";

    // 表示一个白名单（规则），里面可以包含几个规则
    public static final String SYMBOL_RULESET = "RuleSet";

    // 表示一条规则
    public static final String SYMBOL_RULE = "Rule";

    // 表示一个白名单的名字
    public static final String SYMBOL_NAME = "name";

    // 表示数据是key
    public static final String SYMBOL_KEY = "key";

    // 表示数据是value
    public static final String SYMBOL_VALUE = "value";

    // 表示数据是集合
    public static final String SYMBOL_SET = "set";

    // 表示一个rule的结果返回
    public static final String SYMBOL_RESULT = "result";

    // 等于运算
    public static final String OPERATION_EQUALS = "Equals";

    // 大于运算
    public static final String OPERATION_GREATER = "Greater";

    // 小于于运算
    public static final String OPERATION_LESS = "Less";

    // 包含运算
    public static final String OPERATION_IN = "In";

    // 与运算
    public static final String OPERATION_AND = "And";

    // 或运算
    public static final String OPERATION_OR = "Or";

    // 非运算
    public static final String OPERATION_NOT = "Not";
}
