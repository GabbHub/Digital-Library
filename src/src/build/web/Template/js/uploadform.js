
	function makeFileList() {
			var input = document.getElementById("filesToUpload");
                        var maxpage= document.getElementById("controllopagine").value;
			var ul = document.getElementById("fileList");
			while (ul.hasChildNodes()) {
				ul.removeChild(ul.firstChild);
			}
			for (var i = 0; i < input.files.length; i++) {
				var li = document.createElement("li");
				li.innerHTML = 'numero pagina file ' + input.files[i].name + ':<br><br>'+'<input type=\"number\" name=\"'+input.files[i].name+'\" min=\"0\" max=\"'+maxpage+'\" required/><br><br>';
				ul.appendChild(li);
			}
			if(!ul.hasChildNodes()) {
				var li = document.createElement("li");
				li.innerHTML = 'Nessun file selezionato';
				ul.appendChild(li);
			}
		}
        
        $(document).ready(function() {
  $('form').on('submit', function(e){
          var allNumBoxes = [];
    $('input[type=number]').each(function () {
        allNumBoxes.push($(this).val());
    });
      var sorted_arr = allNumBoxes.sort();
          for (var i = 0; i < allNumBoxes.length - 1; i++) {
        if (sorted_arr[i + 1] === sorted_arr[i]) {
            alert("Hai inserito numeri di pagina uguali!");
             e.preventDefault();
        }
    }
  });
});
        
        function checkdoublePass(){
            var pass1 = document.getElementById("password1");
            var pass2 = document.getElementById("password2");
            if (pass1.value!==pass2.value){
             alert("Hai inserito password diverse");
             event.preventDefault(); 
            }
        }
        
         function checkdoubleemail(){
            var email1 = document.getElementById("indirizzo1");
            var email2 = document.getElementById("indirizzo2");
            if (email1.value!==email2.value){
             alert("Hai inserito indirizzi diversi");
             event.preventDefault(); 
            }
        }
                
        function deleteFileList(){
            var ul = document.getElementById("fileList");
            while( ul.firstChild ){
            ul.removeChild( ul.firstChild );
}
        }
