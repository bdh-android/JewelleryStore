<?php


use App\Http\Controllers\CartController;
use App\Http\Controllers\OrderController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\ShippingController;
use App\Http\Controllers\SearchController;
use App\Http\Controllers\CheckOutController;
use App\Http\Controllers\PasswordController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/






Route::group(['middleware'=>['api','checkApiPassword'],'prefix'=>'auth'],function ($router){


    Route::post('registernewuser',[AuthController::class,'registernewuser']);
    Route::post('login',[AuthController::class,'login']);

    
    
	Route::group(['middleware'=>['CheckUserToken']],function ($router){
        Route::post('logout',[AuthController::class,'logout']);
        Route::post('me',[AuthController::class,'me']);
        Route::post('reset_password',[PasswordController::class,'reset']);

    });

 });

Route::group(['middleware'=>['api','checkApiPassword','CheckUserToken']],function($router){

	Route::get('categories_all',[CategoryController::class,'index'])->name('allCategories');
	Route::get('all_products_by_category_id/{id}',[ProductController::class,'getProductsByCategoryId']);
	Route::get('get_product_by_id/{id}',[ProductController::class,'getProductById']);

    Route::post('make_order',[OrderController::class,'makeOrder']);
    Route::get('get_all_user_orders',[OrderController::class,'getUserOrders']);
    Route::get('delete_order',[OrderController::class,'deleteOrder']);

    Route::post('insert_cart',[CartController::class,'insertCart']);
    Route::get('get_cart_items_by_user_id/{userId}',[CartController::class,'getCartItems']);
    Route::get('delete_cart',[CartController::class,'deleteCart']);

    Route::post('save_user_shipping_details',[ShippingController::class,'saveShippingDetails']);
    Route::get('get_user_shipping_details',[ShippingController::class,'getShippingDetails']);
    Route::get('get_search_suggestions/{id}/{search}',[SearchController::class,'makeSuggestions']);
    Route::get('get_search_results/{id}/{search}',[SearchController::class,'getResults']);


    Route::post('checkout',[CheckOutController::class,'checkout']);
    Route::post('payment',[CheckOutController::class,'sale']);
});


Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

