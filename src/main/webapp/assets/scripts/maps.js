angular.module('AccordionApp', ['ui.bootstrap']);

var app = angular.module('Walmart',[]);

walmart.hostWebServices = '';

; (function() {
    'use strict';
    
    var url = '/maps';
    
    var maps = {
        form: undefined,

        init : function() {            
            maps.loadMaps();
        },
        
        loadMaps: function() {
            maps.dominios = {
        		mapas : walmart.loadJSON( 'map' ).maps
            };
        }
    };
    
    app.controller("Maps", function($scope) {
    	maps.init();
    	
    	$scope.mapas = maps.dominios.mapas;
    	
    	$scope.mapId = '';
    	
        $scope.mapas.unshift({
            id: '',
            name: 'Selecione'
        });
        
        $scope.addRoute = function() {
            $scope.routes.unshift({});
        };
        
        $scope.loadRoutes = function(mapId) { 
        	if (mapId && mapId != "") {
				$("#sectionCalc").show();
				$("#sectionRoute").show();
				
        		for (var mapa in $scope.mapas) {
            		if ($scope.mapas[mapa].id == mapId) {
            			$scope.map = {
            	        		id : $scope.mapas[mapa].id,
            	        		name : $scope.mapas[mapa].name
            	        };
            			
            			break;
            		}
            	}

            	$scope.routes = walmart.loadJSON('route/' + $scope.map.id).routes;
        	} else {
        		$("#sectionResult").hide();
				$("#sectionCalc").hide();
				$("#sectionRoute").hide();				
        	}     	
        };
        
        $scope.routeCalculate = function() { 
        	$scope.save();
        	
        	var results = walmart.loadJSON('/map/' + $scope.map.id + '/calculate?origin=' + $scope.origin + '&destination=' + $scope.destination + '&autonomy=' + $scope.autonomy + '&valueLiter=' + $scope.litterPrice).results;
        	
        	$scope.path = results.path;
        	$scope.cost = results.cost;
        	
        	$("#sectionResult").show();
        };
        
        $scope.addMap = function(mapId) { 
    		$("#buttonAddMap").show();
			$("#sectionCalc").show();
			$("#sectionRoute").show();
			
        	$scope.mapId = '';
        	$scope.routes = [];
        	
        	$scope.mapas.unshift({
        		id: '',
        		name: 'Selecione'
        	}); 
        };
        
        $scope.removeMap = function(mapId) {        	
        	if ( mapId && mapId != '' && mapId > 0){
        		if ( window.confirm("Deseja realmente excluir o mapa?") ) {
                	var response = walmart.deleteJSON( 'map/' + mapId  );

                    if (response.error) {
                        alert( "Não foi possível deletar o mapa.");

                        return;
                    }
                    
                    location.reload();
                }
        	}            
        };
        
        $scope.saveMap = function() {
        	walmart.postJSON( '/map?mapId=' + $scope.mapId + '&mapName=' + $scope.map.name,
    			{
                success: function(response) {
                	$scope.mapId = response.mapId;      	        
                },

                error: function(xhr, status) {
                    alert( 'Não foi possível adicionar o mapa.');
                }
            });
        };
        
        $scope.save = function() {
        	$scope.saveMap();

        	walmart.postJSON( '/route?mapId=' + $scope.mapId,
            {
                data: $scope.routes,
                
                isRoutes : true,                

                error: function(xhr, status) {
                    alert( 'Não foi possível salvar os dados!');
                }
            });
        };
        
        $scope.removeRoute = function(index) {     
        	var route = $scope.routes[index];
        	
        	if ( route && route.id ){
        		if ( window.confirm("Deseja realmente excluir a rota?") ) {
                	var response = walmart.deleteJSON( 'route/' + route.id  );

                    if (response.error) {
                        alert( "Não foi possível deletar a rota.");

                        return;
                    }
                    
                    $scope.routes.splice(index,1);
                }
        	}     
        };
    });
}());

