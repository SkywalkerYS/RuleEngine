package RuleTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import RuleEngine.interfaces.IChild;
import android.util.Log;

/**
 * Created by ShengYang on 2017/2/23.
 * 维护一个Rule的列表
 */
public class RuleSet implements IChild<Rule> {

    private static final String TAG = "shengyang";

    private List<Rule> childrenRules;

    public RuleSet(ArrayList<Rule> rules) {
        this.childrenRules = rules;
    }

    public RuleSet() {
        childrenRules = new ArrayList<>();
    }

    /**
     * 检测是否命中集合中所有的Rule
     * @param bindings
     * @return
     */
    public boolean eval(Map<String, ?> bindings) {
        boolean ruleSetEval = true;
        for (Rule rule : childrenRules) {
            boolean ruleEval = rule.eval(bindings);

            Log.e(TAG, "rule name: " + rule.getName());
            if (ruleEval) {
                Log.e(TAG, "rule result: " + rule.getResult(bindings));
            } else {
                Log.e(TAG, "rule result: match fail!!");
            }
            ruleSetEval &= ruleEval;
        }
        return ruleSetEval;
    }

    @Override
    public void addChild(Rule rule) {
        if (rule != null) {
            childrenRules.add(rule);
            rule.setParent(this);
        }
    }

    @Override
    public List<Rule> getChildren() {
        return childrenRules;
    }

    @Override
    public String toString() {
        return "RuleSet{" +
                "childrenRules=" + "" +
                "} \n" + childrenRules;
    }
}
