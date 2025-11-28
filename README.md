# PDF Summarizer

A Spring Boot application that summarizes PDF files using the Google Gemini LLM API.

---

## Features

- Upload a PDF and get a concise summary (3 bullet points)
- Uses Google Gemini API for summarization
- Clean, type-safe DTO-based code

---

## Requirements

- Java 17+
- Maven
- Google Gemini API key

---

## Setup

1. **Clone the repository**
   ```sh
   git clone <your-repo-url>
   cd pdf_summarizer
   ```

2. **Configure your API key**

   Add your Gemini API key to `src/main/resources/application.properties`:
   ```
   gemini.api.key=YOUR_GEMINI_API_KEY
   ```

3. **Build and run**
   ```sh
   mvn spring-boot:run
   ```

---

## API Usage

- **Endpoint:** `POST /api/pdf/summarize`
- **Form field:** `file` (PDF file)

**Example using `curl`:**
```sh
curl -F "file=@yourfile.pdf" http://localhost:8080/api/pdf/summarize
```

---

## Project Structure

- `controller/` — REST API endpoints
- `service/` — Business logic and Gemini API integration
- `dto/` — Data Transfer Objects for request/response mapping
- `target` - folder holds the old compiled files from the last time you ran the app. If you don't       delete them, sometimes old code gets mixed with new code, causing weird bugs.
- `./mvnw clean install` -- Do- Everything command . It tries to install all dependencies from pom.xml and tries run all tests in src/test and zip the package in .jar file and save it to my local library (install).
- `install`: Creates the JAR AND copies it to your central ~/.m2 library. This makes your code reusable by other projects on your computer. It's the standard "success" check for a build.

---

## How DTOs and JSON Conversion Work

- **DTO classes** (like `GeminiRequest` and `GeminiResponse`) are plain Java classes that define the structure of the data sent to and received from the Gemini API.
- You only work with Java objects and lists in your code.
- **Spring’s `RestTemplate` (with Jackson) automatically:**
  - **Serializes** your DTO (e.g., `GeminiRequest`) into JSON when sending a request.
  - **Deserializes** the JSON response from the API into your DTO (e.g., `GeminiResponse`).
- You do **not** need to manually convert between Java objects and JSON—Spring/Jackson handles this for you.

---

## How it works

1. User uploads a PDF via `/api/pdf/summarize`.
2. The app extracts text from the PDF.
3. The text is wrapped in a `GeminiRequest` DTO, which builds a nested Java object structure matching the Gemini API's expected JSON.
4. `RestTemplate` serializes this DTO into JSON and sends it to the Gemini API.
5. The summarized result is received as JSON, which is automatically deserialized into a `GeminiResponse` DTO and returned as a response.



## SecurityConfig.java:
1. This is the “brain” of Spring Security controls the entire security behavior of your application.
2. It tells Spring which URLs need JWT, which are public, what filters to run, and how to authenticate users.

##
- @RequestParam only receives form-data
- @RequestBody can get JSON data
---

## License

1. Spring Data JPA
Easily connect to databases and perform CRUD operations using repositories.
2. Spring Security
Add authentication and authorization to your application.
3. Validation
Use annotations (like @Valid, @NotNull) to validate incoming data in DTOs.
4. Exception Handling
Use @ControllerAdvice and @ExceptionHandler for global error handling.
5. Configuration & Profiles
Manage different environments (dev, prod) using application.properties and profiles.
6. Spring Boot Actuator
Monitor and manage your application with built-in endpoints (health, metrics, etc.).
7. Testing
Write unit and integration tests using Spring Boot’s testing support (@SpringBootTest, @WebMvcTest).
8. Scheduling
Run scheduled tasks using @Scheduled.
9. Caching
Improve performance with caching using @EnableCaching and @Cacheable.
10. Async Processing
Run tasks asynchronously using @Async.
Tip:
Pick one feature at a time, try a small example, and gradually integrate it into your projects!

MIT