package RuleEngine.interfaces;

import java.util.List;

/**
 * Created by ShengYang on 2017/6/22.
 * 构建树的接口
 */

public interface IChild<T> {
    /**
     * 添加子元素
     *
     * @param child 子元素
     */
    void addChild(T child);

    /**
     * 返回所有的子元素
     *
     * @return 所有的子元素
     */
    List<T> getChildren();
}
