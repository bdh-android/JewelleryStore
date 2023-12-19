<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    use HasFactory;
    protected $fillable = [
        'user_id',
        'status',
        'approve_payment',
        'total_price',
    ];


    protected $hidden = [

    ];
    public function rel_user(){
        return $this->belongTo('App\Models\User','user_id','id');
    }
    public function rel_order_details(){
        return $this->hasMany('App\Models\OrderDetails','order_id','id');

    }
}
