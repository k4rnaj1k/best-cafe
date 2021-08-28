box.onsubmit = async (e) => {
    e.preventDefault();

    data ={
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    }
    let responce = await fetch('api/v1/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    if(responce.ok){
    responce.json().then(json =>{
        window.sessionStorage.setItem('Authentication', 'Bearer_' + json.token)
        window.open('/home', '_self');
    })}
}