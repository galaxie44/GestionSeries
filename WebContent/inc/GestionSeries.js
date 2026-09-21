/**
 * 
 */
function askSerieName() {
	var retour = prompt("Nouvelle série", "");
	if (retour == null || retour == "") {
		return false;
	}
	document.getElementById("nomNvlleSerie").value = retour;
	return true;
}

function askEpisodeName(serie, saison) {
	var retour = prompt("Nouvel épisode pour '" + serie + "' Saison " + saison,
			"");
	if (retour == null || retour == "") {
		return false;
	}
	document.getElementById(serie + saison).value = retour;
	return true;
}

function masquer(serie) {
	var lignes = document.getElementsByClassName(serie + "_saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(serie + "_bt");
	bt.title = "Afficher le contenu de la série";
	bt.onclick = function() {
		afficher(serie)
	};
	document.getElementById(serie + "_img").src = "./img/Down.png";
}

function afficher(serie) {
	var lignes = document.getElementsByClassName(serie + "_saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "table-row";
	}
	var bt = document.getElementById(serie + "_bt");
	bt.title = "Masquer le contenu de la série";
	bt.onclick = function() {
		masquer(serie)
	};
	document.getElementById(serie + "_img").src = "./img/Up.png";
}

function masquerSaison(serie) {
	var lignes = document.getElementsByClassName(serie + "_ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(serie + "_bt");
	bt.title = "Afficher le contenu de la saison";
	bt.onclick = function() {
		afficherSaison(serie)
	};
	document.getElementById(serie + "_img").src = "./img/Down.png";
}

function afficherSaison(serie) {
	var lignes = document.getElementsByClassName(serie + "_ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "table-row";
	}
	var bt = document.getElementById(serie + "_bt");
	bt.title = "Masquer le contenu de la saison";
	bt.onclick = function() {
		masquerSaison(serie)
	};
	document.getElementById(serie + "_img").src = "./img/Up.png";
}
