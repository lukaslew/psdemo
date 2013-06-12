(function () {
    'use strict';

    angular.module('personService', ['ngResource']).factory('Person', function ($resource) {
        return $resource('/person/:id', {id : '@id'});
    });

    angular.module('ps-demo', ['personService'])
        .filter('capitalize', function() {
            return function(input, scope) {
                var value = (input && input.toLowerCase()) || '';
                return value.substring(0,1).toUpperCase() + value.substring(1);
            }
        })
        .controller('PersonCtrl', function ($scope, Person) {
            $scope.persons = Person.query();
            $scope.person = {};

            $scope.save = function () {
                Person.save($scope.person, function () {
                    $scope.person = {};
                    $scope.persons = Person.query();
                });
            };

            $scope.edit = function (person) {
                $scope.person.id = person.id;
                $scope.person.name = person.name;
                $scope.person.gender = person.gender;
            }

            $scope.remove = function (person) {
                bootbox.confirm("Are you sure?", function(result) {
                    if (result) {
                        person.$delete(function () {
                            $scope.person = {};
                            $scope.persons = Person.query();
                        });
                    }
                });
            };
        });

}());
