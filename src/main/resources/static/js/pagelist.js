$( document ).ready(function() {
// Hide all group and account level
$('.pa-dt-group-account-level').hide();
$('.pa-dt-account-level').hide();
var button = $( "a.pa-dt-toggle" );
button.click(function() {
debugger;
	// get the parent ID
	var parentId = $(this).parents('tr').attr("id");
	// get the parent account level
	var accountType = $(this).parents('tr').attr("data-account-level");

	$(this).text($(this).text() == '+' ? '-' : '+');

	if ($(this).hasClass("pa-dt-toggle-active")) {
		$(this).removeClass('pa-dt-toggle-active');
		$('[data-owner="' + parentId +'"]').hide();

	} else {
		$(this).addClass('pa-dt-toggle-active');
		$('[data-owner="' + parentId +'"]').show();
	}
});

button.click(function(e){
	e.stopPropagation();
});

});

function awesomeClickCollapse(a){
    debugger;
    var param1 = a.dataset.param1;
    var param2 = a.dataset.param2;
    alert(param1);
    alert(param2);

}
function awesomeClick(a){
    debugger;
    var track_id = a.dataset.param1;
    var workbook_name = a.dataset.param2;
    var workbook_id = a.dataset.param3;
    alert(track_id);
    alert(workbook_name);
    alert(workbook_id);
    var downloadTemplateObject =new Object();
    downloadTemplateObject.track_id=track_id;
    downloadTemplateObject.workbook_name=workbook_name;
    downloadTemplateObject.workbook_id=workbook_id;
 /*   $.ajax({
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
                });*/
}

