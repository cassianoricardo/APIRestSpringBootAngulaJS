//Criação do modulo principal da aplicação
var appPessoa = angular.module("appPessoa",[]);

// Criação de controllers
appPessoa.controller("indexController",function ($scope,$http){
	
	$scope.pessoas=[];
	$scope.pessoa={};
		
	$scope.listarPessoas=function(){
		$http({method:'GET', url:'http://localhost:8080/rest/pessoas'})
		.then(function successCallback(response) {
			$scope.pessoas=response.data;
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function errorCallback(response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
	}
	
	$scope.consultarPessoaPorId=function(id){
		$http({method:'GET', url:'http://localhost:8080/rest/pessoas/'+id})
		.then(function successCallback(response) {
			$scope.pessoas=response.data;
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function errorCallback(response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
	}

	$scope.salvarPessoa=function(){
		$http({method:'POST', url:'http://localhost:8080/rest/pessoa/salvar',data:$scope.pessoa})
		.then(function successCallback(response) {
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function errorCallback(response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
		$scope.listarPessoas();
	}
	
	$scope.removerPessoa=function(id){
		$http({method:'DELETE', url:'http://localhost:8080/rest/pessoa/remover/'+id})
		.then(function (response) {
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function (response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
		$scope.listarPessoas();
	}
	
	$scope.listarPessoas();
	
})