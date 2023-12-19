<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Shipping_Address extends Model
{
    use HasFactory;

    protected $table='shipping_adresses';
     protected $fillable=[
     	 
     'address1',
     'address2',
     'country',
     'state',
     'city',
     'phone_number',
     'zip'

     ];
    
    public function rel_user(){
        return $this->belongsTo('App\Models\User','user_id','id');
    }
}
