numberInput = function(className) {
	document.getElementsByClassName(className)[0].type = 'number';
};

autocompleteOff = function(className) {
	document.getElementsByClassName(className)[0].setAttribute('autocomplete', 'off');
}

buttonOnAccordion = function(accordionClassName, accordionButtonClassName) {
	var accordion = document.getElementsByClassName(accordionClassName)[0];
	var accordionItems = accordion.childNodes;
	var j = 0;

	for (var i = 0; i < accordionItems.length; i++) {
		var accordionItem = accordionItems[i];
		var accordionButton = document.getElementsByClassName(accordionButtonClassName)[j++];
		var buttonLayout = accordionButton.parentNode.parentNode.parentNode.parentNode;

		accordionItem.childNodes[0].childNodes[0].appendChild(accordionButton);
	}

	buttonLayout.removeChild(buttonLayout.childNodes[0]);
	buttonLayout.removeChild(buttonLayout.childNodes[0]);
};