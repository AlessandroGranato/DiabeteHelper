function sendJSON(){ 

            let result = document.querySelector('.result'); 
            let glycemiaCurrentValue = document.querySelector('#glycemiaCurrentValue');
            let lastInsulinQuantity = document.querySelector('#lastInsulinQuantity');
            let lastInsulinDatetime = document.querySelector('#lastInsulinDatetime');
               
            // Creating a XHR object 
            let xhr = new XMLHttpRequest(); 
            let url = "http://localhost:8080/DiabeteHelperBE/api/v1/aliments/calculateInsulin";
        
            // open a connection 
            xhr.open("POST", url, true); 
  
            // Set the request header i.e. which type of content you are sending 
            xhr.setRequestHeader("Content-Type", "application/json"); 

            // Converting JSON data to string 
            //var values = JSON.stringify([{ "alimentName": "Prova1", "quantity": 123 }]);

            var alimentValues = getCellValues();
            console.log("alimentValues");
            console.log(alimentValues);

            var data = {};
            data.glycemiaCurrentValue = glycemiaCurrentValue.value;
            data.lastInsulinQuantity = lastInsulinQuantity.value;
            data.lastInsulinDatetime = lastInsulinDatetime.value;
            data.alimentValues = alimentValues;

            console.log("data");
            console.log(data);

            var jsonString = JSON.stringify(data);

            console.log("jsonString");
            console.log(jsonString);

            // Sending data with the request 
            xhr.send(jsonString);


            // Create a state change callback
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {

                    // Print received data from server
                    result.innerHTML = this.responseText;

                }
            };
}

function addRow() {
    var table = document.getElementById("alimentsTable")
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var element1 = document.createElement("input");
    element1.type = "text";
    cell1.appendChild(element1);
    var element2 = document.createElement("input");
    element2.type = "text";
    cell2.appendChild(element2);
}


function getCellValues() {
    var data = [];
    var table = document.getElementById('alimentsTable');
    for (var r = 0, n = table.rows.length; r < n; r++) {
        var name = table.rows[r].cells[0].children[0].value;
        var quantity = table.rows[r].cells[1].children[0].value;
     // data.push("{\"name\"": + "\"" + val1 + "\" , \"quantity\": " + val2 + "}");
        data.push({"alimentName": name, "quantity" : quantity});
    }
    var text = "[" + data.toString() + "]";
    console.log("getCellValues");
    console.log(data);
    return data;
}