package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class NullsTest {

    companion object {
        private val nulls = Nulls()

        private const val VALID_DESCRIPTION = "Test Description"
        private const val DEFAULT_DESCRIPTION = "This is a default metadata description"

        private val metadataWithNullDescription = Nulls.Metadata(description = null)
        private val metadataWithValidDescription = Nulls.Metadata(description = VALID_DESCRIPTION)
        private val metadataWithDefaultDescription = Nulls.Metadata(description = DEFAULT_DESCRIPTION)

        private val dataWithNullMetadata = Nulls.Data(metadata = null)
        private val dataWithNullDescription = Nulls.Data(metadata = metadataWithNullDescription)
        private val dataWithValidDescription = Nulls.Data(metadata = metadataWithValidDescription)
        private val dataWithDefaultDescription = Nulls.Data(metadata = metadataWithDefaultDescription)

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            assertNotNull(metadataWithNullDescription)
            assertNull(metadataWithNullDescription.description)

            assertNotNull(metadataWithValidDescription)
            assertNotNull(metadataWithValidDescription.description)
            assertEquals(VALID_DESCRIPTION, metadataWithValidDescription.description)

            assertNotNull(metadataWithDefaultDescription)
            assertNotNull(metadataWithDefaultDescription.description)
            assertEquals(DEFAULT_DESCRIPTION, metadataWithDefaultDescription.description)

            assertNull(dataWithNullMetadata.metadata)

            assertNotNull(dataWithNullDescription.metadata)
            assertNull(dataWithNullDescription.metadata?.description)

            assertNotNull(dataWithValidDescription.metadata)
            assertNotNull(dataWithValidDescription.metadata?.description)
            assertEquals(VALID_DESCRIPTION, dataWithValidDescription.metadata?.description)

            assertNotNull(dataWithDefaultDescription.metadata)
            assertNotNull(dataWithDefaultDescription.metadata?.description)
            assertEquals(DEFAULT_DESCRIPTION, dataWithDefaultDescription.metadata?.description)
        }
    }

    @Test
    fun isNull() {
        assertEquals("The object is null", nulls.isNull(null))
        assertEquals("The object is not null: Test String", nulls.isNull("Test String"))
    }

    @Test
    fun getDescription() {
        assertNull(nulls.getDescription(null))
        assertNull(nulls.getDescription(dataWithNullMetadata))
        assertNull(nulls.getDescription(dataWithNullDescription))

        assertNotNull(nulls.getDescription(dataWithValidDescription))
        assertEquals(VALID_DESCRIPTION, nulls.getDescription(dataWithValidDescription))
    }

    @Test
    fun getDescriptionOrDefault() {
        assertEquals(DEFAULT_DESCRIPTION, nulls.getDescriptionOrDefault(null))
        assertEquals(DEFAULT_DESCRIPTION, nulls.getDescriptionOrDefault(dataWithNullMetadata))
        assertEquals(DEFAULT_DESCRIPTION, nulls.getDescriptionOrDefault(dataWithNullDescription))

        assertNotNull(nulls.getDescriptionOrDefault(dataWithValidDescription))
        assertEquals(VALID_DESCRIPTION, nulls.getDescriptionOrDefault(dataWithValidDescription))

        assertNotNull(nulls.getDescriptionOrDefault(dataWithDefaultDescription))
        assertEquals(DEFAULT_DESCRIPTION, nulls.getDescriptionOrDefault(dataWithDefaultDescription))
    }
}