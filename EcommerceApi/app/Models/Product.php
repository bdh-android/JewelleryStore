<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
 
class Product extends Model
{
    use HasFactory;


protected $fillable = [
        'name',
        'description',
        'image',
        'price',
        'amount',
        'category_id'
        
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'created_at',
        'updated_at',
    ];

    
    public function rel_category(){
    	return $this->belongTo('App\Models\Category','category_id','id');
    }
    public function  rel_Product_image(){
        return $this->hasMany('App\Models\Product_Images','product_id','id');
    }
    public function  rel_Product_size(){
        return $this->hasMany('App\Models\Product_Sizes','product_id','id');
    }



}
