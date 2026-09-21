/**
 * Interactions UI GestionSeries
 */

function askSerieName() {
	var retour = prompt("Nouvelle série", "");
	if (retour == null || retour.trim() === "") {
		return false;
	}
	document.getElementById("nomNvlleSerie").value = retour.trim();
	return true;
}

function askEpisodeName(serie, saison) {
	var retour = prompt("Nouvel épisode (série " + serie + ", saison " + saison + ")", "");
	if (retour == null || retour.trim() === "") {
		return false;
	}
	document.getElementById(serie + saison).value = retour.trim();
	return true;
}

function masquer(serie) {
	var lignes = document.getElementsByClassName(serie + "-saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(serie + "-bt");
	bt.title = "Afficher le contenu de la série";
	bt.onclick = function () {
		afficher(serie);
	};
	document.getElementById(serie + "-img").src = "./img/Down.png";
	document.getElementById(serie + "-img").alt = "Déplier";
}

function afficher(serie) {
	var lignes = document.getElementsByClassName(serie + "-saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "";
	}
	var bt = document.getElementById(serie + "-bt");
	bt.title = "Masquer le contenu de la série";
	bt.onclick = function () {
		masquer(serie);
	};
	document.getElementById(serie + "-img").src = "./img/Up.png";
	document.getElementById(serie + "-img").alt = "Replier";
}

function masquerSaison(saisonKey) {
	var lignes = document.getElementsByClassName(saisonKey + "-ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(saisonKey + "-bt");
	bt.title = "Afficher le contenu de la saison";
	bt.onclick = function () {
		afficherSaison(saisonKey);
	};
	document.getElementById(saisonKey + "-img").src = "./img/Down.png";
	document.getElementById(saisonKey + "-img").alt = "Déplier";
}

function afficherSaison(saisonKey) {
	var lignes = document.getElementsByClassName(saisonKey + "-ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "";
	}
	var bt = document.getElementById(saisonKey + "-bt");
	bt.title = "Masquer le contenu de la saison";
	bt.onclick = function () {
		masquerSaison(saisonKey);
	};
	document.getElementById(saisonKey + "-img").src = "./img/Up.png";
	document.getElementById(saisonKey + "-img").alt = "Replier";
}
