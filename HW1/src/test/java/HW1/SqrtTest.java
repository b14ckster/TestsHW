package HW1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SqrtTest {
   static Calculator calculator = new Calculator();

    @DataProvider(name = "double")
    public static Object[][] doubleDP(){
        return new Object[][]{
                {7.01},
                {0.01},
                {128768.1},
                {-98.2},
                {0},
                {6.99},
                {25},
                {Double.MIN_VALUE},
                {Double.MAX_VALUE}
        };
    }

    @Test(testName = "SqrtTest double", dataProvider = "double")
    public void sqrtTestDouble (double a){
        Assert.assertEquals(calculator.sqrt(a), Math.sqrt(Math.abs(a)));
    }
}
