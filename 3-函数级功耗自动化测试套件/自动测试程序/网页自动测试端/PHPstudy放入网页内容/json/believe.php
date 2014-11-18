<?php
	
		
	if( $_GET['tag'] == "add")
	{
		$json = $_POST['param'];
		
		file_put_contents('abc.json',$json);
	
		if(count(json_decode($json)))		
		{
			$result['msg'] = "save success!";
			echo json_encode($result);
		}else{
			$result['msg'] = "Data Empty!";
			echo json_encode($result);
		}
	}else {
		$result['msg'] = "save error!";
		echo json_encode($result);		
	}		
?>