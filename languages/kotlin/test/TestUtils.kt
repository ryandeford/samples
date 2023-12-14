
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.lang.reflect.Field
import java.time.Instant
import java.util.*

class TestUtils private constructor() {
    companion object {
        val SAMPLE_SERVICE_RESULT_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val SAMPLE_SERVICE_RESULT_DATE: Date = Date.from(Instant.parse("1111-11-11T11:11:11.111Z"))
        const val SAMPLE_SERVICE_RESULT_MESSAGE = "Test Sample Service Result Message"

        const val EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN = "(?i)^ServiceResult\\(id=[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}, date=[a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message=%s\\)$"

        val SERVICE_RESULT_WITH_NO_MESSAGE = ServiceResult()
        val SERVICE_RESULT_WITH_NULL_MESSAGE = ServiceResult(message = null)
        val SERVICE_RESULT_WITH_EMPTY_MESSAGE = ServiceResult(message = "")
        val SERVICE_RESULT_WITH_SAMPLE_MESSAGE = ServiceResult(message = SAMPLE_SERVICE_RESULT_MESSAGE)

        fun createServiceResultWithNoMessage(): ServiceResult {
            return ServiceResult()
        }

        fun createServiceResultWithNullMessage(): ServiceResult {
            return ServiceResult(message = null)
        }

        fun createServiceResultWithEmptyMessage(): ServiceResult {
            return ServiceResult(message = "")
        }

        fun createServiceResultWithSampleMessage(): ServiceResult {
            return ServiceResult(message = SAMPLE_SERVICE_RESULT_MESSAGE)
        }

        fun isServiceResultValid(serviceResult: ServiceResult?): Boolean {
            /*
            Note: Even though we're guaranteed to have non-null values for id/date fields
            under normal conditions due to Kotlin type contracts, we're adding a safeguard check
            here just in case consumers are using poor practices via reflection or other destructive
            runtime features. Some IDEs may suggest removing the extra checks, but we'll leave these
            here for now so that unit tests pass and validation checks are more robust
             */
            return serviceResult != null && serviceResult.id != null && serviceResult.date != null
        }
    }

    @Test
    fun verifySampleServiceResultId() {
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), SAMPLE_SERVICE_RESULT_ID)
    }

    @Test
    fun verifySampleServiceResultDate() {
        assertEquals(Date.from(Instant.parse("1111-11-11T11:11:11.111Z")), SAMPLE_SERVICE_RESULT_DATE)
    }

    @Test
    fun verifySampleServiceResultMessage() {
        assertEquals("Test Sample Service Result Message", SAMPLE_SERVICE_RESULT_MESSAGE)
    }

    @Test
    fun verifyExpectedServiceResultMessagePattern() {
        assertEquals(
            "(?i)^ServiceResult\\(id=[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}, date=[a-z]{3} [a-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-z]{3} \\d{4,}, message=%s\\)$",
            EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN
        )
    }

    @Test
    fun verifyServiceResultWithNoMessage() {
        assertInstanceOf(ServiceResult::class.java, SERVICE_RESULT_WITH_NO_MESSAGE)
        assertInstanceOf(UUID::class.java, SERVICE_RESULT_WITH_NO_MESSAGE.id)
        assertInstanceOf(Date::class.java, SERVICE_RESULT_WITH_NO_MESSAGE.date)
        assertNull(SERVICE_RESULT_WITH_NO_MESSAGE.message)

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_NO_MESSAGE))
    }

    @Test
    fun verifyCreateServiceResultWithNoMessage() {
        val serviceResultWithNoMessage = createServiceResultWithNoMessage()

        assertInstanceOf(ServiceResult::class.java, serviceResultWithNoMessage)
        assertInstanceOf(UUID::class.java, serviceResultWithNoMessage.id)
        assertInstanceOf(Date::class.java, serviceResultWithNoMessage.date)
        assertNull(serviceResultWithNoMessage.message)

        assertTrue(isServiceResultValid(serviceResultWithNoMessage))
    }

    @Test
    fun verifyServiceResultWithNullMessage() {
        assertInstanceOf(ServiceResult::class.java, SERVICE_RESULT_WITH_NULL_MESSAGE)
        assertInstanceOf(UUID::class.java, SERVICE_RESULT_WITH_NULL_MESSAGE.id)
        assertInstanceOf(Date::class.java, SERVICE_RESULT_WITH_NULL_MESSAGE.date)
        assertNull(SERVICE_RESULT_WITH_NULL_MESSAGE.message)

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_NULL_MESSAGE))
    }

    @Test
    fun verifyCreateServiceResultWithNullMessage() {
        val serviceResultWithNullMessage = createServiceResultWithNullMessage()

        assertInstanceOf(ServiceResult::class.java, serviceResultWithNullMessage)
        assertInstanceOf(UUID::class.java, serviceResultWithNullMessage.id)
        assertInstanceOf(Date::class.java, serviceResultWithNullMessage.date)
        assertNull(serviceResultWithNullMessage.message)

        assertTrue(isServiceResultValid(serviceResultWithNullMessage))
    }

    @Test
    fun verifyServiceResultWithEmptyMessage() {
        assertInstanceOf(ServiceResult::class.java, SERVICE_RESULT_WITH_EMPTY_MESSAGE)
        assertInstanceOf(UUID::class.java, SERVICE_RESULT_WITH_EMPTY_MESSAGE.id)
        assertInstanceOf(Date::class.java, SERVICE_RESULT_WITH_EMPTY_MESSAGE.date)
        assertEquals("", SERVICE_RESULT_WITH_EMPTY_MESSAGE.message)

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_EMPTY_MESSAGE))
    }

    @Test
    fun verifyCreateServiceResultWithEmptyMessage() {
        val serviceResultWithEmptyMessage = createServiceResultWithEmptyMessage()

        assertInstanceOf(ServiceResult::class.java, serviceResultWithEmptyMessage)
        assertInstanceOf(UUID::class.java, serviceResultWithEmptyMessage.id)
        assertInstanceOf(Date::class.java, serviceResultWithEmptyMessage.date)
        assertEquals("", serviceResultWithEmptyMessage.message)

        assertTrue(isServiceResultValid(serviceResultWithEmptyMessage))
    }

    @Test
    fun verifyServiceResultWithSampleMessage() {
        assertInstanceOf(ServiceResult::class.java, SERVICE_RESULT_WITH_SAMPLE_MESSAGE)
        assertInstanceOf(UUID::class.java, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.id)
        assertInstanceOf(Date::class.java, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.date)
        assertEquals(SAMPLE_SERVICE_RESULT_MESSAGE, SERVICE_RESULT_WITH_SAMPLE_MESSAGE.message)

        assertTrue(isServiceResultValid(SERVICE_RESULT_WITH_SAMPLE_MESSAGE))
    }

    @Test
    fun verifyCreateServiceResultWithSampleMessage() {
        val serviceResultWithSampleMessage = createServiceResultWithSampleMessage()

        assertInstanceOf(ServiceResult::class.java, serviceResultWithSampleMessage)
        assertInstanceOf(UUID::class.java, serviceResultWithSampleMessage.id)
        assertInstanceOf(Date::class.java, serviceResultWithSampleMessage.date)
        assertEquals(SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultWithSampleMessage.message)

        assertTrue(isServiceResultValid(serviceResultWithSampleMessage))
    }

    @Test
    fun verifyIsServiceResultValid() {
        assertFalse(isServiceResultValid(null))

        listOf(
            SERVICE_RESULT_WITH_NO_MESSAGE,
            SERVICE_RESULT_WITH_NULL_MESSAGE,
            SERVICE_RESULT_WITH_EMPTY_MESSAGE,
            SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach { serviceResult ->
            assertTrue(isServiceResultValid(serviceResult))
        }

        listOf("id", "date").forEach { fieldName ->
            try {
                val serviceResultWithInvalidField: ServiceResult = createServiceResultWithSampleMessage()

                val field: Field = serviceResultWithInvalidField.javaClass.getDeclaredField(fieldName)
                field.setAccessible(true)
                field.set(serviceResultWithInvalidField, null)

                assertNull(field.get(serviceResultWithInvalidField))

                when (fieldName) {
                    "id" -> assertNull(serviceResultWithInvalidField.id)
                    "date" -> assertNull(serviceResultWithInvalidField.date)
                    else -> throw IllegalStateException("Unexpected value: $fieldName")
                }
                assertFalse(isServiceResultValid(serviceResultWithInvalidField))
            } catch (e: Exception) {
                fail(e)
            }
        }
    }
}