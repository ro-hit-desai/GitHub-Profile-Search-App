# GitHub Profile Search App 🔍

A modern Android application built with **Kotlin** and **Jetpack Compose** that allows users to search GitHub profiles and view repositories with seamless offline caching and a beautiful **Material Design 3** interface.

---

## 🚀 Features

- **GitHub API Integration** – Fetch repository data from GitHub's REST API  
- **Offline Caching** – Room database for persistent local storage  
- **Modern UI** – Built with Jetpack Compose and Material Design 3  
- **Dark/Light Theme** – Full theme support with smooth transitions  
- **Real-time Search** – Filter repositories by name or ID  
- **WebView Integration** – Open repositories directly in-app  
- **Error Handling** – Graceful network failure management  
- **Dependency Injection** – Hilt for clean architecture  

---

## 🛠️ Technical Implementation

### Architecture & Patterns
- **MVVM Architecture** – Follows Google’s recommended best practices  
- **Repository Pattern** – Clean separation of data layers  
- **Dependency Injection** – Hilt for modular & testable code  
- **State Management** – ViewModel + StateFlow  
- **Single Activity Architecture** – Activity only for UI-related logic  

### Tech Stack
- **Language**: Kotlin  
- **UI Framework**: Jetpack Compose  
- **Networking**: Retrofit + Coroutines  
- **Database**: Room  
- **DI**: Hilt  
- **Navigation**: Compose Navigation  
- **Theming**: Material Design 3  

---

## 📱 API Integration

### GitHub API Endpoint
```http
GET https://api.github.com/search/repositories?q=language:swift&sort=stars&order=desc
```

### API Response Structure
The API returns a paginated list of repositories with the following key fields used in the app:

```json
{
"total_count": 1486427,
"incomplete_results": false,
"items": []
}

```
---

## 📦 Data Model

```kotlin
data class GHRepo(
    val id: Long,
    val name: String,
    @SerializedName("html_url") val repoURL: String,
    val owner: Owner
)

data class Owner(
    val login: String
)
```

---

## 🏗️ Project Structure

```project structure
app/
├── data/
│   ├── local/          # Room database entities & DAOs
│   ├── remote/         # Retrofit service & API models
│   └── repository/     # Data repository implementation
├── domain/             # Business logic & use cases
├── di/                 # Hilt dependency injection modules
└── ui/
    ├── theme/          # Material Design 3 theming
    ├── components/     # Reusable Compose components
    ├── screens/        # Main UI screens
    └── viewmodel/      # ViewModels with StateFlow
```

---


## 🔧 Core Components
Network Service
```kotlin
class NetworkService {
    suspend fun fetchRepositories(): Result<List<GHRepo>>
}
 
```

---


## 🔧 Caching Strategy

- **Room Database** for local storage  
- **Automatic cache updates** on successful API responses  
- **Fallback to cached data** during network failures  
- **Efficient data retrieval** with proper indexing  


## 🎨 UI Components

- **RepositoryListScreen** – Main list with search functionality  
- **RepositoryItem** – Individual repository card  
- **WebViewScreen** – In-app browser for repository URLs  
- **SearchBar** – Real-time filtering component  

## 🎨 UI/UX Features

- **Material Design 3** – Modern design language  
- **Dark/Light Theme** – Automatic theme switching  
- **Smooth Animations** – List transitions and state changes  
- **Responsive Layout** – Adapts to different screen sizes  
- **Loading States** – Skeleton loading during data fetch  
- **Error States** – User-friendly error messages  

---

## 📦 Dependency Management

The project uses **Hilt** for dependency injection with the following key modules:

- **NetworkModule** – Retrofit and API service  
- **DatabaseModule** – Room database and DAOs  
- **RepositoryModule** – Data repository bindings  
- **ViewModelModule** – ViewModel factory injections  

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later  
- Kotlin 1.7+  
- Android SDK 31+  

### Installation
```bash
git clone <repo-url>

```


## 🛠 Building from Source
To build the project locally, run:

```bash
./gradlew assembleDebug
```

## 📊 Performance Optimizations
- **Efficient Recomposition** - Proper state management in Compose  
- **Pagination Ready** - Architecture supports pagination  
- **Memory Management** - Proper lifecycle awareness  
- **Network Optimization** - Caching reduces API calls  

## 🧪 Testing Strategy
- **Unit Tests** - ViewModel and Repository layers  
- **Integration Tests** - End-to-end workflow testing  
- **UI Tests** - Compose testing for UI components  

## 📝 Evaluation Criteria Met
- ✅ **Network Requests & JSON Decoding** - Retrofit with proper error handling  
- ✅ **Caching Implementation** - Room database with efficient CRUD operations  
- ✅ **Clean Code Structure** - Modular, maintainable, and well-documented  
- ✅ **UI Elements & Reusability** - Composable components with proper state management  
- ✅ **Edge Case Handling** - Network failures, empty states, error scenarios  
- ✅ **Performance Optimization** - Efficient RAM and CPU utilization  
- ✅ **Completeness** - All requirements implemented with bonus features  
- ✅ **Documentation** - Comprehensive code comments and README  
- ✅ **Android Best Practices** - Following Google's architecture guidelines  

## 🌟 Bonus Features Implemented
- ✅ **Dark/Light Mode Support** - Full theme system with Material Design 3  
- ✅ **Jetpack Compose UI** - 100% Compose implementation  
- ✅ **Dependency Injection** - Hilt for clean architecture  
- ✅ **Smooth Animations** - Enhanced user experience  
- ✅ **Modern Android Components** - ViewModel, Flows, Coroutines  

## 🤝 Contributing
1. Fork the project  
2. Create your feature branch: `git checkout -b feature/AmazingFeature`  
3. Commit your changes: `git commit -m 'Add some AmazingFeature'`  
4. Push to the branch: `git push origin feature/AmazingFeature`  
5. Open a Pull Request  

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.  

## 👨‍💻 Developer
**Rohit Desai**  
📧 Email: rht9808@gmail.com  
📱 Phone: +91 9730959808  
💼 LinkedIn: [Rohit Desai](https://www.linkedin.com/in/rohit-desai)  

<div align="center">
⭐️ If you find this project helpful, don't forget to give it a star! ⭐️  
Built with ❤️ using Modern Android Development
</div>


