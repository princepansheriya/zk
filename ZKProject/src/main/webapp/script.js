function showStage2() {
	var element = zk.Widget.$('$stage1');
	var $element = jq(element.$n());
	if ($element.hasClass("stage1-layout")) {
		$element.parent().addClass("show-stage2");
	}
}

function backStage() {
	var element = zk.Widget.$('$stage1');
	var $element = jq(element.$n());
	if ($element.hasClass("stage1-layout")) {
		$element.parent().removeClass("show-stage2");
	}
}

function validateInput(event) {
    var input = event.target.value;
    if(/\d/.test(input)) { // Regular expression to check for digits
        alert("Digits are not allowed!");
        event.target.value = input.replace(/\d/g, ''); // Removes any digits
    }
}
