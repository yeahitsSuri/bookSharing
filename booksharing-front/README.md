
# Book Sharing API

This project is a Spring Boot application designed for sharing books among users. It provides functionalities for managing books, feedback, and user roles, and it includes a RESTful API for interacting with the system.

## Features

- **Book Management**: Add, update, and retrieve books.
- **Feedback System**: Users can provide feedback on books.
- **User Roles**: Manage user roles and permissions.
- **Security**: Implemented with Spring Security.
- **Pagination**: Supports paginated responses for book and feedback listings.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database interactions.
- **Spring Security**: For securing the application.
- **PostgreSQL**: Database for storing data.
- **Lombok**: To reduce boilerplate code.
- **OpenAPI**: For API documentation.

## Getting Started

### Prerequisites

- Java 17
- Maven
- PostgreSQL

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/booksharing.git
   cd booksharing
   ```

2. **Configure the database**:
   Update the `application.properties` file with your PostgreSQL database credentials.

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### API Endpoints

- **Books**:
  - `GET /books`: Retrieve all books.
  - `GET /books/{book-id}`: Retrieve a book by ID.
  - `POST /books`: Add a new book.
  - `PATCH /books/shareable/{book_id}`: Update shareable status.

- **Feedback**:
  - `GET /feedbacks/boook/{book_id}`: Retrieve feedback for a book.
  - `POST /feedbacks`: Add feedback for a book.

### Code Structure

- **Book**: 
  - Entity: `Book.java` 
  - Service: `BookService.java`
  - Controller: `BookController.java`
  - Repository: `BookRepo.java`
  - Mapper: `BookMapper.java`

- **Feedback**:
  - Entity: `Feedback.java`
  - Service: `FeedbackService.java`
  - Controller: `FeedbackController.java`
  - Repository: `FeedbackRepo.java`
  - Mapper: `FeedbackMapper.java`

### Error Handling

Global exception handling is implemented in `GlobalExceptionHandler.java` to manage application-wide errors.

### Testing

Tests are located in the `src/test/java/com/yeahitsSuri/booksharing` directory. Use the following command to run tests:
```bash
mvn test
```


## License

This project is licensed under the MIT License.

## Contact

For any inquiries, please contact [yts9614@gmail.com](mailto:yts9614@gmail.com).
