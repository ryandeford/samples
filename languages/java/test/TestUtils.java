import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {

    public static final UUID SAMPLE_SERVICE_RESULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static final Date SAMPLE_SERVICE_RESULT_DATE = Date.from(Instant.parse("1111-11-11T11:11:11.111Z"));

    public static final String SAMPLE_SERVICE_RESULT_MESSAGE = "Test Sample Service Result Message";

    public static final String EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN = "(?i)^ServiceResult: \\[id: [a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}, date: [a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message: %s]$";

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

    public static void setObjectFieldValue(Object targetObject, String targetFieldName, Object targetValue) {
        if (targetObject == null) {
            throw new IllegalArgumentException(
                    "Unable to set a field value on a null target object: [targetObject=null]"
            );
        }
        if (targetFieldName == null || targetFieldName.isBlank()) {
            throw new IllegalArgumentException(
                    String.format("Unable to set a field value for an undefined target field name: [targetFieldName=%s]", targetFieldName)
            );
        }
        if (Arrays.stream(targetObject.getClass().getDeclaredFields()).noneMatch(f -> f.getName().equals(targetFieldName))) {
            throw new IllegalArgumentException(
                    String.format("Unable to set a field value for a non-existent target field name: [targetFieldName=%s, detectedFieldNames=%s]", targetFieldName, Arrays.stream(targetObject.getClass().getDeclaredFields()).map(Field::getName).toList())
            );
        }

        try {
            Field field = targetObject.getClass().getDeclaredField(targetFieldName);

            if (targetValue != null && !field.getType().isAssignableFrom(targetValue.getClass())) {
                throw new IllegalArgumentException(
                        String.format(
                                "Unable to set an invalid field value type for target field name: [targetFieldName=%s, targetValue=%s, targetValueType=%s, detectedValueType=%s]",
                                targetFieldName,
                                targetValue,
                                targetValue.getClass(),
                                field.getType()
                        )
                );
            }

            field.setAccessible(true);
            field.set(targetObject, targetValue);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format(
                            "An error occurred when trying to set a field value on the target object: %s: %s",
                            String.format("[targetObject=%s, targetFieldName=%s, targetValue=%s]", targetObject, targetFieldName, targetValue),
                            e.getMessage()
                    )
            );
        }
    }

    @Test
    public void verifySampleServiceResultId() {
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), SAMPLE_SERVICE_RESULT_ID);
    }

    @Test
    public void verifySampleServiceResultDate() {
        assertEquals(Date.from(Instant.parse("1111-11-11T11:11:11.111Z")), SAMPLE_SERVICE_RESULT_DATE);
    }

    @Test
    public void verifySampleServiceResultMessage() {
        assertEquals("Test Sample Service Result Message", SAMPLE_SERVICE_RESULT_MESSAGE);
    }

    @Test
    public void verifyExpectedServiceResultMessagePattern() {
        assertEquals("(?i)^ServiceResult: \\[id: [a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}, date: [a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message: %s]$", EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN);
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

    @Test
    void setObjectFieldValueWithNullTargetObject() {
        Exception exceptionForNullTargetObject = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue(null, "targetFieldName", "targetValue")
        );
        assertEquals("Unable to set a field value on a null target object: [targetObject=null]", exceptionForNullTargetObject.getMessage());
    }

    @Test
    void setObjectFieldValueWithNullTargetFieldName() {
        Exception exceptionForNullTargetFieldName = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue("targetObject", null, "targetValue")
        );
        assertEquals("Unable to set a field value for an undefined target field name: [targetFieldName=null]", exceptionForNullTargetFieldName.getMessage());
    }

    @Test
    void setObjectFieldValueWithEmptyTargetFieldName() {
        Exception exceptionForEmptyTargetFieldName = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue("targetObject", "", "targetValue")
        );
        assertEquals("Unable to set a field value for an undefined target field name: [targetFieldName=]", exceptionForEmptyTargetFieldName.getMessage());
    }

    @Test
    void setObjectFieldValueWithBlankTargetFieldName() {
        Exception exceptionForBlankTargetFieldName = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue("targetObject", " ", "targetValue")
        );
        assertEquals("Unable to set a field value for an undefined target field name: [targetFieldName= ]", exceptionForBlankTargetFieldName.getMessage());
    }

    @Test
    void setObjectFieldValueWithNonExistentTargetFieldName() {
        ServiceResult targetObject = ServiceResult.createServiceResult();

        List<String> targetObjectDetectedFieldNames = Arrays.stream(targetObject.getClass().getDeclaredFields()).map(Field::getName).toList();
        final String targetFieldNameNonExistent = "targetFieldNameNonExistent";

        Exception exceptionForNonExistentTargetFieldName = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue(targetObject, targetFieldNameNonExistent, "targetValue")
        );
        assertEquals(
                String.format(
                        "Unable to set a field value for a non-existent target field name: [targetFieldName=%s, detectedFieldNames=%s]",
                        targetFieldNameNonExistent,
                        targetObjectDetectedFieldNames
                ),
                exceptionForNonExistentTargetFieldName.getMessage()
        );
    }

    @Test
    void setObjectFieldValueWithInvalidTargetValueType() {
        ServiceResult targetObject = ServiceResult.createServiceResult();
        String targetFieldName = "id";
        String targetValue = "badTargetValue";

        Exception exceptionForInvalidTargetValueType = assertThrows(
                IllegalArgumentException.class,
                () -> setObjectFieldValue(targetObject, targetFieldName, targetValue)
        );
        assertEquals(
                String.format(
                        "Unable to set an invalid field value type for target field name: [targetFieldName=%s, targetValue=%s, targetValueType=%s, detectedValueType=%s]",
                        targetFieldName,
                        targetValue,
                        targetValue.getClass(),
                        UUID.class
                ),
                exceptionForInvalidTargetValueType.getMessage()
        );
    }

    @Test
    void setObjectFieldValueWithNullValue() {
        ServiceResult targetObject = ServiceResult.createServiceResult();
        assertNotNull(targetObject.getId());
        setObjectFieldValue(targetObject, "id", null);
        assertNull(targetObject.getId());
    }

    @Test
    void setObjectFieldValueWithNewValue() {
        ServiceResult targetObject = ServiceResult.createServiceResult();
        UUID targetObjectId = targetObject.getId();
        assertNotNull(targetObjectId);
        assertEquals(targetObjectId, targetObject.getId());
        UUID targetObjectIdNew = UUID.randomUUID();
        assertNotEquals(targetObjectIdNew, targetObjectId);
        setObjectFieldValue(targetObject, "id", targetObjectIdNew);
        assertNotEquals(targetObjectId, targetObject.getId());
        assertEquals(targetObjectIdNew, targetObject.getId());
    }
}
