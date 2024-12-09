const pageSize = 24;

document.addEventListener("DOMContentLoaded", function() {
    paginationLogic();
});

function groupByNameLogic(){
    displayProducts(0);
}

function groupByCategoryLogic(){
    displayProducts(0);
}

function buttonLogic(page){
    displayProducts(page.textContent-1);
}

function getUrlOfProducts(page_index, groupedByInformation){
    if (groupedByInformation === "name"){
        return 'http://localhost:8080/api/products?groupBy=name&page='+page_index+'&size=' + pageSize;
    } else if (groupedByInformation === "category") {
        return 'http://localhost:8080/api/products?groupBy=category&page='+page_index+'&size=' + pageSize;
    }
    return 'http://localhost:8080/api/products?page='+page_index+'&size=' + pageSize;
}

function makeProductsTable(products, groupedByInformation){
    if(document.getElementById("table") != null){
        document.getElementById("table").remove();
    }
    const tableContainer = document.getElementById("tableContainer");
    let createdTable;
    if (groupedByInformation === "name"){
        createdTable = makeProductsTableGroupedByName(products);
    } else if (groupedByInformation === "category"){
        createdTable = makeProductsTableGroupedByCategory(products);
    } else {
        createdTable = makeProductsTableWithoutGrouping(products);
    }
    createdTable.id = 'table';
    tableContainer.appendChild(createdTable);
}

function displayProducts(page_index) {
    let groupedByInformation;
    if (document.getElementById('option1').checked){
        groupedByInformation = "name";
    } else if (document.getElementById('option2').checked){
        groupedByInformation = "category";
    } else {
        groupedByInformation = "none";
    }
    console.log("GroupedByInformation=", groupedByInformation);

    let url = getUrlOfProducts(page_index, groupedByInformation);
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(json => {
            console.log("Just printing out json! ", json);
            return json;
        })
        .then(json => {
            if(json.content.length > 0) {
                makeProductsTable(json.content, groupedByInformation);
            }
            return json;
        }).then(json => {
            if(json.content.length > 0) {
                addPaginationButtons(json.page.number, json.page.totalPages);
            }
        });
}

function paginationLogic(){
    const checkboxGroupByRecipient = document.getElementById('option1');
    const checkboxGroupByDate = document.getElementById('option2');

    checkboxGroupByRecipient.checked = false;
    checkboxGroupByDate.checked = false;

    checkboxGroupByRecipient.addEventListener('change', groupByNameLogic);
    checkboxGroupByDate.addEventListener('change', groupByCategoryLogic);

    displayProducts(0);
}

function addPaginationButtons(what_page_am_i_on, how_many_pages_are_there){
    const pagination = document.getElementById("pagination");

    pagination.innerHTML = "";
    for(let pagination_element of paginate(what_page_am_i_on+1, how_many_pages_are_there).items){
        let paging_button = document.createElement('a');
        paging_button.id = "pagingButton";
        if (pagination_element !== '...') {
            paging_button.addEventListener('click', function () {
                buttonLogic(paging_button);
            });
        }
        if(pagination_element === what_page_am_i_on+1){
            paging_button.className='active';
        }
        paging_button.textContent = pagination_element.toString();
        pagination.appendChild(paging_button);
    }
}

function makeProductsTableGroupedByName(products){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const th3 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Name";
    th2.textContent = "Category";
    th3.textContent = "Price";
    for (let p of products) {
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = p.name;
        newRow.insertCell(1).textContent = p.categoryName;
        newRow.insertCell(2).textContent = p.fullPrice;
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    thead.appendChild(th3);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}
function makeProductsTableGroupedByCategory(products){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Category";
    th2.textContent = "Price";
    for (let p of products) {
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = p.categoryName;
        newRow.insertCell(1).textContent = p.fullPrice;
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}

function makeProductsTableWithoutGrouping(products){
    const tbl = document.createElement('table');
    const thead = document.createElement('thead');
    const th1 = document.createElement('th');
    const th2 = document.createElement('th');
    const th3 = document.createElement('th');
    const th4 = document.createElement('th');
    const th5 = document.createElement('th');
    const tbody = document.createElement('tbody');

    th1.textContent = "Name";
    th2.textContent = "Price";
    th3.textContent = "Quantity";
    th4.textContent = "Price";
    th5.textContent = "Category";
    for (let p of products) {
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = p.name;
        newRow.insertCell(1).textContent = p.price;
        newRow.insertCell(2).textContent = p.quantity;
        newRow.insertCell(3).textContent = p.fullPrice;
        newRow.insertCell(4).textContent = p.categoryName;
    }

    thead.appendChild(th1);
    thead.appendChild(th2);
    thead.appendChild(th3);
    thead.appendChild(th4);
    thead.appendChild(th5);
    tbl.appendChild(thead);
    tbl.appendChild(tbody);
    return tbl;
}

function paginate(current, max) {
    if (!current || !max) return null

    let prev = current === 1 ? null : current - 1,
        next = current === max ? null : current + 1,
        items = []
    items.push(1)

    if (current === 1 && max === 1) return {current, prev, next, items}
    if (current > 4) items.push('...')

    let r = 2, r1 = current - r, r2 = current + r

    for (let i = r1 > 2 ? r1 : 2; i <= Math.min(max, r2); i++) items.push(i)

    if (r2 + 1 < max) items.push('...')
    if (r2 < max) items.push(max)

    return {current, prev, next, items}
}