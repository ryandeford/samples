import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2

class ServiceResultTest {

    @Test
    fun createServiceResult() {
        val serviceResult = ServiceResult()

        assertInstanceOf(UUID::class.java, serviceResult.id)
        assertInstanceOf(Date::class.java, serviceResult.date)
        assertNull(serviceResult.message)
    }

    @Test
    fun createServiceResultWithCustomMessage() {
        val serviceResultWithCustomMessage = ServiceResult(message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE)

        assertInstanceOf(UUID::class.java, serviceResultWithCustomMessage.id)
        assertInstanceOf(Date::class.java, serviceResultWithCustomMessage.date)
        assertInstanceOf(String::class.java, serviceResultWithCustomMessage.message)

        assertEquals(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultWithCustomMessage.message)
        assertSame(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultWithCustomMessage.message)
    }

    @Test
    fun getId() {
        val serviceResult = ServiceResult()
        assertInstanceOf(UUID::class.java, serviceResult.id)
    }

    @Test
    fun getDate() {
        val serviceResult = ServiceResult()
        assertInstanceOf(Date::class.java, serviceResult.date)
    }

    @Test
    fun getMessage() {
        mapOf(
            ServiceResult() to null,
            ServiceResult(message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE) to TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE,
        ).forEach { (serviceResult, expectedMessage) ->
            assertEquals(expectedMessage, serviceResult.message)
            assertSame(expectedMessage, serviceResult.message)
        }
    }

    @Test
    operator fun component1() {
        val serviceResult = ServiceResult()
        val (component1) = serviceResult

        assertInstanceOf(UUID::class.java, component1)
        assertEquals(serviceResult.id, component1)
        assertSame(serviceResult.id, component1)
    }

    @Test
    operator fun component2() {
        val serviceResult = ServiceResult()
        val (_, component2) = serviceResult

        assertInstanceOf(Date::class.java, component2)
        assertEquals(serviceResult.date, component2)
        assertSame(serviceResult.date, component2)
    }

    @Test
    operator fun component3() {
        mapOf(
            ServiceResult() to null,
            ServiceResult(message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE) to TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE,
        ).forEach { (serviceResult, expectedComponent3) ->
            val (_, _, component3) = serviceResult

            assertEquals(expectedComponent3, component3)
            assertSame(expectedComponent3, component3)
            assertEquals(serviceResult.message, component3)
            assertSame(serviceResult.message, component3)
        }
    }

    @Test
    fun copy() {
        val serviceResult = ServiceResult()

        val serviceResultCopy = serviceResult.copy()
        assertInstanceOf(ServiceResult::class.java, serviceResultCopy)
        assertEquals(serviceResult, serviceResultCopy)
        assertEquals(serviceResult.id, serviceResultCopy.id)
        assertEquals(serviceResult.date, serviceResultCopy.date)
        assertEquals(serviceResult.message, serviceResultCopy.message)
        assertNotSame(serviceResult, serviceResultCopy)

        val serviceResultCopyWithCustomMessage = serviceResult.copy(message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE)

        assertInstanceOf(ServiceResult::class.java, serviceResultCopyWithCustomMessage)
        assertEquals(serviceResult.id, serviceResultCopyWithCustomMessage.id)
        assertNotEquals(serviceResult.message, serviceResultCopyWithCustomMessage.message)
        assertEquals(TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE, serviceResultCopyWithCustomMessage.message)
        assertEquals(serviceResult.date, serviceResultCopyWithCustomMessage.date)
        assertNotEquals(serviceResult, serviceResultCopyWithCustomMessage)
        assertNotSame(serviceResult, serviceResultCopyWithCustomMessage)

        val customId = TestUtils.SAMPLE_SERVICE_RESULT_ID
        val customDate = TestUtils.SAMPLE_SERVICE_RESULT_DATE
        val customMessage = "Test Custom Service Result Message"

        val serviceResultCopyWithAllCustomFields = serviceResult.copy(
            id = customId,
            date = customDate,
            message = customMessage,
        )

        assertInstanceOf(ServiceResult::class.java, serviceResultCopyWithAllCustomFields)
        assertEquals(customId, serviceResultCopyWithAllCustomFields.id)
        assertEquals(customDate, serviceResultCopyWithAllCustomFields.date)
        assertEquals(customMessage, serviceResultCopyWithAllCustomFields.message)
        assertNotEquals(serviceResult.id, serviceResultCopyWithAllCustomFields.id)
        assertNotEquals(serviceResult.date, serviceResultCopyWithAllCustomFields.date)
        assertNotEquals(serviceResult.message, serviceResultCopyWithAllCustomFields.message)
        assertNotEquals(serviceResult, serviceResultCopyWithAllCustomFields)
        assertNotSame(serviceResult, serviceResultCopyWithAllCustomFields)
    }

    @Test
    fun testToString() {
        listOf(
            TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach { serviceResult ->
            val serviceResultString = serviceResult.toString()
            assertInstanceOf(String::class.java, serviceResultString)
            assertTrue(serviceResultString.matches(TestUtils.EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN.format(serviceResult.message).toRegex()))
        }
    }

    @Test
    fun testEquals() {
        val serviceResult = ServiceResult(
            message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE,
        )

        assertInstanceOf(ServiceResult::class.java, serviceResult)

        val serviceResultOther = ServiceResult(
            date = TestUtils.SAMPLE_SERVICE_RESULT_DATE,
            message = TestUtils.SAMPLE_SERVICE_RESULT_MESSAGE,
        )

        assertInstanceOf(ServiceResult::class.java, serviceResultOther)

        assertNotEquals(serviceResult.id, serviceResultOther.id)
        assertNotEquals(serviceResult.date, serviceResultOther.date)
        assertEquals(serviceResult.message, serviceResultOther.message)
        assertNotEquals(serviceResult, serviceResultOther)

        // Note: Some IDEs may suggest changing this to a standard '==' check, but we want to call the equals method directly
        assertFalse(serviceResult.equals(serviceResultOther))

        val customId = UUID.randomUUID()
        val customDate = Date.from(Instant.now())
        val customMessage = "Test Custom Service Result Message"

        val serviceResultCustom1 = ServiceResult(id = customId, date = customDate, message = customMessage)
        val serviceResultCustom2 = ServiceResult(id = customId, date = customDate, message = customMessage)

        assertEquals(serviceResultCustom1.id, serviceResultCustom2.id)
        assertEquals(serviceResultCustom1.date, serviceResultCustom2.date)
        assertEquals(serviceResultCustom1.message, serviceResultCustom2.message)
        assertEquals(serviceResultCustom1, serviceResultCustom2)
        assertNotSame(serviceResultCustom1, serviceResultCustom2)

        // Note: Some IDEs may suggest changing this to a standard '==' check, but we want to call the equals method directly
        assertTrue(serviceResultCustom1.equals(serviceResultCustom2))
    }

    @Test
    fun testHashCode() {
        val serviceResult1 = ServiceResult()
        assertInstanceOf(ServiceResult::class.java, serviceResult1)

        val hashCode1 = serviceResult1.hashCode()

        val serviceResult2 = ServiceResult()
        assertInstanceOf(ServiceResult::class.java, serviceResult2)

        val hashCode2 = serviceResult2.hashCode()

        assertNotEquals(hashCode1, hashCode2)

        listOf(
            TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE,
            TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE
        ).forEach { serviceResult ->
            assertNotEquals(hashCode1, serviceResult.hashCode())
            assertNotEquals(hashCode2, serviceResult.hashCode())
        }

        val serviceResultMap = mapOf(
            TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE to TestUtils.SERVICE_RESULT_WITH_NO_MESSAGE.hashCode(),
            TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE to TestUtils.SERVICE_RESULT_WITH_NULL_MESSAGE.hashCode(),
            TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE to TestUtils.SERVICE_RESULT_WITH_EMPTY_MESSAGE.hashCode(),
            TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE to TestUtils.SERVICE_RESULT_WITH_SAMPLE_MESSAGE.hashCode(),
            serviceResult1 to serviceResult1.hashCode(),
            serviceResult2 to serviceResult2.hashCode()
        )

        assertEquals(6, serviceResultMap.size)
    }
}