import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.firestore.FirebaseFirestore
import com.retro.retroapp.BuildConfig
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


class FirestoreInitializerTest {

    private lateinit var firestoreInitializer: FirestoreInitializer
    private lateinit var mockContext: Context
    private lateinit var mockFirestore: FirebaseFirestore

    @BeforeEach
    fun setUp() {
        firestoreInitializer = FirestoreInitializer()
        mockContext = mockk(relaxed = true)
        mockFirestore = mockk(relaxed = true)

        // Mock Firestore static instance
        mockkStatic(FirebaseFirestore::class)
        every { FirebaseFirestore.getInstance() } returns mockFirestore
    }

    @Test
    fun `test Firestore instance is created`() {
        val firestore = firestoreInitializer.create(mockContext)
        assertNotNull(firestore)
    }

    @Test
    fun `test Firestore uses emulator in debug mode`() {
        mockkObject(DebugHelper)
        every { DebugHelper.isDebug } returns true

        firestoreInitializer.create(mockContext)

        verify {
            mockFirestore.useEmulator(
                BuildConfig.FIRESTORE_EMULATOR_HOST,
                BuildConfig.FIRESTORE_EMULATOR_PORT
            )
        }
    }

    @Test
    fun `test Firestore does not use emulator in release mode`() {
        mockkObject(DebugHelper)
        every { DebugHelper.isDebug } returns false

        firestoreInitializer.create(mockContext)

        verify(exactly = 0) { mockFirestore.useEmulator(any(), any()) }
    }

    @Test
    fun `test dependencies returns empty list`() {
        val dependencies: MutableList<Class<out Initializer<*>>> =
            firestoreInitializer.dependencies()
        assertTrue(dependencies.isEmpty()) // Verify the list is empty
    }
}
