import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TestUtils {
    private final String PATH = "src/test/resources/%s/%s";
    private final String INPUT = "input.json";

    public <T> void assertTest(String folder, T solution) throws Exception {
        TestInput input = readFile(folder);
        testEquals(getResult(solution, input), input.result);
    }

    private <T, R> R getResult(T solution, TestInput input) {
        if (solution instanceof Function) {
            return getResult((Function)solution, input);
        } else if (solution instanceof BiFunction) {
            return getResult((BiFunction)solution, input);
        }
        return null;
    }

    private <T,R> R getResult(Function solution, TestInput input){
        return ((Function<T, R>) solution).apply( convertValue((T) input.firstArgument));
    }

    private <T, U, R> R getResult(BiFunction solution, TestInput input){
        return ((BiFunction<T, U, R>) solution).apply( convertValue((T)input.firstArgument), convertValue((U)input.secondArgument));
    }

    /**
     * We just need to deal with the list to convert each element to be primitive
     * TODO: add more types of list
     * @param value
     * @return
     * @param <T>
     */
    private <T> T convertValue(T value){
        if(value instanceof List && !((List) value).isEmpty()) {
            if(((List) value).get(0) instanceof Integer){
                return (T) ((List) value).stream().mapToInt(x -> (int) x).toArray();
            }else if (((List) value).get(0) instanceof Double){
                return  (T) ((List) value).stream().mapToDouble(x -> (double) x).toArray();
            }
        }
        return value;
    }

    /**
     * The actual comparison with the output and expected output
     * TODO: Make the logic more elegant to deal with generic types
     * @param input
     * @param result
     * @param <T>
     */
    private <T>void testEquals(T input, T result){
        if(input instanceof int[] ){
            int index = 0;
            Iterator resultIterator = ((Iterable) result).iterator();
            while(resultIterator.hasNext()){
                Assertions.assertEquals(((int[])input)[index], resultIterator.next());
                index += 1;
            }
        } else if (input instanceof double[]) {
            int index = 0;
            Iterator resultIterator = ((Iterable) result).iterator();
            while(resultIterator.hasNext()){
                Assertions.assertEquals(((double[])input)[index], resultIterator.next());
                index += 1;
            }
        } else{
            Assertions.assertEquals(input, result);
        }
    }

    /**
     * read input files as test input and conver to TestInput type
     * TODO: read all input files under the folder
     * @param folder
     * @return
     * @throws Exception
     */
    private TestInput readFile(String folder) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(String.format(PATH, folder, INPUT)), TestInput.class);
    }
}
