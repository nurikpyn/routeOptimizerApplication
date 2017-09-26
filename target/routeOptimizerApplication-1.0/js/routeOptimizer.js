$(document).ready(function () {
	setScreenDivNewWidth();
	$invProcessRqstForm = $('#routeOptimizerForm');
	$invProcessRqstForm.ajaxForm({ success: loadrouteOptimizerSuccess});	
});
function loadrouteOptimizerSuccess(responseText, statusText) {
	
	$('#routeOptimizerDiv').html($.trim(responseText));
	$(window).scrollTop(0);
}
function setScreenDivNewWidth(){
	if (screen.width<1153) {
		$("div.new_Width").width("1000px");			
	} else {
		$("div.new_Width").attr("style","width:1153px");
	}
}
String.prototype.insert = function (index, string) {
	if (index > 0)
		return this.substring(0, index) + string + this.substring(index, this.length);
	else
		return  string + this;	
};
function getCursorPosition (inputField) {
  var cursorPosition = 0;
  // IE Support
  if (document.selection) {
	  inputField.focus ();
    var sel = document.selection.createRange ();
    sel.moveStart ('character', -inputField.value.length);
    cursorPosition = sel.text.length;
  }
  else if (inputField.selectionStart || inputField.selectionStart == '0')
	  cursorPosition = inputField.selectionStart;
  return (cursorPosition);
}
function isTextSelected(input) {
    if (typeof input.selectionStart == "number") {
        return input.selectionStart == 0 && input.selectionEnd == input.value.length;
    } else if (typeof document.selection != "undefined") {
        input.focus();
        return document.selection.createRange().text == input.value;
    }
}
function validateInteger(evt) {
	//var theEvent = evt || window.event;
	//var key = theEvent.keyCode || theEvent.which;
	var theEvent = evt ? evt : window.event;
	var key = theEvent.keyCode ? theEvent.keyCode : theEvent.which;
	var ctrlDown = theEvent.ctrlKey||theEvent.metaKey // Mac support
	keyString = String.fromCharCode( key );
	/*var regex = /[-\d\.]/;
	var objRegex = /^\d*[\.]?[0-9]{0,2}$/;*/
	var regex = /[\d]/;
	var objRegex = /^\d*$/;	
	var target = theEvent.target;
	if (typeof evt.target == 'undefined') {
	   target = theEvent.srcElement;
	} 
	var val = $(target).val();	
	var isSlected = isTextSelected(target);	
	if(theEvent.keyCode == 8 || theEvent.keyCode == 127 || theEvent.keyCode == 9 || theEvent.keyCode == 37 || theEvent.keyCode == 39 ||
			(ctrlDown && key == 67) || (ctrlDown && key == 88) || (ctrlDown && key == 99) || (ctrlDown && key == 120) ||
			(ctrlDown && key == 86) || (ctrlDown && key == 118) || (isSlected == true && regex.test(keyString))){
		theEvent.returnValue = true;
		return true;
	}	    	
	if(!regex.test(keyString) || !objRegex.test(val.insert(getCursorPosition(target), keyString))) {
		theEvent.returnValue = false;
	    if(theEvent.preventDefault) theEvent.preventDefault();
	}; 		
}	
function addLocation(){
	$("#routeOptimizerDiv #routeOptErrContainer").hide();		
	var noOfLoc = $("#noOfLoc").val();
	var i,j;
	var html="";
	if(noOfLoc!=0 || noOfLoc!=""){	
		$("#routeOptimizerTbl").show();
		
		for(i=0;i<noOfLoc;i++){		
			alert(i);
			//$("#routeOptimizerTblBody tr").length;	
			html = '<tr id="trId'+i+'" class="tabletr odd">'+
			'<td>'+
			'Location - '+(i+1)+
			'</td>'+
			'<td>'+
			'<input type="text" name="locationList['+i+'].longitude" id="longitude_'+i+'" value="" onkeypress="validateInteger(event)"/>'+
			'</td>'+
			'<td>'+
			'<input type="text" name="locationList['+i+'].latitude" id="latitude_'+i+'" value="" onkeypress="validateInteger(event)"/>'+
			'</td>'+
			'</tr>';
			//alert(html);
			$("#routeOptimizerTblBody").append(html);
		}	
	}else{
		$("#routeOptimizerDiv #routeOptErrContainer").show();		
		$('#routeOptimizerDiv #routeOptErrContainer #routeOptErrMsg').prepend('<span class="errormsgItems">Please fill the required number of locations.</span>');
		$(window).scrollTop(0);
	}
}
function saveRouteOptimizer(){
	$('#routeOptimizerDiv #routeOptErrContainer #routeOptErrMsg').empty();
	var hasError = false;
	var lineItemsCount = 0;
	var longitude, latitude;
	$("#routeOptimizerForm #routeOptimizerTblBody tr.tabletr").each(function() {
		  lineItemsCount++;
		  $this = $(this);
		  $trId = $this.attr("id");
		  $trId = $trId.replace("trId", "");
		  longitude = $("#routeOptimizerForm #longitude_"+ $trId).val();
		 // || longitude.match(/^\d+$/)
		  latitude = $("#routeOptimizerForm #latitude_"+ $trId).val();
		  // || $.isNumeric( $("#routeOptimizerForm #longitude_"+ $trId).val())
		  if( $("#routeOptimizerForm #longitude_"+ $trId).val()==""){
			 $("#routeOptimizerForm #longitude_" + $trId).removeClass("routeFieldWithNoError").addClass("routeFieldWithError");
			 hasError = true;
		  }else{
			  $("#routeOptimizerForm #longitude_"+$trId).removeClass("routeFieldWithError").addClass("routeFieldWithNoError");
		  }		  
		  if($("#routeOptimizerForm #latitude_"+ $trId).val()==""){
				$("#routeOptimizerForm #latitude_"+$trId).removeClass("routeFieldWithNoError").addClass("routeFieldWithError");
				hasError = true;
		  }else{
			   $("#routeOptimizerForm #latitude_"+$trId).removeClass("routeFieldWithError").addClass("routeFieldWithNoError");
		  }	 
	  		  
	});
	if (!hasError) {		
		return true;
	} else {		
		$("#routeOptimizerDiv #routeOptErrContainer").show();		
		$('#routeOptimizerDiv #routeOptErrContainer #routeOptErrMsg').prepend('<span class="errormsgItems">Please fill the required highlighted fields.</span>');
		$(window).scrollTop(0);
		return false;
	}
	
}