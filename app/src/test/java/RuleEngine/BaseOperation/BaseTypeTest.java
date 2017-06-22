package RuleEngine.BaseOperation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ShengYang on 2017/4/11.
 */

public class BaseTypeTest {
    String booleanStr;
    String stringStr;
    String floatStr;
    String integerStr;

    @Before
    public void setup() {
        booleanStr = "true";
        stringStr = "'key'";
        floatStr = "9.9";
        integerStr = "23";
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBaseType() throws Exception {
        BaseType nullType = BaseType.getBaseType(null);
    }


    @Test
    public void testGetBooleanBaseType() throws Exception {
        BaseType booleanType = BaseType.getBaseType(booleanStr);
        Assert.assertEquals(booleanType.getType(), Boolean.class);
    }

    @Test
    public void testGetStringBaseType() throws Exception {
        BaseType stringType = BaseType.getBaseType(stringStr);
        Assert.assertEquals(stringType.getType(), String.class);
    }

    @Test
    public void testGetFloatBaseType() throws Exception {
        BaseType floatType = BaseType.getBaseType(floatStr);
        Assert.assertEquals(floatType.getType(), Float.class);
    }

    @Test
    public void testGetIntegerBaseType() throws Exception {
        BaseType intType = BaseType.getBaseType(integerStr);
        Assert.assertEquals(intType.getType(), Integer.class);
    }

}
