<?php

namespace App\Http\Controllers;

use App\Models\Cart;
use App\Models\User;
use App\Models\Product;
use Illuminate\Http\Request;
use App\Http\Traits\ResponseTrait;

class CartController extends Controller
{

 use ResponseTrait;

    //this function delete old cart items then insert new items coming with request
    public function insertCart(Request $request){

        $userid=(int)$request->user_id;//get user id cart owner

        

        $productsList=$request->products;//cart items

         
            $user=User::find($userid);
            $cartitems=$user->rel_user_cart;//cart items related to this user
            $ids=[];
            $count=0;
            //delete old items in the user cart
            if (!$cartitems->isEmpty()){
              foreach ($cartitems as $d){
                  $cartItemId=$d->id;

                  $ids[$count]=$cartItemId;
                  ++$count;
              }
              if (!empty($ids)){
                  Cart::destroy($ids);
              }
            }
            //insert new cart items
            foreach ($productsList as $product){
                 
              
                $productId=(int)$product['product_id'];
                $productcount=(int)$product['amount'];
               $productsize=$product['size'];
                $user->rel_user_cart()->create(['product_id'=>$productId,'amount'=>$productcount,'size'=>$productsize]);

            }

            return $this->returnData("data","","S000","cart inserted successfuly");
           
    }

    //return all cart items 
    public function getCartItems($userid){
        $user=User::find((int)$userid);
        $cartitems=$user->rel_user_cart;//cart items related to this user
        if (!$cartitems->isEmpty()){
           foreach ($cartitems as $item) {
            $product_id = $item->product_id;
           $product= Product::find($product_id);
           $item->name=$product->name;
           $item->price=$product->price;
           $item->image=$product->image;
           }
           return $this->returnData("data",$cartitems);
        
        }else{
            return $this->returnData("data",[],"m001","Cart empty..");
          
        }
    }


    public function deleteCart(Request $request){

      $userid=$request->user_id;
       $user =User::find($userid);
         
        $user->rel_user_cart()->delete();

        return response()->json("cart is deleted");
      
    }
}
