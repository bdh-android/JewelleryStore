<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Payment_Details extends Model
{
    use HasFactory;

    protected $table='payment_details';


    public function rel_user(){
        return $this->belongsTo('App\Models\User','user_id','id');
    }

}
