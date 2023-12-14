import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import java.util.UUID

class DefaultServiceImplTest {

    @Test
    fun executeRequest() {
        val serviceResult = DefaultServiceImpl().executeRequest()

        assertInstanceOf(ServiceResult::class.java, serviceResult)
        assertInstanceOf(UUID::class.java, serviceResult.id)
        assertInstanceOf(Date::class.java, serviceResult.date)
        assertInstanceOf(String::class.java, serviceResult.message)

        val expectedServiceResultMessage = "Default service result from DefaultServiceImpl"

        assertEquals(expectedServiceResultMessage, serviceResult.message)

        assertTrue(
            serviceResult.toString().matches(
                TestUtils.EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN.format(expectedServiceResultMessage).toRegex()
            )
        )
    }
}