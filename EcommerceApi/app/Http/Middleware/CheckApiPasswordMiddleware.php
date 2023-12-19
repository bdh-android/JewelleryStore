<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class CheckApiPasswordMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next)
    {

        $apiPassword=$request->api_password;
        if ($apiPassword!= env("API_PASSWORD",'hSHUOK89uTTNeAPzDWJv1Edf9')) {
            return response()->json(['message'=>'THIS IS NOT VALID API KEY']);
        }
        return $next($request);
    }
}
