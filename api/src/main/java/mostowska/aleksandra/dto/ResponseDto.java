package mostowska.aleksandra.dto;


/**
 * The `ResponseDto` class is used to represent a response that contains data and an optional error message.
 *
 * @param <T> The type of data returned in the response.
 */
public record ResponseDto<T>(T data, String error) {

    /**
     * Constructor for `ResponseDto` that creates an object with data and a default null error message.
     *
     * @param data The data to be included in the response.
     */
    public ResponseDto(T data) {
        this(data, null);
    }


    /**
     * Constructor for `ResponseDto` that creates an object with an error message and default null data.
     *
     * @param error The error message to be included in the response.
     */
    public ResponseDto(String error) {
        this(null, error);
    }
}
