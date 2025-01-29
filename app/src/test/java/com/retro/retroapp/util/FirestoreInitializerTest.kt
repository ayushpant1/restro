import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.retro.retroapp.BuildConfig
import com.retro.retroapp.util.FirestoreInitializer
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

class FirestoreInitializerTest {

    private lateinit var firestoreInitializer: FirestoreInitializer
    private lateinit var mockContext: Context
    private lateinit var mockFirestore: FirebaseFirestore

    @Before
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
        setBuildConfigDebug(true)

        firestoreInitializer.create(mockContext)

        verify { mockFirestore.useEmulator("10.0.2.2", 8081) }
    }

    @Test
    fun `test Firestore does not use emulator in release mode`() {
        setBuildConfigDebug(false)

        firestoreInitializer.create(mockContext)

        verify(exactly = 0) { mockFirestore.useEmulator(any(), any()) }
    }

    // Helper function to modify BuildConfig.DEBUG
    private fun setBuildConfigDebug(value: Boolean) {
        val field = BuildConfig::class.java.getDeclaredField("DEBUG")
        field.isAccessible = true
        field.setBoolean(null, value)
    }
}
