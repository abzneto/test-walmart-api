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

        		$scope.mapId  = mapId;
            	$scope.routes = walmart.loadJSON('route/' + $scope.mapId).routes;
        	} else {
        		$("#sectionResult").hide();
				$("#sectionCalc").hide();
				$("#sectionRoute").hide();				
        	}     	
        };
        
        $scope.save = function() { 
        	if (!$scope.map.name || $scope.map.name.trim() == '') {
        		alert('Favor informar o nome do mapa!!!');
        		return false;
        	}
        	walmart.postJSON( '/map?mapId=' + $scope.mapId + '&mapName=' + $scope.map.name,
    			{
                success: function(response) {
                	$scope.mapId = response.mapId;
                	
                	walmart.postJSON( '/route?mapId=' + $scope.mapId,
                    {
                        data: $scope.routes,
                        
                        success: function(response) {
                        	$scope.loadRoutes($scope.mapId);   
                        	
                        	var results = walmart.loadJSON('/map/' + $scope.mapId + '/calculate?origin=' + $scope.origin + '&destination=' + $scope.destination + '&autonomy=' + $scope.autonomy + '&valueLiter=' + $scope.litterPrice).results;
                        	
                        	if (!results || !results.path || results.path == '') {
                        		alert("Não foi possível calcular com os parâmetros informados.");
                        		
                        		$("#sectionResult").hide();
                        	} else {
                        		$("#sectionResult").show();
                        		$("#resultPath").val( results.path );
                        		$("#resultCost").val( results.cost );
                        		$('html, body').animate({ 'scrollTop' : $(document).height() }, 1);                        		
                        	}
                        },
                        
                        isRoutes : true,                

                        error: function(xhr, status) {
                            alert( 'Não foi possível salvar os dados!');
                        }
                    });
                },

                error: function(xhr, status) {
                    alert( 'Não foi possível adicionar o mapa.');
                }
            });
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

