import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {

    public static final String SAMPLE_SERVICE_RESULT_MESSAGE = "Test Sample Service Result Message";

    public static final String EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN = "(?i)^ServiceResult: \\[id: [a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}, date: [a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message: %s]$";

    public static final ServiceResult SERVICE_RESULT_WITH_NO_MESSAGE = ServiceResult.createServiceResult();
    public static final ServiceResult SERVICE_RESULT_WITH_NULL_MESSAGE = ServiceResult.createServiceResult(null);
    public static final ServiceResult SERVICE_RESULT_WITH_EMPTY_MESSAGE = ServiceResult.createServiceResult("");
    public static final ServiceResult SERVICE_RESULT_WITH_SAMPLE_MESSAGE = ServiceResult.createServiceResult(SAMPLE_SERVICE_RESULT_MESSAGE);

    private TestUtils() {}

    public static ServiceResult createServiceResultWithNoMessage() {
        return ServiceResult.createServiceResult();
    }

    public static ServiceResult createServiceResultWithNullMessage() {
        return ServiceResult.createServiceResult(null);
    }

    public static ServiceResult createServiceResultWithEmptyMessage() {
        return ServiceResult.createServiceResult("");
    }

    public static ServiceResult createServiceResultWithSampleMessage() {
        return ServiceResult.createServiceResult(SAMPLE_SERVICE_RESULT_MESSAGE);
    }

    public static boolean isServiceResultValid(ServiceResult serviceResult) {
        return serviceResult != null
                && serviceResult.getId() != null
                && serviceResult.getDate() != null;
    }

    @Test
    public void verifySampleServiceResultMessage() {
        assertEquals("Test Sample Service Result Message", SAMPLE_SERVICE_RESULT_MESSAGE);
    }

    @Test
    public void verifyExpectedServiceResultMessagePattern() {
        assertEquals("(?i)^ServiceResult: \\[id: [a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}, date: [a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message: %s]$", EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN);
    }

    @Test
    public void verifyServiceResultWithNoMessage() {
        assertInstanceOf(ServiceResult.class, SERVICE_RESULT_WITH_NO_MESSAGE);
        assertInstanceOf(UUID.class, SERVICE_RESULT_WITH_NO_MESSAGE.getId());
        assertInstanceOf(Date.class, SERVICE_RESULT_WITH_NO_MESSAGE.getDate());
        assertNull(SERVICE_RESULT_WITH_NO_MESSAGE.getMessage());

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_NO_MESSAGE));
    }

    @Test
    public void verifyCreateServiceResultWithNoMessage() {
        ServiceResult serviceResultWithNoMessage = createServiceResultWithNoMessage();

        assertInstanceOf(ServiceResult.class, serviceResultWithNoMessage);
        assertInstanceOf(UUID.class, serviceResultWithNoMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithNoMessage.getDate());
        assertNull(serviceResultWithNoMessage.getMessage());

        assertTrue(isServiceResultValid(serviceResultWithNoMessage));
    }

    @Test
    public void verifyServiceResultWithNullMessage() {
        assertInstanceOf(ServiceResult.class, SERVICE_RESULT_WITH_NULL_MESSAGE);
        assertInstanceOf(UUID.class, SERVICE_RESULT_WITH_NULL_MESSAGE.getId());
        assertInstanceOf(Date.class, SERVICE_RESULT_WITH_NULL_MESSAGE.getDate());
        assertNull(SERVICE_RESULT_WITH_NULL_MESSAGE.getMessage());

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_NULL_MESSAGE));
    }

    @Test
    public void verifyCreateServiceResultWithNullMessage() {
        ServiceResult serviceResultWithNullMessage = createServiceResultWithNullMessage();

        assertInstanceOf(ServiceResult.class, serviceResultWithNullMessage);
        assertInstanceOf(UUID.class, serviceResultWithNullMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithNullMessage.getDate());
        assertNull(serviceResultWithNullMessage.getMessage());

        assertTrue(isServiceResultValid(serviceResultWithNullMessage));
    }

    @Test
    public void verifyServiceResultWithEmptyMessage() {
        assertInstanceOf(ServiceResult.class, SERVICE_RESULT_WITH_EMPTY_MESSAGE);
        assertInstanceOf(UUID.class, SERVICE_RESULT_WITH_EMPTY_MESSAGE.getId());
        assertInstanceOf(Date.class, SERVICE_RESULT_WITH_EMPTY_MESSAGE.getDate());
        assertEquals("", SERVICE_RESULT_WITH_EMPTY_MESSAGE.getMessage());

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_EMPTY_MESSAGE));
    }

    @Test
    public void verifyCreateServiceResultWithEmptyMessage() {
        ServiceResult serviceResultWithEmptyMessage = createServiceResultWithEmptyMessage();

        assertInstanceOf(ServiceResult.class, serviceResultWithEmptyMessage);
        assertInstanceOf(UUID.class, serviceResultWithEmptyMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithEmptyMessage.getDate());
        assertEquals("", serviceResultWithEmptyMessage.getMessage());

        assertTrue(isServiceResultValid(serviceResultWithEmptyMessage));
    }

    @Test
    public void verifyServiceResultWithSampleMessage() {
        assertInstanceOf(ServiceResult.class, SERVICE_RESULT_WITH_SAMPLE_MESSAGE);
        assertInstanceOf(UUID.class, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.getId());
        assertInstanceOf(Date.class, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.getDate());
        assertEquals(SAMPLE_SERVICE_RESULT_MESSAGE, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.getMessage());

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_SAMPLE_MESSAGE));
    }

    @Test
    public void verifyCreateServiceResultWithSampleMessage() {
        ServiceResult serviceResultWithSampleMessage = createServiceResultWithSampleMessage();

        assertInstanceOf(ServiceResult.class, serviceResultWithSampleMessage);
        assertInstanceOf(UUID.class, serviceResultWithSampleMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithSampleMessage.getDate());
        assertEquals(SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultWithSampleMessage.getMessage());

        assertTrue(isServiceResultValid(serviceResultWithSampleMessage));
    }

    @Test
    public void verifyIsServiceResultValid() {
        assertFalse(isServiceResultValid(null));

        List.of(
                SERVICE_RESULT_WITH_NO_MESSAGE,
                SERVICE_RESULT_WITH_NULL_MESSAGE,
                SERVICE_RESULT_WITH_EMPTY_MESSAGE,
                SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach(serviceResult -> assertTrue(isServiceResultValid(serviceResult)));

        List.of("id", "date").forEach(fieldName -> {
            try {
                ServiceResult serviceResultWithInvalidField = createServiceResultWithSampleMessage();

                Field field = serviceResultWithInvalidField.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(serviceResultWithInvalidField, null);

                assertNull(field.get(serviceResultWithInvalidField));

                switch (fieldName) {
                    case "id" -> assertNull(serviceResultWithInvalidField.getId());
                    case "date" -> assertNull(serviceResultWithInvalidField.getDate());
                    default -> throw new IllegalStateException("Unexpected value: " + fieldName);
                }

                assertFalse(isServiceResultValid(serviceResultWithInvalidField));
            } catch(Exception e) {
                fail(e);
            }
        });
    }
}
