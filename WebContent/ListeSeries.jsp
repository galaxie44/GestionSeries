<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600;700&family=Source+Serif+4:opsz,wght@8..60,600;8..60,700&display=swap" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="./inc/style.css" />
<script type="text/javascript" src="./inc/GestionSeries.js" defer></script>
<title>Mes séries</title>
</head>
<body>
	<div class="app">
		<header class="topbar">
			<div class="brand">
				<p class="brand-mark">GestionSeries</p>
				<h1>Mes séries</h1>
			</div>
			<form method="post" id="newSerieForm" class="toolbar-form" action="./creerSerie" onsubmit="return askSerieName()">
				<input type="hidden" name="nomNvlleSerie" id="nomNvlleSerie" />
				<button type="submit" class="btn btn-primary" title="Créer une nouvelle série">
					<img src="./img/Add.png" alt="">
					<span>Nouvelle série</span>
				</button>
			</form>
		</header>

		<c:if test="${not empty erreur}">
			<div class="alert alert-error" role="alert">
				<c:out value="${erreur}" />
			</div>
		</c:if>

		<main class="content">
			<c:choose>
				<c:when test="${empty series}">
					<section class="empty-state">
						<h2>Aucune série pour le moment</h2>
						<p>Ajoute ta première série pour commencer le suivi des saisons et des épisodes.</p>
						<button type="submit" form="newSerieForm" class="btn btn-primary" title="Créer une nouvelle série">
							<img src="./img/Add.png" alt="">
							<span>Ajouter une série</span>
						</button>
					</section>
				</c:when>
				<c:otherwise>
					<ul class="series-list">
						<c:forEach var="serie" items="${series}">
							<li class="serie-block status-${serie.toutVu}">
								<div class="row serie-row">
									<div class="row-main">
										<span class="badge badge-status"></span>
										<h2 class="serie-title"><c:out value="${serie.nom}" /></h2>
									</div>
									<div class="row-actions">
										<form method="post" action="./creerSaison">
											<input type="hidden" name="serie" value="${serie.id}" />
											<button type="submit" class="btn btn-icon" title="Créer une nouvelle saison">
												<img src="./img/Add.png" alt="Ajouter une saison">
											</button>
										</form>
										<button type="button" class="btn btn-icon" title="Supprimer la série">
											<img src="./img/Remove.png" alt="Supprimer la série">
										</button>
										<button type="button" id="serie-${serie.id}-bt" class="btn btn-icon"
											title="Masquer le contenu de la série"
											onclick="masquer('serie-${serie.id}')">
											<img id="serie-${serie.id}-img" src="./img/Up.png" alt="Replier">
										</button>
									</div>
								</div>

								<ul class="saison-list">
									<c:forEach var="saison" items="${serie.saisons}">
										<li class="saison-block status-${saison.toutVu} serie-${serie.id}-saison">
											<div class="row saison-row">
												<div class="row-main">
													<span class="saison-label">Saison <c:out value="${saison.numero}" /></span>
													<p class="saison-title"><c:out value="${saison.nom}" /></p>
													<span class="badge badge-status"></span>
												</div>
												<div class="row-actions">
													<form method="post" action="./creerEpisode" onsubmit="return askEpisodeName('${serie.id}', ${saison.id})">
														<input type="hidden" name="serie" value="${serie.id}" />
														<input type="hidden" name="saison" value="${saison.id}" />
														<input type="hidden" name="nomEpisode" id="${serie.id}${saison.id}" />
														<button type="submit" class="btn btn-icon" title="Créer un nouvel épisode">
															<img src="./img/Add.png" alt="Ajouter un épisode">
														</button>
													</form>
													<button type="button" class="btn btn-icon" title="Supprimer la saison">
														<img src="./img/Remove.png" alt="Supprimer la saison">
													</button>
													<button type="button" id="serie-${serie.id}-s${saison.numero}-bt" class="btn btn-icon"
														title="Masquer le contenu de la saison"
														onclick="masquerSaison('serie-${serie.id}-s${saison.numero}')">
														<img id="serie-${serie.id}-s${saison.numero}-img" src="./img/Up.png" alt="Replier">
													</button>
												</div>
											</div>

											<ul class="episode-list">
												<c:forEach var="episode" items="${saison.episodes}">
													<li class="episode-row status-${episode.vu} serie-${serie.id}-saison serie-${serie.id}-s${saison.numero}-ep">
														<div class="row-main">
															<span class="ep-num">E<c:out value="${episode.numero}" /></span>
															<span class="episode-title"><c:out value="${episode.titre}" /></span>
															<span class="badge badge-status"></span>
														</div>
														<div class="row-actions">
															<form method="post" action="./etat">
																<input type="hidden" name="serie" value="${serie.id}" />
																<input type="hidden" name="saison" value="${saison.id}" />
																<input type="hidden" name="episode" value="${episode.id}" />
																<button type="submit" class="btn btn-icon" title="Changer d'état">
																	<img alt="Changer d'état" src="./img/EyeChange_small.png">
																</button>
															</form>
															<button type="button" class="btn btn-icon" title="Supprimer l'épisode">
																<img src="./img/Remove.png" alt="Supprimer l'épisode">
															</button>
														</div>
													</li>
												</c:forEach>
											</ul>
										</li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>
		</main>
	</div>
</body>
</html>
