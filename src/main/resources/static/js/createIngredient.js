formElem.onsubmit = async (e) => {
    e.preventDefault();
    data = {
        name: document.getElementById("nameInput").value
    }
    await fetch('api/v1/ingredients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    }).then(data => {
        data.json().then(json => {
            let tablerow = document.createElement('tr');
            let ingredientId = document.createElement('td');
            let ingredientName = document.createElement('td');
            ingredientId.innerText = json.id;
            ingredientName.innerText = json.name;
            tablerow.appendChild(ingredientId);
            tablerow.appendChild(ingredientName);
            let existing = document.getElementById("existing");
            if (existing == null) {
                existing = document.createElement("table");
                existing.setAttribute("id", "existing");
                document.body.appendChild(existing);
            }
            existing.appendChild(tablerow);
        })
    });
    document.getElementById("nameInput").value="";

}