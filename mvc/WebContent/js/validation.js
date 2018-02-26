function validateUsername(username) {
	if (username.trim() == "" || username.length > 255) {
		return false;
	}
	return true;
}

function validatePassword(password) {
	if (password.trim() == "" || password.length > 255) {
		return false;
	}
	return true;
}

function validateFullname(fullname) {
	if (fullname.trim() == "" || fullname.length > 255) {
		return false;
	}
	return true;
}

function validateAddress(address) {
	if (address.trim() == "" || address.length > 255) {
		return false;
	}
	return true;
}

function validatePhone(phone) {
	if (/((?:\+|00)[17](?: |\-)?|(?:\+|00)[1-9]\d{0,2}(?: |\-)?|(?:\+|00)1\-\d{3}(?: |\-)?)?(0\d|\([0-9]{3}\)|[1-9]{0,3})(?:((?: |\-)[0-9]{2}){4}|((?:[0-9]{2}){4})|((?: |\-)[0-9]{3}(?: |\-)[0-9]{4})|([0-9]{7}))/.test(phone)) {
		return true;
	}
    return false;
}

function validateEmail(email) {
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
		return true;
    }
	return false;
}

function ifInvalidDrawKhaki(idElement, fieldToCheck) {
	if (!fieldToCheck) {
		$(idElement).addClass("w3-khaki");
	} else {
		$(idElement).removeClass("w3-khaki");
	}
}