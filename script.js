let currentUser = "";

// LOGIN
function handleLogin() {
    const u = document.getElementById('user').value;
    const p = document.getElementById('pass').value;

    if (u && p) {
        currentUser = u;
        document.getElementById('loginSection').style.display = 'none';
        document.getElementById('trackerSection').style.display = 'block';
        updateUI();
    } else {
        alert("Please enter login details");
    }
}

// LOGOUT
function goBack() {
    document.getElementById('trackerSection').style.display = 'none';
    document.getElementById('loginSection').style.display = 'block';
    document.getElementById('user').value = "";
    document.getElementById('pass').value = "";
    currentUser = "";
}

// FORM SUBMIT
const form = document.getElementById('transactionForm');

form.addEventListener('submit', (e) => {

    e.preventDefault();

    const desc = document.getElementById('desc').value;
    const amt = parseFloat(document.getElementById('amount').value);
    const type = document.getElementById('type').value;
    const date = document.getElementById('date').value;

    const entry = { desc, amt, type, date };

    let storageKey = 'trackerData_' + currentUser;

    let data = JSON.parse(localStorage.getItem(storageKey)) || [];

    data.push(entry);

    localStorage.setItem(storageKey, JSON.stringify(data));

    form.reset();

    updateUI();
});


// DELETE FUNCTION
function deleteEntry(index) {

    let storageKey = 'trackerData_' + currentUser;

    let data = JSON.parse(localStorage.getItem(storageKey)) || [];

    data.splice(index, 1);

    localStorage.setItem(storageKey, JSON.stringify(data));

    updateUI();
}


// UPDATE UI
function updateUI() {

    let storageKey = 'trackerData_' + currentUser;

    const data = JSON.parse(localStorage.getItem(storageKey)) || [];

    const tbody = document.getElementById('historyBody');

    let inc = 0;
    let exp = 0;

    tbody.innerHTML = "";

    data.forEach((item, index) => {

        if (item.type === 'Income') inc += item.amt;
        else exp += item.amt;

        tbody.innerHTML += `
        <tr>
        <td>${item.desc}</td>

        <td style="color:${item.type === 'Income' ? 'green' : 'red'}">
        ${item.type}
        </td>

        <td>₹${item.amt.toFixed(2)}</td>

        <td>${item.date}</td>

        <td>
        <button onclick="deleteEntry(${index})" 
        style="background:#e74c3c;padding:6px 10px;font-size:12px;">
        Delete
        </button>
        </td>

        </tr>
        `;
    });

    document.getElementById('dashIncome').innerText = `₹${inc.toFixed(2)}`;
    document.getElementById('dashExpense').innerText = `₹${exp.toFixed(2)}`;
    document.getElementById('dashBalance').innerText = `₹${(inc - exp).toFixed(2)}`;
}