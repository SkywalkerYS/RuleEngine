package com.baidu.myapplication;

import static RuleEngine.pub.RuleConstants.RULE_FILE;
import static RuleEngine.pub.RuleConstants.SYMBOL_NAME;
import static RuleEngine.pub.RuleConstants.SYMBOL_RULE;
import static RuleEngine.pub.RuleConstants.SYMBOL_RULESET;
import static com.baidu.myapplication.R.id.result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RuleEngine.CompareOperation.Equals;
import RuleEngine.CompareOperation.Greater;
import RuleEngine.CompareOperation.In;
import RuleEngine.CompareOperation.Less;
import RuleEngine.ExpressionParser;
import RuleEngine.LogicOperation.And;
import RuleEngine.LogicOperation.Or;
import RuleEngine.OperationManager;
import RuleEngine.interfaces.IMatchAction;
import RuleTree.Rule;
import RuleTree.RuleFilter;
import RuleTree.RuleSet;
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
    // 测试的白名单
    private Rule ruleTest;
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
            + "      \"name\": \"tinyVoice\",\n"
            + "      \"Rule\": [\n"
            + "        {\n"
            + "          \"Equals\": [\n"
            + "            {\n"
            + "              \"key\": \"package\",\n"
            + "              \"value\": \"'wechat.com'\"\n"
            + "            },\n"
            + "            {\n"
            + "              \"key\": \"ctrid\",\n"
            + "              \"value\": \"3\"\n"
            + "            },\n"
            + "            {\n"
            + "              \"key\": \"style\",\n"
            + "              \"value\": \"2\"\n"
            + "            },\n"
            + "            {\n"
            + "              \"key\": \"interaction\",\n"
            + "              \"value\": \"1\"\n"
            + "            }\n"
            + "          ],\n"
            + "          \"result\": {\n"
            + "            \"s_text\": \"极简语音\",\n"
            + "            \"action\": 3,\n"
            + "            \"link\": \"http://www.baidu.com\"\n"
            + "          }\n"
            + "        },\n"
            + "        {\n"
            + "          \"Equals\": [\n"
            + "            {\n"
            + "              \"key\": \"package\",\n"
            + "              \"value\": \"'com.huawei.appmarket'\"\n"
            + "            },\n"
            + "            {\n"
            + "              \"key\": \"screen\",\n"
            + "              \"value\": \"1\"\n"
            + "            },\n"
            + "            {\n"
            + "              \"key\": \"style\",\n"
            + "              \"value\": \"2\"\n"
            + "            }\n"
            + "          ],\n"
            + "          \"result\": {\n"
            + "            \"r_text\": \"语音搜索\",\n"
            + "            \"s_text\": \"语音纠错\"\n"
            + "          }\n"
            + "        },\n"
            + "        {\n"
            + "          \"Or\": [\n"
            + "            {\n"
            + "              \"Equals\": [\n"
            + "                {\n"
            + "                  \"key\": \"package\",\n"
            + "                  \"value\": \"'wechat.com'\"\n"
            + "                },\n"
            + "                {\n"
            + "                  \"key\": \"screen\",\n"
            + "                  \"value\": \"1\"\n"
            + "                }\n"
            + "              ]\n"
            + "            },\n"
            + "            {\n"
            + "              \"Greater\": [\n"
            + "                {\n"
            + "                  \"key\": \"version\",\n"
            + "                  \"value\": \"1\"\n"
            + "                }\n"
            + "              ]\n"
            + "            }\n"
            + "          ],\n"
            + "          \"result\": {\n"
            + "            \"action\": 3,\n"
            + "            \"link\": \"http://www.baidu.com\"\n"
            + "          }\n"
            + "        }\n"
            + "      ]\n"
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
            JSONObject jsonObject = new JSONObject(RAWJSON);

            JSONArray rules = (JSONArray) jsonObject.get(SYMBOL_RULESET);
            int ruleSize = rules.length();

            JSONObject rule = null;
            String ruleName = null;
            JSONArray filters = null;

            ruleSet = new RuleSet();

            for (int i = 0; i < ruleSize; i++) {

                rule = rules.getJSONObject(i);
                ruleName = rule.optString(SYMBOL_NAME);

                filters = (JSONArray) rule.get(SYMBOL_RULE);

                int filterSize = filters.length();
                List<RuleFilter> ruleFilters = new ArrayList<>();

                for (int j = 0; j < filterSize; j++) {
                    RuleFilter ruleFilter = ExpressionParser.parseFilter(filters.getJSONObject(j));
                    ruleFilters.add(ruleFilter);
                }
                Rule mRule = new Rule.Builder().withName(ruleName).withFilters(ruleFilters).build();
                if (ruleName.equals("tinyVoice")) {
                    ruleTest = mRule;
                }
                ruleSet.addChild(mRule);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        rawRuleFileTv = (TextView) findViewById(R.id.raw_white_list);
        parseBtn = (Button) findViewById(R.id.parse_btn);
        resultTv = (TextView) findViewById(result);
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

        filterCondition.put("package", "'wechat.com'");
        filterCondition.put("version", 2);
        filterCondition.put("style", 3);
        filterCondition.put("interaction", 1);

        if (ruleTest.eval(filterCondition)) {
            resultTv.setText(ruleTest.getResult(filterCondition) != null ? ruleTest.getResult(filterCondition).toString() : "命中，没有result");
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
