angular
		.module('routeOptimizer')
		.controller(
				'resultsController',
				function($timeout, $http) {
var map;
					var vm = this;
					vm.loading = false;
					vm.selectedIndex = -1;

					vm.selectFile = selectFile;

					vm.fileNameObj = {

						"fileName" : null
					};
					init();

					function init() {
						fetchList();

						$timeout(function() {
							var vehicleRouteLayerGroup;
							var intervalTimer;
							map = L.map('mapid', {
								center : [ -25.274398, 133.7751 ],
								zoom : 4
							});
							L
									.tileLayer(
											'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
											{
												attribution : '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
											}).addTo(map);
						});
					}

					function fetchList() {
						var url = "http://localhost:8080/getSolvedFileList";
						$http.get(url).success(function(response) {
							vm.fileList = response;
						}).error(function(err) {

						});

						//vm.fileList = ['File name 1', 'File name 2'];
					}
					ajaxError = function(jqXHR, textStatus, errorThrown) {
						console.log("Error: " + errorThrown);
						console.log("TextStatus: " + textStatus);
						console.log("jqXHR: " + jqXHR);
						alert("Error: " + errorThrown);
					};
		
					function selectFile(index) {
						vm.selectedIndex = index;
						vm.loading = true;
						var url = "http://localhost:8080/getSolved";
						vm.fileNameObj.fileName = vm.fileList[index];
						//alert(obj.fileName);
						$http.post(url, vm.fileNameObj).success(
								
							function(solution) {
						console.log(solution);
								 var markers = [];
								console.log(solution.customerList);
									angular.forEach(solution.customerList,
											function(index, customer) {
												var customerIcon = L
														.divIcon({
															iconSize : new L.Point(
																	20, 20),
															className : "vehicleRoutingCustomerMarker",
															html : "<span>"
																	+ index.demand
																	+ "</span>"
														});
												var marker = L.marker([
													index.latitude,
													index.longitude ], {
													icon : customerIcon
												});
												marker.addTo(map).bindPopup(
														index.latitude+","+index.longitude
																+ "</br>Deliver "
																+ index.demand
																+ " items.");
												markers.push(marker);
											});
							map.fitBounds(L.featureGroup(markers).getBounds());
									vm.loading = false;
									vm.mapData = solution;
								}).error(function(jqXHR, textStatus, errorThrown) {
									ajaxError(jqXHR, textStatus, errorThrown);
							vm.loading = false;
							alert('some error occured');
						});
					}

				});
