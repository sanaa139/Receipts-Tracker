document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/api/settings', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })  .then(response => response.json())
        .then(json => {
            console.log(json);
            addDataToArray(json);
        });
    createTransaction();
});

function createTransaction(){
    const addTransaction = document.getElementById("openDialogAddTransactionButton");
    addTransaction.addEventListener('click', function (){
        const dialog = document.getElementById('dialogAddTransaction');
        dialog.showModal();

        const closeDialogButton = document.getElementById('closeDialogAddTransactionButton');
        closeDialogButton.addEventListener('click', function (){
            dialog.close();
        });
    });
}

function addDataToArray(categories) {
    const table = document.getElementById('categoryTable').getElementsByTagName('tbody')[0];

    for(let c of categories){
        const newRow = table.insertRow();
        newRow.insertCell(0).textContent = c.name;
        newRow.insertCell(1).textContent = c.id;
        newRow.cells[1].style.display = 'none';

        const deleteButton = document.createElement('button');
        deleteButton.textContent = "X";
        deleteButton.className = "deleteButton";
        newRow.insertCell(2).appendChild(deleteButton);

        deleteButton.addEventListener('click', function(){
            const id = newRow.cells[1].innerText;
            console.log("id: " + id);
            fetch('http://localhost:8080/api/categories/' + id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
        });
    }
}