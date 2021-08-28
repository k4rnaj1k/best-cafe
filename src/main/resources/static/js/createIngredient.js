formElem.onsubmit = async (e) => {
    e.preventDefault();
    data = {
        name: document.getElementById("nameInput").value
    }
    let responce = await fetch('api/v1/ingredients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    if (responce.ok) {
        responce.json().then(json => {
            let tablerow = document.createElement('tr');
            tablerow.setAttribute('id', 'row-'+json.id);
            let ingredientId = document.createElement('td');
            let ingredientName = document.createElement('td');
            let removeIngredientBtn = document.createElement('button');

            removeIngredientBtn.setAttribute('onclick', 'javascript:deleteIngredient(' + json.id + ');');
            removeIngredientBtn.innerText = 'X';
            removeIngredientBtn = document.createElement('td').appendChild(removeIngredientBtn);
            ingredientId.innerText = json.id;
            ingredientName.innerText = json.name;
            tablerow.appendChild(ingredientId);
            tablerow.appendChild(ingredientName);
            tablerow.appendChild(removeIngredientBtn);

            let existing = document.getElementById("existing");
            if (existing == null) {
                existing = document.createElement("table");
                existing.setAttribute("id", "existing");
                document.body.appendChild(existing);
            }
            existing.appendChild(tablerow);
        })
    }
    document.getElementById("nameInput").value = "";
};

async function deleteIngredient(ingredientId) {
    let responce = await fetch('api/v1/ingredients/' + ingredientId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    if (responce.ok) {
        let tableRow = document.getElementById("row-" + ingredientId);
        tableRow.remove();
    }
}
