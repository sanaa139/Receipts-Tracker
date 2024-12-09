let currentTransactionId = -1;

document.addEventListener("DOMContentLoaded", function(){
    const dialog = document.getElementById('dialogAddProducts');
    addProducts();
    paginationLogic();

    var arrProducts = [];

    const saveButton = document.getElementById('saveButton');
    saveButton.addEventListener('click', function () {
        const productsTable = document.getElementById('addProductsTable').getElementsByTagName('tbody')[0];
        const rows = productsTable.rows;

        for (let row of rows) {
            const cells = row.cells;

            const obj = {
                id: -1,
                name: cells[0].textContent,
                price: cells[1].textContent,
                quantity: cells[2].textContent,
                fullPrice: cells[4].textContent,
                transactionId: -1,
                categoryName: cells[3].textContent
            }
            arrProducts.push(obj);
        }

        fetch('http://localhost:8080/api/transactions/bill/' + currentTransactionId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(arrProducts)
        });
        arrProducts = [];
    });

    const closeButton = dialog.getElementsByClassName('closeDialogContainer')[0];
    closeButton.addEventListener('click', function(){
        dialog.close();
    });

    const deleteBillButton = document.getElementById('deleteBillButton');
    deleteBillButton.addEventListener('click', function (){
        fetch('http://localhost:8080/api/transactions/bill/' + currentTransactionId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });
    });
});

function updateProductsTable(transactionID){
    const productsTable = document.getElementById('addProductsTable').getElementsByTagName('tbody')[0];
    fetch('http://localhost:8080/api/transactions/' + transactionID, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })  .then(response => response.json())
        .then(json => {
            $("#addProductsTable tbody tr").remove();

            for(let product of json){
                const newRow = productsTable.insertRow()
                newRow.insertCell(0).textContent = product.name;
                newRow.insertCell(1).textContent = product.price;
                newRow.insertCell(2).textContent = product.quantity;
                newRow.insertCell(3).textContent = product.categoryName;
                newRow.insertCell(4).textContent = product.fullPrice;
                const removeRow = document.createElement('button');
                removeRow.textContent = 'X';
                newRow.insertCell(5).appendChild(removeRow);

                removeRow.addEventListener('click', function (){
                    const row = this.parentElement.parentElement;
                    row.remove();
                });
            }
        });
}

function makeTransactionTableGroupedByRecipients(transactions){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Recipient";
    th2.textContent = "Price";
    for (let t of transactions) {
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = t.recipient;
        newRow.insertCell(1).textContent = t.price;
        if(parseFloat(t.price) > 0){
            newRow.cells[1].style.backgroundColor = "#CCFF99";
        }else if(parseFloat(t.price) < 0){
            newRow.cells[1].style.backgroundColor = "#FF9999";
        }
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}

function makeTransactionTableGroupedByDate(transactions){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Date";
    th2.textContent = "Price";
    for (let t of transactions) {
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = t.date;
        newRow.insertCell(1).textContent = t.price;
        if(parseFloat(t.price) > 0){
            newRow.cells[1].style.backgroundColor = "#CCFF99";
        }else if(parseFloat(t.price) < 0){
            newRow.cells[1].style.backgroundColor = "#FF9999";
        }
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}

function makeTransactionTableWithoutGrouping(transactions){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const th3 = document.createElement('th');
    const th4 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Date";
    th2.textContent = "Recipient";
    th3.textContent = "Price";
    th4.textContent = "Has Bill";

    for (let t of transactions) {
        console.log(t);
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = t.date;
        newRow.insertCell(1).textContent = t.recipient;
        newRow.insertCell(2).textContent = t.price;
        if(parseFloat(t.price) > 0){
            newRow.cells[2].style.backgroundColor = "#CCFF99";
        }else if(parseFloat(t.price) < 0){
            newRow.cells[2].style.backgroundColor = "#FF9999";
        }

        if(t.price < 0){
            newRow.insertCell(3).textContent = t.hasBill;

            const addBillButton = document.createElement('button');
            addBillButton.innerText = "+";
            newRow.insertCell(4).appendChild(addBillButton);

            addBillButton.addEventListener('click', function(){
                currentTransactionId = t.id;
                const dialog = document.getElementById('dialogAddProducts');
                dialog.showModal();
                updateProductsTable(t.id);
            });
        }else if(t.price > 0){
            newRow.insertCell(3).textContent = "";
        }
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    thead.appendChild(th3);
    thead.appendChild(th4);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}
