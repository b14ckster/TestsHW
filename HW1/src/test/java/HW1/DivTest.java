package HW1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DivTest {
    Calculator calculator;

    @BeforeClass
    public void init(){
        calculator = new Calculator();
    }

    @DataProvider(name = "double")
    public static Object[][] doubleDP(){
        return new Object[][]{
                {7.01, -17.8},
                {0.01, 0.99},
                {128768.1, 9812.2},
                {-98.2, -98.2},
                {0, 76.9},
                {0, 1},
                {6.99, -34},
                {-3.4, 3.4},
                {Double.MIN_VALUE, Double.MAX_VALUE}
        };
    }

    @DataProvider(name = "long")
    public static Object[][] longLG() {
        return new Object[][]{
                {7L, -17L},
                {0L, -4L},
                {128768L, 9812L},
                {-98L, -98L},
                {0L, 76L},
                {0L, 1L},
                {6L, 3L},
                {-3L, 3L},
                {Long.MIN_VALUE, Long.MAX_VALUE}
        };
    }

    @Test(testName = "DivTest double", dataProvider = "double")
    public void divTestDouble (double f, double s){
        Assert.assertEquals(calculator.div(f, s), f / s);
    }

    @Test(testName = "DivTest long", dataProvider = "long")
    public void divTestLong(long f, long s) {
        Assert.assertEquals(calculator.div(f, s), f / s);
    }
}
