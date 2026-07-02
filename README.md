This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop (JVM).

* [/androidApp](./androidApp) contains the entry point for the Android application. It manages Android-specific configurations and manifests.

* [/desktopApp](./desktopApp) contains the entry point for the Desktop (JVM) application.

* [/webApp](./webApp) contains the entry point for the Web application, supporting both Wasm and JS targets.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/sharedLogic](./sharedLogic/src) is for the business logic code that is shared between all targets.
  The most important subfolder is [commonMain](./sharedLogic/src/commonMain/kotlin).

* [/sharedUI](./sharedUI/src) is for UI components and screens shared across Compose Multiplatform targets.
  - [commonMain](./sharedUI/src/commonMain/kotlin) contains the shared Compose code.

* [/gradle](./gradle) contains Gradle configuration files, such as the [version catalog](./gradle/libs.versions.toml) for managing dependencies.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`
- Web app:
  - Wasm target (faster, modern browsers): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
  - JS target (slower, supports older browsers): `./gradlew :webApp:jsBrowserDevelopmentRun`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :sharedUI:testAndroidHostTest :sharedLogic:testAndroidHostTest`
- Desktop tests: `./gradlew :sharedUI:jvmTest :sharedLogic:jvmTest`
- Web tests:
  - Wasm target: `./gradlew :sharedUI:wasmJsTest :sharedLogic:wasmJsTest`
  - JS target: `./gradlew :sharedUI:jsTest :sharedLogic:jsTest`
- iOS tests: `./gradlew :sharedLogic:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).