// En iosMain
import androidx.compose.ui.window.ComposeUIViewController
import com.jugomo.kmp_example.App

fun MainViewController() = ComposeUIViewController {
    App() // Llama a tu función de commonMain
}
