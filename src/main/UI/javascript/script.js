var scotchApp = angular.module('scotchApp', ['ngRoute']);

scotchApp.config(function($routeProvider) {
    $routeProvider

        .when('/', {
            templateUrl : 'home.html',
            controller  : 'mainController'
        })

        .when('/secretpage', {
            templateUrl : 'secretpage.html',
           // controller  : 'aboutController'
        })

        .when('/signin', {
            templateUrl : 'signin.html',
            controller  : 'signinController'
        })

        .when('/signout', {
            templateUrl : 'signout.html',
            controller  : 'signoutController'
        })

        .when('/signup', {
            templateUrl : 'signup.html',
            controller  : 'signupController'
        });
});

scotchApp.run(function($rootScope) {
});

scotchApp.controller('mainController', function($scope,$rootScope, $http) {
    $http({
        method: 'GET',
        withCredentials: true,
        url: 'http://127.0.0.1:8080/user/isloggedin'
    }).then(function successCallback(response) {
      //  $scope.$parent.headerUsername = setHeaderUsername(response);
        var answer = angular.fromJson(response.data);
        $scope.$parent.loggedin = answer.result;
    }, function errorCallback(response) {
        $scope.loggedin = false;
    });
});

scotchApp.controller('signoutController', function($scope,$rootScope, $http) {
    $http({
        method: 'GET',
        withCredentials: true,
        url: 'http://127.0.0.1:8080/user/logout'
    }).then(function successCallback(response) {
        $scope.message = "Logged out Successfully";
        $scope.$parent.loggedin = false;
    }, function errorCallback(response) {
        $scope.message = "Error";
    });
});

scotchApp.controller('signinController', function($scope, $http) {
    $scope.showLoader=false;
    $scope.login = function() {
    $scope.message ="";
    $scope.showLoader=true;
        var jsonObj = "username="+$scope.username+"&password="+$scope.password;
        $http({
            url: "http://127.0.0.1:8080/user/login",
            withCredentials: true,
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: jsonObj
        }).success(function(data, status, headers, config) {
            $scope.showLoader=false;
            if(data.result) {
                $scope.message = "You are now signed in.";
                $scope.$parent.loggedin = true;
            }
        }).error(function(data, status, headers, config) {
            $scope.showLoader=false;
            $scope.message = "Wrong username and/or password";
        });
    };
});


scotchApp.controller('signupController', function($scope, $http) {
    $scope.showLoader=false;

    $scope.login = function() {
        $scope.message ="";
        $scope.showLoader=true;
        var jsonObj =
        {
            "email": $scope.username,
            "password": $scope.password,
            "birthdate":"1.1.2001"

        };
        $http({
            url: "http://127.0.0.1:8080/user/signup/",
            method: "POST",
            data: jsonObj
        }).success(function(data, status, headers, config) {
            $scope.showLoader=false;
            if(data.result) {
                $scope.message = "Signed up successfully";
                $scope.$parent.loggedin = true;
                $scope.$parent.headerUsername = data.username;
            }
            else
            {
                $scope.message = data.result;
            }
        }).error(function(data, status, headers, config) {
            $scope.showLoader=false;
            $scope.message = "Error. something went wrong.";
        });
    };
});

function setHeaderUsername(response)
{
    var answer = angular.fromJson(response.data);
    return answer.username;
}
