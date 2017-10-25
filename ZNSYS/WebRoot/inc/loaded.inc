<script>
try{
	$('#popup_container',parent.document).remove();
	$('iframe',parent.document).show();
}catch(exception){
	alert("load移除失败！");
}
</script>