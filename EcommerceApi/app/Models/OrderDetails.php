<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class OrderDetails extends Model
{
    use HasFactory;

    protected $table='orderdetails';
    protected $fillable = [
        'order_id',
        'product_id',
        'size',
        'count'
    ];


    protected $hidden = [

    ];
    public function rel_order(){
        return $this->belongTo('App\Models\Order','order_id','id');
    }
}
