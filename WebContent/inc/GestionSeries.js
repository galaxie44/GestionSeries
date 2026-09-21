/**
 * UI GestionSeries — interactions + modale
 */

(function () {
	var modal = null;
	var input = null;
	var titleEl = null;
	var hintEl = null;
	var pending = null;

	function ready(fn) {
		if (document.readyState === "loading") {
			document.addEventListener("DOMContentLoaded", fn);
		} else {
			fn();
		}
	}

	function openModal(options) {
		pending = options;
		titleEl.textContent = options.title;
		hintEl.textContent = options.hint;
		input.value = "";
		input.placeholder = options.placeholder || "";
		modal.hidden = false;
		setTimeout(function () { input.focus(); }, 30);
	}

	function closeModal() {
		modal.hidden = true;
		pending = null;
	}

	function confirmModal() {
		if (!pending) {
			return;
		}
		var value = input.value.trim();
		if (!value) {
			input.focus();
			return;
		}
		var onConfirm = pending.onConfirm;
		closeModal();
		onConfirm(value);
	}

	ready(function () {
		modal = document.getElementById("nameModal");
		input = document.getElementById("modalInput");
		titleEl = document.getElementById("modalTitle");
		hintEl = document.getElementById("modalHint");

		if (!modal) {
			return;
		}

		document.querySelectorAll("[data-close]").forEach(function (el) {
			el.addEventListener("click", closeModal);
		});

		document.getElementById("modalConfirm").addEventListener("click", confirmModal);

		input.addEventListener("keydown", function (e) {
			if (e.key === "Enter") {
				e.preventDefault();
				confirmModal();
			} else if (e.key === "Escape") {
				closeModal();
			}
		});

		function startNewSerie() {
			openModal({
				title: "Nouvelle série",
				hint: "Choisis un nom clair et reconnaissable.",
				placeholder: "Ex. The Good Place",
				onConfirm: function (value) {
					document.getElementById("nomNvlleSerie").value = value;
					document.getElementById("newSerieForm").submit();
				}
			});
		}

		var btnNew = document.getElementById("btnNewSerie");
		var btnEmpty = document.getElementById("btnNewSerieEmpty");
		if (btnNew) {
			btnNew.addEventListener("click", startNewSerie);
		}
		if (btnEmpty) {
			btnEmpty.addEventListener("click", startNewSerie);
		}

		document.querySelectorAll(".js-episode-form").forEach(function (form) {
			form.addEventListener("submit", function (e) {
				e.preventDefault();
				var serie = form.getAttribute("data-serie");
				var saison = form.getAttribute("data-saison");
				openModal({
					title: "Nouvel épisode",
					hint: "Série " + serie + " · saison " + saison,
					placeholder: "Titre de l'épisode",
					onConfirm: function (value) {
						document.getElementById(serie + saison).value = value;
						form.submit();
					}
				});
			});
		});
	});
})();

function setChevron(el, expanded) {
	if (!el) {
		return;
	}
	el.innerHTML = expanded
		? '<path d="M4 10l4-4 4 4" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/>'
		: '<path d="M4 6l4 4 4-4" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/>';
}

function masquer(serie) {
	var lignes = document.getElementsByClassName(serie + "-saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(serie + "-bt");
	bt.title = "Afficher le contenu";
	bt.onclick = function () { afficher(serie); };
	setChevron(document.getElementById(serie + "-img"), false);
}

function afficher(serie) {
	var lignes = document.getElementsByClassName(serie + "-saison");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "";
	}
	var bt = document.getElementById(serie + "-bt");
	bt.title = "Replier";
	bt.onclick = function () { masquer(serie); };
	setChevron(document.getElementById(serie + "-img"), true);
}

function masquerSaison(saisonKey) {
	var lignes = document.getElementsByClassName(saisonKey + "-ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "none";
	}
	var bt = document.getElementById(saisonKey + "-bt");
	bt.title = "Afficher le contenu";
	bt.onclick = function () { afficherSaison(saisonKey); };
	setChevron(document.getElementById(saisonKey + "-img"), false);
}

function afficherSaison(saisonKey) {
	var lignes = document.getElementsByClassName(saisonKey + "-ep");
	for (var i = 0; i < lignes.length; i++) {
		lignes[i].style.display = "";
	}
	var bt = document.getElementById(saisonKey + "-bt");
	bt.title = "Replier";
	bt.onclick = function () { masquerSaison(saisonKey); };
	setChevron(document.getElementById(saisonKey + "-img"), true);
}
