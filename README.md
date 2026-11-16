# Medicijn Maatje

A simple medication management application built with **Java 25 and Spring Boot 4**.

Medicijn Maatje allows users to keep track of their medications. It provides a REST API for managing medication records and a simple web interface so that users can interact with the application without needing technical knowledge or additional API tools.

## Features

- **Add medications:** Store a medication's name, dosage and frequency.
- **Prevent duplicates:** Medication names are normalized to prevent duplicate entries, regardless of capitalization or surrounding whitespace.
- **Medication overview:** View all registered medications through a paginated and sortable overview.
- **Update medications:** Modify existing medication information.
- **Delete medications:** Remove medications that are no longer needed.
- **Input validation:** Validate medication information and return clear error messages for invalid input.
- **Persistent storage:** Store medication data locally using an embedded H2 database.
- **Web interface:** Manage medications through a simple browser-based interface.

## Requirements

To run the prebuilt application, you need:

- Windows 10 or newer.
- Java 25 or newer.

You do not need to install Maven, IntelliJ IDEA, a database server or any API testing software.

**Download Java 25:**  
https://adoptium.net/temurin/releases/

Select JDK 25 for Windows and complete the installation. Open a new terminal and verify the installation:

```powershell
java -version
```

The output should indicate Java version 25 or newer.

## Installation

1. Open the [GitHub Releases](../../releases) page.
2. Download the latest `Medicijn Maatje` release.
3. Extract the downloaded ZIP file.
4. Make sure the extracted folder contains both `MedicijnMaatje.jar` and `start.bat`.
5. Double-click `start.bat`.

The startup script checks your Java installation and starts the application.

Once the application has started, open the following address in your browser:

**http://localhost:8080**

Keep the console window open while using the application. Closing it stops the application.

## Using the application

### Adding a medication

1. Find the medication form at the top of the page.
2. Enter the medication name.
3. Enter the dosage, for example `500 mg`.
4. Enter the frequency, for example `2 per day`.
5. Click **Add medication**.

The medication will appear in the overview below.

### Editing a medication

Click the **Edit** button next to a medication. Its information will be loaded into the form. Change the values and click **Save changes**.

### Deleting a medication

Click the **Delete** button next to a medication. Confirm the deletion when prompted.

### Viewing medications

The medication overview supports pagination and sorting.

- Select how many medications should be displayed per page.
- Select a field to sort by.
- Choose ascending or descending order.
- Use the Previous and Next buttons to navigate between pages.

### Testing validation

You can try entering invalid information to see how the application handles it.

For example:

- Leave the medication name empty.
- Enter a negative dosage.
- Enter an unsupported dosage format.
- Try adding the same medication twice.

The application should display an appropriate error message instead of accepting invalid data.

## Stopping the application

Return to the console window running MedicijnMaatje and press `CTRL+C` and confirm by typing `y` and pressing `ENTER`.

You can then close the window.

## Data storage

MedicijnMaatje uses an embedded H2 database, so there is no need to install or configure a separate database server.

Medication records are stored locally on the computer and persist when the application is stopped or restarted.

The database is managed by Flyway, which handles database schema creation and migrations.

**Important:** Do not delete the database files if you want to keep your medication records.

## Technical overview

The application is built using a layered architecture:

| Layer | Responsibility |
|---|---|
| Controllers | Expose REST API endpoints and handle HTTP requests. |
| Services | Handle medication-related business logic. |
| Repositories | Communicate with the database through Spring Data JPA. |
| Entities | Represent medication records in the database. |
| DTOs | Define the data exchanged through the API. |
| Exceptions | Handle errors and provide consistent API responses. |

### Technologies

- Java 25
- Spring Boot 4.1.2
- Spring Data JPA
- Hibernate
- H2 Database
- Flyway
- Maven
- HTML, CSS and JavaScript

### API endpoints

The application also exposes a REST API under `/api/medications`.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/medications` | Retrieve medications with pagination and sorting. |
| GET | `/api/medications/{id}` | Retrieve a medication by ID. |
| POST | `/api/medications` | Create a medication. |
| PUT | `/api/medications/{id}` | Update a medication. |
| DELETE | `/api/medications/{id}` | Delete a medication. |

The medication listing endpoint supports query parameters such as `page`, `size` and `sort`.

Example:

```http
GET /api/medications?page=0&size=20&sort=name,asc
```

## Building from source

If you want to build the application yourself:

1. Install JDK 25.
2. Clone this repository.
3. Open a terminal in the project directory.
4. Run:

```bash
mvnw.cmd clean package
```

The executable JAR will be generated in the `target` directory.

You can start it with:

```bash
java -jar target/medicijnmaatje-0.0.1-SNAPSHOT.jar
```
