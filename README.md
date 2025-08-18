# Android MVP (Java)

This project shows a simple Android Project using [Model-View-Presenter Architecture pattern](https://github.com/hilfritz/Android-HBMvp/tree/v1/mvp-framework):

The sample project :
 * contains useful utility methods
 * uses RxJava handling processes like network requests
 * uses these popular libraries: LeakCanary, Fresco, Greenrobot (Eventbus), RxJava, Robolectric (Unit testing), Timber (Logging), Butterknife, Dagger 2, ...

To get more details of the MVP pattern code used in the project see [here on GitHub](https://github.com/hilfritz/Android-HBMvp/tree/v1/mvp-framework) for the structure of the code.


---

## ✨ Overview
This repository demonstrates the **Model–View–Presenter (MVP)** architecture pattern in Android using **Java**.  
It is designed as a **clean, modular, and testable** codebase to help developers understand and apply MVP in real-world Android applications.

## 🛠️ Tech Stack & Libraries
- **Java** — core language for Android development
- **RxJava** — for reactive, asynchronous operations (e.g., network requests)
- **Dagger 2** — dependency injection
- **ButterKnife** — view binding and boilerplate reduction
- **Timber** — lightweight logging
- **LeakCanary** — memory leak detection
- **Fresco** — image loading
- **EventBus (Greenrobot)** — event publishing/subscribing
- **Robolectric** — unit testing framework

## 📂 Project Structure
- **Model** → Manages business logic, data handling, and repositories  
- **View** → Handles UI (Activities/Fragments), displays data  
- **Presenter** → Acts as the middle layer between Model & View, contains presentation logic  

The **MVP framework** ensures:
- Separation of concerns  
- Easier testing and maintainability  
- Reusable components  

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version recommended)  
- Java 8 or higher  
- Gradle build system  

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/hilfritz/Android-HBMvp.git
   ```
2. Open the project in **Android Studio**  
3. Let Gradle sync the dependencies  
4. Run the app on an emulator or connected device  

## 🧪 Testing
This project uses **Robolectric** for unit tests.  
You can run the test suite via:

```bash
./gradlew test
```

## 📖 Further Reading
For an in-depth breakdown of the MVP pattern used in this project, check the [documentation here](https://github.com/hilfritz/Android-HBMvp/tree/v1/mvp-framework).

## 🤝 Contributing
Contributions are welcome! If you’d like to improve the project:  
1. Fork the repo  
2. Create a feature branch (`git checkout -b feature/awesome-feature`)  
3. Commit changes (`git commit -m "Add awesome feature"`)  
4. Push to branch (`git push origin feature/awesome-feature`)  
5. Open a Pull Request  

## 📜 License
This project is licensed under the MIT License.  
Feel free to use it for learning, experimenting, or as a boilerplate for your own MVP projects.

---
💡 *Crafted with clean architecture principles — perfect for recruiters, interview prep, or Android devs exploring MVP.*
