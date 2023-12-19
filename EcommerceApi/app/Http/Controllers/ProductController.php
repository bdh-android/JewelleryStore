<?php

namespace App\Http\Controllers;

use App\Models\Category;
use Illuminate\Http\Request;
use App\Models\Product;
use App\Http\Traits\ResponseTrait;
class ProductController extends Controller
{
    //

use ResponseTrait;
     
    public function getProductsByCategoryId($id){

        $category=Category::find($id);
        
        $products=$category->rel_products()->paginate(10);
		sleep(2);//to simulate some delay . test
       
         return response()->json($products);
    }


 
    public function getProductById($id){


        $products=Product::with('rel_Product_size','rel_Product_image')->where('id',$id)->find($id);
		sleep(2);//to simulate some delay . test
        return $this->returnData("product",$products,"","");
        
    }

   

}
