import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        String expectedStringRegexTemplate = "(?i)^ServiceResult: \\[id: [a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}, date: [a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message: %s]$";

        List.of(
                TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE,
                TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach(serviceResult -> {
            String serviceResultString = serviceResult.toString();
            assertInstanceOf(String.class, serviceResultString);
            assertTrue(serviceResultString.matches(expectedStringRegexTemplate.formatted(serviceResult.getMessage())));
        });
    }

    @Test
    void testEquals() {
        ServiceResult serviceResult = ServiceResult.createServiceResult();
        assertInstanceOf(ServiceResult.class, serviceResult);

        ServiceResult serviceResultOther = ServiceResult.createServiceResult();
        assertInstanceOf(ServiceResult.class, serviceResultOther);

        assertNotEquals(serviceResult, serviceResultOther);
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