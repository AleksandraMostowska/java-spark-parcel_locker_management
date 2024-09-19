package mostowska.aleksandra.transformer;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

/**
 * Transformer class for converting Java objects to JSON format.
 * Implements Spark's ResponseTransformer interface to handle response formatting.
 */
@Component
@RequiredArgsConstructor
public class JsonTransformer implements ResponseTransformer {

    private final Gson gson;

    /**
     * Converts a Java object to its JSON representation.
     *
     * @param o The object to be converted to JSON.
     * @return A JSON string representation of the object.
     * @throws Exception if an error occurs during JSON conversion.
     */
    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
