const API = "http://localhost:8080";


// ================== 👤 AUTH ==================

// GET USER FROM LOCAL STORAGE
function getUser() {
    return JSON.parse(localStorage.getItem("user"));
}

// REGISTER
function register() {
    const name = document.getElementById("rname").value.trim();
    const email = document.getElementById("remail").value.trim();
    const password = document.getElementById("rpassword").value.trim();

    if (!name || !email || !password) {
        alert("All fields are required!");
        return;
    }

    fetch(`${API}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name, email, password })
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("User already exists");
        }
        return res.json();
    })
    .then(() => {
        alert("Registration successful ✅");
        window.location.href = "index.html";
    })
    .catch(err => {
        console.error(err);
        alert("Registration failed ❌ (User may already exist)");
    });
}

// LOGIN
function login() {
    const email = document.getElementById("lemail").value.trim();
    const password = document.getElementById("lpassword").value.trim();

    if (!email || !password) {
        alert("Email & Password required!");
        return;
    }

    fetch(`${API}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Invalid credentials");
        }
        return res.json();
    })
    .then(data => {
        localStorage.setItem("user", JSON.stringify(data));
        alert("Login successful ✅");
        window.location.href = "home.html";
    })
    .catch(err => {
        console.error(err);
        alert("Invalid email or password ❌");
    });
}

// LOGOUT
function logout() {
    localStorage.removeItem("user");
    window.location.href = "index.html";
}


// ================== 📋 TODO ==================

// ADD TASK
function addTask() {
    const task = document.getElementById("task").value.trim();
    const description = document.getElementById("description").value.trim();

    if (!task) {
        alert("Task is required!");
        return;
    }

    const user = getUser();

    const todo = {
        task,
        description,
        status: 0,
        user: { id: user.id }
    };

    fetch(`${API}/todos`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(todo)
    })
    .then(() => {
        alert("Task Added ✅");
        document.getElementById("task").value = "";
        document.getElementById("description").value = "";
        getAll();
    });
}

// GET ALL TASKS
function getAll() {
    const user = getUser();

    fetch(`${API}/todos/${user.id}`)
        .then(res => res.json())
        .then(showTasks);
}

// GET BY ID
function getById() {
    const id = document.getElementById("searchId").value;

    if (!id) {
        alert("Please enter ID");
        return;
    }

    fetch(`${API}/todos/task/${id}`)
        .then(res => {
            if (!res.ok) {
                throw new Error("Task not found");
            }
            return res.json();
        })
        .then(data => {
            showTasks([data]);
        })
        .catch(err => {
            document.getElementById("taskList").innerHTML = "";
            alert("Task doesn't exist ❌");
            console.error(err);
        });
}

// SHOW TASKS
function showTasks(tasks) {
    const list = document.getElementById("taskList");

    if (!list) return; // 🔥 prevents error on other pages

    list.innerHTML = "";

    tasks.forEach(t => {
        const li = document.createElement("li");

        li.innerHTML = `
            <b>ID: ${t.id}</b><br>
            <b>${t.task}</b><br>
            ${t.description || ""}<br>
            Status: ${t.status}%<br>
            <button onclick="updateStatus(${t.id})">Update</button>
            <button onclick="deleteTask(${t.id})">Delete</button>
        `;

        list.appendChild(li);
    });
}

// UPDATE STATUS
function updateStatus(id) {
    const status = prompt("Enter status (0-99)");

    if (status === null) return;

    const value = parseInt(status);

    if (isNaN(value) || value < 0 || value >= 100) {
        alert("Invalid status!");
        return;
    }

    fetch(`${API}/todos/${id}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ status: value })
    })
    .then(res => res.json())
    .then(() => {
        alert("Status Updated ✅");
        getAll();
    })
    .catch(err => {
        console.error(err);
        alert("Update failed ❌");
    });
}

// DELETE TASK
function deleteTask(id) {
    fetch(`${API}/todos/${id}`, {
        method: "DELETE"
    })
    .then(() => {
        alert("Deleted ✅");
        getAll();
    });
}


// ================== 📜 HISTORY ==================

// ================== 📜 HISTORY ==================

function getHistory() {
    const user = JSON.parse(localStorage.getItem("user"));

    // 🔥 Safety check
    if (!user || !user.id) {
        alert("Please login first!");
        window.location.href = "index.html";
        return;
    }

    console.log("Logged-in User ID:", user.id); // DEBUG

    fetch(`http://localhost:8080/history/${user.id}`)
        .then(res => {
            if (!res.ok) {
                throw new Error("Failed to fetch history");
            }
            return res.json();
        })
        .then(data => {
            console.log("History Data:", data); // DEBUG
            showHistory(data);
        })
        .catch(err => {
            console.error(err);
            alert("Error loading history");
        });
}

function showHistory(data) {
    const list = document.getElementById("historyList");

    if (!list) return;

    list.innerHTML = "";

    if (!data || data.length === 0) {
        list.innerHTML = "<p>No history found</p>";
        return;
    }

    data.forEach(h => {
        const li = document.createElement("li");

        // 🔥 Format date & time
        const date = new Date(h.time);
        const formattedTime = date.toLocaleString();

        li.innerHTML = `
            <b>${h.action}</b> - ${h.taskName} <br>
            <small>${formattedTime}</small>
        `;

        list.appendChild(li);
    });
}


// ================== 👤 PROFILE ==================

if (window.location.pathname.includes("profile")) {
    const user = getUser();

    if (user) {
        document.getElementById("pname").innerText = user.name;
        document.getElementById("pemail").innerText = user.email;
        document.getElementById("pdate").innerText = user.createdDate;
    }
}