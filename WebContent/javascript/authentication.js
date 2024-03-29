function testRequest() {
	var request = JSON.stringify({
		type: 'TEST',
		parameter: 'PARAMETER'
	});
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open('POST', 'http://localhost:8001/checkinsy/handler', true);
	xmlHttp.send(request);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			document.getElementById("testResponse").innerHTML = xmlHttp.responseText;
		}
	}
}

function createAccount() {
	var username = 'Tom';
	var password = 'Test';
	var request = JSON.stringify({
		type: 'CREATE_ACCOUNT',
		username: username,
		password: password
	});
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open('POST', 'http://localhost:8001/checkinsy/handler', true);
	xmlHttp.send(request);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			document.getElementById("createResponse").innerHTML = xmlHttp.responseText;
		}
	}
}

function createMember() {
	var name = 'Tom';
	var group = 'Group';
	var request = JSON.stringify({
		type: 'CREATE_MEMBER',
		name: name,
		group: group
	});
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open('POST', 'http://localhost:8001/checkinsy/handler', true);
	xmlHttp.send(request);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			document.getElementById("createMemberResponse").innerHTML = xmlHttp.responseText;
		}
	}
}

function getUsers() {
	var group = 'Group';
	var request = JSON.stringify({
		type: 'GET_USERS',
		group: group,
	});
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open('POST', 'http://localhost:8001/checkinsy/handler', true);
	xmlHttp.send(request);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			document.getElementById("userResponse").innerHTML = xmlHttp.responseText;
		}
	}
}

