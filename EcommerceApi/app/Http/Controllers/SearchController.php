<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Product;
use App\Models\Category;
use App\Http\Traits\ResponseTrait;
class SearchController extends Controller
{

use ResponseTrait;
 public function makeSuggestions($id,$query){

  $category=Category::find($id);
$message = "";
$products=$category->rel_products()->where('name','LIKE','%'.$query.'%')->limit(5)->get();
//return response()->json(['results'=>$products]);
 return $this->returnData("data",$products,"S000",$msg= $message);
 }


 public function getResults($id,$query){

  $category=Category::find($id);

$products=$category->rel_products()->where('name','LIKE','%'.$query.'%')->get();
//return response()->json(['results'=>$products]);
 return $this->returnData("data",$products,"S000",$msg="");
 }

}
