<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Category;
use App\Models\Product;
use App\Http\Traits\ResponseTrait;
class CategoryController extends Controller
{
    //

use ResponseTrait;
     public function index(){
     	 
         $category=Category::with(array('rel_products'=>function($q){
             $q->take(2)->skip(0);
         }))->get();//get all categories and limit thier products as whole entity not limit for every category
        
        $categories=Category::get();

        foreach ($categories as $category) {

            $products=Product::where('category_id',$category->id)->limit(6)->get();
            $category->products=$products;
        }
		sleep(2);//to simulate delay from real server
        return $this->returnData("data",$categories,"S000",$msg="");
         
    }
}
