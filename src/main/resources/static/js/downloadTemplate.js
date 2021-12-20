$( document ).ready(function() {
alert("Dwonload Template Page");
$('#dropOperator').on('change', function (e) {
    var optionSelected = $("option:selected", this);
    var valueSelected = this.value;
    loadTask(valueSelected);
});


$("#btn").on("click",function(){
alert("hi");
});


});
function loadTask(e)
{
    alert("Test-->"+e);
}
function awesomeClick(){
//alert(${moduleName});
   var personJson = $('#moduleName1').val();
   debugger;
   alert(personJson);
         $.ajax({
           url: "/evosys/bankaccount/testAjax", //added '/' in the beginning
           type: 'POST',
           data: personJson,
           dataType: "text",
           contentType: "text/plain", //change
           //mimeType: false,   //remove
           success: function(response) {
           debugger;
           alert(response);
            return false;
           },
           error: function(data){
                   alert("fail");
               }
         });
}

