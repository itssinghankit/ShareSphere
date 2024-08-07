# Social Media Android App

Welcome to ShareSphere, the next-generation social media platform that brings people together in
exciting new ways. Built with cutting-edge Android technology in `Jetpack Compose` and powered by a robust `Node.js`
backend, ShareSphere offers a seamless, intuitive, and feature-rich social experience right at your
fingertips.

---
## Features

- **User Authentication**: Secure registration and login system
- **Image Sharing**: Upload and share images with your friends
- **Real-time Chat**: Communicate with other users instantly
- **Social Interactions**:
    - Like and comment on posts
    - Follow other users
    - View follower/following lists
- **Notifications**: Stay updated with real-time notifications
- **Post Management**:
    - Save posts for later viewing
    - View and update your profile details
- **Day and Night Mode**: Reduce eye strain with easy-to-toggle light and dark themes

## Screenshots

---
## Architecture and Design

ShareSphere is built with modern Android development best practices in mind:

- **MVVM Clean Architecture**: Ensures separation of concerns, making the codebase more maintainable
  and testable.
- **Material 3 Components**: Utilizes the latest Material Design practices and components for a
  modern and consistent user interface.

## Tech Stack

### Frontend (Android)

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3

### Backend

- **Server**: Node.js
- **Database**: MongoDB
- **Deployment**: Vercel

For more details on the backend, check out
our [Backend Repository](https://github.com/itssinghankit/ShareSphere-Backend).

## Dependencies

- **Jetpack Compose**: Modern toolkit for building native Android UI
- **Coroutines**: For managing background tasks and asynchronous code
- **Hilt**: Dependency injection library
- **Retrofit & OkHttp**: For making network requests
- **Navigation Component**: For in-app navigation
- **ViewModel & Lifecycle**: For lifecycle-aware data management
- **Coil**: Image loading library
- **DataStore**: For storing key-value pairs and typed objects
- **Timber**: For logging
- **Gson**: JSON parsing library
- **Paging 3**: For efficiently loading large data sets
- **Compose ConstraintLayout**: For creating flexible layouts in Compose

## Getting Started

1. Clone the repository

```bash
git clone https://github.com/itssinghankit/ShareSphere.git
```

2. Add backend `Base Url` to `local.properties`

```bash
BASE_URL = https://share-sphere-backend.vercel.app/api/v1/
```

3. Open the project in Android Studio
4. Sync the project with Gradle files
5. Run the app on an emulator or physical device

## Contributing

We welcome contributions to improve the app! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Contact

Feel free to reach out with any questions or feedback at
[singhankit.kr@gmail.com](singhankit.kr@gmail.com)