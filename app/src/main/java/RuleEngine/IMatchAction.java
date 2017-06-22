package RuleEngine;

/**
 * Created by ShengYang on 2017/2/23.
 */
public interface IMatchAction {
    // 匹配成功的操作逻辑
    void successAction();
    // 匹配失败的操作逻辑
    void failAction();
}
