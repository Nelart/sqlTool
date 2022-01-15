var baseUrl = "http://127.0.0.1:9080";

// close all command page except welcome
function myFunction() {
    var welcome = document.getElementsByClassName("welcome");
    var query = document.getElementsByClassName("query");
    var charts = document.getElementsByClassName("charts");
    var sqlConsole = document.getElementsByClassName("sqlConsole");
    welcome[0].style.display = "block";
    query[0].style.display = "none";
    charts[0].style.display = "none";
    sqlConsole[0].style.display = "none";

}


// clean function
function clearFunction() {
    var x = document.getElementById('queryInput');
    document.getElementById('queryInput').value = '';
    document.getElementById('queryResult').innerHTML = 'this is result to show';
    console.log(x);
}


// excuteSqlConsole
function excuteSqlConsole() {
    var x = document.getElementById('queryInput');
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    var theUrl = baseUrl+"/sqlTools/api/query";

    xmlhttp.open("POST", theUrl);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({sqlCommand: x.value, hint: ""}));

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log("excuteSqlConsole response with " + typeof this.response);
            const obj = JSON.parse(this.response);
            console.log(obj.data);
            const data = obj.data;

            var strCommand = "Command executed ";
            var strTime = "Measured time: ";
            var strAffected = "";

            if (obj.msg=='ok') {
                strCommand += "successfully. ";
            } else {
                strCommand += "failed. ";
            }

            if (data && data.measuredTime > 0) {
                strTime += data.measuredTime + " [ms] ";
            } else {
                strTime += "0 [ms]";
            }
            if (data && data.affectedRowNum >= 0) {
                strAffected += data.affectedRowNum + " rows affected. ";
            } else {
                strAffected += "-1 rows affected.";
            }
            var res = strCommand + strTime + strAffected;

            document.getElementById('queryResult').innerHTML = res;
        }
    };

    var Str = "Command executed";

    document.getElementById('queryResult').value = '';


}

// 1 QUERIES WINDOWS
function query() {
    var welcome = document.getElementsByClassName("welcome");
    var query = document.getElementsByClassName("query");
    var charts = document.getElementsByClassName("charts");
    var sqlConsole = document.getElementsByClassName("sqlConsole");
    welcome[0].style.display = "none";
    query[0].style.display = "block";
    charts[0].style.display = "none";
    sqlConsole[0].style.display = "none";
}

// QUERIES WINDOWS --USE_NL
function addHint(hint) {
    var nl = document.getElementById("hintNL");
    var hash = document.getElementById("hintHASH");
    var merge = document.getElementById("hintMERGE");
    var nohint = document.getElementById("noHint");
    nl.value = "0";
    nl.style.color = "black";
    hash.value = "0";
    hash.style.color = "black";
    merge.value = "0";
    merge.style.color = "black";
    nohint.value = "0";
    nohint.style.color = "black";

    var change = document.getElementById(hint);
    change.value = "1";
    change.style.color = "blue";

}

// QUERIES WINDOWS -- Clear
function clearQueries() {
    var x = document.getElementById('queriesInput');
    document.getElementById('queriesInput').value = '';
    console.log(x);
}

// QUERIES WINDOWS -- Execute
function executeQueries() {
    var x = document.getElementById('queriesInput');

    var nlVal = document.getElementById("hintNL").value;
    var hashVal = document.getElementById("hintHASH").value;
    var mergeVal = document.getElementById("hintMERGE").value;

    if (nlVal == '1') {
        hintVal = 'hintNL';
    } else if (hashVal == '1') {
        hintVal = 'hintHASH';
    } else if (mergeVal == '1') {
        hintVal = 'hintMERGE';
    } else {
        var hintVal = '';
    }

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    var theUrl = baseUrl+"/sqlTools/api/query";

    xmlhttp.open("POST", theUrl);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({sqlCommand: x.value, hint: hintVal}));

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(typeof this.response);
            const obj = JSON.parse(this.response);
            console.log(obj.data);
            const data = obj.data;

            // add time result to comparsion
            console.log(data.measuredTime);
            var comparisonClass = document.getElementsByClassName('Comparison');
            if (hintVal == "hintNL") {
                yValues[0] = data.measuredTime;
            } else if (hintVal == "hintHASH") {
                yValues[1] = data.measuredTime;
            } else if (hintVal == "hintMERGE") {
                yValues[2] = data.measuredTime;
            } else {
                yValues[3] = data.measuredTime;
            }
            console.log(yValues);

            // add new request query to history
            var historyClass = document.getElementById('History');
            var ptag = document.createElement('button'); // create button element
            ptag.id = historyIdx++;
            ptag.innerHTML = data.query;
            ptag.onclick = function () {
                // selectedHistoryIdx = ptag.id;
                selectedHistorySql = ptag.innerHTML;
                ptag.parentElement.childNodes.forEach((item) => {
                    if (item.tagName == 'BUTTON')
                        item.style.color = "black";
                })
                ptag.style.color = "blue";
            };

            console.log(ptag);
            historyClass.appendChild(ptag);

        }
    };
}


// 2 CHARTS WINDOWS
function charts() {

    var welcome = document.getElementsByClassName("welcome");
    var query = document.getElementsByClassName("query");
    var charts = document.getElementsByClassName("charts");
    var sqlConsole = document.getElementsByClassName("sqlConsole");
    welcome[0].style.display = "none";
    query[0].style.display = "none";
    charts[0].style.display = "block";
    sqlConsole[0].style.display = "none";

}

// Chart -- comparison
function comparison() {
    var comparison = document.getElementsByClassName("Comparison");
    var history = document.getElementById("History");
    var ExecutionPlan = document.getElementsByClassName("ExecutionPlan");
    ExecutionPlan[0].style.display = "none";
    comparison[0].style.display = "block";
    history.style.display = "none";

    var xValues = ["no.4(MERGE)", "no.3(HASH)", "no.2 (NL)", "no.1(NoHint)"];
    var barColors = ["orange", "orange", "orange", "orange"];
    new Chart("myChart", {
        type: "bar",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues,
                label: 'cost time'
            }]
        },
        options: {
            legend: {display: false},
            title: {
                display: true
            }
        },
        plugins: [ChartDataLabels],
        options: {}
    });
}


// Chart -- history
function showHistory() {
    var comparison = document.getElementsByClassName("Comparison");
    var history = document.getElementById("History");
    var ExecutionPlan = document.getElementsByClassName("ExecutionPlan");
    ExecutionPlan[0].style.display = "none";
    comparison[0].style.display = "none";
    history.style.display = "block";
}

// Chart -- explainPlan
function explainPlan() {
    var comparison = document.getElementsByClassName("Comparison");
    var history = document.getElementById("History");
    var ExecutionPlan = document.getElementsByClassName("ExecutionPlan");
    comparison[0].style.display = "none";
    history.style.display = "none";
    ExecutionPlan[0].style.display = "block";

    let ExecutionPlanTable = document.getElementsByClassName("ExecutionPlanTable")[0]
        .getElementsByTagName('tbody')[0];

    ExecutionPlanTable.innerHTML="                            <tr>\n" +
        "                               <th>operation</th>\n" +
        "                               <th>object_name</th>\n" +
        "                               <th>options</th>\n" +
        "                               <th>cardinality</th>\n" +
        "                               <th>bytes</th>\n" +
        "                               <th>tempSpace</th>\n" +
        "                               <th>cost</th>\n" +
        "                            </tr>"

    var x = selectedHistorySql;
    console.log(x);
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    var theUrl = baseUrl+"/sqlTools/api/explainPlan";

    xmlhttp.open("POST", theUrl);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({sqlCommand: x}));

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const obj = JSON.parse(this.response);
            console.log(obj.data);
            const data = obj.data;

            document.getElementById("ExecutionPlanSQL").innerText=x;

            data.forEach(item => {

                let newRow = ExecutionPlanTable.insertRow();
                let newCell = newRow.insertCell();
                let newDiv = document.createElement("div");
                newDiv.innerHTML = createBlankSpace(item.depth) + item.operation;
                newCell.appendChild(newDiv);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.objectName?item.objectName:'');
                newCell.appendChild(newText);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.options?item.options:'');
                newCell.appendChild(newText);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.cardinality);
                newCell.appendChild(newText);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.bytes);
                newCell.appendChild(newText);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.tempSpace);
                newCell.appendChild(newText);

                newCell = newRow.insertCell();
                newText = document.createTextNode(item.cost);
                newCell.appendChild(newText);
            })
        }
    };


}

function createBlankSpace(depth) {
    let result = '';
    for (var i=0; i<4*depth; i++) {
        result += '&nbsp;';
    }
    return result;
}

// 3 SQL console
function sqlConsole() {

    var welcome = document.getElementsByClassName("welcome");
    var query = document.getElementsByClassName("query");
    var charts = document.getElementsByClassName("charts");
    var sqlConsole = document.getElementsByClassName("sqlConsole");
    welcome[0].style.display = "none";
    query[0].style.display = "none";
    charts[0].style.display = "none";
    sqlConsole[0].style.display = "block";

}
