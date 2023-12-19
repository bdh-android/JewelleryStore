<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;
use App\Http\Traits\ResponseTrait;
class CheckUserToken
{
    use ResponseTrait;
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    //check if user has token
    public function handle(Request $request, Closure $next)
    {
        
        $user=null;
        try{
             
            $user=JWTAuth::parseToken()->authenticate();

           
        }catch(\Exception $e){
        if ($e instanceof \Tymon\JWTAuth\Exceptions\TokenInvalidException) {
           return $this->returnError("E0001",'INVALID TOKEN');
              
        }else if ($e instanceof \Tymon\JWTAuth\Exceptions\TokenExpiredException) {
             return $this->returnError("E0002",'EXPIRED TOKEN');
              
        }else {
             return $this->returnError("E0003",'TOKEN NOT FOUND');
             
        }

        } catch(\Throwable $e){
            if ($e instanceof \Tymon\JWTAuth\Exceptions\TokenInvalidException){
                 return $this->returnError("E0001",'INVALID TOKEN');
                
            }else {
                 return $this->returnError("E0003",'TOKEN NOT FOUND');
             
        }
        }

        if (!$user) {
             return $this->returnError("E0004",'not authenticated');
            

        }

        return $next($request);
    }
}
