function addProducts(){

    const productsTable = document.getElementById('addProductsTable').getElementsByTagName('tbody')[0];
    const form = document.getElementById('formCreateNewProduct');
    const select = document.getElementById('select');


    fetch('http://localhost:8080/api/categories', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(json => {
            for(let category of json){
                const option = document.createElement('option');
                option.value = category.name;
                option.textContent = category.name;
                select.appendChild(option);
            }
        });

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        if(select.value !== ''){
            const formData = new FormData(form);

            const newRow = productsTable.insertRow()
            newRow.insertCell(0).textContent = formData.get('name').toString();
            newRow.insertCell(1).textContent = formData.get('price').toString();
            newRow.insertCell(2).textContent = formData.get('quantity').toString();
            newRow.insertCell(3).textContent = select.value;
            newRow.insertCell(4).textContent = (Math.round((parseFloat(formData.get('price')) * parseFloat(formData.get('quantity'))) * 10) / 10).toString();
            const removeRow = document.createElement('button');
            removeRow.textContent = 'X';
            newRow.insertCell(5).appendChild(removeRow);

            removeRow.addEventListener('click', function (){
                const row = this.parentElement.parentElement;
                row.remove();
            });
            form.reset();
        }
    });
}