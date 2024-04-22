<?php

namespace App\Http\Traits;

trait ResponseTrait
{


public function returnError($errorNumber,$msg){
	return response()->json([
		'status'=> false,
		'errorNumber'=>$errorNumber,
		'msg'=>$msg
	]);
}
public function returnSuccessMessage($msg="",$errorNumber="S000"){
	return response()->json([
		'status'=> true,
		'errorNumber'=>$errorNumber,
		'msg'=>$msg
	]);
}

public function returnData($key="data",$value="",$errCode="S000",$msg=""){
	return response()->json([
		'status'=> true,
		'errorNumber'=>$errCode,
		'msg'=>$msg,
		$key=>$value

	]);
}


public function returnValidationError($code="E0011",$validator){
	//return $this->returnData("error","",$code,$validator->errors()->first());
	return $this->returnError($code,$validator->errors()->first());
}
public function returnCodeAccordingToInput($validator){
	$input=array_keys($validator->errors()->toArray());
	$code=$this->getErrorCode($input[0]);
	return $code;
}

public function getErrorCode($input){
	
	if($input=="name") 
		return 'E001';

	else if ($input=="email") 
		return 'E002';

	else if ($input=="password") 
	    return 'E003';	 

    else if ($input=="address1") 
	return 'E004';

    else if ($input=="address2") 
	return 'E005';

    else if ($input=="country") 
	return 'E006';

    else if ($input=="state") 
	return 'E007';

    else if ($input=="city") 
	return 'E008';

    else if ($input=="phone_number") 
	return 'E009';
    
    else if ($input=="zip") 
	return 'E0010';
     
    else if ($input=="new_password") 
	    return 'E0012';
	else if ($input=="confirm_password") 
	    return 'E0013';
	else if ($input=="old_password") 
	    return 'E0014';
    else return "";
	
}


}