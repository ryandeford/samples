import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultServiceImplTest {

    @Test
    void executeRequest() {
        ServiceResult serviceResult = new DefaultServiceImpl().executeRequest();

        assertInstanceOf(ServiceResult.class, serviceResult);
        assertInstanceOf(UUID.class, serviceResult.getId());
        assertInstanceOf(Date.class, serviceResult.getDate());
        assertInstanceOf(String.class, serviceResult.getMessage());

        String expectedServiceResultMessage = "Default service result from DefaultServiceImpl";

        assertEquals(expectedServiceResultMessage, serviceResult.getMessage());

        assertTrue(serviceResult.toString().matches(
                TestUtils.EXPECTED_SERVICE_RESULT_MESSAGE_PATTERN.formatted(expectedServiceResultMessage)
        ));
    }
}