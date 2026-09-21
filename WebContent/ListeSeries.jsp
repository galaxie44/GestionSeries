<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="./inc/style.css" />
<script type="text/javascript" src="./inc/GestionSeries.js"></script>
<title>Mes séries</title>
</head>
<body>
	<h1>Mes séries</h1>
	<span class="erreur"><c:out value="${erreur }" /></span>
	<form method="post" id="newSerieForm" action="./creerSerie" onSubmit="return askSerieName()">
		<input type=hidden name="nomNvlleSerie" id="nomNvlleSerie" />
		<button type="submit" title="Créer une nouvelle série">
			<img src="./img/Add.png">
		</button>
	</form>
	<table style="padding: 20px; border: 1px;">
		<c:forEach var="serie" items="${series}">
			<tr>
				<td colspan="3" class="${serie.toutVu } Serie">${serie.nom}</td>
				<td class="${serie.toutVu }">
					<form method="post" action="./creerSaison">
						<input type="hidden" name="serie" value="${serie.id}" />
						<button type="submit" title="Créer une nouvelle saison">
							<img src="./img/Add.png">
						</button>
					</form>
					<button title="Supprimer la série">
						<img src="./img/Remove.png">
					</button>
					<button id="${serie.nom}_bt" title="Masquer le contenu de la série" onclick="masquer('${serie.nom}')">
						<img id="${serie.nom}_img" src="./img/Up.png">
					</button>
				</td>
			</tr>
			<c:forEach var="saison" items="${serie.saisons}">
				<tr class="${serie.nom}_saison">
					<td class="filler">&nbsp;</td>
					<td class="${saison.toutVu } Saison" colspan="2">Saison N°${saison.numero}</td>
					<td class="${saison.toutVu }">
						<form method="post" action="./creerEpisode" onSubmit="return askEpisodeName('${serie.id}',${saison.id})">
							<input type="hidden" name="serie" value="${serie.id}" />
							<input type="hidden" name="saison" value="${saison.id}" />
							<input type=hidden name="nomEpisode" id="${serie.id}${saison.id}" />
							<button type="submit" title="Créer un nouvel épisode">
								<img src="./img/Add.png">
							</button>
						</form>
						<button title="Supprimer la saison">
							<img src="./img/Remove.png">
						</button>
						<button id="${serie.nom}${saison.numero}_bt" title="Masquer le contenu de la saison"
							onclick="masquerSaison('${serie.nom}${saison.numero}')"
						>
							<img id="${serie.nom}${saison.numero}_img" src="./img/Up.png">
						</button>

					</td>
				</tr>
				<c:forEach var="episode" items="${saison.episodes}">
					<tr class="${serie.nom}_saison ${serie.nom}${saison.numero}_ep">
						<td class="filler">&nbsp;</td>
						<td class="filler">&nbsp;</td>
						<td class="${episode.vu} Episode">${episode.titre}</td>
						<td class="${episode.vu}">
							<form method="post" action="./etat">
								<input type="hidden" name="serie" value="${serie.id}" /> 
								<input type="hidden" name="saison" value="${saison.id}" />
								<input type=hidden name="episode" value="${episode.id}" />
								<button type="submit" title="changer d'état">
									<img alt="changer d'état" src="./img/EyeChange_small.png">
								</button>
							</form>
							<button title="Supprimer l'épisode">
								<img src="./img/Remove.png">
							</button>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>