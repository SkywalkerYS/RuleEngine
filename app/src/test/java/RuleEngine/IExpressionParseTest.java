package RuleEngine;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import RuleEngine.interfaces.IExpression;
import RuleEngine.LogicOperation.Or;

/**
 * Created by ShengYang on 2017/4/11.
 */

public class IExpressionParseTest {

    private String frontRule;

    @Before
    public void setUp() {
        frontRule = "\"Or\": {\n"
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
                + "        }";
    }

    @Test
    public void testParse() {
        try {
            JSONObject jsonObject = new JSONObject(frontRule);
            IExpression rootOperation = ExpressionParser.parse(jsonObject);

            Assert.assertTrue(rootOperation instanceof Or);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
