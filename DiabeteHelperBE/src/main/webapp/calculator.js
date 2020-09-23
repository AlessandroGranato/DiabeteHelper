function sendJSON(){ 

            let result = document.querySelector('.result'); 
            let name = document.querySelector('#name'); 
            let email = document.querySelector('#email'); 
               
            // Creating a XHR object 
            let xhr = new XMLHttpRequest(); 
            let url = "http://localhost:8080/DiabeteHelperBE/api/v1/aliments/calculateInsulin";
        
            // open a connection 
            xhr.open("POST", url, true); 
  
            // Set the request header i.e. which type of content you are sending 
            xhr.setRequestHeader("Content-Type", "application/json"); 

            // Converting JSON data to string 
            //var values = JSON.stringify([{ "alimentName": "Prova1", "quantity": 123 }]);

            var values = getCellValues();
            console.log(values);

            var data = JSON.stringify(values);

            // Sending data with the request 
            xhr.send(values);


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
    	var val1 = table.rows[r].cells[0].children[0].value;
      var val2 = table.rows[r].cells[1].children[0].value;
     // data.push("{\"name\"": + "\"" + val1 + "\" , \"quantity\": " + val2 + "}");
     data.push("{\"name\": " + "\"val1\"" + ", \"quantity\": " + val2 + "}");
    }
    var text = "[" + data.toString() + "]";
    console.log(data);
    return text;
}