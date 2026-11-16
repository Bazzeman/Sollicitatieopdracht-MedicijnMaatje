
# MedicijnMaatje - MoSCoW Requirements

| Priority | Requirement | Description |
| - | - | - |
| Must | Add medication | Add a medication with a name, dosage and usage frequency. |
| Must | View medications | Retrieve an overview of all stored medications. |
| Must | Update medication | Modify the details of an existing medication. |
| Must | Delete medication | Remove an existing medication. |
| Must | Prevent duplicates | Medication names must be unique, regardless of capitalization or surrounding whitespace. |
| Must | Input validation | Required fields must be present. Dosage and usage must follow accepted formats. Invalid input must return a clear error message. |
| Must | Persistent storage | Medication records must remain available after restarting the application. |
| Must | Local setup | The application and its database must run locally without requiring a separately installed database server. |
| Must | Documentation | Provide instructions explaining how to start and test the application. |
| Should | Retrieve medication by ID | Retrieve an individual medication using its unique identifier. |
| Should | Consistent API responses | Return appropriate HTTP status codes and clear error messages. |
| Should | Automated tests | Test the main business logic, validation and API functionality. |
| Could | Next intake information | Calculate or display when the next medication intake is due, provided the schedule contains sufficient information. |
| Could | Pagination and sorting | Support pagination and sorting if the medication list grows. |
| Won't (this version) | Multiple user accounts | The application is intended for one user only. |
| Won't (this version) | Authentication and authorization | No login or access-control system is required for this version. |
| Won't (this version) | Shared medication catalogue | Medication records are stored directly, without a separate global medication catalogue. |
| Won't (this version) | Frontend application | Development focuses exclusively on the backend API. |
| Won't (this version) | Production healthcare security | This prototype is not intended for production use or real patient data. |
