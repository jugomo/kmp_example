import SwiftUI
import shared

// ------------------- USE NATIVE SWIFTUI -----------------------------------------------------------------------

struct ContentView: View {
    @State private var showContent = false
    @State private var catPhotos: [CatPhoto] = []
    @State private var catPhotosError: String?
    @State private var selectedPhoto: CatPhoto?

    var body: some View {
        VStack {
            Button("Click me!") {
                withAnimation {
                    showContent = !showContent
                }
            }

            if showContent {
                VStack(spacing: 16) {
                    Image(systemName: "swift")
                        .font(.system(size: 200))
                        .foregroundColor(.accentColor)
                    Text("SwiftUI: \(Greeting().greet())")
                }
                .transition(.move(edge: .top).combined(with: .opacity))
            }

            List {
                if let catPhotosError {
                    Text("Error: \(catPhotosError)")
                } else if catPhotos.isEmpty {
                    Text("Cargando gatos...")
                } else {
                    ForEach(catPhotos, id: \.id) { photo in
                        HStack {
                            AsyncImage(url: URL(string: photo.url)) { image in
                                image.resizable().aspectRatio(contentMode: .fill)
                            } placeholder: {
                                ProgressView()
                            }
                            .frame(width: 60, height: 60)
                            .clipped()

                            VStack(alignment: .leading) {
                                Text(photo.id)
                                Text("\(photo.width) x \(photo.height)")
                            }
                        }
                        .contentShape(Rectangle())
                        .onTapGesture {
                            selectedPhoto = photo
                        }
                    }
                }
            }
            .refreshable {
                await loadCatPhotos()
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .task {
            await loadCatPhotos()
        }
        .fullScreenCover(isPresented: Binding(
            get: { selectedPhoto != nil },
            set: { isPresented in
                if !isPresented {
                    selectedPhoto = nil
                }
            }
        )) {
            if let selectedPhoto {
                CatPhotoDetailView(photo: selectedPhoto) {
                    self.selectedPhoto = nil
                }
            }
        }
    }

    private func loadCatPhotos() async {
        do {
            catPhotos = try await CatApiKt.getRandomCatPhotos()
            catPhotosError = nil
        } catch {
            catPhotosError = error.localizedDescription
        }
    }
}

struct CatPhotoDetailView: View {
    let photo: CatPhoto
    let onBack: () -> Void

    var body: some View {
        VStack {
            HStack {
                Button("← Atrás", action: onBack)
                Spacer()
            }
            .padding()

            AsyncImage(url: URL(string: photo.url)) { image in
                image.resizable().aspectRatio(contentMode: .fit)
            } placeholder: {
                ProgressView()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

// ------------------- USE COMPOSE -----------------------------------------------------------------------

// struct ComposeView: UIViewControllerRepresentable {
//     func makeUIViewController(context: Context) -> UIViewController {
//         MainViewControllerKt.MainViewController() // Llama a la función de Kotlin
//     }
//
//     func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
// }
//
// struct ContentView: View {
//     var body: some View {
//         ComposeView()
//             .ignoresSafeArea(.all) // Permite que Compose maneje los márgenes
//     }
// }