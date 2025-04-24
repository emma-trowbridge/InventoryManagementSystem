window.onload = function() {
    fetch("inventory")
        .then(response => response.json())
        .then(data => {
            document.getElementById("output").innerText = data.message;
        })
        .catch(error => {
            document.getElementById("output").innerText = "Error loading data.";
            console.error(error);
        });
};