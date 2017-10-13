<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>

<title>Leaflet Web Map</title>

<link rel="stylesheet" href="js/leaflet/leaflet.css" />

<script type="text/javascript" src="js/leaflet/leaflet.js" ></script>

<style>
#map {
    width: 960px;
    height:500px;
}
</style>

</head>

<body>

    <div id="map"></div>

<script>

    var map = L.map('map',{
    center: [43.64701, -79.39425],
    zoom: 15
    });

    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

</script>

</body>

</html>