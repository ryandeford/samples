import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceResultTest {

    @Test
    void createServiceResultWithNoMessage() {
        ServiceResult serviceResultWithNoMessage = ServiceResult.createServiceResult();

        assertTrue(TestUtils.isServiceResultValid(serviceResultWithNoMessage));

        assertInstanceOf(ServiceResult.class, serviceResultWithNoMessage);
        assertInstanceOf(UUID.class, serviceResultWithNoMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithNoMessage.getDate());
        assertNull(serviceResultWithNoMessage.getMessage());
    }

    @Test
    void createServiceResultWithNullMessage() {
        ServiceResult serviceResultWithNullMessage = ServiceResult.createServiceResult(null);

        assertTrue(TestUtils.isServiceResultValid(serviceResultWithNullMessage));

        assertInstanceOf(ServiceResult.class, serviceResultWithNullMessage);
        assertInstanceOf(UUID.class, serviceResultWithNullMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithNullMessage.getDate());
        assertNull(serviceResultWithNullMessage.getMessage());
    }

    @Test
    void createServiceResultWithEmptyMessage() {
        ServiceResult serviceResultWithEmptyMessage = ServiceResult.createServiceResult("");

        assertTrue(TestUtils.isServiceResultValid(serviceResultWithEmptyMessage));

        assertInstanceOf(ServiceResult.class, serviceResultWithEmptyMessage);
        assertInstanceOf(UUID.class, serviceResultWithEmptyMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithEmptyMessage.getDate());
        assertEquals("", serviceResultWithEmptyMessage.getMessage());
    }

    @Test
    void createServiceResultWithSampleMessage() {
        ServiceResult serviceResultWithSampleMessage = ServiceResult.createServiceResult(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE);

        assertTrue(TestUtils.isServiceResultValid(serviceResultWithSampleMessage));

        assertInstanceOf(ServiceResult.class, serviceResultWithSampleMessage);
        assertInstanceOf(UUID.class, serviceResultWithSampleMessage.getId());
        assertInstanceOf(Date.class, serviceResultWithSampleMessage.getDate());
        assertEquals(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultWithSampleMessage.getMessage());
    }

    @Test
    void getId() {
        ServiceResult serviceResult = ServiceResult.createServiceResult();

        UUID id1 = serviceResult.getId();
        assertInstanceOf(UUID.class, id1);

        UUID id2 = serviceResult.getId();
        assertInstanceOf(UUID.class, id2);

        assertSame(id1, id2);
    }

    @Test
    void getDate() {
        ServiceResult serviceResult = ServiceResult.createServiceResult();

        Date date1 = serviceResult.getDate();
        assertInstanceOf(Date.class, date1);

        Date date2 = serviceResult.getDate();
        assertInstanceOf(Date.class, date2);

        assertSame(date1, date2);
    }

    @Test
    void getMessage() {
        Map<ServiceResult, String> serviceResults = new HashMap<>();

        serviceResults.put(ServiceResult.createServiceResult(), null);
        serviceResults.put(ServiceResult.createServiceResult(null), null);
        serviceResults.put(ServiceResult.createServiceResult(""), "");
        serviceResults.put(ServiceResult.createServiceResult(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE), TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE);

        serviceResults.forEach((serviceResult, expectedMessage) -> {
            String message1 = serviceResult.getMessage();
            assertEquals(expectedMessage, message1);

            String message2 = serviceResult.getMessage();
            assertEquals(expectedMessage, message2);

            assertSame(message1, message2);
        });
    }

    @Test
    void testToString() {
        List.of(
                TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach(serviceResult -> {
            String serviceResultString = serviceResult.toString();
            assertInstanceOf(String.class, serviceResultString);
            assertTrue(serviceResultString.matches(TestUtils.EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN.formatted(serviceResult.getMessage())));
        });
    }

    @Test
    void testEquals() {
        ServiceResult serviceResult = ServiceResult.createServiceResult(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE);
        assertInstanceOf(ServiceResult.class, serviceResult);

        ServiceResult serviceResultOther = ServiceResult.createServiceResult(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE);
        assertInstanceOf(ServiceResult.class, serviceResultOther);
        TestUtils.setObjectFieldValue(serviceResultOther, "date", TestUtils.SAMPLE_SERVICE_RESULT_DATE);

        assertNotEquals(serviceResult.getId(), serviceResultOther.getId());
        assertNotEquals(serviceResult.getDate(), serviceResultOther.getDate());
        assertEquals(serviceResult.getMessage(), serviceResultOther.getMessage());
        assertNotEquals(serviceResult, serviceResultOther);

        // Note: Some IDEs may suggest changing this to a standard not equals assertion, but we want to call the equals method directly
        assertFalse(serviceResult.equals(serviceResultOther));

        UUID customId = UUID.randomUUID();
        Date customDate = Date.from(Instant.now());
        String customMessage = "Test Custom Service Result Message";

        ServiceResult serviceResultCustom1 = ServiceResult.createServiceResult(customMessage);
        ServiceResult serviceResultCustom2 = ServiceResult.createServiceResult(customMessage);

        List.of(serviceResultCustom1, serviceResultCustom2).forEach(serviceResultCustom -> {
            TestUtils.setObjectFieldValue(serviceResultCustom, "id", customId);
            TestUtils.setObjectFieldValue(serviceResultCustom, "date", customDate);
            TestUtils.setObjectFieldValue(serviceResultCustom, "message", customMessage);
        });

        assertEquals(serviceResultCustom1.getId(), serviceResultCustom2.getId());
        assertEquals(serviceResultCustom1.getDate(), serviceResultCustom2.getDate());
        assertEquals(serviceResultCustom1.getMessage(), serviceResultCustom2.getMessage());
        assertEquals(serviceResultCustom1, serviceResultCustom2);
        assertNotSame(serviceResultCustom1, serviceResultCustom2);

        // Note: Some IDEs may suggest changing this to a standard equals assertion, but we want to call the equals method directly
        assertTrue(serviceResultCustom1.equals(serviceResultCustom2));
    }

    @Test
    void testHashCode() {
        ServiceResult serviceResult1 = ServiceResult.createServiceResult();
        assertInstanceOf(ServiceResult.class, serviceResult1);

        int hashCode1 = serviceResult1.hashCode();

        ServiceResult serviceResult2 = ServiceResult.createServiceResult();
        assertInstanceOf(ServiceResult.class, serviceResult2);

        int hashCode2 = serviceResult2.hashCode();

        assertNotEquals(hashCode1, hashCode2);

        List.of(
                TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach(serviceResult -> {
            assertNotEquals(hashCode1, serviceResult.hashCode());
            assertNotEquals(hashCode2, serviceResult.hashCode());
        });

        Map<ServiceResult, Integer> serviceResultMap = Map.of(
                TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE, TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE.hashCode(),
                TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE, TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE.hashCode(),
                TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE, TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE.hashCode(),
                TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE, TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE.hashCode(),
                serviceResult1, serviceResult1.hashCode(),
                serviceResult2, serviceResult2.hashCode()
        );

        assertEquals(6, serviceResultMap.size());
    }
}