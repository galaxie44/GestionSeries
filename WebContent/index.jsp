<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// Page d'accueil : on passe toujours par le servlet /liste
	// (qui charge les séries depuis la BDD avant d'afficher la JSP)
	response.sendRedirect(request.getContextPath() + "/liste");
%>
