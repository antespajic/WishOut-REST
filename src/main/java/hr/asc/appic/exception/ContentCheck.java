package hr.asc.appic.exception;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class ContentCheck {

    public static void requireNonNull(BigInteger id, Object object) {
        if (object == null) {
            log.error("Unexpectedly found null for object with id: " + id);
            throw new NoSuchEntityException("Item for id could not be found: " + id);
        }
    }
}
