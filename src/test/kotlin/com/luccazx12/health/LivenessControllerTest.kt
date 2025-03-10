import com.luccazx12.health.LivenessController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class LivenessControllerTest {

    private val livenessController = LivenessController()

    @Test
    fun `checkLiveness should return 200 OK`() {
        // Given
        val expectedStatusCode = HttpStatus.OK

        // When
        val response: ResponseEntity<Void> = livenessController.checkLiveness()

        // Then
        assertEquals(expectedStatusCode, response.statusCode)
        assertEquals(null, response.body)
    }
}
