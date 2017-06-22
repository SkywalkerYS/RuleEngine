package com.baidu.myapplication;

import static pub.RuleConstants.RULE_FILE;
import static pub.RuleConstants.SYMBOL_NAME;
import static pub.RuleConstants.SYMBOL_RESULT;
import static pub.RuleConstants.SYMBOL_RULE;
import static pub.RuleConstants.SYMBOL_RULESET;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.BaseOperation.Expression;
import RuleEngine.CompareOperation.Equals;
import RuleEngine.CompareOperation.Greater;
import RuleEngine.CompareOperation.In;
import RuleEngine.CompareOperation.Less;
import RuleEngine.ExpressionParser;
import RuleEngine.IMatchAction;
import RuleEngine.LogicOperation.And;
import RuleEngine.LogicOperation.Or;
import RuleEngine.OperationManager;
import RuleEngine.Rule;
import RuleEngine.RuleSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "shengyang";

    // 白名单源文件内容
    private TextView rawRuleFileTv;
    // 解析按钮
    private Button parseBtn;
    // 解析结果
    private TextView resultTv;

    private TextView key1;
    private EditText value1;
    private TextView key2;
    private EditText value2;
    private TextView key3;
    private EditText value3;

    private byte[] ruleData;

    private RuleSet ruleSet;
    // 测试入口前置的白名单
    private Rule ruleFront;
    //
    private Map<String, Object> filterCondition = new HashMap<>();

    // 测试数据
    private final static String TEXTJSON = "{\"RuleSet\":[{\"name\":\"front\","
            + "\"Rule\":{\"Or\":{\"And\":{\"In\":{\"key\":\"editor\",\"set\":[1,2,3]},"
            + "\"Greater\":{\"key\":\"version\",\"value\":\"2\"}},\"Equals\":{\"key\":\"location\","
            + "\"value\":\"'SH'\"}},\"result\":{\"color\":\"green\",\"ver\":\"3\"}}},{\"name\":\"voice\","
            + "\"Rule\":{\"And\":{\"In\":{\"key\":\"editor\",\"set\":[4,5,6]},\"Greater\":{\"key\":\"version\","
            + "\"value\":\"3\"}},\"result\":{\"package\":\"com.baidu.input\",\"style\":\"3\"}}}]}";

    private final static String RAWJSON = "{\n"
            + "  \"RuleSet\": [\n"
            + "    {\n"
            + "      \"name\": \"front\",\n"
            + "      \"Rule\": {\n"
            + "        \"Or\": {\n"
            + "          \"And\": {\n"
            + "            \"In\": {\n"
            + "              \"key\": \"editor\",\n"
            + "              \"set\": [1, 2, 3]\n"
            + "            },\n"
            + "            \"Greater\": {\n"
            + "              \"key\": \"version\",\n"
            + "              \"value\": \"2\"\n"
            + "            }\n"
            + "          },\n"
            + "          \"Equals\": {\n"
            + "            \"key\": \"location\",\n"
            + "            \"value\": \"'SH'\"\n"
            + "          }\n"
            + "        },\n"
            + "        \"result\": {\n"
            + "          \"color\": \"green\",\n"
            + "          \"ver\": \"3\"\n"
            + "        }\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"name\": \"voice\",\n"
            + "      \"Rule\": {\n"
            + "        \"And\": {\n"
            + "          \"In\": {\n"
            + "            \"key\": \"editor\",\n"
            + "            \"set\": [4, 5, 6]\n"
            + "          },\n"
            + "          \"Greater\": {\n"
            + "            \"key\": \"version\",\n"
            + "            \"value\": \"3\"\n"
            + "          }\n"
            + "        },\n"
            + "        \"result\": {\n"
            + "          \"package\": \"com.baidu.input\",\n"
            + "          \"style\": \"3\"\n"
            + "        }\n"
            + "      }\n"
            + "    }\n"
            + "  ]\n"
            + "}";

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
        // 构建view
        initView();

        // 初始化支持的规则参数
        initRule();

        // 解析白名单
        parseRule();

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
                if (ruleName.equals("front")) {
                    ruleFront = mRule;
                }
                ruleSet.addRule(mRule);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        rawRuleFileTv = (TextView) findViewById(R.id.raw_white_list);
        parseBtn = (Button) findViewById(R.id.parse_btn);
        resultTv = (TextView) findViewById(R.id.result);
        key1 = (TextView) findViewById(R.id.key1);
        value1 = (EditText) findViewById(R.id.value1);
        key2 = (TextView) findViewById(R.id.key2);
        value2 = (EditText) findViewById(R.id.value2);
        key3 = (TextView) findViewById(R.id.key3);
        value3 = (EditText) findViewById(R.id.value3);

        rawRuleFileTv.setText(RAWJSON);
        rawRuleFileTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        parseBtn.setOnClickListener(this);
    }

    private void eval() {

        filterCondition.put(key1.getText().toString(), Integer.parseInt(value1.getText().toString()));
        filterCondition.put(key2.getText().toString(), Integer.parseInt(value2.getText().toString()));
        filterCondition.put(key3.getText().toString(), value3.getText().toString());

        if (ruleFront.eval(filterCondition)) {
            resultTv.setText("白名单命中(*^ω^*)");
        } else {
            resultTv.setText("白名单不明中(T＿T)!!!");
        }
    }

    private byte[] loadData() {
        try {
            InputStream is = getResources().getAssets().open(RULE_FILE);

            int lenght = is.available();

            byte[] buffer = new byte[lenght];

            is.read(buffer);

            return buffer;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.parse_btn:
                // 验证
                eval();
                break;
            default:
                break;
        }
    }
}
