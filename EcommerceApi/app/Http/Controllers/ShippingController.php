<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Shipping_Address;
use App\Models\User;
use App\Http\Traits\ResponseTrait;
use Illuminate\Support\Facades\Validator;
class ShippingController extends Controller
{
    use ResponseTrait; 

 
 public function saveShippingDetails(Request $request){



    $validator= $this->validator($request->all()); 

            if ($validator->fails()){
              $code=$this->returnCodeAccordingToInput($validator);
              return $this->returnValidationError($code,$validator);
                
            }
    $user_id=$request->user_id;

    $user=User::find($user_id);
  
    
    $user->rel_user_shipping_address()->updateOrCreate([
   	'user_id'=>$request->user_id],[
   		'user_id'=>$request->user_id,
    'address1'=>$request->address1,
     'address2'=>$request->address2,
     'country'=>$request->country,
     'state'=>$request->state,
     'city'=>$request->city,
     'phone_number'=>$request->phone_number,
     'zip'=>$request->zip

   ]);

   return $this->returnData("message","done");
   
 }

 protected function validator(array $data)
    {
         
        return Validator::make($data, [
            'address1' => ['required', 'string', 'max:255'],
            'address2' => ['nullable','max:255'],
            'country' => ['required', 'string', 'max:100'],
            'state' => ['required', 'string', 'max:100'],
            'city' => ['required', 'string', 'max:100'],
            'phone_number' => ['required', 'regex:/^([0-9\s\-\+\(\)]*)$/', 'min:10'],
            'zip' => ['required', 'regex:/\b\d{5}\b/']
        ]);
         
    }


    public function getShippingDetails(Request $request){
    	 $user_id=$request->user_id;

    $user=User::find($user_id);

    $address=$user->rel_user_shipping_address;
    if (empty($address)) {
    	 return $this->returnData("message","not found");
    }
    return $this->returnData("data",$address);
    }

}
