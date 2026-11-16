
const API_URL = "/api/medications";

// Current pagination state
let currentPage = 0;

// HTML elements
const form = document.getElementById("medication-form");
const medicationList = document.getElementById("medication-list");

const medicationId = document.getElementById("medication-id");
const nameInput = document.getElementById("name");
const dosageInput = document.getElementById("dosage");
const frequencyInput = document.getElementById("frequency");

const formTitle = document.getElementById("form-title");
const submitButton = document.getElementById("submit-button");
const cancelButton = document.getElementById("cancel-button");
const message = document.getElementById("message");

const pageSize = document.getElementById("page-size");
const sortField = document.getElementById("sort-field");
const sortDirection = document.getElementById("sort-direction");

const previousPage = document.getElementById("previous-page");
const nextPage = document.getElementById("next-page");
const pageInformation = document.getElementById("page-information");


// Load medications from the backend
async function loadMedications() {
    const params = new URLSearchParams({
        page: currentPage,
        size: pageSize.value,
        sort: `${sortField.value},${sortDirection.value}`
    });

    try {
        const response = await fetch(`${API_URL}?${params}`);

        if (!response.ok) {
            throw new Error("Could not load medications.");
        }

        const data = await response.json();

        renderMedications(data);
    } catch (error) {
        showMessage(error.message);
    }
}


// Display medications and pagination information
function renderMedications(data) {
    medicationList.replaceChildren();

    data.content.forEach(medication => {
        const row = document.createElement("tr");

        addCell(row, medication.name);
        addCell(row, medication.dosage);
        addCell(row, medication.frequency);

        const actionsCell = document.createElement("td");

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.addEventListener("click", () => editMedication(medication));

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.addEventListener("click", () => deleteMedication(medication.id));

        actionsCell.append(editButton, deleteButton);
        row.appendChild(actionsCell);

        medicationList.appendChild(row);
    });

    if (data.content.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");

        cell.colSpan = 4;
        cell.textContent = "No medications found.";

        row.appendChild(cell);
        medicationList.appendChild(row);
    }

    pageInformation.textContent =
        `Page ${data.page + 1} of ${Math.max(data.totalPages, 1)} (${data.totalElements} medications)`;

    previousPage.disabled = data.first;
    nextPage.disabled = data.last;
}


// Create a table cell safely
function addCell(row, value) {
    const cell = document.createElement("td");
    cell.textContent = value;
    row.appendChild(cell);
}


// Create or update a medication
form.addEventListener("submit", async event => {
    event.preventDefault();

    const id = medicationId.value;

    const medication = {
        name: nameInput.value,
        dosage: dosageInput.value,
        frequency: frequencyInput.value
    };

    const isEditing = id !== "";

    const url = isEditing ? `${API_URL}/${id}` : API_URL;
    const method = isEditing ? "PUT" : "POST";

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(medication)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(formatError(error));
        }

        showMessage(
            isEditing
                ? "Medication updated successfully."
                : "Medication added successfully."
        );

        resetForm();
        await loadMedications();

    } catch (error) {
        showMessage(error.message);
    }
});


// Populate the form for editing
function editMedication(medication) {
    medicationId.value = medication.id;
    nameInput.value = medication.name;
    dosageInput.value = medication.dosage;
    frequencyInput.value = medication.frequency;

    formTitle.textContent = "Edit medication";
    submitButton.textContent = "Save changes";
    cancelButton.hidden = false;

    form.scrollIntoView({ behavior: "smooth" });
}


// Delete a medication
async function deleteMedication(id) {
    if (!confirm("Are you sure you want to delete this medication?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(formatError(error));
        }

        showMessage("Medication deleted successfully.");

        await loadMedications();

    } catch (error) {
        showMessage(error.message);
    }
}


// Reset the form
function resetForm() {
    form.reset();

    medicationId.value = "";
    formTitle.textContent = "Add medication";
    submitButton.textContent = "Add medication";
    cancelButton.hidden = true;
}


// Cancel editing
cancelButton.addEventListener("click", resetForm);


// Display a message
function showMessage(text) {
    message.textContent = text;
}


// Format errors returned by the backend
function formatError(error) {
    if (error.details && typeof error.details === "object") {
        return Object.values(error.details).join(" ");
    }

    return error.error || "Something went wrong.";
}


// Pagination controls
previousPage.addEventListener("click", () => {
    if (currentPage > 0) {
        currentPage--;
        loadMedications();
    }
});

nextPage.addEventListener("click", () => {
    currentPage++;
    loadMedications();
});


// Pagination and sorting options
pageSize.addEventListener("change", () => {
    currentPage = 0;
    loadMedications();
});

sortField.addEventListener("change", () => {
    currentPage = 0;
    loadMedications();
});

sortDirection.addEventListener("change", () => {
    currentPage = 0;
    loadMedications();
});


// Initial load
loadMedications();
