<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="./inc/style.css?v=4" />
<script src="./inc/GestionSeries.js?v=4" defer></script>
<title>Mes séries</title>
</head>
<body>
	<div class="shell">
		<header class="nav">
			<div class="nav-brand">
				<span class="logo" aria-hidden="true"></span>
				<span class="logo-text">GestionSeries</span>
			</div>
			<button type="button" class="btn btn-blue" id="btnNewSerie" title="Créer une nouvelle série">
				<svg width="16" height="16" viewBox="0 0 16 16" fill="none" aria-hidden="true">
					<path d="M8 3v10M3 8h10" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"/>
				</svg>
				Nouvelle série
			</button>
		</header>

		<section class="hero">
			<h1>Mes séries</h1>
			<p class="subtitle">Suivi simple de tes saisons et épisodes.</p>
		</section>

		<c:if test="${not empty erreur}">
			<div class="toast toast-error" role="alert"><c:out value="${erreur}" /></div>
		</c:if>

		<main>
			<form method="post" id="newSerieForm" action="./creerSerie" hidden>
				<input type="hidden" name="nomNvlleSerie" id="nomNvlleSerie" />
			</form>

			<c:choose>
				<c:when test="${empty series}">
					<section class="empty">
						<div class="empty-icon" aria-hidden="true">
							<svg width="40" height="40" viewBox="0 0 40 40" fill="none">
								<rect x="6" y="10" width="28" height="20" rx="4" stroke="currentColor" stroke-width="1.6"/>
								<path d="M6 16h28" stroke="currentColor" stroke-width="1.6"/>
								<circle cx="12" cy="13" r="1.2" fill="currentColor"/>
								<circle cx="16.5" cy="13" r="1.2" fill="currentColor"/>
							</svg>
						</div>
						<h2>Aucune série</h2>
						<p>Commence par ajouter une série à ta liste.</p>
						<button type="button" class="btn btn-blue" id="btnNewSerieEmpty">Ajouter une série</button>
					</section>
				</c:when>
				<c:otherwise>
					<div class="stack">
						<c:forEach var="serie" items="${series}">
							<article class="panel status-${serie.toutVu}">
								<header class="panel-head">
									<div class="panel-titles">
										<span class="pill"></span>
										<h2><c:out value="${serie.nom}" /></h2>
									</div>
									<div class="actions">
										<form method="post" action="./creerSaison">
											<input type="hidden" name="serie" value="${serie.id}" />
											<button type="submit" class="icon-btn" title="Ajouter une saison" aria-label="Ajouter une saison">
												<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M8 3v10M3 8h10" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/></svg>
											</button>
										</form>
										<form method="post" action="./supprimerSerie" class="js-confirm-delete"
											data-message="Supprimer la série « ${serie.nom} » et tout son contenu ?">
											<input type="hidden" name="serie" value="${serie.id}" />
											<button type="submit" class="icon-btn" title="Supprimer la série" aria-label="Supprimer la série">
												<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M4 4l8 8M12 4l-8 8" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/></svg>
											</button>
										</form>
										<button type="button" class="icon-btn" id="serie-${serie.id}-bt" title="Replier"
											onclick="masquer('serie-${serie.id}')" aria-label="Replier">
											<svg id="serie-${serie.id}-img" width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M4 10l4-4 4 4" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/></svg>
										</button>
									</div>
								</header>

								<div class="panel-body">
									<c:forEach var="saison" items="${serie.saisons}">
										<section class="season status-${saison.toutVu} serie-${serie.id}-saison">
											<header class="season-head">
												<div class="panel-titles">
													<p class="kicker">Saison <c:out value="${saison.numero}" /></p>
													<h3><c:out value="${saison.nom}" /></h3>
													<span class="pill"></span>
												</div>
												<div class="actions">
													<form method="post" action="./creerEpisode" class="js-episode-form"
														data-serie="${serie.id}" data-saison="${saison.id}">
														<input type="hidden" name="serie" value="${serie.id}" />
														<input type="hidden" name="saison" value="${saison.id}" />
														<input type="hidden" name="nomEpisode" id="${serie.id}${saison.id}" />
														<button type="submit" class="icon-btn" title="Ajouter un épisode" aria-label="Ajouter un épisode">
															<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M8 3v10M3 8h10" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/></svg>
														</button>
													</form>
													<form method="post" action="./supprimerSaison" class="js-confirm-delete"
														data-message="Supprimer la saison ${saison.numero} et ses épisodes ?">
														<input type="hidden" name="serie" value="${serie.id}" />
														<input type="hidden" name="saison" value="${saison.id}" />
														<button type="submit" class="icon-btn" title="Supprimer la saison" aria-label="Supprimer la saison">
															<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M4 4l8 8M12 4l-8 8" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/></svg>
														</button>
													</form>
													<button type="button" class="icon-btn" id="serie-${serie.id}-s${saison.numero}-bt" title="Replier"
														onclick="masquerSaison('serie-${serie.id}-s${saison.numero}')" aria-label="Replier">
														<svg id="serie-${serie.id}-s${saison.numero}-img" width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M4 10l4-4 4 4" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/></svg>
													</button>
												</div>
											</header>

											<ul class="episodes">
												<c:forEach var="episode" items="${saison.episodes}">
													<li class="episode status-${episode.vu} serie-${serie.id}-saison serie-${serie.id}-s${saison.numero}-ep">
														<div class="episode-main">
															<span class="ep-index"><c:out value="${episode.numero}" /></span>
															<span class="ep-title"><c:out value="${episode.titre}" /></span>
															<span class="pill"></span>
														</div>
														<div class="actions">
															<form method="post" action="./etat">
																<input type="hidden" name="serie" value="${serie.id}" />
																<input type="hidden" name="saison" value="${saison.id}" />
																<input type="hidden" name="episode" value="${episode.id}" />
																<button type="submit" class="icon-btn" title="Changer d'état" aria-label="Changer d'état">
																	<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M1.5 8s2.5-4.5 6.5-4.5S14.5 8 14.5 8s-2.5 4.5-6.5 4.5S1.5 8 1.5 8z" stroke="currentColor" stroke-width="1.4"/><circle cx="8" cy="8" r="2" stroke="currentColor" stroke-width="1.4"/></svg>
																</button>
															</form>
															<form method="post" action="./supprimerEpisode" class="js-confirm-delete"
																data-message="Supprimer l'épisode « ${episode.titre} » ?">
																<input type="hidden" name="serie" value="${serie.id}" />
																<input type="hidden" name="saison" value="${saison.id}" />
																<input type="hidden" name="episode" value="${episode.id}" />
																<button type="submit" class="icon-btn" title="Supprimer l'épisode" aria-label="Supprimer l'épisode">
																	<svg width="16" height="16" viewBox="0 0 16 16" fill="none"><path d="M4 4l8 8M12 4l-8 8" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/></svg>
																</button>
															</form>
														</div>
													</li>
												</c:forEach>
											</ul>
										</section>
									</c:forEach>
								</div>
							</article>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</main>
	</div>

	<div class="modal" id="nameModal" hidden>
		<div class="modal-backdrop" data-close></div>
		<div class="modal-card" role="dialog" aria-modal="true" aria-labelledby="modalTitle">
			<h3 id="modalTitle">Nouvelle série</h3>
			<p id="modalHint" class="modal-hint">Donne un nom clair à ta série.</p>
			<label class="field">
				<span class="field-label">Nom</span>
				<input type="text" id="modalInput" autocomplete="off" placeholder="Ex. The Good Place" />
			</label>
			<div class="modal-actions">
				<button type="button" class="btn btn-ghost" data-close>Annuler</button>
				<button type="button" class="btn btn-blue" id="modalConfirm">Continuer</button>
			</div>
		</div>
	</div>
</body>
</html>
