<?php

namespace App\Http\Controllers;

use App\Models\Order;
use App\Models\User;
use App\Http\Traits\ResponseTrait;
use Illuminate\Http\Request;

class OrderController extends Controller
{
use ResponseTrait;


    public function makeOrder(Request $request){

        $userid=(int)$request->user_id;
        $total=$request->total_price;
        
        $productsList=$request->products;//cart items

		 
        try {


        $user=User::find($userid);
        //create new row and return the id of it
        $orderid= $user->rel_order()->create(['status'=>0,'total_price'=>$total])->id;

      
          foreach ($productsList as $product){
                 
              
                $productId=(int)$product['product_id'];
                $productcount=(int)$product['count'];
               $productsize=$product['size'];
               $order=Order::find($orderid);
               $order->rel_order_details()->create(['product_id'=>$productId,'count'=>$productcount,'size'=>$productsize]);

            }
			return $this->returnData("data",$orderid,"S000","data inserted");
          
        }catch (\Exception $e){
			return $this->returnData("data","","R000","error" .$e);
           // return response()->json(['msg'=>"error" .$e]);
        }

        
    }

    public function getUserOrders(Request $request){

        $user=User::find($request->user_id);
        $orders= $user->rel_order()->where('approve_payment',1)->get();
	 return $this->returnData("data",$orders,"S000","");
         //return response()->json($orders);
        
		  
    }
    public function getAllUsersOrders(){
     //for admin
    }

     public function deleteOrder(Request $request){
        $order_id=$request->order_id;
         
        $order =Order::find($order_id);

        $order->rel_order_details()->delete();
        $order->delete();
		return $this->returnData("data","","S000","done");
       // return response()->json("order is deleted");
     }

     
}
