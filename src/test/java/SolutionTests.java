import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leetcode.solutions.HelloWorld;
import org.leetcode.solutions.TwoSum;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class SolutionTests {
    private TestUtils testUtils;

    SolutionTests(){
        testUtils = new TestUtils();
    }
    static Stream<Arguments> testCases(){
        return Stream.of(
                Arguments.of("helloworld", helloWorld),
                Arguments.of("twoSum", twoSum)
        );
    }

    public static BiFunction< Integer, Integer, Integer > helloWorld = HelloWorld::helloWorld;
    public static BiFunction< int[], Integer, int[] > twoSum = TwoSum::twoSum;

    @ParameterizedTest
    @MethodSource ("testCases")
    public <T> void testSolutions(String testName, T solution) throws Exception{
        testUtils.assertTest(testName, solution);
    }
}
