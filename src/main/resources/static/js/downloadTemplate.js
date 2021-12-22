$( document ).ready(function() {
   $("#dropOperator option").each(function() {
          $(this).siblings('[value="'+ this.value +'"]').remove();
        });
$('#dropOperator').on('change', function (e) {
    var selected=$(this).val();
   // alert(selected);
    var valueSelected = this.value;
      var sel = $(this).val(),
          $ddl = $("#choices-multiple-remove-button");
      if (!sel) { // if there is no value means first option is selected
        $ddl.find("option").show();
        $ddl.val(''); // show all options and reset the value
      }
      else {
        $ddl.find("option").hide(); // hide all options
        // show only those options which contain the selected value,
        // set the selected property to true for the only remaining ones
        $ddl.find("option[value='" + sel + "']").show();
      }
}).change();
$("#btn").on("click",function(){
alert("hi");
});
});

function awesomeClick(){
//alert(${moduleName});
   var personJson = $('#moduleName1').val();
   debugger;
   $.ajax({
              url: "/evosys/bankaccount/download?param=StudentRecord.xlsx", //added '/' in the beginning
              type: 'GET',
              data:null,
              xhrFields:{
                      responseType: 'blob'
                  },
              //mimeType: false,   //remove
              success: function(response) {
              debugger;
                var blob = response;
                      var downloadUrl = URL.createObjectURL(blob);
                      var a = document.createElement("a");
                      a.href = downloadUrl;
                      a.download = "downloadFile.xlsx";
                      document.body.appendChild(a);
                      a.click();
               return false;
              },
              error: function(data){
                      alert("fail");
                  }
            });


/*
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
         });*/




       /*debugger;
       var studentAnswers  = new Object();
       studentAnswers.testId = 1;
       studentAnswers.studentId = 1;
       studentAnswers.questionIds =  new Array();
       studentAnswers.questionIds[0] = "12345";
       studentAnswers.answerIds =new Array();
       studentAnswers.answerIds[0] = "12345";

       $.ajax({
           type : "POST",
           url : "/evosys/bankaccount/student",
           dataType: "json",
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           },
           data : JSON.stringify(studentAnswers),
           success : function(response) {
              alert('test submitted');
           },
           error : function(e) {
              alert(e.message);
           }
       });*/



}
function downloadFusionTemplate(){
debugger;
var downloadTemplateObject =new Object();
var moduleName = $('#dropOperator').find(":selected").val();
var options = new Array();
$('#choices-multiple-remove-button > option:selected').each(
     function(i){
         options[i] = $(this).text();
     });
     downloadTemplateObject.moduleName="StudentRecord.xlsx";
     downloadTemplateObject.moduleTasks =  new Array();
     downloadTemplateObject.moduleTasks =options;
     $.ajax({
                type : "POST",
                url : "/evosys/bankaccount/downloadTemplateModule",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data : JSON.stringify(downloadTemplateObject),
                success : function(response) {
                debugger;
                awesomeClick();
                },
                error : function(e) {
                   alert(e.message);
                }
            });


}

