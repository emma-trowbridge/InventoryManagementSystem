document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("addItemForm");
    const tableBody = document.querySelector("#inventoryTable tbody");

    // Load items initially
    fetchItems();

    // Submit form to add item
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const data = {
            name: form.name.value,
            description: form.description.value,
            quantity: parseInt(form.quantity.value),
            price: parseFloat(form.price.value)
        };

        fetch("addItem", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(() => {
            form.reset();
            fetchItems();
        });
    });

    function fetchItems() {
        fetch("listItems")
            .then(res => res.json())
            .then(items => {
                tableBody.innerHTML = "";
                items.forEach(item => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.description || ""}</td>
                        <td>${item.quantity}</td>
                        <td>$${item.price.toFixed(2)}</td>
                    `;
                    tableBody.appendChild(row);
                });
            });
    }
});
