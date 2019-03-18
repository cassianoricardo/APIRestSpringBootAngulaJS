//Criação do modulo principal da aplicação
var appPessoa = angular.module("appPessoa",[]);

// Criação de controllers
appPessoa.controller("indexController",function ($scope,$http){
	
	$scope.pessoas=[];
	$scope.pessoa={nome:""};
		
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
		if($scope.validarCampos()){
		
		$http({method:'POST', url:'http://localhost:8080/rest/pessoa/salvar',data:$scope.pessoa})
		.then(function successCallback(response) {
			$scope.pessoas.push(response.data);
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function errorCallback(response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
		}
	
	}
	
	$scope.removerPessoa=function(pessoa){
		$http({method:'DELETE', url:'http://localhost:8080/rest/pessoa/remover/'+pessoa.id})
		.then(function (response) {
			$scope.pessoas.splice($scope.pessoas.indexOf(pessoa),1);
		   	console.log(response.data);
		   	console.log(response.status);
		  }, function (response) {
			  console.log(response.data);
			  console.log(response.status);
		  });
	}
	
	$scope.listarPessoas();
	
	$("#telefone").mask("(00) 0000-0000");
	$("#celular").mask("(00) 00000-0000");
	
	$scope.validarCampos=function(){
		if($scope.pessoa.nome ==""){
			alert('Preencha o campo com seu nome');
			var elNome = document.getElementById("nome");
			elNome.focus();
			return false;
		}else{
			$scope.pessoa.telefone = document.getElementById("telefone").value.replace(/[^\d]+/g,'');
			$scope.pessoa.celular = document.getElementById("celular").value.replace(/[^\d]+/g,'');
			return true;
		}
	}
})