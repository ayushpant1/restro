import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.retro.retroapp.BuildConfig
import com.retro.retroapp.util.AuthInitializer
import com.retro.retroapp.util.DebugHelper
import com.retro.retroapp.util.FirestoreInitializer
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class AuthInitializerTest {

    private lateinit var authInitializer: AuthInitializer
    private lateinit var mockContext: Context
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @BeforeEach
    fun setUp() {
        authInitializer = AuthInitializer()
        mockContext = mockk(relaxed = true)
        mockFirebaseAuth = mockk(relaxed = true)

        // Mock Firestore static instance
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockFirebaseAuth
    }

    @Test
    fun `test Firestore instance is created`() {
        val firestore = authInitializer.create(mockContext)
        assertNotNull(firestore)
    }

    @Test
    fun `test Firestore uses emulator in debug mode`() {
        mockkObject(DebugHelper)
        every { DebugHelper.isDebug } returns true

        authInitializer.create(mockContext)

        verify { mockFirebaseAuth.useEmulator(BuildConfig.AUTH_EMULATOR_HOST, BuildConfig.AUTH_EMULATOR_PORT) }
    }

    @Test
    fun `test Firestore does not use emulator in release mode`() {
        mockkObject(DebugHelper)
        every { DebugHelper.isDebug } returns false

        authInitializer.create(mockContext)

        verify(exactly = 0) { mockFirebaseAuth.useEmulator(any(), any()) }
    }

    @Test
    fun `test dependencies returns empty list`() {
        val dependencies: MutableList<Class<out Initializer<*>>> = authInitializer.dependencies()
        assertTrue(dependencies.isEmpty()) // Verify the list is empty
    }
}
