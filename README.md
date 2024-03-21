# VocaMemo

VocaMemo is a web application designed to help users memorize and review vocabulary words. It provides features for searching words, viewing word details, highlighting words, and reviewing words.

## Features

- Search for words and retrieve their definitions and examples
- View detailed information about a word, including its meaning, part of speech, and phonetic pronunciation
- Highlight important words for easy reference
- Review highlighted words to reinforce memory retention
- User-friendly interface for seamless navigation and interaction

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- HTML/CSS
- JavaScript
- jQuery
- Bootstrap
- MySQL (or any other supported database)

## Getting Started

To run the VocaMemo application locally, follow these steps:

1. Clone the repository:
   ```
   git clone https://github.com/your-username/vocamemo.git
   ```

2. Configure the database connection in the `application.properties` file:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/vocamemo
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. Build and run the application using Maven:
   ```
   mvn spring-boot:run
   ```

4. Access the application in your web browser at `http://localhost:8080`.

## Project Structure

The project follows a standard Spring Boot project structure:

- `src/main/java`: Contains the Java source code
  - `config`: Configuration classes
  - `controllers`: Controller classes for handling HTTP requests
  - `entities`: Entity classes representing database tables
  - `repositories`: Repository interfaces for data access
  - `services`: Service classes for business logic
- `src/main/resources`: Contains the application resources
  - `static`: Static files (CSS, JavaScript, images)
  - `templates`: Thymeleaf templates for rendering views
- `src/test`: Contains the test classes

## Contributing

Contributions to the VocaMemo project are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## Contact

If you have any questions or inquiries, please contact duy.pk.personal@gmail.com
