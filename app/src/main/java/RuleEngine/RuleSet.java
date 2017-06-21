package RuleEngine;

import java.util.ArrayList;
import java.util.Map;

import android.util.Log;

/**
 * Created by ShengYang on 2017/2/23.
 * 维护一个Rule的列表
 */
public class RuleSet {

    private static final String TAG = "shengyang";

    private ArrayList<Rule> rules;

    public RuleSet(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public RuleSet() {
        rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public boolean eval(Map<String, ?> bindings) {
        boolean ruleSetEval = true;
        for (Rule rule : rules) {
            boolean ruleEval = rule.eval(bindings);

            Log.e(TAG, "rule name: " + rule.getName());
            if (ruleEval) {
                Log.e(TAG, "rule result: " + rule.getResult());
            } else {
                Log.e(TAG, "rule result: match fail!!");
            }
            ruleSetEval &= ruleEval;
        }
        return ruleSetEval;
    }

}
