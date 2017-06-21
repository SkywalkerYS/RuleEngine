package com.baidu.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.LogicOperation.And;
import RuleEngine.CompareOperation.Equals;
import RuleEngine.BaseOperation.Expression;
import RuleEngine.ExpressionParser;
import RuleEngine.CompareOperation.Greater;
import RuleEngine.IMatchAction;
import RuleEngine.CompareOperation.In;
import RuleEngine.CompareOperation.Less;
import RuleEngine.OperationManager;
import RuleEngine.LogicOperation.Or;
import RuleEngine.Rule;
import RuleEngine.RuleSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "shengyang";

    public static final String RULEFILE = "rules.json";

    private byte[] ruleData;

    private RuleSet ruleSet;

    public static final String SYMBOL_RULESET = "RuleSet";
    public static final String SYMBOL_RULE = "Rule";
    public static final String SYMBOL_NAME = "name";
    public static final String SYMBOL_RESULT = "result";


    // 测试数据
    private final static String TEXTJSON = "{\"RuleSet\":[{\"name\":\"front\","
            + "\"Rule\":{\"Or\":{\"And\":{\"In\":{\"key\":\"editor\",\"set\":[1,2,3]},"
            + "\"Greater\":{\"key\":\"version\",\"value\":\"2\"}},\"Equals\":{\"key\":\"location\","
            + "\"value\":\"'SH'\"}},\"result\":{\"color\":\"green\",\"ver\":\"3\"}}},{\"name\":\"voice\","
            + "\"Rule\":{\"And\":{\"In\":{\"key\":\"editor\",\"set\":[4,5,6]},\"Greater\":{\"key\":\"version\","
            + "\"value\":\"3\"}},\"result\":{\"package\":\"com.baidu.input\",\"style\":\"3\"}}}]}";

    // 白名单是否命中的回调
    IMatchAction iMatchAction = new IMatchAction() {
        @Override
        public void successAction() {
            Log.e(TAG, "匹配成功！");
        }

        @Override
        public void failAction() {
            Log.e(TAG, "匹配失败！");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化支持的规则参数
        initRule();

        // 解析白名单
        parseRule();

        // 验证
        eval();
    }

    private void initRule() {
        OperationManager operationManager = OperationManager.INSTANCE;

        operationManager.registerOperation(new And());
        operationManager.registerOperation(new Equals());
        operationManager.registerOperation(new Or());
        operationManager.registerOperation(new Greater());
        operationManager.registerOperation(new Less());
        operationManager.registerOperation(new In());

    }

    private void parseRule() {
        try {
            JSONObject jsonObject = new JSONObject(TEXTJSON);

            JSONArray rules = (JSONArray) jsonObject.get(SYMBOL_RULESET);
            JSONObject rule = null;
            String ruleName = null;
            JSONObject ruleObject = null;
            ruleSet = new RuleSet();
            for (int i = 0; i < rules.length(); i++) {
                rule = rules.getJSONObject(i);
                ruleName = rule.optString(SYMBOL_NAME);
                ruleObject = rule.optJSONObject(SYMBOL_RULE);
                Expression expression = ExpressionParser.parse(ruleObject);
                Rule mRule = new Rule.Builder().withName(ruleName).withExpression(expression).withAction(iMatchAction)
                        .withResult(ruleObject.optJSONObject(SYMBOL_RESULT)).build();
                ruleSet.addRule(mRule);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void eval() {
        Map<String, Object> bindings = new HashMap<>();
        bindings.put("editor", 4);
        bindings.put("version", 1);
        bindings.put("location", "'SH'");

        ruleSet.eval(bindings);
    }

    private byte[] loadData() {
        try {
            InputStream is = getResources().getAssets().open(RULEFILE);

            int lenght = is.available();

            byte[] buffer = new byte[lenght];

            is.read(buffer);

            return buffer;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
