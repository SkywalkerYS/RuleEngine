package RuleTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import RuleEngine.interfaces.IComposition;
import RuleEngine.interfaces.IMatchAction;
import RuleEngine.interfaces.IParent;

/**
 * Created by ShengYang on 2017/2/23.
 */
public class Rule implements IComposition<RuleFilter>, IParent<RuleSet> {

    // 规则的名称
    private String name;
    // 是否命中的回调
    private IMatchAction matchAction;
    // 命中后的处理结果
    private JSONObject result;
    // 父节点，属于哪个规则集合
    private RuleSet parent;
    // 一个rule下所有的过滤条件
    private List<RuleFilter> filters = new ArrayList<>();

    public Rule(String name, List<RuleFilter> filters) {
        this.name = name;
        this.filters = filters;
    }

    public static class Builder {
        private List<RuleFilter> filters = new ArrayList<>();
        private String name = null;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withFilters(List<RuleFilter> filters) {
            this.filters = filters;
            return this;
        }

        public Rule build() {
            return new Rule(name, filters);
        }
    }

    public boolean eval(Map<String, ?> bindings) {
        for (RuleFilter filter : filters) {
           if (filter.match(bindings)) {
               return true;
           }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public JSONObject getResult(Map<String, ?> bindings) {
        if (filters != null && filters.size() > 0) {
            for (RuleFilter ruleFilter : filters) {
                if (ruleFilter.match(bindings)) {
                    return ruleFilter.getResult();
                }
            }
        }
        return null;
    }

    @Override
    public void setParent(RuleSet parent) {
        this.parent = parent;
    }

    @Override
    public RuleSet getParent() {
        return parent;
    }

    @Override
    public void addChild(RuleFilter filter) {
        if (filters != null) {
            filters.add(filter);
            filter.setParent(this);
        }
    }

    @Override
    public List<RuleFilter> getChildren() {
        return filters;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", RuleFilter=" + filters +
                '}';
    }
}
