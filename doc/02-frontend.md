
# Frontend Documentation
This documentation provides an overview of the frontend part of the application, which is developed using React with TypeScript. The frontend comprises various pages, shared components, hooks, interceptors, contexts, and external libraries utilized to enhance the functionality and user experience.

## Technology Stack
- **React**: A JavaScript library for building user interfaces.
- **TypeScript**: A statically typed superset of JavaScript that adds optional static typing.
- **@mui/material**: A modern React UI framework that provides components for building attractive and responsive user interfaces.
- **react-toastify**: A library used for displaying notifications to the user.
- **axios**: A library used for making HTTP requests.
- **react-social-media-embed**: A library used for embedding Instagram posts within the application.
## Project Structure
The frontend part of the application is structured as follows:

- src/:
  - pages/... Individual pages of the application.
  - shared/... Components, interceptors and other logic used in multiple places
    - components/: Contains reusable components used across multiple pages.
    - hooks/: Custom hooks used for managing state, side effects, and reusable logic.
    - interceptors/: Interceptors for handling HTTP requests and responses.
    - contexts/: Context providers for managing global state.
- App.tsx: The main entry point of the application.
- index.tsx: The entry point for rendering the React application.
    
## Key Features
- @mui/material
    Utilized for building modern and responsive user interface components.
    Offers a wide range of customizable components such as buttons, forms, dialogs, and more.
- react-toastify
    Used for displaying notifications to the user.
    Provides customizable toast notifications for success, error, warning, and info messages.
- axios
    Used for making HTTP requests to interact with backend APIs.
    Provides a simple and efficient API for handling asynchronous operations.
- react-social-media-embed
    Used for embedding Instagram posts within the application.
    Allows users to view Instagram posts without leaving the application.

## Getting Started

To set up the frontend part of the application locally, follow these steps:

#### Clone the Repository:

  ```sh
git clone https://github.com/your_username/your_repository.git
  ```
#### Install Dependencies:

  ```sh
cd instagram-analytics/public
npm install
  ```
#### Run the Application:

  ```sh
npm start
  ```

#### Access the Application:
Once the development server starts, access the application via the provided URL.