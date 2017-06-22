package RuleEngine.interfaces;

/**
 * Created by ShengYang on 2017/6/22.
 * 设置父节点的接口
 */

public interface IParent<T> {
    /**
     * 设置父节点
     * @param parent 父节点
     */
    void setParent(T parent);

    /**
     * 获取父节点
     * @return 父节点
     */
    T getParent();
}
