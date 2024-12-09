const pageSize = 24;

function groupByRecipientLogic(){
    displayTransactions(0);
}

function groupByDateLogic(){
    displayTransactions(0);
}

function buttonLogic(page){
    displayTransactions(page.textContent-1);
}

function getUrlOfTransactions(page_index, groupedByInformation){
    if (groupedByInformation === "recipient"){
        return 'http://localhost:8080/api/transactions?groupBy=recipient&page='+page_index+'&size=' + pageSize;
    } else if (groupedByInformation === "date") {
        return 'http://localhost:8080/api/transactions?groupBy=date&page='+page_index+'&size=' + pageSize;
    }
    return 'http://localhost:8080/api/transactions?page='+page_index+'&size=' + pageSize;
}

function makeTransactionTable(transactions, groupedByInformation){
    if(document.getElementById("table") != null){
        document.getElementById("table").remove();
    }
    const tableContainer = document.getElementById("tableContainer");
    let createdTable;
    if (groupedByInformation === "recipient"){
        createdTable = makeTransactionTableGroupedByRecipients(transactions);
    } else if (groupedByInformation === "date"){
        createdTable = makeTransactionTableGroupedByDate(transactions);
    } else {
        createdTable = makeTransactionTableWithoutGrouping(transactions);
    }
    createdTable.id = 'table';
    tableContainer.appendChild(createdTable);
}

function displayTransactions(page_index) {
    let groupedByInformation;
    if (document.getElementById('option1').checked){
        groupedByInformation = "recipient";
    } else if (document.getElementById('option2').checked){
        groupedByInformation = "date";
    } else {
        groupedByInformation = "none";
    }
    console.log("GroupedByInformation=", groupedByInformation);

    let url = getUrlOfTransactions(page_index, groupedByInformation);
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
                makeTransactionTable(json.content, groupedByInformation);
            }
            return json;
        }).then(json => {
            if(json.page.totalPages > 0){
                addPaginationButtons(json.page.number, json.page.totalPages);
            }
        });
}

function paginationLogic(){
    const checkboxGroupByRecipient = document.getElementById('option1');
    const checkboxGroupByDate = document.getElementById('option2');

    checkboxGroupByRecipient.checked = false;
    checkboxGroupByDate.checked = false;

    checkboxGroupByRecipient.addEventListener('change', groupByRecipientLogic);
    checkboxGroupByDate.addEventListener('change', groupByDateLogic);

    displayTransactions(0);
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