<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Product_Sizes extends Model
{
    protected $table='product_sizes';
    use HasFactory;


    public function rel_Product(){
        return $this->belongTo('App\Models\Product','product_id','id');
    }
}
