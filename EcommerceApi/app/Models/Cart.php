<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Cart extends Model
{
    use HasFactory;


    protected $fillable = [
        'user_id',
        'product_id',
        'amount',
        'size'
    ];
     protected $hidden = [
        'created_at',
        'updated_at',
    ];
    public function rel_user(){
        return $this->belongsTo('App\Models\User','user_id','id');
    }
}
