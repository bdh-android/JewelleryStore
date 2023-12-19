<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use App\Providers\Braintree_Configuration;
Use Braintree;
class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        
       /* \Braintree_Configuration::environment(env(‘BRAINTREE_ENV’));
        \Braintree_Configuration::merchantId(env(‘BRAINTREE_MERCHANT_ID’));
        \Braintree_Configuration::publicKey(env(‘BRAINTREE_PUBLIC_KEY’));
        \Braintree_Configuration::privateKey(env(‘BRAINTREE_PRIVATE_KEY’));
*/
         // braintree setup
    $environment = env('‘BRAINTREE_ENV’');
    $merchantId=env('‘BRAINTREE_MERCHANT_ID’');
    $publicKey=env('‘BRAINTREE_PUBLIC_KEY’');
    $privateKey=env('‘BRAINTREE_PRIVATE_KEY’');
  /*  $gateway = new Braintree\Gateway([
    'environment' => 'sandbox',
    'merchantId' => '3k78fmrcbthmr39b',
    'publicKey' => 'cqtvp8tpcd7nx2jq',
    'privateKey' => 'aac349de7c45c0a0f1a082deb0dbbab4'
]);*/
     $braintree = new \Braintree\Gateway([
        'environment' =>'sandbox',
        'merchantId' => '3k78fmrcbthmr39b',
        'publicKey' => 'cqtvp8tpcd7nx2jq',
        'privateKey' => 'aac349de7c45c0a0f1a082deb0dbbab4'
    ]);
    config(['braintree' => $braintree]); 
 
    }
}
