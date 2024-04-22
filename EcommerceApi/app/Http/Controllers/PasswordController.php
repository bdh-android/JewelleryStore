<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Auth;
use Tymon\JWTAuth\Facades\JWTAuth;
use App\Models\User;
use App\Http\Traits\ResponseTrait;
class PasswordController extends Controller
{

	 use ResponseTrait;


    public function reset(Request $request){

    	$input=$request->all();
    	$token= $request->token;
    	$user = JWTAuth::toUser($token);//return user data
    	$userid=$user->id;

    	$rules=array(
    		'old_password'=>'required',
    		'new_password'=>'required|min:8',
    	    'confirm_password'=>'required|same:new_password');
    	$validator=Validator::make($input,$rules);
    	if($validator->fails()){
			 $code=$this->returnCodeAccordingToInput($validator);
			 return $this->returnValidationError($code,$validator);


    	}else {
    		try{
    			if ((Hash::check(request('old_password'),Auth::user()->password))==false) {
					//return $this->returnData("message","Check your old password.","E0021","Check your old password.");
    				 return $this->returnError("E0021","Check your old password.");
    			}else if((Hash::check(request('new_password'),Auth::user()->password))==true){
					//return $this->returnData("message","please enter a new password which is not similer to old password..","E0022","please enter a new password which is not similer to old password.");
                     return $this->returnError("E0022","please enter a new password which is not similer to old password.");
    			}else {
    				User::where('id',$userid)->update(['password'=>Hash::make($input['new_password'])]);
    				 return $this->returnData("data","password updated successfully","S000","password updated successfully");
    			}

    		}catch(\Exception $ex){

    			if (isset($ex->errorInfo[2])) {
    			$msg=$ex->error->errorInfo[2];
    			}else{
    				$msg=$ex->getMessage();
    			}
				//return $this->returnData("error",$msg,"500",$msg);
    			return $this->returnError("500", $msg);
    		}
    	}


    }
}
