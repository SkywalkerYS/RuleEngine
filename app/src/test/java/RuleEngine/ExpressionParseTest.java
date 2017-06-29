package RuleEngine;

import static RuleEngine.pub.RuleConstants.OPERATION_AND;
import static RuleEngine.pub.RuleConstants.OPERATION_EQUALS;
import static RuleEngine.pub.RuleConstants.OPERATION_GREATER;
import static RuleEngine.pub.RuleConstants.OPERATION_IN;
import static RuleEngine.pub.RuleConstants.OPERATION_OR;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import RuleEngine.BaseOperation.BaseType;
import RuleEngine.BaseOperation.Operation;
import RuleEngine.CompareOperation.Equals;
import RuleEngine.CompareOperation.Greater;
import RuleEngine.CompareOperation.In;
import RuleEngine.CompareOperation.Less;
import RuleEngine.LogicOperation.And;
import RuleEngine.LogicOperation.Or;

/**
 * Created by ShengYang on 2017/4/11.
 */

public class ExpressionParseTest {

    private String frontRule;

    private Operation rootOperation;

    @Before
    public void setUp() {
        frontRule = "{"
                + "  \"Or\": {\n"
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
                + "        }"
                + "}";

        OperationManager operationManager = OperationManager.INSTANCE;

        operationManager.registerOperation(new And());
        operationManager.registerOperation(new Equals());
        operationManager.registerOperation(new Or());
        operationManager.registerOperation(new Greater());
        operationManager.registerOperation(new Less());
        operationManager.registerOperation(new In());

        try {
            JSONObject jsonObject = new JSONObject(frontRule);
//            rootOperation = ExpressionParser.parseFilter(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParse() {
        Assert.assertTrue(rootOperation instanceof Or);
    }

    @Test
    public void testAbsCompareOperationParseData() {

    }

    @Test
    public void testAbsLogicOperationParseData() {

    }

    @Test
    public void testEqualsParse() {
        Equals equalOperation = (Equals) OperationManager.INSTANCE.getOperation(OPERATION_EQUALS);

//        Assert.assertEquals("location", equalOperation.getKey());
//        Assert.assertEquals("'SH'", equalOperation.getValue().getValue());
    }

    @Test
    public void testGreaterParse() {
        Greater greaterOperation = (Greater) OperationManager.INSTANCE.getOperation(OPERATION_GREATER);

//        Assert.assertEquals("version", greaterOperation.getKey());
//        Assert.assertEquals(2, greaterOperation.getValue().getValue());
    }

    @Test
    public void testInParse() {
        In inOperation = (In) OperationManager.INSTANCE.getOperation(OPERATION_IN);

//        Assert.assertEquals("editor", inOperation.getKey());

        List<BaseType> expectDataList = new ArrayList<>();
        expectDataList.add(BaseType.getBaseType("1"));
        expectDataList.add(BaseType.getBaseType("2"));
        expectDataList.add(BaseType.getBaseType("3"));
        for (int i = 0; i < expectDataList.size(); i++) {
//            Assert.assertEquals(expectDataList.get(i).getValue(),
//                    inOperation.getValue().get(i).getValue());
        }

    }

    @Test
    public void testOrParse() {
        Or orOperation = (Or) OperationManager.INSTANCE.getOperation(OPERATION_OR);
        List<Operation> orChildren = orOperation.getChildren();
        Assert.assertEquals(OperationManager.INSTANCE.getOperation(OPERATION_EQUALS).getSymbol(),
                orChildren.get(0).getSymbol());
        Assert.assertEquals(OperationManager.INSTANCE.getOperation(OPERATION_AND).getSymbol(), orChildren.get(1)
                .getSymbol());
    }

    @Test
    public void testAndParse() {
        And andOperation = (And) OperationManager.INSTANCE.getOperation(OPERATION_AND);
        List<Operation> orChildren = andOperation.getChildren();
        Assert.assertEquals(OperationManager.INSTANCE.getOperation(OPERATION_IN).getSymbol(),
                orChildren.get(0).getSymbol());
        Assert.assertEquals(OperationManager.INSTANCE.getOperation(OPERATION_GREATER).getSymbol(), orChildren.get(1)
                .getSymbol());
    }

    @Test
    public void testRuleTest() {

    }

}
