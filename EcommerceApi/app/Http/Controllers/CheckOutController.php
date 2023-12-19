<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
 use App\Models\Order;
 use App\Models\User;
 use App\Http\Traits\ResponseTrait;
class CheckOutController extends Controller
{
     use ResponseTrait;
 public function checkout()
 {

 	 $braintree = config('braintree');
    $clienttoken = $braintree->clientToken()->generate();
 	 
   return $this->returnData("client_token",$clienttoken,"S000","");
 	 
 }



public function sale(Request $request)
{
	 
	$order_id=$request->order_id;//we pass order_id to update order status when user pay successfully or delete the order if not success
     $user_Id=$request->user_id;

    $braintree = config('braintree');
    $result = $braintree->transaction()->sale([
        'amount' => $request->amount,
        'paymentMethodNonce' =>$request->nonce,
		'options'=>['submitForSettlement'=>True]
    ]);
    
     if ($result->success) {
     	Order::where('id',$order_id)->update(['approve_payment'=>1]);
     	$this->clearUserCart($user_Id);
     }else {
        
        $this->clearOrder($order_id);
        
     }
	 
    return response()->json($result);
}

 public function clearOrder($order_id){
       

        $order =Order::find($order_id);

        $order->rel_order_details()->delete();
        $order->delete();
       
     }

public function clearUserCart($userId){

       
       $user =User::find($userId);
         
        $user->rel_user_cart()->delete();

        
      
    }
}
