<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Cities of the world</title>
	
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
		<link rel="stylesheet" type="text/css" charset="utf-8" media="all" href="resources/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" charset="utf-8" media="all" href="resources/css/browse.css">
	</head>
	<body>
		<!-- Fixed navbar -->
		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">spring-web-mongo</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#all">All cities</a></li>
						<li><a href="#big">Biggest</a></li>
						<li><a href="#sweden">Sweden</a></li>
					</ul>
				</div>
			</div>
		</div>
	
		<div class="container">
			<div class="jumbotron">
				<h1>Browse data</h1>
				<p>Use the top menu to load different data sets.</p>
				<p>
					<a class="btn btn-lg btn-primary" href="#big"
						role="button">View the biggest cities</a>
				</p>
			</div>
			
			<div class="table-responsive l-hidden">
				<table class="table table-striped table-hover">
			        <thead>
			          <tr>
			            <th>#</th>
			            <th>Name</th>
			            <th>Country</th>
			            <th>Population</th>
			          </tr>
			        </thead>
			        <tbody>
			        	<c:forEach items="${cities}" var="currentcity" varStatus="index">
			        		<tr class="city-row">
			        			<td class="city-counter">${index.count}</td>
			        			<td class="city-name">${currentcity.accentCity}</td>
			        			<td class="city-country t-uppercase">${currentcity.country}</td>
			        			<td class="city-population">${currentcity.population}</td>
		        			</tr>
	        			</c:forEach>
			        </tbody>
				</table>
			</div>
		</div>

		<script type="text/javascript" src="resources/js/jquery/jquery.js"></script>
		<script type="text/javascript" src="resources/js/bootstrap/dist/js/bootstrap.js"></script>
		<script type="text/javascript" src="resources/js/app.js"></script>
	</body>
</html>
