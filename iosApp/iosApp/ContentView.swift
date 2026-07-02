import SwiftUI
import shared

// ------------------- USE NATIVE SWIFTUI -----------------------------------------------------------------------

// struct ContentView: View {
//     @State private var showContent = false
//     var body: some View {
//         VStack {
//             Button("Click me!") {
//                 withAnimation {
//                     showContent = !showContent
//                 }
//             }
//
//             if showContent {
//                 VStack(spacing: 16) {
//                     Image(systemName: "swift")
//                         .font(.system(size: 200))
//                         .foregroundColor(.accentColor)
//                     Text("SwiftUI: \(Greeting().greet())")
//                 }
//                 .transition(.move(edge: .top).combined(with: .opacity))
//             }
//         }
//         .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
//         .padding()
//     }
// }
//
// struct ContentView_Previews: PreviewProvider {
//     static var previews: some View {
//         ContentView()
//     }
// }

// ------------------- USE COMPOSE -----------------------------------------------------------------------

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController() // Llama a la función de Kotlin
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all) // Permite que Compose maneje los márgenes
    }
}