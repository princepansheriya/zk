// script.js

// Function to show stage 2 and hide stage 1
function showStage2() {
	var element = zk.Widget.$('$stage1');
	var $element = jq(element.$n());
	if ($element.hasClass("stage1-layout")) {
		$element.css("display", "none"); // Hide element if it has the class
	}

	var element = zk.Widget.$('$stage2');
	var $element = jq(element.$n());
	if ($element.hasClass("stage2-layout")) {
		$element.css("display", "block");
	}
	
}

// Function to show stage 1 and hide stage 2
function backStage() {
	var element = zk.Widget.$('$stage1');
	var $element = jq(element.$n());
	if ($element.hasClass("stage1-layout")) {
		$element.css("display", "block"); // Hide element if it has the class
	}

	var element = zk.Widget.$('$stage2');
	var $element = jq(element.$n());
	if ($element.hasClass("stage2-layout")) {
		$element.css("display", "none");
	}
}
