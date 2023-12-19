package com.example.e_commerce_store.net;

import com.example.e_commerce_store.models.Cart;
import com.example.e_commerce_store.models.CategoryResponse;
import com.example.e_commerce_store.models.Feed;
import com.example.e_commerce_store.models.LoginResponse;
import com.example.e_commerce_store.models.Order;
import com.example.e_commerce_store.models.OrderResponse;
import com.example.e_commerce_store.models.PasswordResponse;
import com.example.e_commerce_store.models.PaymentRespone;
import com.example.e_commerce_store.models.PaymentResult;
import com.example.e_commerce_store.models.ProductDetail;
import com.example.e_commerce_store.models.RegisterResponse;
import com.example.e_commerce_store.models.SearchResults;
import com.example.e_commerce_store.models.ShippingDetails;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {



//sign up
    @FormUrlEncoded
    @POST("auth/registernewuser?")
    Call<RegisterResponse> createNewUser(@Field("name") String name,@Field("email") String email, @Field("password") String password, @Field("api_password") String api_password);


   //login
    @FormUrlEncoded
    @POST("auth/login?")
    Call<LoginResponse> loginUser(@Field("email") String email, @Field("password") String password, @Field("api_password") String api_password);


    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("auth/me?")
    Call<RegisterResponse> me(@Query("api_password") String api_password,@Header("Authorization")String auth);

//logout
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("auth/logout")
    Call<LoginResponse> Logout(@Query("api_password") String api_password,@Header("Authorization")String auth);
     //reset Password
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("auth/reset_password")
    Call<PasswordResponse> resetPassword(@Query("api_password") String api_password, @Query("old_password")String oldPassword, @Query("new_password")String newPassword, @Query("confirm_password")String confirmPassword, @Header("Authorization")String auth);




    //get all categories
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("categories_all?")
    Call<CategoryResponse> getAllCategories(@Query("api_password") String api_password, @Header("Authorization") String auth);
// get all gategory's products
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("all_products_by_category_id/{id}")
    Call<Feed> getProductsByCategoryId(@Path("id") int id, @Query("page") int page, @Query("api_password")String apiKey,
                                       @Header("Authorization")String auth);


    //get one product by its id
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_product_by_id/{id}")
    Call<ProductDetail> getProductDetailsById(@Path("id") int id,@Query("api_password")String apiKey, @Header("Authorization")String auth);


    //get Cart
    // get user cart items
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_cart_items_by_user_id/{User_id}")
    Call<Cart> getCartItems(@Path("User_id") int id, @Query("api_password")String apiKey, @Header("Authorization")String auth);

   //insert to cart
    @Headers({"Content-Type:application/json"})
    @POST("insert_cart")
    Call<Cart> insertItemsIntoCart(@Query("api_password")String apiKey, @Body JsonObject data, @Header("Authorization")String auth);

    //insert shipping address
    @FormUrlEncoded
    @POST("save_user_shipping_details")
    Call<ShippingDetails> insertShippingDetails(@Query("api_password")String apiKey, @FieldMap Map<String,String> params, @Header("Authorization")String auth);

    //get shipping address
    @Headers({"Content-Type:application/json"})
    @GET("get_user_shipping_details")
    Call<ShippingDetails> getShippingDetails(@Query("api_password")String apiKey,@Header("Authorization")String auth);


   //  search
   @Headers({"Content-Type:application/json"})
    @GET("get_search_suggestions/{category_id}/{search_word}")
    Call<SearchResults> getSearchSuggestions(@Path("category_id") int cat_id,@Path("search_word") String searchWord,@Query("api_password")String apiKey,@Header("Authorization")String auth);

    //search results
    @Headers({"Content-Type:application/json"})
    @GET("get_search_results/{category_id}/{search_word}")
    Call<SearchResults> getSearchResults(@Path("category_id") int cat_id,@Path("search_word") String searchWord,@Query("api_password")String apiKey,@Header("Authorization")String auth);


    //payment
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("checkout")
    Call<PaymentRespone> getGatewayToken(@Query("api_password") String api_password, @Header("Authorization") String auth);


    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("payment")
    Call<PaymentResult> getPaymentState(@Query("api_password") String api_password,@Query("order_id") int orderId,@Query("user_id") int userId,@Query("amount") String amount, @Query("nonce")String nonce, @Header("Authorization") String auth);


    //make an order

    @Headers({"Content-Type:application/json"})
    @POST("make_order")
    Call<OrderResponse> makeOrder(@Query("api_password")String apiKey, @Body JsonObject data, @Header("Authorization")String auth);


    //get all user orders

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("get_all_user_orders?")
    Call<List<Order>> getAllUserOrders(@Query("api_password") String api_password,@Query("user_id") int user_id, @Header("Authorization") String auth);

}
