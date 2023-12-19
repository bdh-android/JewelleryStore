<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Tymon\JWTAuth\Facades\JWTAuth;
use App\Http\Traits\ResponseTrait;

//use Illuminate\Validation\Validator;
class AuthController extends Controller
{
    use ResponseTrait;

	public function __construct(){
		 
	}

    /**
     * Get a JWT via given credentials.
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function login(Request $request)
    {
		
    	$credentials=$request->only(['email', 'password']);
        $validator= $this->validator($request->all(),true); 
            if ($validator->fails()){
              $code=$this->returnCodeAccordingToInput($validator);
              return $this->returnValidationError($code,$validator);
              
            }
        
        if (! $token = $this->guard()->attempt($credentials)) {
			return $this->returnData("error","Unauthorized","U000","your password or email is wrong");
          
        }

        return $this->respondWithToken($token);
    }


    /**
     * Get the authenticated User.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    //return logged in user
    public function me(Request $request)
    {
          
         $user=$this->guard()->user();
          return $this->returnData("data",$user,"S000","");
         
    }

    /**
     * Log the user out (Invalidate the token).
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function logout()
    {
        $this->guard()->logout();

         return $this->returnData("message","","S000","successfully logged out");
        
    }

    /**
     * Refresh a token.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function refresh()
    {
        return $this->respondWithToken(auth()->refresh());
    }

    /**
     * Get the token array structure.
     *
     * @param  string $token
     *
     * @return \Illuminate\Http\JsonResponse
     */
    protected function respondWithToken($token)
    {

       $data=[
            'access_token' => $token,
            'token_type' => 'bearer',
            'expires_in' => $this->guard()->factory()->getTTL()*60,
			'user_id'=> $this->guard()->user()->id
        ];

        return $this->returnData("data",$data,"S000","");
         
    }




    protected function validator(array $data,$isLogin)
    {
         
        if ($isLogin) {
            return Validator::make($data,[
            'email' => ['required', 'string', 'email', 'max:255','exists:users,email'],
            'password' => ['required', 'string', 'min:8'],
                  ]);
        }else{
        return Validator::make($data, [
            'name' => ['required', 'string', 'max:255'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
            'password' => ['required', 'string', 'min:8'],
        ],['name.required'=>'name is required',
            'email.required'=>'email is required',
            'password.required'=>'password is required']);
        }
    }

  
    public function registernewuser(Request $request){


           $validator= $this->validator($request->all(),false);//->validate();

            if ($validator->fails()){
              $code=$this->returnCodeAccordingToInput($validator);
              return $this->returnValidationError($code,$validator);
                
            }

        


       $user= User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password)
			
        ]);
        return $this->returnData("data",$user,"S000","user created");
        
    }



    public function guard(){
        return Auth::guard('api');
    }
}
