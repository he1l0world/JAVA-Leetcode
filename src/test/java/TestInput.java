import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestInput<F, S, T, R> {
    @JsonProperty
    public final F firstArgument = null;
    @JsonProperty
    public final S secondArgument = null;
    @JsonProperty
    public final T thirdArgument = null;
    @JsonProperty
    public final R result = null;
}
