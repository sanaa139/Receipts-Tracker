document.addEventListener("DOMContentLoaded", function(){
    addProducts();
    var arrProducts = []

    const productsTable = document.getElementById('addProductsTable').getElementsByTagName('tbody')[0];
    const dialog = document.getElementById("dialogConfirmTransaction");
    const submitDataButton = document.getElementById('submitDataButton');
    submitDataButton.addEventListener('click', function () {
        const rows = productsTable.rows;

        for (let i = 0; i < rows.length; i++) {
            const row = rows[i];

            const cells = row.cells;

            const obj = {
                name: cells[0].innerText,
                price: cells[1].innerText,
                quantity: cells[2].innerText,
                fullPrice: cells[4].innerText,
                transactionId: -1,
                categoryName: cells[3].textContent
            }

            arrProducts.push(obj);
        }

        fetch('http://localhost:8080/api/bill/transaction', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(arrProducts),
        })
            .then(response => response.json())
            .then(json => {
                console.log("Just printing out json! ", json);
                return json;
            })
            .then(json => {
                arrProducts = [];
                dialog.showModal();
                const table = document.getElementById("transactionTable").getElementsByTagName('tbody')[0];
                $("#transactionTable tbody tr").remove();
                for(let t of json){
                    const newRow = table.insertRow();

                    newRow.insertCell(0).textContent = t.date;
                    newRow.insertCell(1).textContent = t.recipient;
                    newRow.insertCell(2).textContent = t.price;

                    const input = document.createElement('input');
                    input.type = 'checkbox'; // Set the type of input
                    input.type = 'checkbox'; // Set the type of input
                    input.className = "confirmTransactionButton";
                    newRow.insertCell(3).appendChild(input);

                    input.addEventListener('change', function(){
                        var cbs = document.getElementsByClassName("confirmTransactionButton");
                        for (var i = 0; i < cbs.length; i++) {
                            cbs[i].checked = false;
                        }
                        input.checked = true;
                    });

                    let newCell = newRow.insertCell(4);
                    newCell.textContent = t.id;
                    newCell.style.display = 'none';
                }
            });
    });

    const closeButton = document.getElementById("closeDialog");
    closeButton.addEventListener('click', function (){
        dialog.close();
    });


    const saveTransactionChoiceButton = document.getElementById('saveTransactionChoice');
    saveTransactionChoiceButton.addEventListener('click', function () {
        const table = document.getElementById("transactionTable").getElementsByTagName('tbody')[0];
        let confirmedArrayProducts = [];
        for (let row of table.rows) {
            if (row.cells[3].querySelector('input[type="checkbox"]').checked) {
                for(let row of productsTable.rows){
                    product = {
                        name: row.cells[0].innerText,
                        price: row.cells[1].innerText,
                        quantity: row.cells[2].innerText,
                        fullPrice: row.cells[4].innerText,
                        transactionId: -1,
                        categoryName: row.cells[3].textContent
                    };
                    confirmedArrayProducts.push(product);
                }

                transactionId = row.cells[4].textContent;

                fetch('http://localhost:8080/api/bill/' + transactionId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(confirmedArrayProducts),
                });
                break;
            }
        }
    });
});
