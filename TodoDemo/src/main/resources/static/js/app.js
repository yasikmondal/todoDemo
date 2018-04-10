var taskManagerModule = angular.module('todoApp', ['ngAnimate']);
taskManagerModule.controller('todoController', function ($scope,$http) {
 var urlBase="http://localhost:8080";
 $scope.toggle=true;
 $scope.selection = [];
 $scope.statuses=['ACTIVE','COMPLETED','PENDING'];
 $scope.priorities=['HIGH','LOW','MEDIUM'];
 $http.defaults.headers.post["Content-Type"] = "application/json";
 
    function findAllTasks() {
        $http.get(urlBase + '/tasks').
            success(function (data) {
            	
            	$scope.tasks = data;
                for (var i = 0; i < $scope.tasks.length; i++) {
                    if ($scope.tasks[i].taskStatus == 'COMPLETED') {
                        $scope.selection.push($scope.tasks[i].taskId);
                    }
                }
                $scope.name="";
                $scope.desc="";
                $scope.priority="";
                $scope.status="";
                $scope.toggle='!toggle';
            }).error(function(data, status) {
            	  console.error('Repos error', status, data);
            });
    }
    
    findAllTasks();
    
 $scope.addTask = function addTask() {
  if($scope.name=="" || $scope.desc=="" || $scope.priority == "" || $scope.status == ""){
   alert("Please Enter all the field");
  }
  else{
   $http.post(urlBase + '/tasks/insert'+'/'+$scope.name+'/'+$scope.desc+'/'+$scope.priority+'/'+$scope.status
             ).
    success(function(data, status, headers) {
    alert("Task has been added");
             var newTaskUri = headers()["location"];
             findAllTasks();
      });
  }
 };
 
 $scope.changeStats = function changeStats(id, status) {
   $scope.taskId=id;
   $scope.taskStatus=status;
   $http.put(urlBase + '/tasks/updateTasks/'+$scope.taskId + '/'+$scope.taskStatus).
   success(function (data,status ) {
	   alert("Task has been Successfully updated");
	   findAllTasks();
   }).error(function(data, status) {
	   alert("Eror");
   	  
   });
   
 };
 

   $scope.toggleSelection = function toggleSelection(taskUri) {
     var idx = $scope.selection.indexOf(taskUri);
     if (idx > -1) {
       $http.patch(taskUri, { status: 'ACTIVE' }).
    success(function(data) {
        alert("Task unmarked");
              findAllTasks();
      });
       $scope.selection.splice(idx, 1);
     }
     else {
       $http.patch(taskUri, { status: 'COMPLETED' }).
    success(function(data) {
     alert("Task marked as completed");
              findAllTasks();
      });
       $scope.selection.push(taskUri);
     }
   };
 // Archive Completed Tasks
   $scope.archiveTasks = function archiveTasks() {
          $scope.selection.forEach(function(taskUri) {
              if (taskUri != undefined) {
                  $http.patch(taskUri, { taskArchived: 1});
              }
          });
          alert("Task has been Successfully Archived");
          findAllTasks();
   };
   
// Archive Completed Tasks
   $scope.deleteTasks = function deleteTasks(taskUri) {
	   $scope.id=taskUri;
	   $http.delete(urlBase + '/tasks/delete/'+$scope.id).
       success(function (data,status ) {
    	   alert("Task has been Successfully Deleted");
    	   findAllTasks();
       }).error(function(data, status) {
    	   alert("Eror");
       	  
       });
          
          
   };
});
//Angularjs Directive for confirm dialog box
taskManagerModule.directive('ngConfirmClick', [
 function(){
         return {
             link: function (scope, element, attr) {
                 var msg = attr.ngConfirmClick || "Are you sure?";
                 var clickAction = attr.confirmedClick;
                 element.bind('click',function (event) {
                     if ( window.confirm(msg) ) {
                         scope.$eval(clickAction);
                     }
                 });
             }
         };
 }]);