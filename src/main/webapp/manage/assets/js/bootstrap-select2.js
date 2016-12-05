$(function(){
	
	
})

function showOption(id,data){

	var htm = "";
	for(var i=0 ;i<data.length;i++){
		htm+="<option value='"+data[i].id+"'>"+data[i].text+"</option>";
	}
	
	$("#"+id).append(htm);
	
	$("#"+id).selectpicker();
}