var app = angular.module("NotesManagement", []);

app.controller("NotesController", function($scope, $http) {


    $scope.notes = [];
    $scope.noteForm = {
        id: 1,
        caption: "",
        textNote: "",
    };


    $scope.noteFormDelete = {
        id: 1,
        caption: "",
        textNote: "",
    };

    $scope.textFilter="";
    // Now load the data from server
    _refreshNotesData();

    $scope.submitNote = function() {

        var method = "";
        var url = "";

        if ($scope.noteForm.id == -1) {
            method = "POST";
            url = '/notes';
        } else {
            method = "PUT";
            url = '/notes/' +$scope.noteForm.id;
        }

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.noteForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(
            _success
             , _error);

        $('#exampleModal').modal('hide');
    };

    $scope.createNote = function() {
        _clearFormData();
        $('#exampleModal').modal('show');
    }


    $scope.deleteNoteModal = function(note) {
        $scope.noteFormDelete.id = note.id;
        $scope.noteFormDelete.caption = note.caption ;
        $scope.noteFormDelete.textNote = note.textNote;

        $('#deleteModal').modal('show');
    };

    $scope.deleteNote = function(note) {
        $http({
            method: 'DELETE',
            url: '/notes/' + note.id
        }).then(_success, _error);

        $('#deleteModal').modal('hide');
    };

    $scope.editNote = function(note) {
        $scope.noteForm.id = note.id;
        $scope.noteForm.caption = note.caption ;
        $scope.noteForm.textNote = note.textNote;

        $('#exampleModal').modal('show');
    };


    $scope.filterNote = function() {
        _refreshNotesData($scope.textFilter);
    }

    $scope.notFilterNote = function() {
        $scope.textFilter="";
        _refreshNotesData();
    }


    // Private Method
    // HTTP GET- get all notes collection
    // Call: http://localhost:8080/notes
    function _refreshNotesData(textFilter) {
        var url = '/notes';

        if (!textFilter) {
            url = '/notes';
        } else {
            url= '/notes?text_filter='+textFilter;
        }
        console.log(url);
        $http({
            method: 'GET',
            url: url
        }).then(
            function(res) { // success
                $scope.notes = res.data;
            },
            function(res) { // error
                _error(res);
            }
        );
    }

    function _success(res) {
        _refreshNotesData();
        _clearFormData();
    }

    function _error(res) {
        var debugMessage = res.data.debugMessage;
        var status = res.status;
        var message = res.data.message;
        console.log("Error: " + status  );
        console.log("message: " + message);
        console.log("debugMessage: " + debugMessage);
        console.log(res.toString());
        alert("Error: " + status + ":" +message+" - "+"\r\n"+ debugMessage);
    }

    // Clear the form
    function _clearFormData() {
        $scope.noteForm.id = -1;
        $scope.noteForm.caption = "";
        $scope.noteForm.textNote = "";
    };
});