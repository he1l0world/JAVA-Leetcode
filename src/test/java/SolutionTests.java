import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.leetcode.solutions.TwoSum;
import java.util.function.BiFunction;
import java.util.stream.Stream;


@RunWith(Parameterized.class)
public class SolutionTests {
    private static TestUtils testUtils = new TestUtils();

    static Stream<Arguments> testCases(){
        return Stream.of(
                Arguments.of("twoSum", twoSum)
        );
    }

    public static BiFunction< int[], Integer, int[] > twoSum = TwoSum::twoSum;

    @ParameterizedTest
    @MethodSource ("testCases")
    public void testSolutions(){
    }
}
