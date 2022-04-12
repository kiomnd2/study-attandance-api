package kiomnd2.cosmo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Response<T> {

    private final String code;

    private final T body;

    public static <T> Response<T> success(T body) {
        return new Response<T>("0000", body);
    }

}
